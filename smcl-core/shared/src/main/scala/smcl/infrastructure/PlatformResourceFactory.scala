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

package smcl.infrastructure


import scala.util.Try

import smcl.colors.rgb.Color
import smcl.modeling.Len




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
   * @param width
   * @param height
   *
   * @return
   */
  def createPlatformBitmapBuffer(width: Len, height: Len): BitmapBufferAdapter

  /**
   *
   *
   * @return
   */
  def createPlatformColor(source: Color): ColorAdapter

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
   *
   * @return
   */
  def tryToLoadImage(sourceResourcePath: String): Try[BitmapBufferAdapter]

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  def tryToLoadImages(sourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]]

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  def tryToLoadImageFromLocalPath(sourceResourcePath: String): Try[BitmapBufferAdapter]

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  def tryToLoadImagesFromLocalPath(sourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]]

  /**
   *
   *
   * @param relativeSourceResourcePath
   *
   * @return
   */
  def tryToLoadImageFromResources(relativeSourceResourcePath: String): Try[BitmapBufferAdapter]

  /**
   *
   *
   * @param relativeSourceResourcePath
   *
   * @return
   */
  def tryToLoadImagesFromResources(relativeSourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]]

  /**
   *
   *
   * @param absoluteSourceResourcePath
   *
   * @return
   */
  def tryToLoadImageFromServer(absoluteSourceResourcePath: String): Try[BitmapBufferAdapter]

  /**
   *
   *
   * @param absoluteSourceResourcePath
   *
   * @return
   */
  def tryToLoadImagesFromServer(absoluteSourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]]

}
