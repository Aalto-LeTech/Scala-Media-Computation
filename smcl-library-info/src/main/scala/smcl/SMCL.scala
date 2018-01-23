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


import smcl.infrastructure.build.libraryinfo._




/**
 * Provides information about this SMCL build based on
 * a configuration file generated during build.
 */
object SMCL {

  /**  */
  lazy val project: SMCLProjectInfo =
    SMCLProjectInfo(
      fullName = /*<SMCLProjectFullName>*/ "" /*</SMCLProjectFullName>*/,
      abbreviatedName = /*<SMCLProjectAbbreviatedName>*/ "" /*</SMCLProjectAbbreviatedName>*/,
      description = /*<SMCLProjectDescription>*/ "" /*</SMCLProjectDescription>*/,
      homePageURL = /*<SMCLProjectHomepageURL>*/ "" /*</SMCLProjectHomepageURL>*/,
      inceptionYear = /*<SMCLProjectInceptionYear>*/ 1900 /*</SMCLProjectInceptionYear>*/
    )

  /**  */
  lazy val organization: SMCLOrganizationInfo =
    SMCLOrganizationInfo(
      organizationName = /*<SMCLOrganizationName>*/ "" /*</SMCLOrganizationName>*/,
      organizationId = /*<SMCLOrganizationId>*/ "" /*</SMCLOrganizationId>*/,
      organizationHomePageURL = /*<SMCLOrganizationHomePageURL>*/ "" /*</SMCLOrganizationHomePageURL>*/,
      originalOrganizationName = /*<SMCLOriginalOrganizationName>*/ "" /*</SMCLOriginalOrganizationName>*/,
      originalDeveloperName = /*<SMCLOriginalDeveloperName>*/ "" /*</SMCLOriginalDeveloperName>*/,
      countryOfOrigin = /*<SMCLCountryOfOrigin>*/ "" /*</SMCLCountryOfOrigin>*/
    )

  /**  */
  lazy val build: SMCLBuildInfo =
    SMCLBuildInfo(
      majorVersion = /*<SMCLBuildMajorVersion>*/ 0 /*</SMCLBuildMajorVersion>*/,
      minorVersion = /*<SMCLBuildMinorVersion>*/ 0 /*</SMCLBuildMinorVersion>*/,
      microVersion = /*<SMCLBuildMicroVersion>*/ 0 /*</SMCLBuildMicroVersion>*/,
      isRelease = /*<SMCLBuildIsRelease>*/ false /*</SMCLBuildIsRelease>*/,
      projectVersionString = /*<SMCLBuildProjectVersionString>*/ "" /*</SMCLBuildProjectVersionString>*/,
      scalaVersion = /*<SMCLBuildScalaVersion>*/ "" /*</SMCLBuildScalaVersion>*/,
      sbtVersion = /*<SMCLBuildSbtVersion>*/ "" /*</SMCLBuildSbtVersion>*/,
      sourceJavaVersion = /*<SMCLBuildSourceJavaVersion>*/ "" /*</SMCLBuildSourceJavaVersion>*/,
      targetJavaVersion = /*<SMCLBuildTargetJavaVersion>*/ "" /*</SMCLBuildTargetJavaVersion>*/,
      targetPlatform = /*<SMCLBuildTargetPlatform>*/ "" /*</SMCLBuildTargetPlatform>*/,
      isJVMAWTPlatform = /*<SMCLBuildIsJVMAWTPlatform>*/ false /*</SMCLBuildIsJVMAWTPlatform>*/,
      isJSHTML5Platform = /*<SMCLBuildIsJSHTML5Platform>*/ false /*</SMCLBuildIsJSHTML5Platform>*/,
      buildTime = /*<SMCLBuildTime>*/ 1L /*</SMCLBuildTime>*/
    )

  /**  */
  lazy val library: SMCLLibrary =
    SMCLLibrary(
      initializers = /*<SMCLLibraryInitializers>*/ Seq("") /*</SMCLLibraryInitializers>*/
    )

  /**  */
  lazy val module: SMCLModule =
    SMCLModule(
      initializer = /*<SMCLModuleInitializer>*/ "" /*</SMCLModuleInitializer>*/,
      version = /*<SMCLModuleVersion>*/ "" /*</SMCLModuleVersion>*/
    )

}
