/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package smcl.bitmaps.operations


import smcl.colors.rgb._
import smcl.infrastructure._
import smcl.settings._




/**
 * Operation to draw a polygon with given colors. If a color is not given, the default primary/secondary
 * color will be used. The resulting polyline will be automatically closed.
 *
 * @param xCoordinates
 * @param yCoordinates
 * @param numberOfCoordinatesToDraw
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class DrawPolygon(
    xCoordinates: Seq[Int],
    yCoordinates: Seq[Int],
    numberOfCoordinatesToDraw: Int,
    hasBorder: Boolean = ShapesHaveBordersByDefault,
    hasFilling: Boolean = ShapesHaveFillingsByDefault,
    color: Color = DefaultPrimaryColor,
    fillColor: Color = DefaultSecondaryColor)
    extends AbstractOperation
        with Renderable
        with Immutable {

  require(xCoordinates != null, "The x coordinate argument has to be an Seq[Int] instance (was null).")
  require(yCoordinates != null, "The y coordinate argument has to be an Seq[Int] instance (was null).")

  val numberOfCoordinatesPresent: Int = xCoordinates.length.min(yCoordinates.length)

  require(numberOfCoordinatesPresent > 1, s"The coordinate sequences must have at least two coordinate pairs present.")

  require(numberOfCoordinatesToDraw > 1,
    s"At least two coordinate pairs (which equals one line segment) has to be drawn.")

  require(numberOfCoordinatesToDraw <= numberOfCoordinatesPresent,
    s"The coordinate sequences do not contain the requested amount of coordinate pairs " +
        s"(only $numberOfCoordinatesPresent pairs present, $numberOfCoordinatesToDraw requested).")

  require(color != null, "The line color argument has to be a Color instance (was null).")
  require(fillColor != null, "The fill color argument has to be a Color instance (was null).")

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "DrawPolygon"

  /** Information about this [[Renderable]] instance */
  lazy val describedProperties = Map(
    "coordinates" -> Option(xCoordinates.zip(yCoordinates).mkString(StrSpace)),
    "numberOfCoordinatesPresent" -> Option(numberOfCoordinatesPresent.toString),
    "numberOfCoordinatesToDraw" -> Option(numberOfCoordinatesToDraw.toString),
    "hasBorder" -> Option(hasBorder.toString),
    "hasFilling" -> Option(hasFilling.toString),
    "color" -> Option(s"0x${color.toARGBInt.toARGBHexColorString}"),
    "fillColor" -> Option(s"0x${fillColor.toARGBInt.toARGBHexColorString}")
  )

  /**
   * Draws a polygon onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: BitmapBufferAdapter): Unit = {
    destination.drawingSurface.drawPolygon(
      xCoordinates, yCoordinates,
      numberOfCoordinatesToDraw,
      hasBorder, hasFilling,
      color, fillColor)
  }

}
