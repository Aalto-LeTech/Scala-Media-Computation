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


import java.io.InputStream

import scala.util.Try

import smcl.infrastructure.exceptions._
import smcl.infrastructure.jvmawt.JVMReflectionUtils
import smcl.infrastructure.{BitmapBufferAdapter, EnsureClosingOfAfter}
import smcl.pictures.BitmapValidator




/**
 *
 *
 * @param resourceName
 * @param shouldLoadOnlyFirst
 * @param imageInputStreamProvider
 * @param bitmapValidator
 * @param supportedReadableFileExtensions
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class JavaResourceImageLoader(
    resourceName: String,
    shouldLoadOnlyFirst: Boolean,
    imageInputStreamProvider: ImageInputStreamProvider,
    private val bitmapValidator: BitmapValidator,
    private val supportedReadableFileExtensions: Seq[String]) {

  private
  var resourceStream: Option[InputStream] = None

  /**
   *
   *
   * @return
   *
   * @throws ImageNotFoundError if the requested resource could not be found
   */
  def load: Seq[Try[BitmapBufferAdapter]] = {
    resolveResourceStream()
    loadResourceFromStream()
  }

  /**
   * Tries to find the requested resource.
   *
   * @return an [[InputStream]] representing the
   *
   * @throws ImageNotFoundError if the requested resource could not be found
   */
  private
  def resolveResourceStream(): Unit = {
    val loader = JVMReflectionUtils.getClassLoader

    val s = loader.getResourceAsStream(resourceName)
    if (s == null) {
      resourceStream = None
      throw ImageNotFoundError(resourceName)
    }

    resourceStream = Some(s)
  }

  /**
   *
   */
  private
  def loadResourceFromStream(): Seq[Try[BitmapBufferAdapter]] = {
    EnsureClosingOfAfter(imageInputStreamProvider.createFor(resourceStream.get)){inputStream =>
      val loader = new ImageStreamLoader(
        inputStream,
        resourceName,
        shouldLoadOnlyFirst,
        bitmapValidator)

      loader.load
    }
  }

}
