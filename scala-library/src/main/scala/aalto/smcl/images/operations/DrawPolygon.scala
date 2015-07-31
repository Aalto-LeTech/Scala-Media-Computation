package aalto.smcl.images.operations


import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.common.ColorOps._
import aalto.smcl.common.{Color, GS, MetaInformationMap, _}
import aalto.smcl.images.SettingKeys.{DefaultSecondary, DefaultPrimary}




/**
 * Operation to draw a polygon with given colors. If a color is not given, the default primary/secondary
 * color will be used, as defined in the [[GS]]. The resulting polyline will be automatically closed.
 *
 * @param xCoordinates
 * @param yCoordinates
 * @param numberOfCoordinatesToDraw
 * @param isFilled
 * @param lineColor
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class DrawPolygon(
    xCoordinates: Array[Int],
    yCoordinates: Array[Int],
    numberOfCoordinatesToDraw: Int,
    isFilled: Boolean = false,
    lineColor: Color = GS.colorFor(DefaultPrimary),
    fillColor: Color = GS.colorFor(DefaultSecondary))
    extends AbstractSingleSourceOperation with Immutable {

  require(xCoordinates != null, "The x coordinate argument has to be an Array[Int] instance (was null).")
  require(yCoordinates != null, "The y coordinate argument has to be an Array[Int] instance (was null).")

  val numberOfCoordinatesPreset = xCoordinates.length.min(yCoordinates.length)

  require(numberOfCoordinatesPreset > 1, s"The coordinate arrays must have at least two coordinate pairs present.")

  require(numberOfCoordinatesToDraw > 1,
    s"At least two coordinate pairs (which equals one line segment) has to be drawn.")

  require(numberOfCoordinatesToDraw <= numberOfCoordinatesPreset,
    s"The coordinate arrays do not contain the requested amount of coordinate pairs " +
        s"(only $numberOfCoordinatesPreset pairs present, $numberOfCoordinatesToDraw requested).")

  require(lineColor != null, "The line color argument has to be a Color instance (was null).")
  require(fillColor != null, "The fill color argument has to be a Color instance (was null).")

  /** This [[AbstractSingleSourceOperation]] does not have any child operations. */
  val childOperationListsOption: Option[Array[BitmapOperationList]] = None

  /** Information about this [[AbstractSingleSourceOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "coordinates" -> Option(xCoordinates.zip(yCoordinates).mkString(StrSpace)),
    "numberOfCoordinatesPreset" -> Option(numberOfCoordinatesPreset.toString),
    "numberOfCoordinatesToDraw" -> Option(numberOfCoordinatesToDraw.toString),
    "filled" -> Option(isFilled.toString),
    "lineColor" -> Option(s"0x${lineColor.asPixelInt.toArgbHexColorString}"),
    "fillColor" -> Option(s"0x${fillColor.asPixelInt.toArgbHexColorString}")))

  /**
   * Draws a polygon onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: JBufferedImage): Unit = {
    val drawingSurface = destination.createGraphics()
    val oldColor = drawingSurface.getColor

    if (isFilled) {
      drawingSurface.setColor(fillColor.asAwtColor)
      drawingSurface.fillPolygon(xCoordinates, yCoordinates, numberOfCoordinatesToDraw)
    }

    drawingSurface.setColor(lineColor.asAwtColor)
    drawingSurface.drawPolygon(xCoordinates, yCoordinates, numberOfCoordinatesToDraw)

    drawingSurface.setColor(oldColor)
  }

}
