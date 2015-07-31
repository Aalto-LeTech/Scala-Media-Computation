package aalto.smcl.images.operations


import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.common._
import aalto.smcl.common.ColorOps._
import aalto.smcl.common.{Color, GS, MetaInformationMap}
import aalto.smcl.images.SettingKeys.DefaultPrimary




/**
 * Operation to draw a polyline with given color. If the color is not given, the default primary color
 * will be used, as defined in the [[GS]]. If the start and end points do not point to the same pixel,
 * the resulting polyline will not be closed.
 *
 * @param xCoordinates
 * @param yCoordinates
 * @param numberOfCoordinatesToDraw
 * @param color
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class DrawPolyline(
    xCoordinates: Array[Int],
    yCoordinates: Array[Int],
    numberOfCoordinatesToDraw: Int,
    color: Color = GS.colorFor(DefaultPrimary))
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

  require(color != null, "The color argument has to be a Color instance (was null).")

  /** This [[AbstractSingleSourceOperation]] does not have any child operations. */
  val childOperationListsOption: Option[Array[BitmapOperationList]] = None

  /** Information about this [[AbstractSingleSourceOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "coordinates" -> Option(xCoordinates.zip(yCoordinates).mkString(StrSpace)),
    "numberOfCoordinatesPreset" -> Option(numberOfCoordinatesPreset.toString),
    "numberOfCoordinatesToDraw" -> Option(numberOfCoordinatesToDraw.toString),
    "color" -> Option(s"0x${color.asPixelInt.toArgbHexColorString}")))

  /**
   * Draws a line onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: JBufferedImage): Unit = {
    val drawingSurface = destination.createGraphics()
    val oldColor = drawingSurface.getColor

    drawingSurface.setColor(color.asAwtColor)
    drawingSurface.drawPolyline(xCoordinates, yCoordinates, numberOfCoordinatesToDraw)
    drawingSurface.setColor(oldColor)
  }

}
