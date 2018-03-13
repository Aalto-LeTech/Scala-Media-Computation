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


import smcl.colors.ColorValidator
import smcl.infrastructure.{CommonValidators, InjectablesRegistry}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object RichColor extends InjectablesRegistry {

  /** The ColorValidator instance to be used by this object. */
  private
  lazy val colorValidator: ColorValidator = {
    injectable(InjectablesRegistry.IIdColorValidator).asInstanceOf[ColorValidator]
  }

  /** The CommonValidators instance to be used by this object. */
  private
  lazy val commonValidators: CommonValidators = {
    injectable(InjectablesRegistry.IIdCommonValidators).asInstanceOf[CommonValidators]
  }

  /**
   *
   *
   * @param self
   *
   * @return
   */
  def apply(self: Color): RichColor = {
    new RichColor(self, commonValidators, colorValidator)
  }

}




/**
 *
 *
 * @param self
 * @param commonValidators
 * @param colorValidator
 *
 * @author Aleksi Lukkarinen
 */
class RichColor private[smcl](
    val self: Color,
    private val commonValidators: CommonValidators,
    private val colorValidator: ColorValidator) {

  /** The factor the amount of which colors are shaded by the darker() method. */
  final def DefaultShadingFactor = 0.1

  /** The factor the amount of which colors are tinted by the lighter() method. */
  final def DefaultTintingFactor = 0.1


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

    Color(newAbsoluteRedness, self.green, self.blue, self.opacity)
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

    val newRed = (self.red + rednessIncrementFactorFromZeroToOne * (ColorValidator.MaximumRed - self.red)).toInt

    Color(newRed, self.green, self.blue, self.opacity)
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
    val newRed = (invertedFactor * self.red).toInt

    Color(newRed, self.green, self.blue, self.opacity)
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

    Color(self.red, newAbsoluteGreenness, self.blue, self.opacity)
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

    val newGreen = (self.green + greennessIncrementFactorFromZeroToOne * (ColorValidator.MaximumGreen - self.green)).toInt

    Color(self.red, newGreen, self.blue, self.opacity)
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
    val newGreen = (invertedFactor * self.green).toInt

    Color(self.red, newGreen, self.blue, self.opacity)
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

    Color(self.red, self.green, newAbsoluteBlueness, self.opacity)
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

    val newBlue = (self.blue + bluenessIncrementFactorFromZeroToOne * (ColorValidator.MaximumBlue - self.blue)).toInt

    Color(self.red, self.green, newBlue, self.opacity)
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
    val newBlue = (invertedFactor * self.blue).toInt

    Color(self.red, self.green, newBlue, self.opacity)
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
    if (self.isOpaque) self
    else Color(self.red, self.green, self.blue, ColorValidator.MaximumOpacity)

  /**
   * Returns a new [[Color]] identical with this one except having full transparency.
   */
  final def withFullTransparency: Color =
    if (self.isTransparent) self
    else Color(self.red, self.green, self.blue, ColorValidator.MinimumOpacity)

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

    Color(self.red, self.green, self.blue, newAbsoluteOpacity)
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

    val newOpacity = (self.opacity + opacityIncrementFactorFromZeroToOne * (ColorValidator.MaximumOpacity - self.opacity)).toInt

    Color(self.red, self.green, self.blue, newOpacity)
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
    val newOpacity = (invertedFactor * self.opacity).toInt

    Color(self.red, self.green, self.blue, newOpacity)
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
    adjustHueOfRGBByDegrees(self, adjustmentInDegrees)

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

    val weightedRed = redWeight * self.red
    val weightedGreen = greenWeight * self.green
    val weightedBlue = blueWeight * self.blue
    val grayIntensity = (weightedRed + weightedGreen + weightedBlue).toInt.min(ColorValidator.MaximumGray)

    Color(grayIntensity, ColorValidator.FullyOpaque)
  }

  /**
   *
   *
   * @return
   */
  final def darker: Color = shadeByFactor(DefaultShadingFactor)

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
    val newRed = (invertedFactor * self.red).toInt
    val newGreen = (invertedFactor * self.green).toInt
    val newBlue = (invertedFactor * self.blue).toInt

    Color(newRed, newGreen, newBlue, self.opacity)
  }

  /**
   *
   *
   * @return
   */
  final def lighter: Color = tintByFactor(DefaultTintingFactor)

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

    val newRed = (self.red + tintingFactorFromZeroToOne * (ColorValidator.MaximumRed - self.red)).ceil.toInt
    val newGreen = (self.green + tintingFactorFromZeroToOne * (ColorValidator.MaximumGreen - self.green)).ceil.toInt
    val newBlue = (self.blue + tintingFactorFromZeroToOne * (ColorValidator.MaximumBlue - self.blue)).ceil.toInt

    Color(newRed, newGreen, newBlue, self.opacity)
  }

}
