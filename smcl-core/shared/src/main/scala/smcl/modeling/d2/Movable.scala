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

package smcl.modeling.d2


import smcl.modeling.misc.AbstractMovable




/**
 *
 *
 * @tparam ReturnType
 *
 * @author Aleksi Lukkarinen
 */
trait Movable[ReturnType]
    extends AbstractMovable[ReturnType] {

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  @inline
  def moveBy(offsets: Dims): ReturnType =
    moveBy(
      offsets.width.inPixels,
      offsets.height.inPixels)

  /**
   *
   *
   * @param offsetsInPixels
   *
   * @return
   */
  @inline
  def + (offsetsInPixels: CoordinateTuple): ReturnType =
    moveBy(
      offsetsInPixels._1,
      offsetsInPixels._2)

  /**
   *
   *
   * @param offsetsInPixels
   *
   * @return
   */
  @inline
  def - (offsetsInPixels: CoordinateTuple): ReturnType =
    moveBy(
      -offsetsInPixels._1,
      -offsetsInPixels._2)

  /**
   *
   *
   * @param xOffsetInPixels
   * @param yOffsetInPixels
   *
   * @return
   */
  @inline
  def moveBy(
      xOffsetInPixels: Double,
      yOffsetInPixels: Double): ReturnType

  /**
   *
   *
   * @param position
   *
   * @return
   */
  @inline
  def moveTo(position: Pos): ReturnType =
    moveTo(
      position.xInPixels,
      position.yInPixels)

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   *
   * @return
   */
  @inline
  def moveTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): ReturnType

}
