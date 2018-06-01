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
import smcl.infrastructure.jvmawt.imageio.{JavaResourceImageLoader, LocalPathImageLoader, ServerImageLoader}
import smcl.pictures.BitmapValidator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class DefaultAWTImageProvider(
    private val urlProvider: URLProvider,
    private val httpConnectionProvider: HTTPConnectionProvider,
    private val bitmapValidator: BitmapValidator)
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
   * @param sourceResourcePath
   *
   * @return
   */
  override
  def tryToLoadImage(sourceResourcePath: String): Try[BitmapBufferAdapter] = {
    pickFirstResult(
      tryToLoadImages(
        sourceResourcePath,
        shouldLoadOnlyFirst = true))
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
    tryToLoadImages(
      sourceResourcePath,
      shouldLoadOnlyFirst = false)
  }

  /** */
  private
  type LoaderMethod = (String, Boolean) => Try[Seq[Try[BitmapBufferAdapter]]]

  /** */
  private
  type LoadingCondition = Option[String => Boolean]




  /**
   *
   *
   * @param loader
   * @param condition
   */
  private
  case class LoaderAndCondition(
      loader: LoaderMethod,
      condition: LoadingCondition)




  /** */
  private
  val LoadersAndConditions: Seq[LoaderAndCondition] = Seq(
    LoaderAndCondition(tryToLoadImagesFromServer, Some(representsSupportedInternetProtocol)),
    LoaderAndCondition(tryToLoadImagesFromLocalPath, None),
    LoaderAndCondition(tryToLoadImagesFromResources, None)
  )

  /**
   *
   *
   * @param sourceResourcePath
   * @param shouldLoadOnlyFirst
   *
   * @return
   */
  private
  def tryToLoadImages(
      sourceResourcePath: String,
      shouldLoadOnlyFirst: Boolean): Try[Seq[Try[BitmapBufferAdapter]]] = {

    LoadersAndConditions.filter(isTriableLoader(_, sourceResourcePath)).foreach{lac =>
      val result = lac.loader(sourceResourcePath, shouldLoadOnlyFirst)
      if (isAcceptableOverallLoadingResult(result))
        return result
    }

    Failure(ImageNotFoundError(sourceResourcePath, null))
  }

  /**
   *
   *
   * @param lac
   * @param sourceResourcePath
   *
   * @return
   */
  private
  def isTriableLoader(
      lac: LoaderAndCondition,
      sourceResourcePath: String): Boolean = {

    lac.condition.isEmpty || lac.condition.get(sourceResourcePath)
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
  def isAcceptableOverallLoadingResult[A](result: Try[A]): Boolean = {
    result.isSuccess ||
        (result.isFailure &&
            !result.failed.get.isInstanceOf[ImageNotFoundError])
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

    pickFirstResult(
      tryToLoadImagesFromLocalPath(
        sourceResourcePath,
        shouldLoadOnlyFirst = true))
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

    tryToLoadImagesFromLocalPath(
      sourceResourcePath,
      shouldLoadOnlyFirst = false)
  }

  /**
   *
   *
   * @param sourceResourcePath
   * @param shouldLoadOnlyFirst
   *
   * @return
   */
  private
  def tryToLoadImagesFromLocalPath(
      sourceResourcePath: String,
      shouldLoadOnlyFirst: Boolean): Try[Seq[Try[BitmapBufferAdapter]]] = {

    val loader = new LocalPathImageLoader(
      sourceResourcePath,
      shouldLoadOnlyFirst,
      bitmapValidator,
      SupportedReadableFileExtensions)

    Try(loader.load)
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

    pickFirstResult(
      tryToLoadImagesFromResources(
        relativeSourceResourcePath,
        shouldLoadOnlyFirst = true))
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

    tryToLoadImagesFromResources(
      relativeSourceResourcePath,
      shouldLoadOnlyFirst = false)
  }

  /**
   *
   *
   * @param relativeSourceResourcePath
   * @param shouldLoadOnlyFirst
   *
   * @return
   */
  private
  def tryToLoadImagesFromResources(
      relativeSourceResourcePath: String,
      shouldLoadOnlyFirst: Boolean): Try[Seq[Try[BitmapBufferAdapter]]] = {

    val loader = new JavaResourceImageLoader(
      relativeSourceResourcePath,
      shouldLoadOnlyFirst,
      bitmapValidator,
      SupportedReadableFileExtensions)

    Try(loader.load)
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

    pickFirstResult(
      tryToLoadImagesFromServer(
        absoluteSourceResourcePath,
        shouldLoadOnlyFirst = true))
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

    tryToLoadImagesFromServer(
      absoluteSourceResourcePath,
      shouldLoadOnlyFirst = false)
  }

  /**
   *
   *
   * @param absoluteSourceResourcePath
   * @param shouldLoadOnlyFirst
   *
   * @return
   */
  private
  def tryToLoadImagesFromServer(
      absoluteSourceResourcePath: String,
      shouldLoadOnlyFirst: Boolean): Try[Seq[Try[BitmapBufferAdapter]]] = {

    val loader = new ServerImageLoader(
      absoluteSourceResourcePath,
      shouldLoadOnlyFirst,
      urlProvider,
      httpConnectionProvider,
      bitmapValidator)

    Try(loader.load)
  }

  /**
   *
   *
   * @param overallLoadingResult
   *
   * @return
   */
  private
  def pickFirstResult(
      overallLoadingResult: Try[Seq[Try[BitmapBufferAdapter]]]): Try[BitmapBufferAdapter] = {

    if (overallLoadingResult.isFailure)
      return Failure(overallLoadingResult.failed.get)

    overallLoadingResult.get.head
  }

}
