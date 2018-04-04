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

package smcl.infrastructure


/**
 * An interface for some common mathematical operations.
 *
 * @tparam ElementType
 * @tparam ItemTupleType
 *
 * @author Aleksi Lukkarinen
 */
trait CommonTupledMathOps[ElementType <: ToTuple[ItemTupleType], ItemTupleType] {

  this: CommonMathOps[ElementType] =>

  /**
   *
   *
   * @return
   */
  def toAbsTuple: ItemTupleType = this.abs.toTuple

  /**
   *
   *
   * @return
   */
  def toFlooredTuple: ItemTupleType = this.floor.toTuple

  /**
   *
   *
   * @return
   */
  def toCeilingTuple: ItemTupleType = this.ceiling.toTuple

  /**
   *
   *
   * @return
   */
  def toInversedTuple: ItemTupleType = this.inverse.toTuple

  /**
   *
   *
   * @return
   */
  def toPowerTuple(exponent: Double): ItemTupleType =
    this.power(exponent).toTuple

  /**
   *
   *
   * @return
   */
  def toRoundedTuple: ItemTupleType = this.round.toTuple

  /**
   *
   *
   * @return
   */
  def toSignumTuple: ItemTupleType = this.signum.toTuple

}
