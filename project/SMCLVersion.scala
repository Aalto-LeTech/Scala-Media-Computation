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


import scala.util.Try
import scala.util.matching.Regex




/**
 * Companion object for SMCLVersion class.
 *
 * @author Aleksi Lukkarinen
 */
object SMCLVersion {

  private
  val NumericVersionID = "numericVersion"

  private
  val MajorVersionID = "majorVersion"

  private
  val MinorVersionID = "minorVersion"

  private
  val MicroVersionID = "microVersion"

  private
  val VersionQualifierID = "versionQualifier"

  private
  val VersionRegEx: Regex = "^v?(([0-9])+.([0-9]+).([0-9])+)-?(.*)?$"
      .r(NumericVersionID,
        MajorVersionID, MinorVersionID, MicroVersionID,
        VersionQualifierID)

  /**
   *
   *
   * @param versionToParse
   *
   * @return
   */
  def tryFrom(versionToParse: String): Try[SMCLVersion] =
    Try(from(versionToParse))

  /**
   *
   *
   * @param versionToParse
   *
   * @return
   */
  def from(versionToParse: String): SMCLVersion = {
    val possibleMatch = VersionRegEx.findFirstMatchIn(versionToParse)
    if (possibleMatch.isEmpty)
      throw new IllegalArgumentException(
        s"""String "$versionToParse" does not conform SMCL version format!""")

    val sureMatch = possibleMatch.get

    val majorVersion: Int = sureMatch.group(MajorVersionID).toInt
    val minorVersion: Int = sureMatch.group(MinorVersionID).toInt
    val microVersion: Int = sureMatch.group(MicroVersionID).toInt
    val qualifier: String = sureMatch.group(VersionQualifierID).trim

    SMCLVersion(majorVersion, minorVersion, microVersion, Option(qualifier))
  }

}




/**
 * Version identifier of SMCL.
 *
 * @author Aleksi Lukkarinen
 */
case class SMCLVersion(
    major: Int,
    minor: Int,
    micro: Int,
    qualifier: Option[String]) {

  /**
   *
   *
   * @return
   */
  def microIncrement: SMCLVersion =
    SMCLVersion(major, minor, micro + 1, qualifier)

  /**
   *
   *
   * @return
   */
  def minorIncrement: SMCLVersion =
    SMCLVersion(major, minor + 1, 0, qualifier)

  /**
   *
   *
   * @return
   */
  def majorIncrement: SMCLVersion =
    SMCLVersion(major + 1, 0, 0, qualifier)

  /**
   *
   *
   * @return
   */
  def withoutQualifier: SMCLVersion =
    SMCLVersion(major, minor, micro, None)

  /**
   *
   *
   * @return
   */
  def forRelease: SMCLVersion = withoutQualifier

  /**
   *
   *
   * @return
   */
  override
  def toString: String = {
    val numericPart = s"$major.$minor.$micro"

    if (qualifier.isEmpty)
      return numericPart

    s"$numericPart-${qualifier.get}"
  }

}