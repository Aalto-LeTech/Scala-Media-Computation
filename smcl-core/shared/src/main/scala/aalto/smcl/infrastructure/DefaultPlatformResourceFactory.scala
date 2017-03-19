package aalto.smcl.infrastructure


import scala.util.{Either, Try}
import aalto.smcl.colors.RGBAColor
import aalto.smcl.infrastructure.exceptions.SMCLImplementationNotSetError
import aalto.smcl.interfaces.Timestamp




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object DefaultPlatformResourceFactory extends PlatformResourceFactory {

  /** The concrete factory class. */
  private var _implementation: Option[PlatformResourceFactory] = None

  /**
   *
   *
   * @return
   */
  override def createCurrentTimestamp(): Timestamp =
    implementation.createCurrentTimestamp

  /**
   *
   *
   * @return
   */
  override def createPlatformAffineTransformation: AffineTransformationAdapter =
    implementation.createPlatformAffineTransformation

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  override def createPlatformBitmapBuffer(widthInPixels: Int, heightInPixels: Int): BitmapBufferAdapter =
    implementation.createPlatformBitmapBuffer(widthInPixels, heightInPixels)

  /**
   *
   *
   * @return
   */
  override def createPlatformColor(source: RGBAColor): ColorAdapter =
    implementation.createPlatformColor(source)


  /**
   *
   *
   * @return
   */
  override def createUniqueIdString(): String = implementation.createUniqueIdString

  /**
   *
   *
   * @return
   */
  override def screenInformationProvider: ScreenInformationProvider =
    implementation.screenInformationProvider

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  override def tryToLoadImagesFromPath(sourceResourcePath: String): Try[Seq[Either[Throwable, BitmapBufferAdapter]]] =
    implementation.tryToLoadImagesFromPath(sourceResourcePath)

  /**
   *
   *
   * @param factoryImplementation
   */
  def setImplementation(factoryImplementation: PlatformResourceFactory): Unit =
    _implementation = Some(factoryImplementation)

  /**
   *
   *
   * @return
   */
  private def implementation: PlatformResourceFactory =
    _implementation.getOrElse(throw new SMCLImplementationNotSetError(DefaultPlatformResourceFactory))

}
