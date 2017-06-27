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

package aalto.smcl.geometry.d2

import aalto.smcl.geometry.CartesianPosition




/**
 * Companion object for the [[Pos]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Pos {

  /** The origo of a two-dimensional Cartesian coordinate system. */
  val Origo = new Pos(0.0, 0.0)

  /**
   * Creates a new [[Pos]] instance.
   *
   * @param xInPixels
   * @param yInPixels
   *
   * @return
   */
  def apply(
      xInPixels: Double,
      yInPixels: Double): Pos = {

    new Pos(xInPixels, yInPixels)
  }

}




/**
 * Position in a two-dimensional Cartesian coordinate system.
 *
 * @param xInPixels
 * @param yInPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Pos private(
    xInPixels: Double,
    yInPixels: Double)
    extends CartesianPosition(Seq(xInPixels, yInPixels))
            with Movable[Pos] {

  /**
   *
   *
   * @param dX
   * @param dY
   *
   * @return
   */
  def moveBy(dX: Double, dY: Double): Pos = {
    Pos(xInPixels + dX, yInPixels + dY)
  }

  /**
   *
   *
   * @param deltas
   *
   * @return
   */
  override def moveBy(deltas: Double*): Pos = {
    require(
      deltas.length == 2,
      s"Pos represents exactly two coordinates (given: ${deltas.length})")

    moveBy(deltas: _*)
  }

}
