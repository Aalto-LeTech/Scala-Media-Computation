package aalto.smcl.infrastructure


import scala.util.{Either, Try}

import aalto.smcl.colors.RGBAColor




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
trait PlatformResourceFactory {

  /**
   *
   *
   * @return
   */
  def screenInformationProvider: ScreenInformationProvider


  /**
   *
   *
   * @return
   */
  def createPlatformAffineTransformation: AffineTransformationAdapter

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @return
   */
  def createPlatformBitmapBuffer(widthInPixels: Int, heightInPixels: Int): BitmapBufferAdapter

  /**
   *
   *
   * @return
   */
  def createPlatformColor(source: RGBAColor): ColorAdapter

  /**
   *
   *
   * @param sourceResourcePath
   * @return
   */
  def tryToLoadImagesFromPath(sourceResourcePath: String): Try[Seq[Either[Throwable, BitmapBufferAdapter]]]

}
