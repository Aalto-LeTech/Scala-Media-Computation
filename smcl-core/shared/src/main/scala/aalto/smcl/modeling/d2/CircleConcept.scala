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
 * A companion object for [[CircleConcept]].
 *
 * @author Aleksi Lukkarinen
 */
object CircleConcept {

}




/**
 * A conceptual two-dimensional circle that has Cartesian coordinates.
 *
 * @param center
 * @param radiusInPixels
 * @param shapeDataResolver
 *
 * @author Aleksi Lukkarinen
 */
class CircleConcept private(
    center: Pos,
    radiusInPixels: Double,
    shapeDataResolver: ShapeDataResolver)
    extends ConicSectionConcept[CircleConcept](shapeDataResolver) {

  /** */
  lazy val boundary: Bounds = Bounds(
    Pos(
      position.xInPixels - radiusInPixels,
      position.yInPixels - radiusInPixels),
    Pos(
      position.xInPixels + radiusInPixels,
      position.yInPixels + radiusInPixels))

  /** Position of this circle. */
  lazy val position: Pos = boundary.upperLeftMarker

  /** Dimensions of this circle. */
  lazy val dimensions: Dims = Dims(
    boundary.width,
    boundary.height)

  /** Area of this circle. */
  lazy val area: Area = Area.forCircle(radiusInPixels)

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  override
  def moveBy(offsets: Double*): CircleConcept = {
    copy(newCenter = center.moveBy(offsets: _*))
  }

  /**
   *
   *
   * @param newCenter
   * @param newRadiusInPixels
   *
   * @return
   */
  @inline
  def copy(
      newCenter: Pos = center,
      newRadiusInPixels: Double = radiusInPixels): CircleConcept = {

    new CircleConcept(newCenter, newRadiusInPixels)
  }

}
