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
import aalto.smcl.infrastructure._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ColorValidator {

  /** An RGB color component value representing minimal amount of red. */
  val MinimumRGBRed: Int = ByteRange.start

  /** An RGB color component value representing maximal amount of red. */
  val MaximumRGBRed: Int = ByteRange.end

  /** Range for valid values of the red color component of an RGB color. */
  val rgbRedRange = ByteRange

  /** An RGB color component value representing minimal amount of green. */
  val MinimumRGBGreen: Int = ByteRange.start

  /** An RGB color component value representing maximal amount of green. */
  val MaximumRGBGreen: Int = ByteRange.end

  /** Range for valid values of the green color component of an RGB color. */
  val rgbGreenRange = ByteRange

  /** An RGB color component value representing minimal amount of blue. */
  val MinimumRGBBlue: Int = ByteRange.start

  /** An RGB color component value representing maximal amount of blue. */
  val MaximumRGBBlue: Int = ByteRange.end

  /** Range for valid values of the blue color component of an RGB color. */
  val rgbBlueRange = ByteRange

  /** A color component value representing minimal amount of gray. */
  val MinimumRGBGray: Int = ByteRange.start

  /** A color component value representing maximal amount of gray. */
  val MaximumRGBGray: Int = ByteRange.end

  /** Range for valid values of gray color component. */
  val rgbGrayRange = ByteRange

  /** An RGBA color component value representing minimal opacity. */
  val MinimumRGBAOpacity: Int = ByteRange.start

  /** An RGBA color component value representing maximal opacity. */
  val MaximumRGBAOpacity: Int = ByteRange.end

  /** Range for valid values of the opacity color component of an RGBA color. */
  val rgbaOpacityRange = ByteRange

  /** Color component value representing normalized minimal amount of red. */
  val MinimumNormalizedRGBRed: Double = 0.0

  /** Color component value representing normalized maximal amount of red. */
  val MaximumNormalizedRGBRed: Double = 1.0

  /** Color component value representing normalized minimal amount of green. */
  val MinimumNormalizedRGBGreen: Double = 0.0

  /** Color component value representing normalized maximal amount of green. */
  val MaximumNormalizedRGBGreen: Double = 1.0

  /** Color component value representing normalized minimal amount of blue. */
  val MinimumNormalizedRGBBlue: Double = 0.0

  /** Color component value representing normalized maximal amount of blue. */
  val MaximumNormalizedRGBBlue: Double = 1.0

  /** Color component value representing normalized minimal amount of gray. */
  val MinimumNormalizedRGBGray: Double = 0.0

  /** Color component value representing normalized maximal amount of gray. */
  val MaximumNormalizedRGBGray: Double = 1.0

  /** Color component value representing normalized minimal opacity. */
  val MinimumNormalizedRGBAOpacity: Double = 0.0

  /** Color component value representing normalized maximal opacity. */
  val MaximumNormalizedRGBAOpacity: Double = 1.0

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

  /**
   *
   *
   * @param redCandidate
   *
   * @return
   */
  @inline
  def rgbRedComponentIsInRange(redCandidate: Int): Boolean = {
    ColorValidator.rgbRedRange.contains(redCandidate)
  }

  /**
   *
   *
   * @param greenCandidate
   *
   * @return
   */
  @inline
  def rgbGreenComponentIsInRange(greenCandidate: Int): Boolean = {
    ColorValidator.rgbGreenRange.contains(greenCandidate)
  }

  /**
   *
   *
   * @param blueCandidate
   *
   * @return
   */
  @inline
  def rgbBlueComponentIsInRange(blueCandidate: Int): Boolean = {
    ColorValidator.rgbBlueRange.contains(blueCandidate)
  }

  /**
   *
   *
   * @param grayCandidate
   *
   * @return
   */
  @inline
  def rgbGrayComponentIsInRange(grayCandidate: Int): Boolean = {
    ColorValidator.rgbGrayRange.contains(grayCandidate)
  }

  /**
   *
   *
   * @param opacityCandidate
   *
   * @return
   */
  @inline
  def rgbaOpacityComponentIsInRange(opacityCandidate: Int): Boolean = {
    ColorValidator.rgbaOpacityRange.contains(opacityCandidate)
  }

  /**
   *
   *
   * @param redCandidate
   *
   * @return
   */
  @inline
  def rgbNormalizedRedComponentIsInRange(redCandidate: Double): Boolean = {
    redCandidate >= ColorValidator.MinimumNormalizedRGBRed &&
        redCandidate <= ColorValidator.MaximumNormalizedRGBRed
  }

  /**
   *
   *
   * @param greenCandidate
   *
   * @return
   */
  @inline
  def rgbNormalizedGreenComponentIsInRange(greenCandidate: Double): Boolean = {
    greenCandidate >= ColorValidator.MinimumNormalizedRGBGreen &&
        greenCandidate <= ColorValidator.MaximumNormalizedRGBGreen
  }

  /**
   *
   *
   * @param blueCandidate
   *
   * @return
   */
  @inline
  def rgbNormalizedBlueComponentIsInRange(blueCandidate: Double): Boolean = {
    blueCandidate >= ColorValidator.MinimumNormalizedRGBBlue &&
        blueCandidate <= ColorValidator.MaximumNormalizedRGBGreen
  }

  /**
   *
   *
   * @param grayCandidate
   *
   * @return
   */
  @inline
  def rgbNormalizedGrayComponentIsInRange(grayCandidate: Double): Boolean = {
    grayCandidate >= ColorValidator.MinimumNormalizedRGBGray &&
        grayCandidate <= ColorValidator.MaximumNormalizedRGBGray
  }

  /**
   *
   *
   * @param opacityCandidate
   *
   * @return
   */
  @inline
  def rgbaNormalizedOpacityComponentIsInRange(opacityCandidate: Int): Boolean = {
    opacityCandidate >= ColorValidator.MinimumNormalizedRGBAOpacity &&
        opacityCandidate <= ColorValidator.MaximumNormalizedRGBGreen
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
    saturationCandidate >= ColorValidator.MinimumHSISaturation &&
        saturationCandidate <= ColorValidator.MaximumHSISaturation
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
    intensityCandidate >= ColorValidator.MinimumHSIIntensity &&
        intensityCandidate <= ColorValidator.MaximumHSIIntensity
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
    saturationCandidate >= ColorValidator.MinimumHSVSaturation &&
        saturationCandidate <= ColorValidator.MaximumHSVSaturation
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
    valueCandidate >= ColorValidator.MinimumHSVValue &&
        valueCandidate <= ColorValidator.MaximumHSVValue
  }

  /**
   *
   *
   * @param redCandidate
   *
   * @throws RGBRedComponentOutOfRangeError
   */
  @inline
  def validateRGBRedComponent(redCandidate: Int): Unit = {
    if (!rgbRedComponentIsInRange(redCandidate)) {
      throw RGBRedComponentOutOfRangeError(redCandidate,
        ColorValidator.MinimumRGBRed, ColorValidator.MaximumRGBRed)
    }
  }

  /**
   *
   *
   * @param greenCandidate
   *
   * @throws RGBGreenComponentOutOfRangeError
   */
  @inline
  def validateRGBGreenComponent(greenCandidate: Int): Unit = {
    if (!rgbGreenComponentIsInRange(greenCandidate)) {
      throw RGBGreenComponentOutOfRangeError(greenCandidate,
        ColorValidator.MinimumRGBGreen, ColorValidator.MaximumRGBGreen)
    }
  }

  /**
   *
   *
   * @param blueCandidate
   *
   * @throws RGBBlueComponentOutOfRangeError
   */
  @inline
  def validateRGBBlueComponent(blueCandidate: Int): Unit = {
    if (!rgbBlueComponentIsInRange(blueCandidate)) {
      throw RGBBlueComponentOutOfRangeError(blueCandidate,
        ColorValidator.MinimumRGBBlue, ColorValidator.MaximumRGBBlue)
    }
  }

  /**
   *
   *
   * @param grayCandidate
   *
   * @throws RGBGrayComponentOutOfRangeError
   */
  @inline
  def validateRGBGrayComponent(grayCandidate: Int): Unit = {
    if (!rgbGrayComponentIsInRange(grayCandidate)) {
      throw RGBGrayComponentOutOfRangeError(grayCandidate,
        ColorValidator.MinimumRGBGray, ColorValidator.MaximumRGBGray)
    }
  }

  /**
   *
   *
   * @param opacityCandidate
   *
   * @throws RGBAOpacityComponentOutOfRangeError
   */
  @inline
  def validateRGBAOpacityComponent(opacityCandidate: Int): Unit = {
    if (!rgbaOpacityComponentIsInRange(opacityCandidate)) {
      throw RGBAOpacityComponentOutOfRangeError(opacityCandidate,
        ColorValidator.MinimumRGBAOpacity, ColorValidator.MaximumRGBAOpacity)
    }
  }

  /**
   *
   *
   * @param saturationCandidate
   *
   * @throws HSISaturationComponentOutOfRangeError
   */
  @inline
  def validateHSISaturationComponent(saturationCandidate: Double): Unit = {
    if (!hsiSaturationComponentIsInRange(saturationCandidate)) {
      throw HSISaturationComponentOutOfRangeError(saturationCandidate,
        ColorValidator.MinimumHSISaturation, ColorValidator.MaximumHSISaturation)
    }
  }

  /**
   *
   *
   * @param intensityCandidate
   *
   * @throws HSIIntensityComponentOutOfRangeError
   */
  @inline
  def validateHSIIntensityComponent(intensityCandidate: Double): Unit = {
    if (!hsiIntensityComponentIsInRange(intensityCandidate)) {
      throw HSIIntensityComponentOutOfRangeError(intensityCandidate,
        ColorValidator.MinimumHSIIntensity, ColorValidator.MaximumHSIIntensity)
    }
  }

  /**
   *
   *
   * @param saturationCandidate
   *
   * @throws HSVSaturationComponentOutOfRangeError
   */
  @inline
  def validateHSVSaturationComponent(saturationCandidate: Double): Unit = {
    if (!hsvSaturationComponentIsInRange(saturationCandidate)) {
      throw HSVSaturationComponentOutOfRangeError(saturationCandidate,
        ColorValidator.MinimumHSVSaturation, ColorValidator.MaximumHSVSaturation)
    }
  }

  /**
   *
   *
   * @param valueCandidate
   *
   * @throws HSVValueComponentOutOfRangeError
   */
  @inline
  def validateHSVValueComponent(valueCandidate: Double): Unit = {
    if (!hsvValueComponentIsInRange(valueCandidate)) {
      throw HSVValueComponentOutOfRangeError(valueCandidate,
        ColorValidator.MinimumHSVValue, ColorValidator.MaximumHSVValue)
    }
  }

  /**
   *
   *
   * @param grayCandidate
   *
   * @throws RGBGrayComponentOutOfRangeError
   */
  @inline
  def validateRGBGray(grayCandidate: Int): Unit = {
    if (!rgbGrayComponentIsInRange(grayCandidate)) {
      throw RGBGrayComponentOutOfRangeError(grayCandidate,
        ColorValidator.MinimumRGBGray, ColorValidator.MaximumRGBGray)
    }
  }

  /**
   *
   *
   * @param redCandidate
   * @param greenCandidate
   * @param blueCandidate
   *
   * @throws RGBRedComponentOutOfRangeError
   * @throws RGBGreenComponentOutOfRangeError
   * @throws RGBBlueComponentOutOfRangeError
   */
  @inline
  def validateRGBColor(redCandidate: Int, greenCandidate: Int, blueCandidate: Int): Unit = {
    validateRGBRedComponent(redCandidate)
    validateRGBGreenComponent(greenCandidate)
    validateRGBBlueComponent(blueCandidate)
  }

  /**
   *
   *
   * @param grayCandidate
   *
   * @throws RGBGrayComponentOutOfRangeError
   * @throws RGBAOpacityComponentOutOfRangeError
   */
  @inline
  def validateRGBGrayAndOpacity(grayCandidate: Int, opacityCandidate: Int): Unit = {
    validateRGBGrayComponent(grayCandidate)
    validateRGBAOpacityComponent(opacityCandidate)
  }

  /**
   *
   *
   * @param redCandidate
   * @param greenCandidate
   * @param blueCandidate
   *
   * @throws RGBRedComponentOutOfRangeError
   * @throws RGBGreenComponentOutOfRangeError
   * @throws RGBBlueComponentOutOfRangeError
   * @throws RGBAOpacityComponentOutOfRangeError
   */
  @inline
  def validateRGBAColor(
      redCandidate: Int,
      greenCandidate: Int,
      blueCandidate: Int,
      opacityCandidate: Int): Unit = {

    validateRGBColor(redCandidate, greenCandidate, blueCandidate)
    validateRGBAOpacityComponent(opacityCandidate)
  }

  /**
   *
   *
   * @param rgbaTuple
   *
   * @throws RGBRedComponentOutOfRangeError
   * @throws RGBGreenComponentOutOfRangeError
   * @throws RGBBlueComponentOutOfRangeError
   * @throws RGBAOpacityComponentOutOfRangeError
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
   * @throws HSISaturationComponentOutOfRangeError
   * @throws HSIIntensityComponentOutOfRangeError
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
   * @throws HSVSaturationComponentOutOfRangeError
   * @throws HSVValueComponentOutOfRangeError
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
