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
 * A sequence of dimensions.
 *
 * @param dimensions
 *
 * @author Aleksi Lukkarinen
 */
abstract class AbstractDimensions(
    val dimensions: Seq[Double])
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
    dimensions.iterator
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
        dimensions: Seq[Double],
        sum: Int): Int = {

      if (dimensions.isEmpty)
        return sum

      hashCodeRecursive(
        dimensions.tail,
        prime * sum + dimensions.head.##)
    }

    hashCodeRecursive(dimensions, 1)
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
      case that: AbstractDimensions =>
        that.canEqual(this) &&
            that.dimensions == this.dimensions

      case _ => false
    }
  }

}
