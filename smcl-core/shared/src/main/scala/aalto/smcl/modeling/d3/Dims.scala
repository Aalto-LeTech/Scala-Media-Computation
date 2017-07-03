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

package aalto.smcl.modeling.d3


import aalto.smcl.infrastructure.{CommonTupledDoubleMathOps, FlatMap, ItemItemMap, ToTuple, TupledMinMaxItemOps}
import aalto.smcl.modeling.AbstractCartesianDimensions




/**
 * Companion object for the [[Dims]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Dims {

  /** A [[Dims]] instance that presents infinitely large dimensions. */
  val InfinitelyLarge =
    Dims(Double.PositiveInfinity,
      Double.PositiveInfinity,
      Double.PositiveInfinity)

  /** A [[Dims]] instance that presents zero-sized dimensions. */
  val NonExistent = Dims(0.0, 0.0, 0.0)

  /**
   * Creates a new [[Dims]] instance.
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param depthInPixels
   *
   * @return
   */
  @inline
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
  @inline
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
    extends AbstractCartesianDimensions(Seq(widthInPixels, heightInPixels, depthInPixels))
            with ToTuple[DimensionTuple]
            with ItemItemMap[Dims, Double]
            with FlatMap[Dims, DimensionTuple]
            with CommonTupledDoubleMathOps[Dims, DimensionTuple]
            with TupledMinMaxItemOps[Dims, Double, DimensionTuple] {

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  @inline
  override
  def toTuple: DimensionTuple = {
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
  @inline
  def map(f: (Double) => Double): Dims = {
    Dims(
      f(widthInPixels),
      f(heightInPixels),
      f(depthInPixels))
  }

  /**
   *
   *
   * @param f
   *
   * @return
   */
  @inline
  def flatMap(f: (DimensionTuple) => Dims): Dims = {
    f(toTuple)
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
    other.isInstanceOf[Dims]
  }

  /**
   * Returns the minimum of the items contained by this container.
   *
   * @return
   */
  @inline
  override
  def minItem: Double = {
    math.min(math.min(widthInPixels, heightInPixels), depthInPixels)
  }

  /**
   * Returns the minimums of the different types of items
   * contained by both this and other given containers.
   *
   * @return
   */
  @inline
  override
  def minItems(others: Dims*): Dims = {
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
  @inline
  override
  def maxItem: Double = {
    math.max(math.max(widthInPixels, heightInPixels), depthInPixels)
  }

  /**
   * Returns the maximums of the different types of items
   * contained by both this and other given containers.
   *
   * @return
   */
  @inline
  override def maxItems(others: Dims*): Dims = {
    val dims = this +: others
    val maxWidth = dims.maxBy(_.widthInPixels).widthInPixels
    val maxHeight = dims.maxBy(_.heightInPixels).heightInPixels
    val maxDepth = dims.maxBy(_.depthInPixels).depthInPixels

    Dims(maxWidth, maxHeight, maxDepth)
  }

  /**
   *
   *
   * @return
   */
  override def unary_+(): Dims = this

  /**
   *
   *
   * @return
   */
  override def inverse: Dims =
    Dims(-widthInPixels, -heightInPixels, -depthInPixels)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def + (offset: Dims): Pos = {
    val width = widthInPixels + offset.widthInPixels
    val height = heightInPixels + offset.heightInPixels
    val depth = depthInPixels + offset.depthInPixels

    Pos(width, height, depth)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def - (offset: Dims): Pos = {
    val width = widthInPixels - offset.widthInPixels
    val height = heightInPixels - offset.heightInPixels
    val depth = depthInPixels - offset.depthInPixels

    Pos(width, height, depth)
  }

}
