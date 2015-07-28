package aalto.smcl.images.operations


import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.common.{Color, GlobalSettings, MetaInformationMap}
import aalto.smcl.images._




/**
 * Operation to draw a circle with given colors. If a color is not given, the default
 * primary/secondary colors will be used, as defined in the [[GlobalSettings]].
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class DrawCircle(
    centerXInPixels: Int, centerYInPixels: Int, radiusInPixels: Int, isFilled: Boolean,
    lineColor: Color = GlobalSettings.defaultPrimaryColor,
    fillColor: Color = GlobalSettings.defaultSecondaryColor)
    extends AbstractSingleSourceOperation with Immutable {

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
    "filled" -> Option(isFilled.toString),
    "lineColor" -> Option(s"0x${lineColor.asPixelInt.toArgbHexColorString}"),
    "fillColor" -> Option(s"0x${fillColor.asPixelInt.toArgbHexColorString}")))

  /**
   * Draws a circle onto the given bitmap with the given colors.
   */
  override def render(destination: JBufferedImage): Unit = {
    val drawingSurface = destination.createGraphics()
    val oldColor = drawingSurface.getColor

    if (isFilled) {
      drawingSurface.setColor(fillColor.toOpaqueAwtColor)
      drawingSurface.fillOval(
        boundingBoxUpperLeftX, boundingBoxUpperLeftX,
        boundingBoxSideLength, boundingBoxSideLength)
    }

    drawingSurface.setColor(lineColor.toOpaqueAwtColor)
    drawingSurface.drawOval(
      boundingBoxUpperLeftX, boundingBoxUpperLeftX,
      boundingBoxSideLength, boundingBoxSideLength)

    drawingSurface.setColor(oldColor)
  }

}
