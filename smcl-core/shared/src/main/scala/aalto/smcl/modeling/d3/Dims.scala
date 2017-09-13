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


import aalto.smcl.infrastructure._
import aalto.smcl.modeling.misc.CartesianDimensions
import aalto.smcl.modeling.{Len, Vol}




/**
 * Companion object for the [[Dims]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Dims {

  /** A [[Dims]] instance that presents infinitely large dimensions. */
  val InfinitelyLarge = Dims(
    Double.PositiveInfinity,
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

    val w = Len(widthInPixels)
    val h = Len(heightInPixels)
    val d = Len(depthInPixels)

    apply(w, h, d)
  }

  /**
   * Creates a new [[Dims]] instance.
   *
   * @param width
   * @param height
   * @param depth
   *
   * @return
   */
  @inline
  def apply(
      width: Len,
      height: Len,
      depth: Len): Dims = {

    new Dims(width, height, depth)
  }

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
      dimensions.length == 3,
      s"Exactly $NumberOfDimensions dimensions must be given (currently: ${dimensions.length})")

    apply(
      dimensions.head,
      dimensions(1),
      dimensions(2))
  }

}




/**
 * Dimensions in three-dimensional Cartesian coordinate system.
 *
 * @param width
 * @param height
 * @param depth
 *
 * @author Aleksi Lukkarinen
 */
case class Dims private(
    width: Len,
    height: Len,
    depth: Len)
    extends CartesianDimensions
            with ToTuple[DimensionTuple]
            with ItemItemMap[Dims, Double]
            with FlatMap[Dims, DimensionTuple]
            with CommonTupledDoubleMathOps[Dims, DimensionTuple]
            with TupledMinMaxItemOps[Dims, Double, DimensionTuple] {

  /** */
  lazy val lengths: Seq[Double] =
    Seq(width.inPixels, height.inPixels, depth.inPixels)

  /** */
  lazy val volume: Vol = Vol.forCuboid(width, height, depth)

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  @inline
  override
  def toTuple: DimensionTuple = {
    (width, height, depth)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def toDoubleTuple: (Double, Double, Double) = {
    val w = width.inPixels
    val h = height.inPixels
    val d = depth.inPixels

    (w, h, d)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def toIntTuple: (Int, Int, Int) = {
    val w = width.inPixels.closestInt
    val h = height.inPixels.closestInt
    val d = depth.inPixels.closestInt

    (w, h, d)
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
      f(width.inPixels),
      f(height.inPixels),
      f(depth.inPixels))
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
  def minItem: Double = lengths.min

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
    val minWidth = dims.minBy(_.width).width
    val minHeight = dims.minBy(_.height).height
    val minDepth = dims.minBy(_.depth).depth

    Dims(minWidth, minHeight, minDepth)
  }

  /**
   * Returns the maximum of the items contained by this container.
   *
   * @return
   */
  @inline
  override
  def maxItem: Double = lengths.max

  /**
   * Returns the maximums of the different types of items
   * contained by both this and other given containers.
   *
   * @return
   */
  @inline
  override def maxItems(others: Dims*): Dims = {
    val dims = this +: others
    val maxWidth = dims.maxBy(_.width).width
    val maxHeight = dims.maxBy(_.height).height
    val maxDepth = dims.maxBy(_.depth).depth

    Dims(maxWidth, maxHeight, maxDepth)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def + (offset: Dims): Dims = {
    val newWidth = this.width + offset.width
    val newHeight = this.height + offset.height
    val newDepth = this.depth + offset.depth

    Dims(newWidth, newHeight, newDepth)
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
    val newWidth = this.width - offset.width
    val newHeight = this.height - offset.height
    val newDepth = this.depth - offset.depth

    Dims(newWidth, newHeight, newDepth)
  }

  /**
   *
   *
   * @param newWidth
   * @param newHeight
   * @param newDepth
   *
   * @return
   */
  @inline
  def copy(
      newWidth: Len = width,
      newHeight: Len = height,
      newDepth: Len = depth): Dims = {

    Dims(newWidth, newHeight, newDepth)
  }

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toString: String = {
    s"Dims(w: ${width.inPixels} px, h: ${height.inPixels} px, d: ${depth.inPixels} px)"
  }

}
