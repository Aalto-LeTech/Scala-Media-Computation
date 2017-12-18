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


import aalto.smcl.infrastructure.MathUtils
import aalto.smcl.modeling.Area
import aalto.smcl.modeling.d2.TriangleConcept.TriangleDataResolver




/**
 * A companion object for [[TriangleConcept]].
 *
 * @author Aleksi Lukkarinen
 */
object TriangleConcept {

  /** The number of corners (i.e., three) in a triangle. */
  val NumberOfCornersInTriangle: Int = 3

  /** Height of an equilateral triangle as a factor of the length of its side. */
  val HeightOfEquilateralTriangleAsFactorOfSide: Double = math.sqrt(3) / 2.0

  /** Side length of an equilateral triangle as a factor of its halfHeight. */
  val SideOfEquilateralTriangleAsFactorOfHeight: Double =
    1.0 / HeightOfEquilateralTriangleAsFactorOfSide

  /**
   * Creates a new equilateral triangle.
   *
   * @param sideLength
   *
   * @return
   */
  @inline
  def apply(sideLength: Double): TriangleConcept = {
    apply(sideLength, Pos.Origo)
  }

  /**
   * Creates a new equilateral triangle that has a specific center point.
   *
   * @param sideLength
   * @param center
   *
   * @return
   */
  @inline
  def apply(
      sideLength: Double,
      center: Pos): TriangleConcept = {

    val halfHeight: Double =
      HeightOfEquilateralTriangleAsFactorOfSide * sideLength / 2.0

    createIsosceles(halfHeight, sideLength / 2.0, center)
  }

  /**
   * Creates a new equilateral triangle.
   *
   * @param height
   *
   * @return
   */
  @inline
  def basedOnHeight(height: Double): TriangleConcept = {
    basedOnHeight(height, Pos.Origo)
  }

  /**
   * Creates a new equilateral triangle that has a specific center point.
   *
   * @param height
   * @param center
   *
   * @return
   */
  @inline
  def basedOnHeight(
      height: Double,
      center: Pos): TriangleConcept = {

    val halfSide: Double =
      SideOfEquilateralTriangleAsFactorOfHeight * height / 2.0

    createIsosceles(height / 2.0, halfSide, center)
  }

  /**
   * Creates a new isosceles triangle.
   *
   * @param sideLength
   * @param baseLength
   *
   * @return
   */
  @inline
  def apply(
      sideLength: Double,
      baseLength: Double): TriangleConcept = {

    apply(sideLength, baseLength, Pos.Origo)
  }

  /**
   * Creates a new isosceles triangle that has a specific center point.
   *
   * @param sideLength
   * @param baseLength
   * @param center
   *
   * @return
   */
  @inline
  def apply(
      sideLength: Double,
      baseLength: Double,
      center: Pos): TriangleConcept = {

    val halfHeight: Double =
      math.sqrt(sideLength * sideLength - baseLength * baseLength / 4.0) / 2.0

    createIsosceles(halfHeight, baseLength / 2.0, center)
  }

  /**
   * Creates a new isosceles triangle.
   *
   * @param height
   * @param baseLength
   *
   * @return
   */
  @inline
  def basedOnHeightAndBase(
      height: Double,
      baseLength: Double): TriangleConcept = {

    basedOnHeightAndBase(height, baseLength, Pos.Origo)
  }

  /**
   * Creates a new isosceles triangle that has a specific center point.
   *
   * @param height
   * @param baseLength
   * @param center
   *
   * @return
   */
  @inline
  def basedOnHeightAndBase(
      height: Double,
      baseLength: Double,
      center: Pos): TriangleConcept = {

    createIsosceles(height / 2.0, baseLength / 2.0, center)
  }

  /**
   * Creates a new isosceles triangle that has a specific center point.
   *
   * @param halfHeight
   * @param halfBase
   * @param center
   *
   * @return
   */
  private
  def createIsosceles(
      halfHeight: Double,
      halfBase: Double,
      center: Pos): TriangleConcept = {

    val firstCorner: Pos = Pos(
      center.xInPixels,
      center.yInPixels + halfHeight)

    val bottomY = center.yInPixels - halfHeight

    val secondCorner: Pos = Pos(
      center.xInPixels + halfBase,
      bottomY)

    val thirdCorner: Pos = Pos(
      center.xInPixels - halfBase,
      bottomY)

    apply(firstCorner, secondCorner, thirdCorner)
  }

  /**
   * Creates a new (expectedly) scalene triangle.
   *
   * @param baseLength
   * @param leftSideLength
   * @param rightSideLength
   *
   * @return
   */
  def apply(
      baseLength: Double,
      leftSideLength: Double,
      rightSideLength: Double): TriangleConcept = {

    apply(
      baseLength,
      leftSideLength,
      rightSideLength,
      Pos.Origo)
  }

  /**
   * Creates a new (expectedly) scalene triangle that has a specific center point.
   *
   * @param baseLength
   * @param leftSideLength
   * @param rightSideLength
   *
   * @return
   */
  def apply(
      baseLength: Double,
      leftSideLength: Double,
      rightSideLength: Double,
      center: Pos): TriangleConcept = {

    val halfBase: Double = baseLength / 2.0

    val leftAngle: Double =
      MathUtils.acos(
        (leftSideLength * leftSideLength +
            baseLength * baseLength -
            rightSideLength * rightSideLength) /
            (2 * leftSideLength * baseLength))

    val prelimTop =
      Pos(-halfBase + leftSideLength, 0)
          .rotateBy(-leftAngle)

    val halfHeight = prelimTop.yInPixels / 2.0

    val firstCorner: Pos = Pos(
      center.xInPixels + prelimTop.xInPixels,
      center.yInPixels + halfHeight)

    val bottomY = center.yInPixels - halfHeight

    val secondCorner: Pos = Pos(
      center.xInPixels + halfBase,
      bottomY)

    val thirdCorner: Pos = Pos(
      center.xInPixels - halfBase,
      bottomY)

    apply(firstCorner, secondCorner, thirdCorner)
  }

  /**
   * Creates a new (expectedly) scalene triangle.
   *
   * @param firstCorner
   * @param secondCorner
   * @param thirdCorner
   *
   * @return
   */
  def apply(
      firstCorner: Pos,
      secondCorner: Pos,
      thirdCorner: Pos): TriangleConcept = {

    val points: Seq[Pos] =
      Seq(firstCorner, secondCorner, thirdCorner)

    apply(points)
  }

  /**
   * Creates a new (expectedly) scalene triangle.
   *
   * @param points
   *
   * @return
   */
  def apply(points: Seq[Pos]): TriangleConcept = {
    require(points.lengthCompare(NumberOfCornersInTriangle) == 0,
      s"A triangle has to have exactly $NumberOfCornersInTriangle corner points (given: ${points.length})")

    val dataResolver = new TriangleDataResolver(points)

    instantiateTriangle(dataResolver)
  }

  /**
   * Returns a new [[TriangleConcept]] instance with a given data resolver instance.
   *
   * @param dataResolver
   *
   * @return
   */
  @inline
  private
  def instantiateTriangle(
      dataResolver: TriangleDataResolver): TriangleConcept = {

    new TriangleConcept(dataResolver)
  }




  private[d2]
  class TriangleDataResolver(
      val points: Seq[Pos])
      extends PolygonDataResolver {

    lazy val boundary: Bounds =
      BoundaryCalculator.fromPositions(points)

    lazy val position: Pos =
      boundary.upperLeftMarker

    lazy val dimensions: Dims =
      Dims(boundary.width, boundary.height)

    lazy val area: Area =
      throw new NotImplementedError(
        "Area calculation for an arbitrary triangle has not been implemented yet.")

  }




}




/**
 * A conceptual two-dimensional triangle that has Cartesian coordinates.
 *
 * @param dataResolver
 *
 * @author Aleksi Lukkarinen
 */
class TriangleConcept private(dataResolver: TriangleDataResolver)
    extends PolygonConcept[TriangleConcept](dataResolver) {

  /** The first corner point of this triangle. */
  val firstCorner: Pos = points.head

  /** The second corner point of this triangle. */
  val secondCorner: Pos = points(1)

  /** The third corner point of this triangle. */
  val thirdCorner: Pos = points(2)


  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  @inline
  override
  def moveBy(offsets: Double*): TriangleConcept = {
    val movedPoints = points map {_.moveBy(offsets: _*)}

    TriangleConcept(movedPoints)
  }

  /**
   *
   *
   * @param newFirstCorner
   * @param newSecondCorner
   * @param newThirdCorner
   *
   * @return
   */
  @inline
  def copy(
      newFirstCorner: Pos = firstCorner,
      newSecondCorner: Pos = secondCorner,
      newThirdCorner: Pos = thirdCorner): TriangleConcept = {

    if (newFirstCorner != firstCorner
        || newSecondCorner != secondCorner
        || newThirdCorner != thirdCorner) {

      TriangleConcept(firstCorner, secondCorner, thirdCorner)
    }

    this
  }

}
