package aalto.smcl.bitmaps.immutable.primitives


import scala.collection.mutable
import scala.ref.WeakReference

import aalto.smcl.SMCL
import aalto.smcl.bitmaps.BitmapSettingKeys._
import aalto.smcl.bitmaps.immutable.primitives.Bitmap.ViewerUpdateStyle
import aalto.smcl.bitmaps.immutable.primitives.Bitmap.ViewerUpdateStyle.UpdateViewerPerDefaults
import aalto.smcl.bitmaps.immutable.{ConvolutionKernel, BitmapIdentity, PixelRectangle}
import aalto.smcl.bitmaps.operations._
import aalto.smcl.bitmaps.{display => displayInViewer, _}
import aalto.smcl.common._
import aalto.smcl.platform.{ImageProvider, PlatformBitmapBuffer, PlatformDrawingSurface, RenderableBitmap}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Bitmap {

  SMCL.performInitialization()


  /**
   *
   */
  object ViewerUpdateStyle {


    /**
     *
     */
    abstract sealed class Value


    /**
     *
     */
    case object UpdateViewerPerDefaults extends Value


    /**
     *
     */
    case object PreventViewerUpdates extends Value


  }


  /**
   * Creates a new empty [[aalto.smcl.bitmaps.immutable.primitives.Bitmap]] instance.
   */
  def apply(
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      initialBackgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    BitmapValidator.validateBitmapSize(widthInPixels, heightInPixels)

    if (BitmapValidator.warningSizeLimitsAreExceeded(widthInPixels, heightInPixels)) {
      println("\n\nWarning: The image is larger than the recommended maximum size")
    }

    require(initialBackgroundColor != null, "The background color argument has to be a Color instance (was null).")

    val operationList =
      Clear(initialBackgroundColor) +:
          BitmapOperationList(CreateBitmap(widthInPixels, heightInPixels))

    val newBitmap = new Bitmap(operationList, BitmapIdentity())

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
      viewerHandling: ViewerUpdateStyle.Value): collection.Seq[Either[Throwable, Bitmap]] = {

    // The ImageProvider is trusted with validation of the source resource path.
    val loadedBuffersTry = ImageProvider.tryToLoadImagesFromFile(sourceResourcePath)
    if (loadedBuffersTry.isFailure)
      throw loadedBuffersTry.failed.get

    val bitmapsOrThrowables: collection.Seq[Either[Throwable, Bitmap]] =
      loadedBuffersTry.get.zipWithIndex.map {
        case (Left(t), _) =>
          Left(t)

        case (Right(buffer), index) =>
          val operationList = BitmapOperationList(LoadedBitmap(buffer, Option(sourceResourcePath), Option(index)))
          val newBitmap = new Bitmap(operationList, BitmapIdentity())

          if (viewerHandling == UpdateViewerPerDefaults) {
            if (GS.isTrueThat(NewBitmapsAreDisplayedAutomatically))
              newBitmap.display()
          }

          Right(newBitmap)
      }

    bitmapsOrThrowables
  }

  /**
   *
   *
   * @param sourceResourcePath
   * @return
   */
  def apply(sourceResourcePath: String): collection.Seq[Either[Throwable, Bitmap]] =
    apply(sourceResourcePath, UpdateViewerPerDefaults)

}


/**
 *
 *
 * @param operations
 * @param uniqueIdentifier
 *
 * @author Aleksi Lukkarinen
 */
case class Bitmap private(
    private[bitmaps] val operations: BitmapOperationList,
    uniqueIdentifier: BitmapIdentity) extends {

  /** Width of this [[Bitmap]]. */
  val widthInPixels: Int = operations.widthInPixels

  /** Height of this [[Bitmap]]. */
  val heightInPixels: Int = operations.heightInPixels

  /** Rendering buffer for this image. */
  private[this] var _renderingBuffer: WeakReference[PlatformBitmapBuffer] =
    WeakReference[PlatformBitmapBuffer](null)

} with RenderableBitmap
       with PixelRectangle
       with OperableBitmap
       with Immutable
       with TimestampedCreation {

  /**
   * Returns the initial background color of this [[Bitmap]]
   * (may not be the actual background color at a later time).
   */
  val initialBackgroundColor: RGBAColor = operations.initialBackgroundColor()

  /**
   * Applies an [[Renderable]] to this [[Bitmap]].
   *
   * @param newOperation
   * @param viewerHandling
   * @return
   */
  private[bitmaps] def apply(
      newOperation: Renderable,
      viewerHandling: ViewerUpdateStyle.Value): Bitmap = {

    require(newOperation != null, "Operation argument cannot be null.")

    val newBitmap = copy(operations = newOperation +: operations)

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (GS.isTrueThat(DisplayBitmapsAutomaticallyAfterOperations))
        newBitmap.display()
    }

    newBitmap
  }

  /**
   * Applies an [[BufferProvider]] to this [[Bitmap]].
   *
   * @param newOperation
   * @param viewerHandling
   * @return
   */
  private[bitmaps] def apply(
      newOperation: BufferProvider,
      viewerHandling: ViewerUpdateStyle.Value): Bitmap = {

    require(newOperation != null, "Operation argument cannot be null.")

    val newOperationList = BitmapOperationList(newOperation)
    val newBitmap = copy(operations = newOperationList)

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (GS.isTrueThat(DisplayBitmapsAutomaticallyAfterOperations))
        newBitmap.display()
    }

    newBitmap
  }

  /**
   *
   *
   * @return
   */
  def aspectRatio(): (Double, Double) = {
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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

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
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(FilterWithComponentTranslationTable(translator), viewerHandling)
  }

  /**
   *
   *
   * @return
   */
  def negate(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(Negate(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def negateRedComponent(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(NegateRedComponent(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def negateGreenComponent(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(NegateGreenComponent(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def negateBlueComponent(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(NegateBlueComponent(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def negateRedAndGreenComponents(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(NegateRedAndGreenComponents(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def negateRedAndBlueComponents(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(NegateRedAndBlueComponents(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def negateGreenAndBlueComponents(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(NegateGreenAndBlueComponents(), viewerHandling)


  /**
   *
   *
   * @return
   */
  def keepOnlyRedComponent(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(KeepOnlyRedComponent(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def keepOnlyGreenComponent(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(KeepOnlyGreenComponent(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def keepOnlyBlueComponent(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(KeepOnlyBlueComponent(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def keepOnlyRedAndGreenComponents(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(KeepOnlyRedAndGreenComponents(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def keepOnlyRedAndBlueComponents(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(KeepOnlyRedAndBlueComponents(), viewerHandling)

  /**
   *
   *
   * @return
   */
  def keepOnlyGreenAndBlueComponents(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(KeepOnlyGreenAndBlueComponents(), viewerHandling)

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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawLine(
      fromXInPixels, fromYInPixels,
      toXInPixels, toYInPixels,
      color), viewerHandling)
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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawPolyline(
      xCoordinates, yCoordinates,
      numberOfCoordinatesToDraw,
      color), viewerHandling)
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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawPolygon(
      xCoordinates, yCoordinates,
      numberOfCoordinatesToDraw,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawSquare(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      sideLengthInPixels,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawRectangle(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawRoundedSquare(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      sideLengthInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawRoundedRectangle(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawCircle(
      centerXInPixels, centerYInPixels,
      radiusInPixels,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
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
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawEllipse(
      centerXInPixels, centerYInPixels,
      widthInPixels, heightInPixels,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
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
      arcAngleInDegrees: Int = GS.intFor(DefaultArcAngleInDgrees),
      hasBorder: Boolean = GS.isTrueThat(ShapesHaveBordersByDefault),
      hasFilling: Boolean = GS.isTrueThat(ShapesHaveFillingsByDefault),
      color: RGBAColor = GS.colorFor(DefaultPrimary),
      fillColor: RGBAColor = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawArc(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels,
      startAngleInDegrees, arcAngleInDegrees,
      hasBorder, hasFilling,
      color, fillColor), viewerHandling)
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
      bitmapsToCombineWith: Bitmap*)(
      verticalAlignment: VerticalAlignment.Value = GS.optionFor(DefaultVerticalAlignment),
      paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(
      AppendHorizontally(bitmapsToCombineWith :+ this)(
        verticalAlignment, paddingInPixels, backgroundColor),
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
      bitmapsToCombineWith: Bitmap*)(
      verticalAlignment: VerticalAlignment.Value = GS.optionFor(DefaultVerticalAlignment),
      paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(
      AppendHorizontally(this +: bitmapsToCombineWith)(
        verticalAlignment, paddingInPixels, backgroundColor),
      viewerHandling)
  }

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
      bitmapsToCombineWith: Bitmap*)(
      horizontalAlignment: HorizontalAlignment.Value = GS.optionFor(DefaultHorizontalAlignment),
      paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(
      AppendVertically(bitmapsToCombineWith :+ this)(
        horizontalAlignment, paddingInPixels, backgroundColor),
      viewerHandling)
  }

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
      bitmapsToCombineWith: Bitmap*)(
      horizontalAlignment: HorizontalAlignment.Value = GS.optionFor(DefaultHorizontalAlignment),
      paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
      backgroundColor: RGBAColor = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(
      AppendVertically(this +: bitmapsToCombineWith)(
        horizontalAlignment, paddingInPixels, backgroundColor),
      viewerHandling)
  }

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def flipHorizontally(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(FlipHorizontally(), viewerHandling)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def flipVertically(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(FlipVertically(), viewerHandling)

  /**
   *
   *
   * @param viewerHandling
   * @return
   */
  def flipDiagonally(viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap =
    apply(FlipDiagonally(), viewerHandling)


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
  def :/\ (other: Bitmap): Bitmap = appendOnTop(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :/\ (other: scala.collection.Seq[Bitmap]): Bitmap = appendOnTop(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :/\ (other: scala.collection.Traversable[Bitmap]): Bitmap = :/\(other.toSeq)

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
  def /\: (other: Bitmap): Bitmap = appendOnTop(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def /\: (other: scala.collection.Seq[Bitmap]): Bitmap = appendOnTop(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def /\: (other: scala.collection.Traversable[Bitmap]): Bitmap = /\:(other.toSeq)

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
  def :\/ (other: Bitmap): Bitmap = appendOnBottom(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :\/ (other: scala.collection.Seq[Bitmap]): Bitmap = appendOnBottom(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :\/ (other: scala.collection.Traversable[Bitmap]): Bitmap = :\/(other.toSeq)

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
  def \/: (other: Bitmap): Bitmap = appendOnBottom(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def \/: (other: scala.collection.Seq[Bitmap]): Bitmap = appendOnBottom(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def \/: (other: scala.collection.Traversable[Bitmap]): Bitmap = \/:(other.toSeq)

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
  def :>> (other: Bitmap): Bitmap = appendOnRight(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :>> (other: scala.collection.Seq[Bitmap]): Bitmap = appendOnRight(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :>> (other: scala.collection.Traversable[Bitmap]): Bitmap = :>>(other.toSeq)

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
  def >>: (other: Bitmap): Bitmap = appendOnRight(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def >>: (other: scala.collection.Seq[Bitmap]): Bitmap = appendOnRight(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def >>: (other: scala.collection.Traversable[Bitmap]): Bitmap = >>:(other.toSeq)

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
  def :<< (other: Bitmap): Bitmap = appendOnLeft(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :<< (other: scala.collection.Seq[Bitmap]): Bitmap = appendOnLeft(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def :<< (other: scala.collection.Traversable[Bitmap]): Bitmap = :<<(other.toSeq)

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
  def <<: (other: Bitmap): Bitmap = appendOnLeft(other)()

  /**
   *
   *
   * @param other
   * @return
   */
  def <<: (other: scala.collection.Seq[Bitmap]): Bitmap = appendOnLeft(other: _*)()

  /**
   *
   *
   * @param other
   * @return
   */
  def <<: (other: scala.collection.Traversable[Bitmap]): Bitmap = <<:(other.toSeq)

  // ----------------------------------------------------------------------------------------------


  /**
   * Renders this [[Bitmap]] onto a drawing surface using specified coordinates.
   *
   * @param drawingSurface
   * @param x
   * @param y
   */
  def renderOnto(drawingSurface: PlatformDrawingSurface, x: Int, y: Int): Unit = {
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
  def renderOnto(drawingSurface: PlatformDrawingSurface, transformation: AffineTransformation): Unit = {
    require(drawingSurface != null, "Drawing surface argument cannot be null.")

    val rendition = toRenderedRepresentation
    drawingSurface.drawBitmap(_renderingBuffer.apply(), transformation)
  }

  /**
   * Returns a `BufferedImage` instance representing this [[Bitmap]].
   */
  def toRenderedRepresentation: PlatformBitmapBuffer =
    _renderingBuffer.get getOrElse {
      val rendition = operations.render()

      _renderingBuffer = WeakReference[PlatformBitmapBuffer](rendition)

      return rendition
    }

  /**
   * Returns a mutable `ArrayBuffer` containing a given number of copies of this [[Bitmap]] instance.
   *
   * @param size
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
   * @return
   */
  def propagateToArray(size: Int): Array[Bitmap] = propagateToArrayBuffer(size).toArray

  /**
   * Returns an `List` containing a given number of copies of this [[Bitmap]] instance.
   *
   * @param size
   * @return
   */
  def propagateToList(size: Int): List[Bitmap] = propagateToArrayBuffer(size).toList

  /**
   * Returns an `Seq` containing a given number of copies of this [[Bitmap]] instance.
   *
   * @param size
   * @return
   */
  def propagateToSeq(size: Int): Seq[Bitmap] = propagateToArrayBuffer(size).toSeq

  /**
   *
   */
  def display(): Bitmap = {
    displayInViewer(this)

    this
  }

}
