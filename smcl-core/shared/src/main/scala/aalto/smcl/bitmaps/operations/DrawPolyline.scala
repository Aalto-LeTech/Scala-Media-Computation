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

package aalto.smcl.bitmaps.operations


import aalto.smcl.colors.{RGBAColor, _}
import aalto.smcl.infrastructure._




/**
 * Operation to draw a polyline with given color. If the color is not given, the default primary color
 * will be used, as defined in the [[aalto.smcl.infrastructure.GS]]. If the start and end points do not
 * point to the same pixel, the resulting polyline will not be closed.
 *
 * @param xCoordinates
 * @param yCoordinates
 * @param numberOfCoordinatesToDraw
 * @param color
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class DrawPolyline(
    xCoordinates: Seq[Int],
    yCoordinates: Seq[Int],
    numberOfCoordinatesToDraw: Int,
    color: RGBAColor = GS.colorFor(DefaultPrimary))
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

  require(color != null, "The color argument has to be a Color instance (was null).")

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "DrawPolyline"

  /** Information about this [[Renderable]] instance */
  lazy val describedProperties = Map(
    "coordinates" -> Option(xCoordinates.zip(yCoordinates).mkString(StrSpace)),
    "numberOfCoordinatesPresent" -> Option(numberOfCoordinatesPresent.toString),
    "numberOfCoordinatesToDraw" -> Option(numberOfCoordinatesToDraw.toString),
    "color" -> Option(s"0x${color.toARGBInt.toARGBHexColorString}")
  )

  /**
   * Draws a polyline onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: BitmapBufferAdapter): Unit = {
    destination.drawingSurface.drawPolyline(
      xCoordinates, yCoordinates,
      numberOfCoordinatesToDraw,
      color)
  }

}
