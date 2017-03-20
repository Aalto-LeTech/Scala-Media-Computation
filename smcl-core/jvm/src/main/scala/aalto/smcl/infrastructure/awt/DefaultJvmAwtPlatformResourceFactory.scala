package aalto.smcl.infrastructure.awt


import java.util.Calendar

import scala.util.{Either, Try}
import aalto.smcl.colors.RGBAColor
import aalto.smcl.infrastructure._
import aalto.smcl.interfaces.Timestamp




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class DefaultJvmAwtPlatformResourceFactory(
  calendarProvider: JvmCalendarProvider,
  uuidProvider: JvmUniqueIdProvider,
  fontProvider: AwtFontProvider,
  imageProvider: AwtImageProvider,
  val screenInformationProvider: ScreenInformationProvider) extends PlatformResourceFactory {

  /**
   *
   *
   * @return
   */
  override def availableFonts: Seq[String] = fontProvider.availableFonts map {_.getFontName}


  /**
   *
   *
   * @return
   */
  override def createCurrentTimestamp: Timestamp = {
    def currentMoment = calendarProvider.currentMoment

    DefaultTimestamp(
      day = currentMoment.get(Calendar.DAY_OF_MONTH),
      month = currentMoment.get(Calendar.MONTH),
      year = currentMoment.get(Calendar.YEAR),
      hour = currentMoment.get(Calendar.HOUR),
      minute = currentMoment.get(Calendar.MINUTE),
      second = currentMoment.get(Calendar.SECOND),
      milliSecond = currentMoment.get(Calendar.MILLISECOND)
    )
  }

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
  override def createUniqueIdString: String = uuidProvider.newId

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
