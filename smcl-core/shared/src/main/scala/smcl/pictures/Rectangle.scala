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

import smcl.colors
import smcl.colors.rgb
import smcl.modeling.d2.Pos
import smcl.settings._




/**
 * An object-based API for creating rectangles.
 *
 * @author Aleksi Lukkarinen
 */
object Rectangle {

  /**
   *
   *
   * @param sideLength
   *
   * @return
   */
  @inline
  def apply(sideLength: Double): VectorGraphic =
    apply(
      sideLength,
      hasBorder = ShapesHaveBordersByDefault,
      hasFilling = ShapesHaveFillingsByDefault,
      color = DefaultPrimaryColor,
      fillColor = DefaultSecondaryColor)

  /**
   *
   *
   * @param sideLength
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  @inline
  def apply(
      sideLength: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: colors.rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    apply(
      sideLength,
      Pos.Origo,
      hasBorder, hasFilling,
      color, fillColor)
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
  def apply(
      sideLength: Double,
      center: Pos): VectorGraphic = {

    apply(
      sideLength, sideLength,
      center,
      hasBorder = ShapesHaveBordersByDefault,
      hasFilling = ShapesHaveFillingsByDefault,
      color = DefaultPrimaryColor,
      fillColor = DefaultSecondaryColor)
  }

  /**
   *
   *
   * @param sideLength
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
      sideLength: Double,
      center: Pos,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    apply(
      sideLength, sideLength,
      center,
      hasBorder, hasFilling,
      color, fillColor)
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
      height: Double): VectorGraphic = {

    apply(
      baseLength, height,
      Pos.Origo,
      hasBorder = ShapesHaveBordersByDefault,
      hasFilling = ShapesHaveFillingsByDefault,
      color = DefaultPrimaryColor,
      fillColor = DefaultSecondaryColor)
  }

  /**
   *
   *
   * @param baseLength
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
      baseLength: Double,
      height: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    apply(
      baseLength, height,
      Pos.Origo,
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param baseLength
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
      baseLength: Double,
      height: Double,
      center: Pos,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: rgb.Color = DefaultPrimaryColor,
      fillColor: rgb.Color = DefaultSecondaryColor): VectorGraphic = {

    val halfBase = baseLength / 2.0
    val halfHeight = height / 2.0

    val cornerPoints = Seq(
      Pos(-halfBase, halfHeight),
      Pos(halfBase, halfHeight),
      Pos(halfBase, -halfHeight),
      Pos(-halfBase, -halfHeight))

    // TODO: When no filling, create a Polyline, after it is implemented
    Polygon(
      cornerPoints,
      hasBorder, hasFilling,
      color, fillColor)
  }

}
