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




/**
 * An object-based API for creating ellipses.
 *
 * @author Aleksi Lukkarinen
 */
object Ellipse {

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

    apply(
      center,
      Len(2 * semiMajorAxisInPixels),
      Len(2 * semiMinorAxisInPixels),
      hasBorder, hasFilling,
      color, fillColor)
  }

  /**
   *
   *
   * @param center
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
      center: Pos,
      width: Len,
      height: Len,
      hasBorder: Boolean,
      hasFilling: Boolean,
      color: rgb.Color,
      fillColor: rgb.Color): VectorGraphic = {

    Arc(
      center,
      width.inPixels,
      height.inPixels,
      startAngleInDegrees = Angle.Zero.inDegrees,
      arcAngleInDegrees = Angle.FullAngleInDegrees,
      hasBorder, hasFilling,
      color, fillColor)
  }

}
