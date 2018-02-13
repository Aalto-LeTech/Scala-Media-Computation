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




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Line {

  /**
   *
   *
   * {{{
   * def moire(w: Double) = {
   *   import smcl.pictures.fullfeatured.{Image, Line}
   *
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
      color: rgb.Color): Polygon = {

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
      color: rgb.Color): Polygon = {

    val points = Seq(start, end)

    // TODO: Change to polyline after it is implemented
    Polygon(
      points,
      hasBorder = true,
      hasFilling = false,
      color = color)
  }

}
