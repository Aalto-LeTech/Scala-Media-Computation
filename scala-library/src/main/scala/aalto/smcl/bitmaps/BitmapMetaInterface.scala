package aalto.smcl.bitmaps


import aalto.smcl.{SMCL, MetaInterfaceBase}
import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.common.AffineTransformation
import aalto.smcl.interfaces.{ResourceMetadataSource, StaticGeneralBitmapSource, StaticThumbnailBitmapSource}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class BitmapMetaInterface(relatedBitmap: Bitmap)
    extends MetaInterfaceBase
            with ResourceMetadataSource
            with StaticGeneralBitmapSource
            with StaticThumbnailBitmapSource {

  SMCL.performInitialization()


  // @formatter:off
  import java.awt.image.{AffineTransformOp, BufferedImage}
  import java.util.Date
  // @formatter:on

  /** */
  private val ExactlyOneBitmap: Int = 1

  /** */
  private val FirstBitmapIndex: Int = 0


  /**
   * Throws `IllegalArgumentException` if bitmap number differs from zero.
   *
   * @param bitmapNumber                the bitmap number to check for validity
   *
   * @throws IllegalArgumentException   if bitmap number != 0
   */
  private[this] def checkBitmapNumber(bitmapNumber: Int): Unit =
    if (bitmapNumber != 0) {
      throw new IllegalArgumentException("This resource provides only one bitmap (index 0).")
    }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceIdOption(bitmapNumber: Int = FirstBitmapIndex): Option[String] = {
    checkBitmapNumber(bitmapNumber)

    Some(relatedBitmap.uniqueIdentifier.identity)
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceTitleOption(bitmapNumber: Int = FirstBitmapIndex): Option[String] = {
    checkBitmapNumber(bitmapNumber)
    None
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceDescriptionOption(bitmapNumber: Int = FirstBitmapIndex): Option[String] = {
    checkBitmapNumber(bitmapNumber)
    None
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceAuthorsOption(bitmapNumber: Int = FirstBitmapIndex): Option[String] = {
    checkBitmapNumber(bitmapNumber)
    None
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceKeywordsOption(bitmapNumber: Int = FirstBitmapIndex): Option[String] = {
    checkBitmapNumber(bitmapNumber)
    None
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def resourceTimestampOption(bitmapNumber: Int = FirstBitmapIndex): Option[Date] = {
    checkBitmapNumber(bitmapNumber)

    Option(relatedBitmap.created.underlayingDate)
  }

  /**
   *
   *
   * @param bitmapNumber
   * @return
   */
  override def generalBitmapOption(bitmapNumber: Int = FirstBitmapIndex): Option[BufferedImage] = {
    checkBitmapNumber(bitmapNumber)

    Option(relatedBitmap.toRenderedRepresentation.awtBufferedImage)
  }

  /**
   *
   *
   * @return
   */
  override def numberOfGeneralBitmaps(): Int = ExactlyOneBitmap

  /**
   *
   *
   * @return
   */
  override def generalBitmapsOption(): Option[Seq[BufferedImage]] =
    Option(Seq(generalBitmapOption().get))

  /**
   *
   *
   * @param thumbnailNumber
   * @param maximumWidthInPixels
   * @param maximumHeightInPixels
   * @return
   */
  override def thumbnailBitmapOption(
      thumbnailNumber: Int = FirstBitmapIndex,
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): Option[BufferedImage] = {

    checkBitmapNumber(thumbnailNumber)

    // TODO: After Bitmap can tell a suitable scaling factor for a given target size and has scaling operation, refactor the following code to utilize them

    BitmapValidator.validateBitmapSize(maximumWidthInPixels, maximumHeightInPixels)

    var bufferToReturn = relatedBitmap.toRenderedRepresentation.awtBufferedImage

    if (relatedBitmap.widthInPixels > maximumWidthInPixels
        || relatedBitmap.heightInPixels > maximumHeightInPixels) {

      val originalBitmap = bufferToReturn

      val scalingFactor: Double =
        if (relatedBitmap.widthInPixels > maximumWidthInPixels)
          maximumWidthInPixels.toDouble / relatedBitmap.widthInPixels
        else
          maximumHeightInPixels.toDouble / relatedBitmap.heightInPixels

      val transform = AffineTransformation.forFreeScalingOf(scalingFactor, scalingFactor)
      val transformOp = new AffineTransformOp(
        transform.platformAffineTransform.awtAffineTransformation,
        AffineTransformOp.TYPE_BICUBIC)

      bufferToReturn = transformOp.filter(bufferToReturn, null)
    }

    Option(bufferToReturn)
  }

  /**
   *
   *
   * @return
   */
  override def numberOfThumbnailBitmaps(): Int = ExactlyOneBitmap

  /**
   *
   *
   * @param maximumWidth
   * @param maximumHeight
   * @return
   */
  override def thumbnailBitmapsOption(maximumWidth: Int, maximumHeight: Int): Option[Seq[BufferedImage]] =
    Option(Seq(thumbnailBitmapOption(0, maximumWidth, maximumHeight).get))

}
