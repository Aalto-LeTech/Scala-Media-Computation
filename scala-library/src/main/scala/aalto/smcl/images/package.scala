package aalto.smcl

import aalto.smcl.common._
import aalto.smcl.images.immutable.Bitmap

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object images {

  /** Number of bits in one byte. */
  private[images] val ONE_BYTE = 8

  /** Number of bits in two bytes. */
  private[images] val TWO_BYTES = ONE_BYTE + ONE_BYTE

  /** Number of bits in three bytes. */
  private[images] val THREE_BYTES = TWO_BYTES + ONE_BYTE

  /** Bits belonging to the rightmost byte of an `Int`. */
  private[images] val FIRST_BYTE = 0xFF

  /** Bits belonging to the second-rightmost byte of an `Int`. */
  private[images] val SECOND_BYTE = FIRST_BYTE << ONE_BYTE

  /** Bits belonging to the second-leftmost byte of an `Int`. */
  private[images] val THIRD_BYTE = SECOND_BYTE << ONE_BYTE

  /** Bits belonging to the leftmost byte of an `Int`. */
  private[images] val FOURTH_BYTE = THIRD_BYTE << ONE_BYTE

  /** The value range that a single unsigned byte can represent. */
  private[images] val BYTE_RANGE = 0 to 255

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
  val MIN_OPAQUENESS: Int = BYTE_RANGE.start

  /** Color component value representing maximal transparency. */
  val MAX_OPAQUENESS: Int = BYTE_RANGE.end

  /** Color component value representing minimal transparency. */
  val FULLY_OPAQUE: Int = MAX_OPAQUENESS

  /** Color component value representing maximal transparency. */
  val FULLY_TRANSPARENT: Int = MIN_OPAQUENESS

  /** Default width of [[BitmapImage]] instances created without giving width. */
  val DEFAULT_IMAGE_WIDTH_IN_PIXELS: Int = 10

  /** Default height of [[BitmapImage]] instances created without giving height. */
  val DEFAULT_IMAGE_HEIGHT_IN_PIXELS: Int = 10

  /**
   *
   */
  def redComponentFrom(pixelInt: Int): Int = (pixelInt & THIRD_BYTE) >>> TWO_BYTES

  /**
   *
   */
  def withNewRedComponent(pixelInt: Int, newRed: Int): Int = {
    require(BYTE_RANGE.contains(newRed),
      s"'newRed' must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $newRed)")

    (pixelInt & ~THIRD_BYTE) | (newRed << TWO_BYTES)
  }

  /**
   *
   */
  def greenComponentFrom(pixelInt: Int): Int = (pixelInt & SECOND_BYTE) >>> ONE_BYTE

  /**
   *
   */
  def withNewGreenComponent(pixelInt: Int, newGreen: Int): Int = {
    require(BYTE_RANGE.contains(newGreen),
      s"'newGreen' must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $newGreen)")

    (pixelInt & ~SECOND_BYTE) | (newGreen << ONE_BYTE)
  }

  /**
   *
   */
  def blueComponentFrom(pixelInt: Int): Int = pixelInt & FIRST_BYTE

  /**
   *
   */
  def withNewBlueComponent(pixelInt: Int, newBlue: Int): Int = {
    require(BYTE_RANGE.contains(newBlue),
      s"'newBlue' must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $newBlue)")

    (pixelInt & ~FIRST_BYTE) | newBlue
  }

  /**
   *
   */
  def transparencyComponentFrom(pixelInt: Int): Int = pixelInt >>> THREE_BYTES

  /**
   *
   */
  def withNewTransparencyComponent(pixelInt: Int, newTransparency: Int): Int = {
    require(BYTE_RANGE.contains(newTransparency),
      s"'newTransparency' must be between ${BYTE_RANGE.start} and ${BYTE_RANGE.end} (was $newTransparency)")

    (pixelInt & ~FOURTH_BYTE) | (newTransparency << THREE_BYTES)
  }

  /**
   *
   */
  def colorComponentsFrom(pixelInt: Int): collection.immutable.Map[Symbol, Int] = {
    collection.immutable.Map[Symbol, Int](
      'red -> redComponentFrom(pixelInt),
      'green -> greenComponentFrom(pixelInt),
      'blue -> blueComponentFrom(pixelInt),
      'transparency -> transparencyComponentFrom(pixelInt))
  }

  /**
   *
   */
  def pixelIntFrom(
    red: Int = MIN_RED,
    green: Int = MIN_GREEN,
    blue: Int = MIN_BLUE,
    transparency: Int = FULLY_OPAQUE): Int = {

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

  /**
   * Some methods for composing ARGB-style `Int` values as well as
   * extracting the individual color components from them.
   */
  implicit class PixelInt(val self: Int) extends AnyVal {

    /**
     * Returns an immutable map containing individual color components of this ARGB-style `Int`.
     * The keys in the map are `'red`, `'green`, `'blue`, and `'transparency`.
     *
     * {{{
     * scala> 0x89ABCDEF.colorComponentInts
     * res0: Map[Symbol,Int] = Map('red -> 171, 'green -> 205, 'blue -> 239, 'transparency -> 137) // 0x89 = 137 etc.
     * }}}
     */
    def colorComponentInts: collection.immutable.Map[Symbol, Int] = colorComponentsFrom(self)

    /**
     * Returns the red color component of this ARGB-style `Int`.
     */
    def redComponentInt: Int = redComponentFrom(self)

    /**
     * Returns the green color component of this ARGB-style `Int`.
     */
    def greenComponentInt: Int = greenComponentFrom(self)

    /**
     * Returns the blue color component of this ARGB-style `Int`.
     */
    def blueComponentInt: Int = blueComponentFrom(self)

    /**
     * Returns the transparency component of this ARGB-style `Int`.
     */
    def transparencyComponentInt: Int = transparencyComponentFrom(self)

    /**
     * Displays this `Int` as a zero-padded hexadecimal form.
     *
     * {{{
     * scala> -1985229329.toArgbHexColorString
     * res0: String = 89abcdef
     * }}}
     */
    def toArgbHexColorString: String = f"${self}%08x"

    /**
     * Displays this `Int` as a zero-padded binary form divided to bytes by spaces.
     *
     * {{{
     * scala> 0x89abcdef.toArgbBinaryColorString
     * res0: String = 10001001 10101011 11001101 11101111
     * }}}
     */
    def toArgbBinaryColorString: String = {
      val withoutGroups = f"${self.toBinaryString}%32s".replace(STR_SPACE, STR_ZERO)
      val grouped = withoutGroups.sliding(ONE_BYTE, ONE_BYTE).mkString(STR_SPACE)

      return grouped
    }

  }

  /**
   * A string interpolator for creating [[BitmapImage]] instances.
   *
   *
   *
   */
  implicit class BitmapCreationStringInterpolator(val sc: StringContext) extends AnyVal {
    def bmp(args: Any*): Bitmap = {
      val s = sc.standardInterpolator(StringContext.processEscapes, args)

      // TODO: Replace with real functionality when it is available
      Bitmap()
    }
  }

}
