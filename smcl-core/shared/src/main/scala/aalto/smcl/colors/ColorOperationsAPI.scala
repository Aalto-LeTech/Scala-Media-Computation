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


import aalto.smcl.colors
import aalto.smcl.colors.hsi.exceptions.InvalidColorComponentCombinationError
import aalto.smcl.infrastructure._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[colors]
trait ColorOperationsAPI {

  /** ColorValidator instance to be used by the operations of this trait. */
  private lazy val colorValidator: ColorValidator = new ColorValidator()


  /**
   *
   *
   * @param argbInt
   * @param newRed
   *
   * @return
   */
  @inline
  def withNewRedComponent(argbInt: Int, newRed: Int): Int = {
    colorValidator.validateRedComponent(newRed)

    (argbInt & ~ThirdByte) | (newRed << TwoBytes)
  }

  /**
   *
   *
   * @param argbInt
   * @param newGreen
   *
   * @return
   */
  @inline
  def withNewGreenComponent(argbInt: Int, newGreen: Int): Int = {
    colorValidator.validateGreenComponent(newGreen)

    (argbInt & ~SecondByte) | (newGreen << OneByte)
  }

  /**
   *
   *
   * @param argbInt
   * @param newBlue
   *
   * @return
   */
  @inline
  def withNewBlueComponent(argbInt: Int, newBlue: Int): Int = {
    colorValidator.validateBlueComponent(newBlue)

    (argbInt & ~FirstByte) | newBlue
  }

  /**
   *
   *
   * @param argbInt
   * @param newOpacity
   *
   * @return
   */
  @inline
  def withNewOpacityComponent(argbInt: Int, newOpacity: Int): Int = {
    colorValidator.validateOpacityComponent(newOpacity)

    (argbInt & ~FourthByte) | (newOpacity << ThreeBytes)
  }

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  @inline
  def redComponentOf(argbInt: Int): Int = (argbInt & ThirdByte) >>> TwoBytes

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  @inline
  def greenComponentOf(argbInt: Int): Int = (argbInt & SecondByte) >>> OneByte

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  @inline
  def blueComponentOf(argbInt: Int): Int = argbInt & FirstByte

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  @inline
  def opacityComponentOf(argbInt: Int): Int = argbInt >>> ThreeBytes

  /**
   *
   *
   * @param color
   *
   * @return
   */
  @inline
  def rgbaTupleFrom(color: Color): (Int, Int, Int, Int) = {
    (color.red, color.green, color.blue, color.opacity)
  }

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  @inline
  def rgbaTupleFrom(argbInt: Int): (Int, Int, Int, Int) = {
    (redComponentOf(argbInt),
        greenComponentOf(argbInt),
        blueComponentOf(argbInt),
        opacityComponentOf(argbInt))
  }

  /**
   *
   *
   * @param rgbaTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def normalizeRGBA(rgbaTuple: (Int, Int, Int, Int)): (Double, Double, Double, Double) = {
    (normalizeRGBA(_: Int, _: Int, _: Int, _: Int)).tupled.apply(rgbaTuple)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  @inline
  def normalizeRGBA(red: Int, green: Int, blue: Int, opacity: Int): (Double, Double, Double, Double) = {
    colorValidator.validateRGBAColor(red, green, blue, opacity)

    def rgbSum: Double = (red + green + blue).toDouble

    (red.toDouble / rgbSum,
        green.toDouble / rgbSum,
        blue.toDouble / rgbSum,
        opacity.toDouble / ColorValidator.MaximumOpacity)
  }

  /**
   *
   *
   * @param color
   *
   * @return
   */
  @inline
  def rgbTupleFrom(color: Color): (Int, Int, Int) = {
    (color.red, color.green, color.blue)
  }

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  @inline
  def rgbTupleFrom(argbInt: Int): (Int, Int, Int) = {
    (redComponentOf(argbInt),
        greenComponentOf(argbInt),
        blueComponentOf(argbInt))
  }

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def normalizeRGB(rgbTuple: (Int, Int, Int)): (Double, Double, Double) = {
    (normalizeRGB(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  @inline
  def normalizeRGB(red: Int, green: Int, blue: Int): (Double, Double, Double) = {
    colorValidator.validateRGBColor(red, green, blue)

    def sum: Double = (red + green + blue).toDouble

    (red.toDouble / sum, green.toDouble / sum, blue.toDouble / sum)
  }

  /**
   *
   *
   * @param rgbaTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def argbIntFrom(rgbaTuple: (Int, Int, Int, Int)): Int = {
    (argbIntFrom(_: Int, _: Int, _: Int, _: Int)).tupled.apply(rgbaTuple)
  }

  /**
   *
   *
   * @param rgbTuple
   * @param opacity
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def argbIntFrom(rgbTuple: (Int, Int, Int), opacity: Int): Int = {
    (argbIntFrom(_: Int, _: Int, _: Int, opacity)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param opacity
   *
   * @return
   */
  @inline
  def argbIntFrom(
      red: Int = ColorValidator.MinimumRed,
      green: Int = ColorValidator.MinimumGreen,
      blue: Int = ColorValidator.MinimumBlue,
      opacity: Int = ColorValidator.MaximumOpacity): Int = {

    colorValidator.validateRGBAColor(red, green, blue, opacity)

    ((opacity & FirstByte) << ThreeBytes) |
        ((red & FirstByte) << TwoBytes) |
        ((green & FirstByte) << OneByte) |
        (blue & FirstByte)
  }

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def max(rgbTuple: (Int, Int, Int)): Int = {
    (max(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  @inline
  def max(red: Int, green: Int, blue: Int): Int = red.max(green).max(blue)

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def min(rgbTuple: (Int, Int, Int)): Int = {
    (min(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  @inline
  def min(red: Int, green: Int, blue: Int): Int = red.min(green).min(blue)

  /**
   *
   *
   * @param color
   *
   * @return
   */
  @inline
  def isBlack(color: Color): Boolean = isBlack(rgbTupleFrom(color))

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  @inline
  def isBlack(argbInt: Int): Boolean = isBlack(rgbTupleFrom(argbInt))

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def isBlack(rgbTuple: (Int, Int, Int)): Boolean = {
    (isBlack(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  @inline
  def isBlack(red: Int, green: Int, blue: Int): Boolean = {
    red == ColorValidator.MinimumRed.toDouble &&
        green == ColorValidator.MinimumGreen.toDouble &&
        blue == ColorValidator.MinimumBlue.toDouble
  }

  /**
   *
   *
   * @param color
   *
   * @return
   */
  @inline
  def isGray(color: Color): Boolean = isGray(rgbTupleFrom(color))

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  @inline
  def isGray(argbInt: Int): Boolean = isGray(rgbTupleFrom(argbInt))

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def isGray(rgbTuple: (Int, Int, Int)): Boolean = {
    (isGray(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  @inline
  def isGray(red: Int, green: Int, blue: Int): Boolean = {
    red == blue && green == blue
  }

  /**
   *
   *
   * @param color
   *
   * @return
   */
  @inline
  def isWhite(color: Color): Boolean = isWhite(rgbTupleFrom(color))

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  @inline
  def isWhite(argbInt: Int): Boolean = isWhite(rgbTupleFrom(argbInt))

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def isWhite(rgbTuple: (Int, Int, Int)): Boolean = {
    (isWhite(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  @inline
  def isWhite(red: Int, green: Int, blue: Int): Boolean = {
    red == ColorValidator.MaximumRed.toDouble &&
        green == ColorValidator.MaximumGreen.toDouble &&
        blue == ColorValidator.MaximumBlue.toDouble
  }

  /**
   *
   *
   * @param hueCandidateInDegrees
   *
   * @return
   */
  @inline
  def normalizedHSIHueInDegreesFrom(hueCandidateInDegrees: Double): Double = {
    val modCircle = hueCandidateInDegrees % FullCircleInDegrees

    if (modCircle < 0)
      return FullCircleInDegrees + modCircle

    modCircle
  }


  /**
   *
   *
   * @param hueCandidateInDegrees
   *
   * @return
   */
  @inline
  def normalizedHSVHueInDegreesFrom(hueCandidateInDegrees: Double): Double = {
    normalizedHSIHueInDegreesFrom(hueCandidateInDegrees)
  }

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  @inline
  def toHSI(argbInt: Int): (Double, Double, Double) = {
    rgbToHSI(rgbTupleFrom(argbInt))
  }

  /**
   *
   *
   * @param color
   *
   * @return
   */
  @inline
  def toHSI(color: Color): (Double, Double, Double) = {
    rgbToHSI(rgbTupleFrom(color))
  }

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def rgbToHSI(rgbTuple: (Int, Int, Int)): (Double, Double, Double) = {
    (rgbToHSI(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  @inline
  def rgbToHSI(red: Int, green: Int, blue: Int): (Double, Double, Double) = {
    import Math._

    colorValidator.validateRGBColor(red, green, blue)

    val rgbSum: Double = red + green + blue

    val hue: Double =
      if (isGray(red, green, blue)) // Not defined for grays
        ColorValidator.UndefinedHSIHue
      else {
        val RedMinusGreen = red - green
        val RedMinusBlue = red - blue

        val root = sqrt(RedMinusGreen * RedMinusGreen + RedMinusBlue * (green - blue))

        val angleCandidate = toDegrees(acos((RedMinusGreen + RedMinusBlue) / (2.0 * root)))

        if (green >= blue) angleCandidate else FullCircleInDegrees - angleCandidate
      }

    val saturation: Double =
      if (isBlack(red, green, blue))
        ColorValidator.MinimumHSISaturation
      else
        1.0 - 3.0 * (colors.min(red, green, blue) / rgbSum)


    val intensity = rgbSum / 3.0

    (hue, saturation, intensity)
  }

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   *
   * @return
   */
  @inline
  def hsiToColor(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double): Color = {

    hsiToColor(
      hueInDegrees, saturation, intensity,
      ColorValidator.MaximumOpacity)
  }

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def hsiToColor(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double,
      opacity: Int): Color = {

    (Color.apply(_: Int, _: Int, _: Int, opacity))
        .tupled.apply(hsiToRGB(hueInDegrees, saturation, intensity))
  }

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   *
   * @return
   */
  @inline
  def hsiToARGBInt(hueInDegrees: Double, saturation: Double, intensity: Double): Int = {
    hsiToARGBInt(hueInDegrees, saturation, intensity, ColorValidator.MaximumOpacity)
  }

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def hsiToARGBInt(hueInDegrees: Double, saturation: Double, intensity: Double, opacity: Int): Int = {
    (argbIntFrom(_: Int, _: Int, _: Int, opacity))
        .tupled.apply(hsiToRGB(hueInDegrees, saturation, intensity))
  }

  /**
   *
   *
   * @param hsiTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def hsiToRGB(hsiTuple: (Double, Double, Double)): (Int, Int, Int) = {
    (hsiToRGB(_: Double, _: Double, _: Double)).tupled.apply(hsiTuple)
  }

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   *
   * @return
   */
  @inline
  def hsiToRGB(hueInDegrees: Double, saturation: Double, intensity: Double): (Int, Int, Int) = {
    import Math._

    colorValidator.validateHSIColor(hueInDegrees, saturation, intensity)

    // Special case
    if (saturation == ColorValidator.MinimumHSISaturation) {
      val i: Int = round(intensity).toInt
      return (i, i, i)
    }

    val nHueInDeg = normalizedHSIHueInDegreesFrom(hueInDegrees)

    val (aThirdOfCircleHueInDegrees: Double, finalOrder: ((Int, Int, Int) => (Int, Int, Int))) =
      if (nHueInDeg <= 120.0)
        (nHueInDeg,
            (x: Int, y: Int, z: Int) => (x, z, y))
      else if (nHueInDeg <= 240.0)
             (nHueInDeg - 120.0,
                 (x: Int, y: Int, z: Int) => (y, x, z))
      else
        (nHueInDeg - 240.0,
            (x: Int, y: Int, z: Int) => (z, y, x))

    val X = {
      val quotient =
        (saturation * toDegrees(cos(toRadians(aThirdOfCircleHueInDegrees)))) /
            toDegrees(cos(toRadians(60.0 - aThirdOfCircleHueInDegrees)))

      round(intensity * (1 + quotient)).toInt
    }

    val Y = round(intensity - intensity * saturation).toInt

    val Z = round(3.0 * intensity - X - Y).toInt

    val (red, green, blue) = finalOrder(X, Y, Z)

    if (!colorValidator.redComponentIsInRange(red) ||
        !colorValidator.greenComponentIsInRange(green) ||
        !colorValidator.blueComponentIsInRange(blue)) {

      throw InvalidColorComponentCombinationError(hueInDegrees, saturation, intensity)
    }

    (red, green, blue)
  }

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  @inline
  def toHSV(argbInt: Int): (Double, Double, Double) = {
    rgbToHSV(rgbTupleFrom(argbInt))
  }

  /**
   *
   *
   * @param color
   *
   * @return
   */
  @inline
  def toHSV(color: Color): (Double, Double, Double) = {
    rgbToHSV(rgbTupleFrom(color))
  }

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def rgbToHSV(rgbTuple: (Int, Int, Int)): (Double, Double, Double) = {
    (rgbToHSV(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   *
   */
  @inline
  def rgbToHSV(red: Int, green: Int, blue: Int): (Double, Double, Double) = {
    colorValidator.validateRGBColor(red, green, blue)

    val value: Double = max(red, green, blue)

    val vMinusMinRGB: Double = value - min(red, green, blue)

    val saturation: Double =
      if (isBlack(red, green, blue)) // Defined to be zero for black because the division-by-zero
        ColorValidator.MinimumHSVSaturation
      else
        vMinusMinRGB / value

    val hueInDegrees: Double =
      if (isGray(red, green, blue)) // Not defined for grays
        ColorValidator.UndefinedHSVHue
      else {
        if (value == red) {
          if (green >= blue)
            ((green - blue) / vMinusMinRGB + 0) * 60
          else
            ((red - blue) / vMinusMinRGB + 5) * 60
        }
        else if (value == green)
               ((blue - red) / vMinusMinRGB + 2) * 60
        else // value == blue
          ((red - green) / vMinusMinRGB + 4) * 60
      }

    (hueInDegrees, saturation, value)
  }

  /**
   *
   *
   * @param hue
   * @param saturation
   * @param value
   *
   * @return
   */
  @inline
  def hsvToRGB(hue: Double, saturation: Double, value: Double): (Int, Int, Int) = {
    import Math._

    colorValidator.validateHSVColor(hue, saturation, value)

    val huePer60 = normalizedHSVHueInDegreesFrom(hue) / 60.0
    val K = floor(huePer60)
    val T = huePer60 - K

    val V: Int = round(value).toInt
    val X: Int = round(value * (1.0 - saturation)).toInt
    val Y: Int = round(value * (1.0 - saturation * T)).toInt
    val Z: Int = round(value * (1.0 - saturation * (1.0 - T))).toInt

    if (K == 0)
      (V, Z, X)
    else if (K == 1)
           (Y, V, X)
    else if (K == 2)
           (X, V, Z)
    else if (K == 3)
           (X, Y, V)
    else if (K == 4)
           (Z, X, V)
    else
      (V, X, Y)
  }

  /**
   *
   *
   * @param color
   * @param adjustmentInDegrees
   *
   * @return
   */
  @inline
  def adjustHueOfRGBByDegrees(
      color: Color,
      adjustmentInDegrees: Double): Color = {

    Color(
      adjustHueOfRGBByDegrees(rgbTupleFrom(color), adjustmentInDegrees),
      color.opacity,
      color.canonicalName)
  }

  /**
   *
   *
   * @param argbInt
   * @param adjustmentInDegrees
   *
   * @return
   */
  @inline
  def adjustHueOfRGBByDegrees(
      argbInt: Int,
      adjustmentInDegrees: Double): Int = {

    argbIntFrom(
      adjustHueOfRGBByDegrees(rgbTupleFrom(argbInt), adjustmentInDegrees),
      opacityComponentOf(argbInt))
  }

  /**
   *
   *
   * @param rgbTuple
   * @param adjustmentInDegrees
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def adjustHueOfRGBByDegrees(
      rgbTuple: (Int, Int, Int),
      adjustmentInDegrees: Double): (Int, Int, Int) = {

    (adjustHueOfRGBByDegrees(_: Int, _: Int, _: Int, adjustmentInDegrees))
        .tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param adjustmentInDegrees
   *
   * @return
   */
  @inline
  def adjustHueOfRGBByDegrees(
      red: Int, green: Int, blue: Int,
      adjustmentInDegrees: Double): (Int, Int, Int) = {

    val (originalHue, originalSaturation, originalValue) =
      rgbToHSV(red, green, blue) // Validates rgb values

    val newHue = originalHue + adjustmentInDegrees

    hsvToRGB(newHue, originalSaturation, originalValue)
  }

  /**
   *
   *
   * @param color
   *
   * @return
   */
  @inline
  def colorComponentMapFrom(color: Color): Map[Symbol, Double] = {
    colorComponentMapFrom(rgbaTupleFrom(color))
  }

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  @inline
  def colorComponentMapFrom(argbInt: Int): Map[Symbol, Double] = {
    colorComponentMapFrom(rgbaTupleFrom(argbInt))
  }

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def colorComponentMapFrom(rgbTuple: (Int, Int, Int)): Map[Symbol, Double] = {
    (colorComponentMapFrom(_: Int, _: Int, _: Int, ColorValidator.MaximumOpacity))
        .tupled.apply(rgbTuple)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  @inline
  def colorComponentMapFrom(red: Int, green: Int, blue: Int): Map[Symbol, Double] = {
    colorComponentMapFrom(red, green, blue, ColorValidator.MaximumOpacity)
  }

  /**
   *
   *
   * @param rgbaTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def colorComponentMapFrom(rgbaTuple: (Int, Int, Int, Int)): Map[Symbol, Double] = {
    (colorComponentMapFrom(_: Int, _: Int, _: Int, _: Int))
        .tupled.apply(rgbaTuple)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param opacity
   *
   * @return
   */
  @inline
  def colorComponentMapFrom(red: Int, green: Int, blue: Int, opacity: Int): Map[Symbol, Double] = {
    val (hsiHue, hsiSaturation, hsiIntensity) = rgbToHSI(red, green, blue)
    val (hsvHue, hsvSaturation, hsvValue) = rgbToHSV(red, green, blue)

    Map[Symbol, Double](
      'red -> red.toDouble,
      'green -> green.toDouble,
      'blue -> blue.toDouble,
      'opacity -> opacity.toDouble,
      'hsiHue -> hsiHue,
      'hsiSaturation -> hsiSaturation,
      'hsiIntensity -> hsiIntensity,
      'hsvHue -> hsvHue,
      'hsvSaturation -> hsvSaturation,
      'hsvValue -> hsvValue)
  }

}
