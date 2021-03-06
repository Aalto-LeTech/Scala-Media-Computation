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


/**
 * SBT build script for Scala Media Computation Library (SMCL).
 *
 * @author Aleksi Lukkarinen
 */


import sbt.Keys._
import sbtcrossproject.{CrossProject, CrossType}  // To be removed after updating to Scala.js 1.0 ?


//--------------------------------------------------------------------------------------------------
//
// KEYS FOR SETTINGS AND TASKS
//
//--------------------------------------------------------------------------------------------------

lazy val abbreviatedName: SettingKey[String] = settingKey[String](
  "The abbreviated name of the project.")

lazy val countryOfOrigin: SettingKey[String] = settingKey[String](
  "The name of the country this project has originated in.")

lazy val originalOrganizationName: SettingKey[String] = settingKey[String](
  "The name of the original organization in which the development of this project started.")

lazy val targetPlatform: SettingKey[String] = settingKey[String](
  "ID string of the target platform of a subproject.")

lazy val isJVMAWTPlatform: SettingKey[Boolean] = settingKey[Boolean](
  "Whether or not a subproject targets JVM/AWT platform.")

lazy val isJSHTML5Platform: SettingKey[Boolean] = settingKey[Boolean](
  "Whether or not a subproject targets JS/HTML5 platform.")

lazy val packagedCommonResourcesFolder: SettingKey[File] = settingKey[File](
  "Folder containing the common resources that are to be packaged into core JARs.")

lazy val testCommonResourcesFolder: SettingKey[File] = settingKey[File](
  "Folder containing the common resources that are to be available during tests.")

lazy val generalJARAdditions: SettingKey[Seq[(java.io.File, String)]] =
  settingKey[Seq[(java.io.File, String)]](
    "Mappings of additional files common to all JARs produced from a subproject.")

lazy val moduleInitializerCommand: SettingKey[String] = settingKey[String](
  "Initialization commands that initializes a module.")

lazy val smclInitializerCommands: SettingKey[Seq[String]] = settingKey[Seq[String]](
  "Initialization commands that have to be executed before using any other SMCL facilities.")

lazy val releaseNotesTemplate: InputKey[File] = inputKey[File](
  "Generates a new template file for release notes")


//--------------------------------------------------------------------------------------------------
//
// GENERAL CONSTANTS AND CONFIGURATION
//
//--------------------------------------------------------------------------------------------------

lazy val projectFullName: String = "Scala Media Computation Library"
lazy val projectAbbrName: String = "SMCL"

lazy val projectMajorVersion: Int = 1
lazy val projectMinorVersion: Int = 0
lazy val projectMicroVersion: Int = 2
lazy val projectIsRelease: Boolean = false

lazy val buildTime: Long = System.currentTimeMillis()

lazy val prjSmclLibraryInfoId: String = "smcl-library-info"
lazy val prjSmclLibraryInfoName: String = projectFullName + ": Library Information Class Resources"
lazy val prjSmclLibraryInfoDescription: String =
  "The code resources providing library information capabilities."

lazy val prjSmclBitmapViewerId: String = "smcl-bitmap-viewer"
lazy val prjSmclBitmapViewerSubName: String = "Bitmap Viewer"
lazy val prjSmclBitmapViewerName: String = projectFullName + ": " + prjSmclBitmapViewerSubName
lazy val prjSmclBitmapViewerAbbrName: String = projectAbbrName + ": " + prjSmclBitmapViewerSubName
lazy val prjSmclBitmapViewerDescription: String = "Bitmap viewers for " + projectFullName + "."

lazy val prjSmclCoreId: String = "smcl-core"
lazy val prjSmclCoreSubName: String = "Core Library"
lazy val prjSmclCoreName: String = projectFullName + ": " + prjSmclCoreSubName
lazy val prjSmclCoreAbbrName: String = projectAbbrName + ": " + prjSmclCoreSubName
lazy val prjSmclCoreDescription: String = "A class library for bitmap processing using Scala."

lazy val cmnResFolderName: String = "common-resources"
lazy val packagedResFolderName: String = "packaged"
lazy val testResFolderName: String = "test"

lazy val snapshotIdPostfix: String = "-SNAPSHOT"

lazy val buildInfoPlatformIdJVMAWT: String = "jvm-awt"
lazy val buildInfoPlatformIdJSHTML5: String = "js-html5"

lazy val projectVersionString: String =
  Seq(projectMajorVersion, projectMinorVersion, projectMicroVersion).mkString(".")

lazy val moduleVersionString: String = {
  if (projectIsRelease)
    projectVersionString
  else
    projectVersionString + snapshotIdPostfix
}

lazy val projectJavaVersionSource: String = "1.8"
lazy val projectJavaVersionTarget: String = "1.8"

lazy val projectOriginalDeveloper: Developer = Developer(
  id = "lukkark1",
  name = "Aleksi Lukkarinen",
  email = "aleksi.lukkarinen@aalto.fi",
  url = null)

lazy val projectDevelopers: List[Developer] = List(
  projectOriginalDeveloper
)

lazy val smclDefaultImports: Seq[String] = Seq(
  "import smcl._",
  "import smcl.infrastructure._",
  "import smcl.settings._",
  "import smcl.modeling._",
  "import smcl.fonts._",
  "import smcl.colors._",
  "import smcl.pictures._",
  "import smcl.viewers._")

lazy val smclCoreInitializerCommandJVMAWT: String =
  "smcl.infrastructure.jvmawt.Initializer()"

lazy val smclBitmapViewerInitializerCommandJVMAWT: String =
  "smcl.viewers.bitmaps.jvmawt.Initializer()"

lazy val smclCoreInitializerCommandJSHTML5: String =
  "smcl.infrastructure.js.Initializer()"

lazy val smclBitmapViewerInitializerCommandJSHTML5: String =
  "smcl.viewers.bitmaps.js.Initializer()"

lazy val smclInitializerCommandsJVMAWT: Seq[String] = Seq(
  smclCoreInitializerCommandJVMAWT,
  smclBitmapViewerInitializerCommandJVMAWT)

lazy val smclInitializerCommandsJSHTML5: Seq[String] = Seq(
  smclCoreInitializerCommandJSHTML5,
  smclBitmapViewerInitializerCommandJSHTML5)

lazy val smclConsoleDefaultCommandsJVMAWT: String =
  ((smclDefaultImports :+ "") ++ smclInitializerCommandsJVMAWT)
      .mkString(System.lineSeparator())

lazy val slackHookUrlEnvVar = EnvVar("SLACK_HOOK_SMCL_RELEASES")

abbreviatedName in Global := "SMCL"

// TODO: Separate the department
originalOrganizationName in Global :=
    "Aalto University, Department of Computer Science"

organization in Global := "fi.aalto.cs"
organizationName in Global := originalOrganizationName.value
organizationHomepage in Global := Some(url("http://cs.aalto.fi/"))

startYear in Global := Some(2015)
countryOfOrigin in Global := "Finland"
homepage in Global := Some(url("http://github.com/Aalto-LeTech/Scala-Media-Computation"))
developers in Global ++= projectDevelopers

// @formatter:off
description in Global := {
  s"""${abbreviatedName.value} is a class library created to support Scala-based
     |media-oriented introductory programming teaching. It was originally
     |created by ${projectOriginalDeveloper.name} in ${startYear.value} as a part
     |of his Master's Thesis for $originalOrganizationName,
     |${countryOfOrigin.value}.""".stripMargin.replaceAll("\n", " ")
}
// @formatter:on

version in Global := moduleVersionString
isSnapshot in Global := !projectIsRelease

scalaVersion in Global := "2.12.3"

parallelExecution in Global := true

logLevel in Global := Level.Info


//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
// The following definitions exist to keep SBT from publishing
// unnecessary empty artifacts from the aggregate project.
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

Keys.compile := {
  (Keys.compile in (smclBitmapViewerJVM, Compile)).value
  (Keys.compile in (smclCoreJVM, Compile)).value
  (Keys.compile in (smclBitmapViewerJS, Compile)).value
  (Keys.compile in (smclCoreJS, Compile)).value
}

Keys.`package` := {
  (Keys.`package` in (smclBitmapViewerJVM, Compile)).value
  (Keys.`package` in (smclCoreJVM, Compile)).value
  (Keys.`package` in (smclBitmapViewerJS, Compile)).value
  (Keys.`package` in (smclCoreJS, Compile)).value
}

Keys.test := {
  (Keys.test in (smclBitmapViewerJVM, Test)).value
  (Keys.test in (smclCoreJVM, Test)).value
  (Keys.test in (smclBitmapViewerJS, Test)).value
  (Keys.test in (smclCoreJS, Test)).value
}

Keys.test in LearningTest := {
  (Keys.test in (smclCoreJVM, LearningTest)).value
  (Keys.test in (smclCoreJS, LearningTest)).value
  (Keys.test in (smclBitmapViewerJVM, LearningTest)).value
  (Keys.test in (smclBitmapViewerJS, LearningTest)).value
}

Keys.test in ItgTest := {
  (Keys.test in (smclCoreJVM, ItgTest)).value
  (Keys.test in (smclCoreJS, ItgTest)).value
  (Keys.test in (smclBitmapViewerJVM, ItgTest)).value
  (Keys.test in (smclBitmapViewerJS, ItgTest)).value
}

Keys.test in GUITest := {
  (Keys.test in (smclCoreJVM, GUITest)).value
  (Keys.test in (smclCoreJS, GUITest)).value
  (Keys.test in (smclBitmapViewerJVM, GUITest)).value
  (Keys.test in (smclBitmapViewerJS, GUITest)).value
}

Keys.test in SmokeTest := {
  (Keys.test in (smclCoreJVM, SmokeTest)).value
  (Keys.test in (smclCoreJS, SmokeTest)).value
  (Keys.test in (smclBitmapViewerJVM, SmokeTest)).value
  (Keys.test in (smclBitmapViewerJS, SmokeTest)).value
}

Keys.doc := {
  (Keys.doc in (smclCoreJVM, Compile)).value
  (Keys.doc in (smclCoreJS, Compile)).value
  (Keys.doc in (smclBitmapViewerJVM, Compile)).value
  (Keys.doc in (smclBitmapViewerJS, Compile)).value
}


//--------------------------------------------------------------------------------------------------
//
// COMMAND ALIASES
//
//--------------------------------------------------------------------------------------------------

addCommandAlias("cp", "; clean ; package")
addCommandAlias("rcp", "; reload ; clean ; package")
addCommandAlias("cpt", "; clean ; package ; test")
addCommandAlias("rcpt", "; reload ; clean ; package ; test")
addCommandAlias("testAll", "; learning:test ; test ; integration:test ; gui:test ; smoke:test")

addCommandAlias("jvmC", "; smcl-coreJVM/clean ; smcl-bitmap-viewerJVM/clean")
addCommandAlias("jvmP", "; smcl-coreJVM/package ; smcl-bitmap-viewerJVM/package")
addCommandAlias("jvmCP", "; jvmC ; jvmP")
addCommandAlias("jvmCPT", "; jvmC ; jvmP ; jvmUT")
addCommandAlias("jvmUT", "; smcl-coreJVM/test ; smcl-bitmap-viewerJVM/test")
addCommandAlias("jvmIT", "; smcl-coreJVM/integration:test ; smcl-bitmap-viewerJVM/integration:test")
addCommandAlias("jvmLT", "; smcl-coreJVM/learning:test ; smcl-bitmap-viewerJVM/learning:test")
addCommandAlias("jvmGT", "; smcl-coreJVM/gui:test ; smcl-bitmap-viewerJVM/gui:test")
addCommandAlias("jvmST", "; smcl-coreJVM/smoke:test ; smcl-bitmap-viewerJVM/smoke:test")
addCommandAlias("jvmTestAll", "; jvmST ; jvmUT ; jvmIT ; jvmLT ; jvmGT")
addCommandAlias("jvmD", "; smcl-coreJVM/doc ; smcl-bitmap-viewerJVM/doc")
addCommandAlias("jvmPublishLocal", "; smcl-coreJVM/publishLocal ; smcl-bitmap-viewerJVM/publishLocal")
addCommandAlias("jvmPublishM2", "; smcl-coreJVM/publishM2 ; smcl-bitmap-viewerJVM/publishM2")


//--------------------------------------------------------------------------------------------------
//
// TEST DEFINITIONS
//
//--------------------------------------------------------------------------------------------------

lazy val confUnitTestId: String = "test"
lazy val confSmokeTestId: String = "smoke"
lazy val confItgTestId: String = "integration"
lazy val confGUITestId: String = "gui"
lazy val confLearningTestId: String = "learning"

lazy val testConfIDs: Seq[String] = Seq(
  confUnitTestId,
  confItgTestId,
  confGUITestId,
  confLearningTestId,
  confSmokeTestId
)

lazy val testConfIDCommaString: String = testConfIDs.mkString(",")

def createConfToConfSemicolonString(ids: Seq[String]): String = {
  val idToIdSeparator = "->"
  val listSeparator = ";"

  def idToId(id: String): String = Seq(id, id).mkString(idToIdSeparator)

  ids.map(idToId).mkString(listSeparator)
}

lazy val confToConfSemiColonString: String =
  createConfToConfSemicolonString("compile" +: testConfIDs)


lazy val SmokeTest: Configuration =
  config(confSmokeTestId) extend Test describedAs "For running smoke tests"

lazy val ItgTest: Configuration =
  config(confItgTestId) extend Test describedAs "For running integration tests"

lazy val GUITest: Configuration =
  config(confGUITestId) extend Test describedAs "For running GUI-based tests"

lazy val LearningTest: Configuration =
  config(confLearningTestId) extend Test describedAs "For running learning tests"


def smokeTestFilterForJVM(name: String): Boolean =
  (name endsWith "SmokeTests") || (name endsWith "SmokeTestsForJVM") ||
      (name endsWith "SmokeTestsuite") || (name endsWith "SmokeTestsuiteForJVM")

def smokeTestFilterForJS(name: String): Boolean =
  (name endsWith "SmokeTestsForJS") || (name endsWith "SmokeTestsuiteForJS")


def integrationTestFilterForJVM(name: String): Boolean =
  (name endsWith "ItgTests") || (name endsWith "ItgTestsForJVM") ||
      (name endsWith "ItgTestsuite") || (name endsWith "ItgTestsuiteForJVM")

def integrationTestFilterForJS(name: String): Boolean =
  (name endsWith "ItgTestsForJS") || (name endsWith "ItgTestsuiteForJS")


def guiTestFilterForJVM(name: String): Boolean =
  (name endsWith "GUITests") || (name endsWith "GUITestsForJVM") ||
      (name endsWith "GUITestsuite") || (name endsWith "GUITestsuiteForJVM")

def guiTestFilterForJS(name: String): Boolean =
  (name endsWith "GUITestsForJS") || (name endsWith "GUITestsuiteForJS")


def learningTestFilterForJVM(name: String): Boolean =
  (name endsWith "LearningTests") || (name endsWith "LearningTestsForJVM") ||
      (name endsWith "LearningTestsuite") || (name endsWith "LearningTestsuiteForJVM")

def learningTestFilterForJS(name: String): Boolean =
  (name endsWith "LearningTestsForJS") || (name endsWith "LearningTestsuiteForJS")


def unitTestFilterForJVM(name: String): Boolean =
  ((name endsWith "Tests") || (name endsWith "TestsForJVM") ||
      (name endsWith "Testsuite") || (name endsWith "TestsuiteForJVM")) &&
      !(integrationTestFilterForJVM(name) ||
          smokeTestFilterForJVM(name) ||
          guiTestFilterForJVM(name) ||
          learningTestFilterForJVM(name))

def unitTestFilterForJS(name: String): Boolean =
  ((name endsWith "TestsForJS") || (name endsWith "TestsuiteForJS")) &&
      !(integrationTestFilterForJS(name) ||
          smokeTestFilterForJS(name) ||
          guiTestFilterForJS(name) ||
          learningTestFilterForJS(name))


//--------------------------------------------------------------------------------------------------
//
// GENERAL SETTINGS
//
//--------------------------------------------------------------------------------------------------

lazy val smclGeneralSettings: Seq[Def.Setting[_]] = Seq(
  packagedCommonResourcesFolder :=
      (baseDirectory in ThisBuild).value / cmnResFolderName / packagedResFolderName,

  testCommonResourcesFolder :=
      (baseDirectory in ThisBuild).value / cmnResFolderName / testResFolderName,
)

lazy val smclGeneralCodeProjectSettings: Seq[Def.Setting[_]] = smclGeneralSettings ++ Seq(
  slackNotify := None, // To limit slackNotify to the aggregate project only

  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked"
  ),

  scalacOptions in (Compile, doc) := Seq(
    "-implicits",
    "-groups",
    "-doc-root-content", baseDirectory.value + "/root-doc.txt",
    "-doc-version", (scalaVersion in Global).value
  ),

  javacOptions ++= Seq(
    "-source", projectJavaVersionSource,
    "-target", projectJavaVersionTarget
  ),

  libraryDependencies ++= Seq(
    /**
     * scalatest
     *
     * @see http://www.scalatest.org
     * @see http://search.maven.org/#search|ga|1|scalatest
     */
    "org.scalatest" %%% "scalatest" % "3.0.4" % testConfIDCommaString,

    /**
     * ScalaCheck
     *
     * @see https://www.scalacheck.org/
     * @see http://search.maven.org/#search|ga|1|scalacheck
     */
    "org.scalacheck" %%% "scalacheck" % "1.13.4" % testConfIDCommaString

    /**
     * Scalactic
     *
     * @see http://www.scalactic.org/
     * @see http://search.maven.org/#search|ga|1|scalactic
     */
    // "org.scalactic" %% "scalactic" % "3.0.1"

    /**
     * Scalaz
     *
     * @see https://github.com/scalaz/scalaz
     * @see http://search.maven.org/#search|ga|1|scalaz
     */
    // "org.scalaz" %% "scalaz-core" % "7.2.12"
  ),

  libraryinfoSourceProject in Compile := Some(`smcl-library-info`),

  libraryinfoIncludeFileFilter in Compile := new FileFilter {

    val pathSeparator = "\\" + Path.sep

    val path = Seq(".*", "smcl", "infrastructure", "build", ".*").mkString(pathSeparator)

    def accept(f: File): Boolean =
      path.r.pattern.matcher(f.getAbsolutePath).matches

  },

  libraryinfoFilteredKeysValues in Compile := Seq[(String, Any)](
    "SMCLProjectFullName" -> projectFullName,
    "SMCLProjectAbbreviatedName" -> abbreviatedName.value,
    "SMCLProjectDescription" -> description.value,
    "SMCLProjectHomepageURL" -> homepage.value.get.toString,
    "SMCLProjectInceptionYear" -> startYear.value.get,
    "SMCLOrganizationName" -> organizationName.value,
    "SMCLOrganizationId" -> organization.value,
    "SMCLOrganizationHomePageURL" -> organizationHomepage.value.get.toString,
    "SMCLOriginalOrganizationName" -> originalOrganizationName.value,
    "SMCLOriginalDeveloperName" -> projectOriginalDeveloper.name,
    "SMCLCountryOfOrigin" -> countryOfOrigin.value,
    "SMCLBuildMajorVersion" -> projectMajorVersion,
    "SMCLBuildMinorVersion" -> projectMinorVersion,
    "SMCLBuildMicroVersion" -> projectMicroVersion,
    "SMCLBuildIsRelease" -> projectIsRelease,
    "SMCLBuildProjectVersionString" -> projectVersionString,
    "SMCLBuildScalaVersion" -> scalaVersion.value,
    "SMCLBuildSbtVersion" -> sbtVersion.value,
    "SMCLBuildSourceJavaVersion" -> projectJavaVersionSource,
    "SMCLBuildTargetJavaVersion" -> projectJavaVersionTarget,
    "SMCLBuildTargetPlatform" -> targetPlatform.value,
    "SMCLBuildIsJVMAWTPlatform" -> isJVMAWTPlatform.value,
    "SMCLBuildIsJSHTML5Platform" -> isJSHTML5Platform.value,
    "SMCLBuildTime" -> buildTime,
    "SMCLLibraryInitializers" -> smclInitializerCommands.value,
    "SMCLModuleInitializer" -> moduleVersionString,
    "SMCLModuleVersion" -> moduleInitializerCommand.value
  ),

  publishArtifact in Test := true,

  generalJARAdditions := {
    Seq[(java.io.File, String)](
      new java.io.File("LICENSE") -> "LICENSE"
      // , resourceManaged.value / "main" / "smcl-config.json" -> "conf/smcl-config.json"
    )
  },

  mappings in (Compile, packageBin) ++=
      generalJARAdditions.value,

  mappings in (Compile, packageDoc) ++=
      generalJARAdditions.value,

  mappings in (Compile, packageSrc) ++=
      generalJARAdditions.value,

  mappings in (Test, packageBin) ++=
      generalJARAdditions.value,

  mappings in (Test, packageDoc) ++=
      generalJARAdditions.value,

  mappings in (Test, packageSrc) ++=
      generalJARAdditions.value
)

lazy val smclGeneralJsSettings: Seq[Def.Setting[_]] = Seq(
  libraryDependencies ++= Seq(
    /**
     * Scala.js DOM
     *
     * @see https://www.scala-js.org/
     * @see http://search.maven.org/#search|ga|1|scalajs-dom
     */
    "org.scala-js" %%% "scalajs-dom" % "0.9.4"
  ),

  // TODO: Check the correctness of the line below when updating to Scala.js 1.0
  jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv,

  isJVMAWTPlatform := false,
  isJSHTML5Platform := !isJVMAWTPlatform.value,
  targetPlatform := buildInfoPlatformIdJSHTML5,
  smclInitializerCommands := smclInitializerCommandsJSHTML5,

  testOptions in Test := Seq(Tests.Filter(unitTestFilterForJS)),
  testOptions in ItgTest := Seq(Tests.Filter(integrationTestFilterForJS)),
  testOptions in GUITest := Seq(Tests.Filter(guiTestFilterForJS)),
  testOptions in SmokeTest := Seq(Tests.Filter(smokeTestFilterForJS)),
  testOptions in LearningTest := Seq(Tests.Filter(learningTestFilterForJS))
  // testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "???")
)

lazy val smclGeneralJvmSettings: Seq[Def.Setting[_]] = Seq(
  libraryDependencies ++= Seq(
    /**
     * Scala.js Stubs for Scala
     *
     * @see https://www.scala-js.org/
     * @see http://search.maven.org/#search|ga|1|scalajs-stubs
     */
    "org.scala-js" %% "scalajs-stubs" % "0.6.21" % "provided"
  ),

  isJVMAWTPlatform := true,
  isJSHTML5Platform := !isJVMAWTPlatform.value,
  targetPlatform := buildInfoPlatformIdJVMAWT,
  smclInitializerCommands := smclInitializerCommandsJVMAWT,

  testOptions in Test := Seq(Tests.Filter(unitTestFilterForJVM)),
  testOptions in ItgTest := Seq(Tests.Filter(integrationTestFilterForJVM)),
  testOptions in GUITest := Seq(Tests.Filter(guiTestFilterForJVM)),
  testOptions in SmokeTest := Seq(Tests.Filter(smokeTestFilterForJVM)),
  testOptions in LearningTest := Seq(Tests.Filter(learningTestFilterForJVM)),
  // testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "???")

  initialCommands in console :=
      smclConsoleDefaultCommandsJVMAWT
)


//--------------------------------------------------------------------------------------------------
//
// PROJECT: SMCL LIBRARY INFORMATION CLASS RESOURCES
//
// This project exists in this build script only to make IDE to understand its structure.
// It is not treated as a normal project in the build, but its content is copied during build
// to the actual projects as managed code. Consequently, the code of this project exists in
// the actual projects as it was written directly in each of them.
//
//--------------------------------------------------------------------------------------------------

// Do NOT add the general setting collections to this project!!
lazy val `smcl-library-info`: Project = project.in(file("smcl-library-info"))
    .disablePlugins(SbtGithubReleasePlugin)
    .settings(
      name := prjSmclLibraryInfoId,
      description := prjSmclLibraryInfoDescription,
      onLoadMessage := prjSmclLibraryInfoName + " Project Loaded",

      publishArtifact in Compile := false,
      publish := {},
      publishLocal := {},
      publishM2 := {}
    )


//--------------------------------------------------------------------------------------------------
//
// PROJECT: SMCL BITMAP VIEWER
//
//--------------------------------------------------------------------------------------------------

lazy val smclBitmapViewer: CrossProject =
  CrossProject(prjSmclBitmapViewerId, file(prjSmclBitmapViewerId))(JVMPlatform, JSPlatform)
      .crossType(CrossType.Full)
      .configs(ItgTest, GUITest, SmokeTest, LearningTest)
      .enablePlugins(LibraryInfoPlugin)
      .disablePlugins(SbtGithubReleasePlugin)
      .settings(
        name := prjSmclBitmapViewerId,
        description := prjSmclBitmapViewerDescription,

        smclGeneralCodeProjectSettings,

        libraryinfoIncludeFileFilter in Compile :=
            (libraryinfoIncludeFileFilter in Compile).value || "SMCLBitmapViewer.scala",

        scalacOptions in (Compile, doc) := Seq("-doc-title", prjSmclBitmapViewerAbbrName),

        inConfig(ItgTest)(Defaults.testTasks),
        inConfig(GUITest)(Defaults.testTasks),
        inConfig(SmokeTest)(Defaults.testTasks),
        inConfig(LearningTest)(Defaults.testTasks)
      )
      .jvmSettings(
        smclGeneralJvmSettings,
        onLoadMessage := prjSmclBitmapViewerName + " JVM/AWT Project Loaded",
        moduleInitializerCommand := smclBitmapViewerInitializerCommandJVMAWT,

        libraryDependencies ++= Seq(
          /**
           * RxScala
           *
           * @see http://reactivex.io/rxscala/
           * @see http://search.maven.org/#search|ga|1|rxscala
           */
          "io.reactivex" %% "rxscala" % "0.26.5",

          /**
           * Scala Swing
           *
           * @see http://www.scala-lang.org/
           * @see http://search.maven.org/#search|ga|1|scala-swing
           */
          "org.scala-lang.modules" %% "scala-swing" % "2.0.0"
        ),
      )
      .jsConfigure(_.enablePlugins(JSDependenciesPlugin))
      .jsSettings(
        smclGeneralJsSettings,

        onLoadMessage := prjSmclBitmapViewerName + " JS/HTML5 Project Loaded",
        moduleInitializerCommand := smclBitmapViewerInitializerCommandJSHTML5,

        inConfig(ItgTest)(ScalaJSPlugin.testConfigSettings),
        inConfig(GUITest)(ScalaJSPlugin.testConfigSettings),
        inConfig(SmokeTest)(ScalaJSPlugin.testConfigSettings),
        inConfig(LearningTest)(ScalaJSPlugin.testConfigSettings)
      )
      .dependsOn(smclCore % confToConfSemiColonString)

lazy val smclBitmapViewerJVM: Project = smclBitmapViewer.jvm
lazy val smclBitmapViewerJS: Project = smclBitmapViewer.js


//--------------------------------------------------------------------------------------------------
//
// PROJECT: SMCL CORE LIBRARY
//
//--------------------------------------------------------------------------------------------------

lazy val smclCore: CrossProject =
  CrossProject(prjSmclCoreId, file(prjSmclCoreId))(JVMPlatform, JSPlatform)
      .crossType(CrossType.Full)
      .configs(ItgTest, GUITest, SmokeTest, LearningTest)
      .enablePlugins(LibraryInfoPlugin)
      .disablePlugins(SbtGithubReleasePlugin)
      .settings(
        name := prjSmclCoreId,
        description := prjSmclCoreDescription,

        smclGeneralCodeProjectSettings,

        libraryinfoIncludeFileFilter in Compile :=
            (libraryinfoIncludeFileFilter in Compile).value || "SMCL.scala" || "SMCLCore.scala",

        scalacOptions ++= Seq(
          "-opt:l:inline",
          "-opt-inline-from:**",
          "-opt-warnings:at-inline-failed",
        ),
        scalacOptions in (Compile, doc) := Seq("-doc-title", prjSmclCoreAbbrName),

        unmanagedResourceDirectories in Compile += packagedCommonResourcesFolder.value,
        unmanagedResourceDirectories in Test += testCommonResourcesFolder.value,

        inConfig(ItgTest)(Defaults.testTasks),
        inConfig(GUITest)(Defaults.testTasks),
        inConfig(SmokeTest)(Defaults.testTasks),
        inConfig(LearningTest)(Defaults.testTasks)
      )
      .jvmSettings(
        smclGeneralJvmSettings,

        onLoadMessage := prjSmclCoreName + " JVM/AWT Project Loaded",
        moduleInitializerCommand := smclCoreInitializerCommandJVMAWT
      )
      .jsConfigure(_.enablePlugins(JSDependenciesPlugin))
      .jsSettings(
        smclGeneralJsSettings,

        onLoadMessage := prjSmclCoreName + " JS/HTML5 Project Loaded",
        moduleInitializerCommand := smclCoreInitializerCommandJSHTML5,

        inConfig(ItgTest)(ScalaJSPlugin.testConfigSettings),
        inConfig(GUITest)(ScalaJSPlugin.testConfigSettings),
        inConfig(SmokeTest)(ScalaJSPlugin.testConfigSettings),
        inConfig(LearningTest)(ScalaJSPlugin.testConfigSettings)
      )

lazy val smclCoreJVM: Project = smclCore.jvm
lazy val smclCoreJS: Project = smclCore.js


//--------------------------------------------------------------------------------------------------
//
// PROJECT: SMCL ROOT AGGREGATE
//
//--------------------------------------------------------------------------------------------------
lazy val smcl: Project = project.in(file("."))
    .settings(
      smclGeneralSettings,

      onLoadMessage := projectFullName + " Root Project Loaded",

      clean := clean.dependsOn(
        clean in `smcl-library-info`
      ).value,

      resourceDirectory in Compile := packagedCommonResourcesFolder.value,
      resourceDirectory in Test := testCommonResourcesFolder.value,

      slackRoom := "#smcl-releases",

      slackHookUrl := slackHookUrlEnvVar.fold[String]{
        sLog.value.warn(
          s"::\n:: Environment variable ${slackHookUrlEnvVar.name} is undefined!!\n::")

        ""
      }{value => value},

      ghreleaseNotes := {tagName =>
        val notes = SMCLReleaseNotes.getFor(tagName, baseDirectory.value)
        if (notes.content.isEmpty) {
          throw new IllegalStateException(
            s"""Release notes file "${notes.file.getAbsolutePath}" is empty!""")
        }

        notes.content
      },

      releaseNotesTemplate := {
        val tagName = SbtGithubReleasePlugin.tagNameArg.parsed
        val smclVersion = SMCLVersion.from(tagName).forRelease
        val notesFile = SMCLReleaseNotes.fileFor(smclVersion, baseDirectory.value)
        if (notesFile.exists()) {
          throw new IllegalStateException(
            s"""Release notes file "${notesFile.getAbsolutePath}" exists already!""")
        }

        val templateContent: String =
          s"""# Release Notes for $projectFullName ${smclVersion.toString}
             |""".stripMargin
        IO.writeLines(notesFile, templateContent.split("\n"))

        notesFile
      },

      initialCommands in console :=
          smclConsoleDefaultCommandsJVMAWT,

      publishArtifact in Compile := false,
      publish := {},
      publishLocal := {},
      publishM2 := {}
    )
    .aggregate(
      smclBitmapViewerJVM, smclCoreJVM,
      smclBitmapViewerJS, smclCoreJS)

    // These dependencies are required to have their
    // contents inserted to the classpath of SBT's console
    .dependsOn(smclCoreJVM, smclBitmapViewerJVM)
