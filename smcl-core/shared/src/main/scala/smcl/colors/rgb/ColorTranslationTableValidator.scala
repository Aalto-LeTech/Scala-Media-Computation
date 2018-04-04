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
import smcl.colors.exceptions._
import smcl.colors.rgb.exceptions._
import smcl.infrastructure._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class ColorTranslationTableValidator private[smcl](
    private val colorValidator: ColorValidator) {

  /**
   *
   *
   * @param redCandidate
   *
   * @return
   */
  @inline
  final
  def redComponentIsInRange(redCandidate: Short): Boolean = {
    colorValidator.redComponentIsInRange(redCandidate)
  }

  /**
   *
   *
   * @param greenCandidate
   *
   * @return
   */
  @inline
  final
  def greenComponentIsInRange(greenCandidate: Short): Boolean = {
    colorValidator.greenComponentIsInRange(greenCandidate)
  }

  /**
   *
   *
   * @param blueCandidate
   *
   * @return
   */
  @inline
  final
  def blueComponentIsInRange(blueCandidate: Short): Boolean = {
    colorValidator.blueComponentIsInRange(blueCandidate)
  }

  /**
   *
   *
   * @param opacityCandidate
   *
   * @return
   */
  @inline
  final
  def opacityComponentIsInRange(opacityCandidate: Short): Boolean = {
    colorValidator.opacityComponentIsInRange(opacityCandidate)
  }

  /**
   *
   *
   * @param reds
   *
   * @return
   */
  @inline
  final
  def lengthOfRedDimensionIsValid(reds: Seq[Short]): Boolean = {
    reds.lengthCompare(ByteRange.length) == 0
  }

  /**
   *
   *
   * @param greens
   *
   * @return
   */
  @inline
  final
  def lengthOfGreenDimensionIsValid(greens: Seq[Short]): Boolean = {
    greens.lengthCompare(ByteRange.length) == 0
  }

  /**
   *
   *
   * @param blues
   *
   * @return
   */
  @inline
  final
  def lengthOfBlueDimensionIsValid(blues: Seq[Short]): Boolean = {
    blues.lengthCompare(ByteRange.length) == 0
  }

  /**
   *
   *
   * @param opacities
   *
   * @return
   */
  @inline
  final
  def lengthOfOpacityDimensionIsValid(opacities: Seq[Short]): Boolean = {
    opacities.lengthCompare(ByteRange.length) == 0
  }

  /**
   *
   *
   * @param redCandidate
   */
  @inline
  final
  def validateRedComponent(redCandidate: Short): Unit = {
    colorValidator.validateRedComponent(redCandidate)
  }

  /**
   *
   *
   * @param redCandidate
   */
  @inline
  final
  def validateFunctionProvidedRedComponent(redCandidate: Short): Unit =
    if (!redComponentIsInRange(redCandidate)) {
      throw RedComponentFromValueProviderOutOfRangeError(
        redCandidate, ColorValidator.MinimumRed, ColorValidator.MaximumRed)
    }

  /**
   *
   *
   * @param greenCandidate
   */
  @inline
  final
  def validateGreenComponent(greenCandidate: Short): Unit = {
    colorValidator.validateGreenComponent(greenCandidate)
  }

  /**
   *
   *
   * @param greenCandidate
   */
  @inline
  final
  def validateFunctionProvidedGreenComponent(greenCandidate: Short): Unit =
    if (!greenComponentIsInRange(greenCandidate)) {
      throw GreenComponentFromValueProviderOutOfRangeError(
        greenCandidate, ColorValidator.MinimumGreen, ColorValidator.MaximumGreen)
    }

  /**
   *
   *
   * @param blueCandidate
   */
  @inline
  final
  def validateBlueComponent(blueCandidate: Short): Unit = {
    colorValidator.validateBlueComponent(blueCandidate)
  }

  /**
   *
   *
   * @param blueCandidate
   */
  @inline
  final
  def validateFunctionProvidedBlueComponent(blueCandidate: Short): Unit =
    if (!blueComponentIsInRange(blueCandidate)) {
      throw BlueComponentFromValueProviderOutOfRangeError(
        blueCandidate, ColorValidator.MinimumBlue, ColorValidator.MaximumBlue)
    }

  /**
   *
   *
   * @param opacityCandidate
   */
  @inline
  final
  def validateOpacityComponent(opacityCandidate: Short): Unit = {
    colorValidator.validateOpacityComponent(opacityCandidate)
  }

  /**
   *
   *
   * @param opacityCandidate
   */
  @inline
  final
  def validateFunctionProvidedOpacityComponent(opacityCandidate: Short): Unit =
    if (!opacityComponentIsInRange(opacityCandidate)) {
      throw OpacityComponentFromValueProviderOutOfRangeError(opacityCandidate,
        ColorValidator.MinimumOpacity, ColorValidator.MaximumOpacity)
    }

  /**
   *
   *
   * @param reds
   */
  @inline
  final
  def validateRedDimensionLength(reds: Seq[Short]): Unit = {
    if (!lengthOfRedDimensionIsValid(reds)) {
      throw InvalidRedDimensionLengthError(reds.length, ByteRange.length)
    }
  }

  /**
   *
   *
   * @param greens
   */
  @inline
  final
  def validateGreenDimensionLength(greens: Seq[Short]): Unit = {
    if (!lengthOfGreenDimensionIsValid(greens)) {
      throw InvalidGreenDimensionLengthError(greens.length, ByteRange.length)
    }
  }

  /**
   *
   *
   * @param blues
   */
  @inline
  final
  def validateBlueDimensionLength(blues: Seq[Short]): Unit = {
    if (!lengthOfBlueDimensionIsValid(blues)) {
      throw InvalidBlueDimensionLengthError(blues.length, ByteRange.length)
    }
  }

  /**
   *
   *
   * @param opacities
   */
  @inline
  final
  def validateOpacityDimensionLength(opacities: Seq[Short]): Unit = {
    if (!lengthOfOpacityDimensionIsValid(opacities)) {
      throw InvalidOpacityDimensionLengthError(opacities.length, ByteRange.length)
    }
  }

  /**
   *
   *
   * @param reds
   */
  @inline
  final
  def validateRedDimension(reds: Seq[Short]): Unit = {
    require(reds != null, "Red component value array cannot be null.")

    validateRedDimensionLength(reds)

    reds foreach validateRedComponent
  }

  /**
   *
   *
   * @param greens
   */
  @inline
  final
  def validateGreenDimension(greens: Seq[Short]): Unit = {
    require(greens != null, "Green component value array cannot be null.")

    validateGreenDimensionLength(greens)

    greens foreach validateGreenComponent
  }

  /**
   *
   *
   * @param blues
   */
  @inline
  final
  def validateBlueDimension(blues: Seq[Short]): Unit = {
    require(blues != null, "Blue component value array cannot be null.")

    validateBlueDimensionLength(blues)

    blues foreach validateBlueComponent
  }

  /**
   *
   *
   * @param opacities
   */
  @inline
  final
  def validateOpacityDimension(opacities: Seq[Short]): Unit = {
    require(opacities != null, "Opacity component value array cannot be null.")

    validateOpacityDimensionLength(opacities)

    opacities foreach validateOpacityComponent
  }

  /**
   *
   *
   * @param reds
   * @param greens
   * @param blues
   * @param opacities
   */
  @inline
  final
  def validateSeparateDimensions(
      reds: Seq[Short],
      greens: Seq[Short],
      blues: Seq[Short],
      opacities: Seq[Short]): Unit = {

    validateRedDimension(reds)
    validateGreenDimension(greens)
    validateBlueDimension(blues)
    validateOpacityDimension(opacities)
  }

  /**
   *
   *
   * @param rgbaTuple
   */
  @inline
  final
  def validateFunctionProvidedComponents(
      rgbaTuple: (Short, Short, Short, Short)): Unit = {

    validateFunctionProvidedRedComponent(rgbaTuple._1)
    validateFunctionProvidedGreenComponent(rgbaTuple._2)
    validateFunctionProvidedBlueComponent(rgbaTuple._3)
    validateFunctionProvidedOpacityComponent(rgbaTuple._4)
  }

}
