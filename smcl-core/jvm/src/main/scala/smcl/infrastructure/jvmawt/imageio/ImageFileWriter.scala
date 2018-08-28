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


import java.awt.image.BufferedImage
import java.io.{File, FileNotFoundException, FileOutputStream, IOException}

import scala.util.Try

import javax.imageio.{ImageIO, ImageWriter}

import smcl.infrastructure.exceptions._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ImageFileWriter {

  /**
   *
   *
   * @param imageFormat
   *
   * @return
   *
   * @throws ImageWriterNotRetrievedError     if the first suitable [[ImageWriter]] cannot be retrieved
   * @throws SuitableImageWriterNotFoundError if no suitable [[ImageWriter]] is found
   */
  def apply(imageFormat: String): ImageFileWriter = {
    val writer = findSuitableImageWriter(imageFormat)

    new ImageFileWriter(writer)
  }


  /**
   *
   *
   * @param format
   *
   * @return
   *
   * @throws ImageWriterNotRetrievedError     if the first suitable [[ImageWriter]] cannot be retrieved
   * @throws SuitableImageWriterNotFoundError if no suitable [[ImageWriter]] is found
   */
  private
  def findSuitableImageWriter(format: String): ImageWriter = {
    val imageWriters = ImageIO.getImageWritersByFormatName(format)
    if (!imageWriters.hasNext)
      throw SuitableImageWriterNotFoundError

    Try(imageWriters.next()).recover({
      case t: Throwable => throw ImageWriterNotRetrievedError(t)
    }).get
  }

}




/**
 *
 *
 * @param imageWriter
 *
 * @author Aleksi Lukkarinen
 */
class ImageFileWriter private(imageWriter: ImageWriter) {

  /**
   *
   *
   * @param pathToFile
   * @param bufferedImageToWrite
   *
   * @throws FileOverwritingIsDeniedBySMCLError       if given path points to an existing file (not folder)
   * @throws ImageWritingFailedError                  if an [[IOException]] occurred while writing to the file represented by the given path
   * @throws OperationPreventedBySecurityManagerError if an existing security manager prevents access to the file represented by the given path
   * @throws PathIsNullError                          if given path is null
   * @throws PathIsEmptyOrOnlyWhitespaceError         if given path is an empty string or contains only whitespace
   * @throws PathPointsToFolderError                  if given path points to an existing folder
   * @throws UnableToOpenFileForWritingError          if the file represented by the given path cannot be opened
   */
  def writeTo(
      pathToFile: String,
      bufferedImageToWrite: BufferedImage): Unit = {

    val destFile = checkPath(pathToFile)
    val destStream: FileOutputStream = openOutputStream(destFile)

    writeToStream(destStream, bufferedImageToWrite, destFile.getCanonicalPath)
  }

  /**
   *
   *
   * @param pathToFile
   *
   * @return
   *
   * @throws FileOverwritingIsDeniedBySMCLError       if given path points to an existing file (not folder)
   * @throws OperationPreventedBySecurityManagerError if an existing security manager prevents access to the file represented by the given path
   * @throws PathIsEmptyOrOnlyWhitespaceError         if given path is an empty string or contains only whitespace
   * @throws PathIsNullError                          if given path is null
   * @throws PathPointsToFolderError                  if given path points to an existing folder
   */
  private
  def checkPath(pathToFile: String): File = {
    if (pathToFile == null)
      throw PathIsNullError

    val trimmedPath = pathToFile.trim
    if (trimmedPath.isEmpty)
      throw PathIsEmptyOrOnlyWhitespaceError

    val destFile = new File(trimmedPath)

    var representsExistingFolder = false
    var representsExistsingFile = false

    try {
      representsExistingFolder = destFile.isDirectory
      representsExistsingFile = destFile.exists
    }
    catch {
      case e: SecurityException =>
        throw OperationPreventedBySecurityManagerError(
          s"""A security manager prevents reading from file \"$pathToFile\".""", e)
    }

    if (representsExistingFolder)
      throw PathPointsToFolderError
    if (representsExistsingFile)
      throw FileOverwritingIsDeniedBySMCLError(trimmedPath)

    destFile
  }

  /**
   *
   *
   * @param destinationFile
   *
   * @return
   *
   * @throws OperationPreventedBySecurityManagerError if an existing security manager prevents access to the file represented by the given path
   * @throws UnableToOpenFileForWritingError          if the file represented by the given path cannot be opened
   */
  private
  def openOutputStream(destinationFile: File): FileOutputStream = {
    try {
      new FileOutputStream(destinationFile)
    }
    catch {
      case e: FileNotFoundException =>
        throw UnableToOpenFileForWritingError(destinationFile.getCanonicalPath, e)

      case e: SecurityException =>
        throw OperationPreventedBySecurityManagerError(
          s"""A security manager prevents writing to file \"${destinationFile.getCanonicalPath}\".""", e)
    }
  }

  /**
   *
   *
   * @param destinationStream
   * @param bufferedImageToWrite
   * @param destinationPath
   *
   * @throws ImageWritingFailedError if an [[IOException]] occurred while writing to the file represented by the given path
   */
  private
  def writeToStream(
      destinationStream: FileOutputStream,
      bufferedImageToWrite: BufferedImage,
      destinationPath: String): Unit = {

    try {
      val imageOutputStream = ImageIO.createImageOutputStream(destinationStream)
      imageWriter.setOutput(imageOutputStream)
      imageWriter.write(bufferedImageToWrite)
    }
    catch {
      case e: IOException =>
        throw ImageWritingFailedError(destinationPath, e)
    }
  }

}
