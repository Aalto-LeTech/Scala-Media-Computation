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
import smcl.modeling.d2.RectangleConcept.RectangleDataResolver




/**
 * A companion object for [[RectangleConcept]].
 *
 * @author Aleksi Lukkarinen
 */
object RectangleConcept {

  /**
   *
   *
   * @param sideLength
   *
   * @return
   */
  @inline
  def apply(sideLength: Double): RectangleConcept = {
    apply(sideLength, Pos.Origo)
  }

  /**
   *
   *
   * @param sideLength
   * @param center
   *
   * @return
   */
  @inline
  def apply(sideLength: Double, center: Pos): RectangleConcept = {
    apply(sideLength, sideLength, center)
  }

  /**
   *
   *
   * @param baseLength
   * @param height
   *
   * @return
   */
  @inline
  def apply(
      baseLength: Double,
      height: Double): RectangleConcept = {

    apply(baseLength, height, Pos.Origo)
  }

  /**
   *
   *
   * @param baseLength
   * @param height
   *
   * @return
   */
  @inline
  def apply(
      baseLength: Double,
      height: Double,
      center: Pos): RectangleConcept = {

    val halfBase = baseLength / 2.0
    val halfHeight = height / 2.0

    val cornerPoints = Seq(
      Pos(-halfBase, halfHeight),
      Pos(halfBase, halfHeight),
      Pos(halfBase, -halfHeight),
      Pos(-halfBase, -halfHeight))

    instantiateRectangle(cornerPoints)
  }

  /**
   * Returns a new [[RectangleConcept]] instance with a new data resolver instance.
   *
   * @param points
   *
   * @return
   */
  @inline
  private
  def instantiateRectangle(
      points: Seq[Pos]): RectangleConcept = {

    val dataResolver =
      new RectangleDataResolver(points)

    instantiateRectangle(dataResolver)
  }

  /**
   * Returns a new [[RectangleConcept]] instance with a given data resolver instance.
   *
   * @param dataResolver
   *
   * @return
   */
  @inline
  private
  def instantiateRectangle(
      dataResolver: RectangleDataResolver): RectangleConcept = {

    new RectangleConcept(dataResolver)
  }




  private[d2]
  class RectangleDataResolver(
      val points: Seq[Pos])
      extends PolygonDataResolver {

    lazy val boundary: Bounds =
      Bounds(points.head, points(1), points(2), points(3))

    lazy val position: Pos =
      boundary.upperLeftMarker

    lazy val dimensions: Dims =
      Dims(boundary.width, boundary.height)

    lazy val area: Area =
      Area.forRectangle(boundary.width, boundary.height)
  }




}




/**
 * A conceptual two-dimensional rectangle that has Cartesian coordinates.
 *
 * @param dataResolver
 *
 * @author Aleksi Lukkarinen
 */
class RectangleConcept private(dataResolver: RectangleDataResolver)
    extends PolygonConcept[RectangleConcept](dataResolver) {

  /** The first corner of this rectangle. */
  val firstCorner: Pos = points.head

  /** The second corner of this rectangle. */
  val secondCorner: Pos = points(1)

  /** The third corner of this rectangle. */
  val thirdCorner: Pos = points(2)

  /** The fourth corner of this rectangle. */
  val fourthCorner: Pos = points(3)

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  @inline
  def moveBy(offsets: Double*): RectangleConcept = {
    val movedPoints = points map {_.moveBy(offsets: _*)}

    RectangleConcept.instantiateRectangle(movedPoints)
  }


  /**
   * Returns a "copy" of this rectangle. As it makes no sense to allow arbitrary individual modifications of
   * rectangles' corner points and because these objects are immutable, this method returns a reference to
   * this same object and exists only for completeness' sake.
   *
   * @return
   */
  @inline
  def copy(): RectangleConcept = this

}
