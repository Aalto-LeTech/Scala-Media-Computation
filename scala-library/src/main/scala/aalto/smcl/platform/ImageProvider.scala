package aalto.smcl.platform


import java.io.{File, IOException}
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.util.Locale
import javax.imageio.stream.ImageInputStream
import javax.imageio.{ImageIO, ImageReader}

import aalto.smcl.bitmaps._
import aalto.smcl.common._

import scala.util._


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object ImageProvider {

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
   * @return
   */
  def tryToLoadImagesFromFile(path: String): Try[Seq[Either[Throwable, PlatformBitmapBuffer]]] =
    Try(loadImagesFromFile(path))

  /**
   *
   *
   * @param path
   * @return
   *
   * @throws SMCLImageInputStreamNotCreatedError
   * @throws SMCLSuitableImageStreamProviderNotFoundError
   * @throws SMCLSuitableImageReaderNotFoundError
   * @throws SMCLImageReaderNotRetrievedError
   * @throws SecurityException
   */
  private def loadImagesFromFile(path: String): Seq[Either[Throwable, PlatformBitmapBuffer]] = {
    val (imageFile, imagePath, imageExtension) =
      ensureThatImageFileIsReadableAndSupported(path)

    EnsureClosingOfAfter(createImageInputStreamFor(imageFile)) { inputStream =>
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
   * @return
   */
  private def loadImagesFromReader(
    reader: ImageReader,
    filePath: String): Seq[Either[Throwable, PlatformBitmapBuffer]] = {

    val WithSearchingAllowed = true
    val lastImageIndex = reader.getMinIndex + reader.getNumImages(WithSearchingAllowed) - 1
    val imageNumberRange = reader.getMinIndex to lastImageIndex

    imageNumberRange.map(loadSingleImageFromReader(_, reader, filePath)).toSeq
  }

  /**
   *
   *
   * @param currentImageIndex
   * @param reader
   * @param filePath
   * @return
   */
  private def loadSingleImageFromReader(
    currentImageIndex: Int,
    reader: ImageReader,
    filePath: String): Either[Throwable, PlatformBitmapBuffer] = {

    val width = Try(reader.getWidth(currentImageIndex)).recover({
      case e: IOException => return Left(e)
    }).get

    val height = Try(reader.getHeight(currentImageIndex)).recover({
      case e: IOException => return Left(e)
    }).get

    if (BitmapValidator.maximumSizeLimitsAreExceeded(width, height)) {
      val newThrowable =
        new SMCLMaximumBitmapSizeExceededError(
          Option(width), Option(height),
          Option(filePath), Option(currentImageIndex))

      return Left(newThrowable)
    }

    if (BitmapValidator.minimumSizeLimitsAreNotMet(width, height)) {
      val newThrowable =
        new SMCLMinimumBitmapSizeNotMetError(
          Option(width), Option(height),
          Option(filePath), Option(currentImageIndex))

      return Left(newThrowable)
    }

    val readImage = Try(reader.read(currentImageIndex)).recover({
      case e: IOException => return Left(e)
    }).get

    val platformBitmapBuffer = Try(PlatformBitmapBuffer(readImage)).recover({
      case t: Throwable => return Left(t)
    }).get

    Right(platformBitmapBuffer)
  }


  /**
   *
   *
   * @param inputStream
   * @return
   *
   * @throws SMCLSuitableImageReaderNotFoundError
   * @throws SMCLImageReaderNotRetrievedError
   */
  private def findSuitableImageReaderFor(inputStream: ImageInputStream): ImageReader = {
    val imageReaders = ImageIO.getImageReaders(inputStream)
    if (!imageReaders.hasNext)
      throw new SMCLSuitableImageReaderNotFoundError()

    Try(imageReaders.next()).recover({
      case t: Throwable => throw new SMCLImageReaderNotRetrievedError(t)
    }).get
  }

  /**
   *
   *
   * @param imageFile
   * @return
   *
   * @throws SMCLImageInputStreamNotCreatedError
   * @throws SMCLSuitableImageStreamProviderNotFoundError
   */
  private def createImageInputStreamFor(imageFile: File): ImageInputStream = {
    val inputStream = Try[ImageInputStream](ImageIO.createImageInputStream(imageFile)).recover({
      case e: IOException => throw new SMCLImageInputStreamNotCreatedError(e)
    }).get

    if (inputStream == null)
      throw new SMCLSuitableImageStreamProviderNotFoundError()

    inputStream
  }

  /**
   *
   *
   * @param filePath
   * @return
   *
   * @throws SecurityException
   */
  private def ensureThatImageFileIsReadableAndSupported(filePath: String): (File, String, String) = {
    require(filePath != null, "Path of the image file to be loaded cannot be null.")

    val pathToFile = filePath.trim
    require(!filePath.isEmpty,
      "Path of the image file to be loaded cannot be an empty string or contain only white space.")


    val imageFile = new File(filePath)
    val imageFilePath = imageFile.toPath

    val attributes: BasicFileAttributes =
      Try(Files.readAttributes(imageFilePath, classOf[BasicFileAttributes])).recover({
        case t: Throwable => throw new SMCLFileAttributeRetrievalFailedError(t)
      }).get

    require(!attributes.isDirectory, "The specified path points to a folder instead of an image file.")
    require(!attributes.isSymbolicLink, "The specified path points to a symbolic link instead of an image file.")
    require(attributes.isRegularFile, "The specified path does not point to a regular image file.")
    require(Files.exists(imageFilePath), "The specified file does not exist.")
    require(attributes.size > 0, "The specified file has no content.")


    val extension: String = FileUtils.resolveExtensionOf(imageFile.getName)
    ensureThatFileExtensionIsOfSupportedImageType(extension)

    (imageFile.getAbsoluteFile, imageFile.getAbsolutePath, extension)
  }

  /**
   *
   *
   * @param extension
   */
  private def ensureThatFileExtensionIsOfSupportedImageType(extension: String): Unit = {
    require(supportedReadableFileExtensions.contains(extension),
      "Extension of the given file is unknown (the supported ones are " +
        supportedReadableFileExtensions.mkString(StrComma + StrSpace) + ").")
  }

  /**
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

}
