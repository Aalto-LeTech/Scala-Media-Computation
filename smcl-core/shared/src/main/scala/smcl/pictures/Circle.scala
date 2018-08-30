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




/** An object-based API for creating circles.
  *
  * @author Aleksi Lukkarinen
  */
object Circle {

  /**
    *
    * @param center
    * @param radiusInPixels
    *
    * @return
    */
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
    * @param center
    * @param radiusInPixels
    * @param hasBorder
    * @param hasFilling
    * @param color
    * @param fillColor
    *
    * @return
    */
  def apply(
      center: Pos,
      radiusInPixels: Double,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    apply(
      center,
      Len(radiusInPixels).double,
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
    *
    * @param diameter
    * @param hasBorder
    * @param hasFilling
    * @param color
    * @param fillColor
    *
    * @return
    */
  def apply(
      center: Pos,
      diameter: Len = Len(DefaultCircleRadiusInPixels).double,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: rgb.Color = DefaultPrimaryColor,
      fillColor: rgb.Color = DefaultSecondaryColor): VectorGraphic = {

    Arc(
      center,
      diameter.inPixels,
      diameter.inPixels,
      startAngleInDegrees = Angle.Zero.inDegrees,
      arcAngleInDegrees = Angle.FullAngleInDegrees,
      hasBorder, hasFilling,
      color, fillColor)
  }

}
