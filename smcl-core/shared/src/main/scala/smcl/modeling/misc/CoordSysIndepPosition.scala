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
 * A position in some coordinate system.
 *
 * @tparam DimensionType
 *
 * @author Aleksi Lukkarinen
 */
trait CoordSysIndepPosition[DimensionType <: CoordSysIndepDimensions]
    extends ModelingObject
        with Equals
        with Iterable[Double]
        with HasDimensions[DimensionType] {

  /**
   *
   *
   * @return
   */
  protected
  def coordinates: Seq[Double]

  /**
   * Provides an iterator for the dimension values.
   *
   * @return
   */
  @inline
  override final
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
      case that: CoordSysIndepPosition[DimensionType] =>
        that.canEqual(this) &&
            that.coordinates == this.coordinates

      case _ => false
    }
  }

}
