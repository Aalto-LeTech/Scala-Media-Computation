package aalto.smcl.bitmaps.operations


import aalto.smcl.common._
import aalto.smcl.platform.PlatformBitmapBuffer




/**
 * Operation to flip a bitmap diagonally.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class FlipDiagonally()
  extends AbstractOperation with OneSourceFilter with Immutable {

  /** Information about this [[Renderable]] instance */
  lazy val metaInformation = MetaInformationMap(Map())


  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[Buffered]].
   *
   * @param sources     possible [[PlatformBitmapBuffer]] instances used as sources
   * @return
   */
  override protected def createStaticBuffer(sources: PlatformBitmapBuffer*): PlatformBitmapBuffer = {
    require(sources.length == 1, s"Flip requires exactly one source image (provided: ${sources.length}).")

    val transformation =
      AffineTransformation.forDiagonalFlipOf(
        sources(0).widthInPixels,
        sources(0).heightInPixels)

    sources(0).createTransfomedVersionWith(transformation)
  }

}
