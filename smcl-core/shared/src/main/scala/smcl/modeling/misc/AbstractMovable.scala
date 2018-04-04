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


import smcl.settings._




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
   * @param positionType
   *
   * @return
   */
  @inline
  final
  def moveTo(
      coordinatesInPixels: Seq[Double],
      positionType: PositionType = DefaultPositionType): ReturnType = {

    positionType match {
      case PosTypeCenter          => moveCenterTo(coordinatesInPixels)
      case PosTypeUpperLeftCorner => moveUpperLeftCornerTo(coordinatesInPixels)
    }
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  def moveCenterTo(coordinatesInPixels: Seq[Double]): ReturnType

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  def moveUpperLeftCornerTo(coordinatesInPixels: Seq[Double]): ReturnType

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
