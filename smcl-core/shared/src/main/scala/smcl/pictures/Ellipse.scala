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
 * An object-based API for creating ellipses.
 *
 * @author Aleksi Lukkarinen
 */
object Ellipse {

  /**
   *
   *
   * @param upperLeftCorner
   * @param width
   * @param height
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def apply(
      upperLeftCorner: Pos,
      width: Len,
      height: Len,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    val lowerRightCorner = upperLeftCorner + (width.inPixels, height.inPixels)

    apply(
      upperLeftCorner,
      lowerRightCorner,
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param center
   * @param semiMajorAxisInPixels
   * @param semiMinorAxisInPixels
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def apply(
      center: Pos,
      semiMajorAxisInPixels: Double,
      semiMinorAxisInPixels: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    val axes = (semiMajorAxisInPixels, semiMinorAxisInPixels)
    val upperLeftCorner = center - axes
    val lowerRightCorner = center + axes

    apply(
      upperLeftCorner,
      lowerRightCorner,
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param upperLeftCorner
   * @param lowerRightCorner
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def apply(
      upperLeftCorner: Pos,
      lowerRightCorner: Pos,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: rgb.Color = DefaultPrimaryColor,
      fillColor: rgb.Color = DefaultSecondaryColor): VectorGraphic = {

    Arc(
      upperLeftCorner,
      lowerRightCorner,
      startAngleInDegrees = Angle.Zero.inDegrees,
      arcAngleInDegrees = Angle.FullAngleInDegrees,
      hasBorder, hasFilling,
      color, fillColor)
  }

}
