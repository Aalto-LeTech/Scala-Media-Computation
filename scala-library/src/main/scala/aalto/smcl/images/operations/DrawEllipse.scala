package aalto.smcl.images.operations

import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.common.ColorOps._
import aalto.smcl.common.{Color, GS, MetaInformationMap}
import aalto.smcl.images.SettingKeys.{DefaultPrimary, DefaultSecondary}




/**
 * Operation to draw a circle with given colors. If a color is not given, the default
 * primary/secondary colors will be used, as defined in the [[GS]].
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class DrawEllipse(
    centerXInPixels: Int,
    centerYInPixels: Int,
    widthInPixels: Int,
    heightInPixels: Int,
    isFilled: Boolean,
    lineColor: Color = GS.colorFor(DefaultPrimary),
    fillColor: Color = GS.colorFor(DefaultSecondary))
    extends AbstractSingleSourceOperation with Immutable {

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
      drawingSurface.setColor(fillColor.asAwtColor)
      drawingSurface.fillOval(
        boundingBoxUpperLeftX, boundingBoxUpperLeftY,
        widthInPixels, heightInPixels)
    }

    drawingSurface.setColor(lineColor.asAwtColor)
    drawingSurface.drawOval(
      boundingBoxUpperLeftX, boundingBoxUpperLeftY,
      widthInPixels, heightInPixels)

    drawingSurface.setColor(oldColor)
  }

}
