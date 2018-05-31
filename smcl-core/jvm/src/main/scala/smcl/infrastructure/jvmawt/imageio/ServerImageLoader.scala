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
import java.net.{HttpURLConnection, URL}

import scala.util.Try

import javax.imageio.ImageIO
import javax.imageio.stream.ImageInputStream

import smcl.infrastructure.exceptions._
import smcl.infrastructure.{BitmapBufferAdapter, EnsureClosingOfAfter}
import smcl.pictures.BitmapValidator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class ServerImageLoader(
    private val bitmapValidator: BitmapValidator) {

  /**
   *
   *
   * @param path
   * @param shouldLoadOnlyFirst
   *
   * @return
   *
   * @throws OperationPreventedBySecurityManagerError
   * @throws PathIsNullError
   * @throws PathIsEmptyOrOnlyWhitespaceError
   * @throws FileAttributeRetrievalFailedError
   * @throws PathPointsToFolderError
   * @throws PathPointsToSymbolicLinkError
   * @throws PathDoesNotPointToRegularFileError
   * @throws UnknownFileExtensionError
   * @throws FileNotFoundError
   * @throws EmptyFileError
   * @throws SuitableImageStreamProviderNotFoundError
   * @throws ImageInputStreamNotCreatedError
   * @throws SuitableImageReaderNotFoundError
   * @throws ImageReaderNotRetrievedError
   */
  def tryToLoadFrom(
      path: String,
      shouldLoadOnlyFirst: Boolean): Seq[Try[BitmapBufferAdapter]] = {

    val url = new URL(path)
    val connection = url.openConnection().asInstanceOf[HttpURLConnection]
    connection.setRequestMethod("GET")

    // TODO: Ensure that mimetype is supported

    EnsureClosingOfAfter(createImageInputStreamFor(connection.getInputStream)){inputStream =>
      val loader = new ImageStreamLoader(bitmapValidator)

      loader.tryToLoadFrom(inputStream, path, shouldLoadOnlyFirst)
    }
  }

  /**
   *
   *
   * @param imageStream
   *
   * @return
   *
   * @throws SuitableImageStreamProviderNotFoundError
   * @throws ImageInputStreamNotCreatedError
   */
  private
  def createImageInputStreamFor(imageStream: InputStream): ImageInputStream = {
    val inputStream =
      Try(ImageIO.createImageInputStream(imageStream)).recover({
        case e: IOException => throw ImageInputStreamNotCreatedError(e)
      }).get

    if (inputStream == null)
      throw SuitableImageStreamProviderNotFoundError

    inputStream
  }

}
