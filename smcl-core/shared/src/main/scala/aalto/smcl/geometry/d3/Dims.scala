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


import aalto.smcl.geometry.CartesianDimensions
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
   * @param depthInPixels
   *
   * @return
   */
  def apply(
      widthInPixels: Double,
      heightInPixels: Double,
      depthInPixels: Double): Dims = {

    require(
      widthInPixels >= 0,
      s"Width cannot be negative (was $widthInPixels)")

    require(
      heightInPixels >= 0,
      s"Height cannot be negative (was $heightInPixels)")

    require(
      depthInPixels >= 0,
      s"Depth cannot be negative (was $depthInPixels)")

    new Dims(widthInPixels, heightInPixels, depthInPixels)
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
      dimensions.length == 3,
      s"Exactly three dimensions must be given (currently: ${dimensions.length})")

    apply(dimensions: _*)
  }

}




/**
 * Dimensions in three-dimensional Cartesian coordinate system.
 *
 * @param widthInPixels
 * @param heightInPixels
 * @param depthInPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Dims private(
    widthInPixels: Double,
    heightInPixels: Double,
    depthInPixels: Double)
    extends CartesianDimensions(Seq(widthInPixels, heightInPixels, depthInPixels))
            with DoubleDoubleMap[Dims]
            with ToTuple[CoordinateTuple]
            with CommonDoubleMathOps[Dims, CoordinateTuple]
            with MinMaxItemOps[Dims, Double, CoordinateTuple] {

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  @inline
  override def toTuple: CoordinateTuple = {
    (widthInPixels, heightInPixels, depthInPixels)
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
  def map(f: (Double) => Double): Dims = {
    Dims(
      f(widthInPixels),
      f(heightInPixels),
      f(depthInPixels))
  }

  /**
   * Returns the minimum of the items contained by this container.
   *
   * @return
   */
  override def minItem: Double = {
    math.min(math.min(widthInPixels, heightInPixels), depthInPixels)
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
    val minDepth = dims.minBy(_.depthInPixels).depthInPixels

    Dims(minWidth, minHeight, minDepth)
  }

  /**
   * Returns the maximum of the items contained by this container.
   *
   * @return
   */
  override def maxItem: Double = {
    math.max(math.max(widthInPixels, heightInPixels), depthInPixels)
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
    val maxDepth = dims.maxBy(_.depthInPixels).depthInPixels

    Dims(maxWidth, maxHeight, maxDepth)
  }

}
