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

package smcl.pictures.operations


import smcl.colors.rgb._
import smcl.infrastructure._
import smcl.settings._




/**
 * Operation to draw a rounded-corner rectangle with given colors. If a color is not
 * given, the default primary/secondary colors will be used.
 *
 * @param upperLeftCornerXInPixels
 * @param upperLeftCornerYInPixels
 * @param widthInPixels
 * @param heightInPixels
 * @param roundingWidthInPixels
 * @param roundingHeightInPixels
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
private[pictures]
case class DrawRoundedRectangle(
    upperLeftCornerXInPixels: Int,
    upperLeftCornerYInPixels: Int,
    widthInPixels: Int = DefaultBitmapWidthInPixels,
    heightInPixels: Int = DefaultBitmapHeightInPixels,
    roundingWidthInPixels: Int = DefaultRoundingWidthInPixels,
    roundingHeightInPixels: Int = DefaultRoundingHeightInPixels,
    hasBorder: Boolean = ShapesHaveBordersByDefault,
    hasFilling: Boolean = ShapesHaveFillingsByDefault,
    color: Color = DefaultPrimaryColor,
    fillColor: Color = DefaultSecondaryColor)
    extends AbstractOperation
        with Renderable
        with Immutable {

  require(widthInPixels > 0, s"The width argument must be greater than zero (was $widthInPixels).")
  require(heightInPixels > 0, s"The height argument must be greater than zero (was $heightInPixels).")
  require(roundingWidthInPixels > 0, s"The rounding width argument must be greater than zero (was $roundingWidthInPixels).")
  require(roundingHeightInPixels > 0, s"The rounding height argument must be greater than zero (was $roundingHeightInPixels).")
  require(color != null, "The line color argument has to be a Color instance (was null).")
  require(fillColor != null, "The fill color argument has to be a Color instance (was null).")

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "DrawRoundedRectangle"

  /** Information about this [[Renderable]] instance */
  lazy val describedProperties = Map(
    "upperLeftX" -> Option(s"$upperLeftCornerXInPixels px"),
    "upperLeftY" -> Option(s"$upperLeftCornerYInPixels px"),
    "width" -> Option(s"$widthInPixels px"),
    "height" -> Option(s"$heightInPixels px"),
    "roundingWidth" -> Option(s"$roundingWidthInPixels px"),
    "roundingHeight" -> Option(s"$roundingHeightInPixels px"),
    "hasBorder" -> Option(hasBorder.toString),
    "hasFilling" -> Option(hasFilling.toString),
    "color" -> Option(s"0x${color.toARGBInt.toARGBHexColorString}"),
    "fillColor" -> Option(s"0x${fillColor.toARGBInt.toARGBHexColorString}")
  )

  /**
   * Draws a rounded-corner rectangle onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: BitmapBufferAdapter): Unit = {
    destination.drawingSurface.drawRoundedRectangle(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels,
      roundingWidthInPixels, roundingHeightInPixels,
      hasBorder, hasFilling,
      color, fillColor)
  }

}
