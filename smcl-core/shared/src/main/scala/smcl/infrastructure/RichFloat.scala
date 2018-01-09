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


import smcl.modeling.{Angle, Area, Len, Vol}




/**
 *
 *
 * @author Juha Sorva
 * @author Aleksi Lukkarinen
 */
private[smcl]
class RichFloat(val value: Float) {

  /**
   *
   *
   * @param minimum
   *
   * @return
   */
  def atLeast(minimum: Float): Float =
    value.max(minimum)

  /**
   *
   *
   * @param maximum
   *
   * @return
   */
  def atMost(maximum: Float): Float =
    value.min(maximum)

  /**
   *
   *
   * @param low
   * @param high
   *
   * @return
   */
  def isBetween(low: Float, high: Float): Boolean =
    value >= low && value < high

  /**
   *
   *
   * @param low
   * @param high
   *
   * @return
   */
  def clamp(low: Float, high: Float): Float =
    value atLeast low atMost high

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def * (other: Len): Len = other * value

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def * (other: Area): Area = other * value

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def * (other: Vol): Vol = other * value

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def * (other: Angle): Angle = other * value

}
