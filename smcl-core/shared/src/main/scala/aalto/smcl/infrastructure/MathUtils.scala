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
object MathUtils {

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def sinCosFor(angleInDegrees: Double): (Double, Double) = {
    (math.sin(angleInDegrees): @inline,
        math.cos(angleInDegrees): @inline)
  }

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def sinFor(angleInDegrees: Double): Double = {
    math.sin(angleInDegrees): @inline
  }

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def cosFor(angleInDegrees: Double): Double = {
    math.cos(angleInDegrees): @inline
  }

}
