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

package aalto.smcl.bitmaps.simplified


import scala.collection.mutable

import aalto.smcl.bitmaps.fullfeatured.{AbstractBitmap, BitmapCompanion}
import aalto.smcl.bitmaps.operations._
import aalto.smcl.bitmaps.{BitmapValidator, ConvolutionKernel, PixelSnapshot, PixelSnapshotReceiver}
import aalto.smcl.colors.ColorValidator
import aalto.smcl.colors.rgb.{Color, ColorComponentTranslationTable}
import aalto.smcl.infrastructure.{BitmapBufferAdapter, Identity, OneHalfOfCircleInDegrees, OneQuarterOfCircleInDegreesClockwise, OneQuarterOfCircleInDegreesCounterClockwise}
import aalto.smcl.settings.{CanvasesAreResizedBasedOnTransformations, DefaultArcAngleInDegrees, DefaultArcStartAngleInDegrees, DefaultBackgroundColor, DefaultBitmapHeightInPixels, DefaultBitmapWidthInPixels, DefaultCircleRadiusInPixels, DefaultHorizontalAlignment, DefaultPaddingInPixels, DefaultPrimaryColor, DefaultRoundingHeightInPixels, DefaultRoundingWidthInPixels, DefaultSecondaryColor, DefaultVerticalAlignment, HACenter, HorizontalAlignment, PreventViewerUpdates, ShapesHaveBordersByDefault, ShapesHaveFillingsByDefault, UpdateViewerPerDefaults, VAMiddle, VerticalAlignment}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Bitmap extends BitmapCompanion[Bitmap] {

  /**
   * Creates a new empty [[Bitmap]] instance.
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param initialBackgroundColor
   *
   * @return
   */
  def apply(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      initialBackgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    super.apply(
      widthInPixels,
      heightInPixels,
      initialBackgroundColor,
      UpdateViewerPerDefaults)
  }

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  def apply(sourceResourcePath: String): Bitmap = {
    super.apply(
      sourceResourcePath,
      UpdateViewerPerDefaults)
  }

  /**
   * Creates a new empty [[Bitmap]] instance and applies a processing function
   * to it. Does NOT display the bitmap with the bitmap viewer.
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param initialBackgroundColor
   * @param processor
   *
   * @return
   */
  private[bitmaps]
  def createAndInitialize(
      widthInPixels: Int,
      heightInPixels: Int,
      initialBackgroundColor: Color,
      processor: Option[(Bitmap) => Bitmap]): Bitmap = {

    super.apply(
      widthInPixels,
      heightInPixels,
      initialBackgroundColor,
      processor,
      PreventViewerUpdates)
  }

  /**
   *
   *
   * @param operations
   * @param bitmapValidator
   * @param colorValidator
   * @param uniqueIdentifier
   *
   * @return
   */
  override
  protected
  def instantiateBitmap(
      operations: BitmapOperationList,
      bitmapValidator: BitmapValidator,
      colorValidator: ColorValidator,
      uniqueIdentifier: Identity): Bitmap = {

    new Bitmap(
      operations,
      bitmapValidator,
      colorValidator,
      uniqueIdentifier)
  }

}


/**
 *
 *
 * @param operations
 * @param bitmapValidator
 * @param uniqueIdentifier
 *
 * @author Aleksi Lukkarinen
 */
class Bitmap private[bitmaps](
    override private[bitmaps] val operations: BitmapOperationList,
    private val bitmapValidator: BitmapValidator,
    private val colorValidator: ColorValidator,
    uniqueIdentifier: Identity)
    extends AbstractBitmap(
      operations,
      bitmapValidator,
      colorValidator,
      uniqueIdentifier)
            with PixelSnapshotReceiver[Bitmap] {

  /**
   *
   *
   * @param f
   *
   * @return
   */
  def map(f: (Bitmap) => Bitmap): Bitmap = {
    f(this)
  }

  /**
   * Applies an [[Renderable]] to this [[Bitmap]].
   *
   * @param newOperation
   *
   * @return
   */
  private[bitmaps]
  def apply(newOperation: Renderable): Bitmap = {
    super.apply(
      newOperation,
      UpdateViewerPerDefaults).asInstanceOf[Bitmap]
  }

  /**
   * Applies an [[Renderable]] to this [[Bitmap]] without displaying the resulting bitmap.
   *
   * @param newOperation
   *
   * @return
   */
  private[bitmaps]
  override def applyInitialization(newOperation: Renderable): Bitmap = {
    super.apply(
      newOperation,
      PreventViewerUpdates).asInstanceOf[Bitmap]
  }

  /**
   * Applies an [[BufferProvider]] to this [[Bitmap]].
   *
   * @param newOperation
   *
   * @return
   */
  private[bitmaps]
  def apply(newOperation: BufferProvider): Bitmap = {
    super.apply(
      newOperation,
      UpdateViewerPerDefaults).asInstanceOf[Bitmap]
  }

  /**
   * Applies an [[BufferProvider]] to this [[Bitmap]] without displaying the resulting bitmap.
   *
   * @param newOperation
   *
   * @return
   */
  private[bitmaps]
  override def applyInitialization(
      newOperation: BufferProvider): Bitmap = {

    super.apply(
      newOperation,
      PreventViewerUpdates).asInstanceOf[Bitmap]
  }

  /**
   *
   *
   * @param snapshotBuffer
   */
  private[smcl]
  def applyPixelSnapshot(
      snapshotBuffer: BitmapBufferAdapter): Bitmap = {

    apply(ApplyPixelSnapshot(snapshotBuffer))
  }

  /**
   *
   *
   * @return
   */
  def aspectRatio: (Double, Double) = {
    if (widthInPixels == heightInPixels)
      return (1.0, 1.0)

    if (widthInPixels > heightInPixels)
      return (heightInPixels.toDouble / widthInPixels, 1.0)

    (1.0, widthInPixels.toDouble / heightInPixels)
  }

  /**
   *
   *
   * @param color
   *
   * @return
   */
  def clear(color: Color = DefaultBackgroundColor): Bitmap = {
    apply(Clear(color))
  }

  /**
   *
   *
   * @param kernel
   *
   * @return
   */
  def convolveWith(kernel: ConvolutionKernel): Bitmap = {
    apply(ConvolveWithCustomKernel(kernel))
  }

  /**
   *
   *
   * @param translator
   *
   * @return
   */
  def filterWith(translator: ColorComponentTranslationTable): Bitmap = {
    apply(FilterWithComponentTranslationTable(translator))
  }

  /**
   *
   *
   * @param function
   *
   * @return
   */
  def iteratePixelsWith(
      function: (Int, Int, Int, Int) => (Int, Int, Int, Int)): Bitmap = {

    apply(IteratePixels(function))
  }

  /**
   *
   *
   * @return
   */
  def createPixelSnapshot: PixelSnapshot[Bitmap] = {
    val buffer =
      toRenderedRepresentation.copyPortionXYWH(
        0, 0, widthInPixels, heightInPixels)

    new PixelSnapshot[Bitmap](
      widthInPixels,
      heightInPixels,
      this,
      buffer,
      this)
  }

  /**
   *
   *
   * @return
   */
  def toGrayscaleByLuminocity: Bitmap = apply(ToGrayscaleByLuminocity())

  /**
   *
   *
   * @return
   */
  def toGrayscaleByLightness: Bitmap = apply(ToGrayscaleByLightness())

  /**
   *
   *
   * @param redWeight
   * @param greenWeight
   * @param blueWeight
   *
   * @return
   */
  def toGrayscale(
      redWeight: Double,
      greenWeight: Double,
      blueWeight: Double): Bitmap = {

    apply(
      ToWeightedGrayscale(
        redWeight,
        greenWeight,
        blueWeight,
        colorValidator))
  }

  /**
   *
   *
   * @return
   */
  def negate: Bitmap = apply(Negate())

  /**
   *
   *
   * @return
   */
  def unary_~(): Bitmap = negate

  /**
   *
   *
   * @return
   */
  def negateRedComponent: Bitmap = apply(NegateRedComponent())

  /**
   *
   *
   * @return
   */
  def negateGreenComponent: Bitmap = apply(NegateGreenComponent())

  /**
   *
   *
   * @return
   */
  def negateBlueComponent: Bitmap = apply(NegateBlueComponent())

  /**
   *
   *
   * @return
   */
  def negateRedAndGreenComponents: Bitmap = {
    apply(NegateRedAndGreenComponents())
  }

  /**
   *
   *
   * @return
   */
  def negateRedAndBlueComponents: Bitmap = {
    apply(NegateRedAndBlueComponents())
  }

  /**
   *
   *
   * @return
   */
  def negateGreenAndBlueComponents: Bitmap = {
    apply(NegateGreenAndBlueComponents())
  }

  /**
   *
   *
   * @return
   */
  def keepOnlyRedComponent: Bitmap = apply(KeepOnlyRedComponent())

  /**
   *
   *
   * @return
   */
  def keepOnlyGreenComponent: Bitmap = apply(KeepOnlyGreenComponent())

  /**
   *
   *
   * @return
   */
  def keepOnlyBlueComponent: Bitmap = apply(KeepOnlyBlueComponent())

  /**
   *
   *
   * @return
   */
  def keepOnlyRedAndGreenComponents: Bitmap = {
    apply(KeepOnlyRedAndGreenComponents())
  }

  /**
   *
   *
   * @return
   */
  def keepOnlyRedAndBlueComponents: Bitmap = {
    apply(KeepOnlyRedAndBlueComponents())
  }

  /**
   *
   *
   * @return
   */
  def keepOnlyGreenAndBlueComponents: Bitmap = {
    apply(KeepOnlyGreenAndBlueComponents())
  }

  /**
   *
   *
   * @param strengthAsPercentage
   */
  def posterize(strengthAsPercentage: Int): Bitmap = {
    apply(Posterize(strengthAsPercentage))
  }

  /**
   *
   *
   * @param fromXInPixels
   * @param fromYInPixels
   * @param toXInPixels
   * @param toYInPixels
   * @param color
   *
   * @return
   */
  def drawLine(
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: Color = DefaultPrimaryColor): Bitmap = {

    apply(
      DrawLine(
        fromXInPixels,
        fromYInPixels,
        toXInPixels,
        toYInPixels,
        color))
  }

  /**
   *
   *
   * @param xCoordinates
   * @param yCoordinates
   * @param numberOfCoordinatesToDraw
   * @param color
   *
   * @return
   */
  def drawPolyline(
      xCoordinates: Seq[Int],
      yCoordinates: Seq[Int],
      numberOfCoordinatesToDraw: Int,
      color: Color = DefaultPrimaryColor): Bitmap = {

    apply(
      DrawPolyline(
        xCoordinates,
        yCoordinates,
        numberOfCoordinatesToDraw,
        color))
  }

  /**
   *
   *
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
      xCoordinates: Seq[Int],
      yCoordinates: Seq[Int],
      numberOfCoordinatesToDraw: Int,
      hasBorder: Boolean = true,
      hasFilling: Boolean = false,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Bitmap = {

    apply(
      DrawPolygon(
        xCoordinates,
        yCoordinates,
        numberOfCoordinatesToDraw,
        hasBorder,
        hasFilling,
        color,
        fillColor))
  }

  /**
   *
   *
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
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Bitmap = {

    apply(
      DrawSquare(
        upperLeftCornerXInPixels,
        upperLeftCornerYInPixels,
        sideLengthInPixels,
        hasBorder,
        hasFilling,
        color,
        fillColor))
  }

  /**
   *
   *
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
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Bitmap = {

    apply(
      DrawRectangle(
        upperLeftCornerXInPixels,
        upperLeftCornerYInPixels,
        widthInPixels,
        heightInPixels,
        hasBorder,
        hasFilling,
        color,
        fillColor))
  }

  /**
   *
   *
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
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
      roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
      roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Bitmap = {

    apply(
      DrawRoundedSquare(
        upperLeftCornerXInPixels,
        upperLeftCornerYInPixels,
        sideLengthInPixels,
        roundingWidthInPixels,
        roundingHeightInPixels,
        hasBorder,
        hasFilling,
        color,
        fillColor))
  }

  /**
   *
   *
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

    apply(
      DrawRoundedRectangle(
        upperLeftCornerXInPixels,
        upperLeftCornerYInPixels,
        widthInPixels,
        heightInPixels,
        roundingWidthInPixels,
        roundingHeightInPixels,
        hasBorder,
        hasFilling,
        color,
        fillColor))
  }

  /**
   *
   *
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
      centerXInPixels: Int,
      centerYInPixels: Int,
      radiusInPixels: Int = DefaultCircleRadiusInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Bitmap = {

    apply(
      DrawCircle(
        centerXInPixels,
        centerYInPixels,
        radiusInPixels,
        hasBorder,
        hasFilling,
        color,
        fillColor))
  }

  /**
   *
   *
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
      centerXInPixels: Int,
      centerYInPixels: Int,
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: Color = DefaultPrimaryColor,
      fillColor: Color = DefaultSecondaryColor): Bitmap = {

    apply(
      DrawEllipse(
        centerXInPixels,
        centerYInPixels,
        widthInPixels,
        heightInPixels,
        hasBorder,
        hasFilling,
        color,
        fillColor))
  }

  /**
   *
   *
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

    apply(
      DrawArc(
        upperLeftCornerXInPixels,
        upperLeftCornerYInPixels,
        widthInPixels,
        heightInPixels,
        startAngleInDegrees,
        arcAngleInDegrees,
        hasBorder,
        hasFilling,
        color,
        fillColor))
  }

  /**
   *
   *
   * @param colorToTrim
   *
   * @return
   */
  def trim(colorToTrim: Color = DefaultBackgroundColor): Bitmap = {
    apply(Trim(this, colorToTrim))
  }

  /**
   *
   *
   * @param windowTopLeftX
   * @param windowTopLeftY
   * @param windowBottomRightX
   * @param windowBottomRightY
   *
   * @return
   */
  def crop(
      windowTopLeftX: Int,
      windowTopLeftY: Int,
      windowBottomRightX: Int,
      windowBottomRightY: Int): Bitmap = {

    apply(
      Crop(
        this,
        windowTopLeftX,
        windowTopLeftY,
        windowBottomRightX,
        windowBottomRightY))
  }

  /**
   *
   *
   * @param extraPixelsOntoLeftEdge
   * @param extraPixelsOntoTopEdge
   * @param extraPixelsOntoRightEdge
   * @param extraPixelsOntoBottomEdge
   * @param color
   *
   * @return
   */
  def augmentCanvas(
      extraPixelsOntoLeftEdge: Int = 0,
      extraPixelsOntoTopEdge: Int = 0,
      extraPixelsOntoRightEdge: Int = 0,
      extraPixelsOntoBottomEdge: Int = 0,
      color: Color = DefaultBackgroundColor): Bitmap = {

    apply(
      AugmentCanvas(
        this,
        extraPixelsOntoLeftEdge,
        extraPixelsOntoTopEdge,
        extraPixelsOntoRightEdge,
        extraPixelsOntoBottomEdge,
        color,
        bitmapValidator))
  }

  /**
   *
   *
   * @param topBitmap
   * @param topBitmapUpperLeftX
   * @param topBitmapUpperLeftY
   * @param backgroundColor
   */
  def underlayBehind(
      topBitmap: Bitmap,
      topBitmapUpperLeftX: Int,
      topBitmapUpperLeftY: Int,
      topBitmapOpacity: Int = ColorValidator.MaximumOpacity,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    apply(
      OverlayFreely(
        this,
        topBitmap,
        topBitmapUpperLeftX,
        topBitmapUpperLeftY,
        topBitmapOpacity,
        backgroundColor,
        colorValidator))
  }

  /**
   *
   *
   * @param bottomBitmap
   * @param upperLeftX
   * @param upperLeftY
   * @param opacity
   * @param backgroundColor
   *
   * @return
   */
  def overlayOn(
      bottomBitmap: Bitmap,
      upperLeftX: Int,
      upperLeftY: Int,
      opacity: Int = ColorValidator.MaximumOpacity,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    apply(
      OverlayFreely(
        bottomBitmap,
        this,
        upperLeftX,
        upperLeftY,
        opacity,
        backgroundColor,
        colorValidator))
  }

  /**
   *
   *
   * @param bitmapsToLayOverThisFromBottomToTop
   * @param horizontalAlignment
   * @param verticalAlignment
   * @param opacityForAllBitmaps
   * @param backgroundColor
   *
   * @return
   */
  def overlayPerAlignments(
      bitmapsToLayOverThisFromBottomToTop: Bitmap*)(
      horizontalAlignment: HorizontalAlignment = DefaultHorizontalAlignment,
      verticalAlignment: VerticalAlignment = DefaultVerticalAlignment,
      opacityForAllBitmaps: Int = ColorValidator.MaximumOpacity,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    apply(
      OverlayPerAlignments(this +: bitmapsToLayOverThisFromBottomToTop)(
        horizontalAlignment,
        verticalAlignment,
        opacityForAllBitmaps,
        colorValidator = colorValidator))
  }

  /**
   *
   *
   * @param bitmapsToCombineWith
   * @param verticalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def appendOnLeft(
      bitmapsToCombineWith: Bitmap*)(
      verticalAlignment: VerticalAlignment = DefaultVerticalAlignment,
      paddingInPixels: Int = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    apply(
      AppendHorizontally(bitmapsToCombineWith :+ this)(
        verticalAlignment,
        paddingInPixels,
        backgroundColor,
        bitmapValidator))
  }

  /**
   *
   *
   * @param bitmapsToCombineWith
   * @param verticalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def appendOnRight(
      bitmapsToCombineWith: Bitmap*)(
      verticalAlignment: VerticalAlignment = DefaultVerticalAlignment,
      paddingInPixels: Int = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    apply(
      AppendHorizontally(this +: bitmapsToCombineWith)(
        verticalAlignment,
        paddingInPixels,
        backgroundColor,
        bitmapValidator))
  }

  /**
   *
   *
   * @param bitmap
   *
   * @return
   */
  def sewOnLeftOf(bitmap: Bitmap): Bitmap = {
    appendOnRight(bitmap)(VAMiddle)
  }

  /**
   *
   *
   * @param bitmapsToCombineWith
   * @param horizontalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def appendOnTop(
      bitmapsToCombineWith: Bitmap*)(
      horizontalAlignment: HorizontalAlignment = DefaultHorizontalAlignment,
      paddingInPixels: Int = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    apply(
      AppendVertically(bitmapsToCombineWith :+ this)(
        horizontalAlignment,
        paddingInPixels,
        backgroundColor,
        bitmapValidator))
  }

  /**
   *
   *
   * @param bitmap
   *
   * @return
   */
  def pileOnTopOf(bitmap: Bitmap): Bitmap = {
    appendOnBottom(bitmap)(HACenter)
  }

  /**
   *
   *
   * @param bitmapsToCombineWith
   * @param horizontalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def appendOnBottom(
      bitmapsToCombineWith: Bitmap*)(
      horizontalAlignment: HorizontalAlignment = DefaultHorizontalAlignment,
      paddingInPixels: Int = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    apply(
      AppendVertically(this +: bitmapsToCombineWith)(
        horizontalAlignment,
        paddingInPixels,
        backgroundColor,
        bitmapValidator))
  }

  /**
   *
   *
   * @return
   */
  def flipHorizontally: Bitmap = apply(FlipHorizontally())

  /**
   *
   *
   * @return
   */
  def flipVertically: Bitmap = apply(FlipVertically())

  /**
   *
   *
   * @return
   */
  def flipDiagonally: Bitmap = apply(FlipDiagonally())

  /**
   *
   *
   * @param scalingFactorHorizontal
   * @param scalingFactorVertical
   * @param resizeCanvasBasedOnTransformation
   *
   * @return
   */
  def scale(
      scalingFactorHorizontal: Double = 1.0,
      scalingFactorVertical: Double = 1.0,
      resizeCanvasBasedOnTransformation: Boolean = true): Bitmap = {

    apply(
      Scale(
        this,
        scalingFactorHorizontal,
        scalingFactorVertical,
        resizeCanvasBasedOnTransformation))
  }

  /**
   *
   *
   * @param scalingFactor
   *
   * @return
   */
  def scale(scalingFactor: Double): Bitmap = {
    scale(scalingFactor, scalingFactor)
  }

  /**
   *
   *
   * @param scalingFactor
   * @param resizeCanvasBasedOnTransformation
   *
   * @return
   */
  def scaleHorizontally(
      scalingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations): Bitmap = {

    scale(
      scalingFactorHorizontal = scalingFactor,
      resizeCanvasBasedOnTransformation = resizeCanvasBasedOnTransformation)
  }

  /**
   *
   *
   * @param scalingFactor
   * @param resizeCanvasBasedOnTransformation
   *
   * @return
   */
  def scaleVertically(
      scalingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations): Bitmap = {

    scale(
      scalingFactorVertical = scalingFactor,
      resizeCanvasBasedOnTransformation = resizeCanvasBasedOnTransformation)
  }

  /**
   *
   *
   * @param shearingFactorHorizontal
   * @param shearingFactorVertical
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def shear(
      shearingFactorHorizontal: Double = 0.0,
      shearingFactorVertical: Double = 0.0,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    apply(
      Shear(
        this,
        shearingFactorHorizontal,
        shearingFactorVertical,
        resizeCanvasBasedOnTransformation,
        backgroundColor))
  }

  /**
   *
   *
   * @param shearingFactor
   *
   * @return
   */
  def shear(shearingFactor: Double): Bitmap = {
    shear(shearingFactor, shearingFactor)
  }

  /**
   *
   *
   * @param shearingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def shearHorizontally(
      shearingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    shear(
      shearingFactorHorizontal = shearingFactor,
      resizeCanvasBasedOnTransformation = resizeCanvasBasedOnTransformation,
      backgroundColor = backgroundColor)
  }

  /**
   *
   *
   * @param shearingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def shearVertically(
      shearingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    shear(
      shearingFactorVertical = shearingFactor,
      resizeCanvasBasedOnTransformation = resizeCanvasBasedOnTransformation,
      backgroundColor = backgroundColor)
  }

  /**
   *
   *
   * @param angleInDegrees
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def rotateDegs(
      angleInDegrees: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    apply(
      Rotate(
        this,
        angleInDegrees,
        resizeCanvasBasedOnTransformation,
        backgroundColor))
  }

  /**
   *
   *
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def rotate90DegsCW(
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    rotateDegs(
      OneQuarterOfCircleInDegreesClockwise,
      resizeCanvasBasedOnTransformation,
      backgroundColor)
  }

  /**
   *
   *
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def rotate90DegsCCW(
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    rotateDegs(
      OneQuarterOfCircleInDegreesCounterClockwise,
      resizeCanvasBasedOnTransformation,
      backgroundColor)
  }

  /**
   *
   *
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def rotate180Degs(
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    rotateDegs(
      OneHalfOfCircleInDegrees,
      resizeCanvasBasedOnTransformation,
      backgroundColor)
  }

  /**
   *
   *
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def turn(
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    rotate90DegsCW(resizeCanvasBasedOnTransformation, backgroundColor)
  }

  /**
   *
   *
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   *
   * @return
   */
  def unturn(
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    rotate90DegsCCW(resizeCanvasBasedOnTransformation, backgroundColor)
  }

  /**
   *
   *
   * @param numberOfReplicas
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def replicateHorizontally(
      numberOfReplicas: Int,
      paddingInPixels: Int = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    apply(
      ReplicateHorizontally(
        this,
        numberOfReplicas,
        paddingInPixels,
        backgroundColor,
        bitmapValidator))
  }

  /**
   *
   *
   * @param numberOfReplicas
   * @param paddingInPixels
   * @param backgroundColor
   *
   * @return
   */
  def replicateVertically(
      numberOfReplicas: Int,
      paddingInPixels: Int = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor): Bitmap = {

    apply(
      ReplicateVertically(
        this,
        numberOfReplicas,
        paddingInPixels,
        backgroundColor,
        bitmapValidator))
  }

  /**
   * Returns a mutable `ArrayBuffer` containing a given number of copies of this [[Bitmap]] instance.
   *
   * @param size
   *
   * @return
   */
  private
  def propagateToArrayBuffer(size: Int): mutable.ArrayBuffer[Bitmap] = {
    require(size >= 0, s"Size of the collection cannot be negative (was $size)")

    mutable.ArrayBuffer.fill[Bitmap](size)(this)
  }

  /**
   * Returns an `Array` containing a given number of copies of this [[Bitmap]] instance.
   *
   * @param size
   *
   * @return
   */
  def propagateToArray(size: Int): Array[Bitmap] = {
    propagateToArrayBuffer(size).toArray
  }

  /**
   * Returns an `List` containing a given number of copies of this [[Bitmap]] instance.
   *
   * @param size
   *
   * @return
   */
  def propagateToList(size: Int): List[Bitmap] = {
    propagateToArrayBuffer(size).toList
  }

  /**
   * Returns an `Seq` containing a given number of copies of this [[Bitmap]] instance.
   *
   * @param size
   *
   * @return
   */
  def propagateToSeq(size: Int): Seq[Bitmap] = {
    propagateToArrayBuffer(size)
  }

  /**
   *
   *
   * @param operations
   * @param bitmapValidator
   * @param colorValidator
   * @param uniqueIdentifier
   *
   * @return
   */
  override
  protected
  def instantiateBitmap(
      operations: BitmapOperationList,
      bitmapValidator: BitmapValidator,
      colorValidator: ColorValidator,
      uniqueIdentifier: Identity): Bitmap = {

    Bitmap.instantiateBitmap(
      operations,
      bitmapValidator,
      colorValidator,
      uniqueIdentifier)
  }

}
