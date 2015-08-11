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
    CommonValidators.validatePercentage(newAbsoluteRednessInPercents, Option("Redness"))

    withAbsoluteRednessFactor(newAbsoluteRednessInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteRednessFactorFromZeroToOne
   */
  def withAbsoluteRednessFactor(newAbsoluteRednessFactorFromZeroToOne: Double): RGBAColor = {
    CommonValidators.validateZeroToOneFactor(newAbsoluteRednessFactorFromZeroToOne, Option("Redness"))

    withAbsoluteRedness((newAbsoluteRednessFactorFromZeroToOne * ColorValidator.MaximumRgbRed).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteRedness
   */
  def withAbsoluteRedness(newAbsoluteRedness: Int): RGBAColor = {
    ColorValidator.validateRgbRedComponent(newAbsoluteRedness)

    RGBAColor(newAbsoluteRedness, self.green, self.blue, self.opacity)
  }

  /**
   *
   *
   * @param rednessIncrementInPercents
   */
  def increaseRednessByPercentage(rednessIncrementInPercents: Double): RGBAColor = {
    CommonValidators.validatePercentage(rednessIncrementInPercents, Option("Redness increment"))

    increaseRednessByFactor(rednessIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param rednessIncrementFactorFromZeroToOne
   */
  def increaseRednessByFactor(rednessIncrementFactorFromZeroToOne: Double): RGBAColor = {
    CommonValidators.validateZeroToOneFactor(rednessIncrementFactorFromZeroToOne, Option("Redness increment"))

    val newRed = (self.red + rednessIncrementFactorFromZeroToOne * (ColorValidator.MaximumRgbRed - self.red)).toInt

    RGBAColor(newRed, self.green, self.blue, self.opacity)
  }

  /**
   *
   *
   * @param rednessDecrementInPercents
   */
  def decreaseRednessByPercentage(rednessDecrementInPercents: Double): RGBAColor = {
    CommonValidators.validatePercentage(rednessDecrementInPercents, Option("Redness decrement"))

    decreaseRednessByFactor(rednessDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param rednessDecrementFactorFromZeroToOne
   */
  def decreaseRednessByFactor(rednessDecrementFactorFromZeroToOne: Double): RGBAColor = {
    CommonValidators.validateZeroToOneFactor(rednessDecrementFactorFromZeroToOne, Option("Redness decrement"))

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
    CommonValidators.validatePercentage(newAbsoluteGreennessInPercents, Option("Greenness"))

    withAbsoluteGreennessFactor(newAbsoluteGreennessInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteGreennessFactorFromZeroToOne
   */
  def withAbsoluteGreennessFactor(newAbsoluteGreennessFactorFromZeroToOne: Double): RGBAColor = {
    CommonValidators.validateZeroToOneFactor(newAbsoluteGreennessFactorFromZeroToOne, Option("Greenness"))

    withAbsoluteGreenness((newAbsoluteGreennessFactorFromZeroToOne * ColorValidator.MaximumRgbGreen).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteGreenness
   */
  def withAbsoluteGreenness(newAbsoluteGreenness: Int): RGBAColor = {
    ColorValidator.validateRgbGreenComponent(newAbsoluteGreenness)

    RGBAColor(self.red, newAbsoluteGreenness, self.blue, self.opacity)
  }

  /**
   *
   *
   * @param greennessIncrementInPercents
   */
  def increaseGreennessByPercentage(greennessIncrementInPercents: Double): RGBAColor = {
    CommonValidators.validatePercentage(greennessIncrementInPercents, Option("Greenness increment"))

    increaseGreennessByFactor(greennessIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param greennessIncrementFactorFromZeroToOne
   */
  def increaseGreennessByFactor(greennessIncrementFactorFromZeroToOne: Double): RGBAColor = {
    CommonValidators.validateZeroToOneFactor(greennessIncrementFactorFromZeroToOne, Option("Greenness increment"))

    val newGreen = (self.green + greennessIncrementFactorFromZeroToOne * (ColorValidator.MaximumRgbGreen - self.green)).toInt

    RGBAColor(self.red, newGreen, self.blue, self.opacity)
  }

  /**
   *
   *
   * @param greennessDecrementInPercents
   */
  def decreaseGreennessByPercentage(greennessDecrementInPercents: Double): RGBAColor = {
    CommonValidators.validatePercentage(greennessDecrementInPercents, Option("Greenness decrement"))

    decreaseGreennessByFactor(greennessDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param greennessDecrementFactorFromZeroToOne
   */
  def decreaseGreennessByFactor(greennessDecrementFactorFromZeroToOne: Double): RGBAColor = {
    CommonValidators.validateZeroToOneFactor(greennessDecrementFactorFromZeroToOne, Option("Greenness decrement"))

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
    CommonValidators.validatePercentage(newAbsoluteBluenessInPercents, Option("Blueness"))

    withAbsoluteBluenessFactor(newAbsoluteBluenessInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteBluenessFactorFromZeroToOne
   */
  def withAbsoluteBluenessFactor(newAbsoluteBluenessFactorFromZeroToOne: Double): RGBAColor = {
    CommonValidators.validateZeroToOneFactor(newAbsoluteBluenessFactorFromZeroToOne, Option("Blueness"))

    withAbsoluteBlueness((newAbsoluteBluenessFactorFromZeroToOne * ColorValidator.MaximumRgbBlue).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteBlueness
   */
  def withAbsoluteBlueness(newAbsoluteBlueness: Int): RGBAColor = {
    ColorValidator.validateRgbBlueComponent(newAbsoluteBlueness)

    RGBAColor(self.red, self.green, newAbsoluteBlueness, self.opacity)
  }

  /**
   *
   *
   * @param bluenessIncrementInPercents
   */
  def increaseBluenessByPercentage(bluenessIncrementInPercents: Double): RGBAColor = {
    CommonValidators.validatePercentage(bluenessIncrementInPercents, Option("Blueness increment"))

    increaseBluenessByFactor(bluenessIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param bluenessIncrementFactorFromZeroToOne
   */
  def increaseBluenessByFactor(bluenessIncrementFactorFromZeroToOne: Double): RGBAColor = {
    CommonValidators.validateZeroToOneFactor(bluenessIncrementFactorFromZeroToOne, Option("Blueness increment"))

    val newBlue = (self.blue + bluenessIncrementFactorFromZeroToOne * (ColorValidator.MaximumRgbBlue - self.blue)).toInt

    RGBAColor(self.red, self.green, newBlue, self.opacity)
  }

  /**
   *
   *
   * @param bluenessDecrementInPercents
   */
  def decreaseBluenessByPercentage(bluenessDecrementInPercents: Double): RGBAColor = {
    CommonValidators.validatePercentage(bluenessDecrementInPercents, Option("Blueness decrement"))

    decreaseBluenessByFactor(bluenessDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param bluenessDecrementFactorFromZeroToOne
   */
  def decreaseBluenessByFactor(bluenessDecrementFactorFromZeroToOne: Double): RGBAColor = {
    CommonValidators.validateZeroToOneFactor(bluenessDecrementFactorFromZeroToOne, Option("Blueness decrement"))

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
    else RGBAColor(self.red, self.green, self.blue, ColorValidator.MaximumRgbaOpacity)

  /**
   * Returns a new [[RGBAColor]] identical with this one except having full transparency.
   */
  def withFullTransparency: RGBAColor =
    if (self.isTransparent) self
    else RGBAColor(self.red, self.green, self.blue, ColorValidator.MinimumRgbaOpacity)

  /**
   *
   *
   * @param newAbsoluteOpacityInPercents
   */
  def withAbsoluteOpacityPercentage(newAbsoluteOpacityInPercents: Double): RGBAColor = {
    CommonValidators.validatePercentage(newAbsoluteOpacityInPercents, Option("Opacity"))

    withAbsoluteOpacityFactor(newAbsoluteOpacityInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteOpacityFactorFromZeroToOne
   */
  def withAbsoluteOpacityFactor(newAbsoluteOpacityFactorFromZeroToOne: Double): RGBAColor = {
    CommonValidators.validateZeroToOneFactor(newAbsoluteOpacityFactorFromZeroToOne, Option("Opacity"))

    withAbsoluteOpacity((newAbsoluteOpacityFactorFromZeroToOne * FullyOpaque).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteOpacity
   */
  def withAbsoluteOpacity(newAbsoluteOpacity: Int): RGBAColor = {
    ColorValidator.validateRgbaOpacityComponent(newAbsoluteOpacity)

    RGBAColor(self.red, self.green, self.blue, newAbsoluteOpacity)
  }

  /**
   *
   *
   * @param opacityIncrementInPercents
   */
  def increaseOpacityByPercentage(opacityIncrementInPercents: Double): RGBAColor = {
    CommonValidators.validatePercentage(opacityIncrementInPercents, Option("Opacity increment"))

    increaseOpacityByFactor(opacityIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param opacityIncrementFactorFromZeroToOne
   */
  def increaseOpacityByFactor(opacityIncrementFactorFromZeroToOne: Double): RGBAColor = {
    CommonValidators.validateZeroToOneFactor(opacityIncrementFactorFromZeroToOne, Option("Opacity increment"))

    val newOpacity = (self.opacity + opacityIncrementFactorFromZeroToOne * (ColorValidator.MaximumRgbaOpacity - self.opacity)).toInt

    RGBAColor(self.red, self.green, self.blue, newOpacity)
  }

  /**
   *
   *
   * @param opacityDecrementInPercents
   */
  def decreaseOpacityByPercentage(opacityDecrementInPercents: Double): RGBAColor = {
    CommonValidators.validatePercentage(opacityDecrementInPercents, Option("Opacity decrement"))

    decreaseOpacityByFactor(opacityDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param opacityDecrementFactorFromZeroToOne
   */
  def decreaseOpacityByFactor(opacityDecrementFactorFromZeroToOne: Double): RGBAColor = {
    CommonValidators.validateZeroToOneFactor(opacityDecrementFactorFromZeroToOne, Option("Opacity decrement"))

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

    CommonValidators.validateZeroToOneFactor(redWeight, Option("Red weight"))
    CommonValidators.validateZeroToOneFactor(greenWeight, Option("Green weight"))
    CommonValidators.validateZeroToOneFactor(blueWeight, Option("Blue weight"))

    ColorValidator.validateRgbColorWeightCombination(redWeight, greenWeight, blueWeight)

    val weightedRed = redWeight * self.red
    val weightedGreen = greenWeight * self.green
    val weightedBlue = blueWeight * self.blue
    val grayIntensity = (weightedRed + weightedGreen + weightedBlue).toInt.min(ColorValidator.MaximumRgbGray)

    RGBAColor(grayIntensity, FullyOpaque)
  }

  /**
   *
   *
   * @param shadingFactorInPercents
   */
  def shadeByPercentage(shadingFactorInPercents: Double): RGBAColor = {
    CommonValidators.validatePercentage(shadingFactorInPercents, Option("Shading"))

    shadeByFactor(shadingFactorInPercents / 100)
  }

  /**
   *
   *
   * @param shadingFactorFromZeroToOne
   */
  def shadeByFactor(shadingFactorFromZeroToOne: Double): RGBAColor = {
    CommonValidators.validateZeroToOneFactor(shadingFactorFromZeroToOne, Option("Shading"))

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
    CommonValidators.validatePercentage(tintingFactorInPercents, Option("Tinting"))

    tintByFactor(tintingFactorInPercents / 100.0)
  }

  /**
   *
   *
   * @param tintingFactorFromZeroToOne
   */
  def tintByFactor(tintingFactorFromZeroToOne: Double): RGBAColor = {
    CommonValidators.validateZeroToOneFactor(tintingFactorFromZeroToOne, Option("Tinting"))

    val newRed = (self.red + tintingFactorFromZeroToOne * (ColorValidator.MaximumRgbRed - self.red)).toInt
    val newGreen = (self.green + tintingFactorFromZeroToOne * (ColorValidator.MaximumRgbGreen - self.green)).toInt
    val newBlue = (self.blue + tintingFactorFromZeroToOne * (ColorValidator.MaximumRgbBlue - self.blue)).toInt

    RGBAColor(newRed, newGreen, newBlue, self.opacity)
  }

}
