package aalto.smcl.images

import java.awt.image._

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object BitmapImage {

  /** Default width of <code>BitmapImage</code> instances created without giving width. */
  val DEFAULT_WIDTH_IN_PIXELS: Int = 10

  /** Default height of <code>BitmapImage</code> instances created without giving height. */
  val DEFAULT_HEIGHT_IN_PIXELS: Int = 10

}

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class BitmapImage(
    val widthInPixels: Int = BitmapImage.DEFAULT_WIDTH_IN_PIXELS,
    val heightInPixels: Int = BitmapImage.DEFAULT_HEIGHT_IN_PIXELS) {

  require(widthInPixels > 0, s"Width of the image must be greater than zero (was $widthInPixels)")
  require(heightInPixels > 0, s"Height of the image must be greater than zero (was $heightInPixels)")

  /** Represents the range of numbers from zero to the width of this image. */
  val widthRange = 0 to widthInPixels

  /** Represents the range of numbers from zero to the height of this image. */
  val heightRange = 0 to heightInPixels

  /** Represents the pixels of this image. */
  val pixels = new Pixels(this)

}
