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
import smcl.modeling.d2.Pos
import smcl.modeling.{Angle, Len}
import smcl.settings._




/**
 * An object-based API for creating circles.
 *
 * @author Aleksi Lukkarinen
 */
object Circle {

  /**
   *
   *
   * @param center
   * @param radiusInPixels
   *
   * @return
   */
  @inline
  def apply(
      center: Pos,
      radiusInPixels: Double): VectorGraphic = {

    apply(
      center,
      radiusInPixels,
      hasBorder = ShapesHaveBordersByDefault,
      hasFilling = ShapesHaveFillingsByDefault,
      color = DefaultPrimaryColor,
      fillColor = DefaultSecondaryColor)
  }

  /**
   *
   *
   * @param center
   * @param radiusInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  @inline
  def apply(
      center: Pos,
      radiusInPixels: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    val upperLeftCorner = center - (radiusInPixels, radiusInPixels)
    val width = Len(radiusInPixels).double

    apply(
      upperLeftCorner,
      width,
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param upperLeftCorner
   * @param width
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  @inline
  def apply(
      upperLeftCorner: Pos,
      width: Len,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: rgb.Color = DefaultPrimaryColor,
      fillColor: rgb.Color = DefaultSecondaryColor): VectorGraphic = {

    val lowerRightCorner = upperLeftCorner + width.toDimsWith(width)

    Arc(
      upperLeftCorner,
      lowerRightCorner,
      startAngleInDegrees = Angle.Zero.inDegrees,
      arcAngleInDegrees = Angle.FullAngleInDegrees,
      hasBorder, hasFilling,
      color, fillColor)
  }

}
