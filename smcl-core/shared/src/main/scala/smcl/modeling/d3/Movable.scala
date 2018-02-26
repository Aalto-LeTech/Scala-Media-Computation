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

package smcl.modeling.d3


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
      offsets.height.inPixels,
      offsets.depth.inPixels)

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
      offsetsInPixels._2,
      offsetsInPixels._3)

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
      -offsetsInPixels._2,
      -offsetsInPixels._3)

  /**
   *
   *
   * @param xOffsetInPixels
   * @param yOffsetInPixels
   * @param zOffsetInPixels
   *
   * @return
   */
  @inline
  def moveBy(
      xOffsetInPixels: Double,
      yOffsetInPixels: Double,
      zOffsetInPixels: Double): ReturnType

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   * @param zCoordinateInPixels
   *
   * @return
   */
  @inline
  def moveTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double,
      zCoordinateInPixels: Double): ReturnType

}
