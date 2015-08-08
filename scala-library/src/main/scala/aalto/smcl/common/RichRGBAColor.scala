package aalto.smcl.common


/**
 *
 *
 * @param self
 *
 * @author Aleksi Lukkarinen
 */
class RichRGBAColor(val self: RGBAColor) {


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
  def withAbsoluteRednessPercentage(newAbsoluteRednessInPercents: Double): RGBAColor = {
    require(newAbsoluteRednessInPercents >= 0.0 && newAbsoluteRednessInPercents <= 100.0,
      s"Redness percentage must be a Double between 0.0 and 100.0 (was $newAbsoluteRednessInPercents)")

    withAbsoluteRednessFactor(newAbsoluteRednessInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteRednessFactorFromZeroToOne
   */
  def withAbsoluteRednessFactor(newAbsoluteRednessFactorFromZeroToOne: Double): RGBAColor = {
    require(newAbsoluteRednessFactorFromZeroToOne >= 0.0 && newAbsoluteRednessFactorFromZeroToOne <= 1.0,
      s"Redness factor must be a Double between 0.0 and 1.0 (was $newAbsoluteRednessFactorFromZeroToOne)")

    withAbsoluteRedness((newAbsoluteRednessFactorFromZeroToOne * MaximumRed).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteRedness
   */
  def withAbsoluteRedness(newAbsoluteRedness: Int): RGBAColor = {
    require(newAbsoluteRedness >= MinimumRed && newAbsoluteRedness <= MaximumRed,
      s"Redness must be an Int between $MinimumRed and $MaximumRed (was $newAbsoluteRedness)")

    RGBAColor(newAbsoluteRedness, self.green, self.blue, self.opacity)
  }

  /**
   *
   *
   * @param rednessIncrementInPercents
   */
  def increaseRednessByPercentage(rednessIncrementInPercents: Double): RGBAColor = {
    require(rednessIncrementInPercents >= 0.0 && rednessIncrementInPercents <= 100.0,
      s"The redness increment percentage must be a Double between 0.0 and 100.0 (was $rednessIncrementInPercents)")

    increaseRednessByFactor(rednessIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param rednessIncrementFactorFromZeroToOne
   */
  def increaseRednessByFactor(rednessIncrementFactorFromZeroToOne: Double): RGBAColor = {
    require(rednessIncrementFactorFromZeroToOne >= 0.0 && rednessIncrementFactorFromZeroToOne <= 100.0,
      s"The redness increment factor must be a Double between 0.0 and 1.0 (was $rednessIncrementFactorFromZeroToOne)")

    val newRed = (self.red + rednessIncrementFactorFromZeroToOne * (MaximumRed - self.red)).toInt

    RGBAColor(newRed, self.green, self.blue, self.opacity)
  }

  /**
   *
   *
   * @param rednessDecrementInPercents
   */
  def decreaseRednessByPercentage(rednessDecrementInPercents: Double): RGBAColor = {
    require(rednessDecrementInPercents >= 0.0 && rednessDecrementInPercents <= 100.0,
      s"The redness decrement percentage must be a Double between 0.0 and 100.0 (was $rednessDecrementInPercents)")

    decreaseRednessByFactor(rednessDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param rednessDecrementFactorFromZeroToOne
   */
  def decreaseRednessByFactor(rednessDecrementFactorFromZeroToOne: Double): RGBAColor = {
    require(rednessDecrementFactorFromZeroToOne >= 0.0 && rednessDecrementFactorFromZeroToOne <= 1.0,
      s"The redness decrement factor must be a Double between 0.0 and 1.0 (was $rednessDecrementFactorFromZeroToOne)")

    val invertedFactor: Double = 1.0 - rednessDecrementFactorFromZeroToOne
    val newRed = (invertedFactor * self.red).toInt

    RGBAColor(newRed, self.green, self.blue, self.opacity)
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
  def withAbsoluteGreennessPercentage(newAbsoluteGreennessInPercents: Double): RGBAColor = {
    require(newAbsoluteGreennessInPercents >= 0.0 && newAbsoluteGreennessInPercents <= 100.0,
      s"Greenness percentage must be a Double between 0.0 and 100.0 (was $newAbsoluteGreennessInPercents)")

    withAbsoluteGreennessFactor(newAbsoluteGreennessInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteGreennessFactorFromZeroToOne
   */
  def withAbsoluteGreennessFactor(newAbsoluteGreennessFactorFromZeroToOne: Double): RGBAColor = {
    require(newAbsoluteGreennessFactorFromZeroToOne >= 0.0 && newAbsoluteGreennessFactorFromZeroToOne <= 1.0,
      s"Greenness factor must be a Double between 0.0 and 1.0 (was $newAbsoluteGreennessFactorFromZeroToOne)")

    withAbsoluteGreenness((newAbsoluteGreennessFactorFromZeroToOne * MaximumGreen).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteGreenness
   */
  def withAbsoluteGreenness(newAbsoluteGreenness: Int): RGBAColor = {
    require(newAbsoluteGreenness >= MinimumGreen && newAbsoluteGreenness <= MaximumGreen,
      s"Greenness must be an Int between $MinimumGreen and $MaximumGreen (was $newAbsoluteGreenness)")

    RGBAColor(self.red, newAbsoluteGreenness, self.blue, self.opacity)
  }

  /**
   *
   *
   * @param greennessIncrementInPercents
   */
  def increaseGreennessByPercentage(greennessIncrementInPercents: Double): RGBAColor = {
    require(greennessIncrementInPercents >= 0.0 && greennessIncrementInPercents <= 100.0,
      s"The greenness increment percentage must be a Double between 0.0 and 100.0 (was $greennessIncrementInPercents)")

    increaseGreennessByFactor(greennessIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param greennessIncrementFactorFromZeroToOne
   */
  def increaseGreennessByFactor(greennessIncrementFactorFromZeroToOne: Double): RGBAColor = {
    require(greennessIncrementFactorFromZeroToOne >= 0.0 && greennessIncrementFactorFromZeroToOne <= 100.0,
      s"The redness increment factor must be a Double between 0.0 and 1.0 (was $greennessIncrementFactorFromZeroToOne)")

    val newGreen = (self.green + greennessIncrementFactorFromZeroToOne * (MaximumGreen - self.green)).toInt

    RGBAColor(self.red, newGreen, self.blue, self.opacity)
  }

  /**
   *
   *
   * @param greennessDecrementInPercents
   */
  def decreaseGreennessByPercentage(greennessDecrementInPercents: Double): RGBAColor = {
    require(greennessDecrementInPercents >= 0.0 && greennessDecrementInPercents <= 100.0,
      s"The greenness decrement percentage must be a Double between 0.0 and 100.0 (was $greennessDecrementInPercents)")

    decreaseGreennessByFactor(greennessDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param greennessDecrementFactorFromZeroToOne
   */
  def decreaseGreennessByFactor(greennessDecrementFactorFromZeroToOne: Double): RGBAColor = {
    require(greennessDecrementFactorFromZeroToOne >= 0.0 && greennessDecrementFactorFromZeroToOne <= 1.0,
      s"The greenness decrement factor must be a Double between 0.0 and 1.0 (was $greennessDecrementFactorFromZeroToOne)")

    val invertedFactor: Double = 1.0 - greennessDecrementFactorFromZeroToOne
    val newGreen = (invertedFactor * self.green).toInt

    RGBAColor(self.red, newGreen, self.blue, self.opacity)
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
  def withAbsoluteBluenessPercentage(newAbsoluteBluenessInPercents: Double): RGBAColor = {
    require(newAbsoluteBluenessInPercents >= 0.0 && newAbsoluteBluenessInPercents <= 100.0,
      s"Blueness percentage must be a Double between 0.0 and 100.0 (was $newAbsoluteBluenessInPercents)")

    withAbsoluteBluenessFactor(newAbsoluteBluenessInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteBluenessFactorFromZeroToOne
   */
  def withAbsoluteBluenessFactor(newAbsoluteBluenessFactorFromZeroToOne: Double): RGBAColor = {
    require(newAbsoluteBluenessFactorFromZeroToOne >= 0.0 && newAbsoluteBluenessFactorFromZeroToOne <= 1.0,
      s"Blueness factor must be a Double between 0.0 and 1.0 (was $newAbsoluteBluenessFactorFromZeroToOne)")

    withAbsoluteBlueness((newAbsoluteBluenessFactorFromZeroToOne * MaximumBlue).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteBlueness
   */
  def withAbsoluteBlueness(newAbsoluteBlueness: Int): RGBAColor = {
    require(newAbsoluteBlueness >= MinimumBlue && newAbsoluteBlueness <= MaximumBlue,
      s"Blueness must be an Int between $MinimumBlue and $MaximumBlue (was $newAbsoluteBlueness)")

    RGBAColor(self.red, self.green, newAbsoluteBlueness, self.opacity)
  }

  /**
   *
   *
   * @param bluenessIncrementInPercents
   */
  def increaseBluenessByPercentage(bluenessIncrementInPercents: Double): RGBAColor = {
    require(bluenessIncrementInPercents >= 0.0 && bluenessIncrementInPercents <= 100.0,
      s"The blueness increment percentage must be a Double between 0.0 and 100.0 (was $bluenessIncrementInPercents)")

    increaseBluenessByFactor(bluenessIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param bluenessIncrementFactorFromZeroToOne
   */
  def increaseBluenessByFactor(bluenessIncrementFactorFromZeroToOne: Double): RGBAColor = {
    require(bluenessIncrementFactorFromZeroToOne >= 0.0 && bluenessIncrementFactorFromZeroToOne <= 100.0,
      s"The blueness increment factor must be a Double between 0.0 and 1.0 (was $bluenessIncrementFactorFromZeroToOne)")

    val newBlue = (self.blue + bluenessIncrementFactorFromZeroToOne * (MaximumBlue - self.blue)).toInt

    RGBAColor(self.red, self.green, newBlue, self.opacity)
  }

  /**
   *
   *
   * @param bluenessDecrementInPercents
   */
  def decreaseBluenessByPercentage(bluenessDecrementInPercents: Double): RGBAColor = {
    require(bluenessDecrementInPercents >= 0.0 && bluenessDecrementInPercents <= 100.0,
      s"The blueness decrement percentage must be a Double between 0.0 and 100.0 (was $bluenessDecrementInPercents)")

    decreaseBluenessByFactor(bluenessDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param bluenessDecrementFactorFromZeroToOne
   */
  def decreaseBluenessByFactor(bluenessDecrementFactorFromZeroToOne: Double): RGBAColor = {
    require(bluenessDecrementFactorFromZeroToOne >= 0.0 && bluenessDecrementFactorFromZeroToOne <= 1.0,
      s"The blueness decrement factor must be a Double between 0.0 and 1.0 (was $bluenessDecrementFactorFromZeroToOne)")

    val invertedFactor: Double = 1.0 - bluenessDecrementFactorFromZeroToOne
    val newBlue = (invertedFactor * self.blue).toInt

    RGBAColor(self.red, self.green, newBlue, self.opacity)
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
   * Returns a new [[RGBAColor]] identical with this one except having full opacity.
   */
  def withFullOpacity: RGBAColor =
    if (self.isOpaque) self
    else RGBAColor(self.red, self.green, self.blue, MaximumOpacity)

  /**
   * Returns a new [[RGBAColor]] identical with this one except having full transparency.
   */
  def withFullTransparency: RGBAColor =
    if (self.isTransparent) self
    else RGBAColor(self.red, self.green, self.blue, MinimumOpacity)

  /**
   *
   *
   * @param newAbsoluteOpacityInPercents
   */
  def withAbsoluteOpacityPercentage(newAbsoluteOpacityInPercents: Double): RGBAColor = {
    require(newAbsoluteOpacityInPercents >= 0.0 && newAbsoluteOpacityInPercents <= 100.0,
      s"Opacity percentage must be a Double between 0.0 and 100.0 (was $newAbsoluteOpacityInPercents)")

    withAbsoluteOpacityFactor(newAbsoluteOpacityInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteOpacityFactorFromZeroToOne
   */
  def withAbsoluteOpacityFactor(newAbsoluteOpacityFactorFromZeroToOne: Double): RGBAColor = {
    require(newAbsoluteOpacityFactorFromZeroToOne >= 0.0 && newAbsoluteOpacityFactorFromZeroToOne <= 1.0,
      s"Opacity factor must be a Double between 0.0 and 1.0 (was $newAbsoluteOpacityFactorFromZeroToOne)")

    withAbsoluteOpacity((newAbsoluteOpacityFactorFromZeroToOne * FullyOpaque).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteOpacity
   */
  def withAbsoluteOpacity(newAbsoluteOpacity: Int): RGBAColor = {
    require(newAbsoluteOpacity >= MinimumOpacity && newAbsoluteOpacity <= MaximumOpacity,
      s"Opacity must be an Int between $MinimumOpacity and $MaximumOpacity (was $newAbsoluteOpacity)")

    RGBAColor(self.red, self.green, self.blue, newAbsoluteOpacity)
  }

  /**
   *
   *
   * @param opacityIncrementInPercents
   */
  def increaseOpacityByPercentage(opacityIncrementInPercents: Double): RGBAColor = {
    require(opacityIncrementInPercents >= 0.0 && opacityIncrementInPercents <= 100.0,
      s"The opacity increment percentage must be a Double between 0.0 and 100.0 (was $opacityIncrementInPercents)")

    increaseOpacityByFactor(opacityIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param opacityIncrementFactorFromZeroToOne
   */
  def increaseOpacityByFactor(opacityIncrementFactorFromZeroToOne: Double): RGBAColor = {
    require(opacityIncrementFactorFromZeroToOne >= 0.0 && opacityIncrementFactorFromZeroToOne <= 100.0,
      s"The opacity increment factor must be a Double between 0.0 and 1.0 (was $opacityIncrementFactorFromZeroToOne)")

    val newOpacity = (self.opacity + opacityIncrementFactorFromZeroToOne * (MaximumOpacity - self.opacity)).toInt

    RGBAColor(self.red, self.green, self.blue, newOpacity)
  }

  /**
   *
   *
   * @param opacityDecrementInPercents
   */
  def decreaseOpacityByPercentage(opacityDecrementInPercents: Double): RGBAColor = {
    require(opacityDecrementInPercents >= 0.0 && opacityDecrementInPercents <= 100.0,
      s"The opacity decrement percentage must be a Double between 0.0 and 100.0 (was $opacityDecrementInPercents)")

    decreaseOpacityByFactor(opacityDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param opacityDecrementFactorFromZeroToOne
   */
  def decreaseOpacityByFactor(opacityDecrementFactorFromZeroToOne: Double): RGBAColor = {
    require(opacityDecrementFactorFromZeroToOne >= 0.0 && opacityDecrementFactorFromZeroToOne <= 1.0,
      s"The opacity decrement factor must be a Double between 0.0 and 1.0 (was $opacityDecrementFactorFromZeroToOne)")

    val invertedFactor: Double = 1.0 - opacityDecrementFactorFromZeroToOne
    val newOpacity = (invertedFactor * self.opacity).toInt

    RGBAColor(self.red, self.green, self.blue, newOpacity)
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
   * @param redWeight
   * @param greenWeight
   * @param blueWeight
   * @return
   */
  def toWeightedGray(
    redWeight: Double = 0.33,
    greenWeight: Double = 0.33,
    blueWeight: Double = 0.33): RGBAColor = {

    require(redWeight >= 0 && greenWeight >= 0 && blueWeight >= 0,
      s"Each of the weights must be >= 0 (were $redWeight, $greenWeight, $blueWeight)")

    val weightSum = redWeight + greenWeight + blueWeight
    require(weightSum <= 1.0, s"The sum of the three weights must be <= 1 (was $weightSum)")

    val weightedRed = redWeight * self.red
    val weightedGreen = greenWeight * self.green
    val weightedBlue = blueWeight * self.blue
    val grayIntensity = (weightedRed + weightedGreen + weightedBlue).toInt.min(MaximumGray)

    RGBAColor(grayIntensity, FullyOpaque)
  }

  /**
   *
   *
   * @param shadingFactorInPercents
   */
  def shadeByPercentage(shadingFactorInPercents: Double): RGBAColor = {
    require(shadingFactorInPercents >= 0.0 && shadingFactorInPercents <= 100.0,
      s"The shading factor must be a Double between 0.0 and 100.0 (was $shadingFactorInPercents)")

    shadeByFactor(shadingFactorInPercents / 100)
  }

  /**
   *
   *
   * @param shadingFactorFromZeroToOne
   */
  def shadeByFactor(shadingFactorFromZeroToOne: Double): RGBAColor = {
    require(shadingFactorFromZeroToOne >= 0.0 && shadingFactorFromZeroToOne <= 1.0,
      s"The shading factor must be a Double between 0.0 and 1.0 (was $shadingFactorFromZeroToOne)")

    val invertedFactor: Double = 1.0 - shadingFactorFromZeroToOne
    val newRed = (invertedFactor * self.red).toInt
    val newGreen = (invertedFactor * self.green).toInt
    val newBlue = (invertedFactor * self.blue).toInt

    RGBAColor(newRed, newGreen, newBlue, self.opacity)
  }

  /**
   *
   *
   * @param tintingFactorInPercents
   */
  def tintByPercentage(tintingFactorInPercents: Double): RGBAColor = {
    require(tintingFactorInPercents >= 0.0 && tintingFactorInPercents <= 100.0,
      s"The tinting factor must be a Double between 0.0 and 100.0 (was $tintingFactorInPercents)")

    tintByFactor(tintingFactorInPercents / 100.0)
  }

  /**
   *
   *
   * @param tintingFactorFromZeroToOne
   */
  def tintByFactor(tintingFactorFromZeroToOne: Double): RGBAColor = {
    require(tintingFactorFromZeroToOne >= 0.0 && tintingFactorFromZeroToOne <= 100.0,
      s"The tinting factor must be a Double between 0.0 and 1.0 (was $tintingFactorFromZeroToOne)")

    val newRed = (self.red + tintingFactorFromZeroToOne * (MaximumRed - self.red)).toInt
    val newGreen = (self.green + tintingFactorFromZeroToOne * (MaximumGreen - self.green)).toInt
    val newBlue = (self.blue + tintingFactorFromZeroToOne * (MaximumBlue - self.blue)).toInt

    RGBAColor(newRed, newGreen, newBlue, self.opacity)
  }

}
