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

package aalto.smcl.bitmaps


import aalto.smcl.geometry.Dims




/**
 * A rectangle having its dimensions measured in pixels.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
trait PixelRectangle {

  /** Width of this rectangle in pixels. */
  def widthInPixels: Int

  /** Height of this rectangle in pixels. */
  def heightInPixels: Int

  /** The range of numbers from zero until the width of this rectangle. */
  lazy val widthRangeInPixels: Range = 0 until widthInPixels

  /** The range of numbers from zero until the height of this rectangle. */
  lazy val heightRangeInPixels: Range = 0 until heightInPixels

  /** Dimensions (width and height) of this rectangle. */
  private[smcl] lazy val sizeInPixels: Dims[Int] = Dims(widthInPixels, heightInPixels)

  /** Area of this rectangle in pixels (equals to `pixelCount`). */
  lazy val areaInPixels: Int = widthInPixels * heightInPixels

  /** Total number of pixels occupied by the area of this rectangle (equals to `areaInPixels`). */
  lazy val pixelCount: Int = areaInPixels

}
