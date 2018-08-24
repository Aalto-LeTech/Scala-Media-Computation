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

import javax.imageio.{ImageIO, ImageReader}

import smcl.infrastructure.exceptions._
import smcl.infrastructure.jvmawt.JVMReflectionUtils
import smcl.infrastructure.{BitmapBufferAdapter, EnsureClosingOfAfter}
import smcl.pictures.BitmapValidator
import smcl.pictures.exceptions.{MaximumBitmapSizeExceededError, MinimumBitmapSizeNotMetError}




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
    private val resourceName: String,
    private val shouldLoadOnlyFirst: Boolean,
    private val imageInputStreamProvider: ImageInputStreamProvider,
    private val bitmapValidator: BitmapValidator,
    private val supportedReadableFileExtensions: Seq[String]) {

  private
  var resourceStream: Option[InputStream] = None

  /**
   *
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
   *
   * @return
   *
   * @throws ImageInputStreamNotCreatedError          if a cache file is needed but could not be created
   * @throws ImageReaderNotRetrievedError             if the first suitable [[ImageReader]] cannot be retrieved
   * @throws MaximumBitmapSizeExceededError           if a bitmap is larger than the maximum allowed bitmap size
   * @throws MinimumBitmapSizeNotMetError             if a bitmap is smaller than the minimum allowed bitmap size
   * @throws SuitableImageReaderNotFoundError         if no suitable [[ImageReader]] is found
   * @throws SuitableImageStreamProviderNotFoundError if [[ImageIO]] did not find a suitable image stream service provider instance
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
