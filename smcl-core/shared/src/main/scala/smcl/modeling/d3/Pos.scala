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

package smcl.modeling.d3


import scala.language.implicitConversions

import smcl.infrastructure.{CommonTupledDoubleMathOps, FlatMap, ItemItemMap, MathUtils, ToTuple, TupledMinMaxItemOps}
import smcl.modeling.Len
import smcl.modeling.misc.CartesianPosition




/**
 * Companion object for [[Pos]].
 *
 * @author Aleksi Lukkarinen
 */
object Pos {

  /** A [[Pos]] instance that represents the origo of a three-dimensional Cartesian coordinate system. */
  val Origo: Pos = createInstance(0.0, 0.0, 0.0, isDefined = true)

  /** A [[Pos]] instance that represents a non-existent position. */
  val NotDefined: Pos = createInstance(0.0, 0.0, 0.0, isDefined = false) // TODO: Mutation methods shouldn't do anything


  /**
   * Creates a new [[Pos]] instance.
   *
   * @param xInPixels
   * @param yInPixels
   * @param zInPixels
   * @param isDefined
   *
   * @return
   */
  @inline
  private
  def createInstance(
      xInPixels: Double,
      yInPixels: Double,
      zInPixels: Double,
      isDefined: Boolean): Pos = {

    new Pos(
      xInPixels,
      yInPixels,
      zInPixels,
      isDefined)
  }

  /**
   * Creates a new [[Pos]] instance.
   *
   * @param xInPixels
   * @param yInPixels
   * @param zInPixels
   *
   * @return
   */
  @inline
  def apply(
      xInPixels: Double,
      yInPixels: Double,
      zInPixels: Double): Pos = {

    createInstance(
      xInPixels,
      yInPixels,
      zInPixels,
      isDefined = true)
  }

  /**
   * Creates a new [[Pos]] instance.
   *
   * @param coordinates
   *
   * @return
   */
  @inline
  def apply(coordinates: Double*): Pos = {
    require(
      coordinates.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions coordinates must be given (found: ${coordinates.length})")

    createInstance(
      coordinates(0),
      coordinates(1),
      coordinates(2),
      isDefined = true)
  }

  /**
   *
   *
   * @param p
   *
   * @return
   */
  @inline
  implicit def asTriple(p: Pos): (Double, Double, Double) =
    (p.xInPixels, p.yInPixels, p.zInPixels)

  /**
   *
   *
   * @param p
   *
   * @return
   */
  @inline
  implicit def asFlooredIntTriple(p: Pos): (Int, Int, Int) =
    (p.xInPixels.floor.toInt,
        p.yInPixels.floor.toInt,
        p.zInPixels.floor.toInt)

}




/**
 * Position in a three-dimensional Cartesian coordinate system.
 *
 * @param xInPixels
 * @param yInPixels
 * @param zInPixels
 * @param isDefined
 *
 * @author Aleksi Lukkarinen
 */
case class Pos private(
    xInPixels: Double,
    yInPixels: Double,
    zInPixels: Double,
    isDefined: Boolean)
    extends CartesianPosition[Dims]
        with ToTuple[CoordinateTuple]
        with ItemItemMap[Pos, Double]
        with FlatMap[Pos, CoordinateTuple]
        with CommonTupledDoubleMathOps[Pos, CoordinateTuple]
        with TupledMinMaxItemOps[Pos, Double, CoordinateTuple]
        with HasDims
        with Movable[Pos] {

  /** */
  lazy val coordinates: Seq[Double] =
    Seq(xInPixels, yInPixels, zInPixels)

  /** */
  lazy val dimensions: Dims = Dims.Zeros

  /**
   *
   *
   * @return
   */
  def isOrigo: Boolean = this == Pos.Origo

  /**
   *
   *
   * @return
   */
  def xInPixelsFloored: Int = xInPixels.floor.toInt

  /**
   *
   *
   * @return
   */
  def yInPixelsFloored: Int = yInPixels.floor.toInt

  /**
   *
   *
   * @return
   */
  def zInPixelsFloored: Int = zInPixels.floor.toInt

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  override
  def toTuple: (Double, Double, Double) =
    (xInPixels, yInPixels, zInPixels)

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  override
  def toFlooredTuple: (Double, Double, Double) =
    (xInPixelsFloored, yInPixelsFloored, zInPixelsFloored)

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  def toFlooredIntTuple: (Int, Int, Int) =
    (xInPixelsFloored, yInPixelsFloored, zInPixelsFloored)

  /**
   *
   *
   * @param f
   * @tparam ResultType
   *
   * @return
   */
  def toTupleWith[ResultType](
      f: (Double, Double, Double) => ResultType): (Pos, ResultType) = {

    (this, convertWith(f))
  }

  /**
   *
   *
   * @param f
   * @tparam ResultType
   *
   * @return
   */
  def toTupleFlooredWith[ResultType](
      f: (Int, Int, Int) => ResultType): (Pos, ResultType) = {

    val fPos = floor
    val result = f(
      fPos.xInPixelsFloored,
      fPos.yInPixelsFloored,
      fPos.zInPixelsFloored)

    (fPos, result)
  }

  /**
   *
   *
   * @param f
   * @tparam ResultType
   *
   * @return
   */
  def convertWith[ResultType](
      f: (Double, Double, Double) => ResultType): ResultType = {

    f(xInPixels, yInPixels, zInPixels)
  }

  /**
   *
   *
   * @param f
   * @tparam ResultType
   *
   * @return
   */
  def convertFlooredWith[ResultType](
      f: (Int, Int, Int) => ResultType): ResultType = {

    f(xInPixelsFloored, yInPixelsFloored, zInPixelsFloored)
  }

  /**
   *
   * @param f
   *
   * @return
   */
  override
  def map(f: (Double) => Double): Pos =
    Pos(
      f(xInPixels),
      f(yInPixels),
      f(zInPixels))

  /**
   *
   *
   * @param f
   *
   * @return
   */
  def flatMap(f: (CoordinateTuple) => Pos): Pos = f(toTuple)

  /**
   *
   *
   * @param other
   *
   * @return
   */
  override
  def canEqual(other: Any): Boolean = other.isInstanceOf[Pos]

  /**
   *
   *
   * @param offsetsInPixels
   *
   * @return
   */
  override
  def moveBy(offsetsInPixels: Seq[Double]): Pos = {
    require(
      offsetsInPixels.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions offsets must be given (found: ${offsetsInPixels.length})")

    Pos(
      xInPixels + offsetsInPixels.head,
      yInPixels + offsetsInPixels.tail.head,
      zInPixels + offsetsInPixels.tail.tail.head)
  }

  /**
   *
   *
   * @param xOffsetInPixels
   * @param yOffsetInPixels
   * @param zOffsetInPixels
   *
   * @return
   */
  override
  def moveBy(
      xOffsetInPixels: Double,
      yOffsetInPixels: Double,
      zOffsetInPixels: Double): Pos = {

    Pos(
      xInPixels + xOffsetInPixels,
      yInPixels + yOffsetInPixels,
      zInPixels + zOffsetInPixels)
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  override
  def moveUpperLeftCornerTo(coordinatesInPixels: Seq[Double]): Pos =
    moveCenterTo(coordinatesInPixels)

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   * @param zCoordinateInPixels
   *
   * @return
   */
  override
  def moveUpperLeftCornerTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double,
      zCoordinateInPixels: Double): Pos = {

    Pos(
      xCoordinateInPixels,
      yCoordinateInPixels,
      zCoordinateInPixels)
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  override
  def moveCenterTo(coordinatesInPixels: Seq[Double]): Pos = {
    require(
      coordinatesInPixels.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions offsets must be given (found: ${coordinatesInPixels.length})")

    Pos(
      coordinatesInPixels.head,
      coordinatesInPixels.tail.head,
      coordinatesInPixels.tail.tail.head)
  }

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   * @param zCoordinateInPixels
   *
   * @return
   */
  override
  def moveCenterTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double,
      zCoordinateInPixels: Double): Pos = {

    Pos(
      xCoordinateInPixels,
      yCoordinateInPixels,
      zCoordinateInPixels)
  }

  /**
   * Returns the minimum of the items contained by this container.
   *
   * @return
   */
  override
  def minItem: Double = {
    math.min(math.min(xInPixels, yInPixels), zInPixels)
  }

  /**
   * Returns the minimums of the different types of items
   * contained by both this and other given containers.
   *
   * @return
   */
  override
  def minItems(others: Pos*): Pos = {
    val positions = this +: others
    val minX = positions.minBy(_.xInPixels).xInPixels
    val minY = positions.minBy(_.yInPixels).yInPixels
    val minZ = positions.minBy(_.zInPixels).zInPixels

    Pos(minX, minY, minZ)
  }

  /**
   * Returns the maximum of the items contained by this container.
   *
   * @return
   */
  override
  def maxItem: Double = {
    math.max(math.max(xInPixels, yInPixels), zInPixels)
  }

  /**
   * Returns the maximums of the different types of items
   * contained by both this and other given containers.
   *
   * @return
   */
  override
  def maxItems(others: Pos*): Pos = {
    val positions = this +: others
    val minX = positions.maxBy(_.xInPixels).xInPixels
    val minY = positions.maxBy(_.yInPixels).yInPixels
    val minZ = positions.maxBy(_.zInPixels).zInPixels

    Pos(minX, minY, minZ)
  }

  /**
   *
   *
   * @return
   */
  override
  def unary_+(): Pos = this

  /**
   *
   *
   * @return
   */
  override
  def inverse: Pos = Pos(-xInPixels, -yInPixels, -zInPixels)

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def distanceFrom(other: Pos): Len = {
    val diffX = math.abs(other.xInPixels - xInPixels)
    val diffY = math.abs(other.yInPixels - yInPixels)
    val diffZ = math.abs(other.zInPixels - zInPixels)
    val distance = math.sqrt(math.pow(diffX, 2) + math.pow(diffY, 2) + math.pow(diffZ, 2))

    Len(distance)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def toMinMaxWith(other: Pos): (Pos, Pos) = {
    val (xMin, xMax) = MathUtils.sort(xInPixels, other.xInPixels)
    val (yMin, yMax) = MathUtils.sort(yInPixels, other.yInPixels)
    val (zMin, zMax) = MathUtils.sort(zInPixels, other.zInPixels)

    (Pos(xMin, yMin, zMin), Pos(xMax, yMax, zMax))
  }

  /**
   *
   *
   * @param newXInPixels
   * @param newYInPixels
   * @param newZInPixels
   *
   * @return
   */
  def copy(
      newXInPixels: Double = xInPixels,
      newYInPixels: Double = yInPixels,
      newZInPixels: Double = zInPixels): Pos = {

    Pos(newXInPixels, newYInPixels, newZInPixels)
  }

  /**
   *
   *
   * @return
   */
  override
  def toString: String =
    s"Pos(x: $xInPixels px, y: $yInPixels px, z: $zInPixels px)"

}
