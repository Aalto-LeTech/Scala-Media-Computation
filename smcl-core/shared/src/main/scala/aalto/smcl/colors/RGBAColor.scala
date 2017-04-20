/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package aalto.smcl.colors


import aalto.smcl.colors
import aalto.smcl.infrastructure.{ColorAdapter, _}




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
   *
   * @return
   */
  def apply(red: Int, green: Int, blue: Int, opacity: Int,
      nameOption: Option[String] = None): RGBAColor = {

    ColorValidator.validateRgbaColor(red, green, blue, opacity)

    val resultNameOption = ColorValidator.validateColorNameOption(nameOption)

    new RGBAColor(red, green, blue, opacity, resultNameOption)
  }

  /**
   *
   *
   * @param rgbaTuple
   * @param nameOption
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(rgbaTuple: (Int, Int, Int, Int), nameOption: Option[String]): RGBAColor =
  (RGBAColor(_: Int, _: Int, _: Int, _: Int, nameOption)).tupled.apply(rgbaTuple)

  /**
   *
   *
   * @param rgbaTuple
   *
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
   *
   * @return
   */
  def apply(gray: Int, opacity: Int, nameOption: Option[String]): RGBAColor =
    RGBAColor(gray, gray, gray, opacity, nameOption)

  /**
   *
   *
   * @param grayOpacityTuple
   * @param nameOption
   *
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
   *
   * @return
   */
  def apply(red: Int, green: Int, blue: Int, nameOption: Option[String]): RGBAColor =
    RGBAColor(red, green, blue, ColorValidator.MaximumRgbaOpacity, nameOption)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  def apply(red: Int, green: Int, blue: Int): RGBAColor =
    RGBAColor(red, green, blue, ColorValidator.MaximumRgbaOpacity)

  /**
   *
   *
   * @param rgbTuple
   * @param nameOption
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(rgbTuple: (Int, Int, Int), nameOption: Option[String]): RGBAColor =
  (RGBAColor(_: Int, _: Int, _: Int, nameOption)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param rgbTuple
   *
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
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(rgbTuple: (Int, Int, Int), opacity: Int, nameOption: Option[String]): RGBAColor =
  (RGBAColor(_: Int, _: Int, _: Int, opacity, nameOption)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param rgbTuple
   * @param opacity
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(rgbTuple: (Int, Int, Int), opacity: Int): RGBAColor =
  (RGBAColor(_: Int, _: Int, _: Int, opacity)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param gray
   * @param opacity
   *
   * @return
   */
  def apply(gray: Int, opacity: Int): RGBAColor =
    RGBAColor(gray, gray, gray, opacity)


  /**
   *
   *
   * @param grayOpacityTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(grayOpacityTuple: (Int, Int)): RGBAColor =
  (RGBAColor(_: Int, _: Int)).tupled.apply(grayOpacityTuple)

  /**
   *
   *
   * @param argbInt
   * @param nameOption
   *
   * @return
   */
  def apply(argbInt: Int, nameOption: Option[String]): RGBAColor =
    RGBAColor(rgbaTupleFrom(argbInt), nameOption)

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  def apply(argbInt: Int): RGBAColor =
    RGBAColor(rgbaTupleFrom(argbInt))

  /**
   *
   *
   * @param platformColor
   *
   * @return
   */
  private[smcl] def apply(platformColor: ColorAdapter): RGBAColor =
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
   *
   * @return
   */
  private[smcl] def apply(
      platformColor: ColorAdapter,
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
   * @param value
   *
   * @return
   */
  def fromHsv(hueInDegrees: Double, saturation: Double, value: Double): RGBAColor =
    fromHsv(hueInDegrees, saturation, value, ColorValidator.MaximumRgbaOpacity)

  /**
   *
   *
   * @param hsvTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHsv(hsvTuple: (Double, Double, Double)): RGBAColor =
  (fromHsv(_: Double, _: Double, _: Double, ColorValidator.MaximumRgbaOpacity)).tupled.apply(hsvTuple)

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param value
   *
   * @return
   */
  def fromHsv(
      hueInDegrees: Double,
      saturation: Double,
      value: Double,
      nameOption: Option[String]): RGBAColor =
    fromHsv(hueInDegrees, saturation, value, ColorValidator.MaximumRgbaOpacity, nameOption)

  /**
   *
   *
   * @param hsvTuple
   * @param nameOption
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHsv(hsvTuple: (Double, Double, Double), nameOption: Option[String]): RGBAColor =
  (fromHsv(_: Double, _: Double, _: Double, ColorValidator.MaximumRgbaOpacity, nameOption)).tupled.apply(hsvTuple)

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param value
   * @param opacity
   *
   * @return
   */
  def fromHsv(
      hueInDegrees: Double,
      saturation: Double,
      value: Double,
      opacity: Int): RGBAColor =
    RGBAColor(hsvToRgb(hueInDegrees, saturation, value), opacity)

  /**
   *
   *
   * @param hsvTuple
   * @param opacity
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHsv(hsvTuple: (Double, Double, Double), opacity: Int): RGBAColor =
  (fromHsv(_: Double, _: Double, _: Double, opacity)).tupled.apply(hsvTuple)

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param value
   * @param opacity
   *
   * @return
   */
  def fromHsv(
      hueInDegrees: Double,
      saturation: Double,
      value: Double,
      opacity: Int,
      nameOption: Option[String]): RGBAColor =
    RGBAColor(hsvToRgb(hueInDegrees, saturation, value), opacity, nameOption)

  /**
   *
   *
   * @param hsvTuple
   * @param opacity
   * @param nameOption
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHsv(
      hsvTuple: (Double, Double, Double),
      opacity: Int,
      nameOption: Option[String]): RGBAColor =
  (fromHsv(_: Double, _: Double, _: Double, opacity, nameOption)).tupled.apply(hsvTuple)

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   *
   * @return
   */
  def fromHsi(hueInDegrees: Double, saturation: Double, intensity: Double): RGBAColor =
    fromHsi(hueInDegrees, saturation, intensity, ColorValidator.MaximumRgbaOpacity)

  /**
   *
   *
   * @param hsiTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHsi(hsiTuple: (Double, Double, Double)): RGBAColor =
  (fromHsi(_: Double, _: Double, _: Double, ColorValidator.MaximumRgbaOpacity)).tupled.apply(hsiTuple)

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   *
   * @return
   */
  def fromHsi(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double,
      nameOption: Option[String]): RGBAColor =
    fromHsi(hueInDegrees, saturation, intensity, ColorValidator.MaximumRgbaOpacity, nameOption)

  /**
   *
   *
   * @param hsiTuple
   * @param nameOption
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHsi(hsiTuple: (Double, Double, Double), nameOption: Option[String]): RGBAColor =
  (fromHsi(_: Double, _: Double, _: Double, ColorValidator.MaximumRgbaOpacity, nameOption)).tupled.apply(hsiTuple)

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   * @param opacity
   *
   * @return
   */
  def fromHsi(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double,
      opacity: Int): RGBAColor =
    RGBAColor(hsiToRgb(hueInDegrees, saturation, intensity), opacity)

  /**
   *
   *
   * @param hsiTuple
   * @param opacity
   *
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
   *
   * @return
   */
  def fromHsi(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double,
      opacity: Int,
      nameOption: Option[String]): RGBAColor =
    RGBAColor(hsiToRgb(hueInDegrees, saturation, intensity), opacity, nameOption)

  /**
   *
   *
   * @param hsiTuple
   * @param opacity
   * @param nameOption
   *
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
  lazy val toArgbInt: Int = argbIntFrom(red, green, blue, opacity)

  /** Returns `true` if this [[RGBAColor]] is fully opaque, otherwise `false`. */
  val isOpaque: Boolean = opacity == ColorValidator.MaximumRgbaOpacity

  /** Returns `false` if this [[RGBAColor]] is fully opaque, otherwise `true`. */
  val isTransparent: Boolean = !isOpaque

  /** Returns `true` if this [[RGBAColor]] is created by the user, otherwise `false`. */
  val isUserCreated: Boolean = !isPreset

  /** This [[RGBAColor]] represented as a 32-digit binary string of four 8-digit groups. */
  lazy val toBinaryString: String = toArgbInt.toArgbBinaryColorString

  /** This [[RGBAColor]] represented as a hexadecimal string. */
  lazy val toHexString: String = toArgbInt.toArgbHexColorString

  /** Information about this [[aalto.smcl.bitmaps.operations.Renderable]] instance */
  lazy val metaInformation = MetaInformationMap("RGBAColor", Map(
    "red" -> Option(red.toString),
    "green" -> Option(green.toString),
    "blue" -> Option(blue.toString),
    "opacity" -> Option(opacity.toString)))

  /** */
  lazy val toColorComponentMap: Map[Symbol, Double] = colorComponentMapFrom(this)

  /** */
  lazy val toNormalizedRgbaComponents: (Double, Double, Double, Double) =
    normalizeRgba(red, green, blue, opacity)

  /** */
  lazy val toGray: RGBAColor = RGBAColor(toHsiIntensity.toInt, FullyOpaque)

  /** */
  lazy val toHsi: (Double, Double, Double) = colors.toHsi(this)

  /** */
  lazy val toHsiHueInDegrees: Double = toHsi._1

  /** */
  lazy val toHsiSaturation: Double = toHsi._2

  /** */
  lazy val toHsiIntensity: Double = toHsi._3

  /** */
  lazy val toHsv: (Double, Double, Double) = colors.toHsv(this)

  /** */
  lazy val toHsvHueInDegrees: Double = toHsv._1

  /** */
  lazy val toHsvSaturation: Double = toHsv._2

  /** */
  lazy val toHsvValue: Double = toHsv._3

  /** */
  lazy val isBlack: Boolean = colors.isBlack(this)

  /** */
  lazy val isWhite: Boolean = colors.isWhite(this)

  /** */
  lazy val isGray: Boolean = colors.isGray(this)

  /** */
  lazy val keepingOnlyRedComponent: RGBAColor =
    RGBAColor(red, ColorValidator.MinimumRgbGreen, ColorValidator.MinimumRgbBlue, FullyOpaque)

  /** */
  lazy val keepingOnlyGreenComponent: RGBAColor =
    RGBAColor(ColorValidator.MinimumRgbRed, green, ColorValidator.MinimumRgbBlue, FullyOpaque)

  /** */
  lazy val keepingOnlyBlueComponent: RGBAColor =
    RGBAColor(ColorValidator.MinimumRgbRed, ColorValidator.MinimumRgbGreen, blue, FullyOpaque)

  /** */
  lazy val keepingOnlyRedAndGreenComponents: RGBAColor =
    RGBAColor(red, green, ColorValidator.MinimumRgbBlue, FullyOpaque)

  /** */
  lazy val keepingOnlyRedAndBlueComponents: RGBAColor =
    RGBAColor(red, ColorValidator.MinimumRgbGreen, blue, FullyOpaque)

  /** */
  lazy val keepingOnlyGreenAndBlueComponents: RGBAColor =
    RGBAColor(ColorValidator.MinimumRgbRed, green, blue, FullyOpaque)

  /** */
  lazy val representingOpacityAsGreyLevel: RGBAColor =
    RGBAColor(opacity, opacity, opacity, FullyOpaque)

  /**
   *
   *
   * @param that
   *
   * @return
   */
  override def compare(that: RGBAColor): Int =
    Math.signum(that.toHsiHueInDegrees - this.toHsiHueInDegrees).toInt

  /**
   *
   *
   * @param that
   *
   * @return
   */
  def compareByHsiSaturation(that: RGBAColor): Int =
    Math.signum(that.toHsiSaturation - this.toHsiSaturation).toInt

  /**
   *
   *
   * @param that
   *
   * @return
   */
  def compareByHsiIntensity(that: RGBAColor): Int =
    Math.signum(that.toHsiIntensity - this.toHsiIntensity).toInt

  /**
   * Returns a string representation of this [[RGBAColor]].
   */
  override def toString: String =
    s"ARGB: 0x$toHexString -- $opacity - $red - $green - $blue"

}
