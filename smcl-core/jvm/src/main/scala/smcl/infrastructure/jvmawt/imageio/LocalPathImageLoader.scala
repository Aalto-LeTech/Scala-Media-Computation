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


import java.io.{File, IOException}
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{Files, NoSuchFileException}

import scala.util.Try

import javax.imageio.ImageIO
import javax.imageio.stream.ImageInputStream

import smcl.infrastructure.exceptions._
import smcl.infrastructure.{BitmapBufferAdapter, CommonFileUtils, EnsureClosingOfAfter}
import smcl.pictures.BitmapValidator




/**
 *
 *
 * @param path
 * @param shouldLoadOnlyFirst
 * @param bitmapValidator
 * @param supportedReadableFileExtensions
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class LocalPathImageLoader(
    private val path: String,
    private val shouldLoadOnlyFirst: Boolean,
    private val bitmapValidator: BitmapValidator,
    private val supportedReadableFileExtensions: Seq[String]) {

  /**
   *
   *
   * @return
   *
   * @throws OperationPreventedBySecurityManagerError
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
  def load: Seq[Try[BitmapBufferAdapter]] = {
    val (imageFile, imagePath) = ensureThatLocalImageFileIsReadable(path)
    ensureThatFileExtensionIsSupportedForReading(imageFile.getName)

    EnsureClosingOfAfter(createImageInputStreamFor(imageFile)){inputStream =>
      val loader = new ImageStreamLoader(
        inputStream, imagePath, shouldLoadOnlyFirst, bitmapValidator)

      loader.load
    }
  }

  /**
   *
   *
   * @param filePath
   *
   * @return
   *
   * @throws OperationPreventedBySecurityManagerError if retrieval of file attributes was prevented by a security manager
   * @throws PathIsNullError                          if the given path was actually null
   * @throws PathIsEmptyOrOnlyWhitespaceError         if the given path is empty or contains only whitespace
   * @throws FileAttributeRetrievalFailedError        if the attributes of the file that the given path points to could not be retrieved
   * @throws PathPointsToFolderError                  if the given path points to a folder
   * @throws PathPointsToSymbolicLinkError            if the given path poins to a symbolic link
   * @throws PathDoesNotPointToRegularFileError       if the given path does not point to a regular file
   * @throws UnknownFileExtensionError                if the extension of the file is not supported
   * @throws FileNotFoundError                        if the given path points to a file that does not seem to exist
   * @throws EmptyFileError                           if the given path points to an empty file
   */
  private
  def ensureThatLocalImageFileIsReadable(filePath: String): (File, String) = {
    if (filePath == null)
      throw PathIsNullError

    val trimmedPathString = filePath.trim
    if (trimmedPathString.isEmpty)
      throw PathIsEmptyOrOnlyWhitespaceError

    val imageFile = new File(trimmedPathString)
    val imageFilePath = imageFile.toPath

    val fileAttributes: BasicFileAttributes =
      Try(Files.readAttributes(imageFilePath, classOf[BasicFileAttributes])).recover({
        case e: NoSuchFileException =>
          throw ImageNotFoundError(trimmedPathString,
            FileNotFoundError(trimmedPathString, e))

        case e: SecurityException =>
          throw OperationPreventedBySecurityManagerError("Retrieving file attributes",
            FileAttributeRetrievalFailedError(e))

        case other: Throwable =>
          throw FileAttributeRetrievalFailedError(other)
      }).get

    Map(
      fileAttributes.isDirectory -> PathPointsToFolderError,
      fileAttributes.isSymbolicLink -> PathPointsToSymbolicLinkError,
      !fileAttributes.isRegularFile -> PathDoesNotPointToRegularFileError,
      !Files.exists(imageFilePath) -> FileNotFoundError(trimmedPathString),
      (fileAttributes.size <= 0) -> EmptyFileError
    ).find(_._1) map {pair => throw pair._2}

    (imageFile.getAbsoluteFile, imageFile.getAbsolutePath)
  }

  /**
   *
   *
   * @param fileName
   *
   * @throws UnknownFileExtensionError
   */
  private
  def ensureThatFileExtensionIsSupportedForReading(fileName: String): Unit = {
    val fileExtension: String =
      new CommonFileUtils().resolveExtensionOf(fileName)

    if (!supportedReadableFileExtensions.contains(fileExtension))
      throw UnknownFileExtensionError(
        fileExtension, supportedReadableFileExtensions)
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
  private
  def createImageInputStreamFor(imageFile: File): ImageInputStream = {
    val inputStream =
      Try(ImageIO.createImageInputStream(imageFile)).recover({
        case e: IOException => throw ImageInputStreamNotCreatedError(e)
      }).get

    if (inputStream == null)
      throw SuitableImageStreamProviderNotFoundError

    inputStream
  }

}
