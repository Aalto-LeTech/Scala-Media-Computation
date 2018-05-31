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


import java.util.Locale

import scala.util.{Failure, Try}

import javax.imageio.ImageIO

import smcl.infrastructure.BitmapBufferAdapter
import smcl.infrastructure.exceptions._
import smcl.infrastructure.jvmawt.imageio.{LocalPathImageLoader, ServerImageLoader}
import smcl.pictures.BitmapValidator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class DefaultAWTImageProvider(bitmapValidator: BitmapValidator)
    extends AWTImageProvider {

  /** */
  private
  val SupportedInternetProtocols: Seq[String] = Seq("http", "https")

  /** */
  private
  val SupportedInternetProtocolsWithColonAndSlashes: Seq[String] =
    SupportedInternetProtocols.map(_.trim.toLowerCase + "://")

  /** */
  lazy val SupportedReadableMimeTypes: Seq[String] =
    ImageIO.getReaderMIMETypes.toSeq

  /** */
  lazy val SupportedReadableFileExtensions: Seq[String] =
    ImageIO.getReaderFileSuffixes.map(_.toLowerCase(Locale.getDefault)).toSeq

  /** */
  lazy val SupportedWritableMimeTypes: Seq[String] =
    ImageIO.getWriterMIMETypes.toSeq

  /** */
  lazy val SupportedWritableFileExtensions: Seq[String] =
    ImageIO.getWriterFileSuffixes.map(_.toLowerCase(Locale.getDefault)).toSeq

  /**
   *
   *
   * @param sourceResourcePath
   *
   * @return
   */
  override
  def tryToLoadImage(sourceResourcePath: String): Try[BitmapBufferAdapter] = {
    var result: Try[BitmapBufferAdapter] =
      Failure[BitmapBufferAdapter](ImageNotFoundError(sourceResourcePath, null))

    if (representsSupportedInternetProtocol(sourceResourcePath)) {
      result = tryToLoadImageFromServer(sourceResourcePath)
      if (isAcceptableLoadingResult(result))
        return result
    }

    result = tryToLoadImageFromLocalPath(sourceResourcePath)
    if (isAcceptableLoadingResult(result))
      return result

    tryToLoadImageFromResources(sourceResourcePath)
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
    var result: Try[Seq[Try[BitmapBufferAdapter]]] =
      Failure[Seq[Try[BitmapBufferAdapter]]](ImageNotFoundError(sourceResourcePath, null))

    if (representsSupportedInternetProtocol(sourceResourcePath)) {
      result = tryToLoadImagesFromServer(sourceResourcePath)
      if (isAcceptableLoadingResult(result))
        return result
    }

    result = tryToLoadImagesFromLocalPath(sourceResourcePath)
    if (isAcceptableLoadingResult(result))
      return result

    tryToLoadImagesFromResources(sourceResourcePath)
  }

  /**
   *
   *
   * @param path
   *
   * @return
   */
  private
  def representsSupportedInternetProtocol(path: String): Boolean = {
    SupportedInternetProtocolsWithColonAndSlashes.exists(path.toLowerCase.startsWith)
  }

  /**
   *
   *
   * @param result
   * @tparam A
   *
   * @return
   */
  private
  def isAcceptableLoadingResult[A](result: Try[A]): Boolean = {
    result.isSuccess ||
        (result.isFailure &&
            !result.failed.get.isInstanceOf[ImageNotFoundError])
  }

  /**
   *
   *
   * @param path
   *
   * @return
   */
  override
  def tryToLoadImageFromLocalPath(path: String): Try[BitmapBufferAdapter] = {
    val loader = new LocalPathImageLoader(bitmapValidator, SupportedReadableFileExtensions)

    val overallLoadingResult = Try(loader.tryToLoadFrom(path, shouldLoadOnlyFirst = true))
    if (overallLoadingResult.isFailure)
      return Failure(overallLoadingResult.failed.get)

    overallLoadingResult.get.head
  }

  /**
   *
   *
   * @param path
   *
   * @return
   */
  override
  def tryToLoadImagesFromLocalPath(path: String): Try[Seq[Try[BitmapBufferAdapter]]] = {
    val loader = new LocalPathImageLoader(bitmapValidator, SupportedReadableFileExtensions)

    Try(loader.tryToLoadFrom(path, shouldLoadOnlyFirst = false))
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

    ???
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

    ???
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

    val loader = new ServerImageLoader(bitmapValidator)
    val overallLoadingResult =
      Try(loader.tryToLoadFrom(absoluteSourceResourcePath, shouldLoadOnlyFirst = true))

    if (overallLoadingResult.isFailure)
      return Failure(overallLoadingResult.failed.get)

    overallLoadingResult.get.head
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

    val loader = new ServerImageLoader(bitmapValidator)

    Try(loader.tryToLoadFrom(absoluteSourceResourcePath, shouldLoadOnlyFirst = true))
  }

}
