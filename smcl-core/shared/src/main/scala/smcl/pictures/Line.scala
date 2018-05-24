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
import smcl.settings._




/**
 * An object-based API for creating lines.
 *
 * {{{
 * def moire(w: Double) = {
 *   def lines(x: Double, c: Double) = Seq(
 *           Line(Pos.Origo, Pos(x, -c), Red),
 *           Line(Pos.Origo, Pos(x, c), Blue),
 *           Line(Pos.Origo, Pos(-c, x), Green),
 *           Line(Pos.Origo, Pos(c, x), Brown))
 *
 *   val wPerTwo = (w/2).toInt
 *   val r = Range.inclusive(-wPerTwo, wPerTwo, 2)
 *
 *   Image((for{x <- r} yield lines(x, wPerTwo)).flatten: _*)
 * }
 * }}}
 *
 * @author Aleksi Lukkarinen
 */
object Line {

  /**
   *
   *
   * @param startX
   * @param startY
   * @param endX
   * @param endY
   *
   * @return
   */
  def apply(
      startX: Double,
      startY: Double,
      endX: Double,
      endY: Double): VectorGraphic = {

    apply(
      Pos(startX, startY),
      Pos(endX, endY))
  }

  /**
   *
   *
   * @param startX
   * @param startY
   * @param endX
   * @param endY
   * @param color
   *
   * @return
   */
  def apply(
      startX: Double,
      startY: Double,
      endX: Double,
      endY: Double,
      color: rgb.Color): VectorGraphic = {

    apply(
      Pos(startX, startY),
      Pos(endX, endY),
      color)
  }

  /**
   *
   *
   * @param start
   * @param end
   * @param color
   *
   * @return
   */
  def apply(
      start: Pos,
      end: Pos,
      color: rgb.Color = DefaultPrimaryColor): VectorGraphic = {

    val points = Seq(Pos.Origo, end - start)

    // TODO: Change to Polyline after it is implemented
    Polygon(
      start,
      points,
      hasBorder = true,
      hasFilling = false,
      color = color)
  }

}
