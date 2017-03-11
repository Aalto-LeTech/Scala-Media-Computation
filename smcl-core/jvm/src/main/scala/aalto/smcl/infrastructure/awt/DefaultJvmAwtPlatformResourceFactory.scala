package aalto.smcl.infrastructure.awt


import scala.util.{Either, Try}

import aalto.smcl.colors.RGBAColor
import aalto.smcl.infrastructure._


/**
  *
  *
  * @author Aleksi Lukkarinen
  */
class DefaultJvmAwtPlatformResourceFactory extends PlatformResourceFactory {

  /**  */
  private val _AwtImageProvider = new AwtImageProvider()

  /**  */
  private val _AwtScreenInformationProvider = new AwtScreenInformationProvider()


  /**
   *
   *
   * @return
   */
  override def screenInformationProvider: ScreenInformationProvider = _AwtScreenInformationProvider

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
    * @param sourceResourcePath
    * @return
    */
  override def tryToLoadImagesFromPath(sourceResourcePath: String): Try[Seq[Either[Throwable, BitmapBufferAdapter]]] =
    _AwtImageProvider.tryToLoadImagesFromFile(sourceResourcePath)

}
