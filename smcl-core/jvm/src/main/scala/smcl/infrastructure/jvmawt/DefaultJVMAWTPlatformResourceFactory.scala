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
import java.util.Calendar

import scala.util.Try

import javax.imageio.{ImageIO, ImageReader}

import smcl.colors.rgb.Color
import smcl.infrastructure._
import smcl.infrastructure.exceptions._
import smcl.modeling.Len
import smcl.pictures.exceptions.{MaximumBitmapSizeExceededError, MinimumBitmapSizeNotMetError}




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
  override
  def availableFonts: Seq[String] = {
    fontProvider.availableFonts map {_.getFontName}
  }


  /**
   *
   *
   * @return
   */
  override
  def createCurrentTimestamp: Timestamp = {
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
  override
  def createPlatformColor(source: Color): ColorAdapter = {
    AWTColorAdapter(source)
  }

  /**
   *
   *
   * @return
   */
  override
  def createUniqueIdString: String = {
    uuidProvider.newId
  }

  /**
   *
   *
   * @param sourceResourcePath
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

    imageProvider.tryToLoadImagesFromLocalPath(sourceResourcePath)
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

    imageProvider.tryToLoadImageFromLocalPath(sourceResourcePath)
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

    imageProvider.tryToLoadImageFromResources(relativeSourceResourcePath)
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

    imageProvider.tryToLoadImagesFromResources(relativeSourceResourcePath)
  }

  /**
   *
   *
   * @param absoluteSourceResourcePath
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

    imageProvider.tryToLoadImagesFromServer(absoluteSourceResourcePath)
  }

}
