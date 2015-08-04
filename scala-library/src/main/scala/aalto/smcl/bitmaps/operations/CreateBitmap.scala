package aalto.smcl.bitmaps.operations


import aalto.smcl.bitmaps.BitmapSettingKeys.{DefaultBitmapHeightInPixels, DefaultBitmapWidthInPixels}
import aalto.smcl.bitmaps.immutable.primitives.Bitmap
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
    extends AbstractBufferProviderOperation with Immutable {

  /** This [[AbstractBufferProviderOperation]] does not have any child operations. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] = None

  /** Information about this [[AbstractBufferProviderOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "width" -> Option("${widthInPixels} px"),
    "height" -> Option("${heightInPixels} px")
  ))

  /**
   * Returns a new bitmap buffer of a size given to this [[Bitmap]] instance.
   */
  def buffer: PlatformBitmapBuffer = PlatformBitmapBuffer(widthInPixels, heightInPixels)

}
