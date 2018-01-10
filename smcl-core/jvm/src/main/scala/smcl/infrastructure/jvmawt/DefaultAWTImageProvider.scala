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


import java.io.{File, IOException}
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{Files, NoSuchFileException}
import java.util.Locale
import javax.imageio.stream.ImageInputStream
import javax.imageio.{ImageIO, ImageReader}

import scala.util.{Try, _}

import smcl.infrastructure.exceptions._
import smcl.infrastructure.{BitmapBufferAdapter, CommonFileUtils, EnsureClosingOfAfter}
import smcl.modeling.Len
import smcl.pictures.BitmapValidator
import smcl.pictures.exceptions.{MaximumBitmapSizeExceededError, MinimumBitmapSizeNotMetError}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class DefaultAWTImageProvider(bitmapValidator: BitmapValidator) extends AWTImageProvider {

  /** */
  lazy val supportedReadableMimeTypes: Seq[String] =
    ImageIO.getReaderMIMETypes.toSeq

  /** */
  lazy val supportedReadableFileExtensions: Seq[String] =
    ImageIO.getReaderFileSuffixes.map(_.toLowerCase(Locale.getDefault)).toSeq

  /** */
  lazy val supportedWritableMimeTypes: Seq[String] =
    ImageIO.getWriterMIMETypes.toSeq

  /** */
  lazy val supportedWritableFileExtensions: Seq[String] =
    ImageIO.getWriterFileSuffixes.map(_.toLowerCase(Locale.getDefault)).toSeq


  /**
   *
   *
   * @param path
   *
   * @return
   */
  override def tryToLoadImageFromFile(path: String): Try[BitmapBufferAdapter] = {
    val overallLoadingResult = Try(loadImagesFromFile(path, shouldLoadOnlyFirst = true))
    if (overallLoadingResult.isFailure)
      return Failure(overallLoadingResult.failed.get)

    overallLoadingResult.get.head
  }

  /**
   *
   *
   * @param path
   *
   * @return
   */
  override def tryToLoadImagesFromFile(path: String): Try[Seq[Try[BitmapBufferAdapter]]] = {
    Try(loadImagesFromFile(path, shouldLoadOnlyFirst = false))
  }

  /**
   *
   *
   * @param path
   * @param shouldLoadOnlyFirst
   *
   * @return
   *
   * @throws SecurityException
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
  private def loadImagesFromFile(
      path: String,
      shouldLoadOnlyFirst: Boolean): Seq[Try[BitmapBufferAdapter]] = {

    val (imageFile, imagePath, imageExtension) =
      ensureThatImageFileIsReadableAndSupported(path)

    EnsureClosingOfAfter(createImageInputStreamFor(imageFile)){inputStream =>
      val reader: ImageReader = findSuitableImageReaderFor(inputStream)

      try {
        reader.setInput(inputStream)
        loadImagesFromReader(reader, imagePath, shouldLoadOnlyFirst)
      }
      finally {
        if (reader != null)
          reader.dispose()
      }
    }
  }

  /**
   *
   * @param reader
   * @param filePath
   * @param shouldLoadOnlyFirst
   *
   * @return
   */
  private def loadImagesFromReader(
      reader: ImageReader,
      filePath: String,
      shouldLoadOnlyFirst: Boolean): Seq[Try[BitmapBufferAdapter]] = {

    val WithSearchingAllowed = true
    val firstImageIndex = reader.getMinIndex

    if (shouldLoadOnlyFirst) {
      Seq(loadSingleImageFromReader(firstImageIndex, reader, filePath))
    }
    else {
      val lastImageIndex = firstImageIndex + reader.getNumImages(WithSearchingAllowed) - 1
      val imageNumberRange = firstImageIndex to lastImageIndex

      imageNumberRange.map(loadSingleImageFromReader(_, reader, filePath))
    }
  }

  /**
   *
   *
   * @param imageIndex
   * @param reader
   * @param filePath
   *
   * @return
   */
  private def loadSingleImageFromReader(
      imageIndex: Int,
      reader: ImageReader,
      filePath: String): Try[BitmapBufferAdapter] = for {

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
          Option(filePath), Option(imageIndex))
      }

      if (bitmapValidator.minimumSizeLimitsAreNotMet(width, height)) {
        throw MinimumBitmapSizeNotMetError(
          width, height,
          BitmapValidator.MinimumBitmapWidth,
          BitmapValidator.MinimumBitmapHeight,
          Option(filePath), Option(imageIndex))
      }
    }

    readImage <- Try(reader.read(imageIndex))

  } yield AWTBitmapBufferAdapter(readImage)

  /**
   *
   *
   * @param imageFile
   *
   * @return
   *
   * @throws SuitableImageStreamProviderNotFoundError
   * @throws ImageInputStreamNotCreatedError
   */
  private def createImageInputStreamFor(imageFile: File): ImageInputStream = {
    val inputStream =
      Try(ImageIO.createImageInputStream(imageFile)).recover({
        case e: IOException => throw ImageInputStreamNotCreatedError(e)
      }).get

    if (inputStream == null)
      throw SuitableImageStreamProviderNotFoundError

    inputStream
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
  private def findSuitableImageReaderFor(
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
   *
   * @param filePath
   *
   * @return
   *
   * @throws SecurityException
   * @throws PathIsNullError
   * @throws PathIsEmptyOrOnlyWhitespaceError
   * @throws FileAttributeRetrievalFailedError
   * @throws PathPointsToFolderError
   * @throws PathPointsToSymbolicLinkError
   * @throws PathDoesNotPointToRegularFileError
   * @throws UnknownFileExtensionError
   * @throws FileNotFoundError
   * @throws EmptyFileError
   */
  private def ensureThatImageFileIsReadableAndSupported(
      filePath: String): (File, String, String) = {

    if (filePath == null)
      throw PathIsNullError

    val trimmedPathString = filePath.trim
    if (trimmedPathString.isEmpty)
      throw PathIsEmptyOrOnlyWhitespaceError

    val imageFile = new File(trimmedPathString)
    val imageFilePath = imageFile.toPath

    val fileAttributes: BasicFileAttributes =
      Try(Files.readAttributes(imageFilePath, classOf[BasicFileAttributes])).recover({
        case e: NoSuchFileException => throw FileNotFoundError(trimmedPathString, e)
        case other: Throwable       => throw FileAttributeRetrievalFailedError(other)
      }).get

    Map(
      fileAttributes.isDirectory -> PathPointsToFolderError,
      fileAttributes.isSymbolicLink -> PathPointsToSymbolicLinkError,
      !fileAttributes.isRegularFile -> PathDoesNotPointToRegularFileError,
      !Files.exists(imageFilePath) -> FileNotFoundError(trimmedPathString),
      (fileAttributes.size <= 0) -> EmptyFileError
    ).find(_._1) map {pair => throw pair._2}

    val fileExtension: String = new CommonFileUtils().resolveExtensionOf(imageFile.getName)
    if (!supportedReadableFileExtensions.contains(fileExtension))
      throw UnknownFileExtensionError(
        fileExtension, supportedReadableFileExtensions)

    (imageFile.getAbsoluteFile, imageFile.getAbsolutePath, fileExtension)
  }


  /* TODO: Later


    /**
     *
     *
     *
     * @param url
     * @return
     */
    def tryToLoadImageFromURL(url: String): Try[PlatformBitmapBuffer] =
      Try(loadImageFromUrl(url))

    /**
     *
     *
     * @param url
     * @return
     */
    private[platform] def loadImageFromUrl(url: String): PlatformBitmapBuffer = {
      val imageResource = new File(url)

      null
    }
  */

}
