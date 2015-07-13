package aalto.smcl.images.immutable

import java.awt.{
  Color => JColor,
  Graphics2D => JGraphics2D
}
import java.awt.image.{ BufferedImage => JBufferedImage }
import scala.collection.immutable
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
    val operationList = List[BitmapOperation]() :+ CreateBitmap(width, height) :+ Clear(Option(bgColor))

    new Bitmap(operationList, width, height)
  }

  /**
   *
   */
  def apply(sourceFilePath: String): Bitmap = {

    // TODO: Load image from the given file and init the Bitmap accordingly
    val width = 10
    val height = 10
    val operationList = List[BitmapOperation]()

    new Bitmap(operationList, width, height)
  }

}

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class Bitmap private (
  private val operationList: immutable.List[BitmapOperation],
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
  override def initialBackgroundColor(): Int =
    operationList.head match {
      case Clear(color) => color getOrElse 0xFFFFFFFF
      case _            => 0xFFFFFFFF
    }

  /**
   * Returns a copy of this [[Bitmap]].
   */
  def copy(): Bitmap = {
    new Bitmap(operationList, widthInPixels, heightInPixels)
  }

  /**
   * Applies a [[BitmapOperation]] to this [[Bitmap]].
   */
  override def apply(operation: BitmapOperation): Bitmap = {
    new Bitmap(operationList :+ operation, widthInPixels, heightInPixels)
  }

  /**
   * Renders this [[Bitmap]] onto a drawing surface.
   */
  def render(drawingSurface: JGraphics2D, x: Int, y: Int): Unit = {
    ensureThatBufferIsRendered()
    drawingSurface.drawImage(_renderingBuffer.apply, null, x, y)
  }

  /**
   * Creates the rendering buffer, if it does not exist, and renders this [[Bitmap]] onto it.
   */
  private[this] def ensureThatBufferIsRendered(): Unit =
    if (_renderingBuffer.get.isEmpty) {
      val buffer = new JBufferedImage(widthInPixels, heightInPixels, JBufferedImage.TYPE_INT_ARGB)
      val drawingSurface = buffer.createGraphics

      _renderingBuffer = WeakReference[JBufferedImage](buffer)

      operationList.foreach { operation =>
        operation.apply(this, drawingSurface, widthInPixels, heightInPixels)
      }
    }

  /**
   * Returns a string representation of this [[Bitmap]].
   */
  override def toString() = {
    s"[BitmapImage ${widthInPixels}x${heightInPixels} px" +
      s"; created: ${created}]"
  }

}
