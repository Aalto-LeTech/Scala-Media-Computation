package aalto.smcl.platform


import java.awt.image.BufferedImage
import java.io.{File, IOException}
import java.util.Locale
import javax.imageio.stream.ImageInputStream
import javax.imageio.{ImageIO, ImageReadParam, ImageTypeSpecifier}

import scala.collection.mutable.ArrayBuffer
import scala.util._

import aalto.smcl.common._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ImageProvider {

  /** The image file will be read sequentially only once. */
  val SeekForwardOnly = true

  /** Any metadata in the image file will be ignored. */
  val IgnoreMetadata = true

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
   * @param filePath
   * @return
   */
  def tryToLoadImagesFromFile(filePath: String): Try[Seq[Try[PlatformBitmapBuffer]]] =
    Try(loadImagesFromFile(filePath))

  /**
   * private[platform]
   *
   * @param filePath
   * @return
   *
   * @throws SecurityException
   */
  def loadImagesFromFile(filePath: String): Seq[Try[PlatformBitmapBuffer]] = {
    require(filePath != null, "Path of the image file to be loaded cannot be null.")


    val pathToFile = filePath.trim
    require(!filePath.isEmpty,
      "Path of the image file to be loaded cannot be an empty string or contain only white space.")


    val imageFile = new File(filePath)
    require(!imageFile.isDirectory, "The specified path points to a folder instead of an image file.")
    require(imageFile.isFile, "The specified path does not point to a regular image file.")
    require(imageFile.exists(), "The specified file does not exist.")
    require(imageFile.length() > 0, "The specified file has no content.")


    val extension = {
      val fileName = imageFile.getName
      val lastPeriod = fileName.lastIndexOf(StrPeriod)

      if (lastPeriod > -1)
        fileName.substring(lastPeriod + 1).trim.toLowerCase(Locale.getDefault)
      else
        StrEmpty
    }
    require(supportedReadableFileExtensions.contains(extension),
      "Extension of the given file is unknown (the supported ones are " +
          supportedReadableFileExtensions.mkString(StrComma + StrSpace) + ").")


    val inputStream = Try[ImageInputStream](ImageIO.createImageInputStream(imageFile)) recover {
      case caughtException: IOException =>
        throw new RuntimeException(
          "Input stream for the image file could not be created, possibly because a cache file could not be created.",
          caughtException)
    }
    if (inputStream.get == null) {
      throw new RuntimeException(
        "Input stream for the image file could not be created because a suitable stream provider could not be found.")
    }


    val imageReaders = ImageIO.getImageReaders(inputStream.get)
    if (!imageReaders.hasNext)
      throw new RuntimeException("A suitable image reader for the given image file could not be created.")


    val destinationBufferImageType = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_ARGB)
    val imageReadingParams = new ImageReadParam()
    imageReadingParams.setDestinationType(destinationBufferImageType)


    val reader = imageReaders.next()
    reader.setInput(inputStream, SeekForwardOnly, IgnoreMetadata)

    val readImages = new ArrayBuffer[Try[PlatformBitmapBuffer]]()
    var allImagesRead = false
    var currentImageIndex = reader.getMinIndex
    var readImage: Try[BufferedImage] = null
    while (!allImagesRead) {
      readImage = Try(reader.read(currentImageIndex, imageReadingParams))

      if (readImage.isSuccess) {
        readImages += Try(PlatformBitmapBuffer(readImage.get))
      }
      else {
        readImages.last.failed.get match {
          case _: IndexOutOfBoundsException => allImagesRead = true
          case _                            => ()
        }
      }

      currentImageIndex += 1
    }

    readImages.toSeq
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
