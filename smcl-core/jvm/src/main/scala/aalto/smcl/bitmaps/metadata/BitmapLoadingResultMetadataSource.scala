/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package aalto.smcl.bitmaps.metadata


import java.awt.image.BufferedImage

import aalto.smcl.bitmaps.BitmapLoadingResult
import aalto.smcl.infrastructure.jvmawt.AwtBitmapBufferAdapter
import aalto.smcl.interfaces.awt.{StaticGeneralBitmapSource, StaticThumbnailBitmapSource}
import aalto.smcl.interfaces.{MetaInterfaceBase, ResourceMetadataSource, Timestamp}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[metadata]
case class BitmapLoadingResultMetadataSource(relatedBitmapLoadingResult: BitmapLoadingResult)
    extends MetaInterfaceBase
            with ResourceMetadataSource
            with StaticGeneralBitmapSource
            with StaticThumbnailBitmapSource {

  /**
   *
   *
   * @param bitmapNumber
   */
  def validateBitmapNumber(bitmapNumber: Int): Unit = {
    require(bitmapNumber >= 0 && bitmapNumber <= relatedBitmapLoadingResult.bitmaps.length,
      s"The valid bitmap indices are 0 to ${relatedBitmapLoadingResult.bitmaps.indices.last}")
  }

  /**
   *
   *
   * @param bitmapNumber
   *
   * @return
   */
  override def resourceIdOption(bitmapNumber: Int = 0): Option[String] = {
    validateBitmapNumber(bitmapNumber)

    Some(relatedBitmapLoadingResult.bitmaps(bitmapNumber).uniqueIdentifier.identity)
  }

  /**
   *
   *
   * @param bitmapNumber
   *
   * @return
   */
  override def resourceAuthorsOption(bitmapNumber: Int = 0): Option[String] = {
    validateBitmapNumber(bitmapNumber)

    None
  }

  /**
   *
   *
   * @param bitmapNumber
   *
   * @return
   */
  override def resourceKeywordsOption(bitmapNumber: Int = 0): Option[String] = {
    validateBitmapNumber(bitmapNumber)

    None
  }

  /**
   *
   *
   * @param bitmapNumber
   *
   * @return
   */
  override def resourceTimestampOption(bitmapNumber: Int = 0): Option[Timestamp] = {
    validateBitmapNumber(bitmapNumber)

    Some(relatedBitmapLoadingResult.bitmaps(bitmapNumber).created)
  }

  /**
   *
   *
   * @param bitmapNumber
   *
   * @return
   */
  override def resourceDescriptionOption(bitmapNumber: Int = 0): Option[String] = {
    validateBitmapNumber(bitmapNumber)

    None
  }

  /**
   *
   *
   * @param bitmapNumber
   *
   * @return
   */
  override def resourceTitleOption(bitmapNumber: Int = 0): Option[String] = {
    validateBitmapNumber(bitmapNumber)

    None
  }

  /**
   *
   *
   * @param bitmapNumber
   *
   * @return
   */
  override def generalBitmapOption(bitmapNumber: Int = 0): Option[BufferedImage] = {
    validateBitmapNumber(bitmapNumber)

    Some(relatedBitmapLoadingResult.bitmaps(bitmapNumber).toRenderedRepresentation.asInstanceOf[AwtBitmapBufferAdapter].awtBufferedImage)
  }

  /**
   *
   *
   * @return
   */
  override def numberOfGeneralBitmaps(): Int = relatedBitmapLoadingResult.bitmaps.length

  /**
   *
   *
   * @return
   */
  override def generalBitmapsOption(): Option[Seq[BufferedImage]] =
    Some(relatedBitmapLoadingResult.bitmaps map (_.toRenderedRepresentation.asInstanceOf[AwtBitmapBufferAdapter].awtBufferedImage))

  /**
   *
   *
   * @param thumbnailNumber
   * @param maximumWidthInPixels
   * @param maximumHeightInPixels
   *
   * @return
   */
  override def thumbnailBitmapOption(
      thumbnailNumber: Int = 0,
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): Option[BufferedImage] = {

    validateBitmapNumber(thumbnailNumber)

    // TODO: After Bitmap can tell a suitable scaling factor for a given target size and has scaling operation, refactor the following code to utilize them

    val bitmap = relatedBitmapLoadingResult.bitmaps(thumbnailNumber)
    var buffer = bitmap.toRenderedRepresentation.asInstanceOf[AwtBitmapBufferAdapter].awtBufferedImage

    if (bitmap.widthInPixels > maximumWidthInPixels
        || bitmap.heightInPixels > maximumHeightInPixels) {

      val scalingFactor =
        if (bitmap.widthInPixels > maximumWidthInPixels)
          maximumWidthInPixels.toDouble / bitmap.widthInPixels
        else
          maximumHeightInPixels.toDouble / bitmap.heightInPixels

      buffer = bitmap.scale(scalingFactor).toRenderedRepresentation.asInstanceOf[AwtBitmapBufferAdapter].awtBufferedImage
    }

    Some(buffer)
  }

  /**
   *
   *
   * @param maximumWidthInPixels
   * @param maximumHeightInPixels
   *
   * @return
   */
  override def thumbnailBitmapsOption(
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): Option[Seq[BufferedImage]] = {

    Some(relatedBitmapLoadingResult.bitmaps.indices
        .map(thumbnailBitmapOption(_: Int, maximumWidthInPixels, maximumHeightInPixels).get))
  }

  /**
   *
   *
   * @return
   */
  override def numberOfThumbnailBitmaps(): Int = relatedBitmapLoadingResult.bitmaps.length

}
