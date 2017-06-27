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


import aalto.smcl.geometry.d2.{Bounds, HasBounds, HasPos, Pos}
import aalto.smcl.infrastructure.{DrawingSurfaceAdapter, Identity}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class Image(
    override val identity: Identity,
    val widthInPixels: Int,
    val heightInPixels: Int,
    val position: Pos)
    extends ImageElement(identity)
            with HasPos
            with HasBounds {

  // TODO: Tarkistukset

  /** */
  val boundary: Option[Bounds] =
    Some(Bounds(
      position,
      Pos(position.xInPixels + widthInPixels,
        position.yInPixels + heightInPixels)))

  /** */
  val isRenderable: Boolean =
    widthInPixels > 0 && heightInPixels > 0

  /**
   *
   *
   * @param drawingSurface
   */
  override def renderOn(drawingSurface: DrawingSurfaceAdapter): Unit = {

  }

  /**
   *
   *
   * @return
   */
  override def toBitmap: Bmp = {
    Bmp(1, 1)
  }

  /**
   * Rotates this object around a given point of the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  override def rotateBy(angleInDegrees: Double, centerOfRotation: Pos): ImageElement = {
    this
  }

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  override def moveBy(offsets: Double*): ImageElement = {
    this
  }

}
