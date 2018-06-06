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

package smcl.infrastructure.jvmawt.imageio


import java.io.{IOException, InputStream}
import java.net.{HttpURLConnection, ProtocolException, SocketTimeoutException, URL, UnknownServiceException}
import java.util.Locale

import scala.annotation.switch
import scala.util.Try

import javax.imageio.ImageIO
import javax.imageio.stream.ImageInputStream

import smcl.infrastructure.exceptions._
import smcl.infrastructure.jvmawt.HTTPConnectionProvider
import smcl.infrastructure.{BitmapBufferAdapter, EnsureClosingOfAfter}
import smcl.pictures.BitmapValidator




/**
 *
 *
 * @param targetURL
 * @param shouldLoadOnlyFirst
 * @param httpConnectionCreator
 * @param bitmapValidator
 * @param supportedReadableMimeTypes
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class ServerImageLoader(
    private val targetURL: URL,
    private val shouldLoadOnlyFirst: Boolean,
    private val httpConnectionCreator: HTTPConnectionProvider,
    private val bitmapValidator: BitmapValidator,
    private val supportedReadableMimeTypes: Seq[String]) {

  /** A timeout in milliseconds for establishing an HTTP connection. */
  val ConnectionTimeOutInMilliseconds: Int = 10000

  /** A timeout in milliseconds for reading data over an HTTP connection. */
  val ReadTimeOutInMilliseconds: Int = 10000

  /**
   *
   *
   * @return
   *
   * @throws UnableToOpenHTTPConnectionError             if an [[HttpURLConnection]] instance could not be created; if the HTTP request method cannot be reset; if the request method is not valid; if the [[ConnectionTimeOutInMilliseconds]] expires before a connection has been established; or if an I/O error occurs during establishing the connection
   * @throws RedirectionRequestedError                   for HTTP status codes 301, 302, 307, and 308
   * @throws ImageNotFoundError                          for HTTP status codes 204, 205, 404, and 410
   * @throws AccessDeniedByServerError                   for HTTP status codes 401, 402, 403, 407, and 451
   * @throws RequestedURITooLongError                    for HTTP status code 414
   * @throws TooManyRequestsToServerError                for HTTP status code 429
   * @throws ServerError                                 for all HTTP status codes beginning with 5
   * @throws UnknownHTTPResponseError                    for all HTTP status codes other than 200 that are not reported with other exceptions
   * @throws UnknownMIMETypeError                        if the MIME type sent by the server is not supported
   * @throws UnableToRetrieveDataOverHTTPConnectionError if an [[InputStream]] could not be created for the data
   * @throws SuitableImageStreamProviderNotFoundError    if Java's ImageIO did not find a suitable image reader
   * @throws ImageInputStreamNotCreatedError             if a cache file is needed but could not be created
   */
  def load: Seq[Try[BitmapBufferAdapter]] = {
    ensureThatMimeTypeIsSupported()
    retrieveImages()
  }

  /**
   *
   *
   * @throws UnableToOpenHTTPConnectionError if an [[HttpURLConnection]] instance could not be created; if the HTTP request method cannot be reset; if the request method is not valid; if the [[ConnectionTimeOutInMilliseconds]] expires before a connection has been established; or if an I/O error occurs during establishing the connection
   * @throws RedirectionRequestedError       for HTTP status codes 301, 302, 307, and 308
   * @throws ImageNotFoundError              for HTTP status codes 204, 205, 404, and 410
   * @throws AccessDeniedByServerError       for HTTP status codes 401, 402, 403, 407, and 451
   * @throws RequestedURITooLongError        for HTTP status code 414
   * @throws TooManyRequestsToServerError    for HTTP status code 429
   * @throws ServerError                     for all HTTP status codes beginning with 5
   * @throws UnknownHTTPResponseError        for all HTTP status codes other than 200 that are not reported with other exceptions
   * @throws UnknownMIMETypeError            if the MIME type sent by the server is not supported
   */
  private
  def ensureThatMimeTypeIsSupported(): Unit = {
    val connection = new WrappedHTTPConnection(
      targetURL,
      HTTPMethodHead,
      ConnectionTimeOutInMilliseconds,
      ReadTimeOutInMilliseconds)

    connection.useFor{connection =>
      val mimeType = {
        val typeString = connection.getContentType.trim.toLowerCase(Locale.getDefault)
        stripMIMETypeExtensionsFrom(typeString)
      }

      if (!supportedReadableMimeTypes.contains(mimeType))
        throw UnknownMIMETypeError(mimeType, supportedReadableMimeTypes)
    }
  }

  /**
   *
   *
   * @param mimeType
   *
   * @return
   */
  private
  def stripMIMETypeExtensionsFrom(mimeType: String): String = {
    val placeOfSemicolon = mimeType.indexOf(";")
    if (placeOfSemicolon > -1) {
      return mimeType.substring(0, placeOfSemicolon).trim
    }

    mimeType
  }

  /**
   *
   *
   * @return
   *
   * @throws UnableToOpenHTTPConnectionError             if an [[HttpURLConnection]] instance could not be created; if the HTTP request method cannot be reset; if the request method is not valid; if the [[ConnectionTimeOutInMilliseconds]] expires before a connection has been established; or if an I/O error occurs during establishing the connection
   * @throws RedirectionRequestedError                   for HTTP status codes 301, 302, 307, and 308
   * @throws ImageNotFoundError                          for HTTP status codes 204, 205, 404, and 410
   * @throws AccessDeniedByServerError                   for HTTP status codes 401, 402, 403, 407, and 451
   * @throws RequestedURITooLongError                    for HTTP status code 414
   * @throws TooManyRequestsToServerError                for HTTP status code 429
   * @throws ServerError                                 for all HTTP status codes beginning with 5
   * @throws UnknownHTTPResponseError                    for all HTTP status codes other than 200 that are not reported with other exceptions
   * @throws UnableToRetrieveDataOverHTTPConnectionError if an [[InputStream]] could not be created for the data
   * @throws SuitableImageStreamProviderNotFoundError    if Java's [[ImageIO]] did not find a suitable image reader
   * @throws ImageInputStreamNotCreatedError             if a cache file is needed but could not be created
   */
  private
  def retrieveImages(): Seq[Try[BitmapBufferAdapter]] = {
    val connection = new WrappedHTTPConnection(
      targetURL,
      HTTPMethodGet,
      ConnectionTimeOutInMilliseconds,
      ReadTimeOutInMilliseconds)

    connection.openInputStreamFor{stream =>
      EnsureClosingOfAfter(createImageInputStreamFor(stream)){inputStream =>
        val loader = new ImageStreamLoader(
          inputStream,
          targetURL.toExternalForm,
          shouldLoadOnlyFirst,
          bitmapValidator)

        loader.load
      }
    }
  }

  /**
   *
   *
   * @param connectionInputStream
   *
   * @return
   *
   * @throws SuitableImageStreamProviderNotFoundError if Java's [[ImageIO]] did not find a suitable image reader
   * @throws ImageInputStreamNotCreatedError          if a cache file is needed but could not be created
   */
  private
  def createImageInputStreamFor(connectionInputStream: InputStream): ImageInputStream = {
    val inputStream =
      Try(ImageIO.createImageInputStream(connectionInputStream)).recover({
        case e: IOException => throw ImageInputStreamNotCreatedError(e)
      }).get

    if (inputStream == null)
      throw SuitableImageStreamProviderNotFoundError

    inputStream
  }




  /**
   * Wraps Java's [[HttpURLConnection]].
   *
   * @param targetURL
   * @param connectionTimeOutInMilliseconds
   * @param readTimeOutInMilliseconds
   *
   * @throws IllegalArgumentException        if either of the connection timeouts is negative
   * @throws UnableToOpenHTTPConnectionError if an [[HttpURLConnection]] instance could not be created; if the HTTP request method cannot be reset; if the request method is not valid; if the [[ConnectionTimeOutInMilliseconds]] expires before a connection has been established; or if an I/O error occurs during establishing the connection
   * @throws RedirectionRequestedError       for HTTP status codes 301, 302, 307, and 308
   * @throws ImageNotFoundError              for HTTP status codes 204, 205, 404, and 410
   * @throws AccessDeniedByServerError       for HTTP status codes 401, 402, 403, 407, and 451
   * @throws RequestedURITooLongError        for HTTP status code 414
   * @throws TooManyRequestsToServerError    for HTTP status code 429
   * @throws ServerError                     for all HTTP status codes beginning with 5
   * @throws UnknownHTTPResponseError        for all HTTP status codes other than 200 that are not reported with other exceptions
   */
  private
  class WrappedHTTPConnection(
      targetURL: URL,
      method: HTTPMethod,
      connectionTimeOutInMilliseconds: Int,
      readTimeOutInMilliseconds: Int) {

    private
    val HTTP_TEMPORARY_REDIRECT: Int = 307

    private
    val HTTP_PERMANENT_REDIRECT: Int = 308

    private
    val HTTP_URI_TOO_LONG: Int = 414

    private
    val HTTP_TOO_MANY_REQUESTS: Int = 429

    private
    val HTTP_UNAVAILABLE_FOR_LEGAL_REASONS: Int = 451

    private
    val connection: HttpURLConnection = httpConnectionCreator.createBasedOn(targetURL)

    init()

    /**
     *
     */
    private
    def init(): Unit = {
      createConnection()
      checkHTTPStatusCode()
    }

    /**
     *
     *
     * @throws IllegalArgumentException        if either of the connection timeouts is negative
     * @throws UnableToOpenHTTPConnectionError if an [[HttpURLConnection]] instance could not be created; if the HTTP request method cannot be reset; if the request method is not valid; if the [[ConnectionTimeOutInMilliseconds]] expires before a connection has been established; or if an I/O error occurs during establishing the connection
     */
    private
    def createConnection(): Unit = {
      connection.setInstanceFollowRedirects(true)
      connection.setConnectTimeout(connectionTimeOutInMilliseconds)
      connection.setReadTimeout(readTimeOutInMilliseconds)

      try {
        // A SecurityException will be thrown, "if a security manager is set and the method
        // is 'TRACE', but the 'allowHttpTrace' NetPermission is not granted". However, the
        // 'TRACE' method is not used in this class, so in practice, this will not happen.

        connection.setRequestMethod(method.name)
      }
      catch {
        case e: ProtocolException =>
          throw UnableToOpenHTTPConnectionError(targetURL, e)
      }

      try {
        connection.connect()
      }
      catch {
        case e@(_: IOException | _: SocketTimeoutException) =>
          throw UnableToOpenHTTPConnectionError(targetURL, e)
      }
    }

    /**
     *
     *
     * @throws RedirectionRequestedError    for HTTP status codes 301, 302, 307, and 308
     * @throws ImageNotFoundError           for HTTP status codes 204, 205, 404, and 410
     * @throws AccessDeniedByServerError    for HTTP status codes 401, 402, 403, 407, and 451
     * @throws RequestedURITooLongError     for HTTP status code 414
     * @throws TooManyRequestsToServerError for HTTP status code 429
     * @throws ServerError                  for all HTTP status codes beginning with 5
     * @throws UnknownHTTPResponseError     for all HTTP status codes other than 200 that are not reported with other exceptions
     */
    private
    def checkHTTPStatusCode(): Unit = {
      val statusCode: Int = connection.getResponseCode

      if (statusCode.toString.charAt(0) == '5') {
        throw ServerError(targetURL, statusCode)
      }
      else {
        (statusCode: @switch) match {
          case HttpURLConnection.HTTP_OK =>                     // 200 --> Everything OK

          case HttpURLConnection.HTTP_MOVED_PERM                // 301
               | HttpURLConnection.HTTP_MOVED_TEMP              // 302
               | HTTP_TEMPORARY_REDIRECT                        // 307
               | HTTP_PERMANENT_REDIRECT =>                     // 308
            val location = connection.getHeaderField("Location")
            throw RedirectionRequestedError(targetURL, location, statusCode)

          case HttpURLConnection.HTTP_NOT_FOUND                 // 404
               | HttpURLConnection.HTTP_GONE                    // 410
               | HttpURLConnection.HTTP_NO_CONTENT              // 204
               | HttpURLConnection.HTTP_RESET =>                // 205
            throw ImageNotFoundError(targetURL, statusCode)

          case HttpURLConnection.HTTP_UNAUTHORIZED              // 401
               | HttpURLConnection.HTTP_PAYMENT_REQUIRED        // 402
               | HttpURLConnection.HTTP_FORBIDDEN               // 403
               | HttpURLConnection.HTTP_PROXY_AUTH              // 407
               | HTTP_UNAVAILABLE_FOR_LEGAL_REASONS =>          // 451
            throw AccessDeniedByServerError(targetURL, statusCode)

          case HTTP_URI_TOO_LONG =>                             // 414
            throw RequestedURITooLongError(targetURL)

          case HTTP_TOO_MANY_REQUESTS =>                        // 429
            throw TooManyRequestsToServerError(targetURL)

          case _ =>
            throw UnknownHTTPResponseError(targetURL, statusCode)
        }
      }
    }

    /**
     *
     *
     * @param workUnit
     * @tparam A
     *
     * @return
     */
    def useFor[A](workUnit: HttpURLConnection => A): A = {
      try {
        workUnit(connection)
      }
      finally {
        connection.disconnect()
      }
    }

    /**
     *
     *
     * @param workUnit
     * @tparam A
     *
     * @return
     */
    def openInputStreamFor[A](workUnit: InputStream => A): A = {
      val inputStream =
        try {
          connection.getInputStream
        }
        catch {
          case e@(_: IOException | _: UnknownServiceException) =>
            throw UnableToRetrieveDataOverHTTPConnectionError(targetURL, e)
        }

      try {
        workUnit(inputStream)
      }
      finally {
        inputStream.close()
      }
    }

  }




}
