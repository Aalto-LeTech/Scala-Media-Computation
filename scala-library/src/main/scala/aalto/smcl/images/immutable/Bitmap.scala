package aalto.smcl.images.immutable


import java.awt.image.{BufferedImage => JBufferedImage}
import java.awt.{Color => JColor, Graphics2D => JGraphics2D}
import java.util.UUID

import scala.ref.WeakReference

import aalto.smcl.common._
import aalto.smcl.images.operations._
import aalto.smcl.images.{display => displayInViewer, _}




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
      initialBackgroundColorOption: Option[Color] = None): Bitmap = {

    val width = widthInPixelsOption.fold(DEFAULT_IMAGE_WIDTH_IN_PIXELS) {w =>
      require(w > 0, s"Width of the image must be greater than zero (was $w)")
      w
    }

    val height = heightInPixelsOption.fold(DEFAULT_IMAGE_HEIGHT_IN_PIXELS) {h =>
      require(h > 0, s"Height of the image must be greater than zero (was $h)")
      h
    }

    val bgColor = initialBackgroundColorOption getOrElse NamedColors.white
    val operationList = Clear(Option(bgColor)) +: BitmapOperationList(CreateBitmap(width, height))

    new Bitmap(operationList, UUID.randomUUID()).display()
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
  def apply(newOperation: AbstractSingleSourceOperation): Bitmap = {
    require(newOperation != null, "Operation argument cannot be null.")

    copy(operations = newOperation +: operations).display()
  }

  /**
   *
   * @param colorOption
   */
  def clear(colorOption: Option[Color]): Bitmap = {
    require(colorOption != null, "Color argument cannot be null.")

    apply(Clear(colorOption))
  }

  /**
   * Renders this [[Bitmap]] onto a drawing surface.
   */
  def render(drawingSurface: JGraphics2D, x: Int, y: Int): Unit = {
    toRenderedRepresentation
    drawingSurface.drawImage(_renderingBuffer.apply(), null, x, y)
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
