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

package aalto.smcl.geometry


/**
 * Companion object for [[Pos3]].
 *
 * @author Aleksi Lukkarinen
 */
object Pos3 {

  /** The origo of a three-dimensional Cartesian coordinate system. */
  val Origo = new Pos3(0, 0, 0)

  /**
   * Creates a new [[Pos3]] instance.
   *
   * @param xInPixels
   * @param yInPixels
   * @param zInPixels
   *
   * @return
   */
  def apply(
      xInPixels: Int,
      yInPixels: Int,
      zInPixels: Int): Pos3 = {

    new Pos3(xInPixels, yInPixels, zInPixels)
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
case class Pos3 private(
    xInPixels: Int,
    yInPixels: Int,
    zInPixels: Int)
    extends CartesianPosition(
      Seq(xInPixels, yInPixels, zInPixels))
            with Movable[Pos3] {

  /**
   *
   *
   * @param dX
   * @param dY
   *
   * @return
   */
  def moveBy(dX: Int, dY: Int, dZ: Int): Pos3 = {
    Pos3(xInPixels + dX, yInPixels + dY, zInPixels + dZ)
  }

  /**
   *
   *
   * @param deltas
   *
   * @return
   */
  override def moveBy(deltas: Int*): Pos3 = {
    require(
      deltas.length == 3,
      s"Pos represents exactly three coordinates (given: ${deltas.length})")

    moveBy(deltas: _*)
  }

}
