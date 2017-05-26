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

package aalto.smcl.bitmaps


import aalto.smcl.colors.RGBAColor
import aalto.smcl.settings.{DefaultBitmapHeightInPixels, DefaultPrimaryColor, UpdateViewerPerDefaults, ViewerUpdateStyle}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
class VerticalLineCreator private[bitmaps]() {

  /**
   * Creates a new empty [[Bitmap]] instance with a vertical line drawn on it.
   *
   * @param heightInPixels
   * @param color
   * @param viewerHandling
   *
   * @return
   */
  def createOne(
      heightInPixels: Int = DefaultBitmapHeightInPixels,
      color: RGBAColor = DefaultPrimaryColor,
      viewerHandling: ViewerUpdateStyle = UpdateViewerPerDefaults): Bitmap = {

    require(heightInPixels > 0, s"Width of the line must be at least 1 pixel (was $heightInPixels)")
    require(color != null, "The color argument has to be a Color instance (was null).")

    Bitmap(1, heightInPixels, color, viewerHandling)
  }

}
