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


package smcl


import smcl.infrastructure.LibraryInformationProvider




/**
 * Provides information about this SMCL build based on
 * a configuration file generated during build.
 */
object Library extends LibraryInformationProvider {



  /**
   * Returns the full name of this library.
   *
   * @return
   */
  override
  def fullName: String = ???

  /**
   * Returns the abbreviated name of this library.
   *
   * @return
   */
  override
  def abbreviatedName: String = ???

  /**
   * Returns the major version of this library.
   *
   * @return
   */
  override
  def majorVersion: Int = ???

  /**
   * Returns the minor version of this library.
   *
   * @return
   */
  override
  def minorVersion: Int = ???

  /**
   * Returns the microversion of this library.
   *
   * @return
   */
  override
  def microVersion: Int = ???

  /**
   * Returns the platform ID of this library.
   *
   * @return
   */
  override
  def platform: String = ???

  /**
   * Tells whether or not this library is running on Java platform.
   *
   * @return
   */
  override
  def isJavaPlatform: Boolean = ???

  /**
   * Tells whether or not this library is running on JavaScript platform.
   *
   * @return
   */
  override
  def isJavaScriptPlatform: Boolean = ???

  /**
   * Tells whether or not the version of this library is a release one.
   *
   * @return <code>true</code> in case of a release version, <code>false</code> otherwise
   */
  override
  def isRelease: Boolean = ???

  /**
   * Returns the version of this library as a string.
   *
   * @return
   */
  override
  def projectVersionString: String = ???

  /**
   * Returns the version of this module as a string.
   *
   * @return
   */
  override
  def moduleVersionString: String = ???

  /**
   * Returns a description of this library.
   *
   * @return
   */
  override
  def description: String = ???

  /**
   * Returns the URL of the home page of this library.
   *
   * @return
   */
  override
  def homePageURL: String = ???

  /**
   * Returns the inception year of this library.
   *
   * @return
   */
  override
  def inceptionYear: Int = ???

  /**
   * Returns the name of the organization developing of this library.
   *
   * @return
   */
  override
  def organizationName: String = ???

  /**
   * Returns the id (i.e., package prefix) of the organization developing this library.
   *
   * @return
   */
  override
  def organizationId: String = ???

  /**
   * Returns the URL of the home page of the organization developing this library.
   *
   * @return
   */
  override
  def organizationHomePageURL: String = ???

  /**
   * Returns the names of the developers of this library.
   *
   * @return
   */
  override
  def developers: Seq[String] = ???

  /**
   * Returns the name of the organization in which the development of this library started.
   *
   * @return
   */
  override
  def originalOrganizationName: String = ???

  /**
   * Returns the name of the developer who started the development of this library.
   *
   * @return
   */
  override
  def originalDeveloperName: String = ???

  /**
   * Returns the name of the country in which the development of this library started.
   *
   * @return
   */
  override
  def countryOfOrigin: String = ???

  /**
   * Returns the version of Scala used to build this library distribution.
   *
   * @return
   */
  override
  def scalaVersion: String = ???

  /**
   * Returns the version of SBT used to build this library distribution.
   *
   * @return
   */
  override
  def sbtVersion: String = ???

  /**
   * Returns the built time of this library distribution as a long integer
   * containing the milliseconds returned by Java's
   * <code>System.currentTimeMillis()</code>.
   *
   * @return
   */
  override
  def builtAtMillis: Long = ???

}
