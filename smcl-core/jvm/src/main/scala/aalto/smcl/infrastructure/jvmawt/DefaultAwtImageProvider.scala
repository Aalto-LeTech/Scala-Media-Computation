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

package aalto.smcl.infrastructure.jvmawt


import java.io.{File, IOException}
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{Files, NoSuchFileException}
import java.util.Locale
import javax.imageio.stream.ImageInputStream
import javax.imageio.{ImageIO, ImageReader}

import scala.util._

import aalto.smcl.bitmaps._
import aalto.smcl.infrastructure.exceptions._
import aalto.smcl.infrastructure.{BitmapBufferAdapter, CommonFileUtils, EnsureClosingOfAfter}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class DefaultAwtImageProvider(bitmapValidator: BitmapValidator) extends AwtImageProvider {

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
  override def tryToLoadImagesFromFile(path: String): Try[Seq[Either[Throwable, BitmapBufferAdapter]]] =
    Try(loadImagesFromFile(path))

  /**
   *
   *
   * @param path
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
  private def loadImagesFromFile(path: String): Seq[Either[Throwable, BitmapBufferAdapter]] = {
    val (imageFile, imagePath, imageExtension) =
      ensureThatImageFileIsReadableAndSupported(path)

    EnsureClosingOfAfter(createImageInputStreamFor(imageFile)){inputStream =>
      val reader: ImageReader = findSuitableImageReaderFor(inputStream)

      try {
        reader.setInput(inputStream)
        loadImagesFromReader(reader, imagePath)
      }
      finally {
        if (reader != null)
          reader.dispose()
      }
    }
  }

  /**
   *
   *
   * @param filePath
   * @param reader
   *
   * @return
   */
  private def loadImagesFromReader(
      reader: ImageReader,
      filePath: String): Seq[Either[Throwable, BitmapBufferAdapter]] = {

    val WithSearchingAllowed = true
    val lastImageIndex = reader.getMinIndex + reader.getNumImages(WithSearchingAllowed) - 1
    val imageNumberRange = reader.getMinIndex to lastImageIndex

    imageNumberRange.map(loadSingleImageFromReader(_, reader, filePath))
  }

  /**
   *
   *
   * @param currentImageIndex
   * @param reader
   * @param filePath
   *
   * @return
   */
  private def loadSingleImageFromReader(
      currentImageIndex: Int,
      reader: ImageReader,
      filePath: String): Either[Throwable, BitmapBufferAdapter] = {

    val width = Try(reader.getWidth(currentImageIndex)).recover({
      case e: IOException => return Left(e)
    }).get

    val height = Try(reader.getHeight(currentImageIndex)).recover({
      case e: IOException => return Left(e)
    }).get

    if (bitmapValidator.maximumSizeLimitsAreExceeded(width, height)) {
      val newThrowable =
        new SMCLMaximumBitmapSizeExceededError(
          Option(width), Option(height),
          Option(filePath), Option(currentImageIndex), bitmapValidator)

      return Left(newThrowable)
    }

    if (bitmapValidator.minimumSizeLimitsAreNotMet(width, height)) {
      val newThrowable =
        new SMCLMinimumBitmapSizeNotMetError(
          Option(width), Option(height),
          Option(filePath), Option(currentImageIndex), bitmapValidator)

      return Left(newThrowable)
    }

    val readImage = Try(reader.read(currentImageIndex)).recover({
      case e: IOException => return Left(e)
    }).get

    val platformBitmapBuffer = Try(AwtBitmapBufferAdapter(readImage)).recover({
      case t: Throwable => return Left(t)
    }).get

    Right(platformBitmapBuffer)
  }

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
    val inputStream = Try[ImageInputStream](ImageIO.createImageInputStream(imageFile)).recover({
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
  private def findSuitableImageReaderFor(inputStream: ImageInputStream): ImageReader = {
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
  private def ensureThatImageFileIsReadableAndSupported(filePath: String): (File, String, String) = {
    if (filePath == null)
      throw PathIsNullError

    val trimmedPathstring = filePath.trim
    if (trimmedPathstring.isEmpty)
      throw PathIsEmptyOrOnlyWhitespaceError

    val imageFile = new File(trimmedPathstring)
    val imageFilePath = imageFile.toPath

    val fileAttributes: BasicFileAttributes =
      Try(Files.readAttributes(imageFilePath, classOf[BasicFileAttributes])).recover({
        case e: NoSuchFileException => throw FileNotFoundError(trimmedPathstring, e)
        case other: Throwable       => throw FileAttributeRetrievalFailedError(other)
      }).get

    Map(
      fileAttributes.isDirectory -> PathPointsToFolderError,
      fileAttributes.isSymbolicLink -> PathPointsToSymbolicLinkError,
      !fileAttributes.isRegularFile -> PathDoesNotPointToRegularFileError,
      !Files.exists(imageFilePath) -> FileNotFoundError(trimmedPathstring),
      (fileAttributes.size <= 0) -> EmptyFileError
    ).find(_._1) map { pair => throw pair._2 }

    val fileExtension: String = new CommonFileUtils().resolveExtensionOf(imageFile.getName)
    if (supportedReadableFileExtensions.contains(fileExtension))
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
