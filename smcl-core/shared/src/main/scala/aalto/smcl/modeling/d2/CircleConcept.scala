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

package aalto.smcl.modeling.d2


import aalto.smcl.modeling.misc.ShapeConcept




/**
 * Circle in two-dimensional Cartesian coordinate system with [[Int]] coordinates.
 *
 * @param position
 * @param radiusInPixels
 *
 * @author Aleksi Lukkarinen
 */
case class CircleConcept private(
    position: Pos,
    radiusInPixels: Double)
    extends ShapeConcept
            with HasPos
            with HasBounds
            with Movable[CircleConcept] {

  /** */
  val boundary: Option[Bounds] = Some(Bounds(
    Pos(
      position.xInPixels - radiusInPixels,
      position.yInPixels - radiusInPixels),
    Pos(
      position.xInPixels + radiusInPixels,
      position.yInPixels + radiusInPixels)
  ))

  /**
   *
   *
   * @param deltas
   *
   * @return
   */
  @inline
  override def moveBy(deltas: Double*): CircleConcept = {
    require(
      deltas.length == 2,
      s"Circle uses exactly two coordinates (given: ${deltas.length})")

    CircleConcept(position + deltas, radiusInPixels)
  }

}
