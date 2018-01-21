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
 *
 * SBT build script for Scala Media Computation Library (SMCL).
 *
 */


import org.scalajs.sbtplugin.ScalaJSPluginInternal
import org.scalajs.sbtplugin.cross.CrossProject
import sbt.Keys.{scalacOptions, testOptions, _}
import sbt.inConfig
import sbtbuildinfo.BuildInfoPlugin.autoImport.buildInfoPackage




enablePlugins(ScalaJSPlugin)


//-------------------------------------------------------------------------------------------------
//
// GENERAL CONSTANTS
//
//-------------------------------------------------------------------------------------------------

lazy val projectFullName = "Scala Media Computation Library"
lazy val projectAbbreviatedName = "SMCL"

lazy val projectMajorVersion = 0
lazy val projectMinorVersion = 0
lazy val projectMicroVersion = 3
lazy val projectIsRelease = false

lazy val projectHomepageUrl = "http://github.com/Aalto-LeTech/Scala-Media-Computation"

lazy val projectStartYear = 2015
// TODO: Separate the department
lazy val projectOriginalOrganizationName = "Aalto University, Department of Computer Science"
lazy val projectCountryOfOrigin = "Finland"
lazy val projectOriginalDeveloper = Developer(
  id = "lukkark1",
  name = "Aleksi Lukkarinen",
  email = "aleksi.lukkarinen@aalto.fi",
  url = null)

lazy val projectOrganizationName = projectOriginalOrganizationName
lazy val projectOrganizationId = "fi.aalto.cs"
lazy val projectOrganizationUrl = "http://cs.aalto.fi/"
lazy val projectDevelopers = List(
  projectOriginalDeveloper
)

// @formatter:off
lazy val projectDescription: String =
    s"""$projectAbbreviatedName is a class library created to support Scala-based
       |media-oriented introductory programming teaching. It was originally
       |created by ${projectOriginalDeveloper.name} in $projectStartYear as a part
       |of his Master's Thesis for $projectOriginalOrganizationName,
       |$projectCountryOfOrigin.""".stripMargin.replaceAll("\n", " ")
  // @formatter:on

lazy val primaryScalaVersion = "2.12.4"

lazy val projectJavaVersionSource = "1.8"
lazy val projectJavaVersionTarget = "1.8"

lazy val projectIdJvmPostfix = "-jvm"
lazy val projectIdJsPostfix = "-js"
lazy val projectIdTestPostfix = "-tests"

lazy val prjSmclBitmapViewerId = "smcl-bitmap-viewer"
lazy val prjSmclBitmapViewerJvmId = prjSmclBitmapViewerId + projectIdJvmPostfix
lazy val prjSmclBitmapViewerJsId = prjSmclBitmapViewerId + projectIdJsPostfix
lazy val prjSmclBitmapViewerName = projectFullName + " Bitmap Viewer"
lazy val prjSmclBitmapViewerDescription = "Bitmap viewers for " + projectFullName + "."

lazy val prjSmclCoreId = "smcl-core"
lazy val prjSmclCoreJvmId = prjSmclCoreId + projectIdJvmPostfix
lazy val prjSmclCoreJsId = prjSmclCoreId + projectIdJsPostfix
lazy val prjSmclCoreName = projectFullName + " Core Library"
lazy val prjSmclCoreDescription = "A class library for bitmap processing using Scala."

lazy val snapshotIdPostfix = "-SNAPSHOT"

lazy val projectVersionString =
  Seq(projectMajorVersion, projectMinorVersion, projectMicroVersion).mkString(".")

lazy val moduleVersionString = {
  if (projectIsRelease)
    projectVersionString
  else
    projectVersionString + snapshotIdPostfix
}

lazy val buildInfoObjectNameJvm = "Library"
lazy val buildInfoPackageNameJvm = "smcl"
lazy val buildInfoPlatformIdJvm = "jvm-awt"

lazy val buildInfoObjectNameJs = buildInfoObjectNameJvm
lazy val buildInfoPackageNameJs = buildInfoPackageNameJvm
lazy val buildInfoPlatformIdJs = "js-html5"

lazy val buildInfoKeyPlatform = "platform"
lazy val buildInfoKeyIsJavaPlatform = "isJavaPlatform"
lazy val buildInfoKeyIsJavaScriptPlatform = "isJavaScriptPlatform"


//-------------------------------------------------------------------------------------------------
//
// GENERAL TASK DEFINITIONS
//
//-------------------------------------------------------------------------------------------------

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


//-------------------------------------------------------------------------------------------------
//
// COMMAND ALIASES
//
//-------------------------------------------------------------------------------------------------

addCommandAlias("cp", "; clean ; package")

addCommandAlias("rcp", "; reload ; clean ; package")

addCommandAlias("cpt", "; clean ; package ; test")

addCommandAlias("rcpt", "; reload ; clean ; package ; test")

addCommandAlias("testAll", "; learning:test ; test ; integration:test ; gui:test ; smoke:test")


//-------------------------------------------------------------------------------------------------
//
// TEST DEFINITIONS
//
//-------------------------------------------------------------------------------------------------

lazy val confUnitTestId = "test"
lazy val confSmokeTestId = "smoke"
lazy val confItgTestId = "integration"
lazy val confGUITestId = "gui"
lazy val confLearningTestId = "learning"

lazy val testConfIDs: Seq[String] = Seq(
  confUnitTestId,
  confItgTestId,
  confGUITestId,
  confLearningTestId,
  confSmokeTestId
)

lazy val testConfIDCommaString = testConfIDs.mkString(",")

def createConfToConfSemicolonString(ids: Seq[String]): String = {
  val idToIdSeparator = "->"
  val listSeparator = ";"

  def idToId(id: String): String = Seq(id, id).mkString(idToIdSeparator)

  ids.map(idToId).mkString(listSeparator)
}

lazy val confToConfSemiColonString =
  createConfToConfSemicolonString("compile" +: testConfIDs)


lazy val SmokeTest = config(confSmokeTestId) extend Test describedAs "For running smoke tests"
lazy val ItgTest = config(confItgTestId) extend Test describedAs "For running integration tests"
lazy val GUITest = config(confGUITestId) extend Test describedAs "For running GUI-based tests"
lazy val LearningTest = config(confLearningTestId) extend Test describedAs "For running learning tests"


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


//-------------------------------------------------------------------------------------------------
//
// GENERAL SETTINGS
//
//-------------------------------------------------------------------------------------------------

inThisBuild(Seq(
  version := moduleVersionString,
  isSnapshot := !projectIsRelease,

  organization := projectOrganizationId,
  organizationName := projectOrganizationName,
  organizationHomepage := Some(url(projectOrganizationUrl)),

  startYear := Some(projectStartYear),
  homepage := Some(url(projectHomepageUrl)),
  developers ++= projectDevelopers,

  scalaVersion := primaryScalaVersion,

  parallelExecution := true,

  logLevel := Level.Info
))

lazy val smclGeneralSettings = Seq(
  scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),

  scalacOptions in (Compile, doc) := Seq(
    "-implicits",
    "-doc-root-content", baseDirectory.value + "/root-doc.txt"
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
    "org.scalatest" %%% "scalatest" % "3.0.1" % testConfIDCommaString withSources () withJavadoc (),

    /**
     * ScalaCheck
     *
     * @see https://www.scalacheck.org/
     * @see http://search.maven.org/#search|ga|1|scalacheck
     */
    "org.scalacheck" %%% "scalacheck" % "1.13.4" % testConfIDCommaString withSources () withJavadoc ()

    /**
     * Scalactic
     *
     * @see http://www.scalactic.org/
     * @see http://search.maven.org/#search|ga|1|scalactic
     */
    // "org.scalactic" %% "scalactic" % "3.0.1" withSources() withJavadoc()

    /**
     * Scalaz
     *
     * @see https://github.com/scalaz/scalaz
     * @see http://search.maven.org/#search|ga|1|scalaz
     */
    // "org.scalaz" %% "scalaz-core" % "7.2.12"
  ),

  publishArtifact in Test := true,

  initialCommands in console :=
      """import smcl._
        |import smcl.infrastructure._
        |import smcl.settings._
        |import smcl.modeling._
        |import smcl.fonts._
        |import smcl.colors._
        |import smcl.pictures._
        |import smcl.viewers._
        |
        |smcl.infrastructure.jvmawt.Initializer()
        |smcl.viewers.bitmaps.jvmawt.Initializer()
        |""".stripMargin
)

lazy val smclGeneralBuildInfoSettings = Seq(
  buildInfoKeys := Seq[BuildInfoKey](
    "fullName" -> projectFullName,
    "abbreviatedName" -> projectAbbreviatedName,
    "majorVersion" -> projectMajorVersion,
    "minorVersion" -> projectMinorVersion,
    "microVersion" -> projectMicroVersion,
    "isRelease" -> projectIsRelease,
    "projectVersionString" -> projectVersionString,
    "moduleVersionString" -> moduleVersionString,
    "description" -> projectDescription,
    "homePageURL" -> projectHomepageUrl,
    "inceptionYear" -> projectStartYear,
    "organizationName" -> projectOrganizationName,
    "organizationId" -> projectOrganizationId,
    "organizationHomePageURL" -> projectOrganizationUrl,
    // TODO: Create info for contact person
    // TODO: return objects instead of strings
    "developers" -> projectDevelopers,
    "originalOrganizationName" -> projectOriginalOrganizationName,
    "originalDeveloperName" -> projectOriginalDeveloper.name,
    "countryOfOrigin" -> projectCountryOfOrigin,
    scalaVersion,
    sbtVersion),

  buildInfoOptions += BuildInfoOption.BuildTime,

  buildInfoUsePackageAsPath := true,

  buildInfoOptions +=
      BuildInfoOption.Traits(
        "smcl.infrastructure.LibraryInformationProvider")
)

lazy val smclGeneralJsSettings =
  smclGeneralBuildInfoSettings ++ Seq(
    libraryDependencies ++= Seq(
      /**
       * Scala.js DOM
       *
       * @see https://www.scala-js.org/
       * @see http://search.maven.org/#search|ga|1|scalajs-dom
       */
      "org.scala-js" %%% "scalajs-dom" % "0.9.1" withSources () withJavadoc ()
    ),

    jsDependencies += RuntimeDOM,

    buildInfoObject := buildInfoObjectNameJs,
    buildInfoPackage := buildInfoPackageNameJs,
    buildInfoKeys ++= Seq[BuildInfoKey](
      buildInfoKeyPlatform -> buildInfoPlatformIdJs,
      buildInfoKeyIsJavaPlatform -> false,
      buildInfoKeyIsJavaScriptPlatform -> true
    ),

    testOptions in Test := Seq(Tests.Filter(unitTestFilterForJS)),
    testOptions in ItgTest := Seq(Tests.Filter(integrationTestFilterForJS)),
    testOptions in GUITest := Seq(Tests.Filter(guiTestFilterForJS)),
    testOptions in SmokeTest := Seq(Tests.Filter(smokeTestFilterForJS)),
    testOptions in LearningTest := Seq(Tests.Filter(learningTestFilterForJS))
    // testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "???")
  )

lazy val smclGeneralJvmSettings =
  smclGeneralBuildInfoSettings ++ Seq(
    libraryDependencies ++= Seq(
      /**
       * Scala.js Stubs for Scala
       *
       * @see https://www.scala-js.org/
       * @see http://search.maven.org/#search|ga|1|scalajs-stubs
       */
      "org.scala-js" %% "scalajs-stubs" % "0.6.14" % "provided" withSources () withJavadoc ()
    ),

    buildInfoObject := buildInfoObjectNameJvm,
    buildInfoPackage := buildInfoPackageNameJvm,
    buildInfoKeys ++= Seq[BuildInfoKey](
      buildInfoKeyPlatform -> buildInfoPlatformIdJvm,
      buildInfoKeyIsJavaPlatform -> true,
      buildInfoKeyIsJavaScriptPlatform -> false
    ),

    testOptions in Test := Seq(Tests.Filter(unitTestFilterForJVM)),
    testOptions in ItgTest := Seq(Tests.Filter(integrationTestFilterForJVM)),
    testOptions in GUITest := Seq(Tests.Filter(guiTestFilterForJVM)),
    testOptions in SmokeTest := Seq(Tests.Filter(smokeTestFilterForJVM)),
    testOptions in LearningTest := Seq(Tests.Filter(learningTestFilterForJVM))
    // testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "???")
  )


//-------------------------------------------------------------------------------------------------
//
// PROJECT: SMCL BITMAP VIEWER
//
//-------------------------------------------------------------------------------------------------

lazy val smclBitmapViewer =
  CrossProject(prjSmclBitmapViewerJvmId, prjSmclBitmapViewerJsId, file(prjSmclBitmapViewerId), CrossType.Full)
      .configs(ItgTest, GUITest, SmokeTest, LearningTest)
      .settings(
        name := prjSmclBitmapViewerId,
        description := prjSmclBitmapViewerDescription,
        smclGeneralSettings,
        scalacOptions in (Compile, doc) := Seq("-doc-title", prjSmclBitmapViewerName),
        inConfig(ItgTest)(Defaults.testTasks),
        inConfig(GUITest)(Defaults.testTasks),
        inConfig(SmokeTest)(Defaults.testTasks),
        inConfig(LearningTest)(Defaults.testTasks)
      )
      .jvmConfigure{project =>
        project.enablePlugins(BuildInfoPlugin)
      }
      .jvmSettings(
        smclGeneralJvmSettings,
        onLoadMessage := prjSmclBitmapViewerName + " JVM/AWT Project Loaded",
        libraryDependencies ++= Seq(
          /**
           * RxScala
           *
           * @see http://reactivex.io/rxscala/
           * @see http://search.maven.org/#search|ga|1|rxscala
           */
          "io.reactivex" %% "rxscala" % "0.26.5" withSources () withJavadoc (),

          /**
           * Scala Swing
           *
           * @see http://www.scala-lang.org/
           * @see http://search.maven.org/#search|ga|1|scala-swing
           */
          "org.scala-lang.modules" %% "scala-swing" % "2.0.0" withSources () withJavadoc ()
        ),
      )
      .jsConfigure{project =>
        project.enablePlugins(BuildInfoPlugin)
      }
      .jsSettings(
        smclGeneralJsSettings,
        onLoadMessage := prjSmclBitmapViewerName + " JS/HTML5 Project Loaded",
        inConfig(ItgTest)(ScalaJSPluginInternal.scalaJSTestSettings),
        inConfig(GUITest)(ScalaJSPluginInternal.scalaJSTestSettings),
        inConfig(SmokeTest)(ScalaJSPluginInternal.scalaJSTestSettings),
        inConfig(LearningTest)(ScalaJSPluginInternal.scalaJSTestSettings),
      )
      .dependsOn(smclCore % confToConfSemiColonString)

lazy val smclBitmapViewerJVM = smclBitmapViewer.jvm
lazy val smclBitmapViewerJS = smclBitmapViewer.js


//-------------------------------------------------------------------------------------------------
//
// PROJECT: SMCL CORE LIBRARY
//
//-------------------------------------------------------------------------------------------------

lazy val smclCore =
  CrossProject(prjSmclCoreJvmId, prjSmclCoreJsId, file(prjSmclCoreId), CrossType.Full)
      .configs(ItgTest, GUITest, SmokeTest, LearningTest)
      .settings(
        name := prjSmclCoreId,
        description := prjSmclCoreDescription,
        smclGeneralSettings,
        scalacOptions in (Compile, doc) := Seq("-doc-title", prjSmclCoreName),
        inConfig(ItgTest)(Defaults.testTasks),
        inConfig(GUITest)(Defaults.testTasks),
        inConfig(SmokeTest)(Defaults.testTasks),
        inConfig(LearningTest)(Defaults.testTasks)
      )
      .jvmConfigure{project =>
        project.enablePlugins(BuildInfoPlugin)
      }
      .jvmSettings(
        smclGeneralJvmSettings,
        onLoadMessage := prjSmclCoreName + " JVM/AWT Project Loaded",
      )
      .jsConfigure{project =>
        project.enablePlugins(BuildInfoPlugin)
      }
      .jsSettings(
        smclGeneralJsSettings,
        onLoadMessage := prjSmclCoreName + " JS/HTML5 Project Loaded",
        inConfig(ItgTest)(ScalaJSPluginInternal.scalaJSTestSettings),
        inConfig(GUITest)(ScalaJSPluginInternal.scalaJSTestSettings),
        inConfig(SmokeTest)(ScalaJSPluginInternal.scalaJSTestSettings),
        inConfig(LearningTest)(ScalaJSPluginInternal.scalaJSTestSettings),
      )

lazy val smclCoreJVM = smclCore.jvm
lazy val smclCoreJS = smclCore.js


//-------------------------------------------------------------------------------------------------
//
// PROJECT: SMCL ROOT AGGREGATE
//
//-------------------------------------------------------------------------------------------------
lazy val smcl = project.in(file("."))
    .settings(
      onLoadMessage := projectFullName + " Root Project Loaded",

      // To keep SBT from publishing unnecessary empty artifacts
      publishArtifact in Compile := false,
      publish := {},
      publishLocal := {}
    )
    .aggregate(
      smclBitmapViewerJVM, smclBitmapViewerJS,
      smclCoreJVM, smclCoreJS)
    .dependsOn(
      smclBitmapViewerJVM, smclBitmapViewerJS,
      smclCoreJVM, smclCoreJS)
