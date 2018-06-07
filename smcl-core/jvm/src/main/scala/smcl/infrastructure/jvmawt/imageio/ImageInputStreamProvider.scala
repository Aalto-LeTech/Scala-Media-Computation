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
   * @throws SuitableImageStreamProviderNotFoundError if Java's [[ImageIO]] did not find a suitable image reader
   * @throws ImageInputStreamNotCreatedError          if a cache file is needed but could not be created
   */
  def createFor(stream: InputStream): ImageInputStream = {
    val inputStream =
      Try(ImageIO.createImageInputStream(stream)).recover({
        case e: IOException => throw ImageInputStreamNotCreatedError(e)
      }).get

    if (inputStream == null)
      throw SuitableImageStreamProviderNotFoundError

    inputStream
  }

}
