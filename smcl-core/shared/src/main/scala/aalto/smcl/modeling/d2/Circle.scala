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


import aalto.smcl.modeling.misc.AbstractCircle




/**
 * Circle in two-dimensional Cartesian coordinate system with [[Int]] coordinates.
 *
 * @param position
 * @param radiusInPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Circle private(
    override val position: Pos,
    override val radiusInPixels: Double)
    extends AbstractCircle[Pos](position, radiusInPixels)
            with Movable[Circle] {

  /**
   *
   *
   * @param deltas
   *
   * @return
   */
  @inline
  override def moveBy(deltas: Double*): Circle = {
    require(
      deltas.length == 2,
      s"Circle uses exactly two coordinates (given: ${deltas.length})")

    Circle(position + deltas, radiusInPixels)
  }

}
