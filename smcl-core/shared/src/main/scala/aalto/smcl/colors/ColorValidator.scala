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


import aalto.smcl.colors.exceptions._
import aalto.smcl.colors.hsi.exceptions.{IntensityComponentOutOfRangeError, SaturationComponentOutOfRangeError}
import aalto.smcl.colors.hsv.exceptions.{SaturationComponentOutOfRangeError, ValueComponentOutOfRangeError}
import aalto.smcl.colors.rgb.exceptions.{BlueComponentOutOfRangeError, GreenComponentOutOfRangeError, RedComponentOutOfRangeError}
import aalto.smcl.infrastructure._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ColorValidator {

  /** An RGB color component value representing minimal amount of red. */
  val MinimumRed: Int = ByteRange.start

  /** An RGB color component value representing maximal amount of red. */
  val MaximumRed: Int = ByteRange.end

  /** Range for valid values of the red color component of an RGB color. */
  val RedRange = ByteRange

  /** An RGB color component value representing minimal amount of green. */
  val MinimumGreen: Int = ByteRange.start

  /** An RGB color component value representing maximal amount of green. */
  val MaximumGreen: Int = ByteRange.end

  /** Range for valid values of the green color component of an RGB color. */
  val GreenRange = ByteRange

  /** An RGB color component value representing minimal amount of blue. */
  val MinimumBlue: Int = ByteRange.start

  /** An RGB color component value representing maximal amount of blue. */
  val MaximumBlue: Int = ByteRange.end

  /** Range for valid values of the blue color component of an RGB color. */
  val BlueRange = ByteRange

  /** A color component value representing minimal amount of gray. */
  val MinimumGray: Int = ByteRange.start

  /** A color component value representing maximal amount of gray. */
  val MaximumGray: Int = ByteRange.end

  /** Range for valid values of gray color component. */
  val GrayRange = ByteRange

  /** An RGBA color component value representing minimal opacity. */
  val MinimumOpacity: Int = ByteRange.start

  /** An RGBA color component value representing maximal opacity. */
  val MaximumOpacity: Int = ByteRange.end

  /** Range for valid values of the opacity color component of an RGBA color. */
  val OpacityRange = ByteRange

  /** Color component value representing normalized minimal amount of red. */
  val MinimumNormalizedRed: Double = 0.0

  /** Color component value representing normalized maximal amount of red. */
  val MaximumNormalizedRed: Double = 1.0

  /** Color component value representing normalized minimal amount of green. */
  val MinimumNormalizedGreen: Double = 0.0

  /** Color component value representing normalized maximal amount of green. */
  val MaximumNormalizedGreen: Double = 1.0

  /** Color component value representing normalized minimal amount of blue. */
  val MinimumNormalizedBlue: Double = 0.0

  /** Color component value representing normalized maximal amount of blue. */
  val MaximumNormalizedBlue: Double = 1.0

  /** Color component value representing normalized minimal amount of gray. */
  val MinimumNormalizedGray: Double = 0.0

  /** Color component value representing normalized maximal amount of gray. */
  val MaximumNormalizedGray: Double = 1.0

  /** Color component value representing normalized minimal opacity. */
  val MinimumNormalizedOpacity: Double = 0.0

  /** Color component value representing normalized maximal opacity. */
  val MaximumNormalizedOpacity: Double = 1.0

  /** Color component value representing start of the hue cycle. */
  val MinimumHSIHue: Double = 0.0

  /** Color component value representing end of the hue cycle. */
  val MaximumHSIHue: Double = FullCircleInDegrees.toDouble

  /** Color component value representing an undefined hue (at the center of the hue cycle). */
  val UndefinedHSIHue: Double = Double.NaN

  /** Color component value representing minimal amount of saturation. */
  val MinimumHSISaturation: Double = 0.0

  /** Color component value representing maximal amount of saturation. */
  val MaximumHSISaturation: Double = 1.0

  /** Color component value representing minimal amount of intensity. */
  val MinimumHSIIntensity: Double = ByteRange.start.toDouble

  /** Color component value representing maximal amount of intensity. */
  val MaximumHSIIntensity: Double = ByteRange.end.toDouble

  /** Color component value representing start of the hue cycle. */
  val MinimumHSVHue: Double = 0.0

  /** Color component value representing end of the hue cycle. */
  val MaximumHSVHue: Double = FullCircleInDegrees.toDouble

  /** Color component value representing an undefined hue (at the center of the hue cycle). */
  val UndefinedHSVHue: Double = Double.NaN

  /** Color component value representing minimal amount of saturation. */
  val MinimumHSVSaturation: Double = 0.0

  /** Color component value representing maximal amount of saturation. */
  val MaximumHSVSaturation: Double = 1.0

  /** Color component value representing minimal amount of intensity. */
  val MinimumHSVValue: Double = ByteRange.start.toDouble

  /** Color component value representing maximal amount of intensity. */
  val MaximumHSVValue: Double = ByteRange.end.toDouble

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class ColorValidator() {

  /** A shortcut to the companion object. */
  private val Const = ColorValidator

  /**
   *
   *
   * @param redCandidate
   *
   * @return
   */
  @inline
  def redComponentIsInRange(redCandidate: Int): Boolean = {
    Const.RedRange.contains(redCandidate)
  }

  /**
   *
   *
   * @param greenCandidate
   *
   * @return
   */
  @inline
  def greenComponentIsInRange(greenCandidate: Int): Boolean = {
    Const.GreenRange.contains(greenCandidate)
  }

  /**
   *
   *
   * @param blueCandidate
   *
   * @return
   */
  @inline
  def blueComponentIsInRange(blueCandidate: Int): Boolean = {
    Const.BlueRange.contains(blueCandidate)
  }

  /**
   *
   *
   * @param grayCandidate
   *
   * @return
   */
  @inline
  def grayComponentIsInRange(grayCandidate: Int): Boolean = {
    Const.GrayRange.contains(grayCandidate)
  }

  /**
   *
   *
   * @param opacityCandidate
   *
   * @return
   */
  @inline
  def opacityComponentIsInRange(opacityCandidate: Int): Boolean = {
    Const.OpacityRange.contains(opacityCandidate)
  }

  /**
   *
   *
   * @param redCandidate
   *
   * @return
   */
  @inline
  def normalizedRedComponentIsInRange(redCandidate: Double): Boolean = {
    redCandidate >= Const.MinimumNormalizedRed &&
        redCandidate <= Const.MaximumNormalizedRed
  }

  /**
   *
   *
   * @param greenCandidate
   *
   * @return
   */
  @inline
  def normalizedGreenComponentIsInRange(greenCandidate: Double): Boolean = {
    greenCandidate >= Const.MinimumNormalizedGreen &&
        greenCandidate <= Const.MaximumNormalizedGreen
  }

  /**
   *
   *
   * @param blueCandidate
   *
   * @return
   */
  @inline
  def normalizedBlueComponentIsInRange(blueCandidate: Double): Boolean = {
    blueCandidate >= Const.MinimumNormalizedBlue &&
        blueCandidate <= Const.MaximumNormalizedGreen
  }

  /**
   *
   *
   * @param grayCandidate
   *
   * @return
   */
  @inline
  def normalizedGrayComponentIsInRange(grayCandidate: Double): Boolean = {
    grayCandidate >= Const.MinimumNormalizedGray &&
        grayCandidate <= Const.MaximumNormalizedGray
  }

  /**
   *
   *
   * @param opacityCandidate
   *
   * @return
   */
  @inline
  def normalizedOpacityComponentIsInRange(opacityCandidate: Int): Boolean = {
    opacityCandidate >= Const.MinimumNormalizedOpacity &&
        opacityCandidate <= Const.MaximumNormalizedGreen
  }

  /**
   *
   *
   * @param saturationCandidate
   *
   * @return
   */
  @inline
  def hsiSaturationComponentIsInRange(saturationCandidate: Double): Boolean = {
    saturationCandidate >= Const.MinimumHSISaturation &&
        saturationCandidate <= Const.MaximumHSISaturation
  }

  /**
   *
   *
   * @param intensityCandidate
   *
   * @return
   */
  @inline
  def hsiIntensityComponentIsInRange(intensityCandidate: Double): Boolean = {
    intensityCandidate >= Const.MinimumHSIIntensity &&
        intensityCandidate <= Const.MaximumHSIIntensity
  }

  /**
   *
   *
   * @param saturationCandidate
   *
   * @return
   */
  @inline
  def hsvSaturationComponentIsInRange(saturationCandidate: Double): Boolean = {
    saturationCandidate >= Const.MinimumHSVSaturation &&
        saturationCandidate <= Const.MaximumHSVSaturation
  }

  /**
   *
   *
   * @param valueCandidate
   *
   * @return
   */
  @inline
  def hsvValueComponentIsInRange(valueCandidate: Double): Boolean = {
    valueCandidate >= Const.MinimumHSVValue &&
        valueCandidate <= Const.MaximumHSVValue
  }

  /**
   *
   *
   * @param redCandidate
   *
   * @throws RedComponentOutOfRangeError
   */
  @inline
  def validateRedComponent(redCandidate: Int): Unit = {
    if (!redComponentIsInRange(redCandidate)) {
      throw RedComponentOutOfRangeError(redCandidate,
        Const.MinimumRed, Const.MaximumRed)
    }
  }

  /**
   *
   *
   * @param greenCandidate
   *
   * @throws GreenComponentOutOfRangeError
   */
  @inline
  def validateGreenComponent(greenCandidate: Int): Unit = {
    if (!greenComponentIsInRange(greenCandidate)) {
      throw GreenComponentOutOfRangeError(greenCandidate,
        Const.MinimumGreen, Const.MaximumGreen)
    }
  }

  /**
   *
   *
   * @param blueCandidate
   *
   * @throws BlueComponentOutOfRangeError
   */
  @inline
  def validateBlueComponent(blueCandidate: Int): Unit = {
    if (!blueComponentIsInRange(blueCandidate)) {
      throw BlueComponentOutOfRangeError(blueCandidate,
        Const.MinimumBlue, Const.MaximumBlue)
    }
  }

  /**
   *
   *
   * @param grayCandidate
   *
   * @throws GrayComponentOutOfRangeError
   */
  @inline
  def validateRGBGrayComponent(grayCandidate: Int): Unit = {
    if (!grayComponentIsInRange(grayCandidate)) {
      throw GrayComponentOutOfRangeError(grayCandidate,
        Const.MinimumGray, Const.MaximumGray)
    }
  }

  /**
   *
   *
   * @param opacityCandidate
   *
   * @throws OpacityComponentOutOfRangeError
   */
  @inline
  def validateOpacityComponent(opacityCandidate: Int): Unit = {
    if (!opacityComponentIsInRange(opacityCandidate)) {
      throw OpacityComponentOutOfRangeError(opacityCandidate,
        Const.MinimumOpacity, Const.MaximumOpacity)
    }
  }

  /**
   *
   *
   * @param saturationCandidate
   *
   * @throws SaturationComponentOutOfRangeError
   */
  @inline
  def validateHSISaturationComponent(saturationCandidate: Double): Unit = {
    if (!hsiSaturationComponentIsInRange(saturationCandidate)) {
      throw hsi.exceptions.SaturationComponentOutOfRangeError(saturationCandidate,
        Const.MinimumHSISaturation, Const.MaximumHSISaturation)
    }
  }

  /**
   *
   *
   * @param intensityCandidate
   *
   * @throws IntensityComponentOutOfRangeError
   */
  @inline
  def validateHSIIntensityComponent(intensityCandidate: Double): Unit = {
    if (!hsiIntensityComponentIsInRange(intensityCandidate)) {
      throw IntensityComponentOutOfRangeError(intensityCandidate,
        Const.MinimumHSIIntensity, Const.MaximumHSIIntensity)
    }
  }

  /**
   *
   *
   * @param saturationCandidate
   *
   * @throws SaturationComponentOutOfRangeError
   */
  @inline
  def validateHSVSaturationComponent(saturationCandidate: Double): Unit = {
    if (!hsvSaturationComponentIsInRange(saturationCandidate)) {
      throw hsv.exceptions.SaturationComponentOutOfRangeError(saturationCandidate,
        Const.MinimumHSVSaturation, Const.MaximumHSVSaturation)
    }
  }

  /**
   *
   *
   * @param valueCandidate
   *
   * @throws ValueComponentOutOfRangeError
   */
  @inline
  def validateHSVValueComponent(valueCandidate: Double): Unit = {
    if (!hsvValueComponentIsInRange(valueCandidate)) {
      throw ValueComponentOutOfRangeError(valueCandidate,
        Const.MinimumHSVValue, Const.MaximumHSVValue)
    }
  }

  /**
   *
   *
   * @param grayCandidate
   *
   * @throws GrayComponentOutOfRangeError
   */
  @inline
  def validateGray(grayCandidate: Int): Unit = {
    if (!grayComponentIsInRange(grayCandidate)) {
      throw GrayComponentOutOfRangeError(grayCandidate,
        Const.MinimumGray, Const.MaximumGray)
    }
  }

  /**
   *
   *
   * @param redCandidate
   * @param greenCandidate
   * @param blueCandidate
   *
   * @throws RedComponentOutOfRangeError
   * @throws GreenComponentOutOfRangeError
   * @throws BlueComponentOutOfRangeError
   */
  @inline
  def validateRGBColor(redCandidate: Int, greenCandidate: Int, blueCandidate: Int): Unit = {
    validateRedComponent(redCandidate)
    validateGreenComponent(greenCandidate)
    validateBlueComponent(blueCandidate)
  }

  /**
   *
   *
   * @param grayCandidate
   *
   * @throws GrayComponentOutOfRangeError
   * @throws OpacityComponentOutOfRangeError
   */
  @inline
  def validateRGBGrayAndOpacity(grayCandidate: Int, opacityCandidate: Int): Unit = {
    validateRGBGrayComponent(grayCandidate)
    validateOpacityComponent(opacityCandidate)
  }

  /**
   *
   *
   * @param redCandidate
   * @param greenCandidate
   * @param blueCandidate
   *
   * @throws RedComponentOutOfRangeError
   * @throws GreenComponentOutOfRangeError
   * @throws BlueComponentOutOfRangeError
   * @throws OpacityComponentOutOfRangeError
   */
  @inline
  def validateRGBAColor(
      redCandidate: Int,
      greenCandidate: Int,
      blueCandidate: Int,
      opacityCandidate: Int): Unit = {

    validateRGBColor(redCandidate, greenCandidate, blueCandidate)
    validateOpacityComponent(opacityCandidate)
  }

  /**
   *
   *
   * @param rgbaTuple
   *
   * @throws RedComponentOutOfRangeError
   * @throws GreenComponentOutOfRangeError
   * @throws BlueComponentOutOfRangeError
   * @throws OpacityComponentOutOfRangeError
   */
  //noinspection ScalaUnnecessaryParentheses
  def validateRGBAColor(rgbaTuple: (Int, Int, Int, Int)): Unit = {
    (validateRGBAColor(_: Int, _: Int, _: Int, _: Int)).tupled.apply(rgbaTuple)
  }

  /**
   *
   *
   * @param hueCandidate
   * @param saturationCandidate
   * @param intensityCandidate
   *
   * @throws SaturationComponentOutOfRangeError
   * @throws IntensityComponentOutOfRangeError
   */
  @inline
  def validateHSIColor(
      hueCandidate: Double,
      saturationCandidate: Double,
      intensityCandidate: Double): Unit = {

    validateHSISaturationComponent(saturationCandidate)
    validateHSIIntensityComponent(intensityCandidate)
  }

  /**
   *
   *
   * @param hueCandidate
   * @param saturationCandidate
   * @param valueCandidate
   *
   * @throws SaturationComponentOutOfRangeError
   * @throws ValueComponentOutOfRangeError
   */
  @inline
  def validateHSVColor(
      hueCandidate: Double,
      saturationCandidate: Double,
      valueCandidate: Double): Unit = {

    validateHSVSaturationComponent(saturationCandidate)
    validateHSVValueComponent(valueCandidate)
  }

  /**
   *
   *
   * @param redWeightCandidate
   * @param greenWeightCandidate
   * @param blueWeightCandidate
   *
   * @throws InvalidColorWeightCombinationError
   */
  @inline
  def validateRGBColorWeightCombination(
      redWeightCandidate: Double,
      greenWeightCandidate: Double,
      blueWeightCandidate: Double): Unit = {

    val weightSum = redWeightCandidate + greenWeightCandidate + blueWeightCandidate

    if (weightSum < 0.0) {
      throw InvalidColorWeightCombinationError(
        s"The sum of the three weights must be >= 0.0 (was $weightSum)")
    }

    if (weightSum > 1.0) {
      throw InvalidColorWeightCombinationError(
        s"The sum of the three weights must be <= 1.0 (was $weightSum)")
    }
  }

  /**
   *
   *
   * @param nameCandidate
   *
   * @return
   */
  @inline
  def validateCanonicalColorName(nameCandidate: Option[String]): Option[String] = {
    if (nameCandidate == null)
      throw ColorNameIsNullError

    if (nameCandidate.nonEmpty) {
      val name = nameCandidate.get.trim

      if (name.isEmpty)
        throw ColorNameIsEmptyOrOnlyWhitespaceError

      val capitalizedName = name.toAmericanTitleCase
      if (capitalizedName != nameCandidate.get)
        return Option(capitalizedName)
    }

    nameCandidate
  }

}
