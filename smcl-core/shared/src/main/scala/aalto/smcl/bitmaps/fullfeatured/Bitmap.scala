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

package aalto.smcl.bitmaps.fullfeatured


import scala.collection.mutable

import aalto.smcl.bitmaps.operations._
import aalto.smcl.bitmaps.{BitmapValidator, ConvolutionKernel, PixelSnapshot, PixelSnapshotReceiver}
import aalto.smcl.colors.ColorValidator
import aalto.smcl.colors.rgb.{Color, ColorComponentTranslationTable}
import aalto.smcl.infrastructure._
import aalto.smcl.settings._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Bitmap extends BitmapCompanion[Bitmap] {

  /**
   * Creates a new empty [[Bitmap]] instance.
   */
  override
  def apply(
      widthInPixels: Int = DefaultBitmapWidthInPixels,
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      initialBackgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    super.apply(
      widthInPixels,
      heightInPixels,
      initialBackgroundColor,
      viewerHandling)
  }

  /**
   *
   *
   * @param sourceResourcePath
   * @param viewerHandling
   *
   * @return
   */
  override
  def apply(
      sourceResourcePath: String,
      viewerHandling: ViewerUpdateStyle): Bitmap = {

    super.apply(
      sourceResourcePath,
      viewerHandling)
  }

  /**
   * Creates a new empty [[Bitmap]] instance and applies a processing function to it.
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param initialBackgroundColor
   * @param processor
   *
   * @return
   */
  def apply(
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
   * HACK HACK HACK HACK --> REMOVE WHEN UNNECESSARY! See BitmapCompanion as well.
   *
   * @param bmpBfrAdapter
   *
   * @return
   */
  private[smcl]
  def apply(bmpBfrAdapter: BitmapBufferAdapter): Bitmap = {
    super.fromBitmapBufferAdapter(bmpBfrAdapter)
  }

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  def apply(sourceResourcePath: String): Bitmap = {
    apply(sourceResourcePath, UpdateViewerPerDefaults)
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
   * @param viewerHandling
   *
   * @return
   */
  private[bitmaps]
  override def apply(
      newOperation: Renderable,
      viewerHandling: ViewerUpdateStyle): Bitmap = {

    super.apply(newOperation, viewerHandling).asInstanceOf[Bitmap]
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
    super.apply(newOperation, PreventViewerUpdates).asInstanceOf[Bitmap]
  }

  /**
   * Applies an [[BufferProvider]] to this [[Bitmap]].
   *
   * @param newOperation
   * @param viewerHandling
   *
   * @return
   */
  private[bitmaps]
  override def apply(
      newOperation: BufferProvider,
      viewerHandling: ViewerUpdateStyle): Bitmap = {

    super.apply(newOperation, viewerHandling).asInstanceOf[Bitmap]
  }

  /**
   * Applies an [[BufferProvider]] to this [[Bitmap]] without displaying the resulting bitmap.
   *
   * @param newOperation
   *
   * @return
   */
  private[bitmaps]
  override def applyInitialization(newOperation: BufferProvider): Bitmap = {
    super.apply(newOperation, PreventViewerUpdates).asInstanceOf[Bitmap]
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
   * @param viewerHandling
   *
   * @return
   */
  def clear(
      color: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(Clear(color), viewerHandling)
  }

  /**
   *
   *
   * @param kernel
   * @param viewerHandling
   *
   * @return
   */
  def convolveWith(
      kernel: ConvolutionKernel,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(ConvolveWithCustomKernel(kernel), viewerHandling)
  }

  /**
   *
   *
   * @param translator
   * @param viewerHandling
   *
   * @return
   */
  def filterWith(
      translator: ColorComponentTranslationTable,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(FilterWithComponentTranslationTable(translator), viewerHandling)
  }

  /**
   *
   *
   * @param function
   * @param viewerHandling
   *
   * @return
   */
  def iteratePixelsWith(
      function: (Int, Int, Int, Int) => (Int, Int, Int, Int),
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(IteratePixels(function), viewerHandling)
  }

  /**
   *
   *
   * @param snapshotBuffer
   */
  private[smcl]
  def applyPixelSnapshot(
      snapshotBuffer: BitmapBufferAdapter): Bitmap = {

    apply(
      ApplyPixelSnapshot(snapshotBuffer),
      UpdateViewerPerDefaults)
  }

  /**
   *
   *
   * @param snapshotBuffer
   * @param viewerHandling
   */
  private[smcl] def applyPixelSnapshot(
      snapshotBuffer: BitmapBufferAdapter,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(ApplyPixelSnapshot(snapshotBuffer), viewerHandling)
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
   * @param viewerHandling
   *
   * @return
   */
  def toGrayscaleByLuminocity(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(ToGrayscaleByLuminocity(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def toGrayscaleByLightness(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(ToGrayscaleByLightness(), viewerHandling)
  }

  /**
   *
   *
   * @param redWeight
   * @param greenWeight
   * @param blueWeight
   * @param viewerHandling
   *
   * @return
   */
  def toGrayscale(
      redWeight: Double,
      greenWeight: Double,
      blueWeight: Double,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      ToWeightedGrayscale(
        redWeight,
        greenWeight,
        blueWeight,
        colorValidator),
      viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def negate(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(Negate(), viewerHandling)
  }

  /**
   *
   *
   * @return
   */
  def unary_~(): Bitmap = negate()

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def negateRedComponent(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(NegateRedComponent(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def negateGreenComponent(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(NegateGreenComponent(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def negateBlueComponent(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(NegateBlueComponent(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def negateRedAndGreenComponents(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(NegateRedAndGreenComponents(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def negateRedAndBlueComponents(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(NegateRedAndBlueComponents(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def negateGreenAndBlueComponents(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(NegateGreenAndBlueComponents(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def keepOnlyRedComponent(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(KeepOnlyRedComponent(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def keepOnlyGreenComponent(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(KeepOnlyGreenComponent(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def keepOnlyBlueComponent(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(KeepOnlyBlueComponent(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def keepOnlyRedAndGreenComponents(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(KeepOnlyRedAndGreenComponents(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def keepOnlyRedAndBlueComponents(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(KeepOnlyRedAndBlueComponents(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def keepOnlyGreenAndBlueComponents(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(KeepOnlyGreenAndBlueComponents(), viewerHandling)
  }

  /**
   *
   *
   * @param strengthAsPercentage
   * @param viewerHandling
   */
  def posterize(
      strengthAsPercentage: Int,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(Posterize(strengthAsPercentage), viewerHandling)
  }

  /**
   *
   *
   * @param fromXInPixels
   * @param fromYInPixels
   * @param toXInPixels
   * @param toYInPixels
   * @param color
   * @param viewerHandling
   *
   * @return
   */
  def drawLine(
      fromXInPixels: Int,
      fromYInPixels: Int,
      toXInPixels: Int,
      toYInPixels: Int,
      color: Color = DefaultPrimaryColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      DrawLine(
        fromXInPixels,
        fromYInPixels,
        toXInPixels,
        toYInPixels,
        color),
      viewerHandling)
  }

  /**
   *
   *
   * @param xCoordinates
   * @param yCoordinates
   * @param numberOfCoordinatesToDraw
   * @param color
   * @param viewerHandling
   *
   * @return
   */
  def drawPolyline(
      xCoordinates: Seq[Int],
      yCoordinates: Seq[Int],
      numberOfCoordinatesToDraw: Int,
      color: Color = DefaultPrimaryColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      DrawPolyline(
        xCoordinates,
        yCoordinates,
        numberOfCoordinatesToDraw,
        color),
      viewerHandling)
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
   * @param viewerHandling
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
      fillColor: Color = DefaultSecondaryColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      DrawPolygon(
        xCoordinates,
        yCoordinates,
        numberOfCoordinatesToDraw,
        hasBorder,
        hasFilling,
        color,
        fillColor),
      viewerHandling)
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
   * @param viewerHandling
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
      fillColor: Color = DefaultSecondaryColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      DrawSquare(
        upperLeftCornerXInPixels,
        upperLeftCornerYInPixels,
        sideLengthInPixels,
        hasBorder,
        hasFilling,
        color,
        fillColor),
      viewerHandling)
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
   * @param viewerHandling
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
      fillColor: Color = DefaultSecondaryColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      DrawRectangle(
        upperLeftCornerXInPixels,
        upperLeftCornerYInPixels,
        widthInPixels,
        heightInPixels,
        hasBorder,
        hasFilling,
        color,
        fillColor),
      viewerHandling)
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
   * @param viewerHandling
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
      fillColor: Color = DefaultSecondaryColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

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
        fillColor),
      viewerHandling)
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
   * @param viewerHandling
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
      fillColor: Color = DefaultSecondaryColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

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
        fillColor),
      viewerHandling)
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
   * @param viewerHandling
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
      fillColor: Color = DefaultSecondaryColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      DrawCircle(
        centerXInPixels,
        centerYInPixels,
        radiusInPixels,
        hasBorder,
        hasFilling,
        color,
        fillColor),
      viewerHandling)
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
   * @param viewerHandling
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
      fillColor: Color = DefaultSecondaryColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      DrawEllipse(
        centerXInPixels,
        centerYInPixels,
        widthInPixels,
        heightInPixels,
        hasBorder,
        hasFilling,
        color,
        fillColor),
      viewerHandling)
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
   * @param viewerHandling
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
      fillColor: Color = DefaultSecondaryColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

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
        fillColor),
      viewerHandling)
  }

  /**
   *
   *
   * @param colorToTrim
   * @param viewerHandling
   *
   * @return
   */
  def trim(
      colorToTrim: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(Trim(this, colorToTrim), viewerHandling)
  }

  /**
   *
   *
   * @param windowTopLeftX
   * @param windowTopLeftY
   * @param windowBottomRightX
   * @param windowBottomRightY
   * @param viewerHandling
   *
   * @return
   */
  def crop(
      windowTopLeftX: Int,
      windowTopLeftY: Int,
      windowBottomRightX: Int,
      windowBottomRightY: Int,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      Crop(
        this,
        windowTopLeftX,
        windowTopLeftY,
        windowBottomRightX,
        windowBottomRightY),
      viewerHandling)
  }

  /**
   *
   *
   * @param extraPixelsOntoLeftEdge
   * @param extraPixelsOntoTopEdge
   * @param extraPixelsOntoRightEdge
   * @param extraPixelsOntoBottomEdge
   * @param color
   * @param viewerHandling
   *
   * @return
   */
  def augmentCanvas(
      extraPixelsOntoLeftEdge: Int = 0,
      extraPixelsOntoTopEdge: Int = 0,
      extraPixelsOntoRightEdge: Int = 0,
      extraPixelsOntoBottomEdge: Int = 0,
      color: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      AugmentCanvas(
        this,
        extraPixelsOntoLeftEdge,
        extraPixelsOntoTopEdge,
        extraPixelsOntoRightEdge,
        extraPixelsOntoBottomEdge,
        color,
        bitmapValidator),
      viewerHandling)
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
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      OverlayFreely(
        this,
        topBitmap,
        topBitmapUpperLeftX,
        topBitmapUpperLeftY,
        topBitmapOpacity,
        backgroundColor,
        colorValidator),
      viewerHandling)
  }

  /**
   *
   *
   * @param bottomBitmap
   * @param upperLeftX
   * @param upperLeftY
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def overlayOn(
      bottomBitmap: Bitmap,
      upperLeftX: Int,
      upperLeftY: Int,
      opacity: Int = ColorValidator.MaximumOpacity,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      OverlayFreely(
        bottomBitmap,
        this,
        upperLeftX,
        upperLeftY,
        opacity,
        backgroundColor,
        colorValidator),
      viewerHandling)
  }


  // ----------------------------------------------------------------------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|++| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HALeft, VATop)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|+*| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HALeft, VAMiddle)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|+-| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HALeft, VABottom)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|*+| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HACenter, VATop)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|**| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HACenter, VAMiddle)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|*-| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HACenter, VABottom)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|-+| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HARight, VATop)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|-*| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HARight, VAMiddle)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|--| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(HARight, VABottom)
  }

  // ----------------------------------------------------------------------------------------------


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
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      OverlayPerAlignments(this +: bitmapsToLayOverThisFromBottomToTop)(
        horizontalAlignment,
        verticalAlignment,
        opacityForAllBitmaps,
        colorValidator = colorValidator),
      viewerHandling)
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
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      AppendHorizontally(bitmapsToCombineWith :+ this)(
        verticalAlignment,
        paddingInPixels,
        backgroundColor,
        bitmapValidator),
      viewerHandling)
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
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      AppendHorizontally(this +: bitmapsToCombineWith)(
        verticalAlignment,
        paddingInPixels,
        backgroundColor,
        bitmapValidator),
      viewerHandling)
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
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      AppendVertically(bitmapsToCombineWith :+ this)(
        horizontalAlignment,
        paddingInPixels,
        backgroundColor,
        bitmapValidator),
      viewerHandling)
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
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      AppendVertically(this +: bitmapsToCombineWith)(
        horizontalAlignment,
        paddingInPixels,
        backgroundColor,
        bitmapValidator),
      viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def flipHorizontally(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(FlipHorizontally(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def flipVertically(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(FlipVertically(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def flipDiagonally(
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(FlipDiagonally(), viewerHandling)
  }

  /**
   *
   *
   * @param scalingFactorHorizontal
   * @param scalingFactorVertical
   * @param resizeCanvasBasedOnTransformation
   * @param viewerHandling
   *
   * @return
   */
  def scale(
      scalingFactorHorizontal: Double = 1.0,
      scalingFactorVertical: Double = 1.0,
      resizeCanvasBasedOnTransformation: Boolean = true,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      Scale(
        this,
        scalingFactorHorizontal,
        scalingFactorVertical,
        resizeCanvasBasedOnTransformation),
      viewerHandling)
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
   * @param viewerHandling
   *
   * @return
   */
  def scaleHorizontally(
      scalingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    scale(
      scalingFactorHorizontal = scalingFactor,
      resizeCanvasBasedOnTransformation = resizeCanvasBasedOnTransformation,
      viewerHandling = viewerHandling)
  }

  /**
   *
   *
   * @param scalingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param viewerHandling
   *
   * @return
   */
  def scaleVertically(
      scalingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    scale(
      scalingFactorVertical = scalingFactor,
      resizeCanvasBasedOnTransformation = resizeCanvasBasedOnTransformation,
      viewerHandling = viewerHandling)
  }

  /**
   *
   *
   * @param shearingFactorHorizontal
   * @param shearingFactorVertical
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def shear(
      shearingFactorHorizontal: Double = 0.0,
      shearingFactorVertical: Double = 0.0,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      Shear(
        this,
        shearingFactorHorizontal,
        shearingFactorVertical,
        resizeCanvasBasedOnTransformation,
        backgroundColor),
      viewerHandling)
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
   * @param viewerHandling
   *
   * @return
   */
  def shearHorizontally(
      shearingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    shear(
      shearingFactorHorizontal = shearingFactor,
      resizeCanvasBasedOnTransformation = resizeCanvasBasedOnTransformation,
      backgroundColor = backgroundColor,
      viewerHandling = viewerHandling)
  }

  /**
   *
   *
   * @param shearingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def shearVertically(
      shearingFactor: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    shear(
      shearingFactorVertical = shearingFactor,
      resizeCanvasBasedOnTransformation = resizeCanvasBasedOnTransformation,
      backgroundColor = backgroundColor,
      viewerHandling = viewerHandling)
  }

  /**
   *
   *
   * @param angleInDegrees
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def rotateDegs(
      angleInDegrees: Double,
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      Rotate(
        this,
        angleInDegrees,
        resizeCanvasBasedOnTransformation,
        backgroundColor),
      viewerHandling)
  }

  /**
   *
   *
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def rotate90DegsCW(
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      Rotate(
        this,
        OneQuarterOfCircleInDegreesClockwise,
        resizeCanvasBasedOnTransformation,
        backgroundColor),
      viewerHandling)
  }

  /**
   *
   *
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def rotate90DegsCCW(
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      Rotate(
        this,
        OneQuarterOfCircleInDegreesCounterClockwise,
        resizeCanvasBasedOnTransformation,
        backgroundColor),
      viewerHandling)
  }

  /**
   *
   *
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def rotate180Degs(
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      Rotate(
        this,
        OneHalfOfCircleInDegrees,
        resizeCanvasBasedOnTransformation,
        backgroundColor),
      viewerHandling)
  }

  /**
   *
   *
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def turn(
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    rotate90DegsCW(resizeCanvasBasedOnTransformation, backgroundColor, viewerHandling)
  }

  /**
   *
   *
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def unturn(
      resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    rotate90DegsCCW(resizeCanvasBasedOnTransformation, backgroundColor, viewerHandling)
  }

  /**
   *
   *
   * @param numberOfReplicas
   * @param paddingInPixels
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def replicateHorizontally(
      numberOfReplicas: Int,
      paddingInPixels: Int = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      ReplicateHorizontally(
        this,
        numberOfReplicas,
        paddingInPixels,
        backgroundColor,
        bitmapValidator),
      viewerHandling)
  }

  /**
   *
   *
   * @param numberOfReplicas
   * @param paddingInPixels
   * @param backgroundColor
   * @param viewerHandling
   *
   * @return
   */
  def replicateVertically(
      numberOfReplicas: Int,
      paddingInPixels: Int = DefaultPaddingInPixels,
      backgroundColor: Color = DefaultBackgroundColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    apply(
      ReplicateVertically(
        this,
        numberOfReplicas,
        paddingInPixels,
        backgroundColor,
        bitmapValidator),
      viewerHandling)
  }


  // ----------------------------------------------------------------------------------------------

  //-------------------------------
  //
  //  :/\
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :/\ (other: Bitmap): Bitmap = appendOnTop(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :/\ (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnTop(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :/\ (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    :/\(other.toSeq)
  }

  //-------------------------------
  //
  //  /\:
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def /\: (other: Bitmap): Bitmap = appendOnTop(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def /\: (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnTop(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def /\: (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    /\:(other.toSeq)
  }

  //-------------------------------
  //
  //  :\/
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :\/ (other: Bitmap): Bitmap = appendOnBottom(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :\/ (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnBottom(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :\/ (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    :\/(other.toSeq)
  }

  //-------------------------------
  //
  //  \/:
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def \/: (other: Bitmap): Bitmap = appendOnBottom(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def \/: (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnBottom(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def \/: (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    \/:(other.toSeq)
  }

  //-------------------------------
  //
  //  :>>
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :>> (other: Bitmap): Bitmap = appendOnRight(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :>> (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnRight(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :>> (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    :>>(other.toSeq)
  }

  //-------------------------------
  //
  //  >>:
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def >>: (other: Bitmap): Bitmap = appendOnRight(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def >>: (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnRight(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def >>: (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    >>:(other.toSeq)
  }

  //-------------------------------
  //
  //  :<<
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :<< (other: Bitmap): Bitmap = appendOnLeft(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :<< (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnLeft(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :<< (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    :<<(other.toSeq)
  }

  //-------------------------------
  //
  //  <<:
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def <<: (other: Bitmap): Bitmap = appendOnLeft(other)()

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def <<: (other: scala.collection.Seq[Bitmap]): Bitmap = {
    appendOnLeft(other: _*)()
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def <<: (other: scala.collection.Traversable[Bitmap]): Bitmap = {
    <<:(other.toSeq)
  }

  // ----------------------------------------------------------------------------------------------


  /**
   * Returns a mutable `ArrayBuffer` containing a given number of copies of this [[Bitmap]] instance.
   *
   * @param size
   *
   * @return
   */
  private def propagateToArrayBuffer(size: Int): mutable.ArrayBuffer[Bitmap] = {
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
