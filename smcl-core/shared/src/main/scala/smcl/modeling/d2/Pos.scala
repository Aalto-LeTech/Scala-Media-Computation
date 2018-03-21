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


import scala.language.implicitConversions

import smcl.infrastructure._
import smcl.modeling.misc.CartesianPosition
import smcl.modeling.{AffineTransformation, Len, Transformer}




/**
 * Companion object for the [[Pos]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Pos {

  /** A [[Pos]] instance that represents the origo of a two-dimensional Cartesian coordinate system. */
  val Origo: Pos = createInstance(0.0, 0.0, isDefined = true)

  /** A [[Pos]] instance that represents a non-existent position. */
  val NotDefined: Pos = new Pos(0.0, 0.0, isDefined = false) // TODO: Mutation methods shouldn't do anything

  /**
   * Creates a new [[Pos]] instance.
   *
   * @param xInPixels
   * @param yInPixels
   *
   * @return
   */
  @inline
  def apply(
      xInPixels: Double,
      yInPixels: Double): Pos = {

    createInstance(
      xInPixels,
      yInPixels,
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
      isDefined = true)
  }

  /**
   * Creates a new [[Pos]] instance.
   *
   * @param xInPixels
   * @param yInPixels
   * @param isDefined
   *
   * @return
   */
  @inline
  private
  def createInstance(
      xInPixels: Double,
      yInPixels: Double,
      isDefined: Boolean): Pos = {

    new Pos(xInPixels, yInPixels, isDefined)
  }

  /**
   *
   *
   * @param p
   *
   * @return
   */
  @inline
  implicit def asPair(p: Pos): (Double, Double) =
    (p.xInPixels, p.yInPixels)

  /**
   *
   *
   * @param p
   *
   * @return
   */
  @inline
  implicit def asFlooredIntPair(p: Pos): (Int, Int) =
    (p.xInPixels.floor.toInt, p.yInPixels.floor.toInt)

}




/**
 * Position in a two-dimensional Cartesian coordinate system.
 *
 * @param xInPixels
 * @param yInPixels
 * @param isDefined
 *
 * @author Aleksi Lukkarinen
 * @author Juha Sorva
 */
case class Pos private[smcl](
    xInPixels: Double,
    yInPixels: Double,
    isDefined: Boolean)
    extends CartesianPosition[Dims]
        with ToTuple[CoordinateTuple]
        with ItemItemMap[Pos, Double]
        with FlatMap[Pos, CoordinateTuple]
        with CommonTupledDoubleMathOps[Pos, CoordinateTuple]
        with TupledMinMaxItemOps[Pos, Double, CoordinateTuple]
        with HasDims
        with HasBounds
        with Movable[Pos]
        with Scalable[Pos]
        with Rotatable[Pos]
        with Transformable[Pos] {

  /** */
  lazy val coordinates: Seq[Double] =
    Seq(xInPixels, yInPixels)

  /** */
  lazy val boundary: Bounds = Bounds(this, this)

  /** */
  lazy val dimensions: Dims = Dims.Zeros

  /**
   *
   *
   * @return
   */
  lazy val quadrant: Option[Short] = {
    if (isOnHorizontalAxis || isOnVerticalAxis)
      None
    else {
      if (xInPixels > 0) {
        if (yInPixels > 0) Some(1) else Some(4)
      }
      else {
        if (yInPixels > 0) Some(2) else Some(3)
      }
    }
  }

  /**
   *
   *
   * @return
   */
  @inline
  def isOrigo: Boolean = this == Pos.Origo

  /**
   *
   *
   * @return
   */
  @inline
  def xInPixelsFloored: Int = xInPixels.floor.toInt

  /**
   *
   *
   * @return
   */
  @inline
  def yInPixelsFloored: Int = yInPixels.floor.toInt

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  @inline
  override
  def toTuple: (Double, Double) = (xInPixels, yInPixels)

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  @inline
  override
  def toFlooredTuple: (Double, Double) = (xInPixelsFloored, yInPixelsFloored)

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  @inline
  def toFlooredIntTuple: (Int, Int) = (xInPixelsFloored, yInPixelsFloored)

  /**
   *
   *
   * @param f
   * @tparam ResultType
   *
   * @return
   */
  @inline
  def toTupleWith[ResultType](
      f: (Double, Double) => ResultType): (Pos, ResultType) = {

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
  @inline
  def toTupleFlooredWith[ResultType](
      f: (Int, Int) => ResultType): (Pos, ResultType) = {

    val fPos = floor
    val result = f(fPos.xInPixelsFloored, fPos.yInPixelsFloored)

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
  @inline
  def convertWith[ResultType](
      f: (Double, Double) => ResultType): ResultType = {

    f(xInPixels, yInPixels)
  }

  /**
   *
   *
   * @param f
   * @tparam ResultType
   *
   * @return
   */
  @inline
  def convertFlooredWith[ResultType](
      f: (Int, Int) => ResultType): ResultType = {

    f(xInPixelsFloored, yInPixelsFloored)
  }

  /**
   *
   *
   * @param yMax
   *
   * @return
   */
  @inline
  def noLowerThan(yMax: Double): Pos =
    Pos(
      xInPixels,
      yInPixels atMost yMax)

  /**
   *
   *
   * @param yLimit
   *
   * @return
   */
  @inline
  def noLowerThan(yLimit: Pos): Pos =
    Pos(
      xInPixels,
      yInPixels atMost yLimit.yInPixels)

  /**
   *
   *
   * @param yMin
   *
   * @return
   */
  @inline
  def noHigherThan(yMin: Double): Pos =
    Pos(
      xInPixels,
      yInPixels atLeast yMin)

  /**
   *
   *
   * @param yLimit
   *
   * @return
   */
  @inline
  def noHigherThan(yLimit: Pos): Pos =
    Pos(
      xInPixels,
      yInPixels atLeast yLimit.yInPixels)

  /**
   *
   *
   * @param xMin
   *
   * @return
   */
  @inline
  def noFurtherLeftThan(xMin: Double): Pos =
    Pos(
      xInPixels atLeast xMin,
      yInPixels)

  /**
   *
   *
   * @param xLimit
   *
   * @return
   */
  @inline
  def noFurtherLeftThan(xLimit: Pos): Pos =
    Pos(
      xInPixels atLeast xLimit.xInPixels,
      yInPixels)

  /**
   *
   *
   * @param xMax
   *
   * @return
   */
  @inline
  def noFurtherRightThan(xMax: Double): Pos =
    Pos(
      xInPixels atMost xMax,
      yInPixels)

  /**
   *
   *
   * @param xLimit
   *
   * @return
   */
  @inline
  def noFurtherRightThan(xLimit: Pos): Pos =
    Pos(
      xInPixels atMost xLimit.xInPixels,
      yInPixels)

  /**
   *
   *
   * @param min
   * @param max
   *
   * @return
   */
  @inline
  def clampX(
      min: Double,
      max: Double): Pos = {

    Pos(
      xInPixels atLeast min atMost max,
      yInPixels)
  }

  /**
   *
   *
   * @param xLimitMin
   * @param xLimitMax
   *
   * @return
   */
  @inline
  def clampX(
      xLimitMin: Pos,
      xLimitMax: Pos): Pos = {

    Pos(
      xInPixels atLeast xLimitMin.xInPixels atMost xLimitMax.xInPixels,
      yInPixels)
  }

  /**
   *
   *
   * @param min
   * @param max
   *
   * @return
   */
  @inline
  def clampY(
      min: Double,
      max: Double): Pos = {

    Pos(
      xInPixels,
      yInPixels atLeast min atMost max)
  }

  /**
   *
   *
   * @param yLimitMin
   * @param yLimitMax
   *
   * @return
   */
  @inline
  def clampY(
      yLimitMin: Pos,
      yLimitMax: Pos): Pos = {

    Pos(
      xInPixels,
      yInPixels atLeast yLimitMin.yInPixels atMost yLimitMax.yInPixels)
  }

  /**
   *
   *
   * @param xMin
   * @param xMax
   * @param yMin
   * @param yMax
   *
   * @return
   */
  @inline
  def clamp(
      xMin: Double,
      xMax: Double,
      yMin: Double,
      yMax: Double): Pos = {

    Pos(
      xInPixels atLeast xMin atMost xMax,
      yInPixels atLeast yMin atMost yMax)
  }

  /**
   *
   *
   * @param xLimitMin
   * @param xLimitMax
   * @param yLimitMin
   * @param yLimitMax
   *
   * @return
   */
  @inline
  def clamp(
      xLimitMin: Pos,
      xLimitMax: Pos,
      yLimitMin: Pos,
      yLimitMax: Pos): Pos = {

    Pos(
      xInPixels atLeast xLimitMin.xInPixels atMost xLimitMax.xInPixels,
      yInPixels atLeast yLimitMin.yInPixels atMost yLimitMax.yInPixels)
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
  def map(f: (Double) => Double): Pos = Pos(f(xInPixels), f(yInPixels))

  /**
   *
   *
   * @param f
   *
   * @return
   */
  @inline
  def flatMap(f: (CoordinateTuple) => Pos): Pos = f(toTuple)

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  override
  def canEqual(other: Any): Boolean = other.isInstanceOf[Pos]

  /**
   *
   *
   * @param offsetsInPixels
   *
   * @return
   */
  @inline
  override
  def moveBy(offsetsInPixels: Seq[Double]): Pos = {
    require(
      offsetsInPixels.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions offsets must be given (found: ${offsetsInPixels.length})")

    moveBy(
      offsetsInPixels.head,
      offsetsInPixels.tail.head)
  }

  /**
   *
   *
   * @param xOffsetInPixels
   * @param yOffsetInPixels
   *
   * @return
   */
  @inline
  def moveBy(
      xOffsetInPixels: Double,
      yOffsetInPixels: Double): Pos = {

    // -- DEBUG --
    //println(s"Move: ($xInPixels, $yInPixels) --> (${xInPixels + xOffset}, ${xInPixels + yOffset})")

    copy(
      newXInPixels = xInPixels + xOffsetInPixels,
      newYInPixels = yInPixels + yOffsetInPixels)
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  @inline
  override
  def moveUpperLeftCornerTo(coordinatesInPixels: Seq[Double]): Pos = {
    require(
      coordinatesInPixels.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions coordinates must be given (found: ${coordinatesInPixels.length})")

    copy(
      newXInPixels = coordinatesInPixels.head,
      newYInPixels = coordinatesInPixels.tail.head)
  }

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   *
   * @return
   */
  @inline
  override
  def moveUpperLeftCornerTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): Pos = {

    copy(
      newXInPixels = xCoordinateInPixels,
      newYInPixels = yCoordinateInPixels)
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  @inline
  override
  def moveCenterTo(coordinatesInPixels: Seq[Double]): Pos = {
    require(
      coordinatesInPixels.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions offsets must be given (found: ${coordinatesInPixels.length})")

    copy(
      newXInPixels = coordinatesInPixels.head,
      newYInPixels = coordinatesInPixels.tail.head)
  }

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   *
   * @return
   */
  @inline
  override
  def moveCenterTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): Pos = {

    copy(
      newXInPixels = xCoordinateInPixels,
      newYInPixels = yCoordinateInPixels)
  }

  /**
   * Returns the minimum of the items contained by this container.
   *
   * @return
   */
  @inline
  override
  def minItem: Double = {
    math.min(xInPixels, yInPixels)
  }

  /**
   * Returns the minimums of the different types of items
   * contained by both this and other given containers.
   *
   * @return
   */
  @inline
  override
  def minItems(others: Pos*): Pos = {
    val positions = this +: others
    val minX = positions.minBy(_.xInPixels).xInPixels
    val minY = positions.minBy(_.yInPixels).yInPixels

    Pos(minX, minY)
  }

  /**
   * Returns the maximum of the items contained by this container.
   *
   * @return
   */
  @inline
  override
  def maxItem: Double = {
    math.max(xInPixels, yInPixels)
  }

  /**
   * Returns the maximums of the different types of items
   * contained by both this and other given containers.
   *
   * @return
   */
  @inline
  override
  def maxItems(others: Pos*): Pos = {
    val positions = this +: others
    val maxX = positions.maxBy(_.xInPixels).xInPixels
    val maxY = positions.maxBy(_.yInPixels).yInPixels

    Pos(maxX, maxY)
  }

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  @inline
  def add(offsets: Dims): Pos = this + offsets

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def add(offset: Double): Pos = this + (offset, offset)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def add(offset: Len): Pos = {
    val offsetInPixels = offset.inPixels
    this + (offsetInPixels, offsetInPixels)
  }

  /**
   *
   *
   * @param offsetX
   * @param offsetY
   *
   * @return
   */
  @inline
  def add(
      offsetX: Double,
      offsetY: Double): Pos = {

    this + (offsetX, offsetY)
  }

  /**
   *
   *
   * @param offsetX
   * @param offsetY
   *
   * @return
   */
  @inline
  def add(
      offsetX: Len,
      offsetY: Len): Pos = {

    this + (offsetX.inPixels, offsetY.inPixels)
  }

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  @inline
  def subtract(offsets: Dims): Pos = this - offsets

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def subtract(offset: Double): Pos = this - (offset, offset)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def subtract(offset: Len): Pos = {
    val offsetInPixels = offset.inPixels
    this - (offsetInPixels, offsetInPixels)
  }

  /**
   *
   *
   * @param offsetX
   * @param offsetY
   *
   * @return
   */
  @inline
  def subtract(
      offsetX: Double,
      offsetY: Double): Pos = {

    this - (offsetX, offsetY)
  }

  /**
   *
   *
   * @param offsetX
   * @param offsetY
   *
   * @return
   */
  @inline
  def subtract(
      offsetX: Len,
      offsetY: Len): Pos = {

    this - (offsetX.inPixels, offsetY.inPixels)
  }

  /**
   *
   *
   * @param another
   *
   * @return
   */
  @inline
  def xDiff(another: Pos): Double =
    another.xInPixels - xInPixels

  /**
   *
   *
   * @param another
   *
   * @return
   */
  @inline
  def yDiff(another: Pos): Double =
    another.yInPixels - yInPixels

  /**
   *
   *
   * @param computeNew
   *
   * @return
   */
  @inline
  def withX(computeNew: Double => Double): Pos =
    setX(computeNew(xInPixels))

  /**
   *
   *
   * @param computeNew
   *
   * @return
   */
  @inline
  def withY(computeNew: Double => Double): Pos =
    setY(computeNew(yInPixels))

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  def distanceFrom(other: Pos): Len = {
    val diffX = other.xInPixels - xInPixels
    val diffY = other.yInPixels - yInPixels
    val distance = math.sqrt(diffX * diffX + diffY * diffY)

    Len(distance)
  }

  /**
   *
   *
   * @param newXInPixels
   *
   * @return
   */
  @inline
  def setX(newXInPixels: Double): Pos =
    copy(newXInPixels = newXInPixels)

  /**
   *
   *
   * @param newYInPixels
   *
   * @return
   */
  @inline
  def setY(newYInPixels: Double): Pos =
    copy(newYInPixels = newYInPixels)

  /**
   *
   *
   * @param offsetInPixels
   *
   * @return
   */
  @inline
  def addX(offsetInPixels: Double): Pos =
    setX(xInPixels + offsetInPixels)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def addX(offset: Len): Pos =
    setX(xInPixels + offset.inPixels)

  /**
   *
   *
   * @param offsetInPixels
   *
   * @return
   */
  @inline
  def addY(offsetInPixels: Double): Pos =
    setY(yInPixels + offsetInPixels)

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def addY(offset: Len): Pos =
    setY(yInPixels + offset.inPixels)

  /**
   *
   *
   * @param destination
   *
   * @return
   */
  @inline
  def dimsTo(destination: Pos): Dims = {
    Dims(
      destination.xInPixels - xInPixels,
      destination.yInPixels - yInPixels)
  }

  /**
   *
   *
   * @param destination
   *
   * @return
   */
  @inline
  def centerBetween(destination: Pos): Pos =
    destination + (this - destination).half

  /**
   *
   *
   * @param lowerRightCorner
   *
   * @return
   */
  @inline
  def toBoundsWith(lowerRightCorner: Pos): Bounds = {
    Bounds(this, lowerRightCorner)
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
    val (xMin, xMax) = MathUtils.sort(xInPixels, other.xInPixels)
    val (yMin, yMax) = MathUtils.sort(yInPixels, other.yInPixels)

    (Pos(xMin, yMin), Pos(xMax, yMax))
  }

  /**
   *
   *
   * @return
   */
  @inline
  def isOnHorizontalAxis: Boolean = yInPixels == 0.0

  /**
   *
   *
   * @return
   */
  @inline
  def isOnVerticalAxis: Boolean = xInPixels == 0.0

  /**
   *
   *
   * @return
   */
  @inline
  def isOnFirstQuadrant: Boolean = quadrant.contains(1)

  /**
   *
   *
   * @return
   */
  @inline
  def isOnSecondQuadrant: Boolean = quadrant.contains(2)

  /**
   *
   *
   * @return
   */
  @inline
  def isOnThirdQuadrant: Boolean = quadrant.contains(3)

  /**
   *
   *
   * @return
   */
  @inline
  def isOnFourthQuadrant: Boolean = quadrant.contains(4)

  /**
   *
   *
   * @param newXInPixels
   * @param newYInPixels
   *
   * @return
   */
  @inline
  def copy(
      newXInPixels: Double = xInPixels,
      newYInPixels: Double = yInPixels): Pos = {

    Pos(newXInPixels, newYInPixels)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def roughly: String =
    f"$xInPixels%1.2f,$yInPixels%1.2f"

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toString: String = s"($xInPixels, $yInPixels)"

  /**
   * Rotates this object around origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCWAroundOrigo: Pos = Transformer.rotateBy90DegsCW(this)

  /**
   * Rotates this object around its center by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW: Pos = this

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW(centerOfRotation: Pos): Pos =
    Transformer.rotateBy90DegsCW(this, centerOfRotation)

  /**
   * Rotates this object around origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCWAroundOrigo: Pos = Transformer.rotateBy90DegsCCW(this)

  /**
   * Rotates this object around the its center by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW: Pos = this

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW(centerOfRotation: Pos): Pos =
    Transformer.rotateBy90DegsCCW(this, centerOfRotation)

  /**
   * Rotates this object around origo (0,0) by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180DegsAroundOrigo: Pos = Transformer.rotateBy180Degs(this)

  /**
   * Rotates this object around its center by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs: Pos = this

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs(centerOfRotation: Pos): Pos =
    Transformer.rotateBy180Degs(this, centerOfRotation)

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  override
  def rotateByAroundOrigo(angleInDegrees: Double): Pos = Transformer.rotate(this, angleInDegrees)

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  override
  def rotateBy(angleInDegrees: Double): Pos = this

  /**
   * Rotates this object around a given point by the specified number of degrees.
   *
   * @param angleInDegrees
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): Pos = {

    Transformer.rotate(this, angleInDegrees, centerOfRotation)
  }

  /**
   *
   *
   * @param widthFactor
   * @param heightFactor
   *
   * @return
   */
  override
  def scaleBy(widthFactor: Double, heightFactor: Double): Pos = {
    copy(
      newXInPixels = widthFactor * width.inPixels,
      newYInPixels = heightFactor * height.inPixels)
  }

  /**
   * Transforms this object using the specified affine transformation.
   *
   * @param t
   *
   * @return
   */
  override
  def transformBy(t: AffineTransformation): Pos = t.process(this)

}
