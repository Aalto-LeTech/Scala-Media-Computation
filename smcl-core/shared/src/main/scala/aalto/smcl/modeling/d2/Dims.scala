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


import aalto.smcl.infrastructure._
import aalto.smcl.modeling.{Area, Len}
import aalto.smcl.modeling.misc.AbstractCartesianDimensions




/**
 * Companion object for the [[Dims]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Dims {

  /** A [[Dims]] instance that presents infinitely large dimensions. */
  val InfinitelyLarge =
    Dims(Double.PositiveInfinity,
      Double.PositiveInfinity)

  /** A [[Dims]] instance that presents zero-sized dimensions. */
  val NonExistent = Dims(0.0, 0.0)

  /**
   * Creates a new [[Dims]] instance.
   *
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  @inline
  def apply(
      widthInPixels: Double,
      heightInPixels: Double): Dims = {

    require(
      widthInPixels >= 0,
      s"Width cannot be negative (was $widthInPixels)")

    require(
      heightInPixels >= 0,
      s"Height cannot be negative (was $heightInPixels)")

    new Dims(Len(widthInPixels), Len(heightInPixels))
  }

  /**
   * Creates a new [[Dims]] instance.
   *
   * @param width
   * @param height
   *
   * @return
   */
  @inline
  def apply(
      width: Len,
      height: Len): Dims = {

    new Dims(width, height)
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
      dimensions.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions dimensions must be given (currently: ${dimensions.length})")

    apply(
      dimensions.head,
      dimensions(1))
  }

}




/**
 * Dimensions in two-dimensional Cartesian coordinate system.
 *
 * @param width
 * @param height
 *
 * @author Aleksi Lukkarinen
 */
case class Dims private(
    width: Len,
    height: Len)
    extends AbstractCartesianDimensions(
      Seq(width.inPixels, height.inPixels))
            with ToTuple[DimensionTuple]
            with ItemItemMap[Dims, Double]
            with FlatMap[Dims, DimensionTuple]
            with CommonTupledDoubleMathOps[Dims, DimensionTuple]
            with TupledMinMaxItemOps[Dims, Double, DimensionTuple] {

  /**  */
  lazy val area: Area = Area.forRectangle(width, height)

  /**
   *
   *
   * @return
   */
  @inline
  def toTuple: DimensionTuple = {
    (width, height)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def toDoubleTuple: (Double, Double) = {
    (width.inPixels, height.inPixels)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def toIntTuple: (Int, Int) = {
    val w = width.inPixels.closestInt
    val h = height.inPixels.closestInt

    (w, h)
  }

  /**
   *
   *
   * @param f
   *
   * @return
   */
  @inline
  override
  def map(f: (Double) => Double): Dims = {
    Dims(f(width.inPixels), f(height.inPixels))
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
    val minWidth = dims.map(_.width).min
    val minHeight = dims.map(_.height).min

    Dims(minWidth, minHeight)
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
  override
  def maxItems(others: Dims*): Dims = {
    val dims = this +: others
    val maxWidth = dims.map(_.width).max
    val maxHeight = dims.map(_.height).max

    Dims(maxWidth, maxHeight)
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

    Dims(newWidth, newHeight)
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

    Dims(newWidth, newHeight)
  }

  /**
   *
   *
   * @param upperLeftCorner
   *
   * @return
   */
  @inline
  def toBounds(upperLeftCorner: Pos): Bounds = {
    val lowerRightCorner = Pos(
      upperLeftCorner.xInPixels + width.inPixels,
      upperLeftCorner.yInPixels + height.inPixels)

    Bounds(upperLeftCorner, lowerRightCorner)
  }

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toString: String = {
    s"Dims(w: ${width.inPixels} px, h: ${height.inPixels} px)"
  }

}
