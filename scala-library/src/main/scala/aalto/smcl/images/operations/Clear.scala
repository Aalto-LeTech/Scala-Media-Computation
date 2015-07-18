package aalto.smcl.images.operations


import java.awt.image.{BufferedImage => JBufferedImage}
import java.awt.{Color => JColor, Graphics2D => JGraphics2D}

import aalto.smcl.common._
import aalto.smcl.images.immutable._




/**
 * Operation to clear a bitmap with a given color. If a color is not given, a pure white will be used.
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class Clear(private val colorOption: Option[Int] = None)
    extends AbstractSingleSourceOperation with Immutable {

  /** The color with which to clear bitmaps. */
  private[this] val _color: Int = colorOption getOrElse 0xFFFFFFFF

  /** This [[Bitmap]] does not have any child operations. */
  val childOperationListsOption: Option[Array[BitmapOperationList]] = None

  /** Information about this operation instance */
  val metaInformation = MetaInformationMap(Map(
    "background-color" -> Option("0x${_color.toArgbHexColorString}")))

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
