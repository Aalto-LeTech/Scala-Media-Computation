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

  /** Color component value representing minimal amount of red. */
  val MinimumRed: Int = ByteRange.start

  /** Color component value representing maximal amount of red. */
  val MaximumRed: Int = ByteRange.end

  /** Color component value representing minimal amount of green. */
  val MinimumGreen: Int = ByteRange.start

  /** Color component value representing maximal amount of green. */
  val MaximumGreen: Int = ByteRange.end

  /** Color component value representing minimal amount of blue. */
  val MinimumBlue: Int = ByteRange.start

  /** Color component value representing maximal amount of blue. */
  val MaximumBlue: Int = ByteRange.end

  /** Color component value representing minimal amount of gray. */
  val MinimumGray: Int = ByteRange.start

  /** Color component value representing maximal amount of gray. */
  val MaximumGray: Int = ByteRange.end

  /** Color component value representing minimal opacity. */
  val MinimumOpacity: Int = ByteRange.start

  /** Color component value representing maximal opacity. */
  val MaximumOpacity: Int = ByteRange.end

  /** Color component value representing maximal opacity. */
  val FullyOpaque: Int = MaximumOpacity

  /** Color component value representing minimal opacity. */
  val FullyTransparent: Int = MinimumOpacity

  /** Number of degrees representing a full circle. */
  val FullCircleInDegrees: Int = 360

  /** Number of degrees representing a full circle. */
  val OneThirdOfCircleInDegrees: Int = FullCircleInDegrees / 3

  /** 60 degrees in radians. */
  val Deg60InRad = Math.PI / 3.0

  /** Color component value representing start of the hue cycle. */
  val MinimumHue: Double = 0.0

  /** Color component value representing end of the hue cycle. */
  val MaximumHue: Double = FullCircleInDegrees.toDouble

  /** Color component value representing minimal amount of saturation. */
  val MinimumSaturation: Double = 0.0

  /** Color component value representing maximal amount of saturation. */
  val MaximumSaturation: Double = 1.0

  /** Color component value representing minimal amount of intensity. */
  val MinimumIntensity: Double = ByteRange.start.toDouble

  /** Color component value representing maximal amount of intensity. */
  val MaximumIntensity: Double = ByteRange.end.toDouble

  /** Application of the RichRGBAColor class. */
  implicit def RGBAColorWrapper(self: RGBAColor): RichRGBAColor = new RichRGBAColor(self)

  /** Application of the RichPixelInt class. */
  implicit def PixelIntWrapper(self: Int): RichPixelInt = new RichPixelInt(self)

}
