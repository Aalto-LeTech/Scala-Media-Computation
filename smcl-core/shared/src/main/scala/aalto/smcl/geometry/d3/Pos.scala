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

package aalto.smcl.geometry.d3


import aalto.smcl.geometry.CartesianPosition




/**
 * Companion object for [[Pos]].
 *
 * @author Aleksi Lukkarinen
 */
object Pos {

  /** The origo of a three-dimensional Cartesian coordinate system. */
  val Origo = new Pos(0.0, 0.0, 0.0)

  /**
   * Creates a new [[Pos]] instance.
   *
   * @param xInPixels
   * @param yInPixels
   * @param zInPixels
   *
   * @return
   */
  def apply(
      xInPixels: Double,
      yInPixels: Double,
      zInPixels: Double): Pos = {

    new Pos(xInPixels, yInPixels, zInPixels)
  }

}




/**
 * Position in a three-dimensional Cartesian coordinate system.
 *
 * @param xInPixels
 * @param yInPixels
 * @param zInPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Pos private(
    xInPixels: Double,
    yInPixels: Double,
    zInPixels: Double)
    extends CartesianPosition(
      Seq(xInPixels, yInPixels, zInPixels))
            with Movable[Pos] {

  /**
   *
   *
   * @param dX
   * @param dY
   *
   * @return
   */
  def moveBy(dX: Double, dY: Double, dZ: Double): Pos = {
    Pos(xInPixels + dX, yInPixels + dY, zInPixels + dZ)
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
      deltas.length == 3,
      s"Pos represents exactly three coordinates (given: ${deltas.length})")

    moveBy(deltas: _*)
  }

}
