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

package smcl.pictures.filters


import smcl.colors.ColorValidator
import smcl.infrastructure.{CommonValidators, InjectablesRegistry}
import smcl.pictures.PictureElement




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ToWeightedGrayscale
    extends InjectablesRegistry {

  /** The CommonValidators instance to be used by this object. */
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
   * @param redWeight
   * @param greenWeight
   * @param blueWeight
   *
   * @return
   */
  @inline
  def apply(
      redWeight: Double,
      greenWeight: Double,
      blueWeight: Double): Filter = {

    commonValidators.validateZeroToOneFactor(redWeight, Option("Red weight"))
    commonValidators.validateZeroToOneFactor(greenWeight, Option("Green weight"))
    commonValidators.validateZeroToOneFactor(blueWeight, Option("Blue weight"))

    colorValidator.validateRGBColorWeightCombination(redWeight, greenWeight, blueWeight)

    new ToWeightedGrayscale(redWeight, greenWeight, blueWeight)
  }

  /**
   *
   *
   * @param target
   * @param redWeight
   * @param greenWeight
   * @param blueWeight
   *
   * @return
   */
  @inline
  def apply(
      target: PictureElement,
      redWeight: Double,
      greenWeight: Double,
      blueWeight: Double): PictureElement = {

    val filter = apply(redWeight, greenWeight, blueWeight)
    filter(target.toBitmap)
  }

}




/**
 *
 *
 * @param redWeight
 * @param greenWeight
 * @param blueWeight
 *
 * @author Aleksi Lukkarinen
 */
class ToWeightedGrayscale private(
    val redWeight: Double,
    val greenWeight: Double,
    val blueWeight: Double)
    extends Filter {

  /**
   *
   *
   * @param target
   *
   * @return
   */
  @inline
  override
  def apply(target: PictureElement): PictureElement = {
    target.toBitmapCopy.translateColorsWith{(red, green, blue, opacity) =>
      val weightedRed = redWeight * red
      val weightedGreen = greenWeight * green
      val weightedBlue = blueWeight * blue
      val intensity = (weightedRed + weightedGreen + weightedBlue).toInt

      (intensity, intensity, intensity, opacity)
    }
  }

}
