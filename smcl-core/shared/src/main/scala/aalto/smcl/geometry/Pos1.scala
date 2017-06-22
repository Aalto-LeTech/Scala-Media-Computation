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
 * Companion object for [[Pos1]].
 *
 * @author Aleksi Lukkarinen
 */
object Pos1 {

  /** The origo of a one-dimensional coordinate system. */
  val Origo = new Pos1(0)

  /**
   * Creates a new [[Pos1]] instance.
   *
   * @param valuePixels
   *
   * @return
   */
  def apply(valuePixels: Int): Pos1 = {
    new Pos1(valuePixels)
  }

}




/**
 * Position in a one-dimensional coordinate system.
 *
 * @param valueInPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Pos1 private(
    valueInPixels: Int)
    extends CartesianPosition(Seq(valueInPixels))
            with Movable[Pos1] {

  /**
   *
   *
   * @param deltas
   *
   * @return
   */
  override def moveBy(deltas: Int*): Pos1 = {
    require(
      deltas.length == 1,
      s"Pos1 represents exactly one coordinate (given: ${deltas.length})")

    Pos1(valueInPixels + deltas(0))
  }

  /**
   *
   *
   * @param delta
   *
   * @return
   */
  def + (delta: Int): Pos1 = {
    Pos1(valueInPixels + delta)
  }

  /**
   *
   *
   * @param delta
   *
   * @return
   */
  def - (delta: Int): Pos1 = {
    Pos1(valueInPixels - delta)
  }

  /**
   *
   *
   * @param delta
   *
   * @return
   */
  def + (delta: Len): Pos1 = {
    Pos1(valueInPixels * delta.valueInPixels)
  }

  /**
   *
   *
   * @param delta
   *
   * @return
   */
  def - (delta: Len): Pos1 = {
    Pos1(valueInPixels - delta.valueInPixels)
  }

}
