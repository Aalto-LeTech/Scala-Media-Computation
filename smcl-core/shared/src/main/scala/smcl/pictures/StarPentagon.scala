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
import smcl.infrastructure.ListUtils
import smcl.modeling.Angle
import smcl.modeling.d2.Pos
import smcl.settings._




/**
 * An object-based API for creating regular star (= concave) pentagons.
 *
 * @author Aleksi Lukkarinen
 */
object StarPentagon {

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param cuspRadiusInPixels
   *
   * @return
   */
  @inline
  def apply(
      widthInPixels: Double,
      heightInPixels: Double,
      cuspRadiusInPixels: Double): VectorGraphic = {

    apply(
      widthInPixels, heightInPixels,
      cuspRadiusInPixels,
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
   * @param cuspRadiusInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  @inline
  def apply(
      widthInPixels: Double,
      heightInPixels: Double,
      cuspRadiusInPixels: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    apply(
      widthInPixels, heightInPixels,
      cuspRadiusInPixels,
      Pos.Origo,
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param cuspRadiusInPixels
   * @param center
   *
   * @return
   */
  @inline
  def apply(
      widthInPixels: Double,
      heightInPixels: Double,
      cuspRadiusInPixels: Double,
      center: Pos): VectorGraphic = {

    apply(
      widthInPixels, heightInPixels,
      cuspRadiusInPixels,
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
   * @param cuspRadiusInPixels
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
      widthInPixels: Double,
      heightInPixels: Double,
      cuspRadiusInPixels: Double,
      center: Pos,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    if (widthInPixels < 0) {
      throw new IllegalArgumentException(
        s"Star pentagon's width cannot be negative (was: $widthInPixels).")
    }

    if (heightInPixels < 0) {
      throw new IllegalArgumentException(
        s"Star pentagon's width cannot be negative (was: $heightInPixels).")
    }

    val circumRadius = Pentagon.limitCircumRadiusTo(widthInPixels, heightInPixels)

    apply(
      circumRadius,
      cuspRadiusInPixels,
      center,
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param circumRadiusInPixels
   * @param cuspRadiusInPixels
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
      circumRadiusInPixels: Double = DefaultCircleRadiusInPixels,
      cuspRadiusInPixels: Double = DefaultStarCuspRadiusInPixels,
      center: Pos = Pos.Origo,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: rgb.Color = DefaultPrimaryColor,
      fillColor: rgb.Color = DefaultSecondaryColor): VectorGraphic = {

    if (circumRadiusInPixels < 0) {
      throw new IllegalArgumentException(
        s"Length of star pentagon's circumradius cannot be negative (was: $circumRadiusInPixels).")
    }

    if (cuspRadiusInPixels < 0) {
      throw new IllegalArgumentException(
        s"Length of star pentagon's cuspradius cannot be negative (was: $cuspRadiusInPixels).")
    }

    val outerPoints = Pentagon.pointsFor(circumRadiusInPixels, center, Angle.Zero).toList
    val innerPoints = cuspRadiusPointsFor(cuspRadiusInPixels, center).toList
    val points = ListUtils.intersperse(outerPoints, innerPoints)

    Polygon(
      points,
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param cuspRadiusInPixels
   * @param center
   *
   * @return
   */
  @inline
  def cuspRadiusPointsFor(
      cuspRadiusInPixels: Double,
      center: Pos): Seq[Pos] = {

    Pentagon.pointsFor(cuspRadiusInPixels, center, Pentagon.RotationalSymmetryAngle.half)
  }

}
