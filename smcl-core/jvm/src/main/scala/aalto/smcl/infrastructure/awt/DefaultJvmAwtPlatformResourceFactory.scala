package aalto.smcl.infrastructure.awt


import scala.util.{Either, Try}
import aalto.smcl.colors.RGBAColor
import aalto.smcl.infrastructure._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class DefaultJvmAwtPlatformResourceFactory(
  uuidProvider: JvmUniqueIdProvider,
  imageProvider: AwtImageProvider,
  screenInfoProvider: AwtScreenInformationProvider) extends PlatformResourceFactory {

  /**
   *
   *
   * @return
   */
  override def screenInformationProvider: ScreenInformationProvider = screenInfoProvider

  /**
   *
   *
   * @return
   */
  override def createPlatformAffineTransformation: AffineTransformationAdapter = AwtAffineTransformationAdapter()

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  def createPlatformBitmapBuffer(widthInPixels: Int, heightInPixels: Int): BitmapBufferAdapter =
    AwtBitmapBufferAdapter(widthInPixels, heightInPixels)

  /**
   *
   *
   * @return
   */
  override def createPlatformColor(source: RGBAColor): ColorAdapter = AwtColorAdapter(source)

  /**
   *
   *
   * @return
   */
  override def createUniqueIdString(): String = uuidProvider.newId()

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  // TODO: A terrible return type --> Redesign!!
  override def tryToLoadImagesFromPath(sourceResourcePath: String): Try[Seq[Either[Throwable, BitmapBufferAdapter]]] =
    imageProvider.tryToLoadImagesFromFile(sourceResourcePath)

}
