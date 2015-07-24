package aalto.smcl.images.operations


import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.common.{GlobalSettings, MetaInformationMap}
import aalto.smcl.images.immutable.Color




/**
 * Operation to draw an unfilled circle with a given color. If a color is not given,
 * the default primary color will be used, as defined in the [[GlobalSettings.defaultPrimaryColor]].
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class DrawCircle(
    centerXInPixels: Int, centerYInPixels: Int, radiusInPixels: Int,
    private val colorOption: Option[Color] = None)
    extends AbstractSingleSourceOperation with Immutable {

  /** The color with which to draw the circle. */
  private[this] val _color: Color = colorOption getOrElse GlobalSettings.defaultPrimaryColor

  /** X coordinate of the upper-left corner of the bounding box of the circle to be drawn. */
  private[this] val _upperLeftX: Int = centerXInPixels - radiusInPixels

  /** Y coordinate of the upper-left corner of the bounding box of the circle to be drawn. */
  private[this] val _upperLeftY: Int = centerYInPixels - radiusInPixels

  /** Length of a side of the bounding box of the circle to be drawn. */
  private[this] val _side: Int = 2 * radiusInPixels

  /** This [[AbstractSingleSourceOperation]] does not have any child operations. */
  val childOperationListsOption: Option[Array[BitmapOperationList]] = None

  /** Information about this [[AbstractSingleSourceOperation]] instance */
  val metaInformation = MetaInformationMap(Map(
    "centerX" -> Option(s"$centerXInPixels px"),
    "centerY" -> Option(s"$centerYInPixels px"),
    "radius" -> Option(s"$radiusInPixels px"),
    "color" -> Option("0x${_color.asPixelInt.toArgbHexColorString}")))

  /**
   * Draws a circle onto the given bitmap with the given color.
   */
  override def render(destination: JBufferedImage): Unit = {
    val drawingSurface = destination.createGraphics()
    val oldColor = drawingSurface.getColor

    drawingSurface.setColor(_color.toOpaqueAwtColor)
    drawingSurface.drawOval(_upperLeftX, _upperLeftX, _side, _side)
    drawingSurface.setColor(oldColor)
  }

}
