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

package smcl.modeling.misc


/**
 *
 *
 * @tparam ReturnType
 *
 * @author Aleksi Lukkarinen
 */
trait AbstractMovable[ReturnType] {

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  def moveTo(coordinatesInPixels: Seq[Double]): ReturnType

  /**
   *
   *
   * @param offsetsInPixels
   *
   * @return
   */
  def moveBy(offsetsInPixels: Seq[Double]): ReturnType

  /**
   *
   *
   * @param offsetsInPixels
   *
   * @return
   */
  def + (offsetsInPixels: Seq[Double]): ReturnType = moveBy(offsetsInPixels)

}
