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
import java.net.{HttpURLConnection, ProtocolException, URL, UnknownServiceException}

import scala.util.Try

import javax.imageio.ImageIO
import javax.imageio.stream.ImageInputStream

import smcl.infrastructure.exceptions._
import smcl.infrastructure.jvmawt.{HTTPConnectionProvider, URLProvider}
import smcl.infrastructure.{BitmapBufferAdapter, EnsureClosingOfAfter}
import smcl.pictures.BitmapValidator




/**
 *
 *
 * @param sourcePath
 * @param shouldLoadOnlyFirst
 * @param urlCreator
 * @param httpConnectionCreator
 * @param bitmapValidator
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class ServerImageLoader(
    private val sourcePath: String,
    private val shouldLoadOnlyFirst: Boolean,
    private val urlCreator: URLProvider,
    private val httpConnectionCreator: HTTPConnectionProvider,
    private val bitmapValidator: BitmapValidator) {

  private
  var url: Option[URL] = None

  private
  var connection: Option[HttpURLConnection] = None

  /**
   *
   *
   * @return
   *
   * @throws InvalidPathError
   * @throws UnableToOpenHTTPConnectionError
   * @throws UnableToRetrieveDataOverHTTPConnectionError
   * @throws ImageInputStreamNotCreatedError
   * @throws SuitableImageReaderNotFoundError
   */
  def load: Seq[Try[BitmapBufferAdapter]] = {
    initURL()
    initConnection()

    ensureThatMimeTypeIsSupported()
    retrieveImages()
  }

  /**
   *
   *
   * @throws InvalidPathError if an URL instance could not be created based on the given address
   */
  private
  def initURL(): Unit = {
    if (url.isEmpty)
      url = Some(urlCreator.createBasedOn(sourcePath))
  }

  /**
   *
   *
   * @throws UnableToOpenHTTPConnectionError when a connection could not be opened
   */
  private
  def initConnection(): Unit = {
    if (connection.isEmpty)
      connection = Some(httpConnectionCreator.createBasedOn(url.get))
  }

  /**
   *
   */
  private
  def ensureThatMimeTypeIsSupported(): Unit = {
    setRequestMethod(HTTPMethodHead)
    // TODO: Ensure that mimetype is supported
  }

  /**
   *
   *
   * @param method
   *
   * @throws UnableToRetrieveDataOverHTTPConnectionError
   */
  private
  def setRequestMethod(method: HTTPMethod): Unit = {
    try {
      connection.get.setRequestMethod(method.name)
    }
    catch {
      case e@(_: ProtocolException | _: SecurityException) =>
        throw UnableToRetrieveDataOverHTTPConnectionError(url.get, e)
    }
  }

  /**
   *
   *
   * @return
   *
   * @throws UnableToRetrieveDataOverHTTPConnectionError
   * @throws SuitableImageReaderNotFoundError
   * @throws ImageReaderNotRetrievedError
   */
  private
  def retrieveImages(): Seq[Try[BitmapBufferAdapter]] = {
    setRequestMethod(HTTPMethodGet)

    val inputStream =
      try {
        connection.get.getInputStream
      }
      catch {
        case e@(_: IOException | _: UnknownServiceException) =>
          throw UnableToRetrieveDataOverHTTPConnectionError(url.get, e)
      }

    EnsureClosingOfAfter(createImageInputStreamFor(inputStream)){inputStream =>
      val loader = new ImageStreamLoader(
        inputStream, sourcePath, shouldLoadOnlyFirst, bitmapValidator)

      loader.load
    }
  }

  /**
   *
   *
   * @param connectionInputStream
   *
   * @return
   *
   * @throws SuitableImageStreamProviderNotFoundError
   * @throws ImageInputStreamNotCreatedError
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

}
