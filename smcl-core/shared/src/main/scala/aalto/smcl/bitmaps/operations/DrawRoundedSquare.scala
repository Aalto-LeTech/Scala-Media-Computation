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
import aalto.smcl.settings._




/**
 * Operation to draw a rounded-corner square with given colors. If a color is not
 * given, the default primary/secondary colors will be used.
 *
 * @param upperLeftCornerXInPixels
 * @param upperLeftCornerYInPixels
 * @param sideLengthInPixels
 * @param roundingWidthInPixels
 * @param roundingHeightInPixels
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class DrawRoundedSquare(
    upperLeftCornerXInPixels: Int,
    upperLeftCornerYInPixels: Int,
    sideLengthInPixels: Int = DefaultBitmapWidthInPixels,
    roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
    roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
    hasBorder: Boolean = ShapesHaveBordersByDefault,
    hasFilling: Boolean = ShapesHaveFillingsByDefault,
    color: RGBAColor = DefaultPrimaryColor,
    fillColor: RGBAColor = DefaultSecondaryColor)
    extends AbstractOperation
            with Renderable
            with Immutable {

  require(sideLengthInPixels > 0, s"The side length argument must be greater than zero (was $sideLengthInPixels).")
  require(roundingWidthInPixels > 0, s"The rounding width argument must be greater than zero (was $roundingWidthInPixels).")
  require(roundingHeightInPixels > 0, s"The rounding height argument must be greater than zero (was $roundingHeightInPixels).")
  require(color != null, "The line color argument has to be a Color instance (was null).")
  require(fillColor != null, "The fill color argument has to be a Color instance (was null).")

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "DrawRoundedSquare"

  /** Information about this [[Renderable]] instance */
  lazy val describedProperties = Map(
    "upperLeftX" -> Option(s"$upperLeftCornerXInPixels px"),
    "upperLeftY" -> Option(s"$upperLeftCornerYInPixels px"),
    "sideLength" -> Option(s"$sideLengthInPixels px"),
    "roundingWidth" -> Option(s"$roundingWidthInPixels px"),
    "roundingHeight" -> Option(s"$roundingHeightInPixels px"),
    "hasBorder" -> Option(hasBorder.toString),
    "hasFilling" -> Option(hasFilling.toString),
    "color" -> Option(s"0x${color.toARGBInt.toARGBHexColorString}"),
    "fillColor" -> Option(s"0x${fillColor.toARGBInt.toARGBHexColorString}")
  )

  /**
   * Draws a rounded-corner square onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: BitmapBufferAdapter): Unit = {
    destination.drawingSurface.drawRoundedRectangle(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      sideLengthInPixels, sideLengthInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      hasBorder, hasFilling,
      color, fillColor)
  }

}
