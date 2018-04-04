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

package smcl.modeling.d1


import smcl.infrastructure.{CommonDoubleMathOps, FlatMap, ItemItemMap, MinMaxOps}
import smcl.modeling.misc.CartesianDimensions




/**
 * Companion object for the [[Dims]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Dims {

  /** A [[Dims]] instance that presents an infinitely large positive dimension. */
  lazy val PositiveInfinity: Dims = Dims(Double.PositiveInfinity)

  /** A [[Dims]] instance that presents an infinitely large positive dimension. */
  lazy val NegativeInfinity: Dims = Dims(Double.NegativeInfinity)

  /** A [[Dims]] instance that presents one-sized dimensions. */
  lazy val One: Dims = Dims(1.0)

  /** A [[Dims]] instance that presents zero-sized dimensions. */
  lazy val Zero: Dims = Dims(0.0)

  /**
   * Creates a new [[Dims]] instance.
   *
   * @param dimensions
   *
   * @return
   */
  def apply(dimensions: Seq[Double]): Dims = {
    require(
      dimensions.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions dimension must be given (currently: ${dimensions.length})")

    apply(dimensions.head)
  }

  /**
   * Creates a new [[Dims]] instance.
   *
   * @param inPixels
   *
   * @return
   */
  def apply(inPixels: Double): Dims = {
    require(
      inPixels >= 0.0,
      s"Magnitude cannot be negative (was $inPixels)")

    new Dims(inPixels)
  }

}




/**
 * A dimension in one-dimensional Cartesian coordinate system.
 *
 * @param lengthInPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Dims private(lengthInPixels: Double)
    extends CartesianDimensions
        with Ordered[Dims]
        with ItemItemMap[Dims, Double]
        with FlatMap[Dims, Double]
        with CommonDoubleMathOps[Dims]
        with MinMaxOps[Dims] {

  /** */
  lazy val lengths: Seq[Double] = Seq(lengthInPixels)

  /**
   *
   *
   * @param f
   *
   * @return
   */
  override
  def map(f: (Double) => Double): Dims = Dims(f(lengthInPixels))

  /**
   *
   *
   * @param f
   *
   * @return
   */
  def flatMap(f: (Double) => Dims): Dims = f(lengthInPixels)

  /**
   *
   *
   * @param that
   *
   * @return
   */
  override
  def compare(that: Dims): Int = {
    lengthInPixels.compare(that.lengthInPixels)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  override
  def canEqual(other: Any): Boolean = other.isInstanceOf[Dims]

  /**
   * Returns the minimum of the given objects.
   *
   * @return
   */
  override
  def min(others: Dims*): Dims = (this +: others).min

  /**
   * Returns the maximum of the given objects.
   *
   * @return
   */
  override
  def max(others: Dims*): Dims = (this +: others).max

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  def + (offset: Dims): Dims = {
    Dims(lengthInPixels + offset.lengthInPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  def - (offset: Dims): Dims = {
    Dims(lengthInPixels - offset.lengthInPixels)
  }

  /**
   *
   *
   * @param newLengthInPixels
   *
   * @return
   */
  def copy(newLengthInPixels: Double = lengthInPixels): Dims = {
    Dims(newLengthInPixels)
  }

  /**
   *
   *
   * @return
   */
  override
  def toString: String = {
    s"Dims($lengthInPixels px)"
  }

}
