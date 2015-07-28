package aalto.smcl.images.immutable


import java.awt.geom.AffineTransform
import java.awt.image.{BufferedImage => JBufferedImage}
import java.awt.{Color => JColor, Graphics2D => JGraphics2D}
import java.util.UUID

import scala.ref.WeakReference

import aalto.smcl.common._
import aalto.smcl.images.SettingKeys._
import aalto.smcl.images.operations._
import aalto.smcl.images.{display => displayInViewer, _}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Bitmap {

  aalto.smcl.images.SettingsManager.initializeSettings()

  /**
   * Creates a new empty [[Bitmap]] instance.
   */
  def apply(
      widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
      heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
      initialBackgroundColor: Color = GS.colorFor(DefaultBackground)): Bitmap = {

    require(widthInPixels > 10, s"Width of the image must be at least 10 pixels (was $widthInPixels)")
    require(heightInPixels > 10, s"Height of the image must be at least 10 pixels (was $heightInPixels)")

    val operationList =
      Clear(initialBackgroundColor) +:
          BitmapOperationList(CreateBitmap(widthInPixels, heightInPixels))

    val newBitmap = new Bitmap(operationList, UUID.randomUUID())

    if (GS.isTrueThat(NewBitmapsAreDisplayedAutomatically))
      newBitmap.display()

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
 * @author Aleksi Lukkarinen
 */
case class Bitmap private(private val operations: BitmapOperationList, id: UUID) extends {

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
   */
  private[images] def apply(newOperation: AbstractSingleSourceOperation): Bitmap = {
    require(newOperation != null, "Operation argument cannot be null.")

    val newBitmap = copy(operations = newOperation +: operations)

    if (GS.isTrueThat(DisplayBitmapsAutomaticallyAfterOperations))
      newBitmap.display()

    newBitmap
  }

  /**
   *
   *
   * @param color
   */
  def clear(color: Color = GS.colorFor(DefaultBackground)): Bitmap = {
    require(color != null, "The color argument has to be a Color instance (was null).")
    apply(Clear(color))
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
   * @return
   */
  def drawCircle(
      centerXInPixels: Int,
      centerYInPixels: Int,
      radiusInPixels: Int,
      isFilled: Boolean,
      lineColor: Color = GS.colorFor(DefaultPrimary),
      fillColor: Color = GS.colorFor(DefaultSecondary)): Bitmap = {

    require(lineColor != null, "The line color argument has to be a Color instance (was null).")
    require(fillColor != null, "The fill color argument has to be a Color instance (was null).")

    apply(DrawCircle(centerXInPixels, centerYInPixels, radiusInPixels, isFilled, lineColor, fillColor))
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
