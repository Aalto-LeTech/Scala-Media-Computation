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
class RichDouble(val value: Double) {

  /**
   *
   *
   * @param minimum
   *
   * @return
   */
  def atLeast(minimum: Double): Double =
    value.max(minimum)

  /**
   *
   *
   * @param maximum
   *
   * @return
   */
  def atMost(maximum: Double): Double =
    value.min(maximum)

  /**
   *
   *
   * @param low
   * @param high
   *
   * @return
   */
  def isBetween(low: Double, high: Double): Boolean =
    value >= low && value < high

  /**
   *
   *
   * @param low
   * @param high
   *
   * @return
   */
  def clamp(low: Double, high: Double): Double =
    value atLeast low atMost high

  /**
   *
   *
   * @return
   */
  def closestInt: Int = {
    val longValue = value.round

    if (longValue > Int.MaxValue || longValue < Int.MinValue)
      throw new IllegalArgumentException("Integer out of bounds: " + longValue)

    longValue.toInt
  }

  /**
   *
   *
   * @return
   */
  def truncate: Double = {
    if (value > 0)
      return value.floor

    value.ceil
  }

  /**
   *
   *
   * @return
   */
  def truncatedInt: Int = value.toInt

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
