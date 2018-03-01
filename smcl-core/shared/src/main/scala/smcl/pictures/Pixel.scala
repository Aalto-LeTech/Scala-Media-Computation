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


import smcl.colors.rgb.Color




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
 *
 * @author Aleksi Lukkarinen
 */
case class Pixel private[pictures](
    relatedPixelSnapshot: PixelSnapshot,
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
  @inline
  def red: Int = relatedPixelSnapshot.reds(linearPosition)

  /**
   *
   *
   * @param value
   */
  @inline
  def red_=(value: Int): Unit =
    relatedPixelSnapshot.reds(linearPosition) = value

  /**
   *
   *
   * @return
   */
  @inline
  def green: Int = relatedPixelSnapshot.greens(linearPosition)

  /**
   *
   *
   * @param value
   */
  @inline
  def green_=(value: Int): Unit =
    relatedPixelSnapshot.greens(linearPosition) = value

  /**
   *
   *
   * @return
   */
  @inline
  def blue: Int = relatedPixelSnapshot.blues(linearPosition)

  /**
   *
   *
   * @param value
   */
  @inline
  def blue_=(value: Int): Unit =
    relatedPixelSnapshot.blues(linearPosition) = value

  /**
   *
   *
   * @return
   */
  @inline
  def opacity: Int = relatedPixelSnapshot.opacities(linearPosition)

  /**
   *
   *
   * @param value
   */
  @inline
  def opacity_=(value: Int): Unit =
    relatedPixelSnapshot.opacities(linearPosition) = value

  /**
   *
   *
   * @return
   */
  @inline
  def color: Color = Color(red, green, blue, opacity)

  /**
   *
   *
   * @return
   */
  @inline
  def color_=(value: Color): Unit = {
    red = value.red
    green = value.green
    blue = value.blue
    opacity = value.opacity
  }

  /**
   *
   *
   * @return
   */
  @inline
  def setFrom(other: Pixel): Unit = {
    red = other.red
    green = other.green
    blue = other.blue
    opacity = other.opacity
  }

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toString: String =
    s"Pixel($currentXInPixels, $currentYInPixels): $red - $green - $blue - $opacity"

}
