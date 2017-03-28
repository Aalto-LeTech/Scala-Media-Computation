package aalto.smcl.interfaces.awt


import java.awt.image.BufferedImage




/**
 * Interface for querying objects for thumbnail bitmap representation.
 *
 * @author Aleksi Lukkarinen
 */
trait StaticThumbnailBitmapSource {

  /**
   *
   *
   * @return
   */
  def numberOfThumbnailBitmaps(): Int

  /**
   *
   *
   * @param maximumWidthInPixels
   * @param maximumHeightInPixels
   * @return
   */
  def thumbnailBitmapsOption(
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): Option[Seq[BufferedImage]]

  /**
   *
   *
   * @param thumbnailNumber
   * @param maximumWidthInPixels
   * @param maximumHeightInPixels
   * @return
   */
  def thumbnailBitmapOption(
      thumbnailNumber: Int = 0,
      maximumWidthInPixels: Int,
      maximumHeightInPixels: Int): Option[BufferedImage]

}
