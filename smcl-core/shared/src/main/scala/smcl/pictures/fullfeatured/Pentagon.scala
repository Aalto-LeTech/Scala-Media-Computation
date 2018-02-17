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

package smcl.pictures.fullfeatured


import smcl.colors.rgb
import smcl.modeling.d2.Pos
import smcl.settings.{DefaultPrimaryColor, DefaultSecondaryColor, ShapesHaveBordersByDefault, ShapesHaveFillingsByDefault}




/**
 * An object-based API for creating regular convex pentagons.
 *
 * @author Aleksi Lukkarinen
 */
object Pentagon {

  /** Magnitude of regular convex pentagon's internal angles. */
  val InternalAngleInDegrees: Int = 108

  /** The ratio of regular convex pentagon's height and side length. */
  lazy val HeightPerSideRatio: Double = Math.sqrt(5.0 + 2.0 * Math.sqrt(5.0))

  /** The ratio of regular convex pentagon's diagonal length (i.e., width) and side length. */
  lazy val DiagonalPerSideRatio: Double = (1.0 + Math.sqrt(5.0)) / 2.0

  /** The ratio of regular convex pentagon's diagonal length (i.e., width) and height. */
  lazy val DiagonalPerHeightRatio: Double = DiagonalPerSideRatio / HeightPerSideRatio

  /** The ratio of regular convex pentagon's diagonal length and circumradius. */
  lazy val DiagonalPerCircumradiusRatio: Double = Math.sqrt((5.0 + Math.sqrt(5.0)) / 2.0)

  /**
   *
   *
   * @param width
   * @param height
   *
   * @return
   */
  @inline
  def apply(
      width: Double,
      height: Double): VectorGraphic = {

    apply(
      width, height,
      hasBorder = ShapesHaveBordersByDefault,
      hasFilling = ShapesHaveFillingsByDefault,
      color = DefaultPrimaryColor,
      fillColor = DefaultSecondaryColor)
  }

  /**
   *
   *
   * @param width
   * @param height
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  @inline
  def apply(
      width: Double,
      height: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    apply(
      width, height,
      Pos.Origo,
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param width
   * @param height
   * @param center
   *
   * @return
   */
  @inline
  def apply(
      width: Double,
      height: Double,
      center: Pos): VectorGraphic = {

    apply(
      width, height,
      center,
      hasBorder = ShapesHaveBordersByDefault,
      hasFilling = ShapesHaveFillingsByDefault,
      color = DefaultPrimaryColor,
      fillColor = DefaultSecondaryColor)
  }

  /**
   *
   *
   * @param width
   * @param height
   * @param center
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  @inline
  def apply(
      width: Double,
      height: Double,
      center: Pos,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    val heightBasedDiagonal = diagonalFromHeight(height)
    val effectiveDiagonal = heightBasedDiagonal.min(width)
    val circumRadius = circumRadiusFromDiagonal(effectiveDiagonal)

    apply(
      circumRadius,
      center,
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
  @inline
  def apply(
      circumRadiusInPixels: Double,
      center: Pos = Pos.Origo,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: rgb.Color = DefaultPrimaryColor,
      fillColor: rgb.Color = DefaultSecondaryColor): VectorGraphic = {

    val p1 = center - (0, circumRadiusInPixels)
    val p2 = p1.rotateBy(72)
    val p3 = p2.rotateBy(72)
    val p4 = p3.rotateBy(72)
    val p5 = p4.rotateBy(72)

    val points: Seq[Pos] = Seq(p1, p2, p3, p4, p5)

    Polygon(
      points,
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param height
   *
   * @return
   */
  @inline
  def diagonalFromHeight(height: Double): Double =
    DiagonalPerHeightRatio * height

  /**
   *
   *
   * @param side
   *
   * @return
   */
  @inline
  def diagonalLengthFromSideLength(side: Double): Double =
    DiagonalPerSideRatio * side

  /**
   *
   *
   * @param diagonal
   *
   * @return
   */
  @inline
  def sideLengthFromDiagonalLength(diagonal: Double): Double =
    diagonal / DiagonalPerSideRatio

  /**
   *
   *
   * @param height
   *
   * @return
   */
  @inline
  def sideLengthFromHeight(height: Double): Double =
    height / HeightPerSideRatio

  /**
   *
   *
   * @param diagonal
   *
   * @return
   */
  @inline
  def circumRadiusFromDiagonal(diagonal: Double): Double =
    diagonal / DiagonalPerCircumradiusRatio

}
