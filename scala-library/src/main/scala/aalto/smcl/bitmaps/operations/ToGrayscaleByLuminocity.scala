package aalto.smcl.bitmaps.operations

import aalto.smcl.colors.ColorValidator
import aalto.smcl.infrastructure.{CommonValidators, MetaInformationMap, PlatformBitmapBuffer}




/**
 * Operation to convert bitmap's colors into grayscale using given component weights.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class ToGrayscaleByLuminocity(
    redWeight: Double,
    greenWeight: Double,
    blueWeight: Double)
    extends AbstractOperation with OneSourceFilter with Immutable {

  CommonValidators.validateZeroToOneFactor(redWeight, Option("Red weight"))
  CommonValidators.validateZeroToOneFactor(greenWeight, Option("Green weight"))
  CommonValidators.validateZeroToOneFactor(blueWeight, Option("Blue weight"))

  ColorValidator.validateRgbColorWeightCombination(redWeight, greenWeight, blueWeight)


  /** Information about this [[Renderable]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
  ))

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[Buffered]].
   *
   * @param sources     possible [[PlatformBitmapBuffer]] instances used as sources
   * @return
   */
  override protected def createStaticBuffer(sources: PlatformBitmapBuffer*): PlatformBitmapBuffer = {
    require(sources.length == 1,
      s"Grayscale conversion requires exactly one source image (provided: ${sources.length}).")

    sources(0).iteratePixelsWith {(red, green, blue, opacity) =>
      val intensity = (redWeight * red + greenWeight * green + blueWeight * blue).toInt

      (intensity, intensity, intensity, opacity)
    }
  }

}
