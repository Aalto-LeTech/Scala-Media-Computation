package aalto.smcl.bitmaps.operations


import aalto.smcl.bitmaps.BitmapSettingKeys.{DefaultBitmapHeightInPixels, DefaultBitmapWidthInPixels}
import aalto.smcl.common.{GS, MetaInformationMap}
import aalto.smcl.platform.PlatformBitmapBuffer




/**
 * An operation to create a bitmap buffer of a given size.
 *
 * @param widthInPixels
 * @param heightInPixels
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class CreateBitmap(
    widthInPixels: Int = GS.intFor(DefaultBitmapWidthInPixels),
    heightInPixels: Int = GS.intFor(DefaultBitmapHeightInPixels))
    extends AbstractOperation with BufferProviderOperation with Immutable {

  /** Information about this [[BufferProviderOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "width" -> Option("${widthInPixels} px"),
    "height" -> Option("${heightInPixels} px")
  ))


  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[BufferProviderOperation]].
   *
   * @return
   */
  override def createStaticBuffer(): PlatformBitmapBuffer =
    PlatformBitmapBuffer(widthInPixels, heightInPixels)

}
