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
  def abs: ElementType = map(math.abs)

  /**
   *
   *
   * @return
   */
  def ceiling: ElementType = map(math.ceil)

  /**
   *
   *
   * @return
   */
  def double: ElementType = map(2 * _)

  /**
   *
   *
   * @return
   */
  def floor: ElementType = map(math.floor)

  /**
   *
   *
   * @return
   */
  def half: ElementType = map(_ / 2.0)

  /**
   *
   *
   * @return
   */
  def inverse: ElementType = map(-_)

  /**
   *
   *
   * @return
   */
  def unary_+(): ElementType = map(x => x)

  /**
   *
   *
   * @param exponent
   *
   * @return
   */
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
  def round: ElementType = map(d => math.round(d))

  /**
   *
   *
   * @return
   */
  def signum: ElementType = map(math.signum)

  /**
   *
   *
   * @return
   */
  def triple: ElementType = map(3 * _)

  /**
   *
   *
   * @return
   */
  def truncate: ElementType = map {value =>
    if (value > 0)
      math.floor(value)
    else
      math.ceil(value)
  }

}
