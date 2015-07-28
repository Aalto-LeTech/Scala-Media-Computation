package aalto.smcl


import java.awt.{Color => AwtColor}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object common {

  /** An empty string. */
  protected[smcl] val STR_EMPTY = ""

  /** A string containing a single space. */
  protected[smcl] val STR_SPACE = " "

  /** A string containing a single colon. */
  protected[smcl] val STR_COLON = ":"

  /** A string containing a single colon. */
  protected[smcl] val STR_COLON_AS_UNICODE = "\u003A"

  /** A string containing a single semicolon. */
  protected[smcl] val STR_SEMICOLON = ";"

  /** A string containing a single semicolon. */
  protected[smcl] val STR_SEMICOLON_AS_UNICODE = "\u003B"

  /** A string containing a single left angle bracket. */
  protected[smcl] val STR_LEFT_ANGLE_BRACKET = "["

  /** A string containing a single right angle bracket. */
  protected[smcl] val STR_RIGHT_ANGLE_BRACKET = "]"

  /** A string containing a single zero. */
  protected[smcl] val STR_ZERO = "0"

  /** Number of bits in one byte. */
  protected[smcl] val ONE_BYTE = 8

  /** Number of bits in two bytes. */
  protected[smcl] val TWO_BYTES = ONE_BYTE + ONE_BYTE

  /** Number of bits in three bytes. */
  protected[smcl] val THREE_BYTES = TWO_BYTES + ONE_BYTE

  /** Bits belonging to the rightmost byte of an `Int`. */
  protected[smcl] val FIRST_BYTE = 0xFF

  /** Bits belonging to the second-rightmost byte of an `Int`. */
  protected[smcl] val SECOND_BYTE = FIRST_BYTE << ONE_BYTE

  /** Bits belonging to the second-leftmost byte of an `Int`. */
  protected[smcl] val THIRD_BYTE = SECOND_BYTE << ONE_BYTE

  /** Bits belonging to the leftmost byte of an `Int`. */
  protected[smcl] val FOURTH_BYTE = THIRD_BYTE << ONE_BYTE

  /** The value range that a single unsigned byte can represent. */
  protected[smcl] val BYTE_RANGE = 0 to 255

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


  /**
   *
   *
   * @param self
   */
  implicit class RichColor(val self: Color) {

    /** This [[Color]] with full opaqueness. */
    def toOpaqueColor: Color =
      if (self.isOpaque) self
      else Color(self.red, self.green, self.blue, MAX_OPAQUENESS, self.nameOption)

    /** This [[Color]] as a `java.awt.Color` with full opaqueness. */
    def toOpaqueAwtColor: AwtColor =
      new AwtColor(self.red, self.green, self.blue, MAX_OPAQUENESS)

  }

}
