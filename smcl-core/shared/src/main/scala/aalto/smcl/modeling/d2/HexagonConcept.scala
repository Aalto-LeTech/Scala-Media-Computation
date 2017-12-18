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
import aalto.smcl.modeling.d2.HexagonConcept.HexagonDataResolver




/**
 * A companion object for [[HexagonConcept]].
 *
 * @author Aleksi Lukkarinen
 */
object HexagonConcept {

  /**
   * Returns a new [[aalto.smcl.modeling.d2.HexagonConcept]] instance.
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
      circumRadiusInPixels: Double): HexagonConcept = {

    val points: Seq[Pos] = ???

    instantiateHexagon(center, circumRadiusInPixels, points)
  }

  /**
   * Returns a new [[HexagonConcept]] instance with a new data resolver instance.
   *
   * @param points
   *
   * @return
   */
  @inline
  private
  def instantiateHexagon(
      center: Pos,
      circumRadiusInPixels: Double,
      points: Seq[Pos]): HexagonConcept = {

    val dataResolver =
      new HexagonDataResolver(
        center, circumRadiusInPixels, points)

    instantiateHexagon(dataResolver)
  }

  /**
   * Returns a new [[HexagonConcept]] instance with a given data resolver instance.
   *
   * @param dataResolver
   *
   * @return
   */
  @inline
  private
  def instantiateHexagon(
      dataResolver: HexagonDataResolver): HexagonConcept = {

    new HexagonConcept(dataResolver)
  }




  private[d2]
  class HexagonDataResolver(
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
      Area.forHexagon(circumRadiusInPixels)

  }




}




/**
 * A conceptual two-dimensional hexagon that has Cartesian coordinates.
 *
 * @param dataResolver
 *
 * @author Aleksi Lukkarinen
 */
class HexagonConcept(dataResolver: HexagonDataResolver)
    extends PolygonConcept[HexagonConcept](dataResolver) {

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  override
  def moveBy(offsets: Double*): HexagonConcept = {
    val movedPoints = points map {_.moveBy(offsets: _*)}

    val updatedDataResolver =
      new HexagonDataResolver(
        dataResolver.center,
        dataResolver.circumRadiusInPixels,
        movedPoints)

    HexagonConcept.instantiateHexagon(updatedDataResolver)
  }

  /**
   * Returns a "copy" of this hexagon. As it makes no sense to allow arbitrary individual modifications of
   * hexagons' corner points and because these objects are immutable, this method returns a reference to
   * this same object and exists only for completeness' sake.
   *
   * @return
   */
  @inline
  def copy(): HexagonConcept = this

}
