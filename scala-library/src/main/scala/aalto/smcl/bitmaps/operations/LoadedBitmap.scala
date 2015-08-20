package aalto.smcl.bitmaps.operations


import aalto.smcl.common.MetaInformationMap
import aalto.smcl.platform.PlatformBitmapBuffer




/**
 *
 *
 * @param bitmap
 * @param resourcePathOption
 * @param bitmapIndexInResourceOption
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class LoadedBitmap(
  bitmap: PlatformBitmapBuffer,
  resourcePathOption: Option[String],
  bitmapIndexInResourceOption: Option[Int])
  extends AbstractOperation with BufferProvider with Immutable {

  require(resourcePathOption != null, "The resource path argument has to be a String or None (was null).")
  require(bitmapIndexInResourceOption != null, "The bitmap index argument has to be an Int or None (was null).")

  /** Width of the provided buffer in pixels. */
  override def widthInPixels: Int = bitmap.widthInPixels

  /** Height of the provided buffer in pixels. */
  override def heightInPixels: Int = bitmap.heightInPixels

  /** Information about this [[Renderable]] instance */
  lazy override val metaInformation = MetaInformationMap(Map(
    "resourcePath" -> Option(resourcePathOption.getOrElse("<unknown>")),
    "imageIndexInFile" -> Option(bitmapIndexInResourceOption.fold("<undefined>") {
      _.toString
    })))

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[BufferProvider]].
   *
   * @return
   */
  override def createStaticBuffer(sources: PlatformBitmapBuffer*): PlatformBitmapBuffer = bitmap

  /**
   * Returns the buffer from which the provided buffer copies are made.
   * Users of this trait must provide an implementation, which returns
   * a [[PlatformBitmapBuffer]] instance always after instantiation of
   * the class claiming to provide the buffer.
   *
   * @return    bitmap buffer to be made copies of for providees
   */
  override protected def provideNewBufferToBeCopiedForProvidees(): PlatformBitmapBuffer =
    getOrCreateStaticBuffer()

}
