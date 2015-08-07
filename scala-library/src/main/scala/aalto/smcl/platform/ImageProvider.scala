package aalto.smcl.platform


import java.awt.image.BufferedImage
import java.io.{File, IOException}
import java.util.Locale
import javax.imageio.{ImageReadParam, ImageIO}
import javax.imageio.stream.ImageInputStream

import scala.collection.mutable.ArrayBuffer
import scala.util._

import aalto.smcl.common._




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
   * @param filePath
   * @return
   */
  def tryToLoadImagesFromFile(filePath: String): Try[Seq[Either[Throwable, PlatformBitmapBuffer]]] =
    Try(loadImagesFromFile(filePath))

  /**
   * private[platform]
   *
   * @param filePath
   * @return
   *
   * @throws SecurityException
   */
  def loadImagesFromFile(filePath: String): Seq[Either[Throwable, PlatformBitmapBuffer]] = {
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


    val inputStreamTry = Try[ImageInputStream](ImageIO.createImageInputStream(imageFile)) recover {
      case caughtException: IOException =>
        throw new RuntimeException(
          "Input stream for the image file could not be created, possibly because a cache file could not be created.",
          caughtException)
    }
    if (inputStreamTry.get == null) {
      throw new RuntimeException(
        "Input stream for the image file could not be created because a suitable stream provider could not be found.")
    }

    try {
      val imageReaders = ImageIO.getImageReaders(inputStreamTry.get)
      if (!imageReaders.hasNext)
        throw new RuntimeException("A suitable image reader for the given image file could not be found.")

      val readerTry = Try(imageReaders.next())
      if (readerTry.isFailure) {
        throw new RuntimeException(
          "A suitable image reader for the given image file could not be retrieved.",
          readerTry.failed.get)
      }

      val reader = readerTry.get

      try {
        reader.setInput(inputStreamTry.get)

        val readImages = new ArrayBuffer[Either[Throwable, PlatformBitmapBuffer]]()
        val numberOfImagesInFile = reader.getNumImages(true)
        var currentImageIndex = reader.getMinIndex
        var readImageTry: Try[BufferedImage] = null

        for (currentImageIndex <- currentImageIndex to (currentImageIndex + numberOfImagesInFile - 1)) {
          val widthTry = Try(reader.getWidth(currentImageIndex))
          if (widthTry.isFailure) {
            readImages += Left(widthTry.failed.get)
          }
          else {
            val heightTry = Try(reader.getHeight(currentImageIndex))
            if (heightTry.isFailure) {
              readImages += Left(heightTry.failed.get)
            }
            else {

              // AL 7.8.2015
              // This code segment, which loads the image, creates an another ARGB-type buffer and copies the image
              // into it, should be replaced wit a more memory-efficient course of operation. However, I was unable
              // to make the ImageIO to load the image straight into an ARGB buffer, and that's why this operation
              // is handled as can be seen below.
              if (true) {
                readImageTry = Try(reader.read(currentImageIndex))
                if (readImageTry.isFailure) {
                  readImages += Left(readImageTry.failed.get)
                }
                else {
                  val destinationBuffer = new BufferedImage(
                    widthTry.get,
                    heightTry.get,
                    BufferedImage.TYPE_INT_ARGB)

                  val g2d = destinationBuffer.createGraphics()
                  g2d.drawImage(readImageTry.get, null, 0, 0)
                  g2d.dispose()

                  readImages += Right(PlatformBitmapBuffer(destinationBuffer))
                }
              }

              // For example, the following wouldn't work:
              if (false) {
                println(s"Possible image types for image $currentImageIndex: ")
                val types = reader.getImageTypes(currentImageIndex)
                while (types.hasNext) {
                  println(types.next().getBufferedImageType)
                }

                val destinationBuffer = new BufferedImage(
                  widthTry.get,
                  heightTry.get,
                  BufferedImage.TYPE_INT_ARGB)

                val readParams = new ImageReadParam()
                readParams.setDestination(destinationBuffer)
                //readParams.setSourceBands(Array(0, 1, 2))
                //readParams.setDestinationBands(Array(1, 2, 3))


                readImageTry = Try(reader.read(currentImageIndex, readParams))
                readImageTry match {
                  case Success(x) => readImages += Right(PlatformBitmapBuffer(x))
                  case Failure(t) => readImages += Left(t)
                }
              }

            }
          }
        }

        readImages.toSeq
      }
      finally {
        readerTry.map(_.dispose())
      }
    }

    finally {
      inputStreamTry.map(_.close())
    }
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
