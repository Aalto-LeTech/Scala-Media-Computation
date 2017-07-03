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
trait CommonMathOps[ElementType] {

  /**
   *
   *
   * @return
   */
  @inline
  def abs: ElementType

  /**
   *
   *
   * @return
   */
  @inline
  def floor: ElementType

  /**
   *
   *
   * @return
   */
  @inline
  def ceiling: ElementType

  /**
   *
   *
   * @return
   */
  @inline
  def unary_+(): ElementType

  /**
   *
   *
   * @return
   */
  @inline
  def unary_-(): ElementType = inverse

  /**
   *
   *
   * @return
   */
  @inline
  def inverse: ElementType

  /**
   *
   *
   * @param exponent
   *
   * @return
   */
  @inline
  def power(exponent: Double): ElementType

  /**
   *
   *
   * @return
   */
  @inline
  def round: ElementType

  /**
   *
   *
   * @return
   */
  @inline
  def signum: ElementType

}
