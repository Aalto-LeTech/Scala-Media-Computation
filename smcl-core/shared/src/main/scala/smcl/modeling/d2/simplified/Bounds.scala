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
      width: Int,
      height: Int): Bounds = {

    new Bounds(pos.xInt, pos.yInt, width, height) // XXX floor or round or what?
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
case class Bounds(left: Int, top: Int, width: Int, height: Int) { // XXX hasposition

  /** */
  lazy val right: Int = left + width

  /** */
  lazy val bottom: Int = top + height

  /** */
  lazy val pos = Pos(left, top)

  /**
   *
   *
   * @param x
   * @param y
   *
   * @return
   */
  def contains(x: Int, y: Int): Boolean =
    x.isBetween(this.left, this.right) &&
        y.isBetween(this.top, this.bottom)

  /**
   *
   *
   * @param pos
   *
   * @return
   */
  def contains(pos: Pos): Boolean =
    this.contains(pos.xInt, pos.yInt) // XXX floor or round or what?

  /**
   *
   *
   * @return
   */
  override
  def toString = s"($left,$top):w=$width,h=$height"

}
