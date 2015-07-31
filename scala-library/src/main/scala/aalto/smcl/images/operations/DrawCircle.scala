package aalto.smcl.images.operations


import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.common.{Color, GS, MetaInformationMap}
import aalto.smcl.common.ColorOps._
import aalto.smcl.images.SettingKeys.{DefaultCircleRadiusInPixels, DefaultSecondary, DefaultPrimary}




/**
 * Operation to draw a circle with given colors. If a color is not given, the default
 * primary/secondary colors will be used, as defined in the [[GS]].
 *
 * @param centerXInPixels
 * @param centerYInPixels
 * @param radiusInPixels
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class DrawCircle(
    centerXInPixels: Int,
    centerYInPixels: Int,
    radiusInPixels: Int = GS.intFor(DefaultCircleRadiusInPixels),
    hasBorder: Boolean = true,
    hasFilling: Boolean = false,
    color: Color = GS.colorFor(DefaultPrimary),
    fillColor: Color = GS.colorFor(DefaultSecondary))
    extends AbstractSingleSourceOperation with Immutable {

  require(radiusInPixels > 0, s"The radius argument must be greater than zero (was $radiusInPixels).")
  require(color != null, "The line color argument has to be a Color instance (was null).")
  require(fillColor != null, "The fill color argument has to be a Color instance (was null).")

  /** X coordinate of the upper-left corner of the bounding box of the circle to be drawn. */
  val boundingBoxUpperLeftX: Int = centerXInPixels - radiusInPixels

  /** Y coordinate of the upper-left corner of the bounding box of the circle to be drawn. */
  val boundingBoxUpperLeftY: Int = centerYInPixels - radiusInPixels

  /** Length of a side of the bounding box of the circle to be drawn. */
  val boundingBoxSideLength: Int = 2 * radiusInPixels

  /** This [[AbstractSingleSourceOperation]] does not have any child operations. */
  val childOperationListsOption: Option[Array[BitmapOperationList]] = None

  /** Information about this [[AbstractSingleSourceOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "centerX" -> Option(s"$centerXInPixels px"),
    "centerY" -> Option(s"$centerYInPixels px"),
    "radius" -> Option(s"$radiusInPixels px"),
    "hasBorder" -> Option(hasBorder.toString),
    "hasFilling" -> Option(hasFilling.toString),
    "color" -> Option(s"0x${color.asPixelInt.toArgbHexColorString}"),
    "fillColor" -> Option(s"0x${fillColor.asPixelInt.toArgbHexColorString}")))

  /**
   * Draws a circle onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: JBufferedImage): Unit = {
    val drawingSurface = destination.createGraphics()
    val oldColor = drawingSurface.getColor

    if (hasFilling) {
      drawingSurface.setColor(fillColor.asAwtColor)
      drawingSurface.fillOval(
        boundingBoxUpperLeftX, boundingBoxUpperLeftY,
        boundingBoxSideLength, boundingBoxSideLength)
    }

    if (hasBorder) {
      drawingSurface.setColor(color.asAwtColor)
      drawingSurface.drawOval(
        boundingBoxUpperLeftX, boundingBoxUpperLeftY,
        boundingBoxSideLength, boundingBoxSideLength)
    }

    drawingSurface.setColor(oldColor)
  }

}
