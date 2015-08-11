package aalto.smcl.common


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ColorOps {

  /**
   *
   *
   * @param pixelInt
   * @param newRed
   * @return
   */
  @inline
  def withNewRedComponent(pixelInt: Int, newRed: Int): Int = {
    ColorValidator.validateRgbRedComponent(newRed)

    (pixelInt & ~ThirdByte) | (newRed << TwoBytes)
  }

  /**
   *
   *
   * @param pixelInt
   * @param newGreen
   * @return
   */
  @inline
  def withNewGreenComponent(pixelInt: Int, newGreen: Int): Int = {
    ColorValidator.validateRgbGreenComponent(newGreen)

    (pixelInt & ~SecondByte) | (newGreen << OneByte)
  }

  /**
   *
   *
   * @param pixelInt
   * @param newBlue
   * @return
   */
  @inline
  def withNewBlueComponent(pixelInt: Int, newBlue: Int): Int = {
    ColorValidator.validateRgbBlueComponent(newBlue)

    (pixelInt & ~FirstByte) | newBlue
  }

  /**
   *
   *
   * @param pixelInt
   * @param newOpacity
   * @return
   */
  @inline
  def withNewOpacityComponent(pixelInt: Int, newOpacity: Int): Int = {
    ColorValidator.validateRgbaOpacityComponent(newOpacity)

    (pixelInt & ~FourthByte) | (newOpacity << ThreeBytes)
  }

  /**
   *
   *
   * @param pixelInt
   * @return
   */
  @inline
  def redComponentOf(pixelInt: Int): Int =
    (pixelInt & ThirdByte) >>> TwoBytes

  /**
   *
   *
   * @param pixelInt
   * @return
   */
  @inline
  def greenComponentOf(pixelInt: Int): Int =
    (pixelInt & SecondByte) >>> OneByte

  /**
   *
   *
   * @param pixelInt
   * @return
   */
  @inline
  def blueComponentOf(pixelInt: Int): Int =
    pixelInt & FirstByte

  /**
   *
   *
   * @param pixelInt
   * @return
   */
  @inline
  def opacityComponentOf(pixelInt: Int): Int =
    pixelInt >>> ThreeBytes

  /**
   *
   *
   * @param color
   * @return
   */
  @inline
  def rgbaTupleFrom(color: RGBAColor): (Int, Int, Int, Int) =
    (color.red, color.green, color.blue, color.opacity)

  /**
   *
   *
   * @param pixelInt
   * @return
   */
  @inline
  def rgbaTupleFrom(pixelInt: Int): (Int, Int, Int, Int) =
    (redComponentOf(pixelInt),
        greenComponentOf(pixelInt),
        blueComponentOf(pixelInt),
        opacityComponentOf(pixelInt))

  /**
   *
   *
   * @param rgbaTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def normalizeRgba(rgbaTuple: (Int, Int, Int, Int)): (Double, Double, Double, Double) =
    (normalizeRgba(_: Int, _: Int, _: Int, _: Int)).tupled.apply(rgbaTuple)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @return
   */
  @inline
  def normalizeRgba(red: Int, green: Int, blue: Int, opacity: Int): (Double, Double, Double, Double) = {
    ColorValidator.validateRgbaColor(red, green, blue, opacity)

    def rgbSum: Double = (red + green + blue).toDouble

    (red.toDouble / rgbSum,
        green.toDouble / rgbSum,
        blue.toDouble / rgbSum,
        opacity.toDouble / ColorValidator.MaximumRgbaOpacity)
  }

  /**
   *
   *
   * @param color
   * @return
   */
  @inline
  def rgbTupleFrom(color: RGBAColor): (Int, Int, Int) =
    (color.red, color.green, color.blue)

  /**
   *
   *
   * @param pixelInt
   * @return
   */
  @inline
  def rgbTupleFrom(pixelInt: Int): (Int, Int, Int) =
    (redComponentOf(pixelInt),
        greenComponentOf(pixelInt),
        blueComponentOf(pixelInt))

  /**
   *
   *
   * @param rgbTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def normalizeRgb(rgbTuple: (Int, Int, Int)): (Double, Double, Double) =
    (normalizeRgb(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @return
   */
  @inline
  def normalizeRgb(red: Int, green: Int, blue: Int): (Double, Double, Double) = {
    ColorValidator.validateRgbColor(red, green, blue)

    def sum: Double = (red + green + blue).toDouble

    (red.toDouble / sum, green.toDouble / sum, blue.toDouble / sum)
  }

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param opacity
   * @return
   */
  @inline
  def pixelIntFrom(
      red: Int = ColorValidator.MinimumRgbRed,
      green: Int = ColorValidator.MinimumRgbGreen,
      blue: Int = ColorValidator.MinimumRgbBlue,
      opacity: Int = ColorValidator.MaximumRgbaOpacity): Int = {

    ColorValidator.validateRgbaColor(red, green, blue, opacity)

    (opacity << ThreeBytes) | (red << TwoBytes) | (green << OneByte) | blue
  }

  /**
   *
   *
   * @param color
   * @return
   */
  @inline
  def isBlack(color: RGBAColor): Boolean =
    isBlack(rgbTupleFrom(color))

  /**
   *
   *
   * @param pixelInt
   * @return
   */
  @inline
  def isBlack(pixelInt: Int): Boolean =
    isBlack(rgbTupleFrom(pixelInt))

  /**
   *
   *
   * @param rgbTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def isBlack(rgbTuple: (Int, Int, Int)): Boolean =
    (isBlack(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @return
   */
  @inline
  def isBlack(red: Int, green: Int, blue: Int): Boolean =
    red == ColorValidator.MinimumRgbRed.toDouble &&
        green == ColorValidator.MinimumRgbGreen.toDouble &&
        blue == ColorValidator.MinimumRgbBlue.toDouble

  /**
   *
   *
   * @param color
   * @return
   */
  @inline
  def isWhite(color: RGBAColor): Boolean =
    isWhite(rgbTupleFrom(color))

  /**
   *
   *
   * @param pixelInt
   * @return
   */
  @inline
  def isWhite(pixelInt: Int): Boolean =
    isWhite(rgbTupleFrom(pixelInt))

  /**
   *
   *
   * @param rgbTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def isWhite(rgbTuple: (Int, Int, Int)): Boolean =
    (isWhite(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @return
   */
  @inline
  def isWhite(red: Int, green: Int, blue: Int): Boolean =
    red == ColorValidator.MaximumRgbRed.toDouble &&
        green == ColorValidator.MaximumRgbGreen.toDouble &&
        blue == ColorValidator.MaximumRgbBlue.toDouble

  /**
   *
   *
   * @param color
   * @return
   */
  @inline
  def hsiHueInDegreesOf(color: RGBAColor): Double =
    hsiHueInDegreesFrom(rgbTupleFrom(color))

  /**
   *
   *
   * @param pixelInt
   * @return
   */
  @inline
  def hsiHueInDegreesOf(pixelInt: Int): Double =
    hsiHueInDegreesFrom(rgbTupleFrom(pixelInt))

  /**
   *
   *
   * @param rgbTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def hsiHueInDegreesFrom(rgbTuple: (Int, Int, Int)): Double =
    (hsiHueInDegreesFrom(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @return
   */
  @inline
  def hsiHueInDegreesFrom(red: Int, green: Int, blue: Int): Double = {
    import Math._

    ColorValidator.validateRgbColor(red, green, blue)

    def RmG = red - green
    def RmB = red - blue

    def root = sqrt(RmG * RmG + RmB * (green - blue))

    def angleCandidate = Math.rint(100.0 * toDegrees(acos((RmG + RmB) / (2.0 * root)))) / 100.0

    if (green >= blue) angleCandidate else FullCircleInDegrees - angleCandidate
  }

  /**
   *
   *
   * @param color
   * @return
   */
  @inline
  def hsiSaturationOf(color: RGBAColor): Double =
    hsiSaturationFrom(rgbTupleFrom(color))

  /**
   *
   *
   * @param pixelInt
   * @return
   */
  @inline
  def hsiSaturationOf(pixelInt: Int): Double =
    hsiSaturationFrom(rgbTupleFrom(pixelInt))

  /**
   *
   *
   * @param rgbTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def hsiSaturationFrom(rgbTuple: (Int, Int, Int)): Double =
    (hsiSaturationFrom(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @return
   */
  @inline
  def hsiSaturationFrom(red: Int, green: Int, blue: Int): Double = {
    ColorValidator.validateRgbColor(red, green, blue)

    if (isBlack(red, green, blue))
      return ColorValidator.MinimumHsiSaturation

    1.0 - 3.0 * (red.min(green).min(blue) / (red + green + blue))
  }

  /**
   *
   *
   * @param color
   * @return
   */
  @inline
  def hsiIntensityOf(color: RGBAColor): Double =
    hsiIntensityFrom(rgbTupleFrom(color))

  /**
   *
   *
   * @param pixelInt
   * @return
   */
  @inline
  def hsiIntensityOf(pixelInt: Int): Double =
    hsiIntensityFrom(rgbTupleFrom(pixelInt))

  /**
   *
   *
   * @param rgbTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  def hsiIntensityFrom(rgbTuple: (Int, Int, Int)): Double =
    (hsiIntensityFrom(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @return
   */
  @inline
  def hsiIntensityFrom(red: Int, green: Int, blue: Int): Double = {
    ColorValidator.validateRgbColor(red, green, blue)

    Math.rint(100 * ((red + green + blue).toDouble / 3.0)) / 100
  }

  /**
   *
   *
   * @param hueCandidateInDegrees
   * @return
   */
  @inline
  def normalizedHueInDegreesFrom(hueCandidateInDegrees: Double): Double =
    hueCandidateInDegrees % FullCircleInDegrees

  /**
   *
   *
   * @param pixelInt
   * @return
   */
  @inline
  def toHsi(pixelInt: Int): (Double, Double, Double) =
    rgbToHsi(rgbTupleFrom(pixelInt))

  /**
   *
   *
   * @param color
   * @return
   */
  @inline
  def toHsi(color: RGBAColor): (Double, Double, Double) =
    rgbToHsi(rgbTupleFrom(color))

  /**
   *
   *
   * @param rgbTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def rgbToHsi(rgbTuple: (Int, Int, Int)): (Double, Double, Double) =
    (rgbToHsi(_: Int, _: Int, _: Int)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @return
   */
  @inline
  def rgbToHsi(red: Int, green: Int, blue: Int): (Double, Double, Double) = {
    // The functions below will validate the parameters (three times, actually...)

    val hue = hsiHueInDegreesFrom(red, green, blue)
    val saturation = hsiSaturationFrom(red, green, blue)
    val intensity = hsiIntensityFrom(red, green, blue)

    (hue, saturation, intensity)
  }

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   * @return
   */
  @inline
  def hsiToColor(hueInDegrees: Double, saturation: Double, intensity: Double): RGBAColor =
    hsiToColor(hueInDegrees, saturation, intensity, ColorValidator.MaximumRgbaOpacity)

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def hsiToColor(hueInDegrees: Double, saturation: Double, intensity: Double, opacity: Int): RGBAColor =
    (RGBAColor.apply(_: Int, _: Int, _: Int, opacity)).tupled.apply(hsiToRgb(hueInDegrees, saturation, intensity))

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   * @return
   */
  @inline
  def hsiToPixelInt(hueInDegrees: Double, saturation: Double, intensity: Double): Int =
    hsiToPixelInt(hueInDegrees, saturation, intensity, ColorValidator.MaximumRgbaOpacity)

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def hsiToPixelInt(hueInDegrees: Double, saturation: Double, intensity: Double, opacity: Int): Int =
    (pixelIntFrom(_: Int, _: Int, _: Int, opacity)).tupled.apply(hsiToRgb(hueInDegrees, saturation, intensity))

  /**
   *
   *
   * @param hsiTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def hsiToRgb(hsiTuple: (Double, Double, Double)): (Int, Int, Int) =
    (hsiToRgb(_: Double, _: Double, _: Double)).tupled.apply(hsiTuple)

  /**
   *
   *
   * @param hueInDegrees
   * @param saturation
   * @param intensity
   * @return
   */
  @inline
  def hsiToRgb(hueInDegrees: Double, saturation: Double, intensity: Double): (Int, Int, Int) = {
    import Math._

    ColorValidator.validateHsiColor(hueInDegrees, saturation, intensity)

    // Special case
    if (saturation == 0.0) {
      val i: Int = round(intensity).toInt
      return (i, i, i)
    }

    val normalizedHueInDegrees = normalizedHueInDegreesFrom(hueInDegrees)

    val (aThirdOfCircleHueInDegrees: Double, finalOrder: ((Int, Int, Int) => (Int, Int, Int))) =
      normalizedHueInDegrees match {
        case hue: Double if hue <= 120.0 =>
          (hue,
              (x: Int, y: Int, z: Int) => (x, z, y))

        case hue: Double if hue <= 240.0 =>
          (hue - 120.0,
              (x: Int, y: Int, z: Int) => (y, x, z))

        case hue: Double =>
          (hue - 240.0,
              (x: Int, y: Int, z: Int) => (z, y, x))
      }

    val X = {
      val quotient =
        (saturation * toDegrees(cos(toRadians(aThirdOfCircleHueInDegrees)))) /
            toDegrees(cos(toRadians(60.0 - aThirdOfCircleHueInDegrees)))

      round(intensity * (1 + quotient)).toInt
    }

    val Y = round(intensity - intensity * saturation).toInt

    val Z = round(3.0 * intensity - X - Y).toInt

    val (red, green, blue) = finalOrder(X, Y, Z)

    if (!ColorValidator.rgbRedComponentIsInRange(red) ||
        !ColorValidator.rgbGreenComponentIsInRange(green) ||
        !ColorValidator.rgbBlueComponentIsInRange(blue)) {

      throw new SMCLInvalidHsiValueCombinationError(hueInDegrees, saturation, intensity)
    }

    (red, green, blue)
  }

  /**
   *
   *
   * @param color
   * @return
   */
  @inline
  def colorComponentMapFrom(color: RGBAColor): Map[Symbol, Double] =
    colorComponentMapFrom(rgbaTupleFrom(color))

  /**
   *
   *
   * @param pixelInt
   * @return
   */
  @inline
  def colorComponentMapFrom(pixelInt: Int): Map[Symbol, Double] =
    colorComponentMapFrom(rgbaTupleFrom(pixelInt))

  /**
   *
   *
   * @param rgbTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def colorComponentMapFrom(rgbTuple: (Int, Int, Int)): Map[Symbol, Double] =
    (colorComponentMapFrom(_: Int, _: Int, _: Int, ColorValidator.MaximumRgbaOpacity)).tupled.apply(rgbTuple)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @return
   */
  @inline
  def colorComponentMapFrom(red: Int, green: Int, blue: Int): Map[Symbol, Double] =
    colorComponentMapFrom(red, green, blue, ColorValidator.MaximumRgbaOpacity)

  /**
   *
   *
   * @param rgbaTuple
   * @return
   */
  //noinspection ScalaUnnecessaryParentheses
  @inline
  def colorComponentMapFrom(rgbaTuple: (Int, Int, Int, Int)): Map[Symbol, Double] =
    (colorComponentMapFrom(_: Int, _: Int, _: Int, _: Int)).tupled.apply(rgbaTuple)

  /**
   *
   *
   * @param red
   * @param green
   * @param blue
   * @param opacity
   * @return
   */
  @inline
  def colorComponentMapFrom(red: Int, green: Int, blue: Int, opacity: Int): Map[Symbol, Double] =
    Map[Symbol, Double](
      'red -> red.toDouble,
      'green -> green.toDouble,
      'blue -> blue.toDouble,
      'opacity -> opacity.toDouble,
      'hsiHue -> hsiHueInDegreesFrom(red, green, blue),
      'hsiSaturation -> hsiSaturationFrom(red, green, blue),
      'hsiIntensity -> hsiIntensityFrom(red, green, blue))

}
