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
 * Operation to draw a rectangle with given colors. If a color is not given, the default
 * primary/secondary colors will be used.
 *
 * @param upperLeftCornerXInPixels
 * @param upperLeftCornerYInPixels
 * @param widthInPixels
 * @param heightInPixels
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class DrawRectangle(
    upperLeftCornerXInPixels: Int,
    upperLeftCornerYInPixels: Int,
    widthInPixels: Int = DefaultBitmapWidthInPixels,
    heightInPixels: Int = DefaultBitmapHeightInPixels,
    hasBorder: Boolean = ShapesHaveBordersByDefault,
    hasFilling: Boolean = ShapesHaveFillingsByDefault,
    color: Color = DefaultPrimaryColor,
    fillColor: Color = DefaultSecondaryColor)
    extends AbstractOperation
        with Renderable
        with Immutable {

  require(widthInPixels > 0, s"The width argument must be greater than zero (was $widthInPixels).")
  require(heightInPixels > 0, s"The height argument must be greater than zero (was $heightInPixels).")
  require(color != null, "The line color argument has to be a Color instance (was null).")
  require(fillColor != null, "The fill color argument has to be a Color instance (was null).")

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "DrawRectangle"

  /** Information about this [[Renderable]] instance */
  lazy val describedProperties = Map(
    "upperLeftX" -> Option(s"$upperLeftCornerXInPixels px"),
    "upperLeftY" -> Option(s"$upperLeftCornerYInPixels px"),
    "width" -> Option(s"$widthInPixels px"),
    "height" -> Option(s"$heightInPixels px"),
    "hasBorder" -> Option(hasBorder.toString),
    "hasFilling" -> Option(hasFilling.toString),
    "color" -> Option(s"0x${color.toARGBInt.toARGBHexColorString}"),
    "fillColor" -> Option(s"0x${fillColor.toARGBInt.toARGBHexColorString}")
  )

  /**
   * Draws a rectangle onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: BitmapBufferAdapter): Unit = {
    destination.drawingSurface.drawRectangle(
      upperLeftCornerXInPixels, upperLeftCornerYInPixels,
      widthInPixels, heightInPixels,
      hasBorder, hasFilling,
      color, fillColor)
  }

}
