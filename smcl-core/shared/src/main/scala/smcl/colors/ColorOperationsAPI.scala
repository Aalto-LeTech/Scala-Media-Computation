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

package smcl.colors


import smcl.colors
import smcl.colors.hsi.exceptions.InvalidColorComponentCombinationError
import smcl.infrastructure._




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
  final
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
  final
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
  final
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
  final
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
  final
  def redComponentOf(argbInt: Int): Int = (argbInt & ThirdByte) >>> TwoBytes

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  final
  def greenComponentOf(argbInt: Int): Int = (argbInt & SecondByte) >>> OneByte

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  final
  def blueComponentOf(argbInt: Int): Int = argbInt & FirstByte

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  final
  def opacityComponentOf(argbInt: Int): Int = argbInt >>> ThreeBytes

  /**
   *
   *
   * @param color
   *
   * @return
   */
  final
  def rgbaTupleFrom(color: Color): (Int, Int, Int, Int) =
    (color.red, color.green, color.blue, color.opacity)

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  final
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
  final
  def normalizeRGBA(rgbaTuple: (Int, Int, Int, Int)): (Double, Double, Double, Double) =
    normalizeRGBA(rgbaTuple._1, rgbaTuple._2, rgbaTuple._3, rgbaTuple._4)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  final
  def normalizeRGBA(
      red: Int,
      green: Int,
      blue: Int,
      opacity: Int): (Double, Double, Double, Double) = {

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
  final
  def rgbTupleFrom(color: Color): (Int, Int, Int) =
    (color.red, color.green, color.blue)

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  final
  def rgbTupleFrom(argbInt: Int): (Int, Int, Int) =
    (redComponentOf(argbInt),
        greenComponentOf(argbInt),
        blueComponentOf(argbInt))

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  final
  def normalizeRGB(rgbTuple: (Int, Int, Int)): (Double, Double, Double) =
    normalizeRGB(rgbTuple._1, rgbTuple._2, rgbTuple._3)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  final
  def normalizeRGB(
      red: Int,
      green: Int,
      blue: Int): (Double, Double, Double) = {

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
  final
  def argbIntFrom(rgbaTuple: (Int, Int, Int, Int)): Int =
    argbIntFrom(rgbaTuple._1, rgbaTuple._2, rgbaTuple._3, rgbaTuple._4)

  /**
   *
   *
   * @param rgbTuple
   * @param opacity
   *
   * @return
   */
  final
  def argbIntFrom(
      rgbTuple: (Int, Int, Int),
      opacity: Int): Int = {

    argbIntFrom(rgbTuple._1, rgbTuple._2, rgbTuple._3, opacity)
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
  final
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
  final
  def max(rgbTuple: (Int, Int, Int)): Int =
    max(rgbTuple._1, rgbTuple._2, rgbTuple._3)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  final
  def max(
      red: Int,
      green: Int,
      blue: Int): Int = {

    red.max(green).max(blue)
  }

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  final
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
  final
  def min(
      red: Int,
      green: Int,
      blue: Int): Int = {

    red.min(green).min(blue)
  }

  /**
   *
   *
   * @param color
   *
   * @return
   */
  final
  def isBlack(color: Color): Boolean = isBlack(rgbTupleFrom(color))

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  final
  def isBlack(argbInt: Int): Boolean = isBlack(rgbTupleFrom(argbInt))

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  final
  def isBlack(rgbTuple: (Int, Int, Int)): Boolean =
    isBlack(rgbTuple._1, rgbTuple._2, rgbTuple._3)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  final
  def isBlack(
      red: Int,
      green: Int,
      blue: Int): Boolean = {

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
  final
  def isGray(color: Color): Boolean = isGray(rgbTupleFrom(color))

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  final
  def isGray(argbInt: Int): Boolean = isGray(rgbTupleFrom(argbInt))

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  final
  def isGray(rgbTuple: (Int, Int, Int)): Boolean =
    isGray(rgbTuple._1, rgbTuple._2, rgbTuple._3)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  final
  def isGray(
      red: Int,
      green: Int,
      blue: Int): Boolean = {

    red == blue && green == blue
  }

  /**
   *
   *
   * @param color
   *
   * @return
   */
  final
  def isWhite(color: Color): Boolean = isWhite(rgbTupleFrom(color))

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  final
  def isWhite(argbInt: Int): Boolean = isWhite(rgbTupleFrom(argbInt))

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  final
  def isWhite(rgbTuple: (Int, Int, Int)): Boolean =
    isWhite(rgbTuple._1, rgbTuple._2, rgbTuple._3)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  final
  def isWhite(
      red: Int,
      green: Int,
      blue: Int): Boolean = {

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
  final
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
  final
  def normalizedHSVHueInDegreesFrom(hueCandidateInDegrees: Double): Double =
    normalizedHSIHueInDegreesFrom(hueCandidateInDegrees)

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  final
  def toHSI(argbInt: Int): (Double, Double, Double) =
    rgbToHSI(rgbTupleFrom(argbInt))

  /**
   *
   *
   * @param color
   *
   * @return
   */
  final
  def toHSI(color: Color): (Double, Double, Double) =
    rgbToHSI(rgbTupleFrom(color))

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  final
  def rgbToHSI(rgbTuple: (Int, Int, Int)): (Double, Double, Double) =
    rgbToHSI(rgbTuple._1, rgbTuple._2, rgbTuple._3)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  def rgbToHSI(
      red: Int,
      green: Int,
      blue: Int): (Double, Double, Double) = {

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
  final
  def hsiToColor(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double): Color = {

    hsiToColor(
      hueInDegrees,
      saturation,
      intensity,
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
  final
  def hsiToColor(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double,
      opacity: Int): Color = {

    val rgb = hsiToRGB(hueInDegrees, saturation, intensity)

    Color.apply(rgb._1, rgb._2, rgb._3, opacity)
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
  final
  def hsiToARGBInt(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double): Int = {

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
  final
  def hsiToARGBInt(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double,
      opacity: Int): Int = {

    val rgb = hsiToRGB(hueInDegrees, saturation, intensity)

    argbIntFrom(rgb._1, rgb._2, rgb._3, opacity)
  }

  /**
   *
   *
   * @param hsiTuple
   *
   * @return
   */
  final
  def hsiToRGB(hsiTuple: (Double, Double, Double)): (Int, Int, Int) =
    hsiToRGB(hsiTuple._1, hsiTuple._2, hsiTuple._3)

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   *
   * @return
   */
  def hsiToRGB(
      hueInDegrees: Double,
      saturation: Double,
      intensity: Double): (Int, Int, Int) = {

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
  final
  def toHSV(argbInt: Int): (Double, Double, Double) =
    rgbToHSV(rgbTupleFrom(argbInt))

  /**
   *
   *
   * @param color
   *
   * @return
   */
  final
  def toHSV(color: Color): (Double, Double, Double) =
    rgbToHSV(rgbTupleFrom(color))

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  final
  def rgbToHSV(rgbTuple: (Int, Int, Int)): (Double, Double, Double) =
    rgbToHSV(rgbTuple._1, rgbTuple._2, rgbTuple._3)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   *
   */
  def rgbToHSV(
      red: Int,
      green: Int,
      blue: Int): (Double, Double, Double) = {

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
  def hsvToRGB(
      hue: Double,
      saturation: Double,
      value: Double): (Int, Int, Int) = {

    import Math._

    colorValidator.validateHSVColor(hue, saturation, value)

    val huePer60 = normalizedHSVHueInDegreesFrom(hue) / 60.0
    val K = floor(huePer60)
    val T = huePer60 - K

    val V: Int = round(value).toInt
    val X: Int = round(value * (1.0 - saturation)).toInt
    val Y: Int = round(value * (1.0 - saturation * T)).toInt
    val Z: Int = round(value * (1.0 - saturation * (1.0 - T))).toInt

    if (K == 0) {
      (V, Z, X)
    }
    else if (K == 1) {
      (Y, V, X)
    }
    else if (K == 2) {
      (X, V, Z)
    }
    else if (K == 3) {
      (X, Y, V)
    }
    else if (K == 4) {
      (Z, X, V)
    }
    else {
      (V, X, Y)
    }
  }

  /**
   *
   *
   * @param color
   * @param adjustmentInDegrees
   *
   * @return
   */
  final
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
  final
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
  final
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
  final
  def adjustHueOfRGBByDegrees(
      red: Int,
      green: Int,
      blue: Int,
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
  final
  def colorComponentMapFrom(color: Color): Map[Symbol, Double] =
    colorComponentMapFrom(rgbaTupleFrom(color))

  /**
   *
   *
   * @param argbInt
   *
   * @return
   */
  final
  def colorComponentMapFrom(argbInt: Int): Map[Symbol, Double] =
    colorComponentMapFrom(rgbaTupleFrom(argbInt))

  /**
   *
   *
   * @param rgbTuple
   *
   * @return
   */
  final
  def colorComponentMapFrom(rgbTuple: (Int, Int, Int)): Map[Symbol, Double] =
    colorComponentMapFrom(
      rgbTuple._1,
      rgbTuple._2,
      rgbTuple._3,
      ColorValidator.MaximumOpacity)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   *
   * @return
   */
  final
  def colorComponentMapFrom(
      red: Int,
      green: Int,
      blue: Int): Map[Symbol, Double] = {

    colorComponentMapFrom(red, green, blue, ColorValidator.MaximumOpacity)
  }

  /**
   *
   *
   * @param rgbaTuple
   *
   * @return
   */
  final
  def colorComponentMapFrom(rgbaTuple: (Int, Int, Int, Int)): Map[Symbol, Double] =
    colorComponentMapFrom(rgbaTuple._1, rgbaTuple._2, rgbaTuple._3, rgbaTuple._4)

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
  def colorComponentMapFrom(
      red: Int,
      green: Int,
      blue: Int,
      opacity: Int): Map[Symbol, Double] = {

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
