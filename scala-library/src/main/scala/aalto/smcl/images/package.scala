package aalto.smcl

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

  /** Bits belonging to the rightmost byte of an <code>Int</code>. */
  private[images] val FIRST_BYTE = 0xFF

  /** Bits belonging to the second-rightmost byte of an <code>Int</code>. */
  private[images] val SECOND_BYTE = FIRST_BYTE << ONE_BYTE

  /** Bits belonging to the second-leftmost byte of an <code>Int</code>. */
  private[images] val THIRD_BYTE = SECOND_BYTE << ONE_BYTE

  /** Bits belonging to the leftmost byte of an <code>Int</code>. */
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
  val MIN_TRANSPARENCY: Int = BYTE_RANGE.start

  /** Color component value representing maximal transparency. */
  val MAX_TRANSPARENCY: Int = BYTE_RANGE.end

  /** Color component value representing minimal transparency. */
  val FULLY_OPAQUE: Int = MIN_TRANSPARENCY

  /** Color component value representing maximal transparency. */
  val FULLY_TRANSPARENT: Int = MAX_TRANSPARENCY

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
  def colorComponentsFrom(pixelInt: Int): Tuple4[Int, Int, Int, Int] = {
    (redComponentFrom(pixelInt),
      greenComponentFrom(pixelInt),
      blueComponentFrom(pixelInt),
      transparencyComponentFrom(pixelInt))
  }

  /**
   *
   */
  def pixelIntFrom(
    red: Int = MIN_RED,
    green: Int = MIN_GREEN,
    blue: Int = MIN_BLUE,
    transparency: Int = MIN_TRANSPARENCY): Int = {

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
