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


import aalto.smcl.infrastructure.{CommonDoubleMathOps, FlatMap, ItemItemMap, ToTuple}




/**
 * Base class for magnitudes, such as length, area, and volume.
 *
 * @param value
 *
 * @author Aleksi Lukkarinen
 */
abstract class AbstractMagnitude[ElementType](
    private val value: Double)
    extends AbstractGeometryObject
            with Equals
            with ToTuple[Tuple1[Double]]
            with FlatMap[ElementType, Double]
            with ItemItemMap[ElementType, Double]
            with CommonDoubleMathOps[ElementType] {

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  @inline
  override
  def toTuple: Tuple1[Double] = {
    Tuple1(value)
  }

  /**
   * Converts the object to a tuple.
   *
   * @return
   */
  @inline
  def toIntTuple: Tuple1[Int] = {
    Tuple1(value.toInt)
  }

  /**
   *
   * @param f
   *
   * @return
   */
  @inline
  override
  def flatMap(f: (Double) => ElementType): ElementType = {
    f(value)
  }

  /**
   *
   *
   * @return
   */
  override
  lazy val hashCode: Int = {
    val prime = 31

    prime + value.##
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
      case that: AbstractMagnitude[ElementType] =>
        that.canEqual(this) &&
            that.value == this.value

      case _ => false
    }
  }

}
