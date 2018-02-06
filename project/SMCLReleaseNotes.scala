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


import java.io.{FileNotFoundException, IOException}

import sbt._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object SMCLReleaseNotes {

  /** */
  val DocumentationFolderName = "doc"

  /** */
  val ReleaseNotesFolderName = "release-notes"

  private
  def releaseNotesFileNameFor(version: String) =
    s"release-notes-$version.md"

  /**
   *
   *
   * @param currentVersion
   * @param baseDir
   *
   * @return
   */
  @inline
  def fileFor(currentVersion: String, baseDir: File): File =
    fileFor(SMCLVersion.from(currentVersion), baseDir)

  /**
   *
   *
   * @param currentVersion
   * @param baseDir
   *
   * @return
   */
  @inline
  def fileFor(currentVersion: SMCLVersion, baseDir: File): File = {
    val version = currentVersion.forRelease.toString

    baseDir /
        DocumentationFolderName /
        ReleaseNotesFolderName /
        releaseNotesFileNameFor(version)
  }

  /**
   *
   *
   * @param currentVersion
   * @param baseDir
   *
   * @return
   */
  def getFor(currentVersion: String, baseDir: File): SMCLReleaseNotes = {
    val smclVersion = SMCLVersion.from(currentVersion).forRelease

    val notesFile = fileFor(smclVersion, baseDir)

    SMCLReleaseNotes(smclVersion, notesFile)
  }

}




/**
 *
 *
 * @param version
 * @param file
 *
 * @author Aleksi Lukkarinen
 */
case class SMCLReleaseNotes private(
    version: SMCLVersion,
    file: File) {

  /** */
  lazy val content: String = {
    if (!file.exists) {
      throw new FileNotFoundException(
        s"""Release notes file does not exist in path "${file.getAbsolutePath}"!""")
    }
    if (!file.isFile) {
      throw new IOException(
        s"""Path "${file.getAbsolutePath}" does not represent a release notes file!""")
    }
    if (!file.canRead) {
      throw new IOException(
        s"""Release notes file "${file.getAbsolutePath}" cannot be read!""")
    }

    IO.read(file).trim
  }

}
