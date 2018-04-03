/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package smcl.infrastructure.jvmawt


import java.awt.geom.{AffineTransform, Rectangle2D}
import java.awt.image._
import java.awt.{AlphaComposite, Graphics2D}
import java.io.File

import scala.util.{Failure, Try}

import javax.imageio.ImageIO

import smcl.colors.ColorValidator
import smcl.colors.rgb._
import smcl.infrastructure._
import smcl.infrastructure.exceptions.{FunctionExecutionError, InvalidColorComponentArrayLengthError}
import smcl.modeling.d2.Dims
import smcl.modeling.{AffineTransformation, Len}
import smcl.pictures.{BitmapValidator, _}
import smcl.settings.jvmawt.AffineTransformationInterpolationMethod
import smcl.settings.{CanvasesAreResizedBasedOnTransformations, DefaultBackgroundColor}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object AWTBitmapBufferAdapter
    extends InjectablesRegistry {

  /** A constant for buffer of a 'normalized' type. */
  val NormalizedBufferType: Int = BufferedImage.TYPE_INT_ARGB

  /** The ColorValidator instance to be used by this object. */
  private
  lazy val colorValidator: ColorValidator = {
    injectable(InjectablesRegistry.IIdColorValidator).asInstanceOf[ColorValidator]
  }

  /** The BitmapValidator instance to be used by this object. */
  private
  lazy val bitmapValidator: BitmapValidator = {
    injectable(InjectablesRegistry.IIdBitmapValidator).asInstanceOf[BitmapValidator]
  }

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  def apply(
      widthInPixels: Len,
      heightInPixels: Len): AWTBitmapBufferAdapter = {

    bitmapValidator.validateBitmapSize(widthInPixels, heightInPixels)

    val newBuffer = createNormalizedLowLevelBitmapBufferOf(widthInPixels, heightInPixels, None)

    new AWTBitmapBufferAdapter(newBuffer, colorValidator)
  }

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  def apply(
      widthInPixels: Int,
      heightInPixels: Int): AWTBitmapBufferAdapter = {

    val width = Len(widthInPixels)
    val height = Len(heightInPixels)

    bitmapValidator.validateBitmapSize(width, height)

    val newBuffer = createNormalizedLowLevelBitmapBufferOf(width, height, None)

    new AWTBitmapBufferAdapter(newBuffer, colorValidator)
  }

  /**
   *
   *
   * @param awtBufferedImage
   *
   * @return
   */
  def apply(awtBufferedImage: BufferedImage): AWTBitmapBufferAdapter = {
    require(awtBufferedImage != null, "Provided image buffer cannot be null.")

    bitmapValidator.validateBitmapSize(Len(awtBufferedImage.getWidth), Len(awtBufferedImage.getHeight))

    val normalizedAWTBuffer = convertToNormalizedLowLevelBitmapBufferIfNecessary(awtBufferedImage)

    new AWTBitmapBufferAdapter(normalizedAWTBuffer, colorValidator)
  }

  /**
   *
   *
   * @param width
   * @param height
   *
   * @return
   */
  private[jvmawt]
  def createNormalizedLowLevelBitmapBufferOf(
      width: Len,
      height: Len,
      initializer: Option[Graphics2D => Unit]): BufferedImage = {

    val newBuffer = new BufferedImage(
      width.inPixels.floor.toInt,
      height.inPixels.floor.toInt, NormalizedBufferType)

    performWithGraphics2DOf(newBuffer){g =>
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC))
      g.setColor(DefaultBackgroundColor.toAWTColor)
      g.fillRect(
        0, 0,
        width.inPixels.floor.toInt,
        height.inPixels.floor.toInt)

      initializer.foreach(i => i(g))
    }

    newBuffer
  }

  /**
   *
   *
   * @param buffer
   *
   * @return
   */
  private[jvmawt]
  def convertToNormalizedLowLevelBitmapBufferIfNecessary(
      buffer: BufferedImage): BufferedImage = {

    var bufferCandidate = buffer

    if (bufferCandidate.getType != NormalizedBufferType) {
      val initializer = (g: Graphics2D) => g.drawImage(bufferCandidate, null, 0, 0)

      val newBuffer = createNormalizedLowLevelBitmapBufferOf(
        Len(bufferCandidate.getWidth), Len(bufferCandidate.getHeight), Some(initializer))

      bufferCandidate = newBuffer
    }

    bufferCandidate
  }

  /**
   *
   *
   * @param bitmap
   * @param workUnit
   * @tparam ResultType
   *
   * @return
   */
  private[jvmawt]
  def performWithGraphics2DOf[ResultType](
      bitmap: BufferedImage)(
      workUnit: Graphics2D => ResultType): ResultType = {

    var gr2D: Graphics2D = null
    var memorizedThrowable: Throwable = null

    try {
      gr2D = bitmap.createGraphics()
      workUnit(gr2D)
    }
    catch {
      case caughtThrowable: Throwable =>
        memorizedThrowable = caughtThrowable
        throw caughtThrowable
    }
    finally {
      if (gr2D != null) {
        if (memorizedThrowable != null) {
          try {
            gr2D.dispose()
          }
          catch {
            case caughtThrowable: Throwable =>
              memorizedThrowable.addSuppressed(caughtThrowable)
          }
        }
        else {
          gr2D.dispose()
        }
      }
    }
  }

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class AWTBitmapBufferAdapter private(
    val awtBufferedImage: BufferedImage,
    private val colorValidator: ColorValidator) extends BitmapBufferAdapter {

  /**
   *
   *
   * @return
   */
  override
  def widthInPixels: Int = awtBufferedImage.getWidth

  /**
   *
   *
   * @return
   */
  override
  def heightInPixels: Int = awtBufferedImage.getHeight

  /**
   *
   *
   * @return
   */
  override
  def areaInPixels: Int = heightInPixels * widthInPixels

  /**
   *
   *
   * @return
   */
  override
  def drawingSurface: AWTDrawingSurfaceAdapter = AWTDrawingSurfaceAdapter(this)

  /**
   *
   *
   * @param workUnit
   * @tparam ResultType
   *
   * @return
   */
  private[jvmawt]
  def withGraphics2D[ResultType](workUnit: Graphics2D => ResultType): ResultType =
    AWTBitmapBufferAdapter.performWithGraphics2DOf(awtBufferedImage)(workUnit)

  /**
   *
   *
   * @param xInPixels
   * @param yInPixels
   *
   * @return
   */
  override
  def colorAt(
      xInPixels: Double,
      yInPixels: Double): Color = {

    val argbInt = awtBufferedImage.getRGB(
      xInPixels.floor.toInt,
      yInPixels.floor.toInt)

    Color(argbInt)
  }

  /**
   *
   *
   * @param colorToTrim
   *
   * @return
   */
  override
  def trim(colorToTrim: Color = DefaultBackgroundColor): AWTBitmapBufferAdapter = {
    val (reds, greens, blues, opacities) = colorComponentArrays

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
      return AWTBitmapBufferAdapter(1, 1)
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
   *
   * @return
   */
  override
  def iteratePixelsWith(
      function: (Int, Int, Int, Int) => (Int, Int, Int, Int)): AWTBitmapBufferAdapter = {

    val newBuffer = copyPortionXYWH(0, 0, widthInPixels, heightInPixels)

    val (reds, greens, blues, opacities) = newBuffer.colorComponentArrays

    var index: Int = 0
    val resultRGBATuplesTry = Try{
      while (index < reds.length) {
        val (newRed, newGreen, newBlue, newOpacity) = function(
          reds(index),
          greens(index),
          blues(index),
          opacities(index))

        colorValidator.validateRGBAColor(newRed, newGreen, newBlue, newOpacity)

        reds(index) = newRed
        greens(index) = newGreen
        blues(index) = newBlue
        opacities(index) = newOpacity

        index += 1
      }
    }

    if (resultRGBATuplesTry.isFailure) {
      throw FunctionExecutionError(
        "The given pixel iteration function did not get executed correctly (see the chained exceptions)",
        resultRGBATuplesTry.failed.get
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
  override
  def colorComponentArrays: (Array[Int], Array[Int], Array[Int], Array[Int]) = {

    val getSamples =
      awtBufferedImage.getRaster.getSamples(
        0, 0, widthInPixels, heightInPixels, _: Int,
        null.asInstanceOf[Array[Int]])

    (getSamples(SampleBands.Red.ordinal),
        getSamples(SampleBands.Green.ordinal),
        getSamples(SampleBands.Blue.ordinal),
        getSamples(SampleBands.Opacity.ordinal))
  }

  /**
   *
   *
   * @return
   */
  override
  def setColorComponentArrays(
      reds: Array[Int],
      greens: Array[Int],
      blues: Array[Int],
      opacities: Array[Int]): Unit = {

    if (reds.length != areaInPixels)
      throw InvalidColorComponentArrayLengthError(
        "Expected length for the given red RGBA component array is " +
            s"$areaInPixels, but actually was ${reds.length}")

    if (greens.length != areaInPixels)
      throw InvalidColorComponentArrayLengthError(
        "Expected length for the given green RGBA component array is " +
            s"$areaInPixels, but actually was ${greens.length}")

    if (blues.length != areaInPixels)
      throw InvalidColorComponentArrayLengthError(
        "Expected length for the given blue RGBA component array is " +
            s"$areaInPixels, but actually was ${blues.length}")

    if (opacities.length != areaInPixels)
      throw InvalidColorComponentArrayLengthError(
        "Expected length for the given opacity RGBA component array is " +
            s"$areaInPixels, but actually was ${opacities.length}")

    val raster = awtBufferedImage.getRaster
    val setSamples = raster.setSamples(0, 0, widthInPixels, heightInPixels, _: Int, _: Array[Int])

    setSamples(SampleBands.Red.ordinal, reds)
    setSamples(SampleBands.Green.ordinal, greens)
    setSamples(SampleBands.Blue.ordinal, blues)
    setSamples(SampleBands.Opacity.ordinal, opacities)

    awtBufferedImage.setData(raster)
  }

  /**
   *
   *
   * @param transformation
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  override
  def createTransformedVersionWith(
      transformation: AffineTransformation,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): (AWTBitmapBufferAdapter, Dims) = {

    val globalInterpolationMethod = AffineTransformationInterpolationMethod.lowLevelValue

    val lowLevelTransformation = transformation.toAWTAffineTransform
    val transformedContentBoundaries: Rectangle2D =
      new AffineTransformOp(lowLevelTransformation, globalInterpolationMethod)
          .getBounds2D(awtBufferedImage)

    val (offsetLeft, offsetTop, offsetRight, offsetBottom) =
      if (!resizeCanvasBasedOnTransformation)
        (0.0, 0.0, 0.0, 0.0)
      else
        (-transformedContentBoundaries.getMinX,
            -transformedContentBoundaries.getMinY,
            transformedContentBoundaries.getMaxX - awtBufferedImage.getWidth,
            transformedContentBoundaries.getMaxY - awtBufferedImage.getHeight)

    val (resultingImageWidth, resultingImageHeight) =
      if (!resizeCanvasBasedOnTransformation)
        (awtBufferedImage.getWidth, awtBufferedImage.getHeight)
      else
        (Math.floor(awtBufferedImage.getWidth.toDouble + offsetLeft + offsetRight).toInt,
            Math.floor(awtBufferedImage.getHeight.toDouble + offsetTop + offsetBottom).toInt)

    if (resizeCanvasBasedOnTransformation) {
      if (offsetTop > 0 || offsetLeft > 0) {
        val translationToBringTheRotatedBitmapFullyVisible =
          AffineTransform.getTranslateInstance(offsetLeft, offsetTop)

        lowLevelTransformation preConcatenate translationToBringTheRotatedBitmapFullyVisible
      }
    }

    val finalTransformOperation = new AffineTransformOp(lowLevelTransformation, globalInterpolationMethod)
    val resultingBuffer: AWTBitmapBufferAdapter = AWTBitmapBufferAdapter(resultingImageWidth, resultingImageHeight)

    resultingBuffer.drawingSurface clearUsing backgroundColor

    finalTransformOperation.filter(awtBufferedImage, resultingBuffer.awtBufferedImage)

    (resultingBuffer, Dims(offsetLeft, offsetTop))
  }

  /**
   *
   *
   * @param minXInPixels
   * @param minYInPixels
   * @param maxXInPixels
   * @param maxYInPixels
   *
   * @return
   */
  override
  def boundaryOverflowsForLTRB(
      minXInPixels: Double,
      minYInPixels: Double,
      maxXInPixels: Double,
      maxYInPixels: Double): (Double, Double, Double, Double) = {

    val overflowLeft = if (minXInPixels < 0) -minXInPixels else 0

    val overflowTop = if (minYInPixels < 0) -minYInPixels else 0

    val overflowRight =
      if (maxXInPixels > awtBufferedImage.getWidth)
        maxXInPixels - awtBufferedImage.getWidth
      else 0

    val overflowBottom =
      if (maxYInPixels > awtBufferedImage.getHeight)
        maxYInPixels - awtBufferedImage.getHeight
      else 0

    (overflowLeft, overflowTop, overflowRight, overflowBottom)
  }

  /**
   *
   *
   * @param kernel
   *
   * @return
   */
  override
  def createFilteredVersionWith(
      kernel: ConvolutionKernel): AWTBitmapBufferAdapter = {

    val lowLevelKernel = new Kernel(kernel.width, kernel.height, kernel.toRowMajorArray)
    val operation = new ConvolveOp(lowLevelKernel)

    val newLowLevelBitmap = operation.filter(
      awtBufferedImage,
      emptyAlike.awtBufferedImage)

    AWTBitmapBufferAdapter(newLowLevelBitmap)
  }

  /**
   *
   *
   * @param translator
   *
   * @return
   */
  override
  def createFilteredVersionWith(
      translator: ColorComponentTranslationTable): AWTBitmapBufferAdapter = {

    val lowLevelLookupTable = new ShortLookupTable(0, translator.toArray)
    val operation = new LookupOp(lowLevelLookupTable, null)

    val newLowLevelBitmap = operation.filter(
      awtBufferedImage,
      emptyAlike.awtBufferedImage)

    AWTBitmapBufferAdapter(newLowLevelBitmap)
  }

  /**
   *
   *
   * @return
   */
  override
  def copy: AWTBitmapBufferAdapter =
    AWTBitmapBufferAdapter(BitmapUtils.deepCopy(awtBufferedImage))

  /**
   *
   *
   * @param topLeftXInPixels
   * @param topLeftYInPixels
   * @param bottomRightXInPixels
   * @param bottomRightYInPixels
   *
   * @return
   */
  override
  def copyPortionXYXY(
      topLeftXInPixels: Double,
      topLeftYInPixels: Double,
      bottomRightXInPixels: Double,
      bottomRightYInPixels: Double): AWTBitmapBufferAdapter = {

    val (x0, x1) =
      if (topLeftXInPixels > bottomRightXInPixels)
        (bottomRightXInPixels, topLeftXInPixels)
      else
        (topLeftXInPixels, bottomRightXInPixels)

    val (y0, y1) =
      if (topLeftYInPixels > bottomRightYInPixels)
        (bottomRightYInPixels, topLeftYInPixels)
      else
        (topLeftYInPixels, bottomRightYInPixels)

    val width = x1 - x0 + 1
    val height = y1 - y0 + 1

    copyPortionXYWH(topLeftXInPixels, topLeftYInPixels, width, height)
  }

  /**
   *
   *
   * @param topLeftXInPixels
   * @param topLeftYInPixels
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  override
  def copyPortionXYWH(
      topLeftXInPixels: Double,
      topLeftYInPixels: Double,
      widthInPixels: Double,
      heightInPixels: Double): AWTBitmapBufferAdapter = {

    val flooredWidth: Int = widthInPixels.floor.toInt
    val flooredHeight: Int = heightInPixels.floor.toInt

    val sourceBufferArea =
      awtBufferedImage.getSubimage(
        topLeftXInPixels.floor.toInt,
        topLeftYInPixels.floor.toInt,
        flooredWidth,
        flooredHeight)

    val initializer = (g: Graphics2D) => g.drawImage(sourceBufferArea, null, 0, 0)

    val newBuffer = AWTBitmapBufferAdapter.createNormalizedLowLevelBitmapBufferOf(
      Len(flooredWidth), Len(flooredHeight),
      Some(initializer))

    AWTBitmapBufferAdapter(newBuffer)
  }

  /**
   *
   *
   * @return
   */
  override
  def emptyAlike: AWTBitmapBufferAdapter =
    AWTBitmapBufferAdapter(widthInPixels, heightInPixels)

  /**
   *
   *
   * @param filename
   *
   * @return
   */
  override
  def saveAsPngTo(filename: String): String = {
    val destFile = new File(filename)

    // TODO: Refactor to throw SMCL exceptions

    if (destFile.exists())
      return "Error: The given file exists."

    val savingResult = Try(ImageIO.write(awtBufferedImage, "png", destFile))

    savingResult match {
      case Failure(t: Throwable) => s"Error: ${t.getMessage}"
      case _                     => "Save successful."
    }
  }

}
