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

package aalto.smcl.infrastructure.jvmawt


import java.util.Calendar

import scala.util.Try

import aalto.smcl.colors.rgb.Color
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
    val screenInformationProvider: ScreenInformationProvider)
    extends PlatformResourceFactory {

  /**
   *
   *
   * @return
   */
  override def availableFonts: Seq[String] = {
    fontProvider.availableFonts map {_.getFontName}
  }


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
  override def createPlatformAffineTransformation: AffineTransformationAdapter = {
    AwtAffineTransformationAdapter()
  }

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  def createPlatformBitmapBuffer(
      widthInPixels: Int,
      heightInPixels: Int): BitmapBufferAdapter = {

    AwtBitmapBufferAdapter(widthInPixels, heightInPixels)
  }

  /**
   *
   *
   * @return
   */
  override def createPlatformColor(source: Color): ColorAdapter = {
    AwtColorAdapter(source)
  }

  /**
   *
   *
   * @return
   */
  override def createUniqueIdString: String = {
    uuidProvider.newId
  }

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  override def tryToLoadImagesFromPath(
      sourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]] = {

    imageProvider.tryToLoadImagesFromFile(sourceResourcePath)
  }

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  override def tryToLoadImageFromPath(
      sourceResourcePath: String): Try[BitmapBufferAdapter] = {

    imageProvider.tryToLoadImageFromFile(sourceResourcePath)
  }

}
