package aalto.smcl.images.operations


import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.common.ColorOps._
import aalto.smcl.common.{Color, GS, MetaInformationMap}
import aalto.smcl.images.SettingKeys.{DefaultBitmapHeightInPixels, DefaultBitmapWidthInPixels, DefaultPrimary, DefaultSecondary}




/**
 * Operation to draw an ellipse with given colors. If a color is not given, the default
 * primary/secondary colors will be used, as defined in the [[GS]].
 *
 * @param centerXInPixels
 * @param centerYInPixels
 * @param widthInPixels
 * @param heightInPixels
 * @param hasBorder
 * @param hasFilling
 * @param lineColor
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class DrawEllipse(
    centerXInPixels: Int,
    centerYInPixels: Int,
    widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels),
    hasBorder: Boolean = true,
    hasFilling: Boolean = false,
    lineColor: Color = GS.colorFor(DefaultPrimary),
    fillColor: Color = GS.colorFor(DefaultSecondary))
    extends AbstractSingleSourceOperation with Immutable {

  require(widthInPixels > 0, s"The width argument must be greater than zero (was $widthInPixels).")
  require(heightInPixels > 0, s"The height argument must be greater than zero (was $heightInPixels).")
  require(lineColor != null, "The line color argument has to be a Color instance (was null).")
  require(fillColor != null, "The fill color argument has to be a Color instance (was null).")

  /** X coordinate of the upper-left corner of the bounding box of the circle to be drawn. */
  val boundingBoxUpperLeftX: Int = centerXInPixels - (widthInPixels / 2)

  /** Y coordinate of the upper-left corner of the bounding box of the circle to be drawn. */
  val boundingBoxUpperLeftY: Int = centerYInPixels - (heightInPixels / 2)

  /** This [[AbstractSingleSourceOperation]] does not have any child operations. */
  val childOperationListsOption: Option[Array[BitmapOperationList]] = None

  /** Information about this [[AbstractSingleSourceOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "centerX" -> Option(s"$centerXInPixels px"),
    "centerY" -> Option(s"$centerYInPixels px"),
    "width" -> Option(s"$widthInPixels px"),
    "height" -> Option(s"$heightInPixels px"),
    "filled" -> Option(hasFilling.toString),
    "lineColor" -> Option(s"0x${lineColor.asPixelInt.toArgbHexColorString}"),
    "fillColor" -> Option(s"0x${fillColor.asPixelInt.toArgbHexColorString}")))

  /**
   * Draws an ellipse onto the given bitmap with the given colors.
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
        widthInPixels, heightInPixels)
    }

    if (hasBorder) {
      drawingSurface.setColor(lineColor.asAwtColor)
      drawingSurface.drawOval(
        boundingBoxUpperLeftX, boundingBoxUpperLeftY,
        widthInPixels, heightInPixels)
    }

    drawingSurface.setColor(oldColor)
  }

}
