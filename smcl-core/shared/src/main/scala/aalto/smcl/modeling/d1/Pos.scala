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


import aalto.smcl.infrastructure.{CommonDoubleMathOps, FlatMap, ItemItemMap, MinMaxOps}
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
 * @param inPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Pos private(
    inPixels: Double)
    extends CartesianPosition(Seq(inPixels))
            with ItemItemMap[Pos, Double]
            with FlatMap[Pos]
            with CommonDoubleMathOps[Pos]
            with MinMaxOps[Pos]
            with Movable[Pos] {

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  override def moveBy(offsets: Double*): Pos = {
    require(
      offsets.length == 1,
      s"Pos1 represents exactly one coordinate (given: ${offsets.length})")

    Pos(inPixels + offsets(0))
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  def + (offset: Double): Pos = {
    Pos(inPixels + offset)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  def - (offset: Double): Pos = {
    Pos(inPixels - offset)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  def + (offset: Len): Pos = {
    Pos(inPixels * offset.inPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  def - (offset: Len): Pos = {
    Pos(inPixels - offset.inPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  def + (offset: Pos): Pos = {
    Pos(inPixels * offset.inPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  def - (offset: Pos): Pos = {
    Pos(inPixels - offset.inPixels)
  }

  /**
   *
   * @param f
   *
   * @return
   */
  override def map(f: (Double) => Double): Pos = {
    Pos(f(inPixels))
  }

  /**
   * Returns the minimum of the given objects.
   *
   * @return
   */
  override def min(others: Pos*): Pos = {
    (this +: others).minBy(_.inPixels)
  }

  /**
   * Returns the maximum of the given objects.
   *
   * @return
   */
  override def max(others: Pos*): Pos = {
    (this +: others).maxBy(_.inPixels)
  }

}
