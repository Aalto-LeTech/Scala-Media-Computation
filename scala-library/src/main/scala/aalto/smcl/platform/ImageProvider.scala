package aalto.smcl.platform


import java.io.{File, IOException}
import java.util.Locale
import javax.imageio.stream.ImageInputStream
import javax.imageio.{ImageIO, ImageReader}

import aalto.smcl.bitmaps._
import aalto.smcl.common._

import scala.util._


/**
 * private[smcl]
 *
 * @author Aleksi Lukkarinen
 */
object ImageProvider {

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
   *
   * @throws SecurityException
   */
  def tryToLoadImagesFromFile(path: String): Try[Seq[Either[Throwable, PlatformBitmapBuffer]]] =
    Try(loadImagesFromFile(path))

  /**
   *
   *
   * @param path
   * @return
   *
   * @throws SecurityException
   */
  private def loadImagesFromFile(path: String): Seq[Either[Throwable, PlatformBitmapBuffer]] = {
    val (imageFile, imagePath, imageExtension) =
      ensureThatImageFileIsReadableAndSupported(path)

    EnsureClosingOfAfter(createImageInputStreamFor(imageFile)) { inputStream =>
      val reader: ImageReader = findSuitableImageReaderFor(inputStream)

      try {
        reader.setInput(inputStream)
        loadImagesFromReader(reader, path)
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

    val widthTry = Try(reader.getWidth(currentImageIndex))
    if (widthTry.isFailure)
      return Left(widthTry.failed.get)

    val width = widthTry.get

    val heightTry = Try(reader.getHeight(currentImageIndex))
    if (heightTry.isFailure)
      return Left(heightTry.failed.get)

    val height = heightTry.get

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

    val readImageTry = Try(reader.read(currentImageIndex))
    if (readImageTry.isFailure)
      return Left(readImageTry.failed.get)

    val platformBitmapBufferTry = Try(PlatformBitmapBuffer(readImageTry.get))
    if (platformBitmapBufferTry.isFailure)
      return Left(platformBitmapBufferTry.failed.get)

    Right(platformBitmapBufferTry.get)
  }


  /**
   *
   *
   * @param inputStream
   * @return
   */
  private def findSuitableImageReaderFor(inputStream: ImageInputStream): ImageReader = {
    val imageReaders = ImageIO.getImageReaders(inputStream)
    if (!imageReaders.hasNext)
      throw new SMCLSuitableImageReaderNotFoundError()

    val readerTry = Try(imageReaders.next())
    if (readerTry.isFailure)
      throw new SMCLImageReaderNotRetrievedError(readerTry.failed.get)

    readerTry.get
  }

  /**
   *
   *
   * @param imageFile
   * @return
   */
  private def createImageInputStreamFor(imageFile: File): ImageInputStream = {
    val inputStreamTry = Try[ImageInputStream](ImageIO.createImageInputStream(imageFile)) recover {
      case caughtException: IOException => throw new SMCLImageInputStreamNotCreatedError(caughtException)
    }
    if (inputStreamTry.get == null)
      throw new SMCLSuitableImageStreamProviderNotFoundError()

    inputStreamTry.get
  }

  /**
   *
   *
   * @param filePath
   * @return
   */
  private def ensureThatImageFileIsReadableAndSupported(filePath: String): (File, String, String) = {
    require(filePath != null, "Path of the image file to be loaded cannot be null.")

    val pathToFile = filePath.trim
    require(!filePath.isEmpty,
      "Path of the image file to be loaded cannot be an empty string or contain only white space.")


    val imageFile = new File(filePath)
    require(!imageFile.isDirectory, "The specified path points to a folder instead of an image file.")
    require(imageFile.isFile, "The specified path does not point to a regular image file.")
    require(imageFile.exists(), "The specified file does not exist.")
    require(imageFile.length() > 0, "The specified file has no content.")


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
