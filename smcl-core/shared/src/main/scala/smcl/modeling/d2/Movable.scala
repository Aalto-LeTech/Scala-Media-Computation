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


import smcl.modeling.Len
import smcl.modeling.misc.AbstractMovable
import smcl.settings.{PosTypeCenter, PositionType, PosTypeUpperLeftCorner}




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
  def + (offsets: Dims): ReturnType =
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
   * @param offsets
   *
   * @return
   */
  @inline
  def - (offsets: Dims): ReturnType =
    moveBy(
      -offsets.width.inPixels,
      -offsets.height.inPixels)

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
   * @param xOffset
   * @param yOffset
   *
   * @return
   */
  @inline
  def moveBy(
      xOffset: Len,
      yOffset: Len): ReturnType = {

    moveBy(
      xOffset.inPixels,
      yOffset.inPixels)
  }

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
   * @param positionType
   *
   * @return
   */
  @inline
  def moveTo(
      position: Pos,
      positionType: PositionType): ReturnType = {

    moveTo(
      position.xInPixels,
      position.yInPixels,
      positionType)
  }

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   * @param positionType
   *
   * @return
   */
  @inline
  def moveTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double,
      positionType: PositionType): ReturnType = {

    positionType match {
      case PosTypeCenter =>
        moveCenterTo(
          xCoordinateInPixels,
          yCoordinateInPixels)

      case PosTypeUpperLeftCorner =>
        moveUpperLeftCornerTo(
          xCoordinateInPixels,
          yCoordinateInPixels)
    }
  }

  /**
   *
   *
   * @param position
   *
   * @return
   */
  @inline
  def moveUpperLeftCornerTo(position: Pos): ReturnType =
    moveUpperLeftCornerTo(
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
  def moveUpperLeftCornerTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): ReturnType

  /**
   *
   *
   * @param position
   *
   * @return
   */
  @inline
  def moveCenterTo(position: Pos): ReturnType =
    moveCenterTo(
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
  def moveCenterTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): ReturnType

}
