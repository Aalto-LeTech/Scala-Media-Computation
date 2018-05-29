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

package smcl.pictures


import smcl.colors.rgb
import smcl.modeling.Angle
import smcl.modeling.d2.Pos
import smcl.settings._




/**
 * An object-based API for creating regular convex pentagons.
 *
 * @author Aleksi Lukkarinen
 */
object Pentagon {

  /** Magnitude of regular convex pentagon's internal angles. */
  lazy val InternalAngle: Angle = Angle(108)

  /** Magnitude of regular convex pentagon's rotational symmetry angle. */
  lazy val RotationalSymmetryAngle = Angle(72)

  /** The ratio of regular convex pentagon's height and side length. */
  lazy val HeightPerSideRatio: Double = Math.sqrt(5.0 + 2.0 * Math.sqrt(5.0)) / 2.0

  /** The ratio of regular convex pentagon's diagonal length (i.e., width) and side length. */
  lazy val DiagonalPerSideRatio: Double = (1.0 + Math.sqrt(5.0)) / 2.0

  /** The ratio of regular convex pentagon's diagonal length (i.e., width) and height. */
  lazy val DiagonalPerHeightRatio: Double =
    (1.0 + Math.sqrt(5.0)) / Math.sqrt(5.0 + 2.0 * Math.sqrt(5.0))

  /** The ratio of regular convex pentagon's diagonal length and circumradius. */
  lazy val DiagonalPerCircumradiusRatio: Double = Math.sqrt((5.0 + Math.sqrt(5.0)) / 2.0)

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  def apply(
      widthInPixels: Double,
      heightInPixels: Double): VectorGraphic = {

    apply(
      widthInPixels, heightInPixels,
      hasBorder = ShapesHaveBordersByDefault,
      hasFilling = ShapesHaveFillingsByDefault,
      color = DefaultPrimaryColor,
      fillColor = DefaultSecondaryColor)
  }

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def apply(
      widthInPixels: Double,
      heightInPixels: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    apply(
      widthInPixels, heightInPixels,
      Pos.Origo,
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param center
   *
   * @return
   */
  def apply(
      widthInPixels: Double,
      heightInPixels: Double,
      center: Pos): VectorGraphic = {

    apply(
      widthInPixels, heightInPixels,
      center,
      hasBorder = ShapesHaveBordersByDefault,
      hasFilling = ShapesHaveFillingsByDefault,
      color = DefaultPrimaryColor,
      fillColor = DefaultSecondaryColor)
  }

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param center
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def apply(
      widthInPixels: Double,
      heightInPixels: Double,
      center: Pos,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    if (widthInPixels < 0) {
      throw new IllegalArgumentException(
        s"Pentagon's width cannot be negative (was: $widthInPixels).")
    }

    if (heightInPixels < 0) {
      throw new IllegalArgumentException(
        s"Pentagon's height cannot be negative (was: $heightInPixels).")
    }

    val circumRadius = limitCircumRadiusTo(widthInPixels, heightInPixels)
    val points = pointsFor(circumRadius, Angle.Zero)
    val refY = 2 * circumRadius - heightInPixels

    Polygon(
      center,
      points,
      Pos(0, -refY),
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param circumRadiusInPixels
   * @param center
   *
   * @return
   */
  def apply(
      circumRadiusInPixels: Double,
      center: Pos = Pos.Origo,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: rgb.Color = DefaultPrimaryColor,
      fillColor: rgb.Color = DefaultSecondaryColor): VectorGraphic = {

    if (circumRadiusInPixels < 0) {
      throw new IllegalArgumentException(
        s"Length of pentagon's circumradius cannot be negative (was: $circumRadiusInPixels).")
    }

    val points = pointsFor(circumRadiusInPixels, Angle.Zero)

    Polygon(
      center,
      points,
      Pos.Origo,
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param circumRadiusInPixels
   *
   * @return
   */
  def pointsFor(
      circumRadiusInPixels: Double,
      startAngle: Angle): Seq[Pos] = {

    val firstPointCandidate = Pos(0, -circumRadiusInPixels)
    val firstPoint = firstPointCandidate.rotateByAroundOrigo(startAngle.inDegrees)

    val symmetryAngle = RotationalSymmetryAngle.inDegrees
    val rotationAngles = Seq.tabulate(5)(n => n * symmetryAngle).tail

    firstPoint +: rotationAngles.map(firstPoint.rotateByAroundOrigo)
  }

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  def limitCircumRadiusTo(
      widthInPixels: Double,
      heightInPixels: Double): Double = {

    val heightBasedDiagonal = diagonalFromHeight(heightInPixels)
    val effectiveDiagonal = heightBasedDiagonal.min(widthInPixels)

    circumRadiusFromDiagonal(effectiveDiagonal)
  }

  /**
   *
   *
   * @param height
   *
   * @return
   */
  def diagonalFromHeight(height: Double): Double =
    DiagonalPerHeightRatio * height

  /**
   *
   *
   * @param side
   *
   * @return
   */
  def diagonalLengthFromSideLength(side: Double): Double =
    DiagonalPerSideRatio * side

  /**
   *
   *
   * @param diagonal
   *
   * @return
   */
  def sideLengthFromDiagonalLength(diagonal: Double): Double =
    diagonal / DiagonalPerSideRatio

  /**
   *
   *
   * @param height
   *
   * @return
   */
  def sideLengthFromHeight(height: Double): Double =
    height / HeightPerSideRatio

  /**
   *
   *
   * @param diagonal
   *
   * @return
   */
  def circumRadiusFromDiagonal(diagonal: Double): Double =
    diagonal / DiagonalPerCircumradiusRatio

}
