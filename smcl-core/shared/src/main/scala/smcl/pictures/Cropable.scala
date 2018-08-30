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

package smcl.pictures


import smcl.modeling.d2
import smcl.modeling.d2.{Bounds, Pos}




/**
  *
  * @tparam ReturnType
  *
  * @author Aleksi Lukkarinen
  */
trait Cropable[ReturnType <: PictureElement] {

  /**
    *
    * @param boundary
    *
    * @return
    */
  @inline
  final
  def crop(boundary: Bounds): ReturnType = {
    crop(
      boundary.upperLeftCorner,
      boundary.lowerRightCorner)
  }

  /**
    *
    * @param upperLeftCorner
    * @param lowerRightCorner
    *
    * @return
    */
  @inline
  final
  def crop(
      upperLeftCorner: Pos,
      lowerRightCorner: Pos): ReturnType = {

    crop(
      upperLeftCorner.xInPixels,
      upperLeftCorner.yInPixels,
      lowerRightCorner.xInPixels,
      lowerRightCorner.yInPixels)
  }

  /**
    *
    * @param upperLeftXInPixels
    * @param upperLeftYInPixels
    * @param lowerRightXInPixels
    * @param lowerRightYInPixels
    *
    * @return
    */
  def crop(
      upperLeftXInPixels: Double,
      upperLeftYInPixels: Double,
      lowerRightXInPixels: Double,
      lowerRightYInPixels: Double): ReturnType

  /**
    *
    * @param background
    * @param upperLeftCornerOfCropped
    *
    * @return
    */
  @inline
  final
  def cropToSizeOf(
      background: d2.HasDims,
      upperLeftCornerOfCropped: Pos): ReturnType = {

    crop(
      upperLeftCornerOfCropped,
      background.dimensions.width.inPixels,
      background.dimensions.height.inPixels)
  }

  /**
    *
    * @param upperLeftCorner
    * @param widthInPixels
    * @param heightInPixels
    *
    * @return
    */
  def crop(
      upperLeftCorner: Pos,
      widthInPixels: Double,
      heightInPixels: Double): ReturnType

}
