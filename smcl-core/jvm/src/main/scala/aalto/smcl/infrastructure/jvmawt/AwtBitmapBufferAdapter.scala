package aalto.smcl.infrastructure.jvmawt


import java.awt.geom.{AffineTransform, Rectangle2D}
import java.awt.image._
import java.awt.{AlphaComposite, Graphics2D}
import java.io.File
import javax.imageio.ImageIO

import scala.util.{Failure, Try}

import aalto.smcl.bitmaps._
import aalto.smcl.colors._
import aalto.smcl.common._
import aalto.smcl.infrastructure._
import aalto.smcl.infrastructure.exceptions.{SMCLFunctionExecutionError, SMCLInvalidColorComponentArrayLengthError}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object AwtBitmapBufferAdapter {

  /** */
  val NormalizedBufferType = BufferedImage.TYPE_INT_ARGB

  /** */
  private val _bitmapValidator: BitmapValidator = new BitmapValidator()


  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @return
   */
  def apply(widthInPixels: Int, heightInPixels: Int): AwtBitmapBufferAdapter = {
    _bitmapValidator.validateBitmapSize(widthInPixels, heightInPixels)

    val newBuffer = createNormalizedLowLevelBitmapBufferOf(widthInPixels, heightInPixels)

    new AwtBitmapBufferAdapter(newBuffer)
  }

  /**
   *
   *
   * @param awtBufferedImage
   * @return
   */
  def apply(awtBufferedImage: BufferedImage): AwtBitmapBufferAdapter = {
    require(awtBufferedImage != null, "Provided image buffer cannot be null.")

    _bitmapValidator.validateBitmapSize(awtBufferedImage.getWidth, awtBufferedImage.getHeight)

    val normalizedAwtBuffer = convertToNormalizedLowLevelBitmapBufferIfNecessary(awtBufferedImage)

    new AwtBitmapBufferAdapter(normalizedAwtBuffer)
  }

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @return
   */
  private[infrastructure] def createNormalizedLowLevelBitmapBufferOf(
      widthInPixels: Int,
      heightInPixels: Int): BufferedImage = {

    val newBuffer = new BufferedImage(widthInPixels, heightInPixels, NormalizedBufferType)

    var drawingSurface: Graphics2D = null
    try {
      drawingSurface = newBuffer.createGraphics()
      drawingSurface.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC))
      drawingSurface.setColor(GS.colorFor(DefaultBackground).toAwtColor)
      drawingSurface.fillRect(0, 0, widthInPixels, heightInPixels)
    }
    finally {
      drawingSurface.dispose()
    }

    newBuffer
  }


  /**
   *
   *
   * @param buffer
   * @return
   */
  private[infrastructure] def convertToNormalizedLowLevelBitmapBufferIfNecessary(
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
private[smcl]
class AwtBitmapBufferAdapter private(val awtBufferedImage: BufferedImage) extends BitmapBufferAdapter {

  /**
   *
   *
   * @return
   */
  override def widthInPixels: Int = awtBufferedImage.getWidth

  /**
   *
   *
   * @return
   */
  override def heightInPixels: Int = awtBufferedImage.getHeight

  /**
   *
   *
   * @return
   */
  override def areaInPixels: Int = heightInPixels * widthInPixels

  /**
   *
   *
   * @return
   */
  override def drawingSurface: AwtDrawingSurfaceAdapter = AwtDrawingSurfaceAdapter(this)


  /**
   *
   *
   * @param colorToTrim
   * @return
   */
  override def trim(colorToTrim: RGBAColor = GS.colorFor(DefaultBackground)): AwtBitmapBufferAdapter = {
    val (reds, greens, blues, opacities) = colorComponentArrays()

    val redToTrim = colorToTrim.red
    val greenToTrim = colorToTrim.green
    val blueToTrim = colorToTrim.blue
    val opacityToTrim = colorToTrim.opacity

    // Check for "empty" columns on the left edge (and also if the raster is fully "empty")
    var row = 0
    var column = 0
    var deviationFound: Boolean = false
    while (column < widthInPixels && !deviationFound) {
      while (row < heightInPixels && !deviationFound) {
        val linearPos = (row * widthInPixels) + column

        if (reds(linearPos) != redToTrim
            || greens(linearPos) != greenToTrim
            || blues(linearPos) != blueToTrim
            || opacities(linearPos) != opacityToTrim) {

          deviationFound = true
        }

        row += 1
      }

      column += 1
      row = 0
    }
    if (column >= widthInPixels && !deviationFound) {
      // Raster was fully "empty" ==> return 1 x 1 bitmap
      return AwtBitmapBufferAdapter(1, 1)
    }

    val firstNonEmptyColumn = column - 1

    // At this point, we know that the raster was not fully "empty", which means that there has to be at least one
    // deviating pixel. Consequently, the following loops have to stop before crossing the opposite edge of the raster.


    // Check for "empty" columns on the right edge
    row = 0
    column = widthInPixels - 1
    deviationFound = false
    while (column >= 0 && !deviationFound) {
      while (row < heightInPixels && !deviationFound) {
        val linearPos = (row * widthInPixels) + column

        if (reds(linearPos) != redToTrim
            || greens(linearPos) != greenToTrim
            || blues(linearPos) != blueToTrim
            || opacities(linearPos) != opacityToTrim) {

          deviationFound = true
        }

        row += 1
      }

      column -= 1
      row = 0
    }

    val lastNonEmptyColumn = column + 1

    // Check for "empty" rows on the top edge
    row = 0
    column = 0
    deviationFound = false
    while (row < heightInPixels && !deviationFound) {
      while (column < widthInPixels && !deviationFound) {
        val linearPos = (row * widthInPixels) + column

        if (reds(linearPos) != redToTrim
            || greens(linearPos) != greenToTrim
            || blues(linearPos) != blueToTrim
            || opacities(linearPos) != opacityToTrim) {

          deviationFound = true
        }

        column += 1
      }

      row += 1
      column = 0
    }

    val firstNonEmptyRow = row - 1


    // Check for "empty" rows on the bottom edge
    row = heightInPixels - 1
    column = 0
    deviationFound = false
    while (row >= 0 && !deviationFound) {
      while (column < widthInPixels && !deviationFound) {
        val linearPos = (row * widthInPixels) + column

        if (reds(linearPos) != redToTrim
            || greens(linearPos) != greenToTrim
            || blues(linearPos) != blueToTrim
            || opacities(linearPos) != opacityToTrim) {

          deviationFound = true
        }

        column += 1
      }

      row -= 1
      column = 0
    }

    val lastNonEmptyRow = row + 1


    // Return a copy of the non-empty portion of the source bitmap
    copyPortionXYXY(firstNonEmptyColumn, firstNonEmptyRow, lastNonEmptyColumn, lastNonEmptyRow)
  }

  /**
   *
   *
   * @param function
   * @return
   */
  override def iteratePixelsWith(function: (Int, Int, Int, Int) => (Int, Int, Int, Int)): AwtBitmapBufferAdapter = {
    val newBuffer = copyPortionXYWH(0, 0, widthInPixels, heightInPixels)

    val (reds, greens, blues, opacities) = newBuffer.colorComponentArrays()

    var index: Int = 0
    val resultRgbaTuplesTry = Try{
      while (index < reds.length) {
        val (newRed, newGreen, newBlue, newOpacity) = function(
          reds(index),
          greens(index),
          blues(index),
          opacities(index))

        ColorValidator.validateRgbaColor(newRed, newGreen, newBlue, newOpacity)

        reds(index) = newRed
        greens(index) = newGreen
        blues(index) = newBlue
        opacities(index) = newOpacity

        index += 1
      }
    }

    if (resultRgbaTuplesTry.isFailure) {
      throw new SMCLFunctionExecutionError(
        "The given pixel iteration function did not get executed correctly (see the chained exceptions)",
        resultRgbaTuplesTry.failed.get
      )
    }

    newBuffer.setColorComponentArrays(reds, greens, blues, opacities)
    newBuffer
  }

  /**
   *
   *
   * @return
   */
  override def colorComponentArrays(): (Array[Int], Array[Int], Array[Int], Array[Int]) = {
    import aalto.smcl.colors.RGBASampleBand._

    val getSamples =
      awtBufferedImage.getRaster.getSamples(
        0, 0, widthInPixels, heightInPixels, _: Int,
        null.asInstanceOf[Array[Int]])

    (getSamples(Red.id), getSamples(Green.id), getSamples(Blue.id), getSamples(Opacity.id))
  }

  /**
   *
   *
   * @return
   */
  override def setColorComponentArrays(
      reds: Array[Int],
      greens: Array[Int],
      blues: Array[Int],
      opacities: Array[Int]): Unit = {

    if (reds.length != areaInPixels)
      throw new SMCLInvalidColorComponentArrayLengthError(
        "Expected length for the given red RGBA component array is " +
            s"$areaInPixels, but actually was ${reds.length}")

    if (greens.length != areaInPixels)
      throw new SMCLInvalidColorComponentArrayLengthError(
        "Expected length for the given green RGBA component array is " +
            s"$areaInPixels, but actually was ${greens.length}")

    if (blues.length != areaInPixels)
      throw new SMCLInvalidColorComponentArrayLengthError(
        "Expected length for the given blue RGBA component array is " +
            s"$areaInPixels, but actually was ${blues.length}")

    if (opacities.length != areaInPixels)
      throw new SMCLInvalidColorComponentArrayLengthError(
        "Expected length for the given opacity RGBA component array is " +
            s"$areaInPixels, but actually was ${opacities.length}")


    import aalto.smcl.colors.RGBASampleBand._

    val raster = awtBufferedImage.getRaster
    val setSamples = raster.setSamples(0, 0, widthInPixels, heightInPixels, _: Int, _: Array[Int])

    setSamples(Red.id, reds)
    setSamples(Green.id, greens)
    setSamples(Blue.id, blues)
    setSamples(Opacity.id, opacities)

    awtBufferedImage.setData(raster)
  }

  /**
   *
   *
   * @param transformation
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @return
   */
  override def createTransformedVersionWith(
      transformation: AffineTransformation,
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground)): AwtBitmapBufferAdapter = {

    val globalInterpolationMethod =
      GS.enumSettingFor[AwtAffineTransformationInterpolationMethod.Value](AwtAffTransfInterpMethod).value.id

    var resultingImageWidth: Int = awtBufferedImage.getWidth
    var resultingImageHeight: Int = awtBufferedImage.getHeight
    val lowLevelTransformation =
      transformation.platformAffineTransform.asInstanceOf[AwtAffineTransformationAdapter].awtAffineTransformation
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
    val resultingBuffer: AwtBitmapBufferAdapter = AwtBitmapBufferAdapter(resultingImageWidth, resultingImageHeight)

    resultingBuffer.drawingSurface clearUsing backgroundColor

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
  override def boundaryOverflowsForLTRB(
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
  override def createFilteredVersionWith(kernel: ConvolutionKernel): AwtBitmapBufferAdapter = {
    val lowLevelKernel = new Kernel(kernel.width, kernel.height, kernel.toRowMajorArray)
    val operation = new ConvolveOp(lowLevelKernel)

    val newLowLevelBitmap = operation.filter(
      awtBufferedImage,
      emptyAlike.awtBufferedImage)

    AwtBitmapBufferAdapter(newLowLevelBitmap)
  }

  /**
   *
   *
   * @param translator
   * @return
   */
  override def createFilteredVersionWith(translator: RGBAComponentTranslationTable): AwtBitmapBufferAdapter = {
    val lowLevelLookupTable = new ShortLookupTable(0, translator.toArray)
    val operation = new LookupOp(lowLevelLookupTable, null)

    val newLowLevelBitmap = operation.filter(
      awtBufferedImage,
      emptyAlike.awtBufferedImage)

    AwtBitmapBufferAdapter(newLowLevelBitmap)
  }

  /**
   *
   *
   * @return
   */
  override def copy: AwtBitmapBufferAdapter =
    AwtBitmapBufferAdapter(BitmapUtils.deepCopy(awtBufferedImage))

  /**
   *
   *
   * @param topLeftX
   * @param topLeftY
   * @param bottomRightX
   * @param bottomRightY
   * @return
   */
  override def copyPortionXYXY(
      topLeftX: Int,
      topLeftY: Int,
      bottomRightX: Int,
      bottomRightY: Int): AwtBitmapBufferAdapter = {

    val (x0, x1) =
      if (topLeftX > bottomRightX)
        (bottomRightX, topLeftX)
      else
        (topLeftX, bottomRightX)

    val (y0, y1) =
      if (topLeftY > bottomRightY)
        (bottomRightY, topLeftY)
      else
        (topLeftY, bottomRightY)

    val width = x1 - x0 + 1
    val height = y1 - y0 + 1

    copyPortionXYWH(topLeftX, topLeftY, width, height)
  }

  /**
   *
   *
   * @param topLeftX
   * @param topLeftY
   * @param width
   * @param height
   * @return
   */
  override def copyPortionXYWH(
      topLeftX: Int,
      topLeftY: Int,
      width: Int,
      height: Int): AwtBitmapBufferAdapter = {

    val sourceBufferArea =
      awtBufferedImage.getSubimage(topLeftX, topLeftY, width, height)

    val newBuffer = AwtBitmapBufferAdapter.createNormalizedLowLevelBitmapBufferOf(width, height)

    var drawingSurface: Graphics2D = null
    try {
      drawingSurface = newBuffer.createGraphics()
      drawingSurface.drawImage(sourceBufferArea, null, 0, 0)
    }
    finally {
      drawingSurface.dispose()
    }

    AwtBitmapBufferAdapter(newBuffer)
  }

  /**
   *
   *
   * @return
   */
  override def emptyAlike: AwtBitmapBufferAdapter =
    AwtBitmapBufferAdapter(widthInPixels, heightInPixels)

  /**
   *
   *
   * @param filename
   * @return
   */
  override def saveAsPngTo(filename: String): String = {
    val destFile = new File(filename)

    if (destFile.exists())
      return "Error: The given file exists."

    val savingResult = Try(ImageIO.write(awtBufferedImage, "png", destFile))

    savingResult match {
      case Failure(t: Throwable) => s"Error: ${t.getMessage}"
      case _ => "Save successful."
    }
  }

}
