package aalto.smcl.images

import java.awt.image._

/**
 * @author Aleksi Lukkarinen
 */
object BitmapImage {

  /** Number of bits in one byte. */
  private val ONE_BYTE = 8

  /** Number of bits in two bytes. */
  private val TWO_BYTES = ONE_BYTE + ONE_BYTE

  /** Number of bits in three bytes. */
  private val THREE_BYTES = TWO_BYTES + ONE_BYTE

  /** Bits belonging to the rightmost byte of an <code>Int</code>. */
  private val FIRST_BYTE = 0xFF

  /** Bits belonging to the second-rightmost byte of an <code>Int</code>. */
  private val SECOND_BYTE = FIRST_BYTE << ONE_BYTE

  /** Bits belonging to the second-leftmost byte of an <code>Int</code>. */
  private val THIRD_BYTE = SECOND_BYTE << ONE_BYTE

  /** The value range that a single unsigned byte can represent. */
  private val BYTE_RANGE = 0 to 255

  /** Default width of <code>BitmapImage</code> instances created without giving width. */
  val DEFAULT_WIDTH_IN_PIXELS: Int = 10

  /** Default height of <code>BitmapImage</code> instances created without giving height. */
  val DEFAULT_HEIGHT_IN_PIXELS: Int = 10

  /** Color component value representing minimal amount of red. */
  val MIN_RED: Int = BYTE_RANGE.start

  /** Color component value representing maximal amount of red. */
  val MAX_RED: Int = BYTE_RANGE.end

  /** Color component value representing minimal amount of green. */
  val MIN_GREEN: Int = BYTE_RANGE.start

  /** Color component value representing maximal amount of green. */
  val MAX_GREEN: Int = BYTE_RANGE.end

  /** Color component value representing minimal amount of blue. */
  val MIN_BLUE: Int = BYTE_RANGE.start

  /** Color component value representing maximal amount of blue. */
  val MAX_BLUE: Int = BYTE_RANGE.end

  /** Color component value representing minimal transparency. */
  val MIN_TRANSPARENCY: Int = BYTE_RANGE.start

  /** Color component value representing maximal transparency. */
  val MAX_TRANSPARENCY: Int = BYTE_RANGE.end

  /** Color component value representing minimal transparency. */
  val FULLY_OPAQUE: Int = MIN_TRANSPARENCY

  /** Color component value representing maximal transparency. */
  val FULLY_TRANSPARENT: Int = MAX_TRANSPARENCY

  /**
   *
   *
   */
  def apply(widthInPixels: Int = DEFAULT_WIDTH_IN_PIXELS,
            heightInPixels: Int = DEFAULT_HEIGHT_IN_PIXELS): BitmapImage = {

    new BitmapImage(widthInPixels, heightInPixels)
  }

  /**
   *
   */
  private[images] def red(pixelInt: Int): Int = (pixelInt & THIRD_BYTE) >>> TWO_BYTES

  /**
   *
   */
  private[images] def green(pixelInt: Int): Int = (pixelInt & SECOND_BYTE) >>> ONE_BYTE

  /**
   *
   */
  private[images] def blue(pixelInt: Int): Int = pixelInt & FIRST_BYTE

  /**
   *
   */
  private[images] def transparency(pixelInt: Int): Int = pixelInt >>> THREE_BYTES

  /**
   *
   */
  private[images] def pixelBytes(pixelInt: Int): Tuple4[Int, Int, Int, Int] = {
    (red(pixelInt), green(pixelInt), blue(pixelInt), transparency(pixelInt))
  }

  /**
   *
   */
  private[images] def pixelInt(
    red: Int = MIN_RED,
    green: Int = MIN_GREEN,
    blue: Int = MIN_BLUE,
    transparency: Int = MIN_TRANSPARENCY) = {

    require(BYTE_RANGE.contains(red),
      s"The 'red' value must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $red)")

    require(BYTE_RANGE.contains(green),
      s"The 'green' value must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $green)")

    require(BYTE_RANGE.contains(blue),
      s"The 'blue' value must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $blue)")

    require(BYTE_RANGE.contains(transparency),
      s"The transparency value must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $transparency)")

    (transparency << THREE_BYTES) | (red << TWO_BYTES) | (green << ONE_BYTE) | blue
  }

}

/**
 * @author Aleksi Lukkarinen
 */
class BitmapImage(
    val widthInPixels: Int = BitmapImage.DEFAULT_WIDTH_IN_PIXELS,
    val heightInPixels: Int = BitmapImage.DEFAULT_HEIGHT_IN_PIXELS) {

  require(widthInPixels > 0, s"Width of the image must be greater than zero (was $widthInPixels)")
  require(heightInPixels > 0, s"Height of the image must be greater than zero (was $heightInPixels)")

  /**  */
  private val _widthRange = 0 to widthInPixels

  /**  */
  private val _heightRange = 0 to heightInPixels

  /** The underlying BufferedImage instance acting as a container for pixel data.  */
  private val _image: BufferedImage = new BufferedImage(
    widthInPixels, heightInPixels, BufferedImage.TYPE_INT_RGB)

  val pixels = new Pixels(this)

  /**
   *
   */
  def pixel(x: Int, y: Int): Tuple4[Int, Int, Int, Int] = {
    require(_widthRange.contains(x),
      s"The x coordinate must be >= zero and less than the width of the image (was $x)")

    require(_heightRange.contains(y),
      s"The y coordinate must be >= zero and less than the height of the image (was $y)")

    BitmapImage.pixelBytes(_image.getRGB(x, y))
  }

  /**
   *
   */
  def setPixel(x: Int, y: Int, red: Int, green: Int, blue: Int, transparency: Int) = {
    require(_widthRange.contains(x),
      s"The x coordinate must be >= zero and less than the width of the image (was $x)")

    require(_heightRange.contains(y),
      s"The y coordinate must be >= zero and less than the height of the image (was $y)")

    _image.setRGB(x, y, BitmapImage.pixelInt(red, green, blue, transparency))
  }

}
