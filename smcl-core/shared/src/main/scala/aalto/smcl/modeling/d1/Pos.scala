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

package aalto.smcl.modeling.d1


import aalto.smcl.modeling.{CartesianPosition, Len}




/**
 * Companion object for [[Pos]].
 *
 * @author Aleksi Lukkarinen
 */
object Pos {

  /** The origo of a one-dimensional coordinate system. */
  val Origo = new Pos(0.0)

  /**
   * Creates a new [[Pos]] instance.
   *
   * @param valuePixels
   *
   * @return
   */
  def apply(valuePixels: Double): Pos = {
    new Pos(valuePixels)
  }

}




/**
 * Position in a one-dimensional coordinate system.
 *
 * @param valueInPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Pos private(
    valueInPixels: Double)
    extends CartesianPosition(Seq(valueInPixels))
            with Movable[Pos] {

  /**
   *
   *
   * @param deltas
   *
   * @return
   */
  override def moveBy(deltas: Double*): Pos = {
    require(
      deltas.length == 1,
      s"Pos1 represents exactly one coordinate (given: ${deltas.length})")

    Pos(valueInPixels + deltas(0))
  }

  /**
   *
   *
   * @param delta
   *
   * @return
   */
  def + (delta: Int): Pos = {
    Pos(valueInPixels + delta)
  }

  /**
   *
   *
   * @param delta
   *
   * @return
   */
  def - (delta: Int): Pos = {
    Pos(valueInPixels - delta)
  }

  /**
   *
   *
   * @param delta
   *
   * @return
   */
  def + (delta: Len): Pos = {
    Pos(valueInPixels * delta.inPixels)
  }

  /**
   *
   *
   * @param delta
   *
   * @return
   */
  def - (delta: Len): Pos = {
    Pos(valueInPixels - delta.inPixels)
  }

}
