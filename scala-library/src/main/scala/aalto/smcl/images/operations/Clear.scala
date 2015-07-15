package aalto.smcl.images.operations

import java.awt.{
  Color => JColor,
  Graphics2D => JGraphics2D
}
import java.awt.image.{ BufferedImage => JBufferedImage }
import aalto.smcl.images._

/**
 * Operation to clear a bitmap with a given color. If a color is not given, a pure white will be used.
 *
 * @author Aleksi Lukkarinen
 */
case class Clear(private val colorOption: Option[Int] = None) extends AbstractSingleSourceOperation {

  /** The color with which to clear bitmaps. */
  private[this] val _color: Int = colorOption getOrElse 0xFFFFFFFF

  /** This [[BitmapOperation]] does not have any child operations. */
  val childOperationListsOption: Option[Array[BitmapOperationList]] = None

  /** Information about this operation instance */
  val metaInformation = Map(
    "name" -> "Clear",
    "backgroundcolor" -> "0x${_color.toArgbHexColorString}"
  )

  /**
   * Clears the given bitmap with the given color.
   */
  override def render(destination: JBufferedImage): Unit = {
    val drawingSurface = destination.createGraphics()
    val oldColor = drawingSurface.getColor

    drawingSurface.setColor(new JColor(_color, true))
    drawingSurface.fillRect(0, 0, destination.getWidth, destination.getHeight)
    drawingSurface.setColor(oldColor)
  }

}
