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

package aalto.smcl.bitmaps.fullfeatured


import aalto.smcl.infrastructure.{DrawingSurfaceAdapter, Identity}
import aalto.smcl.modeling.d2.{HasBounds, HasPos, Pos}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait ImageElement
    extends HasPos
            with HasBounds {

  /** */
  def identity: Identity

  /** Tells if this [[ImageElement]] can be rendered on a bitmap. */
  def isRenderable: Boolean

  /**
   * Renders this [[ImageElement]] on a drawing surface.
   *
   * @param drawingSurface
   */
  def renderOn(
      drawingSurface: DrawingSurfaceAdapter,
      position: Pos): Unit

  /**
   *
   *
   * @return
   */
  def toBitmap: Bmp = Bmp(this)

}
