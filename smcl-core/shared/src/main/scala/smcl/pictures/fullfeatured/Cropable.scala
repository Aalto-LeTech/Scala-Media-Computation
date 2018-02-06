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

package smcl.pictures.fullfeatured


import smcl.modeling.d2
import smcl.modeling.d2.{Bounds, Pos}




/**
 *
 *
 * @tparam ReturnType
 *
 * @author Aleksi Lukkarinen
 */
trait Cropable[ReturnType <: ImageElement] {

  /**
   *
   *
   * @param background
   * @param upperLeftCornerOfCropped
   *
   * @return
   */
  @inline
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
   *
   * @param boundary
   *
   * @return
   */
  @inline
  def crop(boundary: Bounds): ReturnType = {
    crop(
      boundary.upperLeftMarker,
      boundary.lowerRightMarker)
  }

  /**
   *
   *
   * @param upperLeftCorner
   * @param lowerRightCorner
   *
   * @return
   */
  @inline
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
   *
   * @param upperLeftCorner
   * @param width
   * @param height
   *
   * @return
   */
  @inline
  def crop(
      upperLeftCorner: Pos,
      width: Double,
      height: Double): ReturnType = {

    crop(
      upperLeftCorner.xInPixels,
      upperLeftCorner.yInPixels,
      upperLeftCorner.xInPixels + width - 1,
      upperLeftCorner.yInPixels + height - 1)
  }

  /**
   *
   *
   * @param upperLeftX
   * @param upperLeftY
   * @param lowerRightX
   * @param lowerRightY
   *
   * @return
   */
  @inline
  def crop(
      upperLeftX: Double,
      upperLeftY: Double,
      lowerRightX: Double,
      lowerRightY: Double): ReturnType

}
