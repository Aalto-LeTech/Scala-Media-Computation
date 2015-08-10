package aalto.smcl.common


import aalto.smcl.bitmaps.operations.AbstractSingleSourceOperation
import aalto.smcl.common.ColorOps._
import aalto.smcl.platform.PlatformColor




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object RGBAColor {

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param opacity
   * @param nameOption
   * @return
   */
  private[smcl] def validateColorArguments(red: Int, green: Int, blue: Int,
      opacity: Int, nameOption: Option[String] = None): Tuple5[Int, Int, Int, Int, Option[String]] = {

    require(ByteRange.contains(red),
      s"The 'red' value must be between ${ByteRange.start} and ${ByteRange.end} (was $red)")

    require(ByteRange.contains(green),
      s"The 'green' value must be between ${ByteRange.start} and ${ByteRange.end} (was $green)")

    require(ByteRange.contains(blue),
      s"The 'blue' value must be between ${ByteRange.start} and ${ByteRange.end} (was $blue)")

    require(ByteRange.contains(opacity),
      s"The opacity value must be between ${ByteRange.start} and ${ByteRange.end} (was $opacity)")

    require(nameOption != null, "The nameOption argument must be Option(<name>) or None (was null).")

    var resultNameOption = nameOption
    if (nameOption.nonEmpty) {
      val name = nameOption.get.trim

      require(name != StrEmpty, "The name cannot be empty or contain only whitespace.")

      if (name != nameOption.get)
        resultNameOption = Option(name)
    }

    (red, green, blue, opacity, resultNameOption)
  }


  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param opacity
   * @param nameOption
   * @return
   */
  def apply(red: Int, green: Int, blue: Int, opacity: Int,
      nameOption: Option[String] = None): RGBAColor = {

    val (validRed, validGreen, validBlue, validOpacity, validNameOption) =
      validateColorArguments(red, green, blue, opacity, nameOption)

    new RGBAColor(validRed, validGreen, validBlue, validOpacity, validNameOption)
  }

  /**
   *
   *
   * @param rgbaTuple
   * @param nameOption
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(rgbaTuple: (Int, Int, Int, Int), nameOption: Option[String]): RGBAColor =
    (RGBAColor(_: Int, _: Int, _: Int, _: Int, nameOption)).tupled.apply(rgbaTuple)

  /**
   *
   *
   * @param rgbaTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(rgbaTuple: (Int, Int, Int, Int)): RGBAColor =
    (RGBAColor(_: Int, _: Int, _: Int, _: Int)).tupled.apply(rgbaTuple)

  /**
   *
   *
   * @param gray
   * @param opacity
   * @param nameOption
   * @return
   */
  def apply(gray: Int, opacity: Int, nameOption: Option[String]): RGBAColor = {
    val (validRed, validGreen, validBlue, validOpacity, validNameOption) =
      validateColorArguments(gray, gray, gray, opacity, nameOption)

    new RGBAColor(validRed, validGreen, validBlue, validOpacity, validNameOption)
  }

  /**
   *
   *
   * @param grayOpacityTuple
   * @param nameOption
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(grayOpacityTuple: (Int, Int), nameOption: Option[String]): RGBAColor =
    (RGBAColor(_: Int, _: Int, nameOption)).tupled.apply(grayOpacityTuple)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param nameOption
   * @return
   */
  def apply(red: Int, green: Int, blue: Int, nameOption: Option[String]): RGBAColor =
    RGBAColor(red, green, blue, MaximumOpacity, nameOption)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @return
   */
  def apply(red: Int, green: Int, blue: Int): RGBAColor =
    RGBAColor(red, green, blue, MaximumOpacity)

  /**
   *
   *
   * @param rgbTuple
   * @param nameOption
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(rgbTuple: (Int, Int, Int), nameOption: Option[String]): RGBAColor =
    (RGBAColor(_: Int, _: Int, _: Int, nameOption)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param rgbTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(rgbTuple: (Int, Int, Int)): RGBAColor =
    (RGBAColor(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param rgbTuple
   * @param opacity
   * @param nameOption
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(rgbTuple: (Int, Int, Int), opacity:Int, nameOption: Option[String]): RGBAColor =
    (RGBAColor(_: Int, _: Int, _: Int, opacity, nameOption)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param rgbTuple
   * @param opacity
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(rgbTuple: (Int, Int, Int), opacity:Int): RGBAColor =
    (RGBAColor(_: Int, _: Int, _: Int, opacity)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param gray
   * @param opacity
   * @return
   */
  def apply(gray: Int, opacity: Int): RGBAColor = {
    val (validRed, validGreen, validBlue, validOpacity, validNameOption) =
      validateColorArguments(gray, gray, gray, opacity)

    new RGBAColor(validRed, validGreen, validBlue, validOpacity, validNameOption)
  }

  /**
   *
   *
   * @param grayOpacityTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(grayOpacityTuple: (Int, Int)): RGBAColor =
    (RGBAColor(_: Int, _: Int)).tupled.apply(grayOpacityTuple)

  /**
   *
   *
   * @param pixelInt
   * @param nameOption
   * @return
   */
  def apply(pixelInt: Int, nameOption: Option[String]): RGBAColor =
    RGBAColor(ColorOps.rgbaTupleFrom(pixelInt), nameOption)

  /**
   *
   *
   * @param pixelInt
   * @return
   */
  def apply(pixelInt: Int): RGBAColor =
    RGBAColor(ColorOps.rgbaTupleFrom(pixelInt))

  /**
   *
   *
   * @param platformColor
   * @return
   */
  private[smcl] def apply(platformColor: PlatformColor): RGBAColor =
    RGBAColor(
      platformColor.red,
      platformColor.green,
      platformColor.blue,
      platformColor.opacity)

  /**
   *
   *
   * @param platformColor
   * @param nameOption
   * @return
   */
  private[smcl] def apply(
      platformColor: PlatformColor,
      nameOption: Option[String]): RGBAColor =
    RGBAColor(
      platformColor.red,
      platformColor.green,
      platformColor.blue,
      platformColor.opacity,
      nameOption)

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   * @return
   */
  def fromHsi(hueInDegrees: Double, saturation: Double, intensity: Double): RGBAColor =
    fromHsi(hueInDegrees, saturation, intensity, MaximumOpacity)

  /**
   *
   *
   * @param hsiTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHsi(hsiTuple: (Double, Double, Double)): RGBAColor =
    (fromHsi(_: Double, _: Double, _: Double, MaximumOpacity)).tupled.apply(hsiTuple)

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   * @return
   */
  def fromHsi(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double,
      nameOption: Option[String]): RGBAColor =
    fromHsi(hueInDegrees, saturation, intensity, MaximumOpacity, nameOption)

  /**
   *
   *
   * @param hsiTuple
   * @param nameOption
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHsi(hsiTuple: (Double, Double, Double), nameOption: Option[String]): RGBAColor =
    (fromHsi(_: Double, _: Double, _: Double, MaximumOpacity, nameOption)).tupled.apply(hsiTuple)

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   * @param opacity
   * @return
   */
  def fromHsi(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double,
      opacity: Int): RGBAColor =
    RGBAColor(ColorOps.hsiToRgb(hueInDegrees, saturation, intensity))

  /**
   *
   *
   * @param hsiTuple
   * @param opacity
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHsi(hsiTuple: (Double, Double, Double), opacity: Int): RGBAColor =
    (fromHsi(_: Double, _: Double, _: Double, opacity)).tupled.apply(hsiTuple)

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   * @param opacity
   * @return
   */
  def fromHsi(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double,
      opacity: Int,
      nameOption: Option[String]): RGBAColor =
    RGBAColor(ColorOps.hsiToRgb(hueInDegrees, saturation, intensity), opacity, nameOption)

  /**
   *
   *
   * @param hsiTuple
   * @param opacity
   * @param nameOption
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHsi(
      hsiTuple: (Double, Double, Double),
      opacity: Int,
      nameOption: Option[String]): RGBAColor =
    (fromHsi(_: Double, _: Double, _: Double, opacity, nameOption)).tupled.apply(hsiTuple)

}


/**
 *
 *
 * @param red
 * @param green
 * @param blue
 * @param opacity
 * @param nameOption
 *
 * @author Aleksi Lukkarinen
 */
class RGBAColor protected(
    val red: Int,
    val green: Int,
    val blue: Int,
    val opacity: Int,
    val nameOption: Option[String] = None) extends {

  /** Returns `true` if this [[RGBAColor]] is provided by SMCL, otherwise `false`. */
  val isPreset: Boolean = false

} with Ordered[RGBAColor] with Immutable with Tokenizable {

  /** This [[RGBAColor]] coded into an `Int`. */
  lazy val toPixelInt: Int = pixelIntFrom(red, green, blue, opacity)

  /** Returns `true` if this [[RGBAColor]] is fully opaque, otherwise `false`. */
  val isOpaque: Boolean = opacity == MaximumOpacity

  /** Returns `false` if this [[RGBAColor]] is fully opaque, otherwise `true`. */
  val isTransparent: Boolean = !isOpaque

  /** Returns `true` if this [[RGBAColor]] is created by the user, otherwise `false`. */
  val isUserCreated: Boolean = !isPreset

  /** This [[RGBAColor]] represented as a 32-digit binary string of four 8-digit groups. */
  lazy val toBinaryString: String = toPixelInt.toArgbBinaryColorString

  /** This [[RGBAColor]] represented as a hexadecimal string. */
  lazy val toHexString: String = toPixelInt.toArgbHexColorString

  /** Information about this [[AbstractSingleSourceOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "red" -> Option(red.toString),
    "green" -> Option(green.toString),
    "blue" -> Option(blue.toString),
    "opacity" -> Option(opacity.toString)))

  /** */
  lazy val toColorComponentMap = ColorOps.colorComponentMapFrom(this)

  /** */
  lazy val toHueInDegrees: Double = ColorOps.hueInDegreesOf(this)

  /** */
  lazy val toSaturation: Double = ColorOps.saturationOf(this)

  /** */
  lazy val toIntensity: Double = ColorOps.intensityOf(this)

  /** */
  lazy val toGray: RGBAColor = RGBAColor(toIntensity.toInt, FullyOpaque)

  /** */
  lazy val toHsi: (Double, Double, Double) = ColorOps.toHsi(this)

  /** */
  lazy val isBlack: Boolean = ColorOps.isBlack(this)

  /** */
  lazy val isWhite: Boolean = ColorOps.isWhite(this)

  /** */
  lazy val keepingOnlyRedComponent: RGBAColor =
    RGBAColor(red, MinimumGreen, MinimumBlue, FullyOpaque)

  /** */
  lazy val keepingOnlyGreenComponent: RGBAColor =
    RGBAColor(MinimumRed, green, MinimumBlue, FullyOpaque)

  /** */
  lazy val keepingOnlyBlueComponent: RGBAColor =
    RGBAColor(MinimumRed, MinimumGreen, blue, FullyOpaque)

  /** */
  lazy val keepingOnlyRedAndGreenComponents: RGBAColor =
    RGBAColor(red, green, MinimumBlue, FullyOpaque)

  /** */
  lazy val keepingOnlyRedAndBlueComponents: RGBAColor =
    RGBAColor(red, MinimumGreen, blue, FullyOpaque)

  /** */
  lazy val keepingOnlyGreenAndBlueComponents: RGBAColor =
    RGBAColor(MinimumRed, green, blue, FullyOpaque)

  /** */
  lazy val representingOpacityAsGreyLevel: RGBAColor =
    RGBAColor(opacity, opacity, opacity, FullyOpaque)

  /**
   *
   *
   * @param that
   * @return
   */
  override def compare(that: RGBAColor): Int =
    Math.signum(that.toHueInDegrees - this.toHueInDegrees).toInt

  /**
   *
   *
   * @param that
   * @return
   */
  def compareBySaturation(that: RGBAColor): Int =
    Math.signum(that.toSaturation - this.toSaturation).toInt

  /**
   *
   *
   * @param that
   * @return
   */
  def compareByIntensity(that: RGBAColor): Int =
    Math.signum(that.toIntensity - this.toIntensity).toInt

  /**
   * Returns a string representation of this [[RGBAColor]].
   */
  override def toString: String =
    s"ARGB: 0x$toHexString -- $opacity - $red - $green - $blue"

}
