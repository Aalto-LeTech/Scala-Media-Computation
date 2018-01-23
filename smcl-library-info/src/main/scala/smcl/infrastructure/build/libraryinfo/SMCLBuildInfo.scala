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


package smcl.infrastructure.build.libraryinfo


import java.text.SimpleDateFormat
import java.util.TimeZone




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object SMCLBuildInfo {

  /** */
  val DefaultDateTimeFormat: String =
    "dd.MM.yyyy HH:mm:ss.SSSXXX"

  /** */
  val DefaultTimeZoneName: String = "UTC"

  /** */
  private[libraryinfo]
  lazy val DefaultDateTimeFormatter =
    new SimpleDateFormat(DefaultDateTimeFormat)

  /** */
  private[libraryinfo]
  lazy val DefaultTimeZone: TimeZone =
    TimeZone.getTimeZone(DefaultTimeZoneName)

}




/**
 *
 *
 * @param majorVersion         major version of the library in this build
 * @param minorVersion         minor version of the library in this build
 * @param microVersion         micro version of the library in this build
 * @param isRelease            <code>true</code> if this version of the library is a release build
 * @param projectVersionString the version of this library as a string
 * @param scalaVersion
 * @param sbtVersion
 * @param sourceJavaVersion
 * @param targetJavaVersion
 * @param targetPlatform
 * @param isJVMAWTPlatform
 * @param isJSHTML5Platform
 * @param buildTime
 *
 * @author Aleksi Lukkarinen
 */
case class SMCLBuildInfo(
    majorVersion: Int,
    minorVersion: Int,
    microVersion: Int,
    isRelease: Boolean,
    projectVersionString: String,
    scalaVersion: String,
    sbtVersion: String,
    sourceJavaVersion: String,
    targetJavaVersion: String,
    targetPlatform: String,
    isJVMAWTPlatform: Boolean,
    isJSHTML5Platform: Boolean,
    buildTime: Long) {

  /**
   * Tells if this version of the library is a snapshot build.
   *
   * @return <code>true</code> if snapshot; otherwise <code>false</code>
   */
  def isSnapshot: Boolean = !isRelease

  /**
   * Returns the build time of this library distribution as a string.
   *
   * @param f  formatter to format the date with; if <code>None</code>, the [[SMCLBuildInfo.DefaultDateTimeFormat]] will be used
   * @param tz time zone to format the date with; if <code>None</code>, the [[SMCLBuildInfo.DefaultTimeZoneName]] will be used
   *
   * @return
   */
  def buildTimeString(
      f: Option[SimpleDateFormat],
      tz: Option[TimeZone]): String = {

    val formatter = f.getOrElse(SMCLBuildInfo.DefaultDateTimeFormatter)
    formatter.setTimeZone(tz.getOrElse(SMCLBuildInfo.DefaultTimeZone))
    formatter.format(buildTime)
  }

}
