package aalto.smcl.bitmaps.operations


import aalto.smcl.infrastructure.{MetaInformationMap, PlatformBitmapBuffer}




/**
 * Operation to apply a given pixel snapshot buffer to a bitmap.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class ApplyPixelSnapshot(snapshotBuffer: PlatformBitmapBuffer)
  extends AbstractOperation
  with OneSourceFilter
  with Immutable {

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
  override protected def createStaticBuffer(sources: PlatformBitmapBuffer*): PlatformBitmapBuffer =
    snapshotBuffer

}
