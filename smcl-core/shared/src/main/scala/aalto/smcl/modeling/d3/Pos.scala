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


import aalto.smcl.infrastructure.{CommonTupledDoubleMathOps, FlatMap, ItemItemMap, MathUtils, ToTuple, TupledMinMaxItemOps}
import aalto.smcl.modeling.{CartesianPosition, Len}




/**
 * Companion object for [[Pos]].
 *
 * @author Aleksi Lukkarinen
 */
object Pos {

  /** The origo of a three-dimensional Cartesian coordinate system. */
  val Origo = new Pos(0.0, 0.0, 0.0)

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

    new Pos(xInPixels, yInPixels, zInPixels)
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
      coordinates.length == 3,
      s"Exactly three coordinates must be given (currently: ${coordinates.length})")

    apply(coordinates: _*)
  }

}




/**
 * Position in a three-dimensional Cartesian coordinate system.
 *
 * @param xInPixels
 * @param yInPixels
 * @param zInPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Pos private(
    xInPixels: Double,
    yInPixels: Double,
    zInPixels: Double)
    extends CartesianPosition(Seq(xInPixels, yInPixels, zInPixels))
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
  def toTuple: (Double, Double, Double) = {
    (xInPixels, yInPixels, zInPixels)
  }

  /**
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
      f(yInPixels),
      f(zInPixels))
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
  def moveBy(dX: Double, dY: Double, dZ: Double): Pos = {
    Pos(xInPixels + dX, yInPixels + dY, zInPixels + dZ)
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
      deltas.length == 3,
      s"Pos represents exactly three coordinates (given: ${deltas.length})")

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
    math.min(math.min(xInPixels, yInPixels), zInPixels)
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
    val minZ = positions.minBy(_.zInPixels).zInPixels

    Pos(minX, minY, minZ)
  }

  /**
   * Returns the maximum of the items contained by this container.
   *
   * @return
   */
  @inline
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
  @inline
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
   * @param offset
   *
   * @return
   */
  @inline
  def + (offset: Dims): Pos = {
    val x = xInPixels + offset.widthInPixels
    val y = yInPixels + offset.heightInPixels
    val z = zInPixels + offset.depthInPixels

    Pos(x, y, z)
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
    val z = zInPixels - offset.depthInPixels

    Pos(x, y, z)
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
  @inline
  def toMinMaxWith(other: Pos): (Pos, Pos) = {
    val (xMin, xMax) = MathUtils.sort(xInPixels, other.xInPixels)
    val (yMin, yMax) = MathUtils.sort(yInPixels, other.yInPixels)
    val (zMin, zMax) = MathUtils.sort(zInPixels, other.zInPixels)

    (Pos(xMin, yMin, zMin), Pos(xMax, yMax, zMax))
  }

}
