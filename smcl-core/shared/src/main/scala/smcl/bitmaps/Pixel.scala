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

package smcl.bitmaps


import smcl.bitmaps.fullfeatured.AbstractBitmap




/**
 *
 *
 * @param relatedPixelSnapshot
 * @param MinXInPixels
 * @param MaxXInPixels
 * @param MinYInPixels
 * @param MaxYInPixels
 * @param currentXInPixels
 * @param currentYInPixels
 * @tparam BitmapType
 *
 * @author Aleksi Lukkarinen
 */
case class Pixel[BitmapType <: AbstractBitmap] private[bitmaps](
    relatedPixelSnapshot: PixelSnapshot[BitmapType],
    MinXInPixels: Int,
    MaxXInPixels: Int,
    MinYInPixels: Int,
    MaxYInPixels: Int,
    currentXInPixels: Int,
    currentYInPixels: Int) {

  /** */
  private[this]
  lazy val linearPosition: Int =
    currentYInPixels * (MaxXInPixels - MinXInPixels + 1) + currentXInPixels


  /**
   *
   *
   * @return
   */
  def red: Int = relatedPixelSnapshot.reds(linearPosition)

  /**
   *
   *
   * @param value
   */
  def red_=(value: Int): Unit =
    relatedPixelSnapshot.reds(linearPosition) = value

  /**
   *
   *
   * @return
   */
  def green: Int = relatedPixelSnapshot.greens(linearPosition)

  /**
   *
   *
   * @param value
   */
  def green_=(value: Int): Unit =
    relatedPixelSnapshot.greens(linearPosition) = value

  /**
   *
   *
   * @return
   */
  def blue: Int = relatedPixelSnapshot.blues(linearPosition)

  /**
   *
   *
   * @param value
   */
  def blue_=(value: Int): Unit =
    relatedPixelSnapshot.blues(linearPosition) = value

  /**
   *
   *
   * @return
   */
  def opacity: Int = relatedPixelSnapshot.opacities(linearPosition)

  /**
   *
   *
   * @param value
   */
  def opacity_=(value: Int): Unit =
    relatedPixelSnapshot.opacities(linearPosition) = value

  /**
   *
   *
   * @return
   */
  override def toString: String =
    s"Pixel($currentXInPixels, $currentYInPixels): $red - $green - $blue - $opacity"

}
