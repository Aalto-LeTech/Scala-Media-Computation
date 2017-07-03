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
 *
 *
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
    this.value.max(minimum)

  /**
   *
   *
   * @param maximum
   *
   * @return
   */
  def atMost(maximum: Float): Float =
    this.value.min(maximum)

  /**
   *
   *
   * @param low
   * @param high
   *
   * @return
   */
  def isBetween(low: Float, high: Float): Boolean =
    this.value >= low && this.value < high

}
