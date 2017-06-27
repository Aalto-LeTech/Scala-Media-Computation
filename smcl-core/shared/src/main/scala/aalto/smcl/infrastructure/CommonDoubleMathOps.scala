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

package aalto.smcl.infrastructure


/**
 * An interface for some common mathematical operations that use Double values.
 *
 * @author Aleksi Lukkarinen
 */
trait CommonDoubleMathOps[ElementType <: ToTuple[ItemTupleType], ItemTupleType]
    extends CommonMathOps[ElementType, ItemTupleType] {

  this: DoubleDoubleMap[ElementType] =>

  /**
   *
   *
   * @return
   */
  @inline
  def abs: ElementType = {
    this.map(math.abs)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def floor: ElementType = {
    this.map(math.floor)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def ceiling: ElementType = {
    this.map(math.ceil)
  }

  /**
   *
   *
   * @param exponent
   *
   * @return
   */
  @inline
  def power(exponent: Double): ElementType = {
    this.map(math.pow(_, exponent))
  }

  /**
   *
   *
   * @return
   */
  @inline
  def round: ElementType = {
    this.map(d => math.round(d))
  }

  /**
   *
   *
   * @return
   */
  @inline
  def signum: ElementType = {
    this.map(math.signum)
  }

}
