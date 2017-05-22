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
class RGBATranslationTableValidator private[smcl](
    private val colorValidator: ColorValidator) {

  /**
   *
   *
   * @param redCandidate
   *
   * @return
   */
  @inline
  def redComponentIsInRange(redCandidate: Short): Boolean = {
    colorValidator.rgbRedComponentIsInRange(redCandidate)
  }

  /**
   *
   *
   * @param greenCandidate
   *
   * @return
   */
  @inline
  def greenComponentIsInRange(greenCandidate: Short): Boolean = {
    colorValidator.rgbGreenComponentIsInRange(greenCandidate)
  }

  /**
   *
   *
   * @param blueCandidate
   *
   * @return
   */
  @inline
  def blueComponentIsInRange(blueCandidate: Short): Boolean = {
    colorValidator.rgbBlueComponentIsInRange(blueCandidate)
  }

  /**
   *
   *
   * @param opacityCandidate
   *
   * @return
   */
  @inline
  def opacityComponentIsInRange(opacityCandidate: Short): Boolean = {
    colorValidator.rgbaOpacityComponentIsInRange(opacityCandidate)
  }

  /**
   *
   *
   * @param reds
   *
   * @return
   */
  @inline
  def lengthOfRedDimensionIsValid(reds: Seq[Short]): Boolean = {
    reds.length == ByteRange.length
  }

  /**
   *
   *
   * @param greens
   *
   * @return
   */
  @inline
  def lengthOfGreenDimensionIsValid(greens: Seq[Short]): Boolean = {
    greens.length == ByteRange.length
  }

  /**
   *
   *
   * @param blues
   *
   * @return
   */
  @inline
  def lengthOfBlueDimensionIsValid(blues: Seq[Short]): Boolean = {
    blues.length == ByteRange.length
  }

  /**
   *
   *
   * @param opacities
   *
   * @return
   */
  @inline
  def lengthOfOpacityDimensionIsValid(opacities: Seq[Short]): Boolean = {
    opacities.length == ByteRange.length
  }

  /**
   *
   *
   * @param redCandidate
   */
  @inline
  def validateRedComponent(redCandidate: Short): Unit = {
    colorValidator.validateRGBRedComponent(redCandidate)
  }

  /**
   *
   *
   * @param redCandidate
   */
  @inline
  def validateFunctionProvidedRedComponent(redCandidate: Short): Unit =
  if (!redComponentIsInRange(redCandidate)) {
    throw RGBRedComponentFromValueProviderOutOfRangeError(
      redCandidate, ColorValidator.MinimumRGBRed, ColorValidator.MaximumRGBRed)
  }

  /**
   *
   *
   * @param greenCandidate
   */
  @inline
  def validateGreenComponent(greenCandidate: Short): Unit = {
    colorValidator.validateRGBGreenComponent(greenCandidate)
  }

  /**
   *
   *
   * @param greenCandidate
   */
  @inline
  def validateFunctionProvidedGreenComponent(greenCandidate: Short): Unit =
  if (!greenComponentIsInRange(greenCandidate)) {
    throw RGBGreenComponentFromValueProviderOutOfRangeError(
      greenCandidate, ColorValidator.MinimumRGBGreen, ColorValidator.MaximumRGBGreen)
  }

  /**
   *
   *
   * @param blueCandidate
   */
  @inline
  def validateBlueComponent(blueCandidate: Short): Unit = {
    colorValidator.validateRGBBlueComponent(blueCandidate)
  }

  /**
   *
   *
   * @param blueCandidate
   */
  @inline
  def validateFunctionProvidedBlueComponent(blueCandidate: Short): Unit =
  if (!blueComponentIsInRange(blueCandidate)) {
    throw RGBBlueComponentFromValueProviderOutOfRangeError(
      blueCandidate, ColorValidator.MinimumRGBBlue, ColorValidator.MaximumRGBBlue)
  }

  /**
   *
   *
   * @param opacityCandidate
   */
  @inline
  def validateOpacityComponent(opacityCandidate: Short): Unit = {
    colorValidator.validateRGBAOpacityComponent(opacityCandidate)
  }

  /**
   *
   *
   * @param opacityCandidate
   */
  @inline
  def validateFunctionProvidedOpacityComponent(opacityCandidate: Short): Unit =
  if (!opacityComponentIsInRange(opacityCandidate)) {
    throw RGBAOpacityComponentFromValueProviderOutOfRangeError(opacityCandidate,
      ColorValidator.MinimumRGBAOpacity, ColorValidator.MaximumRGBAOpacity)
  }

  /**
   *
   *
   * @param reds
   */
  @inline
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
  def validateFunctionProvidedComponents(
      rgbaTuple: (Short, Short, Short, Short)): Unit = {

    validateFunctionProvidedRedComponent(rgbaTuple._1)
    validateFunctionProvidedGreenComponent(rgbaTuple._2)
    validateFunctionProvidedBlueComponent(rgbaTuple._3)
    validateFunctionProvidedOpacityComponent(rgbaTuple._4)
  }

}
