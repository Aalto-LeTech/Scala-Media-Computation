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
import smcl.infrastructure.exceptions.ImplementationNotSetError
import smcl.modeling.Len




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
  override
  def availableFonts: Seq[String] = {
    implementation.availableFonts
  }

  /**
   *
   *
   * @return
   */
  override
  def createCurrentTimestamp: Timestamp = {
    implementation.createCurrentTimestamp
  }

  /**
   *
   *
   * @param width
   * @param height
   *
   * @return
   */
  override
  def createPlatformBitmapBuffer(
      width: Len,
      height: Len): BitmapBufferAdapter = {

    implementation.createPlatformBitmapBuffer(width, height)
  }

  /**
   *
   *
   * @return
   */
  override
  def createPlatformColor(source: Color): ColorAdapter = {
    implementation.createPlatformColor(source)
  }


  /**
   *
   *
   * @return
   */
  override
  def createUniqueIdString: String = {
    implementation.createUniqueIdString
  }

  /**
   *
   *
   * @return
   */
  override
  def screenInformationProvider: ScreenInformationProvider = {
    implementation.screenInformationProvider
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
    implementation.tryToLoadImage(sourceResourcePath)
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
    implementation.tryToLoadImages(sourceResourcePath)
  }

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  override
  def tryToLoadImageFromLocalPath(
      sourceResourcePath: String): Try[BitmapBufferAdapter] = {

    implementation.tryToLoadImageFromLocalPath(sourceResourcePath)
  }

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  override
  def tryToLoadImagesFromLocalPath(
      sourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]] = {

    implementation.tryToLoadImagesFromLocalPath(sourceResourcePath)
  }

  /**
   *
   *
   * @param relativeSourceResourcePath
   *
   * @return
   */
  override
  def tryToLoadImageFromResources(relativeSourceResourcePath: String): Try[BitmapBufferAdapter] = {
    implementation.tryToLoadImageFromResources(relativeSourceResourcePath)
  }

  /**
   *
   *
   * @param relativeSourceResourcePath
   *
   * @return
   */
  override
  def tryToLoadImagesFromResources(relativeSourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]] = {
    implementation.tryToLoadImagesFromResources(relativeSourceResourcePath)
  }

  /**
   *
   *
   * @param absoluteSourceResourcePath
   *
   * @return
   */
  override
  def tryToLoadImageFromServer(absoluteSourceResourcePath: String): Try[BitmapBufferAdapter] = {
    implementation.tryToLoadImageFromServer(absoluteSourceResourcePath)
  }

  /**
   *
   *
   * @param absoluteSourceResourcePath
   *
   * @return
   */
  override
  def tryToLoadImagesFromServer(absoluteSourceResourcePath: String): Try[Seq[Try[BitmapBufferAdapter]]] = {
    implementation.tryToLoadImagesFromServer(absoluteSourceResourcePath)
  }

  /**
   *
   *
   * @param factoryImplementation
   */
  def setImplementation(factoryImplementation: PlatformResourceFactory): Unit = {
    _implementation = Some(factoryImplementation)
  }

  /**
   *
   *
   * @return
   */
  private
  def implementation: PlatformResourceFactory = {
    _implementation.getOrElse(
      throw ImplementationNotSetError("DefaultPlatformResourceFactory"))
  }

}
