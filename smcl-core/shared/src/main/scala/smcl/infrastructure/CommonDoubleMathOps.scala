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
 * An interface for some common mathematical operations that use Double values.
 *
 * @tparam ElementType
 *
 * @author Aleksi Lukkarinen
 */
trait CommonDoubleMathOps[ElementType]
    extends CommonMathOps[ElementType] {

  this: ItemItemMap[ElementType, Double] =>


  /**
   *
   *
   * @return
   */
  @inline
  def abs: ElementType = map(math.abs)

  /**
   *
   *
   * @return
   */
  @inline
  def ceiling: ElementType = map(math.ceil)

  /**
   *
   *
   * @return
   */
  @inline
  def double: ElementType = map(2 * _)

  /**
   *
   *
   * @return
   */
  @inline
  def floor: ElementType = map(math.floor)

  /**
   *
   *
   * @return
   */
  @inline
  def half: ElementType = map(_ / 2.0)

  /**
   *
   *
   * @return
   */
  @inline
  def inverse: ElementType = map(-_)

  /**
   *
   *
   * @return
   */
  @inline
  def unary_+(): ElementType = map(x => x)

  /**
   *
   *
   * @param exponent
   *
   * @return
   */
  @inline
  def power(exponent: Double): ElementType = {
    map(math.pow(_, exponent))
  }

  /**
   *
   *
   * @return
   */
  def quarter: ElementType = map(_ / 4.0)

  /**
   *
   *
   * @return
   */
  @inline
  def round: ElementType = map(d => math.round(d))

  /**
   *
   *
   * @return
   */
  @inline
  def signum: ElementType = map(math.signum)

  /**
   *
   *
   * @return
   */
  @inline
  def triple: ElementType = map(3 * _)

}
