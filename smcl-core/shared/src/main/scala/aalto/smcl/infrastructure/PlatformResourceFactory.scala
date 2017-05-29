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

package aalto.smcl.infrastructure


import scala.util.Try

import aalto.smcl.colors.rgb.Color
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
   *
   * @return
   */
  def createPlatformBitmapBuffer(widthInPixels: Int, heightInPixels: Int): BitmapBufferAdapter

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
  def tryToLoadImagesFromPath(sourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]]

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  def tryToLoadImageFromPath(sourceResourcePath: String): Try[BitmapBufferAdapter]

}
