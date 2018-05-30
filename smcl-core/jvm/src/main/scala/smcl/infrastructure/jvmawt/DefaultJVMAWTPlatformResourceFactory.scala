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

package smcl.infrastructure.jvmawt


import java.util.Calendar

import scala.util.Try

import smcl.colors.rgb.Color
import smcl.infrastructure._
import smcl.modeling.Len




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class DefaultJVMAWTPlatformResourceFactory(
    calendarProvider: JVMCalendarProvider,
    uuidProvider: JVMUniqueIDProvider,
    fontProvider: AWTFontProvider,
    imageProvider: AWTImageProvider,
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
   * @param width
   * @param height
   *
   * @return
   */
  def createPlatformBitmapBuffer(
      width: Len,
      height: Len): BitmapBufferAdapter = {

    AWTBitmapBufferAdapter(width, height)
  }

  /**
   *
   *
   * @return
   */
  override def createPlatformColor(source: Color): ColorAdapter = {
    AWTColorAdapter(source)
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
  override
  def tryToLoadImage(sourceResourcePath: String): Try[BitmapBufferAdapter] = {
    imageProvider.tryToLoadImage(sourceResourcePath)
  }

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  override
  def tryToLoadImages(sourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]] = {
    imageProvider.tryToLoadImages(sourceResourcePath)
  }

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  override def tryToLoadImagesFromLocalPath(
      sourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]] = {

    imageProvider.tryToLoadImagesFromLocalPath(sourceResourcePath)
  }

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  override def tryToLoadImageFromLocalPath(
      sourceResourcePath: String): Try[BitmapBufferAdapter] = {

    imageProvider.tryToLoadImageFromLocalPath(sourceResourcePath)
  }

  /**
   *
   *
   * @param relativeSourceResourcePath
   *
   * @return
   */
  override
  def tryToLoadImageFromResources(
      relativeSourceResourcePath: String): Try[BitmapBufferAdapter] = {

    imageProvider.tryToLoadImageFromResources(relativeSourceResourcePath)
  }

  /**
   *
   *
   * @param relativeSourceResourcePath
   *
   * @return
   */
  override
  def tryToLoadImagesFromResources(
      relativeSourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]] = {

    imageProvider.tryToLoadImagesFromResources(relativeSourceResourcePath)
  }

  /**
   *
   *
   * @param absoluteSourceResourcePath
   *
   * @return
   */
  override
  def tryToLoadImageFromServer(
      absoluteSourceResourcePath: String): Try[BitmapBufferAdapter] = {

    imageProvider.tryToLoadImageFromServer(absoluteSourceResourcePath)
  }

  /**
   *
   *
   * @param absoluteSourceResourcePath
   *
   * @return
   */
  override
  def tryToLoadImagesFromServer(
      absoluteSourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]] = {

    imageProvider.tryToLoadImagesFromServer(absoluteSourceResourcePath)
  }

}
