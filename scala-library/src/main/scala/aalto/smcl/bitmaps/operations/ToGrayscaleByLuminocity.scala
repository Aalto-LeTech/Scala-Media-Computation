package aalto.smcl.bitmaps.operations


import aalto.smcl.infrastructure.{MetaInformationMap, PlatformBitmapBuffer}




/**
 * Operation to convert bitmap's colors into grayscale by luminocity.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class ToGrayscaleByLuminocity()
    extends AbstractOperation with OneSourceFilter with Immutable {

  /** */
  private val StandardRedWeight: Double = 0.21

  /** */
  private val StandardGreenWeight: Double = 0.72

  /** */
  private val StandardBlueWeight: Double = 0.07


  /** Information about this [[Renderable]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "redWeight" -> Option(StandardRedWeight.toString),
    "greenWeight" -> Option(StandardGreenWeight.toString),
    "blueWeight" -> Option(StandardBlueWeight.toString)
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
      val intensity = (
          StandardRedWeight * red +
              StandardGreenWeight * green +
              StandardBlueWeight * blue).toInt

      (intensity, intensity, intensity, opacity)
    }
  }

}
