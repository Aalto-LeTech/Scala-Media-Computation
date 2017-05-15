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
class ColorValidator() {

  /** Color component value representing minimal amount of red. */
  val MinimumRgbRed: Int = ByteRange.start

  /** Color component value representing maximal amount of red. */
  val MaximumRgbRed: Int = ByteRange.end

  /** Color component value representing minimal amount of green. */
  val MinimumRgbGreen: Int = ByteRange.start

  /** Color component value representing maximal amount of green. */
  val MaximumRgbGreen: Int = ByteRange.end

  /** Color component value representing minimal amount of blue. */
  val MinimumRgbBlue: Int = ByteRange.start

  /** Color component value representing maximal amount of blue. */
  val MaximumRgbBlue: Int = ByteRange.end

  /** Color component value representing minimal amount of gray. */
  val MinimumRgbGray: Int = ByteRange.start

  /** Color component value representing maximal amount of gray. */
  val MaximumRgbGray: Int = ByteRange.end

  /** Color component value representing minimal opacity. */
  val MinimumRgbaOpacity: Int = ByteRange.start

  /** Color component value representing maximal opacity. */
  val MaximumRgbaOpacity: Int = ByteRange.end

  /** Color component value representing normalized minimal amount of red. */
  val MinimumNormalizedRgbRed: Double = 0.0

  /** Color component value representing normalized maximal amount of red. */
  val MaximumNormalizedRgbRed: Double = 1.0

  /** Color component value representing normalized minimal amount of green. */
  val MinimumNormalizedRgbGreen: Double = 0.0

  /** Color component value representing normalized maximal amount of green. */
  val MaximumNormalizedRgbGreen: Double = 1.0

  /** Color component value representing normalized minimal amount of blue. */
  val MinimumNormalizedRgbBlue: Double = 0.0

  /** Color component value representing normalized maximal amount of blue. */
  val MaximumNormalizedRgbBlue: Double = 1.0

  /** Color component value representing normalized minimal amount of gray. */
  val MinimumNormalizedRgbGray: Double = 0.0

  /** Color component value representing normalized maximal amount of gray. */
  val MaximumNormalizedRgbGray: Double = 1.0

  /** Color component value representing normalized minimal opacity. */
  val MinimumNormalizedRgbaOpacity: Double = 0.0

  /** Color component value representing normalized maximal opacity. */
  val MaximumNormalizedRgbaOpacity: Double = 1.0

  /** Color component value representing start of the hue cycle. */
  val MinimumHsiHue: Double = 0.0

  /** Color component value representing end of the hue cycle. */
  val MaximumHsiHue: Double = FullCircleInDegrees.toDouble

  /** Color component value representing an undefined hue (at the center of the hue cycle). */
  val UndefinedHsiHue: Double = Double.NaN

  /** Color component value representing minimal amount of saturation. */
  val MinimumHsiSaturation: Double = 0.0

  /** Color component value representing maximal amount of saturation. */
  val MaximumHsiSaturation: Double = 1.0

  /** Color component value representing minimal amount of intensity. */
  val MinimumHsiIntensity: Double = ByteRange.start.toDouble

  /** Color component value representing maximal amount of intensity. */
  val MaximumHsiIntensity: Double = ByteRange.end.toDouble

  /** Color component value representing start of the hue cycle. */
  val MinimumHsvHue: Double = 0.0

  /** Color component value representing end of the hue cycle. */
  val MaximumHsvHue: Double = FullCircleInDegrees.toDouble

  /** Color component value representing an undefined hue (at the center of the hue cycle). */
  val UndefinedHsvHue: Double = Double.NaN

  /** Color component value representing minimal amount of saturation. */
  val MinimumHsvSaturation: Double = 0.0

  /** Color component value representing maximal amount of saturation. */
  val MaximumHsvSaturation: Double = 1.0

  /** Color component value representing minimal amount of intensity. */
  val MinimumHsvValue: Double = ByteRange.start.toDouble

  /** Color component value representing maximal amount of intensity. */
  val MaximumHsvValue: Double = ByteRange.end.toDouble

  /**
   *
   *
   * @param redCandidate
   *
   * @return
   */
  @inline
  def rgbRedComponentIsInRange(redCandidate: Int): Boolean =
  redCandidate >= MinimumRgbRed && redCandidate <= MaximumRgbRed

  /**
   *
   *
   * @param greenCandidate
   *
   * @return
   */
  @inline
  def rgbGreenComponentIsInRange(greenCandidate: Int): Boolean =
  greenCandidate >= MinimumRgbGreen && greenCandidate <= MaximumRgbGreen

  /**
   *
   *
   * @param blueCandidate
   *
   * @return
   */
  @inline
  def rgbBlueComponentIsInRange(blueCandidate: Int): Boolean =
  blueCandidate >= MinimumRgbBlue && blueCandidate <= MaximumRgbBlue

  /**
   *
   *
   * @param grayCandidate
   *
   * @return
   */
  @inline
  def rgbGrayComponentIsInRange(grayCandidate: Int): Boolean =
  grayCandidate >= MinimumRgbGray && grayCandidate <= MaximumRgbGray

  /**
   *
   *
   * @param opacityCandidate
   *
   * @return
   */
  @inline
  def rgbaOpacityComponentIsInRange(opacityCandidate: Int): Boolean =
  opacityCandidate >= MinimumRgbaOpacity && opacityCandidate <= MaximumRgbaOpacity

  /**
   *
   *
   * @param redCandidate
   *
   * @return
   */
  @inline
  def rgbNormalizedRedComponentIsInRange(redCandidate: Double): Boolean =
  redCandidate >= MinimumNormalizedRgbRed && redCandidate <= MaximumNormalizedRgbRed

  /**
   *
   *
   * @param greenCandidate
   *
   * @return
   */
  @inline
  def rgbNormalizedGreenComponentIsInRange(greenCandidate: Double): Boolean =
  greenCandidate >= MinimumNormalizedRgbGreen && greenCandidate <= MaximumNormalizedRgbGreen

  /**
   *
   *
   * @param blueCandidate
   *
   * @return
   */
  @inline
  def rgbNormalizedBlueComponentIsInRange(blueCandidate: Double): Boolean =
  blueCandidate >= MinimumNormalizedRgbBlue && blueCandidate <= MaximumNormalizedRgbGreen

  /**
   *
   *
   * @param grayCandidate
   *
   * @return
   */
  @inline
  def rgbNormalizedGrayComponentIsInRange(grayCandidate: Double): Boolean =
  grayCandidate >= MinimumNormalizedRgbGray && grayCandidate <= MaximumNormalizedRgbGray

  /**
   *
   *
   * @param opacityCandidate
   *
   * @return
   */
  @inline
  def rgbaNormalizedOpacityComponentIsInRange(opacityCandidate: Int): Boolean =
  opacityCandidate >= MinimumNormalizedRgbaOpacity && opacityCandidate <= MaximumNormalizedRgbGreen

  /**
   *
   *
   * @param saturationCandidate
   *
   * @return
   */
  @inline
  def hsiSaturationComponentIsInRange(saturationCandidate: Double): Boolean =
  saturationCandidate >= MinimumHsiSaturation && saturationCandidate <= MaximumHsiSaturation

  /**
   *
   *
   * @param intensityCandidate
   *
   * @return
   */
  @inline
  def hsiIntensityComponentIsInRange(intensityCandidate: Double): Boolean =
  intensityCandidate >= MinimumHsiIntensity && intensityCandidate <= MaximumHsiIntensity

  /**
   *
   *
   * @param saturationCandidate
   *
   * @return
   */
  @inline
  def hsvSaturationComponentIsInRange(saturationCandidate: Double): Boolean =
  saturationCandidate >= MinimumHsvSaturation && saturationCandidate <= MaximumHsvSaturation

  /**
   *
   *
   * @param valueCandidate
   *
   * @return
   */
  @inline
  def hsvValueComponentIsInRange(valueCandidate: Double): Boolean =
  valueCandidate >= MinimumHsvValue && valueCandidate <= MaximumHsvValue

  /**
   *
   *
   * @param redCandidate
   *
   * @throws RGBRedComponentOutOfRangeError
   */
  @inline
  def validateRgbRedComponent(redCandidate: Int): Unit = {
    if (!rgbRedComponentIsInRange(redCandidate)) {
      throw RGBRedComponentOutOfRangeError(
        redCandidate, MinimumRgbRed, MaximumRgbRed)
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
  def validateRgbGreenComponent(greenCandidate: Int): Unit = {
    if (!rgbGreenComponentIsInRange(greenCandidate)) {
      throw RGBGreenComponentOutOfRangeError(
        greenCandidate, MinimumRgbGreen, MaximumRgbGreen)
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
  def validateRgbBlueComponent(blueCandidate: Int): Unit = {
    if (!rgbBlueComponentIsInRange(blueCandidate)) {
      throw RGBBlueComponentOutOfRangeError(
        blueCandidate, MinimumRgbBlue, MaximumRgbBlue)
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
  def validateRgbGrayComponent(grayCandidate: Int): Unit = {
    if (!rgbGrayComponentIsInRange(grayCandidate)) {
      throw RGBGrayComponentOutOfRangeError(
        grayCandidate, MinimumRgbGray, MaximumRgbGray)
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
  def validateRgbaOpacityComponent(opacityCandidate: Int): Unit = {
    if (!rgbaOpacityComponentIsInRange(opacityCandidate)) {
      throw RGBAOpacityComponentOutOfRangeError(
        opacityCandidate, MinimumRgbaOpacity, MaximumRgbaOpacity)
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
  def validateHsiSaturationComponent(saturationCandidate: Double): Unit = {
    if (!hsiSaturationComponentIsInRange(saturationCandidate)) {
      throw HSISaturationComponentOutOfRangeError(
        saturationCandidate, MinimumHsiSaturation, MaximumHsiSaturation)
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
  def validateHsiIntensityComponent(intensityCandidate: Double): Unit = {
    if (!hsiIntensityComponentIsInRange(intensityCandidate)) {
      throw HSIIntensityComponentOutOfRangeError(
        intensityCandidate, MinimumHsiIntensity, MaximumHsiIntensity)
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
  def validateHsvSaturationComponent(saturationCandidate: Double): Unit = {
    if (!hsvSaturationComponentIsInRange(saturationCandidate)) {
      throw HSVSaturationComponentOutOfRangeError(
        saturationCandidate, MinimumHsvSaturation, MaximumHsvSaturation)
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
  def validateHsvValueComponent(valueCandidate: Double): Unit = {
    if (!hsvValueComponentIsInRange(valueCandidate)) {
      throw HSVValueComponentOutOfRangeError(
        valueCandidate, MinimumHsvValue, MaximumHsvValue)
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
  def validateRgbGray(grayCandidate: Int): Unit = {
    if (!rgbGrayComponentIsInRange(grayCandidate)) {
      throw RGBGrayComponentOutOfRangeError(
        grayCandidate, MinimumRgbGray, MaximumRgbGray)
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
  def validateRgbColor(redCandidate: Int, greenCandidate: Int, blueCandidate: Int): Unit = {
    validateRgbRedComponent(redCandidate)
    validateRgbGreenComponent(greenCandidate)
    validateRgbBlueComponent(blueCandidate)
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
  def validateRgbGrayAndOpacity(grayCandidate: Int, opacityCandidate: Int): Unit = {
    validateRgbGrayComponent(grayCandidate)
    validateRgbaOpacityComponent(opacityCandidate)
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
  def validateRgbaColor(
      redCandidate: Int,
      greenCandidate: Int,
      blueCandidate: Int,
      opacityCandidate: Int): Unit = {

    validateRgbColor(redCandidate, greenCandidate, blueCandidate)
    validateRgbaOpacityComponent(opacityCandidate)
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
  def validateRgbaColor(rgbaTuple: (Int, Int, Int, Int)): Unit =
  (validateRgbaColor(_: Int, _: Int, _: Int, _: Int)).tupled.apply(rgbaTuple)

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
  def validateHsiColor(
      hueCandidate: Double,
      saturationCandidate: Double,
      intensityCandidate: Double): Unit = {

    validateHsiSaturationComponent(saturationCandidate)
    validateHsiIntensityComponent(intensityCandidate)
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
  def validateHsvColor(
      hueCandidate: Double,
      saturationCandidate: Double,
      valueCandidate: Double): Unit = {

    validateHsvSaturationComponent(saturationCandidate)
    validateHsvValueComponent(valueCandidate)
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
  def validateRgbColorWeightCombination(
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
  def validateColorNameOption(nameCandidate: Option[String]): Option[String] = {
    if (nameCandidate == null)
      throw ColorNameIsNullError

    if (nameCandidate.nonEmpty) {
      val name = nameCandidate.get.trim

      if (name.isEmpty)
        throw ColorNameIsEmptyOrOnlyWhitespaceError

      if (name != nameCandidate.get)
        return Option(name)
    }

    nameCandidate
  }

}
