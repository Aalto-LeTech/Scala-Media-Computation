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
import smcl.modeling.d2.CircleConcept.CircleDataResolver




/**
 * A companion object for [[CircleConcept]].
 *
 * @author Aleksi Lukkarinen
 */
object CircleConcept {

  /**
   * Returns a new [[CircleConcept]] instance.
   *
   * @param center
   * @param radiusInPixels
   *
   * @return
   */
  @inline
  private
  def apply(
      center: Pos,
      radiusInPixels: Double): CircleConcept = {

    instantiateCircle(center, radiusInPixels)
  }

  /**
   * Returns a new [[CircleConcept]] instance with a new data resolver instance.
   *
   * @param center
   * @param radiusInPixels
   *
   * @return
   */
  @inline
  private
  def instantiateCircle(
      center: Pos,
      radiusInPixels: Double): CircleConcept = {

    val dataResolver =
      new CircleDataResolver(center, radiusInPixels)

    instantiateCircle(dataResolver)
  }

  /**
   * Returns a new [[CircleConcept]] instance with a given data resolver instance.
   *
   * @param dataResolver
   *
   * @return
   */
  @inline
  private
  def instantiateCircle(
      dataResolver: CircleDataResolver): CircleConcept = {

    new CircleConcept(dataResolver)
  }




  private[d2]
  class CircleDataResolver(
      val center: Pos,
      val radiusInPixels: Double)
      extends ShapeDataResolver {

    lazy val boundary: Bounds = Bounds(
      Pos(
        center.xInPixels - radiusInPixels,
        center.yInPixels - radiusInPixels),
      Pos(
        center.xInPixels + radiusInPixels,
        center.yInPixels + radiusInPixels))

    lazy val position: Pos =
      boundary.upperLeftMarker

    lazy val dimensions: Dims =
      Dims(boundary.width, boundary.height)

    lazy val area: Area =
      Area.forCircle(radiusInPixels)
  }




}




/**
 * A conceptual two-dimensional circle that has Cartesian coordinates.
 *
 * @param dataResolver
 *
 * @author Aleksi Lukkarinen
 */
class CircleConcept private(dataResolver: CircleDataResolver)
    extends ConicSectionConcept[CircleConcept](dataResolver) {

  /**
   * Returns the center point of this circle.
   *
   * @return
   */
  def center: Pos = dataResolver.center

  /**
   * Returns the radius of this circle in pixels.
   *
   * @return
   */
  def radiusInPixels: Double = dataResolver.radiusInPixels

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  @inline
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

    if (newCenter != center || newRadiusInPixels != radiusInPixels) {
      return CircleConcept(newCenter, newRadiusInPixels)
    }

    this
  }

}
