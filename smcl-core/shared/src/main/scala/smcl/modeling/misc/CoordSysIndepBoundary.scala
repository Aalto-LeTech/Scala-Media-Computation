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

package smcl.modeling.misc


import scala.annotation.tailrec

import smcl.infrastructure.FlatMap




/**
 * Boundary of an object.
 *
 * @tparam PositionType
 * @tparam DimensionType
 *
 * @author Aleksi Lukkarinen
 */
trait CoordSysIndepBoundary[PositionType <: CoordSysIndepPosition[DimensionType], DimensionType <: CoordSysIndepDimensions]
    extends Measurement
        with Equals
        with Iterable[PositionType]
        with FlatMap[CoordSysIndepBoundary[PositionType, DimensionType], Seq[PositionType]] {

  /**
   *
   *
   * @return
   */
  @inline
  def corners: Seq[PositionType]

  /**
   * Provides an iterator for the boundary corner positions.
   *
   * @return
   */
  @inline
  override
  def iterator: Iterator[PositionType] = {
    corners.iterator
  }

  /**
   *
   * @param f
   *
   * @return
   */
  @inline
  override
  def flatMap(
      f: (Seq[PositionType]) => CoordSysIndepBoundary[PositionType, DimensionType]): CoordSysIndepBoundary[PositionType, DimensionType] = {

    f(corners)
  }

  /**
   *
   *
   * @return
   */
  override
  lazy val hashCode: Int = {
    val prime = 31

    @tailrec
    def hashCodeRecursive(
        corners: Seq[PositionType],
        sum: Int): Int = {

      if (corners.isEmpty)
        return sum

      hashCodeRecursive(
        corners.tail,
        prime * sum + corners.head.##)
    }

    hashCodeRecursive(corners, 1)
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
  def canEqual(other: Any): Boolean

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  override
  def equals(other: Any): Boolean = {
    other match {
      case that: CoordSysIndepBoundary[PositionType, DimensionType] =>
        that.canEqual(this) &&
            that.corners == this.corners

      case _ => false
    }
  }

}
