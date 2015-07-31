package aalto.smcl.images.operations

import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.common.ColorOps._
import aalto.smcl.common.{Color, GS, MetaInformationMap}
import aalto.smcl.images.SettingKeys.DefaultPrimary




/**
 * Operation to draw a line with given color. If the color is not given, the default
 * primary color will be used, as defined in the [[GS]].
 *
 * @param fromXInPixels
 * @param fromYInPixels
 * @param toXInPixels
 * @param toYInPixels
 * @param color
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class DrawLine(
    fromXInPixels: Int,
    fromYInPixels: Int,
    toXInPixels: Int,
    toYInPixels: Int,
    color: Color = GS.colorFor(DefaultPrimary))
    extends AbstractSingleSourceOperation with Immutable {

  require(color != null, "The color argument has to be a Color instance (was null).")

  /** This [[AbstractSingleSourceOperation]] does not have any child operations. */
  val childOperationListsOption: Option[Array[BitmapOperationList]] = None

  /** Information about this [[AbstractSingleSourceOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "fromX" -> Option(s"$fromXInPixels px"),
    "fromY" -> Option(s"$fromYInPixels px"),
    "toX" -> Option(s"$toXInPixels px"),
    "toY" -> Option(s"$toYInPixels px"),
    "color" -> Option(s"0x${color.asPixelInt.toArgbHexColorString}")))

  /**
   * Draws a line onto the given bitmap with the given color.
   *
   * @param destination
   */
  override def render(destination: JBufferedImage): Unit = {
    val drawingSurface = destination.createGraphics()
    val oldColor = drawingSurface.getColor

    drawingSurface.setColor(color.asAwtColor)
    drawingSurface.drawLine(
      fromXInPixels, fromYInPixels,
      toXInPixels, toYInPixels)

    drawingSurface.setColor(oldColor)
  }

}
