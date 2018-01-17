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

import smcl.infrastructure.exceptions.ImplementationNotSetError




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object DefaultLibraryInformationProvider
    extends LibraryInformationProvider {

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

  /** The concrete factory class. */
  private
  var _implementation: Option[LibraryInformationProvider] = None

  /**
   * Returns the full name of this library.
   *
   * @return
   */
  def fullName: String =
    implementation.fullName

  /**
   * Returns the abbreviated name of this library.
   *
   * @return
   */
  def abbreviatedName: String =
    implementation.abbreviatedName

  /**
   * Returns the major version of this library.
   *
   * @return
   */
  def majorVersion: Int =
    implementation.majorVersion

  /**
   * Returns the minor version of this library.
   *
   * @return
   */
  def minorVersion: Int =
    implementation.minorVersion

  /**
   * Returns the microversion of this library.
   *
   * @return
   */
  def microVersion: Int =
    implementation.microVersion

  /**
   * Returns the platform ID of this library.
   *
   * @return
   */
  def platform: String =
    implementation.platform

  /**
   * Tells whether or not this library is running on Java platform.
   *
   * @return
   */
  def isJavaPlatform: Boolean =
    implementation.isJavaPlatform

  /**
   * Tells whether or not this library is running on JavaScript platform.
   *
   * @return
   */
  def isJavaScriptPlatform: Boolean =
    implementation.isJavaScriptPlatform

  /**
   * Tells whether or not the version of this library is a release one.
   *
   * @return <code>true</code> in case of a release version, <code>false</code> for a snapshot one
   */
  def isRelease: Boolean =
    implementation.isRelease

  /**
   * Tells whether or not the version of this library is a snapshot one.
   *
   * @return <code>true</code> in case of a snapshot version, <code>false</code> for a release one
   */
  def isSnapshot: Boolean =
    !implementation.isRelease

  /**
   * Returns the version of this library as a string.
   *
   * @return
   */
  def projectVersionString: String =
    implementation.projectVersionString

  /**
   * Returns the version of this module as a string.
   *
   * @return
   */
  def moduleVersionString: String =
    implementation.moduleVersionString

  /**
   * Returns a description of this library.
   *
   * @return
   */
  def description: String =
    implementation.description

  /**
   * Returns the URL of the home page of this library.
   *
   * @return
   */
  def homePageURL: String =
    implementation.homePageURL

  /**
   * Returns the inception year of this library.
   *
   * @return
   */
  def inceptionYear: Int =
    implementation.inceptionYear

  /**
   * Returns the name of the organization developing of this library.
   *
   * @return
   */
  def organizationName: String =
    implementation.organizationName

  /**
   * Returns the id (i.e., package prefix) of the organization developing this library.
   *
   * @return
   */
  def organizationId: String =
    implementation.organizationId

  /**
   * Returns the URL of the home page of the organization developing this library.
   *
   * @return
   */
  def organizationHomePageURL: String =
    implementation.organizationHomePageURL

  /**
   * Returns the names of the developers of this library.
   *
   * @return
   */
  def developers: Seq[String] =
    implementation.developers

  /**
   * Returns the name of the organization in which the development of this library started.
   *
   * @return
   */
  def originalOrganizationName: String =
    implementation.originalOrganizationName

  /**
   * Returns the name of the developer who started the development of this library.
   *
   * @return
   */
  def originalDeveloperName: String =
    implementation.originalDeveloperName

  /**
   * Returns the name of the country in which the development of this library started.
   *
   * @return
   */
  def countryOfOrigin: String =
    implementation.countryOfOrigin

  /**
   * Returns the version of Scala used to build this library distribution.
   *
   * @return
   */
  def scalaVersion: String =
    implementation.scalaVersion

  /**
   * Returns the version of SBT used to build this library distribution.
   *
   * @return
   */
  def sbtVersion: String =
    implementation.sbtVersion

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
  def builtAtMillis: Long =
    implementation.builtAtMillis

  /**
   *
   *
   * @param providerImplementation
   */
  def setImplementation(providerImplementation: LibraryInformationProvider): Unit = {
    _implementation = Some(providerImplementation)
  }

  /**
   *
   *
   * @return
   */
  private
  def implementation: LibraryInformationProvider = {
    _implementation.getOrElse(
      throw ImplementationNotSetError("DefaultLibraryInformationProvider"))
  }

}
