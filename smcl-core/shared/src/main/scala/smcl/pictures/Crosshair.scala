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


import smcl.colors.rgb.Color
import smcl.modeling.d2.Pos
import smcl.settings._




/**
 * An object-based API for creating crosshairs, i.e., a cross with a circle.
 *
 * @author Aleksi Lukkarinen
 */
object Crosshair {

  /**
   *
   *
   * @param widthInPixels
   * @param color
   * @param center
   *
   * @return
   */
  @inline
  def forWidth(
      widthInPixels: Double,
      color: Color,
      center: Pos): PictureElement = {

    apply(widthInPixels / 2.0, color, center)
  }

  /**
   *
   *
   * @param radiusInPixels
   * @param color
   * @param center
   *
   * @return
   */
  @inline
  def apply(
      radiusInPixels: Double = DefaultCircleRadiusInPixels / 3.0,
      color: Color = DefaultPrimaryColor,
      center: Pos = Pos.Origo): PictureElement = {

    val top = center.addY(radiusInPixels)
    val bottom = center.addY(-radiusInPixels)
    val left = center.addX(-radiusInPixels)
    val right = center.addX(radiusInPixels)

    val vLine = Line(bottom, top, color)
    val hLine = Line(left, right, color)
    val circle = Circle(
      center,
      radiusInPixels = 0.6 * radiusInPixels,
      hasBorder = true,
      hasFilling = false,
      color = color,
      fillColor = DefaultSecondaryColor)

    Picture(hLine, vLine, circle)
  }

}
