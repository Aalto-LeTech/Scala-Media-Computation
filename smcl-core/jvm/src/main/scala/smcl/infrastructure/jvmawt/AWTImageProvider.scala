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


import scala.util.Try

import smcl.infrastructure.BitmapBufferAdapter




/**
 *
 */
trait AWTImageProvider {

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
  def tryToLoadImageFromLocalPath(
      sourceResourcePath: String): Try[BitmapBufferAdapter]

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  def tryToLoadImagesFromLocalPath(
      sourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]]

  /**
   *
   *
   * @param relativeSourceResourcePath
   *
   * @return
   */
  def tryToLoadImageFromResources(
      relativeSourceResourcePath: String): Try[BitmapBufferAdapter]

  /**
   *
   *
   * @param relativeSourceResourcePath
   *
   * @return
   */
  def tryToLoadImagesFromResources(
      relativeSourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]]

  /**
   *
   *
   * @param absoluteSourceResourcePath
   *
   * @return
   */
  def tryToLoadImageFromServer(
      absoluteSourceResourcePath: String): Try[BitmapBufferAdapter]

  /**
   *
   *
   * @param absoluteSourceResourcePath
   *
   * @return
   */
  def tryToLoadImagesFromServer(
      absoluteSourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]]

}
