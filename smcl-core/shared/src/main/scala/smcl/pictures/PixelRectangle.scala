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


import smcl.modeling.d2.Dims




/**
 * A rectangle having its dimensions measured in pixels.
 *
 * @author Aleksi Lukkarinen
 */
private[pictures]
trait PixelRectangle {

  /** Width of this rectangle in pixels. */
  def widthInPixels: Double

  /** Height of this rectangle in pixels. */
  def heightInPixels: Double

  /** Dimensions (width and height) of this rectangle. */
  private[smcl] lazy val sizeInPixels: Dims = Dims(widthInPixels, heightInPixels)

  /** Area of this rectangle in pixels (equals to `pixelCount`). */
  lazy val areaInPixels: Double = widthInPixels * heightInPixels

  /** Total number of pixels occupied by the area of this rectangle (equals to `areaInPixels`). */
  lazy val pixelCount: Double = areaInPixels

}
