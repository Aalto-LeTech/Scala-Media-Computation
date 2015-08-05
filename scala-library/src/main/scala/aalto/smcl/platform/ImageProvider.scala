package aalto.smcl.platform


import javax.imageio.ImageIO

import scala.util.Try




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ImageProvider {

  /**
   *
   *
   * @param filePath
   * @return
   */
  def tryToLoadImageFromFile(filePath: String): Try[PlatformBitmapBuffer] =
    Try(loadImageFromFile(filePath))


  /**
   *
   *
   * @param filePath
   * @return
   */
  private[platform] def loadImageFromFile(filePath: String): PlatformBitmapBuffer = {
    null
  }

  /**
   *
   *
   * @param url
   * @return
   */
  def tryToLoadImageFromURL(url: String): Try[PlatformBitmapBuffer] =
    Try(loadImageFromUrl(url))


  /**
   *
   *
   * @param url
   * @return
   */
  private[platform] def loadImageFromUrl(url: String): PlatformBitmapBuffer = {
    null
  }

  /**
   *
   *
   * @return
   */
  def supportedReadableMimeTypes(): Seq[String] = ImageIO.getReaderMIMETypes

  /**
   *
   *
   * @return
   */
  def supportedReadableFileExtensions(): Seq[String] = ImageIO.getReaderFileSuffixes

  /**
   *
   *
   * @return
   */
  def supportedWritableMimeTypes(): Seq[String] = ImageIO.getWriterMIMETypes

  /**
   *
   *
   * @return
   */
  def supportedWritableFileExtensions(): Seq[String] = ImageIO.getWriterFileSuffixes

}
