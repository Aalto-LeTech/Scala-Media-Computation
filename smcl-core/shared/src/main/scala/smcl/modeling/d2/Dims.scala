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

package smcl.modeling.d2


import smcl.infrastructure._
import smcl.modeling.misc.CartesianDimensions
import smcl.modeling.{Area, Len}




/**
 * Companion object for the [[Dims]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Dims {

  /** A [[Dims]] instance that presents infinitely large positive dimensions. */
  lazy val PositiveInfinity: Dims = createConstantInstance(Len.PositiveInfinity)

  /** A [[Dims]] instance that presents infinitely large negative dimensions. */
  lazy val NegativeInfinity: Dims = createConstantInstance(Len.NegativeInfinity)

  /** A [[Dims]] instance that presents one-sized dimensions. */
  lazy val Zeros: Dims = createConstantInstance(Len.Zero)

  /** A [[Dims]] instance that presents zero-sized dimensions. */
  lazy val Ones: Dims = createConstantInstance(Len.One)

  /**
   * Creates a new [[Dims]] instance.
   *
   * @param constant
   *
   * @return
   */
  @inline
  private
  def createConstantInstance(
      constant: Len): Dims = {

    apply(constant, constant)
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
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  @inline
  def apply(
      widthInPixels: Double,
      heightInPixels: Double): Dims = {

    apply(
      Len(widthInPixels),
      Len(heightInPixels))
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
      dimensions.lengthCompare(NumberOfDimensions) == 0,
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
    extends CartesianDimensions
        with ToTuple[DimensionTuple]
        with ItemItemMap[Dims, Double]
        with FlatMap[Dims, DimensionTuple]
        with CommonTupledDoubleMathOps[Dims, DimensionTuple]
        with TupledMinMaxItemOps[Dims, Double, DimensionTuple] {

  /** */
  lazy val lengths: Seq[Double] =
    Seq(width.inPixels, height.inPixels)

  /** */
  lazy val center: Dims = Dims(width.half, height.half)

  /** */
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
   * @param newWidth
   *
   * @return
   */
  @inline
  def setWidth(newWidth: Double): Dims =
    setWidth(Len(newWidth))

  /**
   *
   *
   * @param newWidth
   *
   * @return
   */
  @inline
  def setWidth(newWidth: Len): Dims =
    copy(newWidth = newWidth)

  /**
   *
   *
   * @param newHeight
   *
   * @return
   */
  @inline
  def setHeight(newHeight: Double): Dims =
    setHeight(Len(newHeight))

  /**
   *
   *
   * @param newHeight
   *
   * @return
   */
  @inline
  def setHeight(newHeight: Len): Dims =
    copy(newHeight = newHeight)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def wider(offset: Double): Dims =
    setWidth(width + offset)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def wider(offset: Len): Dims =
    setWidth(width + offset)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def higher(offset: Double): Dims =
    setHeight(height + offset)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def higher(offset: Len): Dims =
    setHeight(height + offset)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def grow(offset: Double): Dims =
    copy(
      newWidth = width + offset,
      newHeight = height + offset)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def grow(offset: Len): Dims =
    copy(
      newWidth = width + offset,
      newHeight = height + offset)

  /**
   *
   *
   * @param widthOffset
   * @param heightOffset
   *
   * @return
   */
  @inline
  def grow(
      widthOffset: Double,
      heightOffset: Double): Dims = {

    copy(
      newWidth = width + widthOffset,
      newHeight = height + heightOffset)
  }

  /**
   *
   *
   * @param widthOffset
   * @param heightOffset
   *
   * @return
   */
  @inline
  def grow(
      widthOffset: Len,
      heightOffset: Len): Dims = {

    copy(
      newWidth = width + widthOffset,
      newHeight = height + heightOffset)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def shrink(offset: Double): Dims =
    copy(
      newWidth = width - offset,
      newHeight = height - offset)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def shrink(offset: Len): Dims =
    copy(
      newWidth = width - offset,
      newHeight = height - offset)

  /**
   *
   *
   * @param widthOffset
   * @param heightOffset
   *
   * @return
   */
  @inline
  def shrink(
      widthOffset: Double,
      heightOffset: Double): Dims = {

    copy(
      newWidth = width - widthOffset,
      newHeight = height - heightOffset)
  }

  /**
   *
   *
   * @param widthOffset
   * @param heightOffset
   *
   * @return
   */
  @inline
  def shrink(
      widthOffset: Len,
      heightOffset: Len): Dims = {

    copy(
      newWidth = width - widthOffset,
      newHeight = height - heightOffset)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def toIntTuple: (Int, Int) = {
    val w = width.inPixels.floor.toInt
    val h = height.inPixels.floor.toInt

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
  def toBoundsFrom(upperLeftCorner: Pos): Bounds = {
    val lowerRightCorner =
      upperLeftCorner + (width.inPixels, height.inPixels)

    Bounds(upperLeftCorner, lowerRightCorner)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def diagonalDistance: Len =
    Len(math.sqrt(
      width.square.inPixels + height.square.inPixels))

  /**
   *
   *
   * @param newWidth
   * @param newHeight
   *
   * @return
   */
  @inline
  def copy(
      newWidth: Len = width,
      newHeight: Len = height): Dims = {

    Dims(newWidth, newHeight)
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
