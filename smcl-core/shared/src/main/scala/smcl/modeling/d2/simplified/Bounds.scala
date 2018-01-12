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

package smcl.modeling.d2.simplified


import smcl.infrastructure._

// XXX consolidate with HasEdges trait
// XXX use elsewhere


/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
object Bounds {

  /**
   *
   *
   * @param pos
   * @param width
   * @param height
   *
   * @return
   */
  def apply(
      pos: Pos,
      width: Double,
      height: Double): Bounds = {

    new Bounds(pos.x, pos.y, width, height)
  }

}




/**
 *
 *
 * @param left
 * @param top
 * @param width
 * @param height
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
case class Bounds(
    left: Double,
    top: Double,
    width: Double,
    height: Double) { // XXX hasposition

  /** */
  lazy val right: Double = left + width

  /** */
  lazy val bottom: Double = top + height

  /** */
  lazy val pos = Pos(left, top)

  /**
   *
   *
   * @param pos
   *
   * @return
   */
  def contains(pos: Pos): Boolean =
    contains(pos.x, pos.y)

  /**
   *
   *
   * @param x
   * @param y
   *
   * @return
   */
  def contains(x: Double, y: Double): Boolean =
    x.isBetween(left, right) && y.isBetween(top, bottom)

  /**
   *
   *
   * @return
   */
  override
  def toString = s"($left,$top):w=$width,h=$height"

}
