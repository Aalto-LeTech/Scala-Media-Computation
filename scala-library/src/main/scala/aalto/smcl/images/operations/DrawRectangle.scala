package aalto.smcl.images.operations

import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.common.ColorOps._
import aalto.smcl.common.{Color, GS, MetaInformationMap}
import aalto.smcl.images.SettingKeys.{DefaultPrimary, DefaultSecondary}




/**
 * Operation to draw a rectangle with given colors. If a color is not given, the default
 * primary/secondary colors will be used, as defined in the [[GS]].
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class DrawRectangle(
    upperLeftCornerXInPixels: Int,
    upperLeftCornerYInPixels: Int,
    widthInPixels: Int,
    heightInPixels: Int,
    isFilled: Boolean,
    lineColor: Color = GS.colorFor(DefaultPrimary),
    fillColor: Color = GS.colorFor(DefaultSecondary))
    extends AbstractSingleSourceOperation with Immutable {

  /** This [[AbstractSingleSourceOperation]] does not have any child operations. */
  val childOperationListsOption: Option[Array[BitmapOperationList]] = None

  /** Information about this [[AbstractSingleSourceOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "upperLeftX" -> Option(s"$upperLeftCornerXInPixels px"),
    "upperLeftY" -> Option(s"$upperLeftCornerYInPixels px"),
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
      drawingSurface.fillRect(
        upperLeftCornerXInPixels, upperLeftCornerYInPixels,
        widthInPixels, heightInPixels)
    }

    drawingSurface.setColor(lineColor.asAwtColor)
    drawingSurface.drawRect(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels)

    drawingSurface.setColor(oldColor)
  }

}
