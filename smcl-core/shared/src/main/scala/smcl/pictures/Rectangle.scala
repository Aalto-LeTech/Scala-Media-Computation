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
import smcl.infrastructure.DoubleWrapper
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
   * @param sideLengthInPixels
   *
   * @return
   */
  def apply(sideLengthInPixels: Double): VectorGraphic =
    apply(
      sideLengthInPixels,
      hasBorder = ShapesHaveBordersByDefault,
      hasFilling = ShapesHaveFillingsByDefault,
      color = DefaultPrimaryColor,
      fillColor = DefaultSecondaryColor)

  /**
   *
   *
   * @param sideLengthInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def apply(
      sideLengthInPixels: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: colors.rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    apply(
      sideLengthInPixels,
      Pos.Origo,
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param sideLengthInPixels
   * @param center
   *
   * @return
   */
  def apply(
      sideLengthInPixels: Double,
      center: Pos): VectorGraphic = {

    apply(
      sideLengthInPixels,
      center,
      hasBorder = ShapesHaveBordersByDefault,
      hasFilling = ShapesHaveFillingsByDefault,
      color = DefaultPrimaryColor,
      fillColor = DefaultSecondaryColor)
  }

  /**
   *
   *
   * @param sideLengthInPixels
   * @param center
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def apply(
      sideLengthInPixels: Double,
      center: Pos,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    if (sideLengthInPixels < 0.0) {
      throw new IllegalArgumentException(
        s"Rectangle's side length cannot be negative (was: $sideLengthInPixels).")
    }

    apply(
      sideLengthInPixels, sideLengthInPixels,
      center,
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param baseLengthInPixels
   * @param heightInPixels
   *
   * @return
   */
  def apply(
      baseLengthInPixels: Double,
      heightInPixels: Double): VectorGraphic = {

    apply(
      baseLengthInPixels, heightInPixels,
      Pos.Origo,
      hasBorder = ShapesHaveBordersByDefault,
      hasFilling = ShapesHaveFillingsByDefault,
      color = DefaultPrimaryColor,
      fillColor = DefaultSecondaryColor)
  }

  /**
   *
   *
   * @param baseLengthInPixels
   * @param heightInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def apply(
      baseLengthInPixels: Double,
      heightInPixels: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    apply(
      baseLengthInPixels, heightInPixels,
      Pos.Origo,
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param baseLengthInPixels
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
      baseLengthInPixels: Double,
      heightInPixels: Double,
      center: Pos,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: rgb.Color = DefaultPrimaryColor,
      fillColor: rgb.Color = DefaultSecondaryColor): VectorGraphic = {

    if (baseLengthInPixels < 0.0) {
      throw new IllegalArgumentException(
        s"Rectangle's base length cannot be negative (was: $baseLengthInPixels).")
    }

    if (heightInPixels < 0.0) {
      throw new IllegalArgumentException(
        s"Rectangle's height cannot be negative (was: $heightInPixels).")
    }

    val cornerPoints =
      if (baseLengthInPixels > 0.0 && heightInPixels > 0.0) {
        val ulX = {
          val x = -baseLengthInPixels / 2.0

          if (baseLengthInPixels >= 1.0) x.truncate else x
        }
        val lrX = ulX + baseLengthInPixels - 1

        val ulY = {
          val y = -heightInPixels / 2.0

          if (heightInPixels >= 1.0) y.truncate else y
        }
        val lrY = ulY + heightInPixels - 1

        Seq(
          Pos(ulX, ulY),
          Pos(lrX, ulY),
          Pos(lrX, lrY),
          Pos(ulX, lrY))
      }
      else {
        Seq()
      }

    Polygon(
      center,
      cornerPoints,
      hasBorder, hasFilling,
      color, fillColor)
  }

}
