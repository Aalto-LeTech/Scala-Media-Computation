package aalto.smcl


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

  /** Color component value representing minimal transparency. */
  val MinimumOpaqueness: Int = ByteRange.start

  /** Color component value representing maximal transparency. */
  val MaximumOpaqueness: Int = ByteRange.end

  /** Color component value representing minimal transparency. */
  val FullyOpaque: Int = MaximumOpaqueness

  /** Color component value representing maximal transparency. */
  val FullyTransparent: Int = MinimumOpaqueness


  /**
   *
   *
   * @param self
   */
  implicit class RichRGBAColor(val self: RGBAColor) {

    /**
     * Returns a new [[RGBAColor]] identical with this one except having full opaqueness.
     */
    def toOpaqueColor: RGBAColor =
      if (self.isOpaque) self
      else RGBAColor(self.red, self.green, self.blue, MaximumOpaqueness, self.nameOption)

    /**
     *
     *
     * @param shadingFactorInPercents
     */
    def shadeBy(shadingFactorInPercents: Double): RGBAColor = {
      require(shadingFactorInPercents >= 0.0 && shadingFactorInPercents <= 100.0,
        s"The shading factor must be a Double between 0.0 and 100.0 (was $shadingFactorInPercents)")

      val invertedFactor: Double = 1.0 - (shadingFactorInPercents / 100)
      val newRed = (invertedFactor * self.red).toInt
      val newGreen = (invertedFactor * self.green).toInt
      val newBlue = (invertedFactor * self.blue).toInt

      RGBAColor(newRed, newGreen, newBlue, self.transparency)
    }

    /**
     *
     *
     * @param tintingFactorInPercents
     */
    def tintBy(tintingFactorInPercents: Double): RGBAColor = {
      require(tintingFactorInPercents >= 0.0 && tintingFactorInPercents <= 100.0,
        s"The tinting factor must be a Double between 0.0 and 100.0 (was $tintingFactorInPercents)")

      val scaledFactor: Double = tintingFactorInPercents / 100.0
      val newRed = (self.red + scaledFactor * (MaximumRed - self.red)).toInt
      val newGreen = (self.green + scaledFactor * (MaximumGreen - self.green)).toInt
      val newBlue = (self.blue + scaledFactor * (MaximumBlue - self.blue)).toInt

      RGBAColor(newRed, newGreen, newBlue, self.transparency)
    }

  }


}
