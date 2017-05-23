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
 * Operation to draw a line with given color. If the color is not given, the default
 * primary color will be used, as defined in the [[aalto.smcl.infrastructure.GS]].
 *
 * @param fromXInPixels
 * @param fromYInPixels
 * @param toXInPixels
 * @param toYInPixels
 * @param color
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class DrawLine(
    fromXInPixels: Int,
    fromYInPixels: Int,
    toXInPixels: Int,
    toYInPixels: Int,
    color: RGBAColor = GS.colorFor(DefaultPrimary))
    extends AbstractOperation
            with Renderable
            with Immutable {

  require(color != null, "The color argument has to be a Color instance (was null).")

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "DrawLine"

  /** Information about this [[Renderable]] instance */
  lazy val describedProperties = Map(
    "fromX" -> Option(s"$fromXInPixels px"),
    "fromY" -> Option(s"$fromYInPixels px"),
    "toX" -> Option(s"$toXInPixels px"),
    "toY" -> Option(s"$toYInPixels px"),
    "color" -> Option(s"0x${color.toARGBInt.toARGBHexColorString}")
  )

  /**
   * Draws a line onto the given bitmap with the given colors.
   *
   * @param destination
   */
  override def render(destination: BitmapBufferAdapter): Unit = {
    destination.drawingSurface.drawLine(
      fromXInPixels, fromYInPixels,
      toXInPixels, toYInPixels,
      color)
  }

}
