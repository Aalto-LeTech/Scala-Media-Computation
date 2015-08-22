package aalto.smcl.platform


import java.awt.Graphics2D
import java.awt.geom.{AffineTransform, Rectangle2D}
import java.awt.image._

import aalto.smcl.bitmaps.BitmapSettingKeys.DefaultBackground
import aalto.smcl.bitmaps.BitmapValidator
import aalto.smcl.bitmaps.immutable.ConvolutionKernel
import aalto.smcl.common._
import aalto.smcl.platform.PlatformSettingKeys.PlatformBitmapInterpolationMethod




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object PlatformBitmapBuffer {

  /** */
  val NormalizedBufferType = BufferedImage.TYPE_INT_ARGB


  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @return
   */
  def apply(widthInPixels: Int, heightInPixels: Int): PlatformBitmapBuffer = {
    BitmapValidator.validateBitmapSize(widthInPixels, heightInPixels)

    val newBuffer = createNormalizedLowLevelBitmapBufferOf(widthInPixels, heightInPixels)

    new PlatformBitmapBuffer(newBuffer)
  }

  /**
   *
   *
   * @param awtBufferedImage
   * @return
   */
  def apply(awtBufferedImage: BufferedImage): PlatformBitmapBuffer = {
    require(awtBufferedImage != null, "Provided image buffer cannot be null.")

    BitmapValidator.validateBitmapSize(awtBufferedImage.getWidth, awtBufferedImage.getHeight)

    val normalizedAwtBuffer = convertToNormalizedLowLevelBitmapBufferIfNecessary(awtBufferedImage)

    new PlatformBitmapBuffer(normalizedAwtBuffer)
  }

  /**
   *
   *
   * @param width
   * @param height
   * @return
   */
  private[platform] def createNormalizedLowLevelBitmapBufferOf(width: Int, height: Int) =
    new BufferedImage(width, height, NormalizedBufferType)


  /**
   *
   *
   * @param buffer
   * @return
   */
  private[platform] def convertToNormalizedLowLevelBitmapBufferIfNecessary(
    buffer: BufferedImage): BufferedImage = {

    var bufferCandidate = buffer

    if (bufferCandidate.getType != NormalizedBufferType) {
      val newBuffer = createNormalizedLowLevelBitmapBufferOf(
        bufferCandidate.getWidth, bufferCandidate.getHeight)

      var drawingSurface: Graphics2D = null
      try {
        drawingSurface = newBuffer.createGraphics()
        drawingSurface.drawImage(bufferCandidate, null, 0, 0)
      }
      finally {
        drawingSurface.dispose()
      }

      bufferCandidate = newBuffer
    }

    bufferCandidate
  }

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] class PlatformBitmapBuffer private(val awtBufferedImage: BufferedImage) {

  /** */
  lazy val widthInPixels: Int = awtBufferedImage.getWidth

  /** */
  lazy val heightInPixels: Int = awtBufferedImage.getHeight

  /** */
  def drawingSurface(): PlatformDrawingSurface = PlatformDrawingSurface(this)

  /**
   *
   *
   * @param transformation
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @return
   */
  def createTransfomedVersionWith(
    transformation: AffineTransformation,
    resizeCanvasBasedOnTransformation: Boolean = true,
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground)): PlatformBitmapBuffer = {

    val globalInterpolationMethod =
      GS.enumSettingFor[BitmapInterpolationMethod.Value](PlatformBitmapInterpolationMethod).value.id

    var resultingImageWidth: Int = awtBufferedImage.getWidth
    var resultingImageHeight: Int = awtBufferedImage.getHeight
    val lowLevelTransformation = transformation.platformAffineTransform.awtAffineTransformation
    val transformedContentBoundaries: Rectangle2D =
      new AffineTransformOp(lowLevelTransformation, globalInterpolationMethod)
        .getBounds2D(awtBufferedImage)

    if (resizeCanvasBasedOnTransformation) {
      val offsetLeft = -transformedContentBoundaries.getMinX
      val offsetTop = -transformedContentBoundaries.getMinY
      val offsetRight = transformedContentBoundaries.getMaxX - awtBufferedImage.getWidth
      val offsetBottom = transformedContentBoundaries.getMaxY - awtBufferedImage.getHeight

      if (offsetTop > 0 || offsetLeft > 0) {
        val translationToBringTheRotatedBitmapFullyVisible =
          AffineTransform.getTranslateInstance(offsetLeft, offsetTop)

        lowLevelTransformation preConcatenate translationToBringTheRotatedBitmapFullyVisible
      }

      resultingImageWidth = Math.round(awtBufferedImage.getWidth + offsetLeft + offsetRight).toInt
      resultingImageHeight = Math.round(awtBufferedImage.getHeight + offsetTop + offsetBottom).toInt
    }

    val finalTransformOperation = new AffineTransformOp(lowLevelTransformation, globalInterpolationMethod)
    val resultingBuffer: PlatformBitmapBuffer = PlatformBitmapBuffer(resultingImageWidth, resultingImageHeight)

    resultingBuffer drawingSurface() clearUsing backgroundColor

    finalTransformOperation.filter(awtBufferedImage, resultingBuffer.awtBufferedImage)

    resultingBuffer
  }

  /**
   *
   *
   * @param minX
   * @param minY
   * @param maxX
   * @param maxY
   * @return
   */
  def boundaryOverflowsForLTRB(
    minX: Double,
    minY: Double,
    maxX: Double,
    maxY: Double): (Double, Double, Double, Double) = {

    val overflowLeft = if (minX < 0) -minX else 0
    val overflowTop = if (minY < 0) -minY else 0
    val overflowRight = if (maxX > awtBufferedImage.getWidth) maxX - awtBufferedImage.getWidth else 0
    val overflowBottom = if (maxY > awtBufferedImage.getHeight) maxY - awtBufferedImage.getHeight else 0

    (overflowLeft, overflowTop, overflowRight, overflowBottom)
  }

  /**
   *
   *
   * @param kernel
   * @return
   */
  def createFilteredVersionWith(kernel: ConvolutionKernel): PlatformBitmapBuffer = {
    val lowLevelKernel = new Kernel(kernel.width, kernel.height, kernel.toRowMajorArray)
    val operation = new ConvolveOp(lowLevelKernel)

    val newLowLevelBitmap = operation.filter(
      awtBufferedImage,
      emptyAlike().awtBufferedImage)

    PlatformBitmapBuffer(newLowLevelBitmap)
  }

  /**
   *
   *
   * @param translator
   * @return
   */
  def createFilteredVersionWith(translator: RGBAComponentTranslationTable): PlatformBitmapBuffer = {
    val lowLevelLookupTable = new ShortLookupTable(0, translator.toArray)
    val operation = new LookupOp(lowLevelLookupTable, null)

    val newLowLevelBitmap = operation.filter(
      awtBufferedImage,
      emptyAlike().awtBufferedImage)

    PlatformBitmapBuffer(newLowLevelBitmap)
  }

  /**
   *
   *
   * @return
   */
  def copy(): PlatformBitmapBuffer =
    PlatformBitmapBuffer(BitmapUtils.deepCopy(awtBufferedImage))

  /**
   *
   *
   * @return
   */
  def emptyAlike(): PlatformBitmapBuffer =
    PlatformBitmapBuffer(widthInPixels, heightInPixels)

}
