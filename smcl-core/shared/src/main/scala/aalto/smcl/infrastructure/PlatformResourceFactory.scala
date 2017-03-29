package aalto.smcl.infrastructure


import scala.util.{Either, Try}
import aalto.smcl.colors.RGBAColor
import aalto.smcl.interfaces.Timestamp




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
  def availableFonts: Seq[String]

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
   * @return
   */
  def createUniqueIdString: String

  /**
   *
   *
   * @return
   */
  def createCurrentTimestamp: Timestamp

  /**
   *
   *
   * @param sourceResourcePath
   * @return
   */
  def tryToLoadImagesFromPath(sourceResourcePath: String): Try[Seq[Either[Throwable, BitmapBufferAdapter]]]

}