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
import aalto.smcl.modeling.misc.CartesianPosition
import aalto.smcl.modeling.{Len, Transformer}




/**
 * Companion object for the [[Pos]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Pos {

  /** A [[Pos]] instance that represents the origo of a two-dimensional Cartesian coordinate system. */
  val Origo: Pos = createInstance(0.0, 0.0, isDefined = true)

  /** A [[Pos]] instance that represents a non-existent position. */
  val NotDefined: Pos = createInstance(0.0, 0.0, isDefined = false)

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

}




/**
 * Position in a two-dimensional Cartesian coordinate system.
 *
 * @param xInPixels
 * @param yInPixels
 * @param isDefined
 *
 * @author Aleksi Lukkarinen
 */
case class Pos private[smcl](
    xInPixels: Double,
    yInPixels: Double,
    isDefined: Boolean)
    extends CartesianPosition
        with ToTuple[CoordinateTuple]
        with ItemItemMap[Pos, Double]
        with FlatMap[Pos, CoordinateTuple]
        with CommonTupledDoubleMathOps[Pos, CoordinateTuple]
        with TupledMinMaxItemOps[Pos, Double, CoordinateTuple]
        with Movable[Pos]
        with Rotatable[Pos] {

  /** */
  lazy val coordinates: Seq[Double] = Seq(xInPixels, yInPixels)

  /** */
  lazy val boundary: Bounds = Bounds(this, this)

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
   * Converts the object to a tuple.
   *
   * @return
   */
  @inline
  override
  def toTuple: (Double, Double) = {
    (xInPixels, yInPixels)
  }

  /**
   * Converts the object to a floored tuple of Ints.
   *
   * @return
   */
  @inline
  def toFlooredIntTuple: (Int, Int) = {
    (toFlooredTuple._1.toInt,
        toFlooredTuple._2.toInt)
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
  def map(f: (Double) => Double): Pos = {
    Pos(f(xInPixels), f(yInPixels))
  }

  /**
   *
   *
   * @param f
   *
   * @return
   */
  @inline
  def flatMap(f: (CoordinateTuple) => Pos): Pos = {
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
    other.isInstanceOf[Pos]
  }

  /**
   *
   *
   * @param dX
   * @param dY
   *
   * @return
   */
  @inline
  def moveBy(dX: Double, dY: Double): Pos = {
    Pos(xInPixels + dX, yInPixels + dY)
  }

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  @inline
  override
  def moveBy(offsets: Double*): Pos = {
    require(
      offsets.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions offsets must be given (found: ${offsets.length})")

    moveBy(offsets(0), offsets(1))
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
   * @param offset
   *
   * @return
   */
  @inline
  def + (offset: Dims): Pos = {
    val x = xInPixels + offset.width.inPixels
    val y = yInPixels + offset.height.inPixels

    Pos(x, y)
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
    val x = xInPixels - offset.width.inPixels
    val y = yInPixels - offset.height.inPixels

    Pos(x, y)
  }

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
   * @param other
   *
   * @return
   */
  @inline
  def toBoundsWith(other: Pos): Bounds = {
    Bounds(this, other)
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
  override
  def toString: String = {
    s"Pos(x: $xInPixels px, y: $yInPixels px)"
  }

  /**
   * Rotates this object around the origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW: Pos = {
    Transformer.rotateBy90DegsCW(this)
  }

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW(centerOfRotation: Pos): Pos = {
    Transformer.rotateBy90DegsCW(this, centerOfRotation)
  }

  /**
   * Rotates this object around the origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW: Pos = {
    Transformer.rotateBy90DegsCCW(this)
  }

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW(centerOfRotation: Pos): Pos = {
    Transformer.rotateBy90DegsCCW(this, centerOfRotation)
  }

  /**
   * Rotates this object around the origo (0,0) by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs: Pos = {
    Transformer.rotateBy180Degs(this)
  }

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs(centerOfRotation: Pos): Pos = {
    Transformer.rotateBy180Degs(this, centerOfRotation)
  }

  /**
   * Rotates this object around the origo (0,0) by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  override
  def rotateBy(angleInDegrees: Double): Pos = {
    Transformer.rotate(this, angleInDegrees)
  }

  /**
   * Rotates this object around a given point of the specified number of degrees.
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

}
