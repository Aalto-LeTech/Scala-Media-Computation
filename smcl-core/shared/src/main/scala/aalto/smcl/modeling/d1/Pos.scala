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
import aalto.smcl.modeling.{AbstractCartesianPosition, Len}




/**
 * Companion object for [[Pos]].
 *
 * @author Aleksi Lukkarinen
 */
object Pos {

  /** The zero of a one-dimensional coordinate system. */
  lazy val Zero = new Pos(0.0)

  /** Positive one in a one-dimensional coordinate system. */
  lazy val One = Pos(1.0)

  /** A [[Pos]] instance that represents positive infinity. */
  lazy val PositiveInfinity = Pos(Double.PositiveInfinity)

  /** A [[Pos]] instance that represents negative infinity. */
  lazy val NegativeInfinity = Pos(Double.NegativeInfinity)

  /** A [[Pos]] instance that represents a not-a-number. */
  lazy val NaN = Pos(Double.NaN)

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
    extends AbstractCartesianPosition(Seq(inPixels))
            with Ordered[Pos]
            with ItemItemMap[Pos, Double]
            with FlatMap[Pos, Double]
            with CommonDoubleMathOps[Pos]
            with MinMaxOps[Pos]
            with Movable[Pos] {

  /**
   * Returns <code>true</code> if this instance represents
   * the zero position; otherwise <code>false</code>.
   */
  val isZero: Boolean = inPixels == 0.0

  /**
   * Returns <code>true</code> if this instance represents
   * positive infinity; otherwise <code>false</code>.
   */
  val isPositiveInfinity: Boolean =
    inPixels.isPosInfinity

  /**
   * Returns <code>true</code> if this instance represents
   * negative infinity; otherwise <code>false</code>.
   */
  val isNegativeInfinity: Boolean =
    inPixels.isNegInfinity

  /**
   * Returns <code>true</code> if this instance represents
   * a not-a-number; otherwise <code>false</code>.
   */
  val isNaN: Boolean = inPixels.isNaN

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
   * @param that
   *
   * @return
   */
  override
  def compare(that: Pos): Int = {
    inPixels.compare(that.inPixels)
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
   *
   * @return
   */
  def unary_-(): Pos = {
    Pos(-inPixels)
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
