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


import aalto.smcl.infrastructure._
import aalto.smcl.modeling.misc.AbstractCartesianDimensions




/**
 * Companion object for the [[Dims]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Dims {

  /** A [[Dims]] instance that presents an infinitely large dimension. */
  val InfinitelyLarge = Dims(Double.PositiveInfinity)

  /** A [[Dims]] instance that presents a zero-sized dimension. */
  val NonExistent = Dims(0.0)

  /**
   * Creates a new [[Dims]] instance.
   *
   * @param dimensions
   *
   * @return
   */
  @inline
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
  @inline
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
    extends AbstractCartesianDimensions(Seq(lengthInPixels))
            with Ordered[Dims]
            with ItemItemMap[Dims, Double]
            with FlatMap[Dims, Double]
            with CommonDoubleMathOps[Dims]
            with MinMaxOps[Dims] {

  /**
   *
   *
   * @param f
   *
   * @return
   */
  @inline
  override
  def map(f: (Double) => Double): Dims = Dims(f(lengthInPixels))

  /**
   *
   *
   * @param f
   *
   * @return
   */
  @inline
  def flatMap(f: (Double) => Dims): Dims = f(lengthInPixels)

  /**
   *
   *
   * @param that
   *
   * @return
   */
  @inline
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
  @inline
  override
  def canEqual(other: Any): Boolean = other.isInstanceOf[Dims]

  /**
   * Returns the minimum of the given objects.
   *
   * @return
   */
  @inline
  override
  def min(others: Dims*): Dims = (this +: others).min

  /**
   * Returns the maximum of the given objects.
   *
   * @return
   */
  @inline
  override
  def max(others: Dims*): Dims = (this +: others).max

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
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
  @inline
  def - (offset: Dims): Dims = {
    Dims(lengthInPixels - offset.lengthInPixels)
  }

}
