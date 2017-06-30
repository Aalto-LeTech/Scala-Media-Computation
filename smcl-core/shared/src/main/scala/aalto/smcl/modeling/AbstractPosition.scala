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

package aalto.smcl.modeling


import scala.annotation.tailrec




/**
 * Position in some coordinate system.
 *
 * @param coordinates
 *
 * @author Aleksi Lukkarinen
 */
abstract class AbstractPosition(
    val coordinates: Seq[Double])
    extends AbstractGeometryObject
            with Equals
            with Iterable[Double] {

  /**
   * Provides an iterator for the dimension values.
   *
   * @return
   */
  @inline
  override
  def iterator: Iterator[Double] = {
    coordinates.iterator
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
        coordinates: Seq[Double],
        sum: Int): Int = {

      if (coordinates.isEmpty)
        return sum

      hashCodeRecursive(
        coordinates.tail,
        prime * sum + coordinates.head.##)
    }

    hashCodeRecursive(coordinates, 1)
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
      case that: AbstractPosition =>
        that.canEqual(this) &&
            that.coordinates == this.coordinates

      case _ => false
    }
  }

}
