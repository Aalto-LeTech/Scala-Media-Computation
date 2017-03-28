package aalto.smcl.bitmaps.operations


import aalto.smcl.infrastructure.{MetaInformationMap, BitmapBufferAdapter}




/**
 * Operation to process bitmap's colors by iterating them with a given color-altering function.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class IteratePixels(function: (Int, Int, Int, Int) => (Int, Int, Int, Int))
  extends AbstractOperation
  with OneSourceFilter
  with Immutable {

  /** Information about this [[Renderable]] instance */
  lazy val metaInformation = MetaInformationMap("IteratePixels", Map())

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[Buffered]].
   *
   * @param sources possible [[BitmapBufferAdapter]] instances used as sources
   * @return
   */
  override protected def createStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter = {
    require(sources.length == 1,
      s"Pixel iteration requires exactly one source image (provided: ${sources.length}).")

    sources(0).iteratePixelsWith(function)
  }

}
