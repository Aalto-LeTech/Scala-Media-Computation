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
import aalto.smcl.modeling.d2.Pos




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


  val boundary = None

  /** Tells if this [[Line]] can be rendered on a bitmap. */
  val isRenderable: Boolean = true


  /**
   * Renders this [[aalto.smcl.bitmaps.fullfeatured.Line]] on a drawing surface.
   *
   * @param drawingSurface
   * @param position
   */
  override def renderOn(
      drawingSurface: DrawingSurfaceAdapter,
      position: Pos): Unit = {

  }

  /**
   * Rotates this object around a given point of the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  def rotateBy(angleInDegrees: Double, centerOfRotation: Pos): ImageElement = {
    this
  }

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  def moveBy(offsets: Double*): ImageElement = {
    this
  }

}
