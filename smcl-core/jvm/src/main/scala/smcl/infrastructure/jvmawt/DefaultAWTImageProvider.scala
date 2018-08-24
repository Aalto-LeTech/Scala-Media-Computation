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


import java.io.InputStream
import java.net.HttpURLConnection
import java.util.Locale

import scala.util.{Failure, Try}

import javax.imageio.{ImageIO, ImageReader}

import smcl.infrastructure.BitmapBufferAdapter
import smcl.infrastructure.exceptions._
import smcl.infrastructure.jvmawt.imageio.{ImageInputStreamProvider, JavaResourceImageLoader, LocalPathImageLoader, ServerImageLoader}
import smcl.pictures.BitmapValidator
import smcl.pictures.exceptions.{MaximumBitmapSizeExceededError, MinimumBitmapSizeNotMetError}




/**
 *
 *
 * @param urlProvider
 * @param httpConnectionProvider
 * @param imageInputStreamProvider
 * @param bitmapValidator
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class DefaultAWTImageProvider(
    private val urlProvider: URLProvider,
    private val httpConnectionProvider: HTTPConnectionProvider,
    private val imageInputStreamProvider: ImageInputStreamProvider,
    private val bitmapValidator: BitmapValidator)
    extends AWTImageProvider {

  /** A timeout in milliseconds for establishing an HTTP connection. */
  val InternetConnectionTimeoutInMilliseconds: Int = 10000

  /** A timeout in milliseconds for reading data over an HTTP connection. */
  val InternetReadTimeoutInMilliseconds: Int = 10000

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

  /** */
  private
  val SupportedInternetProtocols: Seq[String] = Seq("http", "https")

  /** */
  private
  val SupportedInternetProtocolsWithColonAndSlashes: Seq[String] =
    SupportedInternetProtocols.map(_.trim.toLowerCase + "://")

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
  // When this is modified, the documented exception of the tryToLoadImages() below have to be updated as well!
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
   *
   * @throws AccessDeniedByServerError                   for HTTP status codes 401, 402, 403, 407, and 451
   * @throws EmptyFileError                              if the given path points to an empty file
   * @throws FileAttributeRetrievalFailedError           if the attributes of the file that the given path points to could not be retrieved
   * @throws FileNotFoundError                           if the given path points to a file that does not seem to exist
   * @throws ImageInputStreamNotCreatedError             if a cache file is needed but could not be created
   * @throws ImageNotFoundError                          for HTTP status codes 204, 205, 404, and 410, and if the requested resource could not be found
   * @throws ImageReaderNotRetrievedError                if the first suitable [[ImageReader]] cannot be retrieved
   * @throws MaximumBitmapSizeExceededError              if a bitmap is larger than the maximum allowed bitmap size
   * @throws MinimumBitmapSizeNotMetError                if a bitmap is smaller than the minimum allowed bitmap size
   * @throws OperationPreventedBySecurityManagerError    if retrieval of file attributes was prevented by a security manager
   * @throws PathDoesNotPointToRegularFileError          if the given path does not point to a regular file
   * @throws PathIsEmptyOrOnlyWhitespaceError            if the given path is empty or contains only whitespace
   * @throws PathIsNullError                             if the given path was actually null
   * @throws PathPointsToFolderError                     if the given path points to a folder
   * @throws PathPointsToSymbolicLinkError               if the given path poins to a symbolic link
   * @throws RedirectionRequestedError                   for HTTP status codes 301, 302, 307, and 308
   * @throws RequestedURITooLongError                    for HTTP status code 414
   * @throws ServerError                                 for all HTTP status codes beginning with 5
   * @throws SuitableImageReaderNotFoundError            if no suitable [[ImageReader]] is found
   * @throws SuitableImageStreamProviderNotFoundError    if [[ImageIO]] did not find a suitable image stream service provider instance
   * @throws TooManyRequestsToServerError                for HTTP status code 429
   * @throws UnknownFileExtensionError                   if the file extension is unknown
   * @throws UnknownHTTPResponseError                    for all HTTP status codes other than 200 that are not reported with other exceptions
   * @throws UnknownMIMETypeError                        if the MIME type sent by the server is not supported
   * @throws UnableToRetrieveDataOverHTTPConnectionError if an I/O error occurs while creating an [[InputStream]] or if the protocol to be used does not support input
   * @throws UnableToOpenHTTPConnectionError             if an [[HttpURLConnection]] instance could not be created; if the HTTP request method cannot be reset; if the request method is not valid; if the connection timeout expires before a connection has been established; or if an I/O error occurs during establishing the connection
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

    Failure(ImageNotFoundError(sourceResourcePath))
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
   *
   * @throws EmptyFileError                           if the given path points to an empty file
   * @throws FileAttributeRetrievalFailedError        if the attributes of the file that the given path points to could not be retrieved
   * @throws FileNotFoundError                        if the given path points to a file that does not seem to exist
   * @throws ImageInputStreamNotCreatedError          if a cache file is needed but could not be created
   * @throws ImageReaderNotRetrievedError             if the first suitable [[ImageReader]] cannot be retrieved
   * @throws MaximumBitmapSizeExceededError           if a bitmap is larger than the maximum allowed bitmap size
   * @throws MinimumBitmapSizeNotMetError             if a bitmap is smaller than the minimum allowed bitmap size
   * @throws OperationPreventedBySecurityManagerError if retrieval of file attributes was prevented by a security manager
   * @throws PathDoesNotPointToRegularFileError       if the given path does not point to a regular file
   * @throws PathIsEmptyOrOnlyWhitespaceError         if the given path is empty or contains only whitespace
   * @throws PathIsNullError                          if the given path was actually null
   * @throws PathPointsToFolderError                  if the given path points to a folder
   * @throws PathPointsToSymbolicLinkError            if the given path poins to a symbolic link
   * @throws SuitableImageReaderNotFoundError         if no suitable [[ImageReader]] is found
   * @throws SuitableImageStreamProviderNotFoundError if [[ImageIO]] did not find a suitable image stream service provider instance
   * @throws UnknownFileExtensionError                if the file extension is unknown
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
   *
   * @throws EmptyFileError                           if the given path points to an empty file
   * @throws FileAttributeRetrievalFailedError        if the attributes of the file that the given path points to could not be retrieved
   * @throws FileNotFoundError                        if the given path points to a file that does not seem to exist
   * @throws ImageInputStreamNotCreatedError          if a cache file is needed but could not be created
   * @throws ImageReaderNotRetrievedError             if the first suitable [[ImageReader]] cannot be retrieved
   * @throws MaximumBitmapSizeExceededError           if a bitmap is larger than the maximum allowed bitmap size
   * @throws MinimumBitmapSizeNotMetError             if a bitmap is smaller than the minimum allowed bitmap size
   * @throws OperationPreventedBySecurityManagerError if retrieval of file attributes was prevented by a security manager
   * @throws PathDoesNotPointToRegularFileError       if the given path does not point to a regular file
   * @throws PathIsEmptyOrOnlyWhitespaceError         if the given path is empty or contains only whitespace
   * @throws PathIsNullError                          if the given path was actually null
   * @throws PathPointsToFolderError                  if the given path points to a folder
   * @throws PathPointsToSymbolicLinkError            if the given path poins to a symbolic link
   * @throws SuitableImageReaderNotFoundError         if no suitable [[ImageReader]] is found
   * @throws SuitableImageStreamProviderNotFoundError if [[ImageIO]] did not find a suitable image stream service provider instance
   * @throws UnknownFileExtensionError                if the file extension is unknown
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
   *
   * @throws EmptyFileError                           if the given path points to an empty file
   * @throws FileAttributeRetrievalFailedError        if the attributes of the file that the given path points to could not be retrieved
   * @throws FileNotFoundError                        if the given path points to a file that does not seem to exist
   * @throws ImageInputStreamNotCreatedError          if a cache file is needed but could not be created
   * @throws ImageReaderNotRetrievedError             if the first suitable [[ImageReader]] cannot be retrieved
   * @throws MaximumBitmapSizeExceededError           if a bitmap is larger than the maximum allowed bitmap size
   * @throws MinimumBitmapSizeNotMetError             if a bitmap is smaller than the minimum allowed bitmap size
   * @throws OperationPreventedBySecurityManagerError if retrieval of file attributes was prevented by a security manager
   * @throws PathDoesNotPointToRegularFileError       if the given path does not point to a regular file
   * @throws PathIsEmptyOrOnlyWhitespaceError         if the given path is empty or contains only whitespace
   * @throws PathIsNullError                          if the given path was actually null
   * @throws PathPointsToFolderError                  if the given path points to a folder
   * @throws PathPointsToSymbolicLinkError            if the given path poins to a symbolic link
   * @throws SuitableImageReaderNotFoundError         if no suitable [[ImageReader]] is found
   * @throws SuitableImageStreamProviderNotFoundError if [[ImageIO]] did not find a suitable image stream service provider instance
   * @throws UnknownFileExtensionError                if the file extension is unknown
   */
  private
  def tryToLoadImagesFromLocalPath(
      sourceResourcePath: String,
      shouldLoadOnlyFirst: Boolean): Try[Seq[Try[BitmapBufferAdapter]]] = {

    val loader = new LocalPathImageLoader(
      sourceResourcePath,
      shouldLoadOnlyFirst,
      imageInputStreamProvider,
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
   *
   * @throws ImageNotFoundError                       if the requested resource could not be found
   * @throws ImageInputStreamNotCreatedError          if a cache file is needed but could not be created
   * @throws ImageReaderNotRetrievedError             if the first suitable [[ImageReader]] cannot be retrieved
   * @throws MaximumBitmapSizeExceededError           if a bitmap is larger than the maximum allowed bitmap size
   * @throws MinimumBitmapSizeNotMetError             if a bitmap is smaller than the minimum allowed bitmap size
   * @throws SuitableImageReaderNotFoundError         if no suitable [[ImageReader]] is found
   * @throws SuitableImageStreamProviderNotFoundError if [[ImageIO]] did not find a suitable image stream service provider instance
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
   *
   * @throws ImageNotFoundError                       if the requested resource could not be found
   * @throws ImageInputStreamNotCreatedError          if a cache file is needed but could not be created
   * @throws ImageReaderNotRetrievedError             if the first suitable [[ImageReader]] cannot be retrieved
   * @throws MaximumBitmapSizeExceededError           if a bitmap is larger than the maximum allowed bitmap size
   * @throws MinimumBitmapSizeNotMetError             if a bitmap is smaller than the minimum allowed bitmap size
   * @throws SuitableImageReaderNotFoundError         if no suitable [[ImageReader]] is found
   * @throws SuitableImageStreamProviderNotFoundError if [[ImageIO]] did not find a suitable image stream service provider instance
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
   *
   * @throws ImageNotFoundError                       if the requested resource could not be found
   * @throws ImageInputStreamNotCreatedError          if a cache file is needed but could not be created
   * @throws ImageReaderNotRetrievedError             if the first suitable [[ImageReader]] cannot be retrieved
   * @throws MaximumBitmapSizeExceededError           if a bitmap is larger than the maximum allowed bitmap size
   * @throws MinimumBitmapSizeNotMetError             if a bitmap is smaller than the minimum allowed bitmap size
   * @throws SuitableImageReaderNotFoundError         if no suitable [[ImageReader]] is found
   * @throws SuitableImageStreamProviderNotFoundError if [[ImageIO]] did not find a suitable image stream service provider instance
   */
  private
  def tryToLoadImagesFromResources(
      relativeSourceResourcePath: String,
      shouldLoadOnlyFirst: Boolean): Try[Seq[Try[BitmapBufferAdapter]]] = {

    val loader = new JavaResourceImageLoader(
      relativeSourceResourcePath,
      shouldLoadOnlyFirst,
      imageInputStreamProvider,
      bitmapValidator,
      SupportedReadableFileExtensions)

    Try(loader.load)
  }

  /**
   *
   *
   * @param absoluteSourceResourcePath
   *
   * @throws AccessDeniedByServerError                   for HTTP status codes 401, 402, 403, 407, and 451
   * @throws ImageInputStreamNotCreatedError             if a cache file is needed but could not be created
   * @throws ImageNotFoundError                          for HTTP status codes 204, 205, 404, and 410
   * @throws RedirectionRequestedError                   for HTTP status codes 301, 302, 307, and 308
   * @throws RequestedURITooLongError                    for HTTP status code 414
   * @throws ServerError                                 for all HTTP status codes beginning with 5
   * @throws SuitableImageStreamProviderNotFoundError    if [[ImageIO]] did not find a suitable image stream service provider instance
   * @throws TooManyRequestsToServerError                for HTTP status code 429
   * @throws UnknownHTTPResponseError                    for all HTTP status codes other than 200 that are not reported with other exceptions
   * @throws UnknownMIMETypeError                        if the MIME type sent by the server is not supported
   * @throws UnableToRetrieveDataOverHTTPConnectionError if an I/O error occurs while creating an [[InputStream]] or if the protocol to be used does not support input
   * @throws UnableToOpenHTTPConnectionError             if an [[HttpURLConnection]] instance could not be created; if the HTTP request method cannot be reset; if the request method is not valid; if the connection timeout expires before a connection has been established; or if an I/O error occurs during establishing the connection
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
   * @throws AccessDeniedByServerError                   for HTTP status codes 401, 402, 403, 407, and 451
   * @throws ImageInputStreamNotCreatedError             if a cache file is needed but could not be created
   * @throws ImageNotFoundError                          for HTTP status codes 204, 205, 404, and 410
   * @throws RedirectionRequestedError                   for HTTP status codes 301, 302, 307, and 308
   * @throws RequestedURITooLongError                    for HTTP status code 414
   * @throws ServerError                                 for all HTTP status codes beginning with 5
   * @throws SuitableImageStreamProviderNotFoundError    if [[ImageIO]] did not find a suitable image stream service provider instance
   * @throws TooManyRequestsToServerError                for HTTP status code 429
   * @throws UnknownHTTPResponseError                    for all HTTP status codes other than 200 that are not reported with other exceptions
   * @throws UnknownMIMETypeError                        if the MIME type sent by the server is not supported
   * @throws UnableToRetrieveDataOverHTTPConnectionError if an I/O error occurs while creating an [[InputStream]] or if the protocol to be used does not support input
   * @throws UnableToOpenHTTPConnectionError             if an [[HttpURLConnection]] instance could not be created; if the HTTP request method cannot be reset; if the request method is not valid; if the connection timeout expires before a connection has been established; or if an I/O error occurs during establishing the connection
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
   *
   * @throws AccessDeniedByServerError                   for HTTP status codes 401, 402, 403, 407, and 451
   * @throws ImageInputStreamNotCreatedError             if a cache file is needed but could not be created
   * @throws ImageNotFoundError                          for HTTP status codes 204, 205, 404, and 410
   * @throws RedirectionRequestedError                   for HTTP status codes 301, 302, 307, and 308
   * @throws RequestedURITooLongError                    for HTTP status code 414
   * @throws ServerError                                 for all HTTP status codes beginning with 5
   * @throws SuitableImageStreamProviderNotFoundError    if [[ImageIO]] did not find a suitable image stream service provider instance
   * @throws TooManyRequestsToServerError                for HTTP status code 429
   * @throws UnknownHTTPResponseError                    for all HTTP status codes other than 200 that are not reported with other exceptions
   * @throws UnknownMIMETypeError                        if the MIME type sent by the server is not supported
   * @throws UnableToRetrieveDataOverHTTPConnectionError if an I/O error occurs while creating an [[InputStream]] or if the protocol to be used does not support input
   * @throws UnableToOpenHTTPConnectionError             if an [[HttpURLConnection]] instance could not be created; if the HTTP request method cannot be reset; if the request method is not valid; if the connection timeout expires before a connection has been established; or if an I/O error occurs during establishing the connection
   */
  private
  def tryToLoadImagesFromServer(
      absoluteSourceResourcePath: String,
      shouldLoadOnlyFirst: Boolean): Try[Seq[Try[BitmapBufferAdapter]]] = {

    Try(urlProvider.createBasedOn(absoluteSourceResourcePath)).fold(
      {throwable =>
        return Failure(throwable)
      }, {url =>
        val loader = new ServerImageLoader(
          url,
          shouldLoadOnlyFirst,
          httpConnectionProvider,
          imageInputStreamProvider,
          bitmapValidator,
          SupportedReadableMimeTypes,
          InternetConnectionTimeoutInMilliseconds,
          InternetReadTimeoutInMilliseconds)

        Try(loader.load)
      })
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
