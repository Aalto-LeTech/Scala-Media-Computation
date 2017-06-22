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


import aalto.smcl.geometry.Pos
import aalto.smcl.infrastructure.Identity




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class Line private(
    override val identity: Identity,
    val position: Pos,
    val start: Pos,
    val end: Pos)
    extends Polygon(
      identity,
      Seq(start, end),
      position) {

  /** Tells if this [[Line]] can be rendered on a bitmap. */
  val isRenderable: Boolean = true


  /**
   * Renders this [[Line]] on a drawing surface.
   *
   * @param drawingSurface
   */
  override def renderOn(drawingSurface: DrawingSurface): Unit = {

  }

  /**
   * Rotates this [[Line]].
   *
   * @param angleInDegrees
   *
   * @return
   */
  override def rotateDegs(angleInDegrees: Double): Line = {
    this
  }

  /**
   *
   * 1   *
   *
   * @return
   */
  override def toBitmap: Bmp = {
    Bmp(1, 1)
  }

}
