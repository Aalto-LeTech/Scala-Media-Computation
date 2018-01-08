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

package smcl.pictures.simplified


import smcl.colors.ColorValidator
import smcl.colors.rgb.{Color, ColorComponentTranslationTable}
import smcl.pictures.ConvolutionKernel
import smcl.settings._




/**
 * Provides a way to modify [[Bitmap]]
 * instances using "stand-alone" functions instead of OOP-based `object.operation`
 * approach. The functions are made available by the package object.
 *
 * @author Aleksi Lukkarinen
 */
private[pictures]
trait BitmapOperationAPI {

  /**
   *
   *
   * @param bmp
   * @param kernel
   *
   * @return
   */
  def convolveWith(
      bmp: Bitmap,
      kernel: ConvolutionKernel): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.convolveWith(kernel)
  }

  /**
   *
   *
   * @param bmp
   * @param translator
   *
   * @return
   */
  def filterWith(
      bmp: Bitmap,
      translator: ColorComponentTranslationTable): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.filterWith(translator)
  }

  /**
   *
   *
   * @param bmp
   * @param function
   *
   * @return
   */
  def iteratePixelsWith(
      bmp: Bitmap,
      function: (Int, Int, Int, Int) => (Int, Int, Int, Int)): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.iteratePixelsWith(function)
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def toGrayscaleByLuminocity(
      bmp: Bitmap): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.toGrayscaleByLuminocity
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def toGrayscaleByLightness(
      bmp: Bitmap): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.toGrayscaleByLightness
  }

  /**
   *
   *
   * @param bmp
   * @param redWeight
   * @param greenWeight
   * @param blueWeight
   *
   * @return
   */
  def toGrayscale(
      bmp: Bitmap,
      redWeight: Double,
      greenWeight: Double,
      blueWeight: Double): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.toGrayscale(redWeight, greenWeight, blueWeight)
  }

  /**
   *
   *
   * @param bmp
   * @param colorToTrim
   *
   * @return
   */
  def trim(
      bmp: Bitmap,
      colorToTrim: Color = DefaultBackgroundColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.trim(colorToTrim)
  }

  /**
   *
   *
   * @param bmp
   * @param windowTopLeftX
   * @param windowTopLeftY
   * @param windowBottomRightX
   * @param windowBottomRightY
   *
   * @return
   */
  def crop(
      bmp: Bitmap,
      windowTopLeftX: Int,
      windowTopLeftY: Int,
      windowBottomRightX: Int,
      windowBottomRightY: Int): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.crop(
      windowTopLeftX,
      windowTopLeftY,
      windowBottomRightX,
      windowBottomRightY)
  }

  /**
   *
   *
   * @param bmp
   * @param extraPixelsOntoLeftEdge
   * @param extraPixelsOntoTopEdge
   * @param extraPixelsOntoRightEdge
   * @param extraPixelsOntoBottomEdge
   * @param color
   *
   * @return
   */
  def augmentCanvas(
      bmp: Bitmap,
      extraPixelsOntoLeftEdge: Int = 0,
      extraPixelsOntoTopEdge: Int = 0,
      extraPixelsOntoRightEdge: Int = 0,
      extraPixelsOntoBottomEdge: Int = 0,
      color: Color = DefaultBackgroundColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.augmentCanvas(
      extraPixelsOntoLeftEdge,
      extraPixelsOntoTopEdge,
      extraPixelsOntoRightEdge,
      extraPixelsOntoBottomEdge,
      color)
  }

  /**
   *
   *
   * @param bottomBitmap
   * @param topBitmap
   * @param topBitmapUpperLeftX
   * @param topBitmapUpperLeftY
   * @param topBitmapOpacity
   * @param backgroundColor
   *
   * @return
   */
  def overlay(
      bottomBitmap: Bitmap,
      topBitmap: Bitmap,
      topBitmapUpperLeftX: Int,
      topBitmapUpperLeftY: Int,
      topBitmapOpacity: Int = ColorValidator.MaximumOpacity,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    bottomBitmap.underlayBehind(
      topBitmap,
      topBitmapUpperLeftX,
      topBitmapUpperLeftY,
      topBitmapOpacity,
      backgroundColor)
  }

  /**
   *
   *
   * @param bitmapsToOverlayFromBottomToTop
   * @param horizontalAlignment
   * @param verticalAlignment
   * @param opacityForAllBitmaps
   * @param backgroundColor
   *
   * @return
   */
  def overlayPerAlignments(
      bitmapsToOverlayFromBottomToTop: Bitmap*)(
      horizontalAlignment: HorizontalAlignment = DefaultHorizontalAlignment,
      verticalAlignment: VerticalAlignment = DefaultVerticalAlignment,
      opacityForAllBitmaps: Int = ColorValidator.MaximumOpacity,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    bitmapsToOverlayFromBottomToTop.head.overlayPerAlignments(bitmapsToOverlayFromBottomToTop.tail: _*)(
      horizontalAlignment,
      verticalAlignment,
      opacityForAllBitmaps,
      backgroundColor)
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def toNegative(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.negate
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def negateRedComponent(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.negateRedComponent
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def negateGreenComponent(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.negateGreenComponent
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def negateBlueComponent(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.negateBlueComponent
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def negateRedAndGreenComponents(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.negateRedAndGreenComponents
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def negateRedAndBlueComponents(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.negateRedAndBlueComponents
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def negateGreenAndBlueComponents(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.negateGreenAndBlueComponents
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def keepOnlyRedComponent(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.keepOnlyRedComponent
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def keepOnlyGreenComponent(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.keepOnlyGreenComponent
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def keepOnlyBlueComponent(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.keepOnlyBlueComponent
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def keepOnlyRedAndGreenComponents(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.keepOnlyRedAndGreenComponents
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def keepOnlyRedAndBlueComponents(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.keepOnlyRedAndBlueComponents
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def keepOnlyGreenAndBlueComponents(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.keepOnlyGreenAndBlueComponents
  }

  /**
   *
   *
   * @param bmp
   * @param strengthAsPercentage
   */
  def posterize(
      bmp: Bitmap,
      strengthAsPercentage: Int): Bitmap = {

    require(bmp != null, "The bitmap to be cleared cannot be null.")

    bmp.posterize(strengthAsPercentage)
  }

  /**
   *
   *
   * @param bmp
   * @param color
   *
   * @return
   */
  def clear(
      bmp: Bitmap,
      color: Color = DefaultBackgroundColor): Bitmap = {

    require(bmp != null, "The bitmap to be cleared cannot be null.")

    bmp.clear(color)
  }

  /**
   *
   *
   * @param bmp
   * @param fromXInPixels
   * @param fromYInPixels
   * @param toXInPixels
   * @param toYInPixels
   * @param color
   *
   * @return
   */
  def drawLine(
      bmp: Bitmap,
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: Color = DefaultPrimaryColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.drawLine(
      fromXInPixels,
      fromYInPixels,
      toXInPixels,
      toYInPixels,
      color)
  }

  /**
   *
   *
   * @param bmp
   * @param xCoordinates
   * @param yCoordinates
   * @param numberOfCoordinatesToDraw
   * @param color
   *
   * @return
   */
  def drawPolyline(
      bmp: Bitmap,
      xCoordinates: Seq[Int],
      yCoordinates: Seq[Int],
      numberOfCoordinatesToDraw: Int,
      color: Color = DefaultPrimaryColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.drawPolyline(
      xCoordinates,
      yCoordinates,
      numberOfCoordinatesToDraw,
      color)
  }

  /**
   *
   *
   * @param bmp
   * @param xCoordinates
   * @param yCoordinates
   * @param numberOfCoordinatesToDraw
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def drawPolygon(
      bmp: Bitmap,
      xCoordinates: Seq[Int],
      yCoordinates: Seq[Int],
      numberOfCoordinatesToDraw: Int,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.drawPolygon(
      xCoordinates,
      yCoordinates,
      numberOfCoordinatesToDraw,
      hasBorder,
      hasFilling,
      color,
      fillColor)
  }

  /**
   *
   *
   * @param bmp
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param sideLengthInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def drawSquare(
      bmp: Bitmap,
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.drawSquare(
      upperLeftCornerXInPixels,
      upperLeftCornerYInPixels,
      sideLengthInPixels,
      hasBorder,
      hasFilling,
      color,
      fillColor)
  }

  /**
   *
   *
   * @param bmp
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param widthInPixels
   * @param heightInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def drawRectangle(
      bmp: Bitmap,
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.drawRectangle(
      upperLeftCornerXInPixels,
      upperLeftCornerYInPixels,
      widthInPixels,
      heightInPixels,
      hasBorder,
      hasFilling,
      color,
      fillColor)
  }

  /**
   *
   *
   * @param bmp
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param sideLengthInPixels
   * @param roundingWidthInPixels
   * @param roundingHeightInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def drawRoundedSquare(
      bmp: Bitmap,
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.drawRoundedSquare(
      upperLeftCornerXInPixels,
      upperLeftCornerYInPixels,
      sideLengthInPixels,
      roundingWidthInPixels,
      roundingHeightInPixels,
      hasBorder,
      hasFilling,
      color,
      fillColor)
  }

  /**
   *
   *
   * @param bmp
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param widthInPixels
   * @param heightInPixels
   * @param roundingWidthInPixels
   * @param roundingHeightInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def drawRoundedRectangle(
      bmp: Bitmap,
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.drawRoundedRectangle(
      upperLeftCornerXInPixels,
      upperLeftCornerYInPixels,
      widthInPixels,
      heightInPixels,
      roundingWidthInPixels,
      roundingHeightInPixels,
      hasBorder,
      hasFilling,
      color,
      fillColor)
  }

  /**
   *
   *
   * @param bmp
   * @param centerXInPixels
   * @param centerYInPixels
   * @param radiusInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def drawCircle(
      bmp: Bitmap,
      centerXInPixels: Int,
      centerYInPixels: Int,
      radiusInPixels: Int = DefaultCircleRadiusInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.drawCircle(
      centerXInPixels,
      centerYInPixels,
      radiusInPixels,
      hasBorder,
      hasFilling,
      color,
      fillColor)
  }

  /**
   *
   *
   * @param bmp
   * @param centerXInPixels
   * @param centerYInPixels
   * @param widthInPixels
   * @param heightInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def drawEllipse(
      bmp: Bitmap,
      centerXInPixels: Int,
      centerYInPixels: Int,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.drawEllipse(
      centerXInPixels,
      centerYInPixels,
      widthInPixels,
      heightInPixels,
      hasBorder,
      hasFilling,
      color,
      fillColor)
  }

  /**
   *
   *
   * @param bmp
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param widthInPixels
   * @param heightInPixels
   * @param startAngleInDegrees
   * @param arcAngleInDegrees
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def drawArc(
      bmp: Bitmap,
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      startAngleInDegrees: Int = DefaultArcStartAngleInDegrees,
      arcAngleInDegrees: Int = DefaultArcAngleInDegrees,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.drawArc(
      upperLeftCornerXInPixels,
      upperLeftCornerYInPixels,
      widthInPixels,
      heightInPixels,
      startAngleInDegrees,
      arcAngleInDegrees,
      hasBorder,
      hasFilling,
      color,
      fillColor)
  }

  /**
   *
   *
   * @param bitmapsToCombine
   * @param verticalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def appendHorizontally(
      bitmapsToCombine: Bitmap*)(
      verticalAlignment: VerticalAlignment = DefaultVerticalAlignment,
      paddingInPixels: Int = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    require(bitmapsToCombine.length > 1, s"There must be at least two bitmaps to combine (was $bitmapsToCombine)")

    bitmapsToCombine.head.appendOnRight(bitmapsToCombine.tail: _*)(
      verticalAlignment,
      paddingInPixels,
      backgroundColor)
  }

  /**
   *
   *
   * @param bitmapLeft
   * @param bitmapRight
   *
   * @return
   */
  def sew(bitmapLeft: Bitmap, bitmapRight: Bitmap): Bitmap = {
    appendHorizontally(bitmapLeft, bitmapRight)(VAMiddle)
  }

  /**
   *
   *
   * @param bitmapsToCombine
   * @param horizontalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def appendVertically(
      bitmapsToCombine: Bitmap*)(
      horizontalAlignment: HorizontalAlignment = DefaultHorizontalAlignment,
      paddingInPixels: Int = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    require(bitmapsToCombine.length > 1, s"There must be at least two bitmaps to combine (was $bitmapsToCombine)")

    bitmapsToCombine.head.appendOnBottom(bitmapsToCombine.tail: _*)(
      horizontalAlignment,
      paddingInPixels,
      backgroundColor)
  }

  /**
   *
   *
   * @param bitmapTop
   * @param bitmapBottom
   *
   * @return
   */
  def pile(bitmapTop: Bitmap, bitmapBottom: Bitmap): Bitmap = {
    appendVertically(bitmapTop, bitmapBottom)(HACenter)
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def flipHorizontally(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.flipHorizontally
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def flipVertically(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.flipVertically
  }

  /**
   *
   *
   * @param bmp
   *
   * @return
   */
  def flipDiagonally(bmp: Bitmap): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.flipDiagonally
  }

  /**
   *
   *
   * @param bmp
   * @param angleInDegrees
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def rotateDegs(
      bmp: Bitmap,
      angleInDegrees: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.rotateDegs(
      angleInDegrees,
      resizeCanvasBasedOnTransformation,
      backgroundColor)
  }

  /**
   *
   *
   * @param bmp
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def rotate90DegsCW(
      bmp: Bitmap,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.rotate90DegsCW(
      resizeCanvasBasedOnTransformation,
      backgroundColor)
  }

  /**
   *
   *
   * @param bmp
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def rotate90DegsCCW(
      bmp: Bitmap,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.rotate90DegsCCW(
      resizeCanvasBasedOnTransformation,
      backgroundColor)
  }

  /**
   *
   *
   * @param bmp
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def rotate180Degs(
      bmp: Bitmap,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.rotate180Degs(
      resizeCanvasBasedOnTransformation,
      backgroundColor)
  }

  /**
   *
   *
   * @param bmp
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def turn(
      bmp: Bitmap,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.rotate90DegsCW(
      resizeCanvasBasedOnTransformation,
      backgroundColor)
  }

  /**
   *
   *
   * @param bmp
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def unturn(
      bmp: Bitmap,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.rotate90DegsCCW(
      resizeCanvasBasedOnTransformation,
      backgroundColor)
  }

  /**
   *
   *
   * @param bmp
   * @param scalingFactorHorizontal
   * @param scalingFactorVertical
   * @param resizeCanvasBasedOnTransformation
   *
   * @return
   */
  def scale(
      bmp: Bitmap,
      scalingFactorHorizontal: Double = 1.0,
      scalingFactorVertical: Double = 1.0,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.scale(
      scalingFactorHorizontal,
      scalingFactorVertical,
      resizeCanvasBasedOnTransformation)
  }

  /**
   *
   *
   * @param bmp
   * @param scalingFactor
   *
   * @return
   */
  def scale(bmp: Bitmap, scalingFactor: Double): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.scale(scalingFactor)
  }

  /**
   *
   *
   * @param bmp
   * @param scalingFactor
   * @param resizeCanvasBasedOnTransformation
   *
   * @return
   */
  def scaleHorizontally(
      bmp: Bitmap,
      scalingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations): Bitmap = {

    bmp.scaleHorizontally(
      scalingFactor,
      resizeCanvasBasedOnTransformation)
  }

  /**
   *
   *
   * @param bmp
   * @param scalingFactor
   * @param resizeCanvasBasedOnTransformation
   *
   * @return
   */
  def scaleVertically(
      bmp: Bitmap,
      scalingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations): Bitmap = {

    bmp.scaleVertically(
      scalingFactor,
      resizeCanvasBasedOnTransformation)
  }

  /**
   *
   *
   * @param bmp
   * @param shearingFactorHorizontal
   * @param shearingFactorVertical
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def shear(
      bmp: Bitmap,
      shearingFactorHorizontal: Double = 0.0,
      shearingFactorVertical: Double = 0.0,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.shear(
      shearingFactorHorizontal,
      shearingFactorVertical,
      resizeCanvasBasedOnTransformation,
      backgroundColor)
  }

  /**
   *
   *
   * @param bmp
   * @param shearingFactor
   *
   * @return
   */
  def shear(bmp: Bitmap, shearingFactor: Double): Bitmap = {
    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.shear(shearingFactor)
  }


  /**
   *
   *
   * @param bmp
   * @param shearingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def shearHorizontally(
      bmp: Bitmap,
      shearingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.shearHorizontally(
      shearingFactor,
      resizeCanvasBasedOnTransformation,
      backgroundColor)
  }

  /**
   *
   *
   * @param bmp
   * @param shearingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def shearVertically(
      bmp: Bitmap,
      shearingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.shearVertically(
      shearingFactor,
      resizeCanvasBasedOnTransformation,
      backgroundColor)
  }


  /**
   *
   *
   * @param bmp
   * @param numberOfReplicas
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def replicateHorizontally(
      bmp: Bitmap,
      numberOfReplicas: Int,
      paddingInPixels: Int = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.replicateHorizontally(
      numberOfReplicas,
      paddingInPixels,
      backgroundColor)
  }

  /**
   *
   *
   * @param bmp
   * @param numberOfReplicas
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def replicateVertically(
      bmp: Bitmap,
      numberOfReplicas: Int,
      paddingInPixels: Int = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")

    bmp.replicateVertically(
      numberOfReplicas,
      paddingInPixels,
      backgroundColor)
  }

}


//  /**
//   *
//   */
//  def argbIntAt(x: Int, y: Int): Int = {
//    require(widthRange.contains(x),
//      s"The x coordinate must be >= zero and less than the width of the image (was $x)")
//
//    require(heightRange.contains(y),
//      s"The y coordinate must be >= zero and less than the height of the image (was $y)")
//
//    buffer.getRGB(x, y)
//  }
//
//  /**
//   *
//   */
//  def colorComponentsAt(x: Int, y: Int): collection.immutable.Map[Symbol, Int] = {
//    colorComponentsFrom(argbIntAt(x, y))
//  }
//
//  /**
//   *
//   */
//  def setARGBIntAt(x: Int, y: Int, argbInt: Int): Unit = {
//    require(widthRange.contains(x),
//      s"The x coordinate must be >= zero and less than the width of the image (was $x)")
//
//    require(heightRange.contains(y),
//      s"The y coordinate must be >= zero and less than the height of the image (was $y)")
//
//    buffer.setRGB(x, y, argbInt)
//  }
//
//  /**
//   *
//   */
//  def setColorComponentsAt(x: Int, y: Int, red: Int, green: Int, blue: Int, opacity: Int): Unit = {
//    require(widthRange.contains(x),
//      s"The x coordinate must be >= zero and less than the width of the image (was $x)")
//
//    require(heightRange.contains(y),
//      s"The y coordinate must be >= zero and less than the height of the image (was $y)")
//
//    buffer.setRGB(x, y, argbIntFrom(red, green, blue, opacity))
//  }
//
//  /**
//   *
//   */
//  def redComponentAt(x: Int, y: Int): Int = redComponentFrom(argbIntAt(x, y))
//
//  /**
//   *
//   */
//  def setRedComponentAt(x: Int, y: Int, red: Int): Unit =
//    setARGBIntAt(x, y, withNewRedComponent(argbIntAt(x, y), red))
//
//  /**
//   *
//   */
//  def greenComponentAt(x: Int, y: Int): Int = greenComponentFrom(argbIntAt(x, y))
//
//  /**
//   *
//   */
//  def setGreenComponentAt(x: Int, y: Int, green: Int): Unit =
//    setARGBIntAt(x, y, withNewGreenComponent(argbIntAt(x, y), green))
//
//  /**
//   *
//   */
//  def blueComponentAt(x: Int, y: Int): Int = blueComponentFrom(argbIntAt(x, y))
//
//  /**
//   *
//   */
//  def setBlueComponentAt(x: Int, y: Int, blue: Int): Unit =
//    setARGBIntAt(x, y, withNewBlueComponent(argbIntAt(x, y), blue))
//
//  /**
//   *
//   */
//  def opacityComponentAt(x: Int, y: Int): Int = opacityComponentFrom(argbIntAt(x, y))
//
//  /**
//   *
//   */
//  def opacityComponentAt_=(x: Int, y: Int, opacity: Int): Unit =
//    setARGBIntAt(x, y, withNewOpacityComponent(argbIntAt(x, y), opacity))
