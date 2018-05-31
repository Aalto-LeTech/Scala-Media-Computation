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
 * @param bitmapValidator
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class ImageStreamLoader(
    private val bitmapValidator: BitmapValidator) {

  /**
   *
   *
   * @param inputStream
   * @param descriptiveImagePath
   * @param shouldLoadOnlyFirst
   *
   * @return
   *
   * @throws SuitableImageReaderNotFoundError
   * @throws ImageReaderNotRetrievedError
   */
  def tryToLoadFrom(
      inputStream: ImageInputStream,
      descriptiveImagePath: String,
      shouldLoadOnlyFirst: Boolean): Seq[Try[BitmapBufferAdapter]] = {

    val reader: ImageReader = findSuitableImageReaderFor(inputStream)

    try {
      reader.setInput(inputStream)
      loadImagesFromReader(reader, descriptiveImagePath, shouldLoadOnlyFirst)
    }
    finally {
      if (reader != null)
        reader.dispose()
    }
  }

  /**
   *
   *
   * @param inputStream
   *
   * @return
   *
   * @throws SuitableImageReaderNotFoundError
   * @throws ImageReaderNotRetrievedError
   */
  private
  def findSuitableImageReaderFor(
      inputStream: ImageInputStream): ImageReader = {

    val imageReaders = ImageIO.getImageReaders(inputStream)
    if (!imageReaders.hasNext)
      throw SuitableImageReaderNotFoundError

    Try(imageReaders.next()).recover({
      case t: Throwable => throw ImageReaderNotRetrievedError(t)
    }).get
  }

  /**
   *
   * @param reader
   * @param descriptiveImagePath
   * @param shouldLoadOnlyFirst
   *
   * @return
   */
  private
  def loadImagesFromReader(
      reader: ImageReader,
      descriptiveImagePath: String,
      shouldLoadOnlyFirst: Boolean): Seq[Try[BitmapBufferAdapter]] = {

    val WithSearchingAllowed = true
    val firstImageIndex = reader.getMinIndex

    if (shouldLoadOnlyFirst) {
      Seq(loadSingleImageFromReader(firstImageIndex, reader, descriptiveImagePath))
    }
    else {
      val lastImageIndex = firstImageIndex + reader.getNumImages(WithSearchingAllowed) - 1
      val imageNumberRange = firstImageIndex to lastImageIndex

      imageNumberRange.map(loadSingleImageFromReader(_, reader, descriptiveImagePath))
    }
  }

  /**
   *
   *
   * @param imageIndex
   * @param reader
   * @param descriptiveImagePath
   *
   * @return
   */
  private
  def loadSingleImageFromReader(
      imageIndex: Int,
      reader: ImageReader,
      descriptiveImagePath: String): Try[BitmapBufferAdapter] = for {

    widthInPixels <- Try(reader.getWidth(imageIndex))
    width = Len(widthInPixels)

    heightInPixels <- Try(reader.getHeight(imageIndex))
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

    readImage <- Try(reader.read(imageIndex))

  } yield AWTBitmapBufferAdapter(readImage)

}
