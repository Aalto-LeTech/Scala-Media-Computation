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




/**
 * Some dimensions in some coordinate system.
 *
 * @author Aleksi Lukkarinen
 */
trait CoordSysIndepDimensions
    extends Measurement
        with Equals
        with Iterable[Double] {

  /**
   *
   *
   * @return
   */
  protected
  def lengths: Seq[Double]

  /**
   * Provides an iterator for the dimension values.
   *
   * @return
   */
  @inline
  override final
  def iterator: Iterator[Double] = {
    lengths.iterator
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
        lengths: Seq[Double],
        sum: Int): Int = {

      if (lengths.isEmpty)
        return sum

      hashCodeRecursive(
        lengths.tail,
        prime * sum + lengths.head.##)
    }

    hashCodeRecursive(lengths, 1)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  override
  def canEqual(other: Any): Boolean

  /**
   *
   *
   * @param other
   *
   * @return
   */
  override
  def equals(other: Any): Boolean = {
    other match {
      case that: CoordSysIndepDimensions =>
        that.canEqual(this) &&
            that.lengths == this.lengths

      case _ => false
    }
  }

}
