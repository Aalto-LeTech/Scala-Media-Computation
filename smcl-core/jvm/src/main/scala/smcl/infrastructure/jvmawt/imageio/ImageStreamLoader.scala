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


import scala.util.Try

import javax.imageio.stream.ImageInputStream
import javax.imageio.{ImageIO, ImageReader}

import smcl.infrastructure.BitmapBufferAdapter
import smcl.infrastructure.exceptions.{ImageReaderNotRetrievedError, SuitableImageReaderNotFoundError}
import smcl.infrastructure.jvmawt.AWTBitmapBufferAdapter
import smcl.modeling.Len
import smcl.pictures.BitmapValidator
import smcl.pictures.exceptions.{MaximumBitmapSizeExceededError, MinimumBitmapSizeNotMetError}




/**
 *
 *
 * @param inputStream
 * @param descriptiveImagePath
 * @param shouldLoadOnlyFirst
 * @param bitmapValidator
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class ImageStreamLoader(
    private val inputStream: ImageInputStream,
    private val descriptiveImagePath: String,
    private val shouldLoadOnlyFirst: Boolean,
    private val bitmapValidator: BitmapValidator) {

  private
  var reader: Option[ImageReader] = None

  /**
   *
   *
   * @return
   *
   * @throws SuitableImageReaderNotFoundError
   * @throws ImageReaderNotRetrievedError
   */
  def load: Seq[Try[BitmapBufferAdapter]] = {
    findSuitableImageReader()
    loadImagesFromReader()
  }

  /**
   *
   *
   * @return
   *
   * @throws SuitableImageReaderNotFoundError
   * @throws ImageReaderNotRetrievedError
   */
  private
  def findSuitableImageReader(): Unit = {
    val imageReaders = ImageIO.getImageReaders(inputStream)
    if (!imageReaders.hasNext)
      throw SuitableImageReaderNotFoundError

    val firstReader = Try(imageReaders.next()).recover({
      case t: Throwable => throw ImageReaderNotRetrievedError(t)
    }).get

    reader = Some(firstReader)
  }

  /**
   *
   *
   * @return
   */
  private
  def loadImagesFromReader(): Seq[Try[BitmapBufferAdapter]] = {
    try {
      reader.get.setInput(inputStream)

      val WithSearchingAllowed = true
      val firstImageIndex = reader.get.getMinIndex

      if (shouldLoadOnlyFirst) {
        Seq(loadSingleImageFromReader(firstImageIndex))
      }
      else {
        val lastImageIndex = firstImageIndex + reader.get.getNumImages(WithSearchingAllowed) - 1
        val imageNumberRange = firstImageIndex to lastImageIndex

        imageNumberRange.map(loadSingleImageFromReader)
      }
    }
    finally {
      if (reader.isDefined) {
        reader.get.dispose()
        reader = None
      }
    }
  }

  /**
   *
   *
   * @param imageIndex
   *
   * @return
   */
  private
  def loadSingleImageFromReader(imageIndex: Int): Try[BitmapBufferAdapter] = for {
    widthInPixels <- Try(reader.get.getWidth(imageIndex))
    width = Len(widthInPixels)

    heightInPixels <- Try(reader.get.getHeight(imageIndex))
    height = Len(heightInPixels)

    _ = {
      if (bitmapValidator.maximumSizeLimitsAreExceeded(width, height)) {
        throw MaximumBitmapSizeExceededError(
          width, height,
          BitmapValidator.MaximumBitmapWidth,
          BitmapValidator.MaximumBitmapHeight,
          Option(descriptiveImagePath), Option(imageIndex))
      }

      if (bitmapValidator.minimumSizeLimitsAreNotMet(width, height)) {
        throw MinimumBitmapSizeNotMetError(
          width, height,
          BitmapValidator.MinimumBitmapWidth,
          BitmapValidator.MinimumBitmapHeight,
          Option(descriptiveImagePath), Option(imageIndex))
      }
    }

    readImage <- Try(reader.get.read(imageIndex))

  } yield AWTBitmapBufferAdapter(readImage)

}
