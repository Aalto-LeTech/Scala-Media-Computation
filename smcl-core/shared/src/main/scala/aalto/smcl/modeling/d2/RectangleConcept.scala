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
import aalto.smcl.modeling.d2.RectangleConcept.instantiateRectangle




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

    val firstCorner = Pos(-halfBase, halfHeight)
    val secondCorner = Pos(halfBase, halfHeight)
    val thirdCorner = Pos(halfBase, -halfHeight)
    val fourthCorner = Pos(-halfBase, -halfHeight)

    instantiateRectangle(
      firstCorner,
      secondCorner,
      thirdCorner,
      fourthCorner)
  }

  /**
   * Returns a new [[RectangleConcept]] instance with a new data resolver instance.
   *
   * @param firstCorner
   * @param secondCorner
   * @param thirdCorner
   * @param fourthCorner
   *
   * @return
   */
  @inline
  private
  def instantiateRectangle(
      firstCorner: Pos,
      secondCorner: Pos,
      thirdCorner: Pos,
      fourthCorner: Pos): RectangleConcept = {

    val dataResolver =
      new RectangleDataResolver(
        firstCorner, secondCorner, thirdCorner, fourthCorner)

    instantiateRectangle(
      firstCorner, secondCorner,
      thirdCorner, fourthCorner,
      dataResolver)
  }

  /**
   * Returns a new [[RectangleConcept]] instance with a given data resolver instance.
   *
   * @param firstCorner
   * @param secondCorner
   * @param thirdCorner
   * @param fourthCorner
   * @param dataResolver
   *
   * @return
   */
  @inline
  private
  def instantiateRectangle(
      firstCorner: Pos,
      secondCorner: Pos,
      thirdCorner: Pos,
      fourthCorner: Pos,
      dataResolver: ShapeDataResolver): RectangleConcept = {

    new RectangleConcept(
      firstCorner, secondCorner,
      thirdCorner, fourthCorner,
      dataResolver)
  }




  private[d2]
  class RectangleDataResolver(
      val firstCorner: Pos,
      val secondCorner: Pos,
      val thirdCorner: Pos,
      val fourthCorner: Pos)
      extends ShapeDataResolver {

    lazy val boundary: Bounds =
      Bounds(firstCorner, secondCorner, thirdCorner, fourthCorner)

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
 * @param firstCorner
 * @param secondCorner
 * @param thirdCorner
 * @param fourthCorner
 *
 * @author Aleksi Lukkarinen
 */
class RectangleConcept(
    val firstCorner: Pos,
    val secondCorner: Pos,
    val thirdCorner: Pos,
    val fourthCorner: Pos,
    shapeDataResolver: ShapeDataResolver)
    extends PolygonConcept[RectangleConcept](shapeDataResolver) {

  /** The corner points of this rectangle. */
  val points: Seq[Pos] = Seq(
    firstCorner,
    secondCorner,
    thirdCorner,
    fourthCorner)

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  override
  def moveBy(offsets: Double*): RectangleConcept = {
    val newFirstCorner = firstCorner.moveBy(offsets: _*)
    val newSecondCorner = secondCorner.moveBy(offsets: _*)
    val newThirdCorner = thirdCorner.moveBy(offsets: _*)
    val newFourthCorner = fourthCorner.moveBy(offsets: _*)

    instantiateRectangle(
      newFirstCorner,
      newSecondCorner,
      newThirdCorner,
      newFourthCorner)
  }

}
