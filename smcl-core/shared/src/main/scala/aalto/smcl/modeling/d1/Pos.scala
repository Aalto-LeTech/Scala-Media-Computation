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


import aalto.smcl.infrastructure.{CommonDoubleMathOps, FlatMap, ItemItemMap, MathUtils, MinMaxOps}
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
  @inline
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
            with FlatMap[Pos, Double]
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
  @inline
  override
  def moveBy(offsets: Double*): Pos = {
    require(
      offsets.length == 1,
      s"Pos1 represents exactly one coordinate (given: ${offsets.length})")

    Pos(inPixels + offsets(0))
  }

  /**
   *
   * @param f
   *
   * @return
   */
  @inline
  override
  def flatMap(f: (Double) => Pos): Pos = {
    f(inPixels)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  override
  def canEqual(other: Any): Boolean = {
    other.isInstanceOf[Pos]
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
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
  @inline
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
  @inline
  def + (offset: Len): Pos = {
    Pos(inPixels + offset.inPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
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
  @inline
  def + (offset: Pos): Pos = {
    Pos(inPixels + offset.inPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def - (offset: Pos): Pos = {
    Pos(inPixels - offset.inPixels)
  }

  /**
   *
   * @param f
   *
   * @return
   */
  @inline
  override
  def map(f: (Double) => Double): Pos = {
    Pos(f(inPixels))
  }

  /**
   * Returns the minimum of the given objects.
   *
   * @return
   */
  @inline
  override
  def min(others: Pos*): Pos = {
    (this +: others).minBy(_.inPixels)
  }

  /**
   * Returns the maximum of the given objects.
   *
   * @return
   */
  @inline
  override
  def max(others: Pos*): Pos = {
    (this +: others).maxBy(_.inPixels)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  def distanceTo(other: Pos): Len = {
    Len(math.abs(other.inPixels - inPixels))
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  def toMinMaxWith(other: Pos): (Pos, Pos) = {
    val (min, max) = MathUtils.sort(inPixels, other.inPixels)

    (Pos(min), Pos(max))
  }

}
