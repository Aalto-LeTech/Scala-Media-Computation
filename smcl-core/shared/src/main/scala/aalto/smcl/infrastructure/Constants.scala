package aalto.smcl.infrastructure


import scala.collection.immutable.Range.Inclusive




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait Constants {

  /** An empty string. */
  protected[smcl] val StrEmpty: String = ""

  /** A string containing a single space. */
  protected[smcl] val StrSpace: String = " "

  /** A string containing a single period. */
  protected[smcl] val StrPeriod: String  = "."

  /** A string containing a single comma. */
  protected[smcl] val StrComma: String  = ","

  /** A string containing a single colon. */
  protected[smcl] val StrColon: String  = ":"

  /** A string containing a single colon. */
  protected[smcl] val StrColonAsUnicode: String  = "\u003A"

  /** A string containing a single semicolon. */
  protected[smcl] val StrSemicolon: String  = ";"

  /** A string containing a single semicolon. */
  protected[smcl] val StrSemicolonAsUnicode: String  = "\u003B"

  /** A string containing a single left angle bracket. */
  protected[smcl] val StrLeftAngleBracket: String  = "["

  /** A string containing a single right angle bracket. */
  protected[smcl] val StrRightAngleBracket: String  = "]"

  /** A string containing a single zero. */
  protected[smcl] val StrZero: String  = "0"

  /** Number of bits in one byte. */
  protected[smcl] val OneByte: Int = 8

  /** Number of bits in two bytes. */
  protected[smcl] val TwoBytes: Int = OneByte + OneByte

  /** Number of bits in three bytes. */
  protected[smcl] val ThreeBytes: Int = TwoBytes + OneByte

  /** Bits belonging to the rightmost byte of an `Int`. */
  protected[smcl] val FirstByte: Int = 0xFF

  /** Bits belonging to the second-rightmost byte of an `Int`. */
  protected[smcl] val SecondByte: Int = FirstByte << OneByte

  /** Bits belonging to the second-leftmost byte of an `Int`. */
  protected[smcl] val ThirdByte: Int = SecondByte << OneByte

  /** Bits belonging to the leftmost byte of an `Int`. */
  protected[smcl] val FourthByte: Int = ThirdByte << OneByte

  /** The value range that a single unsigned byte can represent. */
  protected[smcl] val ByteRange: Inclusive = 0 to 255

  /** 2 * PI */
  protected[smcl] val PI2: Double = 2 * Math.PI

  /** Number of degrees representing a full circle. */
  val FullCircleInDegrees: Int = 360

  /** Number of degrees representing a full circle. */
  val OneThirdOfCircleInDegrees: Int = FullCircleInDegrees / 3

  /** Number of degrees representing a half of a circle. */
  val OneHalfOfCircleInDegrees: Int = 180

  /** Number of degrees representing a quarter of a circle in the clockwise direction. */
  val OneQuarterOfCircleInDegreesClockwise: Int = -90

  /** Number of degrees representing a quarter of a circle in the counter-clockwise direction. */
  val OneQuarterOfCircleInDegreesCounterClockwise: Int = 90

  /** 60 degrees in radians. */
  val Deg60InRad: Double = Math.PI / 3.0

}
