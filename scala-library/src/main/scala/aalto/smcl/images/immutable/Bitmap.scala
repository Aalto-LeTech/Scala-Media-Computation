package aalto.smcl.images.immutable


import java.awt.geom.AffineTransform
import java.awt.image.{BufferedImage => JBufferedImage}
import java.awt.{Color => JColor, Graphics2D => JGraphics2D}
import java.util.UUID

import scala.ref.WeakReference

import aalto.smcl.common._
import aalto.smcl.images.SettingKeys._
import aalto.smcl.images.immutable.Bitmap.ViewerUpdateStyle
import aalto.smcl.images.immutable.Bitmap.ViewerUpdateStyle.UpdateViewerPerDefaults
import aalto.smcl.images.operations._
import aalto.smcl.images.{display => displayInViewer, _}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Bitmap {

  aalto.smcl.images.SettingsInitializer.perform()


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
   * Creates a new empty [[Bitmap]] instance.
   */
  def apply(
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      initialBackgroundColor: Color = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(widthInPixels >= 10, s"Width of the image must be at least 10 pixels (was $widthInPixels)")
    require(heightInPixels >= 10, s"Height of the image must be at least 10 pixels (was $heightInPixels)")

    val operationList =
      Clear(initialBackgroundColor) +:
          BitmapOperationList(CreateBitmap(widthInPixels, heightInPixels))

    val newBitmap = new Bitmap(operationList, UUID.randomUUID())

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (GS.isTrueThat(NewBitmapsAreDisplayedAutomatically))
        newBitmap.display()
    }

    newBitmap
  }


  /**
   *
   */
  //  def apply(sourceFilePath: String): Bitmap = {
  //
  //    // TODO: Load image from the given file and init the Bitmap accordingly
  //    val operationList = BitmapOperationList()
  //
  //    new Bitmap(operationList)
  //  }

}


/**
 *
 *
 * @param operations
 * @param id
 *
 * @author Aleksi Lukkarinen
 */
case class Bitmap private(
    private val operations: BitmapOperationList,
    id: UUID) extends {

  /** Width of this [[Bitmap]]. */
  val widthInPixels: Int = operations.widthInPixels

  /** Height of this [[Bitmap]]. */
  val heightInPixels: Int = operations.heightInPixels

  /** Rendering buffer for this image. */
  private[this] var _renderingBuffer: WeakReference[JBufferedImage] =
    WeakReference[JBufferedImage](null)

} with RenderableBitmap
       with PixelRectangle
       with OperableBitmap
       with Immutable
       with TimestampedCreation {

  /**
   * Returns the initial background color of this [[Bitmap]]
   * (may not be the actual background color at a later time).
   */
  val initialBackgroundColor: Color = operations.initialBackgroundColor()

  /**
   * Applies an [[AbstractSingleSourceOperation]] to this [[Bitmap]].
   *
   * @param newOperation
   * @param viewerHandling
   * @return
   */
  private[images] def apply(
      newOperation: AbstractSingleSourceOperation,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    require(newOperation != null, "Operation argument cannot be null.")

    val newBitmap = copy(operations = newOperation +: operations)

    if (viewerHandling == UpdateViewerPerDefaults) {
      if (GS.isTrueThat(DisplayBitmapsAutomaticallyAfterOperations))
        newBitmap.display()
    }

    newBitmap
  }

  /**
   *
   *
   * @param color
   * @param viewerHandling
   * @return
   */
  def clear(
      color: Color = GS.colorFor(DefaultBackground),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(Clear(color), viewerHandling)
  }

  /**
   *
   *
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param sideLengthInPixels
   * @param isFilled
   * @param lineColor
   * @param fillColor
   * @param viewerHandling
   * @return
   */
  def drawSquare(
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      isFilled: Boolean = false,
      lineColor: Color = GS.colorFor(DefaultPrimary),
      fillColor: Color = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawSquare(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      sideLengthInPixels,
      isFilled, lineColor, fillColor), viewerHandling)
  }

  /**
   *
   *
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param widthInPixels
   * @param heightInPixels
   * @param isFilled
   * @param lineColor
   * @param fillColor
   * @param viewerHandling
   * @return
   */
  def drawRectangle(
      upperLeftCornerXInPixels: Int,
      upperLeftCornerYInPixels: Int,
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      isFilled: Boolean = false,
      lineColor: Color = GS.colorFor(DefaultPrimary),
      fillColor: Color = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawRectangle(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels,
      isFilled, lineColor, fillColor), viewerHandling)
  }

  /**
   *
   *
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param sideLengthInPixels
   * @param roundingWidthInPixels
   * @param roundingHeightInPixels
   * @param isFilled
   * @param lineColor
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
      isFilled: Boolean = false,
      lineColor: Color = GS.colorFor(DefaultPrimary),
      fillColor: Color = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawRoundedSquare(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      sideLengthInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      isFilled, lineColor, fillColor), viewerHandling)
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
   * @param isFilled
   * @param lineColor
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
      isFilled: Boolean = false,
      lineColor: Color = GS.colorFor(DefaultPrimary),
      fillColor: Color = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawRoundedRectangle(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      isFilled, lineColor, fillColor), viewerHandling)
  }

  /**
   *
   *
   * @param centerXInPixels
   * @param centerYInPixels
   * @param radiusInPixels
   * @param isFilled
   * @param lineColor
   * @param fillColor
   * @param viewerHandling
   * @return
   */
  def drawCircle(
      centerXInPixels: Int,
      centerYInPixels: Int,
      radiusInPixels: Int = GS.intFor(DefaultCircleRadiusInPixels),
      isFilled: Boolean = false,
      lineColor: Color = GS.colorFor(DefaultPrimary),
      fillColor: Color = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawCircle(
      centerXInPixels, centerYInPixels,
      radiusInPixels,
      isFilled, lineColor, fillColor), viewerHandling)
  }

  /**
   *
   *
   * @param centerXInPixels
   * @param centerYInPixels
   * @param widthInPixels
   * @param heightInPixels
   * @param isFilled
   * @param lineColor
   * @param fillColor
   * @param viewerHandling
   * @return
   */
  def drawEllipse(
      centerXInPixels: Int,
      centerYInPixels: Int,
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      isFilled: Boolean = false,
      lineColor: Color = GS.colorFor(DefaultPrimary),
      fillColor: Color = GS.colorFor(DefaultSecondary),
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap = {

    apply(DrawEllipse(
      centerXInPixels, centerYInPixels,
      widthInPixels, heightInPixels,
      isFilled, lineColor, fillColor), viewerHandling)
  }

  /**
   * Renders this [[Bitmap]] onto a drawing surface using specified coordinates.
   *
   * @param drawingSurface
   * @param x
   * @param y
   */
  def renderOnto(drawingSurface: JGraphics2D, x: Int, y: Int): Unit = {
    require(drawingSurface != null, "Drawing surface argument cannot be null.")

    toRenderedRepresentation
    drawingSurface.drawImage(_renderingBuffer.apply(), null, x, y)
  }

  /**
   * Renders this [[Bitmap]] onto a drawing surface using specified affine transformation.
   *
   * @param drawingSurface
   * @param affineTransformation
   */
  def renderOnto(drawingSurface: JGraphics2D, affineTransformation: AffineTransform): Unit = {
    require(drawingSurface != null, "Drawing surface argument cannot be null.")

    toRenderedRepresentation
    drawingSurface.drawImage(_renderingBuffer.apply(), affineTransformation, null)
  }

  /**
   * Returns a `BufferedImage` instance representing this [[Bitmap]].
   */
  def toRenderedRepresentation: JBufferedImage =
    _renderingBuffer.get getOrElse {
      val rendition = operations.render()

      _renderingBuffer = WeakReference[JBufferedImage](rendition)

      return rendition
    }

  /**
   *
   */
  def display(): Bitmap = {
    displayInViewer(this)

    this
  }

}
