package aalto.smcl.images.operations


import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.common.ColorOps._
import aalto.smcl.common.{Color, GS, MetaInformationMap}
import aalto.smcl.images.SettingKeys.{DefaultBitmapWidthInPixels, DefaultPrimary, DefaultSecondary}




/**
 * Operation to draw a square with given colors. If a color is not given, the default
 * primary/secondary colors will be used, as defined in the [[GS]].
 *
 * @param upperLeftCornerXInPixels
 * @param upperLeftCornerYInPixels
 * @param sideLengthInPixels
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class DrawSquare(
    upperLeftCornerXInPixels: Int,
    upperLeftCornerYInPixels: Int,
    sideLengthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    hasBorder: Boolean = true,
    hasFilling: Boolean = false,
    color: Color = GS.colorFor(DefaultPrimary),
    fillColor: Color = GS.colorFor(DefaultSecondary))
    extends AbstractSingleSourceOperation with Immutable {

  require(sideLengthInPixels > 0, s"The side length argument must be greater than zero (was $sideLengthInPixels).")
  require(color != null, "The line color argument has to be a Color instance (was null).")
  require(fillColor != null, "The fill color argument has to be a Color instance (was null).")

  /** This [[AbstractSingleSourceOperation]] does not have any child operations. */
  val childOperationListsOption: Option[Array[BitmapOperationList]] = None

  /** Information about this [[AbstractSingleSourceOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "upperLeftX" -> Option(s"$upperLeftCornerXInPixels px"),
    "upperLeftY" -> Option(s"$upperLeftCornerYInPixels px"),
    "side" -> Option(s"$sideLengthInPixels px"),
    "hasBorder" -> Option(hasBorder.toString),
    "hasFilling" -> Option(hasFilling.toString),
    "color" -> Option(s"0x${color.asPixelInt.toArgbHexColorString}"),
    "fillColor" -> Option(s"0x${fillColor.asPixelInt.toArgbHexColorString}")))

  /**
   * Draws a square onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: JBufferedImage): Unit = {
    val drawingSurface = destination.createGraphics()
    val oldColor = drawingSurface.getColor

    if (hasFilling) {
      drawingSurface.setColor(fillColor.asAwtColor)
      drawingSurface.fillRect(
        upperLeftCornerXInPixels, upperLeftCornerYInPixels,
        sideLengthInPixels, sideLengthInPixels)
    }

    if (hasBorder) {
      drawingSurface.setColor(color.asAwtColor)
      drawingSurface.drawRect(
        upperLeftCornerXInPixels, upperLeftCornerYInPixels,
        sideLengthInPixels, sideLengthInPixels)
    }

    drawingSurface.setColor(oldColor)
  }

}
