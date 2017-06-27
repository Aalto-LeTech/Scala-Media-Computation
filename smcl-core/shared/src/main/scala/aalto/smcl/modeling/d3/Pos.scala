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


import aalto.smcl.infrastructure.{CommonTupledDoubleMathOps, FlatMap, ItemItemMap, MinMaxItemOps, ToTuple}
import aalto.smcl.modeling.CartesianPosition




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
  def apply(coordinates: Double*): Dims = {
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
            with FlatMap[Pos]
            with CommonTupledDoubleMathOps[Pos, CoordinateTuple]
            with MinMaxItemOps[Pos, Double, CoordinateTuple]
            with Movable[Pos] {

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  override def toTuple: (Double, Double, Double) = {
    (xInPixels, yInPixels, zInPixels)
  }

  /**
   *
   * @param f
   *
   * @return
   */
  override def map(f: (Double) => Double): Pos = {
    Pos(
      f(xInPixels),
      f(yInPixels),
      f(zInPixels))
  }

  /**
   *
   *
   * @param dX
   * @param dY
   *
   * @return
   */
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
  override def moveBy(deltas: Double*): Pos = {
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
  override def minItem: Double = {
    math.min(math.min(xInPixels, yInPixels), zInPixels)
  }

  /**
   * Returns the minimums of the different types of items
   * contained by both this and other given containers.
   *
   * @return
   */
  override def minItems(others: Pos*): Pos = {
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
  override def maxItem: Double = {
    math.max(math.max(xInPixels, yInPixels), zInPixels)
  }

  /**
   * Returns the maximums of the different types of items
   * contained by both this and other given containers.
   *
   * @return
   */
  override def maxItems(others: Pos*): Pos = {
    val positions = this +: others
    val minX = positions.maxBy(_.xInPixels).xInPixels
    val minY = positions.maxBy(_.yInPixels).yInPixels
    val minZ = positions.maxBy(_.zInPixels).zInPixels

    Pos(minX, minY, minZ)
  }

}
