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


import aalto.smcl.colors.RGBAColor
import aalto.smcl.infrastructure._




/**
 * Operation to clear a bitmap with a given color. If a color is not given,
 * the default background color will be used, as defined in the [[aalto.smcl.GS]].
 *
 * @param color
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class Clear(
    color: RGBAColor = GS.colorFor(DefaultBackground))
    extends AbstractOperation
            with Renderable
            with Immutable {

  require(color != null, "The color argument has to be a Color instance (was null).")

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "Clear"

  /** Information about this operation instance */
  lazy val describedProperties = Map(
    "background-color" -> Option("0x${_color.asArgbInt.toArgbHexColorString}")
  )

  /**
   * Clears the given bitmap with the given color.
   *
   * @param destination
   */
  override def render(destination: BitmapBufferAdapter): Unit =
    destination.drawingSurface clearUsing color

}
