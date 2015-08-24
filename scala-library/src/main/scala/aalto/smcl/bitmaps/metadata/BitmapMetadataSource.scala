package aalto.smcl.bitmaps.metadata


import java.awt.image.BufferedImage
import java.util.Date

import aalto.smcl.MetaInterfaceBase
import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.interfaces.{ResourceMetadataSource, StaticGeneralBitmapSource, StaticThumbnailBitmapSource}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class BitmapMetadataSource(relatedBitmap: Bitmap)
    extends MetaInterfaceBase
            with ResourceMetadataSource
            with StaticGeneralBitmapSource
            with StaticThumbnailBitmapSource {

  /** Number of bitmaps provided per Bitmap instance by this metadata source. */
  val OneBitmap = 1

  /** First possible image index. */
  val FirstImageIndex = 0

  /**
   *
   *
   * @param bitmapNumber
   */
  def validateBitmapNumber(bitmapNumber: Int): Unit = {
    require(bitmapNumber == FirstImageIndex, "This resource supports only one bitmap (index 0).")
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceIdOption(bitmapNumber: Int = FirstImageIndex): Option[String] = {
    validateBitmapNumber(bitmapNumber)

    Some(relatedBitmap.uniqueIdentifier.identity)
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceAuthorsOption(bitmapNumber: Int = FirstImageIndex): Option[String] = {
    validateBitmapNumber(bitmapNumber)

    None
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceKeywordsOption(bitmapNumber: Int = FirstImageIndex): Option[String] = {
    validateBitmapNumber(bitmapNumber)

    None
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceTimestampOption(bitmapNumber: Int = FirstImageIndex): Option[Date] = {
    validateBitmapNumber(bitmapNumber)

    Some(relatedBitmap.created.underlayingDate)
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceDescriptionOption(bitmapNumber: Int = FirstImageIndex): Option[String] = {
    validateBitmapNumber(bitmapNumber)

    None
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceTitleOption(bitmapNumber: Int = FirstImageIndex): Option[String] = {
    validateBitmapNumber(bitmapNumber)

    None
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def generalBitmapOption(bitmapNumber: Int = FirstImageIndex): Option[BufferedImage] = {
    validateBitmapNumber(bitmapNumber)

    Some(relatedBitmap.toRenderedRepresentation.awtBufferedImage)
  }

  /**
   *
   *
   * @return
   */
  override def numberOfGeneralBitmaps(): Int = OneBitmap

  /**
   *
   *
   * @return
   */
  override def generalBitmapsOption(): Option[Seq[BufferedImage]] =
    Some(Seq(relatedBitmap.toRenderedRepresentation.awtBufferedImage))

  /**
   *
   *
   * @param thumbnailNumber
   * @param maximumWidthInPixels
   * @param maximumHeightInPixels
   * @return
   */
  override def thumbnailBitmapOption(
      thumbnailNumber: Int = FirstImageIndex,
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): Option[BufferedImage] = {

    validateBitmapNumber(thumbnailNumber)

    var buffer = relatedBitmap.toRenderedRepresentation.awtBufferedImage

    if (relatedBitmap.widthInPixels > maximumWidthInPixels
        || relatedBitmap.heightInPixels > maximumHeightInPixels) {

      val scalingFactor =
        if (relatedBitmap.widthInPixels > maximumWidthInPixels)
          maximumWidthInPixels.toDouble / relatedBitmap.widthInPixels
        else
          maximumHeightInPixels.toDouble / relatedBitmap.heightInPixels

      buffer = relatedBitmap.scale(scalingFactor).toRenderedRepresentation.awtBufferedImage
    }

    Some(buffer)
  }

  /**
   *
   *
   * @param maximumWidthInPixels
   * @param maximumHeightInPixels
   * @return
   */
  override def thumbnailBitmapsOption(
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): Option[Seq[BufferedImage]] = {

    Some(Seq(thumbnailBitmapOption(0, maximumWidthInPixels, maximumHeightInPixels).get))
  }

  /**
   *
   *
   * @return
   */
  override def numberOfThumbnailBitmaps(): Int = OneBitmap

}
