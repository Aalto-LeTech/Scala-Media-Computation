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
import smcl.modeling.d2.PentagonConcept.PentagonDataResolver




/**
 * A companion object for [[PentagonConcept]].
 *
 * @author Aleksi Lukkarinen
 */
object PentagonConcept {

  /**
   * Returns a new [[PentagonConcept]] instance.
   *
   * @param center
   * @param circumRadiusInPixels
   *
   * @return
   */
  @inline
  private
  def apply(
      center: Pos,
      circumRadiusInPixels: Double): PentagonConcept = {

    val points: Seq[Pos] = ???

    instantiatePentagon(center, circumRadiusInPixels, points)
  }

  /**
   * Returns a new [[PentagonConcept]] instance with a new data resolver instance.
   *
   * @param points
   *
   * @return
   */
  @inline
  private
  def instantiatePentagon(
      center: Pos,
      circumRadiusInPixels: Double,
      points: Seq[Pos]): PentagonConcept = {

    val dataResolver =
      new PentagonDataResolver(
        center, circumRadiusInPixels, points)

    instantiatePentagon(dataResolver)
  }

  /**
   * Returns a new [[PentagonConcept]] instance with a given data resolver instance.
   *
   * @param dataResolver
   *
   * @return
   */
  @inline
  private
  def instantiatePentagon(
      dataResolver: PentagonDataResolver): PentagonConcept = {

    new PentagonConcept(dataResolver)
  }




  private[d2]
  class PentagonDataResolver(
      val center: Pos,
      val circumRadiusInPixels: Double,
      val points: Seq[Pos])
      extends PolygonDataResolver {

    lazy val boundary: Bounds =
      BoundaryCalculator.fromPositions(points)

    lazy val position: Pos =
      boundary.upperLeftMarker

    lazy val dimensions: Dims =
      Dims(boundary.width, boundary.height)

    lazy val area: Area =
      Area.forPentagon(circumRadiusInPixels)

  }




}




/**
 * A conceptual two-dimensional pentagon that has Cartesian coordinates.
 *
 * @param dataResolver
 *
 * @author Aleksi Lukkarinen
 */
class PentagonConcept(dataResolver: PentagonDataResolver)
    extends PolygonConcept[PentagonConcept](dataResolver) {

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  override
  def moveBy(offsets: Double*): PentagonConcept = {
    val movedPoints = points map {_.moveBy(offsets: _*)}

    val updatedDataResolver =
      new PentagonDataResolver(
        dataResolver.center,
        dataResolver.circumRadiusInPixels,
        movedPoints)

    PentagonConcept.instantiatePentagon(updatedDataResolver)
  }

  /**
   * Returns a "copy" of this pentagon. As it makes no sense to allow arbitrary individual modifications of
   * pentagon' corner points and because these objects are immutable, this method returns a reference to
   * this same object and exists only for completeness' sake.
   *
   * @return
   */
  @inline
  def copy(): PentagonConcept = this

}
