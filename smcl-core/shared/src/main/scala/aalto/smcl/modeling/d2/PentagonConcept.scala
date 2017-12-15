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


import aalto.smcl.modeling.Area




/**
 * A companion object for [[PentagonConcept]].
 *
 * @author Aleksi Lukkarinen
 */
object PentagonConcept {

}




/**
 * A conceptual two-dimensional pentagon that has Cartesian coordinates.
 *
 * @param center
 * @param circumRadiusInPixels
 *
 * @author Aleksi Lukkarinen
 */
class PentagonConcept(
    center: Pos,
    circumRadiusInPixels: Double,
    shapeDataResolver: ShapeDataResolver)
    extends PolygonConcept[PentagonConcept](shapeDataResolver) {

  /** The corner points of this pentagon. */
  val points: Seq[Pos] = Seq() // TODO

  /** Area of this pentagon. */
  lazy val area: Area =
    Area.forPentagon(circumRadiusInPixels)

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  override
  def moveBy(offsets: Double*): PentagonConcept = {
    copy(newCenter = center.moveBy(offsets: _*))
  }

  /**
   *
   *
   * @param newCenter
   * @param newCircumRadiusInPixels
   *
   * @return
   */
  @inline
  def copy(
      newCenter: Pos = center,
      newCircumRadiusInPixels: Double = circumRadiusInPixels): PentagonConcept = {

    new PentagonConcept(newCenter, newCircumRadiusInPixels)
  }

}
