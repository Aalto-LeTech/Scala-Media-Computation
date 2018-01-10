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

package smcl.pictures.fullfeatured


import smcl.infrastructure.{DrawingSurfaceAdapter, Identity}
import smcl.modeling.d2._
import smcl.viewers.{display => displayInViewer}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait ImageElement
    extends HasPos
        with HasBounds
        with Movable[ImageElement]
        with Rotatable[ImageElement] {

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
      offsetsToOrigo: Dims): Unit

  /**
   *
   *
   * @return
   */
  def toBitmap: Bmp = Bmp(this)

  /**
   *
   */
  def display(): ImageElement = {
    displayInViewer(toBitmap)

    this
  }

}
