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
 * A companion object for [[EllipseConcept]].
 *
 * @author Aleksi Lukkarinen
 */
object EllipseConcept {

}




/**
 * A conceptual two-dimensional ellipse that has Cartesian coordinates.
 *
 * @param center
 * @param semiMajorAxisInPixels
 * @param semiMinorAxisInPixels
 *
 * @author Aleksi Lukkarinen
 */
class EllipseConcept private(
    center: Pos,
    semiMajorAxisInPixels: Double,
    semiMinorAxisInPixels: Double,
    shapeDataResolver: ShapeDataResolver)
    extends ConicSectionConcept[EllipseConcept](shapeDataResolver) {

  /** Enclosing rectangle of this ellipse. */
  lazy val boundary: Bounds = Bounds(
    Pos(
      position.xInPixels - semiMajorAxisInPixels,
      position.yInPixels - semiMinorAxisInPixels),
    Pos(
      position.xInPixels + semiMajorAxisInPixels,
      position.yInPixels + semiMinorAxisInPixels))

  /** Position of this ellipse. */
  lazy val position: Pos = boundary.upperLeftMarker

  /** Dimensions of this ellipse. */
  lazy val dimensions: Dims = Dims(
    boundary.width,
    boundary.height)

  /** Area of this ellipse. */
  val area: Area = Area.forEllipse(
    semiMajorAxisInPixels,
    semiMinorAxisInPixels)

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  override
  def moveBy(offsets: Double*): EllipseConcept = {
    copy(newCenter = center.moveBy(offsets: _*))
  }

  /**
   *
   *
   * @param newCenter
   * @param newSemiMajorAxisInPixels
   * @param newSemiMinorAxisInPixels
   *
   * @return
   */
  @inline
  def copy(
      newCenter: Pos = center,
      newSemiMajorAxisInPixels: Double = semiMajorAxisInPixels,
      newSemiMinorAxisInPixels: Double = semiMinorAxisInPixels): EllipseConcept = {

    new EllipseConcept(newCenter, newSemiMajorAxisInPixels, newSemiMinorAxisInPixels)
  }

}
