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


import aalto.smcl.infrastructure.{CommonTupledDoubleMathOps, FlatMap, ItemItemMap, MathUtils, ToTuple, TupledMinMaxItemOps}
import aalto.smcl.modeling.{AbstractCartesianPosition, Len}




/**
 * Companion object for the [[Pos]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Pos {

  /** The origo of a two-dimensional Cartesian coordinate system. */
  val Origo = new Pos(0.0, 0.0)

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

    new Pos(xInPixels, yInPixels)
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
      coordinates.length == 2,
      s"Exactly two coordinates must be given (currently: ${coordinates.length})")

    apply(coordinates: _*)
  }

}




/**
 * Position in a two-dimensional Cartesian coordinate system.
 *
 * @param xInPixels
 * @param yInPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Pos private(
    xInPixels: Double,
    yInPixels: Double)
    extends AbstractCartesianPosition(Seq(xInPixels, yInPixels))
            with ToTuple[CoordinateTuple]
            with ItemItemMap[Pos, Double]
            with FlatMap[Pos, CoordinateTuple]
            with CommonTupledDoubleMathOps[Pos, CoordinateTuple]
            with TupledMinMaxItemOps[Pos, Double, CoordinateTuple]
            with Movable[Pos] {

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
   *
   *
   * @param f
   *
   * @return
   */
  @inline
  override
  def map(f: (Double) => Double): Pos = {
    Pos(
      f(xInPixels),
      f(yInPixels))
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
   * @param deltas
   *
   * @return
   */
  @inline
  override
  def moveBy(deltas: Double*): Pos = {
    require(
      deltas.length == 2,
      s"Pos represents exactly two coordinates (given: ${deltas.length})")

    moveBy(deltas: _*)
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
    val x = xInPixels + offset.widthInPixels
    val y = yInPixels + offset.heightInPixels

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
    val x = xInPixels - offset.widthInPixels
    val y = yInPixels - offset.heightInPixels

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
  def distanceTo(other: Pos): Len = {
    val diffX = math.abs(other.xInPixels - xInPixels)
    val diffY = math.abs(other.yInPixels - yInPixels)
    val distance = math.sqrt(math.pow(diffX, 2) + math.pow(diffY, 2))

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

}
