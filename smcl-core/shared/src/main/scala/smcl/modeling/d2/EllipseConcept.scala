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

package smcl.modeling.d2


import smcl.modeling.Area




/**
 * A companion object for [[EllipseConcept]].
 *
 * @author Aleksi Lukkarinen
 */
object EllipseConcept {

  /**
   * Returns a new [[EllipseConcept]] instance.
   *
   * @param center
   * @param semiMajorAxisInPixels
   * @param semiMinorAxisInPixels
   *
   * @return
   */
  @inline
  private
  def apply(
      center: Pos,
      semiMajorAxisInPixels: Double,
      semiMinorAxisInPixels: Double): EllipseConcept = {

    instantiateEllipse(
      center,
      semiMajorAxisInPixels,
      semiMinorAxisInPixels)
  }

  /**
   * Returns a new [[EllipseConcept]] instance with a new data resolver instance.
   *
   * @param center
   * @param semiMajorAxisInPixels
   * @param semiMinorAxisInPixels
   *
   * @return
   */
  @inline
  private
  def instantiateEllipse(
      center: Pos,
      semiMajorAxisInPixels: Double,
      semiMinorAxisInPixels: Double): EllipseConcept = {

    val dataResolver =
      new EllipseDataResolver(
        center,
        semiMajorAxisInPixels,
        semiMinorAxisInPixels)

    instantiateEllipse(
      center,
      semiMajorAxisInPixels,
      semiMinorAxisInPixels,
      dataResolver)
  }

  /**
   * Returns a new [[EllipseConcept]] instance with a given data resolver instance.
   *
   * @param center
   * @param semiMajorAxisInPixels
   * @param semiMinorAxisInPixels
   * @param dataResolver
   *
   * @return
   */
  @inline
  private
  def instantiateEllipse(
      center: Pos,
      semiMajorAxisInPixels: Double,
      semiMinorAxisInPixels: Double,
      dataResolver: ShapeDataResolver): EllipseConcept = {

    new EllipseConcept(
      center,
      semiMajorAxisInPixels,
      semiMinorAxisInPixels,
      dataResolver)
  }




  private[d2]
  class EllipseDataResolver(
      val center: Pos,
      val semiMajorAxisInPixels: Double,
      val semiMinorAxisInPixels: Double)
      extends ShapeDataResolver {

    lazy val boundary: Bounds = Bounds(
      Pos(
        center.xInPixels - semiMajorAxisInPixels,
        center.yInPixels - semiMinorAxisInPixels),
      Pos(
        center.xInPixels + semiMajorAxisInPixels,
        center.yInPixels + semiMinorAxisInPixels))

    lazy val position: Pos =
      boundary.upperLeftMarker

    lazy val dimensions: Dims =
      Dims(boundary.width, boundary.height)

    lazy val area: Area =
      Area.forEllipse(
        semiMajorAxisInPixels,
        semiMinorAxisInPixels)
  }




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

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  @inline
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

    if (newCenter != center
        || newSemiMajorAxisInPixels != semiMajorAxisInPixels
        || newSemiMinorAxisInPixels != semiMinorAxisInPixels) {

      return EllipseConcept(
        newCenter,
        newSemiMajorAxisInPixels,
        newSemiMinorAxisInPixels)
    }

    this
  }

}
