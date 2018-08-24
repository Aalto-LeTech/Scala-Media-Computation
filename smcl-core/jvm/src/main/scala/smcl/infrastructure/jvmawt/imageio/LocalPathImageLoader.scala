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


import java.io.File
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{Files, NoSuchFileException}

import scala.util.Try

import javax.imageio.{ImageIO, ImageReader}

import smcl.infrastructure.exceptions._
import smcl.infrastructure.{BitmapBufferAdapter, CommonFileUtils, EnsureClosingOfAfter}
import smcl.pictures.BitmapValidator
import smcl.pictures.exceptions.{MaximumBitmapSizeExceededError, MinimumBitmapSizeNotMetError}




/**
 *
 *
 * @param path
 * @param shouldLoadOnlyFirst
 * @param imageInputStreamProvider
 * @param bitmapValidator
 * @param supportedReadableFileExtensions
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class LocalPathImageLoader(
    private val path: String,
    private val shouldLoadOnlyFirst: Boolean,
    private val imageInputStreamProvider: ImageInputStreamProvider,
    private val bitmapValidator: BitmapValidator,
    private val supportedReadableFileExtensions: Seq[String]) {

  /**
   *
   *
   * @return
   *
   * @throws EmptyFileError                           if the given path points to an empty file
   * @throws FileAttributeRetrievalFailedError        if the attributes of the file that the given path points to could not be retrieved
   * @throws FileNotFoundError                        if the given path points to a file that does not seem to exist
   * @throws ImageInputStreamNotCreatedError          if a cache file is needed but could not be created
   * @throws ImageReaderNotRetrievedError             if the first suitable [[ImageReader]] cannot be retrieved
   * @throws MaximumBitmapSizeExceededError           if a bitmap is larger than the maximum allowed bitmap size
   * @throws MinimumBitmapSizeNotMetError             if a bitmap is smaller than the minimum allowed bitmap size
   * @throws OperationPreventedBySecurityManagerError if retrieval of file attributes was prevented by a security manager
   * @throws PathDoesNotPointToRegularFileError       if the given path does not point to a regular file
   * @throws PathIsEmptyOrOnlyWhitespaceError         if the given path is empty or contains only whitespace
   * @throws PathIsNullError                          if the given path was actually null
   * @throws PathPointsToFolderError                  if the given path points to a folder
   * @throws PathPointsToSymbolicLinkError            if the given path poins to a symbolic link
   * @throws SuitableImageReaderNotFoundError         if no suitable [[ImageReader]] is found
   * @throws SuitableImageStreamProviderNotFoundError if [[ImageIO]] did not find a suitable image stream service provider instance
   * @throws UnknownFileExtensionError                if the file extension is unknown
   */
  def load: Seq[Try[BitmapBufferAdapter]] = {
    val (imageFile, imagePath) = ensureThatLocalImageFileIsReadable(path)
    ensureThatFileExtensionIsSupportedForReading(imageFile.getName)

    EnsureClosingOfAfter(imageInputStreamProvider.createFor(imageFile)){inputStream =>
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
   * @throws EmptyFileError                           if the given path points to an empty file
   * @throws FileAttributeRetrievalFailedError        if the attributes of the file that the given path points to could not be retrieved
   * @throws FileNotFoundError                        if the given path points to a file that does not seem to exist
   * @throws OperationPreventedBySecurityManagerError if retrieval of file attributes was prevented by a security manager
   * @throws PathDoesNotPointToRegularFileError       if the given path does not point to a regular file
   * @throws PathIsEmptyOrOnlyWhitespaceError         if the given path is empty or contains only whitespace
   * @throws PathIsNullError                          if the given path was actually null
   * @throws PathPointsToFolderError                  if the given path points to a folder
   * @throws PathPointsToSymbolicLinkError            if the given path poins to a symbolic link
   * @throws UnknownFileExtensionError                if the extension of the file is not supported
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
   * @throws UnknownFileExtensionError if the file extension is unknown
   */
  private
  def ensureThatFileExtensionIsSupportedForReading(fileName: String): Unit = {
    val fileExtension: String =
      new CommonFileUtils().resolveExtensionOf(fileName)

    if (!supportedReadableFileExtensions.contains(fileExtension))
      throw UnknownFileExtensionError(
        fileExtension, supportedReadableFileExtensions)
  }

}
