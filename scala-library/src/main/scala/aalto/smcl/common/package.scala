package aalto.smcl


import scala.language.implicitConversions




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object common {

  SMCL.performInitialization()


  /** An empty string. */
  protected[smcl] val StrEmpty = ""

  /** A string containing a single space. */
  protected[smcl] val StrSpace = " "

  /** A string containing a single period. */
  protected[smcl] val StrPeriod = "."

  /** A string containing a single comma. */
  protected[smcl] val StrComma = ","

  /** A string containing a single colon. */
  protected[smcl] val StrColon = ":"

  /** A string containing a single colon. */
  protected[smcl] val StrColonAsUnicode = "\u003A"

  /** A string containing a single semicolon. */
  protected[smcl] val StrSemicolon = ";"

  /** A string containing a single semicolon. */
  protected[smcl] val StrSemicolonAsUnicode = "\u003B"

  /** A string containing a single left angle bracket. */
  protected[smcl] val StrLeftAngleBracket = "["

  /** A string containing a single right angle bracket. */
  protected[smcl] val StrRightAngleBracket = "]"

  /** A string containing a single zero. */
  protected[smcl] val StrZero = "0"

  /** Number of bits in one byte. */
  protected[smcl] val OneByte = 8

  /** Number of bits in two bytes. */
  protected[smcl] val TwoBytes = OneByte + OneByte

  /** Number of bits in three bytes. */
  protected[smcl] val ThreeBytes = TwoBytes + OneByte

  /** Bits belonging to the rightmost byte of an `Int`. */
  protected[smcl] val FirstByte = 0xFF

  /** Bits belonging to the second-rightmost byte of an `Int`. */
  protected[smcl] val SecondByte = FirstByte << OneByte

  /** Bits belonging to the second-leftmost byte of an `Int`. */
  protected[smcl] val ThirdByte = SecondByte << OneByte

  /** Bits belonging to the leftmost byte of an `Int`. */
  protected[smcl] val FourthByte = ThirdByte << OneByte

  /** The value range that a single unsigned byte can represent. */
  protected[smcl] val ByteRange = 0 to 255

  /** 2 * PI */
  protected[smcl] val PI2 = 2 * Math.PI


  /** Color component value representing maximal opacity. */
  val FullyOpaque: Int = ColorValidator.MaximumRgbaOpacity

  /** Color component value representing minimal opacity. */
  val FullyTransparent: Int = ColorValidator.MinimumRgbaOpacity

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
  val Deg60InRad = Math.PI / 3.0

  /** Application of the RichRGBAColor class. */
  implicit def RGBAColorWrapper(self: RGBAColor): RichRGBAColor = new RichRGBAColor(self)

  /** Application of the RichArgbInt class. */
  implicit def ARGBIntWrapper(self: Int): RichARGBInt = new RichARGBInt(self)

}
