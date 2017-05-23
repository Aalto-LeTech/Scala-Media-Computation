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

package aalto.smcl.bitmaps


import scala.collection.mutable
import scala.ref.WeakReference

import aalto.smcl.bitmaps.ViewerUpdateStyle.{PreventViewerUpdates, UpdateViewerPerDefaults}
import aalto.smcl.bitmaps.operations._
import aalto.smcl.colors.{ColorValidator, RGBAColor, RGBAComponentTranslationTable}
import aalto.smcl.geometry.AffineTransformation
import aalto.smcl.infrastructure.{DrawingSurfaceAdapter, _}
import aalto.smcl.viewers.{display => displayInViewer}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Bitmap extends InjectableRegistry {

  /** The ColorValidator instance to be used by this object. */
  private lazy val colorValidator: ColorValidator = {
    injectable(InjectableRegistry.IIdColorValidator).asInstanceOf[ColorValidator]
  }

  /** The BitmapValidator instance to be used by this object. */
  private lazy val bitmapValidator: BitmapValidator = {
    injectable(InjectableRegistry.IIdBitmapValidator).asInstanceOf[BitmapValidator]
  }

  /**
   * Creates a new empty [[Bitmap]] instance.
   */
  def apply(
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      initialBackgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    bitmapValidator.validateBitmapSize(widthInPixels, heightInPixels)

    if (bitmapValidator.warningSizeLimitsAreExceeded(widthInPixels, heightInPixels)) {
      println("\n\nWarning: The image is larger than the recommended maximum size")
    }

    require(initialBackgroundColor != null,
      "The background color argument has to be a Color instance (was null).")

    val operationList =
      Clear(initialBackgroundColor) +:
          BitmapOperationList(CreateBitmap(widthInPixels, heightInPixels))

    val newBitmap = new Bitmap(operationList, bitmapValidator, colorValidator, Identity())

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (GS.isTrueThat(NewBitmapsAreDisplayedAutomatically))
        newBitmap.display()
    }

    newBitmap
  }

  /**
   *
   *
   * @param sourceResourcePath
   * @param viewerHandling
   *
   * @return
   */
  def apply(
      sourceResourcePath: String,
      viewerHandling: ViewerUpdateStyle.Value): BitmapLoadingResult = {

    // The ImageProvider is trusted with validation of the source resource path.
    val loadedBuffersTry = PRF.tryToLoadImagesFromPath(sourceResourcePath)
    if (loadedBuffersTry.isFailure)
      throw loadedBuffersTry.failed.get

    val bitmapsOrThrowables: collection.Seq[Either[(Int, Throwable), (Int, Bitmap)]] =
      loadedBuffersTry.get.zipWithIndex map {
        case (Left(throwable), index) =>
          Left((index, throwable))

        case (Right(buffer), index) =>
          val operationList = BitmapOperationList(
            LoadedBitmap(buffer, Option(sourceResourcePath), Option(index)))
          val newBitmap = new Bitmap(
            operationList, bitmapValidator, colorValidator, Identity())

          if (viewerHandling == UpdateViewerPerDefaults) {
            if (GS.isTrueThat(NewBitmapsAreDisplayedAutomatically))
              newBitmap.display()
          }

          Right((index, newBitmap))
      }

    BitmapLoadingResult(bitmapsOrThrowables)
  }

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  def apply(sourceResourcePath: String): BitmapLoadingResult = {
    apply(sourceResourcePath, UpdateViewerPerDefaults)
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
case class Bitmap private(
    private[bitmaps] val operations: BitmapOperationList,
    private val bitmapValidator: BitmapValidator,
    private val colorValidator: ColorValidator,
    uniqueIdentifier: Identity) extends {

  /** Width of this [[Bitmap]]. */
  val widthInPixels: Int = operations.widthInPixels

  /** Height of this [[Bitmap]]. */
  val heightInPixels: Int = operations.heightInPixels

  /** Rendering buffer for this image. */
  private[this] var _renderingBuffer: WeakReference[BitmapBufferAdapter] =
    WeakReference[BitmapBufferAdapter](null)

} with Displayable
       with RenderableBitmap
       with PixelRectangle
       with Immutable
       with TimestampedCreation {

  /**
   * Returns the initial background color of this [[Bitmap]]
   * (may not be the actual background color at a later time).
   */
  val initialBackgroundColor: RGBAColor = {
    operations.initialBackgroundColor
  }

  /**
   * Applies an [[Renderable]] to this [[Bitmap]].
   *
   * @param newOperation
   * @param viewerHandling
   *
   * @return
   */
  private[bitmaps] def apply(
      newOperation: Renderable,
      viewerHandling: ViewerUpdateStyle.Value): Bitmap = {

    require(newOperation != null, "Operation argument cannot be null.")

    val newBitmap = copy(operations = newOperation +: operations)

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (GS.isTrueThat(BitmapsAreDisplayedAutomaticallyAfterOperations))
        newBitmap.display()
    }

    newBitmap
  }

  /**
   * Applies an [[Renderable]] to this [[Bitmap]] without displaying the resulting bitmap.
   *
   * @param newOperation
   *
   * @return
   */
  private[bitmaps] def applyInitialization(newOperation: Renderable): Bitmap =
    apply(newOperation, PreventViewerUpdates)

  /**
   * Applies an [[BufferProvider]] to this [[Bitmap]].
   *
   * @param newOperation
   * @param viewerHandling
   *
   * @return
   */
  private[bitmaps] def apply(
      newOperation: BufferProvider,
      viewerHandling: ViewerUpdateStyle.Value): Bitmap = {

    require(newOperation != null, "Operation argument cannot be null.")

    val newOperationList = BitmapOperationList(newOperation)
    val newBitmap = copy(operations = newOperationList)

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (GS.isTrueThat(BitmapsAreDisplayedAutomaticallyAfterOperations))
        newBitmap.display()
    }

    newBitmap
  }

  /**
   * Applies an [[BufferProvider]] to this [[Bitmap]] without displaying the resulting bitmap.
   *
   * @param newOperation
   *
   * @return
   */
  private[bitmaps] def applyInitialization(newOperation: BufferProvider): Bitmap = {
    apply(newOperation, PreventViewerUpdates)
  }

  /**
   *
   *
   * @param filename
   *
   * @return
   */
  def saveAsPngTo(filename: String): String = {
    toRenderedRepresentation.saveAsPngTo(filename)
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
      color: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      translator: RGBAComponentTranslationTable,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(IteratePixels(function), viewerHandling)
  }

  /**
   *
   *
   * @param snapshotBuffer
   * @param viewerHandling
   */
  private[smcl] def applyPixelSnapshot(
      snapshotBuffer: BitmapBufferAdapter,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(ApplyPixelSnapshot(snapshotBuffer), viewerHandling)
  }

  /**
   *
   *
   * @return
   */
  def createPixelSnapshot: PixelSnapshot = {
    new PixelSnapshot(this)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def convertToGrayscaleByLuminocity(
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(ToGrayscaleByLuminocity(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   *
   * @return
   */
  def convertToGrayscaleByLightness(
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
  def convertToGrayscale(
      redWeight: Double,
      greenWeight: Double,
      blueWeight: Double,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
      hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
      hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      roundingWidthInPixels: Int = GS.intFor(DefaultRoundingWidthInPixels),
      roundingHeightInPixels: Int = GS.intFor(DefaultRoundingHeightInPixels),
      hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
      hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      roundingWidthInPixels: Int = GS.intFor(DefaultRoundingWidthInPixels),
      roundingHeightInPixels: Int = GS.intFor(DefaultRoundingHeightInPixels),
      hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
      hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      radiusInPixels: Int = GS.intFor(DefaultCircleRadiusInPixels),
      hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
      hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
      hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      startAngleInDegrees: Int = GS.intFor(DefaultArcStartAngleInDegrees),
      arcAngleInDegrees: Int = GS.intFor(DefaultArcAngleInDegrees),
      hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
      hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      colorToTrim: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      color: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      topBitmapOpacity: Int = ColorValidator.MaximumRGBAOpacity,
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      opacity: Int = ColorValidator.MaximumRGBAOpacity,
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
    overlayPerAlignments(this, other)(
      HorizontalAlignment.Left, VerticalAlignment.Top)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|+*| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(
      HorizontalAlignment.Left, VerticalAlignment.Middle)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|+-| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(
      HorizontalAlignment.Left, VerticalAlignment.Bottom)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|*+| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(
      HorizontalAlignment.Center, VerticalAlignment.Top)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|**| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(
      HorizontalAlignment.Center, VerticalAlignment.Middle)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|*-| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(
      HorizontalAlignment.Center, VerticalAlignment.Bottom)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|-+| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(
      HorizontalAlignment.Right, VerticalAlignment.Top)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|-*| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(
      HorizontalAlignment.Right, VerticalAlignment.Middle)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def :|--| (other: Bitmap): Bitmap = {
    overlayPerAlignments(this, other)(
      HorizontalAlignment.Right, VerticalAlignment.Bottom)
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
      horizontalAlignment: HorizontalAlignment.Value = GS.optionFor(DefaultHorizontalAlignment),
      verticalAlignment: VerticalAlignment.Value = GS.optionFor(DefaultVerticalAlignment),
      opacityForAllBitmaps: Int = ColorValidator.MaximumRGBAOpacity,
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      verticalAlignment: VerticalAlignment.Value = GS.optionFor(DefaultVerticalAlignment),
      paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      verticalAlignment: VerticalAlignment.Value = GS.optionFor(DefaultVerticalAlignment),
      paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
    appendOnRight(bitmap)(VerticalAlignment.Middle)
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
      horizontalAlignment: HorizontalAlignment.Value = GS.optionFor(DefaultHorizontalAlignment),
      paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
    appendOnBottom(bitmap)(HorizontalAlignment.Center)
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
      horizontalAlignment: HorizontalAlignment.Value = GS.optionFor(DefaultHorizontalAlignment),
      paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
  def rotate90DegsCw(
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
  def rotate90DegsCcw(
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    rotate90DegsCw(resizeCanvasBasedOnTransformation, backgroundColor, viewerHandling)
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
      resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    rotate90DegsCcw(resizeCanvasBasedOnTransformation, backgroundColor, viewerHandling)
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
      paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
      paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
   * Renders this [[Bitmap]] onto a drawing surface using specified coordinates.
   *
   * @param drawingSurface
   * @param x
   * @param y
   */
  def renderOnto(drawingSurface: DrawingSurfaceAdapter, x: Int, y: Int): Unit = {
    require(drawingSurface != null, "Drawing surface argument cannot be null.")

    val rendition = toRenderedRepresentation
    drawingSurface.drawBitmap(rendition, x, y)
  }

  /**
   * Renders this [[Bitmap]] onto a drawing surface using specified affine transformation.
   *
   * @param drawingSurface
   * @param transformation
   */
  def renderOnto(
      drawingSurface: DrawingSurfaceAdapter,
      transformation: AffineTransformation): Unit = {

    require(drawingSurface != null, "Drawing surface argument cannot be null.")

    val rendition = toRenderedRepresentation
    drawingSurface.drawBitmap(rendition, transformation)
  }

  /**
   * Returns a `BufferedImage` instance representing this [[Bitmap]].
   */
  def toRenderedRepresentation: BitmapBufferAdapter =
    _renderingBuffer.get getOrElse {
      val rendition = operations.render()

      _renderingBuffer = WeakReference[BitmapBufferAdapter](rendition)

      return rendition
    }

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
   */
  def display(): Bitmap = {
    displayInViewer(this)

    this
  }

}
