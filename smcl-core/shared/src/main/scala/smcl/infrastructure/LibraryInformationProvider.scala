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

package smcl.infrastructure


import java.text.SimpleDateFormat
import java.util.TimeZone




/**
 * An interface for delivering information about this library.
 *
 * @author Aleksi Lukkarinen
 */
trait LibraryInformationProvider {

  /** */
  val DefaultDateTimeFormat: String =
    "dd.MM.yyyy HH:mm:ss.SSSXXX"

  /** */
  val DefaultTimeZoneName: String = "UTC"

  /** */
  private
  val DefaultDateTimeFormatter =
    new SimpleDateFormat(DefaultDateTimeFormat)

  /** */
  private
  val DefaultTimeZone: TimeZone =
    TimeZone.getTimeZone(DefaultTimeZoneName)

  /**
   * Returns the full name of this library.
   *
   * @return
   */
  def fullName: String

  /**
   * Returns the abbreviated name of this library.
   *
   * @return
   */
  def abbreviatedName: String

  /**
   * Returns the major version of this library.
   *
   * @return
   */
  def majorVersion: Int

  /**
   * Returns the minor version of this library.
   *
   * @return
   */
  def minorVersion: Int

  /**
   * Returns the microversion of this library.
   *
   * @return
   */
  def microVersion: Int

  /**
   * Returns the platform ID of this library.
   *
   * @return
   */
  def platform: String

  /**
   * Tells whether or not this library is running on Java platform.
   *
   * @return
   */
  def isJavaPlatform: Boolean

  /**
   * Tells whether or not this library is running on JavaScript platform.
   *
   * @return
   */
  def isJavaScriptPlatform: Boolean

  /**
   * Tells whether or not the version of this library is a release one.
   *
   * @return <code>true</code> in case of a release version, <code>false</code> otherwise
   */
  def isRelease: Boolean

  /**
   * Tells whether or not the version of this library is a snapshot one.
   *
   * @return <code>true</code> in case of a snapshot version, <code>false</code> for a release one
   */
  def isSnapshot: Boolean = !isRelease

  /**
   * Returns the version of this library as a string.
   *
   * @return
   */
  def projectVersionString: String

  /**
   * Returns the version of this module as a string.
   *
   * @return
   */
  def moduleVersionString: String

  /**
   * Returns a description of this library.
   *
   * @return
   */
  def description: String

  /**
   * Returns the URL of the home page of this library.
   *
   * @return
   */
  def homePageURL: String

  /**
   * Returns the inception year of this library.
   *
   * @return
   */
  def inceptionYear: Int

  /**
   * Returns the name of the organization developing of this library.
   *
   * @return
   */
  def organizationName: String

  /**
   * Returns the id (i.e., package prefix) of the organization developing this library.
   *
   * @return
   */
  def organizationId: String

  /**
   * Returns the URL of the home page of the organization developing this library.
   *
   * @return
   */
  def organizationHomePageURL: String

  /**
   * Returns the names of the developers of this library.
   *
   * @return
   */
  def developers: Seq[String]

  /**
   * Returns the name of the organization in which the development of this library started.
   *
   * @return
   */
  def originalOrganizationName: String

  /**
   * Returns the name of the developer who started the development of this library.
   *
   * @return
   */
  def originalDeveloperName: String

  /**
   * Returns the name of the country in which the development of this library started.
   *
   * @return
   */
  def countryOfOrigin: String

  /**
   * Returns the version of Scala used to build this library distribution.
   *
   * @return
   */
  def scalaVersion: String

  /**
   * Returns the version of SBT used to build this library distribution.
   *
   * @return
   */
  def sbtVersion: String

  /**
   * Returns the build time of this library distribution as a string.
   *
   * @param f  formatter to format the date with; if <code>None</code>, the [[DefaultDateTimeFormat]] will be used
   * @param tz time zone to format the date with; if <code>None</code>, the [[DefaultTimeZoneName]] will be used
   *
   * @return
   */
  def builtAtString(
      f: Option[SimpleDateFormat],
      tz: Option[TimeZone]): String = {

    val formatter = f.getOrElse(DefaultDateTimeFormatter)
    formatter.setTimeZone(tz.getOrElse(DefaultTimeZone))
    formatter.format(builtAtMillis)
  }

  /**
   * Returns the built time of this library distribution as a long integer
   * containing the milliseconds returned by Java's
   * <code>System.currentTimeMillis()</code>.
   *
   * @return
   */
  def builtAtMillis: Long

}
