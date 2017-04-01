package aalto.smcl.bitmaps


import scala.collection.mutable
import scala.ref.WeakReference

import aalto.smcl.bitmaps.ViewerUpdateStyle.{PreventViewerUpdates, UpdateViewerPerDefaults}
import aalto.smcl.bitmaps.operations._
import aalto.smcl.viewers.{display => displayInViewer}
import aalto.smcl.colors.{ColorValidator, RGBAColor, RGBAComponentTranslationTable}
import aalto.smcl.geometry.AffineTransformation
import aalto.smcl.infrastructure._
import aalto.smcl.infrastructure.DrawingSurfaceAdapter




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ImmutableBitmap {

  /**  */
  private val _bitmapValidator: BitmapValidator = new BitmapValidator()


  /**
   * Creates a new empty [[ImmutableBitmap]] instance.
   */
  def apply(
    widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
    initialBackgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

    _bitmapValidator.validateBitmapSize(widthInPixels, heightInPixels)

    if (_bitmapValidator.warningSizeLimitsAreExceeded(widthInPixels, heightInPixels)) {
      println("\n\nWarning: The image is larger than the recommended maximum size")
    }

    require(initialBackgroundColor != null, "The background color argument has to be a Color instance (was null).")

    val operationList =
      Clear(initialBackgroundColor) +:
        BitmapOperationList(CreateBitmap(widthInPixels, heightInPixels))

    val newBitmap = new ImmutableBitmap(operationList, _bitmapValidator, BitmapIdentity())

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
          val operationList = BitmapOperationList(LoadedBitmap(buffer, Option(sourceResourcePath), Option(index)))
          val newBitmap = new ImmutableBitmap(operationList, _bitmapValidator, BitmapIdentity())

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
   * @return
   */
  def apply(sourceResourcePath: String): BitmapLoadingResult =
    apply(sourceResourcePath, UpdateViewerPerDefaults)

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
case class ImmutableBitmap private(
  private[bitmaps] val operations: BitmapOperationList,
  private val bitmapValidator: BitmapValidator,
  uniqueIdentifier: BitmapIdentity) extends {

  /** Width of this [[ImmutableBitmap]]. */
  val widthInPixels: Int = operations.widthInPixels

  /** Height of this [[ImmutableBitmap]]. */
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
   * Returns the initial background color of this [[ImmutableBitmap]]
   * (may not be the actual background color at a later time).
   */
  val initialBackgroundColor: RGBAColor =
    operations.initialBackgroundColor

  /**
   * Applies an [[Renderable]] to this [[ImmutableBitmap]].
   *
   * @param newOperation
   * @param viewerHandling
   * @return
   */
  private[bitmaps] def apply(
    newOperation: Renderable,
    viewerHandling: ViewerUpdateStyle.Value): ImmutableBitmap = {

    require(newOperation != null, "Operation argument cannot be null.")

    val newBitmap = copy(operations = newOperation +: operations)

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (GS.isTrueThat(BitmapsAreDisplayedAutomaticallyAfterOperations))
        newBitmap.display()
    }

    newBitmap
  }

  /**
   * Applies an [[Renderable]] to this [[ImmutableBitmap]] without displaying the resulting bitmap.
   *
   * @param newOperation
   * @return
   */
  private[bitmaps] def applyInitialization(newOperation: Renderable): ImmutableBitmap =
    apply(newOperation, PreventViewerUpdates)

  /**
   * Applies an [[BufferProvider]] to this [[ImmutableBitmap]].
   *
   * @param newOperation
   * @param viewerHandling
   * @return
   */
  private[bitmaps] def apply(
    newOperation: BufferProvider,
    viewerHandling: ViewerUpdateStyle.Value): ImmutableBitmap = {

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
   * Applies an [[BufferProvider]] to this [[ImmutableBitmap]] without displaying the resulting bitmap.
   *
   * @param newOperation
   * @return
   */
  private[bitmaps] def applyInitialization(newOperation: BufferProvider): ImmutableBitmap =
    apply(newOperation, PreventViewerUpdates)

  /**
   *
   *
   * @param filename
   * @return
   */
  def saveAsPngTo(filename: String): String =
    toRenderedRepresentation.saveAsPngTo(filename)

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
   * @return
   */
  def clear(
    color: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

    apply(Clear(color), viewerHandling)
  }

  /**
   *
   *
   * @param kernel
   * @param viewerHandling
   * @return
   */
  def convolveWith(
    kernel: ConvolutionKernel,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

    apply(ConvolveWithCustomKernel(kernel), viewerHandling)
  }

  /**
   *
   *
   * @param translator
   * @param viewerHandling
   * @return
   */
  def filterWith(
    translator: RGBAComponentTranslationTable,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

    apply(FilterWithComponentTranslationTable(translator), viewerHandling)
  }

  /**
   *
   *
   * @param function
   * @param viewerHandling
   * @return
   */
  def iteratePixelsWith(
    function: (Int, Int, Int, Int) => (Int, Int, Int, Int),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

    apply(ApplyPixelSnapshot(snapshotBuffer), viewerHandling)
  }

  /**
   *
   *
   * @return
   */
  def createPixelSnapshot: PixelSnapshot =
    new PixelSnapshot(this)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def convertToGrayscaleByLuminocity(
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

    apply(ToGrayscaleByLuminocity(), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def convertToGrayscaleByLightness(
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

    apply(ToGrayscaleByLightness(), viewerHandling)
  }

  /**
   *
   *
   * @param redWeight
   * @param greenWeight
   * @param blueWeight
   * @param viewerHandling
   * @return
   */
  def convertToGrayscale(
    redWeight: Double,
    greenWeight: Double,
    blueWeight: Double,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

    apply(ToWeightedGrayscale(redWeight, greenWeight, blueWeight), viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def negate(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(Negate(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def unary_~(): ImmutableBitmap = negate()

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def negateRedComponent(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(NegateRedComponent(), viewerHandling)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def negateGreenComponent(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(NegateGreenComponent(), viewerHandling)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def negateBlueComponent(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(NegateBlueComponent(), viewerHandling)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def negateRedAndGreenComponents(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(NegateRedAndGreenComponents(), viewerHandling)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def negateRedAndBlueComponents(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(NegateRedAndBlueComponents(), viewerHandling)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def negateGreenAndBlueComponents(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(NegateGreenAndBlueComponents(), viewerHandling)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def keepOnlyRedComponent(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(KeepOnlyRedComponent(), viewerHandling)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def keepOnlyGreenComponent(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(KeepOnlyGreenComponent(), viewerHandling)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def keepOnlyBlueComponent(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(KeepOnlyBlueComponent(), viewerHandling)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def keepOnlyRedAndGreenComponents(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(KeepOnlyRedAndGreenComponents(), viewerHandling)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def keepOnlyRedAndBlueComponents(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(KeepOnlyRedAndBlueComponents(), viewerHandling)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def keepOnlyGreenAndBlueComponents(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(KeepOnlyGreenAndBlueComponents(), viewerHandling)

  /**
   *
   *
   * @param strengthAsPercentage
   * @param viewerHandling
   */
  def posterize(
    strengthAsPercentage: Int,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def drawLine(
    fromXInPixels: Int,
    fromYInPixels: Int,
    toXInPixels: Int,
    toYInPixels: Int,
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def drawPolyline(
    xCoordinates: Seq[Int],
    yCoordinates: Seq[Int],
    numberOfCoordinatesToDraw: Int,
    color: RGBAColor = GS.colorFor(DefaultPrimary),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def trim(
    colorToTrim: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def crop(
    windowTopLeftX: Int,
    windowTopLeftY: Int,
    windowBottomRightX: Int,
    windowBottomRightY: Int,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def augmentCanvas(
    extraPixelsOntoLeftEdge: Int = 0,
    extraPixelsOntoTopEdge: Int = 0,
    extraPixelsOntoRightEdge: Int = 0,
    extraPixelsOntoBottomEdge: Int = 0,
    color: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
    topBitmap: ImmutableBitmap,
    topBitmapUpperLeftX: Int,
    topBitmapUpperLeftY: Int,
    topBitmapOpacity: Int = ColorValidator.MaximumRgbaOpacity,
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

    apply(
      OverlayFreely(
        this,
        topBitmap,
        topBitmapUpperLeftX,
        topBitmapUpperLeftY,
        topBitmapOpacity,
        backgroundColor),
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
   * @return
   */
  def overlayOn(
    bottomBitmap: ImmutableBitmap,
    upperLeftX: Int,
    upperLeftY: Int,
    opacity: Int = ColorValidator.MaximumRgbaOpacity,
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

    apply(
      OverlayFreely(
        bottomBitmap,
        this,
        upperLeftX,
        upperLeftY,
        opacity,
        backgroundColor),
      viewerHandling)
  }


  // ----------------------------------------------------------------------------------------------

  /**
   *
   *
   * @param other
   * @return
   */
  def :|++|(other: ImmutableBitmap): ImmutableBitmap =
    overlayPerAlignments(this, other)(HorizontalAlignment.Left, VerticalAlignment.Top)

  /**
   *
   *
   * @param other
   * @return
   */
  def :|+*|(other: ImmutableBitmap): ImmutableBitmap =
    overlayPerAlignments(this, other)(HorizontalAlignment.Left, VerticalAlignment.Middle)

  /**
   *
   *
   * @param other
   * @return
   */
  def :|+-|(other: ImmutableBitmap): ImmutableBitmap =
    overlayPerAlignments(this, other)(HorizontalAlignment.Left, VerticalAlignment.Bottom)

  /**
   *
   *
   * @param other
   * @return
   */
  def :|*+|(other: ImmutableBitmap): ImmutableBitmap =
    overlayPerAlignments(this, other)(HorizontalAlignment.Center, VerticalAlignment.Top)

  /**
   *
   *
   * @param other
   * @return
   */
  def :|**|(other: ImmutableBitmap): ImmutableBitmap =
    overlayPerAlignments(this, other)(HorizontalAlignment.Center, VerticalAlignment.Middle)

  /**
   *
   *
   * @param other
   * @return
   */
  def :|*-|(other: ImmutableBitmap): ImmutableBitmap =
    overlayPerAlignments(this, other)(HorizontalAlignment.Center, VerticalAlignment.Bottom)

  /**
   *
   *
   * @param other
   * @return
   */
  def :|-+|(other: ImmutableBitmap): ImmutableBitmap =
    overlayPerAlignments(this, other)(HorizontalAlignment.Right, VerticalAlignment.Top)

  /**
   *
   *
   * @param other
   * @return
   */
  def :|-*|(other: ImmutableBitmap): ImmutableBitmap =
    overlayPerAlignments(this, other)(HorizontalAlignment.Right, VerticalAlignment.Middle)

  /**
   *
   *
   * @param other
   * @return
   */
  def :|--|(other: ImmutableBitmap): ImmutableBitmap =
    overlayPerAlignments(this, other)(HorizontalAlignment.Right, VerticalAlignment.Bottom)

  // ----------------------------------------------------------------------------------------------


  /**
   *
   *
   * @param bitmapsToLayOverThisFromBottomToTop
   * @param horizontalAlignment
   * @param verticalAlignment
   * @param opacityForAllBitmaps
   * @param backgroundColor
   * @return
   */
  def overlayPerAlignments(
    bitmapsToLayOverThisFromBottomToTop: ImmutableBitmap*)(
    horizontalAlignment: HorizontalAlignment.Value = GS.optionFor(DefaultHorizontalAlignment),
    verticalAlignment: VerticalAlignment.Value = GS.optionFor(DefaultVerticalAlignment),
    opacityForAllBitmaps: Int = ColorValidator.MaximumRgbaOpacity,
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

    apply(
      OverlayPerAlignments(this +: bitmapsToLayOverThisFromBottomToTop)(
        horizontalAlignment,
        verticalAlignment,
        opacityForAllBitmaps),
      viewerHandling)
  }

  /**
   *
   *
   * @param bitmapsToCombineWith
   * @param verticalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   * @return
   */
  def appendOnLeft(
    bitmapsToCombineWith: ImmutableBitmap*)(
    verticalAlignment: VerticalAlignment.Value = GS.optionFor(DefaultVerticalAlignment),
    paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

    apply(
      AppendHorizontally(bitmapsToCombineWith :+ this)(
        verticalAlignment,
        paddingInPixels,
        backgroundColor,
        ImmutableBitmap._bitmapValidator),
      viewerHandling)
  }

  /**
   *
   *
   * @param bitmapsToCombineWith
   * @param verticalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   * @return
   */
  def appendOnRight(
    bitmapsToCombineWith: ImmutableBitmap*)(
    verticalAlignment: VerticalAlignment.Value = GS.optionFor(DefaultVerticalAlignment),
    paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def sewOnLeftOf(bitmap: ImmutableBitmap): ImmutableBitmap =
    appendOnRight(bitmap)(VerticalAlignment.Middle)

  /**
   *
   *
   * @param bitmapsToCombineWith
   * @param horizontalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   * @return
   */
  def appendOnTop(
    bitmapsToCombineWith: ImmutableBitmap*)(
    horizontalAlignment: HorizontalAlignment.Value = GS.optionFor(DefaultHorizontalAlignment),
    paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def pileOnTopOf(bitmap: ImmutableBitmap): ImmutableBitmap =
    appendOnBottom(bitmap)(HorizontalAlignment.Center)

  /**
   *
   *
   * @param bitmapsToCombineWith
   * @param horizontalAlignment
   * @param paddingInPixels
   * @param backgroundColor
   * @return
   */
  def appendOnBottom(
    bitmapsToCombineWith: ImmutableBitmap*)(
    horizontalAlignment: HorizontalAlignment.Value = GS.optionFor(DefaultHorizontalAlignment),
    paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def flipHorizontally(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(FlipHorizontally(), viewerHandling)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def flipVertically(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(FlipVertically(), viewerHandling)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def flipDiagonally(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap =
    apply(FlipDiagonally(), viewerHandling)

  /**
   *
   *
   * @param scalingFactorHorizontal
   * @param scalingFactorVertical
   * @param resizeCanvasBasedOnTransformation
   * @param viewerHandling
   * @return
   */
  def scale(
    scalingFactorHorizontal: Double = 1.0,
    scalingFactorVertical: Double = 1.0,
    resizeCanvasBasedOnTransformation: Boolean = true,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def scale(scalingFactor: Double): ImmutableBitmap =
    scale(scalingFactor, scalingFactor)

  /**
   *
   *
   * @param scalingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param viewerHandling
   * @return
   */
  def scaleHorizontally(
    scalingFactor: Double,
    resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def scaleVertically(
    scalingFactor: Double,
    resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def shear(
    shearingFactorHorizontal: Double = 0.0,
    shearingFactorVertical: Double = 0.0,
    resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def shear(shearingFactor: Double): ImmutableBitmap =
    shear(shearingFactor, shearingFactor)

  /**
   *
   *
   * @param shearingFactor
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def shearHorizontally(
    shearingFactor: Double,
    resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def shearVertically(
    shearingFactor: Double,
    resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def rotateDegs(
    angleInDegrees: Double,
    resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def rotate90DegsCw(
    resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def rotate90DegsCcw(
    resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def rotate180Degs(
    resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def turn(
    resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

    rotate90DegsCw(resizeCanvasBasedOnTransformation, backgroundColor, viewerHandling)
  }

  /**
   *
   *
   * @param resizeCanvasBasedOnTransformation
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def unturn(
    resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

    rotate90DegsCcw(resizeCanvasBasedOnTransformation, backgroundColor, viewerHandling)
  }

  /**
   *
   *
   * @param numberOfReplicas
   * @param paddingInPixels
   * @param backgroundColor
   * @param viewerHandling
   * @return
   */
  def replicateHorizontally(
    numberOfReplicas: Int,
    paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def replicateVertically(
    numberOfReplicas: Int,
    paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): ImmutableBitmap = {

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
   * @return
   */
  def :/\(other: ImmutableBitmap): ImmutableBitmap = appendOnTop(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :/\(other: scala.collection.Seq[ImmutableBitmap]): ImmutableBitmap = appendOnTop(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :/\(other: scala.collection.Traversable[ImmutableBitmap]): ImmutableBitmap = :/\(other.toSeq)

  //-------------------------------
  //
  //  /\:
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   * @return
   */
  def /\:(other: ImmutableBitmap): ImmutableBitmap = appendOnTop(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def /\:(other: scala.collection.Seq[ImmutableBitmap]): ImmutableBitmap = appendOnTop(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def /\:(other: scala.collection.Traversable[ImmutableBitmap]): ImmutableBitmap = /\:(other.toSeq)

  //-------------------------------
  //
  //  :\/
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   * @return
   */
  def :\/(other: ImmutableBitmap): ImmutableBitmap = appendOnBottom(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :\/(other: scala.collection.Seq[ImmutableBitmap]): ImmutableBitmap = appendOnBottom(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :\/(other: scala.collection.Traversable[ImmutableBitmap]): ImmutableBitmap = :\/(other.toSeq)

  //-------------------------------
  //
  //  \/:
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   * @return
   */
  def \/:(other: ImmutableBitmap): ImmutableBitmap = appendOnBottom(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def \/:(other: scala.collection.Seq[ImmutableBitmap]): ImmutableBitmap = appendOnBottom(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def \/:(other: scala.collection.Traversable[ImmutableBitmap]): ImmutableBitmap = \/:(other.toSeq)

  //-------------------------------
  //
  //  :>>
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   * @return
   */
  def :>>(other: ImmutableBitmap): ImmutableBitmap = appendOnRight(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :>>(other: scala.collection.Seq[ImmutableBitmap]): ImmutableBitmap = appendOnRight(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :>>(other: scala.collection.Traversable[ImmutableBitmap]): ImmutableBitmap = :>>(other.toSeq)

  //-------------------------------
  //
  //  >>:
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   * @return
   */
  def >>:(other: ImmutableBitmap): ImmutableBitmap = appendOnRight(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def >>:(other: scala.collection.Seq[ImmutableBitmap]): ImmutableBitmap = appendOnRight(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def >>:(other: scala.collection.Traversable[ImmutableBitmap]): ImmutableBitmap = >>:(other.toSeq)

  //-------------------------------
  //
  //  :<<
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   * @return
   */
  def :<<(other: ImmutableBitmap): ImmutableBitmap = appendOnLeft(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :<<(other: scala.collection.Seq[ImmutableBitmap]): ImmutableBitmap = appendOnLeft(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :<<(other: scala.collection.Traversable[ImmutableBitmap]): ImmutableBitmap = :<<(other.toSeq)

  //-------------------------------
  //
  //  <<:
  //
  //-------------------------------

  /**
   *
   *
   * @param other
   * @return
   */
  def <<:(other: ImmutableBitmap): ImmutableBitmap = appendOnLeft(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def <<:(other: scala.collection.Seq[ImmutableBitmap]): ImmutableBitmap = appendOnLeft(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def <<:(other: scala.collection.Traversable[ImmutableBitmap]): ImmutableBitmap = <<:(other.toSeq)

  // ----------------------------------------------------------------------------------------------


  /**
   * Renders this [[ImmutableBitmap]] onto a drawing surface using specified coordinates.
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
   * Renders this [[ImmutableBitmap]] onto a drawing surface using specified affine transformation.
   *
   * @param drawingSurface
   * @param transformation
   */
  def renderOnto(drawingSurface: DrawingSurfaceAdapter, transformation: AffineTransformation): Unit = {
    require(drawingSurface != null, "Drawing surface argument cannot be null.")

    val rendition = toRenderedRepresentation
    drawingSurface.drawBitmap(rendition, transformation)
  }

  /**
   * Returns a `BufferedImage` instance representing this [[ImmutableBitmap]].
   */
  def toRenderedRepresentation: BitmapBufferAdapter =
    _renderingBuffer.get getOrElse {
      val rendition = operations.render()

      _renderingBuffer = WeakReference[BitmapBufferAdapter](rendition)

      return rendition
    }

  /**
   * Returns a mutable `ArrayBuffer` containing a given number of copies of this [[ImmutableBitmap]] instance.
   *
   * @param size
   * @return
   */
  private def propagateToArrayBuffer(size: Int): mutable.ArrayBuffer[ImmutableBitmap] = {
    require(size >= 0, s"Size of the collection cannot be negative (was $size)")

    mutable.ArrayBuffer.fill[ImmutableBitmap](size)(this)
  }

  /**
   * Returns an `Array` containing a given number of copies of this [[ImmutableBitmap]] instance.
   *
   * @param size
   * @return
   */
  def propagateToArray(size: Int): Array[ImmutableBitmap] = propagateToArrayBuffer(size).toArray

  /**
   * Returns an `List` containing a given number of copies of this [[ImmutableBitmap]] instance.
   *
   * @param size
   * @return
   */
  def propagateToList(size: Int): List[ImmutableBitmap] = propagateToArrayBuffer(size).toList

  /**
   * Returns an `Seq` containing a given number of copies of this [[ImmutableBitmap]] instance.
   *
   * @param size
   * @return
   */
  def propagateToSeq(size: Int): Seq[ImmutableBitmap] = propagateToArrayBuffer(size).toSeq

  /**
   *
   */
  def display(): ImmutableBitmap = {
    displayInViewer(this)

    this
  }

}
