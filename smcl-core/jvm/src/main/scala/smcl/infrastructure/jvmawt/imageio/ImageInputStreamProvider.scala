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


import java.io.{File, IOException, InputStream}

import scala.util.Try

import javax.imageio.ImageIO
import javax.imageio.stream.ImageInputStream

import smcl.infrastructure.exceptions.{ImageInputStreamNotCreatedError, SuitableImageStreamProviderNotFoundError}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class ImageInputStreamProvider() {

  /**
   *
   *
   * @param stream
   *
   * @return
   *
   * @throws SuitableImageStreamProviderNotFoundError if [[ImageIO]] did not find a suitable image stream service provider instance
   * @throws ImageInputStreamNotCreatedError          if a cache file is needed but could not be created
   */
  def createFor(stream: InputStream): ImageInputStream =
    createStreamFor(stream)

  /**
   *
   *
   * @param imageFile
   *
   * @return
   *
   * @throws SuitableImageStreamProviderNotFoundError if [[ImageIO]] did not find a suitable image stream service provider instance
   * @throws ImageInputStreamNotCreatedError          if a cache file is needed but could not be created
   */
  def createFor(imageFile: File): ImageInputStream =
    createStreamFor(imageFile)

  /**
   *
   *
   * @param imageSource
   *
   * @return
   *
   * @throws SuitableImageStreamProviderNotFoundError if [[ImageIO]] did not find a suitable image stream service provider instance
   * @throws ImageInputStreamNotCreatedError          if a cache file is needed but could not be created
   */
  private
  def createStreamFor(imageSource: Any): ImageInputStream = {
    val inputStream =
      Try(ImageIO.createImageInputStream(imageSource)).recover({
        case e: IOException => throw ImageInputStreamNotCreatedError(e)
      }).get

    if (inputStream == null)
      throw SuitableImageStreamProviderNotFoundError

    inputStream
  }

}
