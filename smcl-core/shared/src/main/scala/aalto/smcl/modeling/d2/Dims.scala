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

package aalto.smcl.modeling.d2


import aalto.smcl.modeling.CartesianDimensions
import aalto.smcl.infrastructure.{CommonDoubleMathOps, DoubleDoubleMap, MinMaxItemOps, ToTuple}




/**
 * Companion object for the [[Dims]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Dims {

  /**
   * Creates a new [[Dims]] instance.
   *
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  def apply(
      widthInPixels: Double,
      heightInPixels: Double): Dims = {

    require(
      widthInPixels >= 0,
      s"Width cannot be negative (was $widthInPixels)")

    require(
      heightInPixels >= 0,
      s"Height cannot be negative (was $heightInPixels)")

    new Dims(widthInPixels, heightInPixels)
  }

  /**
   * Creates a new [[Dims]] instance.
   *
   * @param dimensions
   *
   * @return
   */
  def apply(dimensions: Double*): Dims = {
    require(
      dimensions.length == 2,
      s"Exactly two dimensions must be given (currently: ${dimensions.length})")

    apply(dimensions: _*)
  }

}




/**
 * Dimensions in two-dimensional Cartesian coordinate system.
 *
 * @param widthInPixels
 * @param heightInPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Dims private(
    widthInPixels: Double,
    heightInPixels: Double)
    extends CartesianDimensions(Seq(widthInPixels, heightInPixels))
            with ToTuple[CoordinateTuple]
            with DoubleDoubleMap[Dims]
            with CommonDoubleMathOps[Dims, CoordinateTuple]
            with MinMaxItemOps[Dims, Double, CoordinateTuple] {

  /**
   *
   *
   * @return
   */
  @inline
  def toTuple: CoordinateTuple = {
    (widthInPixels, heightInPixels)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def toIntTuple: (Int, Int) = {
    (widthInPixels.toInt, heightInPixels.toInt)
  }

  /**
   *
   * @param f
   *
   * @return
   */
  override def map(f: (Double) => Double): Dims = {
    Dims(f(widthInPixels), f(heightInPixels))
  }

  /**
   * Returns the minimum of the items contained by this container.
   *
   * @return
   */
  override def minItem: Double = {
    math.min(widthInPixels, heightInPixels)
  }

  /**
   * Returns the minimums of the different types of items
   * contained by both this and other given containers.
   *
   * @return
   */
  override def minItems(others: Dims*): Dims = {
    val dims = this +: others
    val minWidth = dims.minBy(_.widthInPixels).widthInPixels
    val minHeight = dims.minBy(_.heightInPixels).heightInPixels

    Dims(minWidth, minHeight)
  }

  /**
   * Returns the maximum of the items contained by this container.
   *
   * @return
   */
  override def maxItem: Double = {
    math.max(widthInPixels, heightInPixels)
  }

  /**
   * Returns the maximums of the different types of items
   * contained by both this and other given containers.
   *
   * @return
   */
  override def maxItems(others: Dims*): Dims = {
    val dims = this +: others
    val maxWidth = dims.maxBy(_.widthInPixels).widthInPixels
    val maxHeight = dims.maxBy(_.heightInPixels).heightInPixels

    Dims(maxWidth, maxHeight)
  }

}
