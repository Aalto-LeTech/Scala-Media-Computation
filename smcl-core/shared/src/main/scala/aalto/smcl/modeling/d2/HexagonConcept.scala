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
 * A companion object for [[HexagonConcept]].
 *
 * @author Aleksi Lukkarinen
 */
object HexagonConcept {

}




/**
 * A conceptual two-dimensional hexagon that has Cartesian coordinates.
 *
 * @param center
 * @param circumRadiusInPixels
 *
 * @author Aleksi Lukkarinen
 */
class HexagonConcept(
    center: Pos,
    circumRadiusInPixels: Double,
    shapeDataResolver: ShapeDataResolver)
    extends PolygonConcept[HexagonConcept](shapeDataResolver) {

  /** The corner points of this hexagon. */
  val points: Seq[Pos] = Seq() // TODO

  /** Area of this hexagon. */
  lazy val area: Area =
    Area.forHexagon(circumRadiusInPixels)

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  override
  def moveBy(offsets: Double*): HexagonConcept = {
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
      newCircumRadiusInPixels: Double = circumRadiusInPixels): HexagonConcept = {

    new HexagonConcept(newCenter, newCircumRadiusInPixels)
  }

}
