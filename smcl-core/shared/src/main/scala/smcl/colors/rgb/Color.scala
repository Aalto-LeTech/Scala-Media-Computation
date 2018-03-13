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

package smcl.colors.rgb


import smcl.colors
import smcl.colors._
import smcl.infrastructure._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Color
    extends InjectablesRegistry {

  /** The ColorValidator instance to be used by this object. */
  private
  lazy val commonValidators: CommonValidators = {
    injectable(InjectablesRegistry.IIdCommonValidators).asInstanceOf[CommonValidators]
  }

  /** The ColorValidator instance to be used by this object. */
  private
  lazy val colorValidator: ColorValidator = {
    injectable(InjectablesRegistry.IIdColorValidator).asInstanceOf[ColorValidator]
  }

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
      red: Int,
      green: Int,
      blue: Int,
      opacity: Int,
      name: Option[String] = None): Color = {

    colorValidator.validateRGBAColor(red, green, blue, opacity)

    val resultName = colorValidator.validateCanonicalColorName(name)

    new Color(
      red, green, blue, opacity,
      resultName,
      commonValidators, colorValidator)
  }

  /**
   *
   *
   * @param rgbaTuple
   * @param name
   *
   * @return
   */
  def apply(
      rgbaTuple: (Int, Int, Int, Int),
      name: Option[String]): Color = {

    (apply(_: Int, _: Int, _: Int, _: Int, name))
        .tupled.apply(rgbaTuple)
  }

  /**
   *
   *
   * @param rgbaTuple
   *
   * @return
   */
  def apply(rgbaTuple: (Int, Int, Int, Int)): Color =
    (apply(_: Int, _: Int, _: Int, _: Int)).tupled.apply(rgbaTuple)

  /**
   *
   *
   * @param gray
   * @param opacity
   * @param name
   *
   * @return
   */
  def apply(
      gray: Int,
      opacity: Int,
      name: Option[String]): Color = {

    apply(gray, gray, gray, opacity, name)
  }

  /**
   *
   *
   * @param grayOpacityTuple
   * @param name
   *
   * @return
   */
  def apply(
      grayOpacityTuple: (Int, Int),
      name: Option[String]): Color = {

    (apply(_: Int, _: Int, name)).tupled.apply(grayOpacityTuple)
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
  def apply(
      red: Int,
      green: Int,
      blue: Int,
      name: Option[String]): Color = {

    apply(red, green, blue, ColorValidator.MaximumOpacity, name)
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
  def apply(
      red: Int,
      green: Int,
      blue: Int): Color = {

    apply(red, green, blue, ColorValidator.MaximumOpacity)
  }

  /**
   *
   *
   * @param rgbTuple
   * @param name
   *
   * @return
   */
  def apply(
      rgbTuple: (Int, Int, Int),
      name: Option[String]): Color = {

    (apply(_: Int, _: Int, _: Int, name)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  def apply(rgbTuple: (Int, Int, Int)): Color =
    (apply(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param rgbTuple
   * @param opacity
   * @param name
   *
   * @return
   */
  def apply(
      rgbTuple: (Int, Int, Int),
      opacity: Int,
      name: Option[String]): Color = {

    (apply(_: Int, _: Int, _: Int, opacity, name)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param rgbTuple
   * @param opacity
   *
   * @return
   */
  def apply(
      rgbTuple: (Int, Int, Int),
      opacity: Int): Color = {

    (apply(_: Int, _: Int, _: Int, opacity)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param gray
   * @param opacity
   *
   * @return
   */
  def apply(
      gray: Int,
      opacity: Int): Color = {

    apply(gray, gray, gray, opacity)
  }

  /**
   *
   *
   * @param grayOpacityTuple
   *
   * @return
   */
  def apply(grayOpacityTuple: (Int, Int)): Color =
    (apply(_: Int, _: Int)).tupled.apply(grayOpacityTuple)

  /**
   *
   *
   * @param argbInt
   * @param name
   *
   * @return
   */
  def apply(
      argbInt: Int,
      name: Option[String]): Color = {

    apply(rgbaTupleFrom(argbInt), name)
  }

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  def apply(argbInt: Int): Color = Color(rgbaTupleFrom(argbInt))

  /**
   *
   *
   * @param platformColor
   *
   * @return
   */
  private[smcl]
  def apply(platformColor: ColorAdapter): Color =
    apply(
      platformColor.red,
      platformColor.green,
      platformColor.blue,
      platformColor.opacity)

  /**
   *
   *
   * @param platformColor
   * @param name
   *
   * @return
   */
  private[smcl]
  def apply(
      platformColor: ColorAdapter,
      name: Option[String]): Color = {

    apply(
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
  def fromHSV(
      hueInDegrees: Double,
      saturation: Double,
      value: Double): Color = {

    fromHSV(hueInDegrees, saturation, value, ColorValidator.MaximumOpacity)
  }

  /**
   *
   *
   * @param hsvTuple
   *
   * @return
   */
  def fromHSV(hsvTuple: (Double, Double, Double)): Color = {
    (fromHSV(_: Double, _: Double, _: Double, ColorValidator.MaximumOpacity))
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
      name: Option[String]): Color = {

    fromHSV(hueInDegrees, saturation, value, ColorValidator.MaximumOpacity, name)
  }

  /**
   *
   *
   * @param hsvTuple
   * @param name
   *
   * @return
   */
  def fromHSV(
      hsvTuple: (Double, Double, Double),
      name: Option[String]): Color = {

    (fromHSV(_: Double, _: Double, _: Double, ColorValidator.MaximumOpacity, name))
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
  def fromHSV(
      hueInDegrees: Double,
      saturation: Double,
      value: Double,
      opacity: Int): Color = {

    apply(hsvToRGB(hueInDegrees, saturation, value), opacity)
  }

  /**
   *
   *
   * @param hsvTuple
   * @param opacity
   *
   * @return
   */
  def fromHSV(
      hsvTuple: (Double, Double, Double),
      opacity: Int): Color = {

    (fromHSV(_: Double, _: Double, _: Double, opacity))
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
  def fromHSV(
      hueInDegrees: Double,
      saturation: Double,
      value: Double,
      opacity: Int,
      name: Option[String]): Color = {

    apply(hsvToRGB(hueInDegrees, saturation, value), opacity, name)
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
  def fromHSV(
      hsvTuple: (Double, Double, Double),
      opacity: Int,
      name: Option[String]): Color = {

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
  def fromHSI(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double): Color = {

    fromHSI(hueInDegrees, saturation, intensity,
      ColorValidator.MaximumOpacity)
  }

  /**
   *
   *
   * @param hsiTuple
   *
   * @return
   */
  def fromHSI(hsiTuple: (Double, Double, Double)): Color =
    (fromHSI(_: Double, _: Double, _: Double, ColorValidator.MaximumOpacity))
        .tupled.apply(hsiTuple)

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
      name: Option[String]): Color = {

    fromHSI(hueInDegrees, saturation, intensity,
      ColorValidator.MaximumOpacity, name)
  }

  /**
   *
   *
   * @param hsiTuple
   * @param name
   *
   * @return
   */
  def fromHSI(
      hsiTuple: (Double, Double, Double),
      name: Option[String]): Color = {

    (fromHSI(_: Double, _: Double, _: Double,
      ColorValidator.MaximumOpacity, name))
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
      opacity: Int): Color = {

    apply(hsiToRGB(hueInDegrees, saturation, intensity), opacity)
  }

  /**
   *
   *
   * @param hsiTuple
   * @param opacity
   *
   * @return
   */
  def fromHSI(
      hsiTuple: (Double, Double, Double),
      opacity: Int): Color = {

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
      name: Option[String]): Color = {

    apply(hsiToRGB(hueInDegrees, saturation, intensity), opacity, name)
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
  def fromHSI(
      hsiTuple: (Double, Double, Double),
      opacity: Int,
      name: Option[String]): Color = {

    (fromHSI(_: Double, _: Double, _: Double, opacity, name))
        .tupled.apply(hsiTuple)
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
class Color protected(
    val red: Int,
    val green: Int,
    val blue: Int,
    val opacity: Int,
    val canonicalName: Option[String] = None,
    private val commonValidators: CommonValidators,
    private val colorValidator: ColorValidator) extends {

  /** Returns `true` if this [[Color]] is provided by SMCL, otherwise `false`. */
  val isPreset: Boolean = false

} with Ordered[Color] with Immutable with Describable {

  /** CSS name of this color. Only some of the preset colors have a value for this property. */
  val cssName: Option[String] = None

  /** This [[Color]] coded into an `Int`. */
  lazy val toARGBInt: Int = argbIntFrom(red, green, blue, opacity)

  /** Returns `true` if this [[Color]] is fully opaque, otherwise `false`. */
  val isOpaque: Boolean = opacity == ColorValidator.MaximumOpacity

  /** Returns `false` if this [[Color]] is fully opaque, otherwise `true`. */
  val isTransparent: Boolean = !isOpaque

  /** Returns `true` if this [[Color]] is created by the user, otherwise `false`. */
  val isUserCreated: Boolean = !isPreset

  /** This [[Color]] represented as a 32-digit binary string of four 8-digit groups. */
  lazy val toBinaryString: String = toARGBInt.toARGBBinaryColorString

  /** This [[Color]] represented as a hexadecimal string. */
  lazy val toHexString: String = toARGBInt.toARGBHexColorString

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "RGBA Color"

  /** */
  lazy val describedProperties = Map(
    "Canonical Name" -> canonicalName.orUnnamed,
    "CSS Name" -> cssName.orUnnamed,
    "Red Component" -> red,
    "Green Component" -> green,
    "Blue Component" -> blue,
    "Opacity Component" -> opacity
  )

  /** */
  lazy val toColorComponentMap: Map[Symbol, Double] = {
    colorComponentMapFrom(this)
  }

  /** */
  lazy val toNormalizedRGBAComponents: (Double, Double, Double, Double) = {
    normalizeRGBA(red, green, blue, opacity)
  }

  /** */
  lazy val toGray: Color = Color(toHSIIntensity.toInt, ColorValidator.FullyOpaque)

  /** */
  lazy val toHSI: (Double, Double, Double) = colors.toHSI(this)

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
  lazy val keepingOnlyRedComponent  : Color = {
    Color(
      red,
      ColorValidator.MinimumGreen,
      ColorValidator.MinimumBlue,
      ColorValidator.FullyOpaque)
  }
  /** */
  lazy val keepingOnlyGreenComponent: Color = {
    Color(
      ColorValidator.MinimumRed,
      green,
      ColorValidator.MinimumBlue,
      ColorValidator.FullyOpaque)
  }

  /** */
  lazy val keepingOnlyBlueComponent: Color = {
    Color(
      ColorValidator.MinimumRed,
      ColorValidator.MinimumGreen,
      blue,
      ColorValidator.FullyOpaque)
  }

  /** */
  lazy val keepingOnlyRedAndGreenComponents: Color = {
    Color(
      red,
      green,
      ColorValidator.MinimumBlue,
      ColorValidator.FullyOpaque)
  }

  /** */
  lazy val keepingOnlyRedAndBlueComponents: Color = {
    Color(
      red,
      ColorValidator.MinimumGreen,
      blue,
      ColorValidator.FullyOpaque)
  }

  /** */
  lazy val keepingOnlyGreenAndBlueComponents: Color = {
    Color(
      ColorValidator.MinimumRed,
      green,
      blue,
      ColorValidator.FullyOpaque)
  }

  /** */
  lazy val representingOpacityAsGreyLevel: Color = {
    Color(
      opacity,
      opacity,
      opacity,
      ColorValidator.FullyOpaque)
  }

  /**
   *
   *
   * @return
   */
  @inline
  override
  lazy val hashCode: Int = {
    val prime = 31

    var result = 1
    result = prime * result + red
    result = prime * result + green
    result = prime * result + blue
    result = prime * result + opacity

    result
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  def canEqual(other: Any): Boolean = other.isInstanceOf[Color]

  /**
   * An equality comparison method that directly calls the equals() method.
   *
   * @param other
   *
   * @return
   */
  @inline
  def is(other: Color): Boolean = equals(other)

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  override
  def equals(other: Any): Boolean = other match {
    case that: Color =>
      that.canEqual(this) &&
          that.red == this.red &&
          that.green == this.green &&
          that.blue == this.blue &&
          that.opacity == this.opacity

    case _ => false
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  def equalsIncludingName(other: Any): Boolean = other match {
    case that: Color =>
      that.canEqual(this) &&
          that.red == this.red &&
          that.green == this.green &&
          that.blue == this.blue &&
          that.opacity == this.opacity &&
          that.canonicalName == this.canonicalName

    case _ => false
  }


  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  def equalsIncludingNameAndPresetness(other: Any): Boolean = other match {
    case that: Color =>
      that.canEqual(this) &&
          that.red == this.red &&
          that.green == this.green &&
          that.blue == this.blue &&
          that.opacity == this.opacity &&
          that.canonicalName == this.canonicalName &&
          that.isPreset == this.isPreset

    case _ => false
  }

  /**
   *
   *
   * @param newRed
   * @param newGreen
   * @param newBlue
   * @param newOpacity
   *
   * @return
   */
  @inline
  def copy(
      newRed: Int = red,
      newGreen: Int = green,
      newBlue: Int = blue,
      newOpacity: Int = opacity): Color = {

    Color(newRed, newGreen, newBlue, newOpacity)
  }

  /**
   *
   *
   * @param that
   *
   * @return
   */
  @inline
  override
  def compare(that: Color): Int = {
    Math.signum(that.toHSIHueInDegrees - this.toHSIHueInDegrees).toInt
  }

  /**
   *
   *
   * @param that
   *
   * @return
   */
  @inline
  def compareByHSISaturation(that: Color): Int = {
    Math.signum(that.toHSISaturation - this.toHSISaturation).toInt
  }

  /**
   *
   *
   * @param that
   *
   * @return
   */
  @inline
  def compareByHSIIntensity(that: Color): Int = {
    Math.signum(that.toHSIIntensity - this.toHSIIntensity).toInt
  }

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toString: String = {
    s"Color(red: $red, green: $green, blue: $blue, opacity: $opacity)"
  }

  /**
   *
   *
   * @param that
   * @param portionOfThis
   *
   * @return
   */
  @inline
  def mixWith(
      that: Color,
      portionOfThis: Double,
      portionsSource: MixUsingPortionsSource): Color = {

    portionsSource match {
      case MUPortionsOfThis     => mixWithUsingPortionOfThis(that, portionOfThis)
      case MUPortionsOfTheOther => mixWithUsingPortionOfThat(that, portionOfThis)
    }
  }

  /**
   *
   *
   * @param that
   * @param portionOfThis
   *
   * @return
   */
  @inline
  private[smcl]
  def mixWithInternal(
      that: Color,
      portionOfThis: Double,
      portionsSource: MixUsingPortionsSource): Color = {

    portionsSource match {
      case MUPortionsOfThis     => mixWithUsingPortionOfThisInternal(that, portionOfThis)
      case MUPortionsOfTheOther => mixWithUsingPortionOfThatInternal(that, portionOfThis)
    }
  }

  /**
   *
   *
   * @param that
   * @param portionOfThis
   *
   * @return
   */
  @inline
  def mixWith(
      that: Color,
      portionOfThis: Double,
      resultOpacity: Int,
      portionsSource: MixUsingPortionsSource): Color = {

    portionsSource match {
      case MUPortionsOfThis     => mixWithUsingPortionOfThis(that, portionOfThis, resultOpacity)
      case MUPortionsOfTheOther => mixWithUsingPortionOfThat(that, portionOfThis, resultOpacity)
    }
  }

  /**
   *
   *
   * @param that
   * @param portionOfThis
   *
   * @return
   */
  @inline
  private[smcl]
  def mixWithInternal(
      that: Color,
      portionOfThis: Double,
      resultOpacity: Int,
      portionsSource: MixUsingPortionsSource): Color = {

    portionsSource match {
      case MUPortionsOfThis =>
        mixWithUsingPortionOfThisInternal(that, portionOfThis, resultOpacity)

      case MUPortionsOfTheOther =>
        mixWithUsingPortionOfThatInternal(that, portionOfThis, resultOpacity)
    }
  }

  /**
   *
   *
   * @param that
   * @param redPortionOfThis
   * @param greenPortionOfThis
   * @param bluePortionOfThis
   * @param opacityPortionOfThis
   *
   * @return
   */
  @inline
  def mixWith(
      that: Color,
      redPortionOfThis: Double,
      greenPortionOfThis: Double,
      bluePortionOfThis: Double,
      opacityPortionOfThis: Double,
      portionsSource: MixUsingPortionsSource): Color = {

    portionsSource match {
      case MUPortionsOfThis =>
        mixWithUsingPortionsOfThis(
          that, redPortionOfThis, greenPortionOfThis, bluePortionOfThis, opacityPortionOfThis)

      case MUPortionsOfTheOther =>
        mixWithUsingPortionsOfThat(
          that, redPortionOfThis, greenPortionOfThis, bluePortionOfThis, opacityPortionOfThis)
    }
  }

  /**
   *
   *
   * @param that
   * @param redPortionOfThis
   * @param greenPortionOfThis
   * @param bluePortionOfThis
   * @param opacityPortionOfThis
   *
   * @return
   */
  @inline
  private[smcl]
  def mixWithInternal(
      that: Color,
      redPortionOfThis: Double,
      greenPortionOfThis: Double,
      bluePortionOfThis: Double,
      opacityPortionOfThis: Double,
      portionsSource: MixUsingPortionsSource): Color = {

    portionsSource match {
      case MUPortionsOfThis =>
        mixWithUsingPortionsOfThisInternal(
          that, redPortionOfThis, greenPortionOfThis, bluePortionOfThis, opacityPortionOfThis)

      case MUPortionsOfTheOther =>
        mixWithUsingPortionsOfThatInternal(
          that, redPortionOfThis, greenPortionOfThis, bluePortionOfThis, opacityPortionOfThis)
    }
  }

  /**
   *
   *
   * @param that
   * @param redPortionOfThis
   * @param greenPortionOfThis
   * @param bluePortionOfThis
   * @param resultOpacity
   *
   * @return
   */
  @inline
  def mixWith(
      that: Color,
      redPortionOfThis: Double,
      greenPortionOfThis: Double,
      bluePortionOfThis: Double,
      resultOpacity: Int,
      portionsSource: MixUsingPortionsSource): Color = {

    portionsSource match {
      case MUPortionsOfThis =>
        mixWithUsingPortionsOfThis(
          that, redPortionOfThis, greenPortionOfThis, bluePortionOfThis, resultOpacity)

      case MUPortionsOfTheOther =>
        mixWithUsingPortionsOfThat(
          that, redPortionOfThis, greenPortionOfThis, bluePortionOfThis, resultOpacity)
    }
  }

  /**
   *
   *
   * @param that
   * @param redPortionOfThis
   * @param greenPortionOfThis
   * @param bluePortionOfThis
   * @param resultOpacity
   * @param portionsSource
   *
   * @return
   */
  @inline
  private[smcl]
  def mixWithInternal(
      that: Color,
      redPortionOfThis: Double,
      greenPortionOfThis: Double,
      bluePortionOfThis: Double,
      resultOpacity: Int,
      portionsSource: MixUsingPortionsSource): Color = {

    portionsSource match {
      case MUPortionsOfThis =>
        mixWithUsingPortionsOfThisInternal(
          that, redPortionOfThis, greenPortionOfThis, bluePortionOfThis, resultOpacity)

      case MUPortionsOfTheOther =>
        mixWithUsingPortionsOfThatInternal(
          that, redPortionOfThis, greenPortionOfThis, bluePortionOfThis, resultOpacity)
    }
  }


  /**
   *
   *
   * @param that
   * @param portionOfThis
   *
   * @return
   */
  @inline
  def mixWithUsingPortionOfThis(
      that: Color,
      portionOfThis: Double): Color = {

    commonValidators.validateZeroToOneFactor(portionOfThis, None)

    mixWithUsingPortionOfThisInternal(that, portionOfThis)
  }

  /**
   *
   *
   * @param that
   * @param portionOfThis
   *
   * @return
   */
  @inline
  private[smcl]
  def mixWithUsingPortionOfThisInternal(
      that: Color,
      portionOfThis: Double): Color = {

    mixWithUsingPortionsOfThisInternal(
      that,
      redPortionOfThis = portionOfThis,
      greenPortionOfThis = portionOfThis,
      bluePortionOfThis = portionOfThis,
      opacityPortionOfThis = portionOfThis)
  }

  /**
   *
   *
   * @param that
   * @param portionOfThis
   *
   * @return
   */
  @inline
  def mixWithUsingPortionOfThis(
      that: Color,
      portionOfThis: Double,
      resultOpacity: Int): Color = {

    commonValidators.validateZeroToOneFactor(portionOfThis, None)
    colorValidator.validateOpacityComponent(resultOpacity)

    mixWithUsingPortionOfThisInternal(
      that, portionOfThis, resultOpacity)
  }

  /**
   *
   *
   * @param that
   * @param portionOfThis
   *
   * @return
   */
  @inline
  private[smcl]
  def mixWithUsingPortionOfThisInternal(
      that: Color,
      portionOfThis: Double,
      resultOpacity: Int): Color = {

    mixWithUsingPortionsOfThisInternal(
      that,
      redPortionOfThis = portionOfThis,
      greenPortionOfThis = portionOfThis,
      bluePortionOfThis = portionOfThis,
      resultOpacity = resultOpacity)
  }

  /**
   *
   *
   * @param that
   * @param redPortionOfThis
   * @param greenPortionOfThis
   * @param bluePortionOfThis
   * @param opacityPortionOfThis
   *
   * @return
   */
  @inline
  def mixWithUsingPortionsOfThis(
      that: Color,
      redPortionOfThis: Double,
      greenPortionOfThis: Double,
      bluePortionOfThis: Double,
      opacityPortionOfThis: Double): Color = {

    commonValidators.validateZeroToOneFactor(redPortionOfThis, None)
    commonValidators.validateZeroToOneFactor(greenPortionOfThis, None)
    commonValidators.validateZeroToOneFactor(bluePortionOfThis, None)
    commonValidators.validateZeroToOneFactor(opacityPortionOfThis, None)

    mixWithUsingPortionsOfThisInternal(
      that,
      redPortionOfThis,
      greenPortionOfThis,
      bluePortionOfThis,
      opacityPortionOfThis)
  }

  /**
   *
   *
   * @param that
   * @param redPortionOfThis
   * @param greenPortionOfThis
   * @param bluePortionOfThis
   * @param opacityPortionOfThis
   *
   * @return
   */
  @inline
  private[smcl]
  def mixWithUsingPortionsOfThisInternal(
      that: Color,
      redPortionOfThis: Double,
      greenPortionOfThis: Double,
      bluePortionOfThis: Double,
      opacityPortionOfThis: Double): Color = {

    val resultOpacity =
      (opacityPortionOfThis * this.opacity +
          (1 - opacityPortionOfThis) * that.opacity).toInt

    mixWithUsingPortionsOfThisInternal(
      that,
      redPortionOfThis,
      greenPortionOfThis,
      bluePortionOfThis,
      resultOpacity)
  }

  /**
   *
   *
   * @param that
   * @param redPortionOfThis
   * @param greenPortionOfThis
   * @param bluePortionOfThis
   * @param resultOpacity
   *
   * @return
   */
  @inline
  def mixWithUsingPortionsOfThis(
      that: Color,
      redPortionOfThis: Double,
      greenPortionOfThis: Double,
      bluePortionOfThis: Double,
      resultOpacity: Int): Color = {

    commonValidators.validateZeroToOneFactor(redPortionOfThis, None)
    commonValidators.validateZeroToOneFactor(greenPortionOfThis, None)
    commonValidators.validateZeroToOneFactor(bluePortionOfThis, None)
    colorValidator.validateOpacityComponent(resultOpacity)

    mixWithUsingPortionsOfThisInternal(
      that,
      redPortionOfThis,
      greenPortionOfThis,
      bluePortionOfThis,
      resultOpacity)
  }

  /**
   *
   *
   * @param that
   * @param redPortionOfThis
   * @param greenPortionOfThis
   * @param bluePortionOfThis
   * @param resultOpacity
   *
   * @return
   */
  @inline
  private[smcl]
  def mixWithUsingPortionsOfThisInternal(
      that: Color,
      redPortionOfThis: Double,
      greenPortionOfThis: Double,
      bluePortionOfThis: Double,
      resultOpacity: Int): Color = {

    val resultRed =
      (redPortionOfThis * this.red +
          (1 - redPortionOfThis) * that.red).toInt

    val resultGreen =
      (greenPortionOfThis * this.green +
          (1 - greenPortionOfThis) * that.green).toInt

    val resultBlue =
      (bluePortionOfThis * this.blue +
          (1 - bluePortionOfThis) * that.blue).toInt

    Color(resultRed, resultGreen, resultBlue, resultOpacity)
  }

  /**
   *
   *
   * @param that
   * @param portionOfThat
   *
   * @return
   */
  @inline
  def mixWithUsingPortionOfThat(
      that: Color,
      portionOfThat: Double): Color = {

    commonValidators.validateZeroToOneFactor(portionOfThat, None)

    mixWithUsingPortionOfThatInternal(that, portionOfThat)
  }

  /**
   *
   *
   * @param that
   * @param portionOfThat
   *
   * @return
   */
  @inline
  private[smcl]
  def mixWithUsingPortionOfThatInternal(
      that: Color,
      portionOfThat: Double): Color = {

    that.mixWithUsingPortionOfThisInternal(this, portionOfThat)
  }

  /**
   *
   *
   * @param that
   * @param portionOfThat
   *
   * @return
   */
  @inline
  def mixWithUsingPortionOfThat(
      that: Color,
      portionOfThat: Double,
      resultOpacity: Int): Color = {

    commonValidators.validateZeroToOneFactor(portionOfThat, None)
    colorValidator.validateOpacityComponent(resultOpacity)

    mixWithUsingPortionOfThatInternal(
      that, portionOfThat, resultOpacity)
  }

  /**
   *
   *
   * @param that
   * @param portionOfThat
   *
   * @return
   */
  @inline
  private[smcl]
  def mixWithUsingPortionOfThatInternal(
      that: Color,
      portionOfThat: Double,
      resultOpacity: Int): Color = {

    that.mixWithUsingPortionsOfThisInternal(
      this,
      redPortionOfThis = portionOfThat,
      greenPortionOfThis = portionOfThat,
      bluePortionOfThis = portionOfThat,
      resultOpacity = resultOpacity)
  }

  /**
   *
   *
   * @param that
   * @param redPortionOfThat
   * @param greenPortionOfThat
   * @param bluePortionOfThat
   * @param opacityPortionOfThat
   *
   * @return
   */
  @inline
  def mixWithUsingPortionsOfThat(
      that: Color,
      redPortionOfThat: Double,
      greenPortionOfThat: Double,
      bluePortionOfThat: Double,
      opacityPortionOfThat: Double): Color = {

    commonValidators.validateZeroToOneFactor(redPortionOfThat, None)
    commonValidators.validateZeroToOneFactor(greenPortionOfThat, None)
    commonValidators.validateZeroToOneFactor(bluePortionOfThat, None)
    commonValidators.validateZeroToOneFactor(opacityPortionOfThat, None)

    mixWithUsingPortionsOfThatInternal(
      that,
      redPortionOfThat,
      greenPortionOfThat,
      bluePortionOfThat,
      opacityPortionOfThat)
  }

  /**
   *
   *
   * @param that
   * @param redPortionOfThat
   * @param greenPortionOfThat
   * @param bluePortionOfThat
   * @param opacityPortionOfThat
   *
   * @return
   */
  @inline
  private[smcl]
  def mixWithUsingPortionsOfThatInternal(
      that: Color,
      redPortionOfThat: Double,
      greenPortionOfThat: Double,
      bluePortionOfThat: Double,
      opacityPortionOfThat: Double): Color = {

    that.mixWithUsingPortionsOfThisInternal(
      this,
      redPortionOfThat,
      greenPortionOfThat,
      bluePortionOfThat,
      opacityPortionOfThat)
  }

  /**
   *
   *
   * @param that
   * @param redPortionOfThat
   * @param greenPortionOfThat
   * @param bluePortionOfThat
   * @param resultOpacity
   *
   * @return
   */
  @inline
  def mixWithUsingPortionsOfThat(
      that: Color,
      redPortionOfThat: Double,
      greenPortionOfThat: Double,
      bluePortionOfThat: Double,
      resultOpacity: Int): Color = {

    commonValidators.validateZeroToOneFactor(redPortionOfThat, None)
    commonValidators.validateZeroToOneFactor(greenPortionOfThat, None)
    commonValidators.validateZeroToOneFactor(bluePortionOfThat, None)
    colorValidator.validateOpacityComponent(resultOpacity)

    mixWithUsingPortionsOfThatInternal(
      that,
      redPortionOfThat,
      greenPortionOfThat,
      bluePortionOfThat,
      resultOpacity)
  }

  /**
   *
   *
   * @param that
   * @param redPortionOfThat
   * @param greenPortionOfThat
   * @param bluePortionOfThat
   * @param resultOpacity
   *
   * @return
   */
  @inline
  private[smcl]
  def mixWithUsingPortionsOfThatInternal(
      that: Color,
      redPortionOfThat: Double,
      greenPortionOfThat: Double,
      bluePortionOfThat: Double,
      resultOpacity: Int): Color = {

    that.mixWithUsingPortionsOfThisInternal(
      this,
      redPortionOfThat,
      greenPortionOfThat,
      bluePortionOfThat,
      resultOpacity)
  }

}
