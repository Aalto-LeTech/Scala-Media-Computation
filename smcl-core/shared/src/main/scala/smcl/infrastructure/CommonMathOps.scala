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
 *
 * @author Aleksi Lukkarinen
 */
trait CommonMathOps[ElementType] {

  /**
   *
   *
   * @return
   */
  def abs: ElementType

  /**
   *
   *
   * @return
   */
  def floor: ElementType

  /**
   *
   *
   * @return
   */
  def ceiling: ElementType

  /**
   *
   *
   * @return
   */
  def double: ElementType

  /**
   *
   *
   * @return
   */
  def half: ElementType

  /**
   *
   *
   * @return
   */
  def inverse: ElementType

  /**
   *
   *
   * @param exponent
   *
   * @return
   */
  def power(exponent: Double): ElementType

  /**
   *
   *
   * @return
   */
  def quarter: ElementType

  /**
   *
   *
   * @return
   */
  def round: ElementType

  /**
   *
   *
   * @return
   */
  def signum: ElementType

  /**
   *
   *
   * @return
   */
  def triple: ElementType

  /**
   *
   *
   * @return
   */
  def unary_+(): ElementType

  /**
   *
   *
   * @return
   */
  def unary_-(): ElementType = inverse

}
