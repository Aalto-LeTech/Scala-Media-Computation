package aalto.smcl.images.immutable

import java.awt.{
  Color => JColor,
  Graphics2D => JGraphics2D
}
import java.awt.image.{ BufferedImage => JBufferedImage }
import scala.ref.WeakReference
import aalto.smcl._
import aalto.smcl.common._
import aalto.smcl.images._
import aalto.smcl.images.operations._

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Bitmap {

  /**
   * Creates a new empty [[Bitmap]] instance.
   */
  def apply(
    widthInPixelsOption: Option[Int] = None,
    heightInPixelsOption: Option[Int] = None,
    initialBackgroundColorOption: Option[Int] = None): Bitmap = {

    val width = widthInPixelsOption.fold(DEFAULT_IMAGE_WIDTH_IN_PIXELS) { w =>
      require(w > 0, s"Width of the image must be greater than zero (was $w)")
      w
    }

    val height = heightInPixelsOption.fold(DEFAULT_IMAGE_HEIGHT_IN_PIXELS) { h =>
      require(h > 0, s"Height of the image must be greater than zero (was $h)")
      h
    }

    val bgColor = initialBackgroundColorOption getOrElse 0xFFFFFFFF
    val operationList = Clear(Option(bgColor)) +: BitmapOperationList(CreateBitmap(width, height))

    new Bitmap(operationList, width, height)
  }

  /**
   *
   */
  //  def apply(sourceFilePath: String): Bitmap = {
  //
  //    // TODO: Load image from the given file and init the Bitmap accordingly
  //    val width = 10
  //    val height = 10
  //    val operationList = BitmapOperationList()
  //
  //    new Bitmap(operationList, width, height)
  //  }

}

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class Bitmap private (
  private val operations: BitmapOperationList,
  val widthInPixels: Int,
  val heightInPixels: Int) extends {

  /** Rendering buffer for this image. */
  private[this] var _renderingBuffer: WeakReference[JBufferedImage] =
    WeakReference[JBufferedImage](null)

} with RenderableBitmap
    with PixelRectangle
    with ColorableBackground
    with OperableBitmap
    with Immutable
    with TimestampedCreation {

  /**
   * Returns the initial background color of this [[Bitmap]]
   * (may not be the actual background color at a later time).
   */
  val initialBackgroundColor: Int = operations.initialBackgroundColor()

  /**
   * Returns a copy of this [[Bitmap]].
   */
  def copy(): Bitmap = {
    new Bitmap(operations, widthInPixels, heightInPixels)
  }

  /**
   * Applies a [[BitmapOperation]] to this [[Bitmap]].
   */
  def apply(operation: BitmapOperation with SingleSource): Bitmap = {
    new Bitmap(operation +: operations, widthInPixels, heightInPixels)
  }

  /**
   * Renders this [[Bitmap]] onto a drawing surface.
   */
  def render(drawingSurface: JGraphics2D, x: Int, y: Int): Unit = {
    toRenderedRepresentation()
    drawingSurface.drawImage(_renderingBuffer.apply, null, x, y)
  }

  /**
   * Returns a `BufferedImage` instance representing this [[Bitmap]].
   */
  def toRenderedRepresentation(): JBufferedImage =
    _renderingBuffer.get getOrElse {
      val renderation = operations.render()

      _renderingBuffer = WeakReference[JBufferedImage](renderation)

      return renderation
    }

  /**
   * Returns a string representation of this [[Bitmap]].
   */
  override def toString() = {
    s"[BitmapImage ${widthInPixels}x${heightInPixels} px" +
      s"; created: ${created}]"
  }

}
