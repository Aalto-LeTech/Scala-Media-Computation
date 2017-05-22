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
import aalto.smcl.infrastructure.exceptions.ImplementationNotSetError
import aalto.smcl.infrastructure.{ColorAdapter, Describable, StringUtils, _}




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
   * @param name
   *
   * @return
   */
  def apply(
      red: Int, green: Int, blue: Int, opacity: Int,
      name: Option[String] = None): RGBAColor = {

    colorValidator.validateRGBAColor(red, green, blue, opacity)

    val resultName = colorValidator.validateColorName(name)

    new RGBAColor(red, green, blue, opacity, resultName)
  }

  /**
   *
   *
   * @param rgbaTuple
   * @param name
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(
      rgbaTuple: (Int, Int, Int, Int),
      name: Option[String]): RGBAColor = {

    (RGBAColor(_: Int, _: Int, _: Int, _: Int, name))
        .tupled.apply(rgbaTuple)
  }

  /**
   *
   *
   * @param rgbaTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(rgbaTuple: (Int, Int, Int, Int)): RGBAColor = {
    (RGBAColor(_: Int, _: Int, _: Int, _: Int)).tupled.apply(rgbaTuple)
  }

  /**
   *
   *
   * @param gray
   * @param opacity
   * @param name
   *
   * @return
   */
  def apply(gray: Int, opacity: Int, name: Option[String]): RGBAColor = {
    RGBAColor(gray, gray, gray, opacity, name)
  }

  /**
   *
   *
   * @param grayOpacityTuple
   * @param name
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(grayOpacityTuple: (Int, Int), name: Option[String]): RGBAColor = {
    (RGBAColor(_: Int, _: Int, name)).tupled.apply(grayOpacityTuple)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param name
   *
   * @return
   */
  def apply(red: Int, green: Int, blue: Int, name: Option[String]): RGBAColor = {
    RGBAColor(red, green, blue, ColorValidator.MaximumRGBAOpacity, name)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  def apply(red: Int, green: Int, blue: Int): RGBAColor = {
    RGBAColor(red, green, blue, ColorValidator.MaximumRGBAOpacity)
  }

  /**
   *
   *
   * @param rgbTuple
   * @param name
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(rgbTuple: (Int, Int, Int), name: Option[String]): RGBAColor = {
    (RGBAColor(_: Int, _: Int, _: Int, name)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(rgbTuple: (Int, Int, Int)): RGBAColor = {
    (RGBAColor(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param rgbTuple
   * @param opacity
   * @param name
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(rgbTuple: (Int, Int, Int), opacity: Int, name: Option[String]): RGBAColor = {
    (RGBAColor(_: Int, _: Int, _: Int, opacity, name)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param rgbTuple
   * @param opacity
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(rgbTuple: (Int, Int, Int), opacity: Int): RGBAColor = {
    (RGBAColor(_: Int, _: Int, _: Int, opacity)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param gray
   * @param opacity
   *
   * @return
   */
  def apply(gray: Int, opacity: Int): RGBAColor = RGBAColor(gray, gray, gray, opacity)


  /**
   *
   *
   * @param grayOpacityTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def apply(grayOpacityTuple: (Int, Int)): RGBAColor = {
    (RGBAColor(_: Int, _: Int)).tupled.apply(grayOpacityTuple)
  }

  /**
   *
   *
   * @param argbInt
   * @param name
   *
   * @return
   */
  def apply(argbInt: Int, name: Option[String]): RGBAColor = {
    RGBAColor(rgbaTupleFrom(argbInt), name)
  }

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  def apply(argbInt: Int): RGBAColor = RGBAColor(rgbaTupleFrom(argbInt))

  /**
   *
   *
   * @param platformColor
   *
   * @return
   */
  private[smcl] def apply(platformColor: ColorAdapter): RGBAColor = {
    RGBAColor(
      platformColor.red,
      platformColor.green,
      platformColor.blue,
      platformColor.opacity)
  }

  /**
   *
   *
   * @param platformColor
   * @param name
   *
   * @return
   */
  private[smcl] def apply(
      platformColor: ColorAdapter,
      name: Option[String]): RGBAColor = {

    RGBAColor(
      platformColor.red,
      platformColor.green,
      platformColor.blue,
      platformColor.opacity,
      name)
  }

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param value
   *
   * @return
   */
  def fromHSV(hueInDegrees: Double, saturation: Double, value: Double): RGBAColor = {
    fromHsv(hueInDegrees, saturation, value, ColorValidator.MaximumRGBAOpacity)
  }

  /**
   *
   *
   * @param hsvTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHSV(hsvTuple: (Double, Double, Double)): RGBAColor = {
    (fromHsv(_: Double, _: Double, _: Double, ColorValidator.MaximumRGBAOpacity))
        .tupled.apply(hsvTuple)
  }

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param value
   *
   * @return
   */
  def fromHSV(
      hueInDegrees: Double,
      saturation: Double,
      value: Double,
      name: Option[String]): RGBAColor = {

    fromHSV(hueInDegrees, saturation, value, ColorValidator.MaximumRGBAOpacity, name)
  }

  /**
   *
   *
   * @param hsvTuple
   * @param name
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHSV(
      hsvTuple: (Double, Double, Double),
      name: Option[String]): RGBAColor = {

    (fromHSV(_: Double, _: Double, _: Double, ColorValidator.MaximumRGBAOpacity, name))
        .tupled.apply(hsvTuple)
  }

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
      opacity: Int): RGBAColor = {

    RGBAColor(hsvToRGB(hueInDegrees, saturation, value), opacity)
  }

  /**
   *
   *
   * @param hsvTuple
   * @param opacity
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHSV(hsvTuple: (Double, Double, Double), opacity: Int): RGBAColor = {
    (fromHsv(_: Double, _: Double, _: Double, opacity)).tupled.apply(hsvTuple)
  }

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
  def fromHSV(
      hueInDegrees: Double,
      saturation: Double,
      value: Double,
      opacity: Int,
      name: Option[String]): RGBAColor = {

    RGBAColor(hsvToRGB(hueInDegrees, saturation, value), opacity, name)
  }

  /**
   *
   *
   * @param hsvTuple
   * @param opacity
   * @param name
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHSV(
      hsvTuple: (Double, Double, Double),
      opacity: Int,
      name: Option[String]): RGBAColor = {

    (fromHSV(_: Double, _: Double, _: Double, opacity, name))
        .tupled.apply(hsvTuple)
  }

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   *
   * @return
   */
  def fromHSI(hueInDegrees: Double, saturation: Double, intensity: Double): RGBAColor = {
    fromHSI(hueInDegrees, saturation, intensity, ColorValidator.MaximumRGBAOpacity)
  }

  /**
   *
   *
   * @param hsiTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHSI(hsiTuple: (Double, Double, Double)): RGBAColor = {
    (fromHSI(_: Double, _: Double, _: Double, ColorValidator.MaximumRGBAOpacity))
        .tupled.apply(hsiTuple)
  }

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   *
   * @return
   */
  def fromHSI(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double,
      name: Option[String]): RGBAColor = {

    fromHSI(hueInDegrees, saturation, intensity,
      ColorValidator.MaximumRGBAOpacity, name)
  }

  /**
   *
   *
   * @param hsiTuple
   * @param name
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHSI(
      hsiTuple: (Double, Double, Double),
      name: Option[String]): RGBAColor = {

    (fromHSI(_: Double, _: Double, _: Double,
      ColorValidator.MaximumRGBAOpacity, name))
        .tupled.apply(hsiTuple)
  }

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
  def fromHSI(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double,
      opacity: Int): RGBAColor = {

    RGBAColor(hsiToRGB(hueInDegrees, saturation, intensity), opacity)
  }

  /**
   *
   *
   * @param hsiTuple
   * @param opacity
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHSI(hsiTuple: (Double, Double, Double), opacity: Int): RGBAColor = {
    (fromHSI(_: Double, _: Double, _: Double, opacity)).tupled.apply(hsiTuple)
  }

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
  def fromHSI(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double,
      opacity: Int,
      name: Option[String]): RGBAColor = {

    RGBAColor(hsiToRGB(hueInDegrees, saturation, intensity), opacity, name)
  }

  /**
   *
   *
   * @param hsiTuple
   * @param opacity
   * @param name
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def fromHSI(
      hsiTuple: (Double, Double, Double),
      opacity: Int,
      name: Option[String]): RGBAColor = {

    (fromHSI(_: Double, _: Double, _: Double, opacity, name))
        .tupled.apply(hsiTuple)
  }


  //
  private var _colorValidator: Option[ColorValidator] = None

  /**
   * Returns the ColorValidator instance to be used by this object.
   *
   * @return
   *
   * @throws ImplementationNotSetError
   */
  private def colorValidator: ColorValidator = {
    _colorValidator.getOrElse(throw ImplementationNotSetError("ColorValidator"))
  }

  /**
   * Set the ColorValidator instance to be used by this object.
   *
   * @param validator
   */
  private[smcl] def setColorValidator(validator: ColorValidator): Unit = {
    require(validator != null,
      "The ColorValidator instance must be given (was null)")

    _colorValidator = Some(validator)
  }

}




/**
 *
 *
 * @param red
 * @param green
 * @param blue
 * @param opacity
 * @param canonicalName
 *
 * @author Aleksi Lukkarinen
 */
class RGBAColor protected(
    val red: Int,
    val green: Int,
    val blue: Int,
    val opacity: Int,
    val canonicalName: Option[String] = None) extends {

  /** Returns `true` if this [[RGBAColor]] is provided by SMCL, otherwise `false`. */
  val isPreset: Boolean = false

} with Ordered[RGBAColor] with Immutable with Describable {

  /** CSS name of this color. Only some of the preset colors have a value for this property. */
  val cssName: Option[String] = None

  /** This [[RGBAColor]] coded into an `Int`. */
  lazy val toARGBInt: Int = argbIntFrom(red, green, blue, opacity)

  /** Returns `true` if this [[RGBAColor]] is fully opaque, otherwise `false`. */
  val isOpaque: Boolean = opacity == ColorValidator.MaximumRGBAOpacity

  /** Returns `false` if this [[RGBAColor]] is fully opaque, otherwise `true`. */
  val isTransparent: Boolean = !isOpaque

  /** Returns `true` if this [[RGBAColor]] is created by the user, otherwise `false`. */
  val isUserCreated: Boolean = !isPreset

  /** This [[RGBAColor]] represented as a 32-digit binary string of four 8-digit groups. */
  lazy val toBinaryString: String = toARGBInt.toArgbBinaryColorString

  /** This [[RGBAColor]] represented as a hexadecimal string. */
  lazy val toHexString: String = toARGBInt.toArgbHexColorString

  /** First text paragraph of the description of this class. */
  @inline
  val descriptionTitle: String = "RGBA Color"

  /** Information about this [[aalto.smcl.bitmaps.operations.Renderable]] instance */
  lazy val describedProperties = Map(
    "Canonical Name" -> new StringUtils().titleCase(canonicalName getOrElse StrUnnamed),
    "CSS Name" -> (cssName getOrElse StrUnnamed),
    "Red Component" -> red,
    "Green Component" -> green,
    "Blue Component" -> blue,
    "Opacity Component" -> opacity
  )

  /** */
  lazy val toColorComponentMap: Map[Symbol, Double] = colorComponentMapFrom(this)

  /** */
  lazy val toNormalizedRGBAComponents: (Double, Double, Double, Double) =
    normalizeRGBA(red, green, blue, opacity)

  /** */
  lazy val toGray: RGBAColor = RGBAColor(toHSIIntensity.toInt, FullyOpaque)

  /** */
  lazy val toHSI: (Double, Double, Double) = colors.toHsi(this)

  /** */
  lazy val toHSIHueInDegrees: Double = toHSI._1

  /** */
  lazy val toHSISaturation: Double = toHSI._2

  /** */
  lazy val toHSIIntensity: Double = toHSI._3

  /** */
  lazy val toHSV: (Double, Double, Double) = colors.toHSV(this)

  /** */
  lazy val toHSVHueInDegrees: Double = toHSV._1

  /** */
  lazy val toHSVSaturation: Double = toHSV._2

  /** */
  lazy val toHSVValue: Double = toHSV._3

  /** */
  lazy val isBlack: Boolean = colors.isBlack(this)

  /** */
  lazy val isWhite: Boolean = colors.isWhite(this)

  /** */
  lazy val isGray: Boolean = colors.isGray(this)

  /** */
  lazy val keepingOnlyRedComponent: RGBAColor =
    RGBAColor(red, ColorValidator.MinimumRGBGreen, ColorValidator.MinimumRGBBlue, FullyOpaque)

  /** */
  lazy val keepingOnlyGreenComponent: RGBAColor =
    RGBAColor(ColorValidator.MinimumRGBRed, green, ColorValidator.MinimumRGBBlue, FullyOpaque)

  /** */
  lazy val keepingOnlyBlueComponent: RGBAColor =
    RGBAColor(ColorValidator.MinimumRGBRed, ColorValidator.MinimumRGBGreen, blue, FullyOpaque)

  /** */
  lazy val keepingOnlyRedAndGreenComponents: RGBAColor =
    RGBAColor(red, green, ColorValidator.MinimumRGBBlue, FullyOpaque)

  /** */
  lazy val keepingOnlyRedAndBlueComponents: RGBAColor =
    RGBAColor(red, ColorValidator.MinimumRGBGreen, blue, FullyOpaque)

  /** */
  lazy val keepingOnlyGreenAndBlueComponents: RGBAColor =
    RGBAColor(ColorValidator.MinimumRGBRed, green, blue, FullyOpaque)

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
    Math.signum(that.toHSIHueInDegrees - this.toHSIHueInDegrees).toInt

  /**
   *
   *
   * @param that
   *
   * @return
   */
  def compareByHSISaturation(that: RGBAColor): Int =
    Math.signum(that.toHSISaturation - this.toHSISaturation).toInt

  /**
   *
   *
   * @param that
   *
   * @return
   */
  def compareByHSIIntensity(that: RGBAColor): Int =
    Math.signum(that.toHSIIntensity - this.toHSIIntensity).toInt

}
