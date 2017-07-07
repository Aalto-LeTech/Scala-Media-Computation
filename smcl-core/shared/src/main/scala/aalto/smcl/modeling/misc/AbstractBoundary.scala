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

package aalto.smcl.modeling.misc

import scala.annotation.tailrec

import aalto.smcl.infrastructure.FlatMap




/**
 * Boundary of an object.
 *
 * @param markers
 *
 * @author Aleksi Lukkarinen
 */
abstract class AbstractBoundary[PositionType <: AbstractPosition](
    val markers: Seq[PositionType])
    extends AbstractGeometryObject
            with Equals
            with Iterable[PositionType]
            with FlatMap[AbstractBoundary[PositionType], Seq[PositionType]] {

  /**
   * Provides an iterator for the boundary marker positions.
   *
   * @return
   */
  @inline
  override
  def iterator: Iterator[PositionType] = {
    markers.iterator
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
      f: (Seq[PositionType]) => AbstractBoundary[PositionType]): AbstractBoundary[PositionType] = {

    f(markers)
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
        markers: Seq[PositionType],
        sum: Int): Int = {

      if (markers.isEmpty)
        return sum

      hashCodeRecursive(
        markers.tail,
        prime * sum + markers.head.##)
    }

    hashCodeRecursive(markers, 1)
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
      case that: AbstractBoundary[PositionType] =>
        that.canEqual(this) &&
            that.markers == this.markers

      case _ => false
    }
  }

}
