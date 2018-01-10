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
import smcl.settings.DefaultBackgroundColor




/**
 * Operation to clear a bitmap with a given color. If a color is not given,
 * the default background color will be used.
 *
 * @param color
 *
 * @author Aleksi Lukkarinen
 */
private[pictures]
case class Clear(
    color: Color = DefaultBackgroundColor)
    extends AbstractOperation
        with Renderable
        with Immutable {

  require(color != null, "The color argument has to be a Color instance (was null).")

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "Clear"

  /** Information about this operation instance */
  lazy val describedProperties = Map(
    "background-color" -> Option(s"0x${color.toARGBInt.toARGBHexColorString}")
  )

  /**
   * Clears the given bitmap with the given color.
   *
   * @param destination
   */
  override def render(destination: BitmapBufferAdapter): Unit =
    destination.drawingSurface clearUsing color

}
