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


import smcl.modeling.Len
import smcl.modeling.misc.AbstractMovable
import smcl.settings._




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
  def + (offsets: Dims): ReturnType =
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
  def + (offsetsInPixels: CoordinateTuple): ReturnType =
    moveBy(
      offsetsInPixels._1,
      offsetsInPixels._2,
      offsetsInPixels._3)

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  def - (offsets: Dims): ReturnType =
    moveBy(
      -offsets.width.inPixels,
      -offsets.height.inPixels,
      -offsets.depth.inPixels)

  /**
   *
   *
   * @param offsetsInPixels
   *
   * @return
   */
  def - (offsetsInPixels: CoordinateTuple): ReturnType =
    moveBy(
      -offsetsInPixels._1,
      -offsetsInPixels._2,
      -offsetsInPixels._3)

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  def moveBy(offsets: Dims): ReturnType =
    moveBy(
      offsets.width.inPixels,
      offsets.height.inPixels,
      offsets.depth.inPixels)

  /**
   *
   *
   * @param xOffsetInPixels
   * @param yOffsetInPixels
   * @param zOffsetInPixels
   *
   * @return
   */
  def moveBy(
      xOffsetInPixels: Len,
      yOffsetInPixels: Len,
      zOffsetInPixels: Len): ReturnType = {

    moveBy(
      xOffsetInPixels.inPixels,
      yOffsetInPixels.inPixels,
      zOffsetInPixels.inPixels)
  }

  /**
   *
   *
   * @param xOffsetInPixels
   * @param yOffsetInPixels
   * @param zOffsetInPixels
   *
   * @return
   */
  def moveBy(
      xOffsetInPixels: Double,
      yOffsetInPixels: Double,
      zOffsetInPixels: Double): ReturnType

  /**
   *
   *
   * @param position
   * @param positionType
   *
   * @return
   */
  def moveTo(
      position: Pos,
      positionType: PositionType): ReturnType = {

    moveTo(
      position.xInPixels,
      position.yInPixels,
      position.zInPixels,
      positionType)
  }

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   * @param zCoordinateInPixels
   * @param positionType
   *
   * @return
   */
  def moveTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double,
      zCoordinateInPixels: Double,
      positionType: PositionType): ReturnType = {

    positionType match {
      case PosTypeCenter =>
        moveCenterTo(
          xCoordinateInPixels,
          yCoordinateInPixels,
          zCoordinateInPixels)

      case PosTypeUpperLeftCorner =>
        moveUpperLeftCornerTo(
          xCoordinateInPixels,
          yCoordinateInPixels,
          zCoordinateInPixels)
    }
  }

  /**
   *
   *
   * @param position
   *
   * @return
   */
  def moveUpperLeftCornerTo(position: Pos): ReturnType =
    moveUpperLeftCornerTo(
      position.xInPixels,
      position.yInPixels,
      position.zInPixels)

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   * @param zCoordinateInPixels
   *
   * @return
   */
  def moveUpperLeftCornerTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double,
      zCoordinateInPixels: Double): ReturnType

  /**
   *
   *
   * @param position
   *
   * @return
   */
  def moveCenterTo(position: Pos): ReturnType =
    moveCenterTo(
      position.xInPixels,
      position.yInPixels,
      position.zInPixels)

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   * @param zCoordinateInPixels
   *
   * @return
   */
  def moveCenterTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double,
      zCoordinateInPixels: Double): ReturnType

}
