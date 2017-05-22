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


import aalto.smcl.infrastructure.CommonValidators




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
  final def withAbsoluteRednessPercentage(newAbsoluteRednessInPercents: Double): RGBAColor = {
    new CommonValidators().validatePercentage(newAbsoluteRednessInPercents, Option("Redness"))

    withAbsoluteRednessFactor(newAbsoluteRednessInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteRednessFactorFromZeroToOne
   */
  final def withAbsoluteRednessFactor(newAbsoluteRednessFactorFromZeroToOne: Double): RGBAColor = {
    new CommonValidators().validateZeroToOneFactor(newAbsoluteRednessFactorFromZeroToOne, Option("Redness"))

    withAbsoluteRedness((newAbsoluteRednessFactorFromZeroToOne * ColorValidator.MaximumRGBRed).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteRedness
   */
  final def withAbsoluteRedness(newAbsoluteRedness: Int): RGBAColor = {
    ColorValidator.validateRGBRedComponent(newAbsoluteRedness)

    RGBAColor(newAbsoluteRedness, self.green, self.blue, self.opacity)
  }

  /**
   *
   *
   * @param rednessIncrementInPercents
   */
  final def increaseRednessByPercentage(rednessIncrementInPercents: Double): RGBAColor = {
    new CommonValidators().validatePercentage(rednessIncrementInPercents, Option("Redness increment"))

    increaseRednessByFactor(rednessIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param rednessIncrementFactorFromZeroToOne
   */
  final def increaseRednessByFactor(rednessIncrementFactorFromZeroToOne: Double): RGBAColor = {
    new CommonValidators().validateZeroToOneFactor(rednessIncrementFactorFromZeroToOne, Option("Redness increment"))

    val newRed = (self.red + rednessIncrementFactorFromZeroToOne * (ColorValidator.MaximumRGBRed - self.red)).toInt

    RGBAColor(newRed, self.green, self.blue, self.opacity)
  }

  /**
   *
   *
   * @param rednessDecrementInPercents
   */
  final def decreaseRednessByPercentage(rednessDecrementInPercents: Double): RGBAColor = {
    new CommonValidators().validatePercentage(rednessDecrementInPercents, Option("Redness decrement"))

    decreaseRednessByFactor(rednessDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param rednessDecrementFactorFromZeroToOne
   */
  final def decreaseRednessByFactor(rednessDecrementFactorFromZeroToOne: Double): RGBAColor = {
    new CommonValidators().validateZeroToOneFactor(rednessDecrementFactorFromZeroToOne, Option("Redness decrement"))

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
  final def withAbsoluteGreennessPercentage(newAbsoluteGreennessInPercents: Double): RGBAColor = {
    new CommonValidators().validatePercentage(newAbsoluteGreennessInPercents, Option("Greenness"))

    withAbsoluteGreennessFactor(newAbsoluteGreennessInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteGreennessFactorFromZeroToOne
   */
  final def withAbsoluteGreennessFactor(newAbsoluteGreennessFactorFromZeroToOne: Double): RGBAColor = {
    new CommonValidators().validateZeroToOneFactor(newAbsoluteGreennessFactorFromZeroToOne, Option("Greenness"))

    withAbsoluteGreenness((newAbsoluteGreennessFactorFromZeroToOne * ColorValidator.MaximumRGBGreen).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteGreenness
   */
  final def withAbsoluteGreenness(newAbsoluteGreenness: Int): RGBAColor = {
    ColorValidator.validateRGBGreenComponent(newAbsoluteGreenness)

    RGBAColor(self.red, newAbsoluteGreenness, self.blue, self.opacity)
  }

  /**
   *
   *
   * @param greennessIncrementInPercents
   */
  final def increaseGreennessByPercentage(greennessIncrementInPercents: Double): RGBAColor = {
    new CommonValidators().validatePercentage(greennessIncrementInPercents, Option("Greenness increment"))

    increaseGreennessByFactor(greennessIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param greennessIncrementFactorFromZeroToOne
   */
  final def increaseGreennessByFactor(greennessIncrementFactorFromZeroToOne: Double): RGBAColor = {
    new CommonValidators().validateZeroToOneFactor(greennessIncrementFactorFromZeroToOne, Option("Greenness increment"))

    val newGreen = (self.green + greennessIncrementFactorFromZeroToOne * (ColorValidator.MaximumRGBGreen - self.green)).toInt

    RGBAColor(self.red, newGreen, self.blue, self.opacity)
  }

  /**
   *
   *
   * @param greennessDecrementInPercents
   */
  final def decreaseGreennessByPercentage(greennessDecrementInPercents: Double): RGBAColor = {
    new CommonValidators().validatePercentage(greennessDecrementInPercents, Option("Greenness decrement"))

    decreaseGreennessByFactor(greennessDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param greennessDecrementFactorFromZeroToOne
   */
  final def decreaseGreennessByFactor(greennessDecrementFactorFromZeroToOne: Double): RGBAColor = {
    new CommonValidators().validateZeroToOneFactor(greennessDecrementFactorFromZeroToOne, Option("Greenness decrement"))

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
  final def withAbsoluteBluenessPercentage(newAbsoluteBluenessInPercents: Double): RGBAColor = {
    new CommonValidators().validatePercentage(newAbsoluteBluenessInPercents, Option("Blueness"))

    withAbsoluteBluenessFactor(newAbsoluteBluenessInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteBluenessFactorFromZeroToOne
   */
  final def withAbsoluteBluenessFactor(newAbsoluteBluenessFactorFromZeroToOne: Double): RGBAColor = {
    new CommonValidators().validateZeroToOneFactor(newAbsoluteBluenessFactorFromZeroToOne, Option("Blueness"))

    withAbsoluteBlueness((newAbsoluteBluenessFactorFromZeroToOne * ColorValidator.MaximumRGBBlue).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteBlueness
   */
  final def withAbsoluteBlueness(newAbsoluteBlueness: Int): RGBAColor = {
    ColorValidator.validateRGBBlueComponent(newAbsoluteBlueness)

    RGBAColor(self.red, self.green, newAbsoluteBlueness, self.opacity)
  }

  /**
   *
   *
   * @param bluenessIncrementInPercents
   */
  final def increaseBluenessByPercentage(bluenessIncrementInPercents: Double): RGBAColor = {
    new CommonValidators().validatePercentage(bluenessIncrementInPercents, Option("Blueness increment"))

    increaseBluenessByFactor(bluenessIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param bluenessIncrementFactorFromZeroToOne
   */
  final def increaseBluenessByFactor(bluenessIncrementFactorFromZeroToOne: Double): RGBAColor = {
    new CommonValidators().validateZeroToOneFactor(bluenessIncrementFactorFromZeroToOne, Option("Blueness increment"))

    val newBlue = (self.blue + bluenessIncrementFactorFromZeroToOne * (ColorValidator.MaximumRGBBlue - self.blue)).toInt

    RGBAColor(self.red, self.green, newBlue, self.opacity)
  }

  /**
   *
   *
   * @param bluenessDecrementInPercents
   */
  final def decreaseBluenessByPercentage(bluenessDecrementInPercents: Double): RGBAColor = {
    new CommonValidators().validatePercentage(bluenessDecrementInPercents, Option("Blueness decrement"))

    decreaseBluenessByFactor(bluenessDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param bluenessDecrementFactorFromZeroToOne
   */
  final def decreaseBluenessByFactor(bluenessDecrementFactorFromZeroToOne: Double): RGBAColor = {
    new CommonValidators().validateZeroToOneFactor(bluenessDecrementFactorFromZeroToOne, Option("Blueness decrement"))

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
  final def withFullOpacity: RGBAColor =
    if (self.isOpaque) self
    else RGBAColor(self.red, self.green, self.blue, ColorValidator.MaximumRGBAOpacity)

  /**
   * Returns a new [[RGBAColor]] identical with this one except having full transparency.
   */
  final def withFullTransparency: RGBAColor =
    if (self.isTransparent) self
    else RGBAColor(self.red, self.green, self.blue, ColorValidator.MinimumRGBAOpacity)

  /**
   *
   *
   * @param newAbsoluteOpacityInPercents
   */
  final def withAbsoluteOpacityPercentage(newAbsoluteOpacityInPercents: Double): RGBAColor = {
    new CommonValidators().validatePercentage(newAbsoluteOpacityInPercents, Option("Opacity"))

    withAbsoluteOpacityFactor(newAbsoluteOpacityInPercents / 100.0)
  }

  /**
   *
   *
   * @param newAbsoluteOpacityFactorFromZeroToOne
   */
  final def withAbsoluteOpacityFactor(newAbsoluteOpacityFactorFromZeroToOne: Double): RGBAColor = {
    new CommonValidators().validateZeroToOneFactor(newAbsoluteOpacityFactorFromZeroToOne, Option("Opacity"))

    withAbsoluteOpacity((newAbsoluteOpacityFactorFromZeroToOne * FullyOpaque).toInt)
  }

  /**
   *
   *
   * @param newAbsoluteOpacity
   */
  final def withAbsoluteOpacity(newAbsoluteOpacity: Int): RGBAColor = {
    ColorValidator.validateRGBAOpacityComponent(newAbsoluteOpacity)

    RGBAColor(self.red, self.green, self.blue, newAbsoluteOpacity)
  }

  /**
   *
   *
   * @param opacityIncrementInPercents
   */
  final def increaseOpacityByPercentage(opacityIncrementInPercents: Double): RGBAColor = {
    new CommonValidators().validatePercentage(opacityIncrementInPercents, Option("Opacity increment"))

    increaseOpacityByFactor(opacityIncrementInPercents / 100.0)
  }

  /**
   *
   *
   * @param opacityIncrementFactorFromZeroToOne
   */
  final def increaseOpacityByFactor(opacityIncrementFactorFromZeroToOne: Double): RGBAColor = {
    new CommonValidators().validateZeroToOneFactor(opacityIncrementFactorFromZeroToOne, Option("Opacity increment"))

    val newOpacity = (self.opacity + opacityIncrementFactorFromZeroToOne * (ColorValidator.MaximumRGBAOpacity - self.opacity)).toInt

    RGBAColor(self.red, self.green, self.blue, newOpacity)
  }

  /**
   *
   *
   * @param opacityDecrementInPercents
   */
  final def decreaseOpacityByPercentage(opacityDecrementInPercents: Double): RGBAColor = {
    new CommonValidators().validatePercentage(opacityDecrementInPercents, Option("Opacity decrement"))

    decreaseOpacityByFactor(opacityDecrementInPercents / 100)
  }

  /**
   *
   *
   * @param opacityDecrementFactorFromZeroToOne
   */
  final def decreaseOpacityByFactor(opacityDecrementFactorFromZeroToOne: Double): RGBAColor = {
    new CommonValidators().validateZeroToOneFactor(opacityDecrementFactorFromZeroToOne, Option("Opacity decrement"))

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
   * @param adjustmentInDegrees
   *
   * @return
   */
  final def adjustHueByDegrees(adjustmentInDegrees: Double): RGBAColor =
    adjustHueOfRgbByDegrees(self, adjustmentInDegrees)

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
      blueWeight: Double = 0.33): RGBAColor = {

    val validators = new CommonValidators()
    validators.validateZeroToOneFactor(redWeight, Option("Red weight"))
    validators.validateZeroToOneFactor(greenWeight, Option("Green weight"))
    validators.validateZeroToOneFactor(blueWeight, Option("Blue weight"))

    ColorValidator.validateRGBColorWeightCombination(redWeight, greenWeight, blueWeight)

    val weightedRed = redWeight * self.red
    val weightedGreen = greenWeight * self.green
    val weightedBlue = blueWeight * self.blue
    val grayIntensity = (weightedRed + weightedGreen + weightedBlue).toInt.min(ColorValidator.MaximumRGBGray)

    RGBAColor(grayIntensity, FullyOpaque)
  }

  /**
   *
   *
   * @param shadingFactorInPercents
   */
  final def shadeByPercentage(shadingFactorInPercents: Double): RGBAColor = {
    new CommonValidators().validatePercentage(shadingFactorInPercents, Option("Shading"))

    shadeByFactor(shadingFactorInPercents / 100)
  }

  /**
   *
   *
   * @param shadingFactorFromZeroToOne
   */
  final def shadeByFactor(shadingFactorFromZeroToOne: Double): RGBAColor = {
    new CommonValidators().validateZeroToOneFactor(shadingFactorFromZeroToOne, Option("Shading"))

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
  final def tintByPercentage(tintingFactorInPercents: Double): RGBAColor = {
    new CommonValidators().validatePercentage(tintingFactorInPercents, Option("Tinting"))

    tintByFactor(tintingFactorInPercents / 100.0)
  }

  /**
   *
   *
   * @param tintingFactorFromZeroToOne
   */
  final def tintByFactor(tintingFactorFromZeroToOne: Double): RGBAColor = {
    new CommonValidators().validateZeroToOneFactor(tintingFactorFromZeroToOne, Option("Tinting"))

    val newRed = (self.red + tintingFactorFromZeroToOne * (ColorValidator.MaximumRGBRed - self.red)).toInt
    val newGreen = (self.green + tintingFactorFromZeroToOne * (ColorValidator.MaximumRGBGreen - self.green)).toInt
    val newBlue = (self.blue + tintingFactorFromZeroToOne * (ColorValidator.MaximumRGBBlue - self.blue)).toInt

    RGBAColor(newRed, newGreen, newBlue, self.opacity)
  }

}
