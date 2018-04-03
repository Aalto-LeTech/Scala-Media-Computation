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
 * This companion object provides a selection of constructors to instantiate the [[Color]] class.
 * There are some convenience constructors using [[Double]] values as their parameters instead of
 * [[Int]] values; these just call `Double.toInt` for all the appropriate values.
 *
 * @author Aleksi Lukkarinen
 */
object Color
    extends InjectablesRegistry {

  /** The [[CommonValidators]] instance to be used by this object. */
  private
  lazy val commonValidators: CommonValidators =
    injectable(InjectablesRegistry.IIdCommonValidators).asInstanceOf[CommonValidators]

  /** The [[ColorValidator]] instance to be used by this object. */
  private
  lazy val colorValidator: ColorValidator =
    injectable(InjectablesRegistry.IIdColorValidator).asInstanceOf[ColorValidator]

  /** The factor the amount of which colors are shaded by the darker() method. */
  final def DefaultShadingFactor = 0.1

  /** The factor the amount of which colors are tinted by the lighter() method. */
  final def DefaultTintingFactor = 0.1

  /**
   * Internal method for actually instantiating the [[Color]] class.
   * All other constructors are to call this method.
   *
   * @param red     value of the red color component
   * @param green   value of the green color component
   * @param blue    value of the blue color component
   * @param opacity value of the opacity color component
   * @param name    optional name string
   *
   * @return a new instance of the [[Color]] class
   */
  @inline
  private
  def instantiateColor(
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
   * @group RGBA Constructors
   *
   * @param red
   * @param green
   * @param blue
   * @param opacity
   * @param name
   *
   * @return
   */
  @inline
  def apply(
      red: Int,
      green: Int,
      blue: Int,
      opacity: Int,
      name: String): Color = {

    instantiateColor(
      red, green, blue, opacity,
      Option(name))
  }

  /**
   *
   *
   * @group RGBA Constructors
   *
   * @param red
   * @param green
   * @param blue
   * @param opacity
   * @param name
   *
   * @return
   */
  @inline
  def apply(
      red: Double,
      green: Double,
      blue: Double,
      opacity: Double,
      name: String): Color = {

    instantiateColor(
      red.toInt, green.toInt, blue.toInt,
      opacity.toInt,
      Option(name))
  }

  /**
   *
   *
   * @group RGBA Constructors
   *
   * @param red
   * @param green
   * @param blue
   * @param opacity
   *
   * @return
   */
  @inline
  def apply(
      red: Int,
      green: Int,
      blue: Int,
      opacity: Int): Color = {

    instantiateColor(
      red, green, blue,
      opacity,
      None)
  }

  /**
   *
   *
   * @group RGBA Constructors
   *
   * @param red
   * @param green
   * @param blue
   * @param opacity
   *
   * @return
   */
  @inline
  def apply(
      red: Double,
      green: Double,
      blue: Double,
      opacity: Double): Color = {

    instantiateColor(
      red.toInt, green.toInt, blue.toInt,
      opacity.toInt)
  }

  /**
   *
   *
   * @group RGBA Constructors
   *
   * @param rgbaTuple
   * @param name
   *
   * @return
   */
  @inline
  def apply(
      rgbaTuple: (Int, Int, Int, Int),
      name: Option[String]): Color = {

    instantiateColor(
      rgbaTuple._1, rgbaTuple._2, rgbaTuple._3,
      rgbaTuple._4,
      name)
  }

  /**
   *
   *
   * @group RGBA Constructors
   *
   * @param rgbaTuple
   *
   * @return
   */
  @inline
  def apply(rgbaTuple: (Int, Int, Int, Int)): Color =
    instantiateColor(
      rgbaTuple._1, rgbaTuple._2, rgbaTuple._3,
      rgbaTuple._4)

  /**
   *
   *
   * @group Grayscale Constructors
   *
   * @param gray
   * @param opacity
   * @param name
   *
   * @return
   */
  @inline
  def apply(
      gray: Int,
      opacity: Int,
      name: Option[String]): Color = {

    instantiateColor(
      gray, gray, gray,
      opacity,
      name)
  }

  /**
   *
   * @group Grayscale Constructors
   *
   * @param gray
   * @param opacity
   * @param name
   *
   * @return
   */
  @inline
  def apply(
      gray: Double,
      opacity: Double,
      name: Option[String]): Color = {

    apply(
      gray.toInt,
      opacity.toInt,
      name)
  }

  /**
   *
   *
   * @group Grayscale Constructors
   *
   * @param grayOpacityTuple
   * @param name
   *
   * @return
   */
  @inline
  def apply(
      grayOpacityTuple: (Int, Int),
      name: Option[String]): Color = {

    apply(
      grayOpacityTuple._1,
      grayOpacityTuple._2,
      name)
  }

  /**
   *
   *
   * @group RGBA Constructors
   *
   * @param red
   * @param green
   * @param blue
   * @param name
   *
   * @return
   */
  @inline
  def apply(
      red: Int,
      green: Int,
      blue: Int,
      name: Option[String]): Color = {

    instantiateColor(
      red, green, blue,
      ColorValidator.MaximumOpacity,
      name)
  }

  /**
   *
   *
   * @group RGBA Constructors
   *
   * @param red
   * @param green
   * @param blue
   * @param name
   *
   * @return
   */
  @inline
  def apply(
      red: Double,
      green: Double,
      blue: Double,
      name: Option[String]): Color = {

    apply(
      red.toInt, green.toInt, blue.toInt,
      name)
  }

  /**
   *
   *
   * @group RGBA Constructors
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  @inline
  def apply(
      red: Int,
      green: Int,
      blue: Int): Color = {

    instantiateColor(
      red, green, blue,
      ColorValidator.MaximumOpacity)
  }

  /**
   *
   *
   * @group RGBA Constructors
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  @inline
  def apply(
      red: Double,
      green: Double,
      blue: Double): Color = {

    apply(red.toInt, green.toInt, blue.toInt)
  }

  /**
   *
   *
   * @group RGBA Constructors
   *
   * @param rgbTuple
   * @param name
   *
   * @return
   */
  @inline
  def apply(
      rgbTuple: (Int, Int, Int),
      name: Option[String]): Color = {

    apply(
      rgbTuple._1, rgbTuple._2, rgbTuple._3,
      name)
  }

  /**
   *
   *
   * @group RGBA Constructors
   *
   * @param rgbTuple
   *
   * @return
   */
  @inline
  def apply(rgbTuple: (Int, Int, Int)): Color =
    apply(rgbTuple._1, rgbTuple._2, rgbTuple._3)

  /**
   *
   *
   * @group RGBA Constructors
   *
   * @param rgbTuple
   * @param opacity
   * @param name
   *
   * @return
   */
  @inline
  def apply(
      rgbTuple: (Int, Int, Int),
      opacity: Int,
      name: Option[String]): Color = {

    instantiateColor(
      rgbTuple._1, rgbTuple._2, rgbTuple._3,
      opacity,
      name)
  }

  /**
   *
   *
   * @group RGBA Constructors
   *
   * @param rgbTuple
   * @param opacity
   *
   * @return
   */
  @inline
  def apply(
      rgbTuple: (Int, Int, Int),
      opacity: Int): Color = {

    instantiateColor(
      rgbTuple._1, rgbTuple._2, rgbTuple._3,
      opacity)
  }

  /**
   *
   *
   * @group Grayscale Constructors
   *
   * @param gray
   * @param opacity
   *
   * @return
   */
  @inline
  def apply(
      gray: Int,
      opacity: Int): Color = {

    instantiateColor(
      gray, gray, gray,
      opacity)
  }

  /**
   *
   *
   * @group Grayscale Constructors
   *
   * @param gray
   * @param opacity
   *
   * @return
   */
  @inline
  def apply(
      gray: Double,
      opacity: Double): Color = {

    apply(gray.toInt, opacity.toInt)
  }

  /**
   *
   *
   * @group Grayscale Constructors
   *
   * @param grayOpacityTuple
   *
   * @return
   */
  @inline
  def apply(grayOpacityTuple: (Int, Int)): Color =
    apply(grayOpacityTuple._1, grayOpacityTuple._2)

  /** */
  private
  val ValidHexStringLengths: Seq[Int] = (1 to 3) :+ 6

  /**
   *
   *
   * @group RGBA Constructors
   *
   * @param rgbHexString
   * @param opacity
   *
   * @return
   */
  @inline
  def apply(
      rgbHexString: String,
      opacity: Int): Color = {

    require(rgbHexString != null, "RGB hex string cannot be null")

    // TODO: Create validators and SMCL exceptions for the checks below

    require(ValidHexStringLengths.contains(rgbHexString.length),
      "Length of the RGB hex string must be either 1, 2, 3, or 6 characters")

    require(rgbHexString.containsOnlyHexCharsWithoutPrefixChars,
      "The RGB hex string can contain only characters 0-9 and A-F, ignoring character case")

    val (redChars, greenChars, blueChars) =
      if (rgbHexString.length <= 2) {
        val g =
          if (rgbHexString.length == 1)
            "0" + rgbHexString
          else
            rgbHexString

        (g, g, g)
      }
      else if (rgbHexString.length == 3) {
        val rcs = rgbHexString.head.toString * 2
        val gcs = rgbHexString.tail.head.toString * 2
        val bcs = rgbHexString.tail.tail.head.toString * 2

        (rcs, gcs, bcs)
      }
      else {
        val rcs = rgbHexString.substring(0, 2)
        val gcs = rgbHexString.substring(2, 4)
        val bcs = rgbHexString.substring(4)

        (rcs, gcs, bcs)
      }

    val hexRadix = 16
    val red = Integer.parseInt(redChars, hexRadix)
    val green = Integer.parseInt(greenChars, hexRadix)
    val blue = Integer.parseInt(blueChars, hexRadix)

    instantiateColor(
      red, green, blue,
      opacity)
  }

  /**
   *
   *
   * @group RGBA Constructors
   *
   * @param rgbHexString
   *
   * @return
   */
  @inline
  def apply(rgbHexString: String): Color =
    apply(
      rgbHexString,
      ColorValidator.MaximumOpacity)


  /**
   *
   *
   * @group ARGB Integer Constructors
   *
   * @param argbInt
   * @param name
   *
   * @return
   */
  @inline
  def apply(
      argbInt: Int,
      name: Option[String]): Color = {

    apply(rgbaTupleFrom(argbInt), name)
  }

  /**
   *
   *
   * @group ARGB Integer Constructors
   *
   * @param argbInt
   *
   * @return
   */
  @inline
  def apply(argbInt: Int): Color = Color(rgbaTupleFrom(argbInt))

  /**
   *
   *
   * @group Constructors accepting platform colors
   *
   * @param platformColor
   *
   * @return
   */
  @inline
  def apply(platformColor: ColorAdapter): Color =
    instantiateColor(
      platformColor.red,
      platformColor.green,
      platformColor.blue,
      platformColor.opacity)

  /**
   *
   *
   * @group Constructors accepting platform colors
   *
   * @param platformColor
   * @param name
   *
   * @return
   */
  @inline
  def apply(
      platformColor: ColorAdapter,
      name: Option[String]): Color = {

    instantiateColor(
      platformColor.red,
      platformColor.green,
      platformColor.blue,
      platformColor.opacity,
      name)
  }

  /**
   *
   *
   * @group HSV Colour Model Constructors
   *
   * @param hueInDegrees
   * @param saturation
   * @param value
   *
   * @return
   */
  @inline
  def fromHSV(
      hueInDegrees: Double,
      saturation: Double,
      value: Double): Color = {

    fromHSV(
      hueInDegrees, saturation, value,
      ColorValidator.MaximumOpacity)
  }

  /**
   *
   *
   * @group HSV Colour Model Constructors
   *
   * @param hsvTuple
   *
   * @return
   */
  @inline
  def fromHSV(hsvTuple: (Double, Double, Double)): Color = {
    fromHSV(
      hsvTuple._1, hsvTuple._2, hsvTuple._3,
      ColorValidator.MaximumOpacity)
  }

  /**
   *
   *
   * @group HSV Colour Model Constructors
   *
   * @param hueInDegrees
   * @param saturation
   * @param value
   *
   * @return
   */
  @inline
  def fromHSV(
      hueInDegrees: Double,
      saturation: Double,
      value: Double,
      name: Option[String]): Color = {

    fromHSV(
      hueInDegrees, saturation, value,
      ColorValidator.MaximumOpacity,
      name)
  }

  /**
   *
   *
   * @group HSV Colour Model Constructors
   *
   * @param hsvTuple
   * @param name
   *
   * @return
   */
  @inline
  def fromHSV(
      hsvTuple: (Double, Double, Double),
      name: Option[String]): Color = {

    fromHSV(
      hsvTuple._1, hsvTuple._2, hsvTuple._3,
      ColorValidator.MaximumOpacity,
      name)
  }

  /**
   *
   *
   * @group HSV Colour Model Constructors
   *
   * @param hueInDegrees
   * @param saturation
   * @param value
   * @param opacity
   *
   * @return
   */
  @inline
  def fromHSV(
      hueInDegrees: Double,
      saturation: Double,
      value: Double,
      opacity: Int): Color = {

    apply(
      hsvToRGB(hueInDegrees, saturation, value),
      opacity)
  }

  /**
   *
   *
   * @group HSV Colour Model Constructors
   *
   * @param hsvTuple
   * @param opacity
   *
   * @return
   */
  @inline
  def fromHSV(
      hsvTuple: (Double, Double, Double),
      opacity: Int): Color = {

    fromHSV(
      hsvTuple._1, hsvTuple._2, hsvTuple._3,
      opacity)
  }

  /**
   *
   *
   * @group HSV Colour Model Constructors
   *
   * @param hueInDegrees
   * @param saturation
   * @param value
   * @param opacity
   *
   * @return
   */
  @inline
  def fromHSV(
      hueInDegrees: Double,
      saturation: Double,
      value: Double,
      opacity: Int,
      name: Option[String]): Color = {

    apply(
      hsvToRGB(hueInDegrees, saturation, value),
      opacity,
      name)
  }

  /**
   *
   *
   * @group HSV Colour Model Constructors
   *
   * @param hsvTuple
   * @param opacity
   * @param name
   *
   * @return
   */
  @inline
  def fromHSV(
      hsvTuple: (Double, Double, Double),
      opacity: Int,
      name: Option[String]): Color = {

    fromHSV(
      hsvTuple._1, hsvTuple._2, hsvTuple._3,
      opacity,
      name)
  }

  /**
   *
   *
   * @group HSI Colour Model Constructors
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   *
   * @return
   */
  @inline
  def fromHSI(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double): Color = {

    fromHSI(
      hueInDegrees, saturation, intensity,
      ColorValidator.MaximumOpacity)
  }

  /**
   *
   *
   * @group HSI Colour Model Constructors
   *
   * @param hsiTuple
   *
   * @return
   */
  @inline
  def fromHSI(hsiTuple: (Double, Double, Double)): Color =
    fromHSI(
      hsiTuple._1, hsiTuple._2, hsiTuple._3,
      ColorValidator.MaximumOpacity)

  /**
   *
   *
   * @group HSI Colour Model Constructors
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   *
   * @return
   */
  @inline
  def fromHSI(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double,
      name: Option[String]): Color = {

    fromHSI(
      hueInDegrees, saturation, intensity,
      ColorValidator.MaximumOpacity,
      name)
  }

  /**
   *
   *
   * @group HSI Colour Model Constructors
   *
   * @param hsiTuple
   * @param name
   *
   * @return
   */
  @inline
  def fromHSI(
      hsiTuple: (Double, Double, Double),
      name: Option[String]): Color = {

    fromHSI(
      hsiTuple._1, hsiTuple._2, hsiTuple._3,
      ColorValidator.MaximumOpacity,
      name)
  }

  /**
   *
   *
   * @group HSI Colour Model Constructors
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   * @param opacity
   *
   * @return
   */
  @inline
  def fromHSI(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double,
      opacity: Int): Color = {

    apply(
      hsiToRGB(hueInDegrees, saturation, intensity),
      opacity)
  }

  /**
   *
   *
   * @group HSI Colour Model Constructors
   *
   * @param hsiTuple
   * @param opacity
   *
   * @return
   */
  @inline
  def fromHSI(
      hsiTuple: (Double, Double, Double),
      opacity: Int): Color = {

    fromHSI(
      hsiTuple._1, hsiTuple._2, hsiTuple._3,
      opacity)
  }

  /**
   *
   *
   * @group HSI Colour Model Constructors
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   * @param opacity
   *
   * @return
   */
  @inline
  def fromHSI(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double,
      opacity: Int,
      name: Option[String]): Color = {

    apply(
      hsiToRGB(hueInDegrees, saturation, intensity),
      opacity,
      name)
  }

  /**
   *
   *
   * @group HSI Colour Model Constructors
   *
   * @param hsiTuple
   * @param opacity
   * @param name
   *
   * @return
   */
  @inline
  def fromHSI(
      hsiTuple: (Double, Double, Double),
      opacity: Int,
      name: Option[String]): Color = {

    fromHSI(
      hsiTuple._1, hsiTuple._2, hsiTuple._3,
      opacity,
      name)
  }

  /**
   *
   *
   * @param color
   *
   * @return
   */
  @inline
  def unapply(color: Color): Option[(Int, Int, Int, Int)] = {
    if (color == null)
      return None

    Some((color.red, color.green, color.blue, color.opacity))
  }

}




/**
 * An RGBA color, i.e., represents a color defined by portions of red, green, and blue
 * colors as well as by the opacity (a.k.a. alpha) of the color. It is also possible to
 * give each color a name.
 *
 * Each of the color components, i.e., red, green, blue, and opacity, are defined as integers
 * with the range of 0 to 255.
 *
 * In addition to the possibility to create custom colors with this class, there is also
 * a selection of preset colors available. Those are defined by instances of the [[PresetColor]]
 * class and stored into the [[PresetColors]] trait.
 *
 * @constructor To be instantiated by using the [[Color]] companion object.
 * @see PresetColor
 * @see PresetColors
 *
 * @param red
 * @param green
 * @param blue
 * @param opacity
 * @param canonicalName
 * @param commonValidators
 * @param colorValidator
 *
 * @author Aleksi Lukkarinen
 */
class Color protected[smcl](
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

  /** This [[Color]] coded into an [[Int]]. */
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
  lazy val toColorComponentMap: Map[Symbol, Double] =
    colorComponentMapFrom(this)

  /** */
  lazy val toNormalizedRGBAComponents: (Double, Double, Double, Double) =
    normalizeRGBA(red, green, blue, opacity)

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
  lazy val keepingOnlyRedComponent: Color =
    Color.instantiateColor(
      red,
      ColorValidator.MinimumGreen,
      ColorValidator.MinimumBlue,
      ColorValidator.FullyOpaque)

  /** */
  lazy val keepingOnlyGreenComponent: Color =
    Color.instantiateColor(
      ColorValidator.MinimumRed,
      green,
      ColorValidator.MinimumBlue,
      ColorValidator.FullyOpaque)

  /** */
  lazy val keepingOnlyBlueComponent: Color =
    Color.instantiateColor(
      ColorValidator.MinimumRed,
      ColorValidator.MinimumGreen,
      blue,
      ColorValidator.FullyOpaque)

  /** */
  lazy val keepingOnlyRedAndGreenComponents: Color =
    Color.instantiateColor(
      red,
      green,
      ColorValidator.MinimumBlue,
      ColorValidator.FullyOpaque)

  /** */
  lazy val keepingOnlyRedAndBlueComponents: Color = {
    Color.instantiateColor(
      red,
      ColorValidator.MinimumGreen,
      blue,
      ColorValidator.FullyOpaque)
  }

  /** */
  lazy val keepingOnlyGreenAndBlueComponents: Color =
    Color.instantiateColor(
      ColorValidator.MinimumRed,
      green,
      blue,
      ColorValidator.FullyOpaque)

  /** */
  lazy val representingOpacityAsGreyLevel: Color =
    Color.instantiateColor(
      opacity,
      opacity,
      opacity,
      ColorValidator.FullyOpaque)

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

    Color.instantiateColor(
      newRed, newGreen, newBlue,
      newOpacity)
  }

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toString: String =
    s"Color(red: $red, green: $green, blue: $blue, opacity: $opacity)"


  ///////////////////////////////////////////////////////////////////////////////////
  //
  //
  //
  //  COLOR COMPARISONS
  //
  //
  //
  ///////////////////////////////////////////////////////////////////////////////////

  /**
   *
   *
   * @group Comparing Colors
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
   * @group Comparing Colors
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
   * @group Comparing Colors
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
   * @group Comparing Colors
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
   * @group Comparing Colors
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
   * @group Comparing Colors
   *
   * @param that
   *
   * @return
   */
  @inline
  override
  def compare(that: Color): Int =
    Math.signum(that.toHSIHueInDegrees - this.toHSIHueInDegrees).toInt

  /**
   *
   *
   * @group Comparing Colors
   *
   * @param that
   *
   * @return
   */
  @inline
  def compareByHSISaturation(that: Color): Int =
    Math.signum(that.toHSISaturation - this.toHSISaturation).toInt

  /**
   *
   *
   * @group Comparing Colors
   *
   * @param that
   *
   * @return
   */
  @inline
  def compareByHSIIntensity(that: Color): Int =
    Math.signum(that.toHSIIntensity - this.toHSIIntensity).toInt


  ///////////////////////////////////////////////////////////////////////////////////
  //
  //
  //
  //  COLOR MIXING
  //
  //
  //
  ///////////////////////////////////////////////////////////////////////////////////

  /**
   *
   *
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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

    Color.instantiateColor(
      resultRed, resultGreen, resultBlue,
      resultOpacity)
  }

  /**
   *
   *
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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
   * @group Mixing Colors
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


  ///////////////////////////////////////////////////////////////////////////////////
  //
  //
  //
  //  REDNESS TRANSFORMATIONS
  //
  //
  //
  ///////////////////////////////////////////////////////////////////////////////////

  /**
   *
   *
   * @param newAbsoluteRednessInPercents
   */
  final def withAbsoluteRednessPercentage(newAbsoluteRednessInPercents: Double): Color = {
    commonValidators.validatePercentage(newAbsoluteRednessInPercents, Option("Redness"))

    withAbsoluteRednessFactor(newAbsoluteRednessInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteRednessFactorFromZeroToOne
   */
  final def withAbsoluteRednessFactor(newAbsoluteRednessFactorFromZeroToOne: Double): Color = {
    commonValidators.validateZeroToOneFactor(newAbsoluteRednessFactorFromZeroToOne, Option("Redness"))

    withAbsoluteRedness((newAbsoluteRednessFactorFromZeroToOne * ColorValidator.MaximumRed).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteRedness
   */
  final def withAbsoluteRedness(newAbsoluteRedness: Int): Color = {
    colorValidator.validateRedComponent(newAbsoluteRedness)

    Color(newAbsoluteRedness, green, blue, opacity)
  }

  /**
   *
   *
   * @param rednessIncrementInPercents
   */
  final def increaseRednessByPercentage(rednessIncrementInPercents: Double): Color = {
    commonValidators.validatePercentage(rednessIncrementInPercents, Option("Redness increment"))

    increaseRednessByFactor(rednessIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param rednessIncrementFactorFromZeroToOne
   */
  final def increaseRednessByFactor(rednessIncrementFactorFromZeroToOne: Double): Color = {
    commonValidators.validateZeroToOneFactor(rednessIncrementFactorFromZeroToOne, Option("Redness increment"))

    val newRed = (red + rednessIncrementFactorFromZeroToOne * (ColorValidator.MaximumRed - red)).toInt

    Color(newRed, green, blue, opacity)
  }

  /**
   *
   *
   * @param rednessDecrementInPercents
   */
  final def decreaseRednessByPercentage(rednessDecrementInPercents: Double): Color = {
    commonValidators.validatePercentage(rednessDecrementInPercents, Option("Redness decrement"))

    decreaseRednessByFactor(rednessDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param rednessDecrementFactorFromZeroToOne
   */
  final def decreaseRednessByFactor(rednessDecrementFactorFromZeroToOne: Double): Color = {
    commonValidators.validateZeroToOneFactor(rednessDecrementFactorFromZeroToOne, Option("Redness decrement"))

    val invertedFactor: Double = 1.0 - rednessDecrementFactorFromZeroToOne
    val newRed = (invertedFactor * red).toInt

    Color(newRed, green, blue, opacity)
  }


  ///////////////////////////////////////////////////////////////////////////////////
  //
  //
  //
  //  GREENNESS TRANSFORMATIONS
  //
  //
  //
  ///////////////////////////////////////////////////////////////////////////////////

  /**
   *
   *
   * @param newAbsoluteGreennessInPercents
   */
  final def withAbsoluteGreennessPercentage(newAbsoluteGreennessInPercents: Double): Color = {
    commonValidators.validatePercentage(newAbsoluteGreennessInPercents, Option("Greenness"))

    withAbsoluteGreennessFactor(newAbsoluteGreennessInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteGreennessFactorFromZeroToOne
   */
  final def withAbsoluteGreennessFactor(newAbsoluteGreennessFactorFromZeroToOne: Double): Color = {
    commonValidators.validateZeroToOneFactor(newAbsoluteGreennessFactorFromZeroToOne, Option("Greenness"))

    withAbsoluteGreenness((newAbsoluteGreennessFactorFromZeroToOne * ColorValidator.MaximumGreen).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteGreenness
   */
  final def withAbsoluteGreenness(newAbsoluteGreenness: Int): Color = {
    colorValidator.validateGreenComponent(newAbsoluteGreenness)

    Color(red, newAbsoluteGreenness, blue, opacity)
  }

  /**
   *
   *
   * @param greennessIncrementInPercents
   */
  final def increaseGreennessByPercentage(greennessIncrementInPercents: Double): Color = {
    commonValidators.validatePercentage(greennessIncrementInPercents, Option("Greenness increment"))

    increaseGreennessByFactor(greennessIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param greennessIncrementFactorFromZeroToOne
   */
  final def increaseGreennessByFactor(greennessIncrementFactorFromZeroToOne: Double): Color = {
    commonValidators.validateZeroToOneFactor(greennessIncrementFactorFromZeroToOne, Option("Greenness increment"))

    val newGreen = (green + greennessIncrementFactorFromZeroToOne * (ColorValidator.MaximumGreen - green)).toInt

    Color(red, newGreen, blue, opacity)
  }

  /**
   *
   *
   * @param greennessDecrementInPercents
   */
  final def decreaseGreennessByPercentage(greennessDecrementInPercents: Double): Color = {
    commonValidators.validatePercentage(greennessDecrementInPercents, Option("Greenness decrement"))

    decreaseGreennessByFactor(greennessDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param greennessDecrementFactorFromZeroToOne
   */
  final def decreaseGreennessByFactor(greennessDecrementFactorFromZeroToOne: Double): Color = {
    commonValidators.validateZeroToOneFactor(greennessDecrementFactorFromZeroToOne, Option("Greenness decrement"))

    val invertedFactor: Double = 1.0 - greennessDecrementFactorFromZeroToOne
    val newGreen = (invertedFactor * green).toInt

    Color(red, newGreen, blue, opacity)
  }


  ///////////////////////////////////////////////////////////////////////////////////
  //
  //
  //
  //  BLUENESS TRANSFORMATIONS
  //
  //
  //
  ///////////////////////////////////////////////////////////////////////////////////

  /**
   *
   *
   * @param newAbsoluteBluenessInPercents
   */
  final def withAbsoluteBluenessPercentage(newAbsoluteBluenessInPercents: Double): Color = {
    commonValidators.validatePercentage(newAbsoluteBluenessInPercents, Option("Blueness"))

    withAbsoluteBluenessFactor(newAbsoluteBluenessInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteBluenessFactorFromZeroToOne
   */
  final def withAbsoluteBluenessFactor(newAbsoluteBluenessFactorFromZeroToOne: Double): Color = {
    commonValidators.validateZeroToOneFactor(newAbsoluteBluenessFactorFromZeroToOne, Option("Blueness"))

    withAbsoluteBlueness((newAbsoluteBluenessFactorFromZeroToOne * ColorValidator.MaximumBlue).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteBlueness
   */
  final def withAbsoluteBlueness(newAbsoluteBlueness: Int): Color = {
    colorValidator.validateBlueComponent(newAbsoluteBlueness)

    Color(red, green, newAbsoluteBlueness, opacity)
  }

  /**
   *
   *
   * @param bluenessIncrementInPercents
   */
  final def increaseBluenessByPercentage(bluenessIncrementInPercents: Double): Color = {
    commonValidators.validatePercentage(bluenessIncrementInPercents, Option("Blueness increment"))

    increaseBluenessByFactor(bluenessIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param bluenessIncrementFactorFromZeroToOne
   */
  final def increaseBluenessByFactor(bluenessIncrementFactorFromZeroToOne: Double): Color = {
    commonValidators.validateZeroToOneFactor(bluenessIncrementFactorFromZeroToOne, Option("Blueness increment"))

    val newBlue = (blue + bluenessIncrementFactorFromZeroToOne * (ColorValidator.MaximumBlue - blue)).toInt

    Color(red, green, newBlue, opacity)
  }

  /**
   *
   *
   * @param bluenessDecrementInPercents
   */
  final def decreaseBluenessByPercentage(bluenessDecrementInPercents: Double): Color = {
    commonValidators.validatePercentage(bluenessDecrementInPercents, Option("Blueness decrement"))

    decreaseBluenessByFactor(bluenessDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param bluenessDecrementFactorFromZeroToOne
   */
  final def decreaseBluenessByFactor(bluenessDecrementFactorFromZeroToOne: Double): Color = {
    commonValidators.validateZeroToOneFactor(bluenessDecrementFactorFromZeroToOne, Option("Blueness decrement"))

    val invertedFactor: Double = 1.0 - bluenessDecrementFactorFromZeroToOne
    val newBlue = (invertedFactor * blue).toInt

    Color(red, green, newBlue, opacity)
  }


  ///////////////////////////////////////////////////////////////////////////////////
  //
  //
  //
  //  OPACITY TRANSFORMATIONS
  //
  //
  //
  ///////////////////////////////////////////////////////////////////////////////////

  /**
   * Returns a new [[Color]] identical with this one except having full opacity.
   */
  final def withFullOpacity: Color =
    if (isOpaque) this
    else Color(red, green, blue, ColorValidator.MaximumOpacity)

  /**
   * Returns a new [[Color]] identical with this one except having full transparency.
   */
  final def withFullTransparency: Color =
    if (isTransparent) this
    else Color(red, green, blue, ColorValidator.MinimumOpacity)

  /**
   *
   *
   * @param newAbsoluteOpacityInPercents
   */
  final def withAbsoluteOpacityPercentage(newAbsoluteOpacityInPercents: Double): Color = {
    commonValidators.validatePercentage(newAbsoluteOpacityInPercents, Option("Opacity"))

    withAbsoluteOpacityFactor(newAbsoluteOpacityInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteOpacityFactorFromZeroToOne
   */
  final def withAbsoluteOpacityFactor(newAbsoluteOpacityFactorFromZeroToOne: Double): Color = {
    commonValidators.validateZeroToOneFactor(newAbsoluteOpacityFactorFromZeroToOne, Option("Opacity"))

    withAbsoluteOpacity((newAbsoluteOpacityFactorFromZeroToOne * ColorValidator.FullyOpaque).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteOpacity
   */
  final def withAbsoluteOpacity(newAbsoluteOpacity: Int): Color = {
    colorValidator.validateOpacityComponent(newAbsoluteOpacity)

    Color(red, green, blue, newAbsoluteOpacity)
  }

  /**
   *
   *
   * @param opacityIncrementInPercents
   */
  final def increaseOpacityByPercentage(opacityIncrementInPercents: Double): Color = {
    commonValidators.validatePercentage(opacityIncrementInPercents, Option("Opacity increment"))

    increaseOpacityByFactor(opacityIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param opacityIncrementFactorFromZeroToOne
   */
  final def increaseOpacityByFactor(opacityIncrementFactorFromZeroToOne: Double): Color = {
    commonValidators.validateZeroToOneFactor(opacityIncrementFactorFromZeroToOne, Option("Opacity increment"))

    val newOpacity = (opacity + opacityIncrementFactorFromZeroToOne * (ColorValidator.MaximumOpacity - opacity)).toInt

    Color(red, green, blue, newOpacity)
  }

  /**
   *
   *
   * @param opacityDecrementInPercents
   */
  final def decreaseOpacityByPercentage(opacityDecrementInPercents: Double): Color = {
    commonValidators.validatePercentage(opacityDecrementInPercents, Option("Opacity decrement"))

    decreaseOpacityByFactor(opacityDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param opacityDecrementFactorFromZeroToOne
   */
  final def decreaseOpacityByFactor(opacityDecrementFactorFromZeroToOne: Double): Color = {
    commonValidators.validateZeroToOneFactor(opacityDecrementFactorFromZeroToOne, Option("Opacity decrement"))

    val invertedFactor: Double = 1.0 - opacityDecrementFactorFromZeroToOne
    val newOpacity = (invertedFactor * opacity).toInt

    Color(red, green, blue, newOpacity)
  }


  ///////////////////////////////////////////////////////////////////////////////////
  //
  //
  //
  //  TRANSFORMATIONS INVOLVING MULTIPLE COLOR COMPONENTS
  //
  //
  //
  ///////////////////////////////////////////////////////////////////////////////////

  /**
   *
   *
   * @param adjustmentInDegrees
   *
   * @return
   */
  final def adjustHueByDegrees(adjustmentInDegrees: Double): Color =
    adjustHueOfRGBByDegrees(color = this, adjustmentInDegrees)

  /**
   *
   *
   * @param redWeight
   * @param greenWeight
   * @param blueWeight
   *
   * @return
   */
  final def toWeightedGray(
      redWeight: Double = 0.33,
      greenWeight: Double = 0.33,
      blueWeight: Double = 0.33): Color = {

    commonValidators.validateZeroToOneFactor(redWeight, Option("Red weight"))
    commonValidators.validateZeroToOneFactor(greenWeight, Option("Green weight"))
    commonValidators.validateZeroToOneFactor(blueWeight, Option("Blue weight"))

    colorValidator.validateRGBColorWeightCombination(redWeight, greenWeight, blueWeight)

    val weightedRed = redWeight * red
    val weightedGreen = greenWeight * green
    val weightedBlue = blueWeight * blue
    val grayIntensity = (weightedRed + weightedGreen + weightedBlue).toInt.min(ColorValidator.MaximumGray)

    Color(grayIntensity, ColorValidator.FullyOpaque)
  }

  /**
   *
   *
   * @return
   */
  final def darker: Color = shadeByFactor(Color.DefaultShadingFactor)

  /**
   *
   *
   * @param shadingFactorInPercents
   */
  final def shadeByPercentage(shadingFactorInPercents: Double): Color = {
    commonValidators.validatePercentage(shadingFactorInPercents, Option("Shading"))

    shadeByFactor(shadingFactorInPercents / 100)
  }

  /**
   *
   *
   * @param shadingFactorFromZeroToOne
   */
  final def shadeByFactor(shadingFactorFromZeroToOne: Double): Color = {
    commonValidators.validateZeroToOneFactor(shadingFactorFromZeroToOne, Option("Shading"))

    val invertedFactor: Double = 1.0 - shadingFactorFromZeroToOne
    val newRed = (invertedFactor * red).toInt
    val newGreen = (invertedFactor * green).toInt
    val newBlue = (invertedFactor * blue).toInt

    Color(newRed, newGreen, newBlue, opacity)
  }

  /**
   *
   *
   * @return
   */
  final def lighter: Color = tintByFactor(Color.DefaultTintingFactor)

  /**
   *
   *
   * @param tintingFactorInPercents
   */
  final def tintByPercentage(tintingFactorInPercents: Double): Color = {
    commonValidators.validatePercentage(tintingFactorInPercents, Option("Tinting"))

    tintByFactor(tintingFactorInPercents / 100.0)
  }

  /**
   *
   *
   * @param tintingFactorFromZeroToOne
   */
  final def tintByFactor(tintingFactorFromZeroToOne: Double): Color = {
    commonValidators.validateZeroToOneFactor(tintingFactorFromZeroToOne, Option("Tinting"))

    val newRed = (red + tintingFactorFromZeroToOne * (ColorValidator.MaximumRed - red)).ceil.toInt
    val newGreen = (green + tintingFactorFromZeroToOne * (ColorValidator.MaximumGreen - green)).ceil.toInt
    val newBlue = (blue + tintingFactorFromZeroToOne * (ColorValidator.MaximumBlue - blue)).ceil.toInt

    Color(newRed, newGreen, newBlue, opacity)
  }

}
