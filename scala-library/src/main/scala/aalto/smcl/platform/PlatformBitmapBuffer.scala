package aalto.smcl.platform


import java.awt.Graphics2D
import java.awt.image._

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

  /** */
  val OneByOneSpecimen = PlatformBitmapBuffer(1, 1)

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
   * @return
   */
  def createTransfomedVersionWith(transformation: AffineTransformation): PlatformBitmapBuffer = {
    val operation = new AffineTransformOp(
      transformation.platformAffineTransform.awtAffineTransformation,
      GS.enumSettingFor[BitmapInterpolationMethod.Value](PlatformBitmapInterpolationMethod).value.id)

    val newLowLevelBitmap = operation.filter(
      awtBufferedImage,
      emptyAlike().awtBufferedImage)

    PlatformBitmapBuffer(newLowLevelBitmap)
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
