package aalto.smcl.common




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ColorValidator {

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
   * @return
   */
  @inline
  def rgbRedComponentIsInRange(redCandidate: Int): Boolean =
    redCandidate >= MinimumRgbRed && redCandidate <= MaximumRgbRed

  /**
   *
   *
   * @param greenCandidate
   * @return
   */
  @inline
  def rgbGreenComponentIsInRange(greenCandidate: Int): Boolean =
    greenCandidate >= MinimumRgbGreen && greenCandidate <= MaximumRgbGreen

  /**
   *
   *
   * @param blueCandidate
   * @return
   */
  @inline
  def rgbBlueComponentIsInRange(blueCandidate: Int): Boolean =
    blueCandidate >= MinimumRgbBlue && blueCandidate <= MaximumRgbBlue

  /**
   *
   *
   * @param grayCandidate
   * @return
   */
  @inline
  def rgbGrayComponentIsInRange(grayCandidate: Int): Boolean =
    grayCandidate >= MinimumRgbGray && grayCandidate <= MaximumRgbGray

  /**
   *
   *
   * @param opacityCandidate
   * @return
   */
  @inline
  def rgbaOpacityComponentIsInRange(opacityCandidate: Int): Boolean =
    opacityCandidate >= MinimumRgbaOpacity && opacityCandidate <= MaximumRgbaOpacity

  /**
   *
   *
   * @param redCandidate
   * @return
   */
  @inline
  def rgbNormalizedRedComponentIsInRange(redCandidate: Double): Boolean =
    redCandidate >= MinimumNormalizedRgbRed && redCandidate <= MaximumNormalizedRgbRed

  /**
   *
   *
   * @param greenCandidate
   * @return
   */
  @inline
  def rgbNormalizedGreenComponentIsInRange(greenCandidate: Double): Boolean =
    greenCandidate >= MinimumNormalizedRgbGreen && greenCandidate <= MaximumNormalizedRgbGreen

  /**
   *
   *
   * @param blueCandidate
   * @return
   */
  @inline
  def rgbNormalizedBlueComponentIsInRange(blueCandidate: Double): Boolean =
    blueCandidate >= MinimumNormalizedRgbBlue && blueCandidate <= MaximumNormalizedRgbGreen

  /**
   *
   *
   * @param grayCandidate
   * @return
   */
  @inline
  def rgbNormalizedGrayComponentIsInRange(grayCandidate: Double): Boolean =
    grayCandidate >= MinimumNormalizedRgbGray && grayCandidate <= MaximumNormalizedRgbGray

  /**
   *
   *
   * @param opacityCandidate
   * @return
   */
  @inline
  def rgbaNormalizedOpacityComponentIsInRange(opacityCandidate: Int): Boolean =
    opacityCandidate >= MinimumNormalizedRgbaOpacity && opacityCandidate <= MaximumNormalizedRgbGreen

  /**
   *
   *
   * @param saturationCandidate
   * @return
   */
  @inline
  def hsiSaturationComponentIsInRange(saturationCandidate: Double): Boolean =
    saturationCandidate >= MinimumHsiSaturation && saturationCandidate <= MaximumHsiSaturation

  /**
   *
   *
   * @param intensityCandidate
   * @return
   */
  @inline
  def hsiIntensityComponentIsInRange(intensityCandidate: Double): Boolean =
    intensityCandidate >= MinimumHsiIntensity && intensityCandidate <= MaximumHsiIntensity

  /**
   *
   *
   * @param saturationCandidate
   * @return
   */
  @inline
  def hsvSaturationComponentIsInRange(saturationCandidate: Double): Boolean =
    saturationCandidate >= MinimumHsvSaturation && saturationCandidate <= MaximumHsvSaturation

  /**
   *
   *
   * @param valueCandidate
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
   * @throws SMCLRgbRedComponentOutOfRangeError
   */
  @inline
  def validateRgbRedComponent(redCandidate: Int): Unit = {
    if (!rgbRedComponentIsInRange(redCandidate)) {
      throw new SMCLRgbRedComponentOutOfRangeError(redCandidate)
    }
  }

  /**
   *
   *
   * @param greenCandidate
   *
   * @throws SMCLRgbGreenComponentOutOfRangeError
   */
  @inline
  def validateRgbGreenComponent(greenCandidate: Int): Unit = {
    if (!rgbGreenComponentIsInRange(greenCandidate)) {
      throw new SMCLRgbGreenComponentOutOfRangeError(greenCandidate)
    }
  }

  /**
   *
   *
   * @param blueCandidate
   *
   * @throws SMCLRgbBlueComponentOutOfRangeError
   */
  @inline
  def validateRgbBlueComponent(blueCandidate: Int): Unit = {
    if (!rgbBlueComponentIsInRange(blueCandidate)) {
      throw new SMCLRgbBlueComponentOutOfRangeError(blueCandidate)
    }
  }

  /**
   *
   *
   * @param grayCandidate
   *
   * @throws SMCLRgbGrayComponentOutOfRangeError
   */
  @inline
  def validateRgbGrayComponent(grayCandidate: Int): Unit = {
    if (!rgbGrayComponentIsInRange(grayCandidate)) {
      throw new SMCLRgbGrayComponentOutOfRangeError(grayCandidate)
    }
  }

  /**
   *
   *
   * @param opacityCandidate
   *
   * @throws SMCLRgbaOpacityComponentOutOfRangeError
   */
  @inline
  def validateRgbaOpacityComponent(opacityCandidate: Int): Unit = {
    if (!rgbaOpacityComponentIsInRange(opacityCandidate)) {
      throw new SMCLRgbaOpacityComponentOutOfRangeError(opacityCandidate)
    }
  }

  /**
   *
   *
   * @param saturationCandidate
   *
   * @throws SMCLHsiSaturationComponentOutOfRangeError
   */
  @inline
  def validateHsiSaturationComponent(saturationCandidate: Double): Unit = {
    if (!hsiSaturationComponentIsInRange(saturationCandidate)) {
      throw new SMCLHsiSaturationComponentOutOfRangeError(saturationCandidate)
    }
  }

  /**
   *
   *
   * @param intensityCandidate
   *
   * @throws SMCLHsiIntensityComponentOutOfRangeError
   */
  @inline
  def validateHsiIntensityComponent(intensityCandidate: Double): Unit = {
    if (!hsiIntensityComponentIsInRange(intensityCandidate)) {
      throw new SMCLHsiIntensityComponentOutOfRangeError(intensityCandidate)
    }
  }

  /**
   *
   *
   * @param saturationCandidate
   *
   * @throws SMCLHsvSaturationComponentOutOfRangeError
   */
  @inline
  def validateHsvSaturationComponent(saturationCandidate: Double): Unit = {
    if (!hsvSaturationComponentIsInRange(saturationCandidate)) {
      throw new SMCLHsvSaturationComponentOutOfRangeError(saturationCandidate)
    }
  }

  /**
   *
   *
   * @param valueCandidate
   *
   * @throws SMCLHsvValueComponentOutOfRangeError
   */
  @inline
  def validateHsvValueComponent(valueCandidate: Double): Unit = {
    if (!hsvValueComponentIsInRange(valueCandidate)) {
      throw new SMCLHsvValueComponentOutOfRangeError(valueCandidate)
    }
  }

  /**
   *
   *
   * @param grayCandidate
   *
   * @throws SMCLRgbGrayComponentOutOfRangeError
   */
  @inline
  def validateRgbGray(grayCandidate: Int): Unit = {
    if (!rgbGrayComponentIsInRange(grayCandidate)) {
      throw new SMCLRgbGrayComponentOutOfRangeError(grayCandidate)
    }
  }

  /**
   *
   *
   * @param redCandidate
   * @param greenCandidate
   * @param blueCandidate
   *
   * @throws SMCLRgbRedComponentOutOfRangeError
   * @throws SMCLRgbGreenComponentOutOfRangeError
   * @throws SMCLRgbBlueComponentOutOfRangeError
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
   * @throws SMCLRgbGrayComponentOutOfRangeError
   * @throws SMCLRgbaOpacityComponentOutOfRangeError
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
   * @throws SMCLRgbRedComponentOutOfRangeError
   * @throws SMCLRgbGreenComponentOutOfRangeError
   * @throws SMCLRgbBlueComponentOutOfRangeError
   * @throws SMCLRgbaOpacityComponentOutOfRangeError
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
   * @param hueCandidate
   * @param saturationCandidate
   * @param intensityCandidate
   *
   * @throws SMCLHsiSaturationComponentOutOfRangeError
   * @throws SMCLHsiIntensityComponentOutOfRangeError
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
   * @throws SMCLHsvSaturationComponentOutOfRangeError
   * @throws SMCLHsvValueComponentOutOfRangeError
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
   * @throws SMCLInvalidColorWeightCombinationError
   */
  @inline
  def validateRgbColorWeightCombination(
      redWeightCandidate: Double,
      greenWeightCandidate: Double,
      blueWeightCandidate: Double): Unit = {

    val weightSum = redWeightCandidate + greenWeightCandidate + blueWeightCandidate

    if (weightSum < 0.0) {
      throw new SMCLInvalidColorWeightCombinationError(
        s"The sum of the three weights must be >= 0.0 (was $weightSum)")
    }

    if (weightSum > 1.0) {
      throw new SMCLInvalidColorWeightCombinationError(
        s"The sum of the three weights must be <= 1.0 (was $weightSum)")
    }
  }

  /**
   *
   *
   * @param nameOptionCandidate
   * @return
   */
  def validateColorNameOption(nameOptionCandidate: Option[String]): Option[String] = {
    require(nameOptionCandidate != null, "The name option argument must be Option(<name>) or None (was null).")

    if (nameOptionCandidate.nonEmpty) {
      val name = nameOptionCandidate.get.trim

      require(name != StrEmpty, "The name cannot be empty or contain only whitespace.")

      if (name != nameOptionCandidate.get)
        return Option(name)
    }

    nameOptionCandidate
  }


}
