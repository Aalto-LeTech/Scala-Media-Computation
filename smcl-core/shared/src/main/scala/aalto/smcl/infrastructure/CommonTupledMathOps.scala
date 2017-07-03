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
 * An interface for some common mathematical operations.
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
  @inline
  def toAbsTuple: ItemTupleType = {
    this.abs.toTuple
  }

  /**
   *
   *
   * @return
   */
  @inline
  def toFlooredTuple: ItemTupleType = {
    this.floor.toTuple
  }

  /**
   *
   *
   * @return
   */
  @inline
  def toCeilingTuple: ItemTupleType = {
    this.ceiling.toTuple
  }

  /**
   *
   *
   * @return
   */
  @inline
  def toInversedTuple: ItemTupleType = {
    this.inverse.toTuple
  }

  /**
   *
   *
   * @return
   */
  @inline
  def toPowerTuple(exponent: Double): ItemTupleType = {
    this.power(exponent).toTuple
  }

  /**
   *
   *
   * @return
   */
  @inline
  def toRoundedTuple: ItemTupleType = {
    this.round.toTuple
  }

  /**
   *
   *
   * @return
   */
  @inline
  def toSignumTuple: ItemTupleType = {
    this.signum.toTuple
  }

}
