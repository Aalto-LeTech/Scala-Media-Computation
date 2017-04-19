/**
 *
 * SBT build script for Scala Media Computation Library (SMCL).
 *
 */

import org.scalajs.sbtplugin.ScalaJSPluginInternal
import org.scalajs.sbtplugin.cross.CrossProject
import sbt.Keys.{scalacOptions, testOptions, _}
import sbt.inConfig




enablePlugins(ScalaJSPlugin)


//-------------------------------------------------------------------------------------------------
//
// GENERAL CONSTANTS
//
//-------------------------------------------------------------------------------------------------

lazy val projectIdJvmPostfix = "-jvm"
lazy val projectIdJsPostfix = "-js"
lazy val projectIdTestPostfix = "-tests"

lazy val smclName = "Scala Media Computation Library"
lazy val smclHomepageUrl = "http://github.com/Aalto-LeTech/Scala-Media-Computation"

lazy val projectOrganizationId = "aalto.cs"
lazy val projectOrganizationName = "Aalto University, Department of Computer Science"
lazy val projectOrganizationUrl = "http://cs.aalto.fi/"
lazy val projectStartYear = 2015
lazy val projectDevelopers = List(
  Developer(id="lukkark1", name="Aleksi Lukkarinen", email="aleksi.lukkarinen@aalto.fi", url=null)
)

lazy val projectJavaVersionSource = "1.8"
lazy val projectJavaVersionTarget = "1.8"

lazy val prjSmclBitmapViewerId = "smcl-bitmap-viewer"
lazy val prjSmclBitmapViewerJvmId = prjSmclBitmapViewerId + projectIdJvmPostfix
lazy val prjSmclBitmapViewerJsId = prjSmclBitmapViewerId + projectIdJsPostfix
lazy val prjSmclBitmapViewerName = smclName + " Bitmap Viewer"
lazy val prjSmclBitmapViewerVersion = "1.0.0-SNAPSHOT"
lazy val prjSmclBitmapViewerDescription = "Bitmap viewers for " + smclName + "."

lazy val prjSmclCoreId = "smcl-core"
lazy val prjSmclCoreJvmId = prjSmclCoreId + projectIdJvmPostfix
lazy val prjSmclCoreJsId = prjSmclCoreId + projectIdJsPostfix
lazy val prjSmclCoreName = smclName + " Core Library"
lazy val prjSmclCoreVersion = "1.0.0-SNAPSHOT"
lazy val prjSmclCoreDescription = "A class library for bitmap processing using Scala."




//-------------------------------------------------------------------------------------------------
//
// GENERAL SETTINGS
//
//-------------------------------------------------------------------------------------------------

lazy val SmokeTest = config("smoke") extend Test describedAs "For running smoke tests"

def smokeTestFilterForJVM(name: String): Boolean =
  (name endsWith "SmokeTests") || (name endsWith "SmokeTestsForJVM") ||
      (name endsWith "SmokeTestsuite") || (name endsWith "SmokeTestsuiteForJVM")

def smokeTestFilterForJS(name: String): Boolean =
  (name endsWith "SmokeTestsForJS") || (name endsWith "SmokeTestsuiteForJS")


lazy val ItgTest = config("integration") extend Test describedAs "For running integration tests"

def integrationTestFilterForJVM(name: String): Boolean =
  (name endsWith "ItgTests") || (name endsWith "ItgTestsForJVM") ||
      (name endsWith "ItgTestsuite") || (name endsWith "ItgTestsuiteForJVM")

def integrationTestFilterForJS(name: String): Boolean =
  (name endsWith "ItgTestsForJS") || (name endsWith "ItgTestsuiteForJS")


lazy val GUITest = config("gui") extend Test describedAs "For running GUI-based tests"

def guiTestFilterForJVM(name: String): Boolean =
  (name endsWith "GUITests") || (name endsWith "GUITestsForJVM") ||
      (name endsWith "GUITestsuite") || (name endsWith "GUITestsuiteForJVM")

def guiTestFilterForJS(name: String): Boolean =
  (name endsWith "GUITestsForJS") || (name endsWith "GUITestsuiteForJS")


lazy val LearningTest = config("learning") extend Test describedAs "For running learning tests"

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


def isSnapshotVersion(version: String): Boolean = version endsWith "-SNAPSHOT"


lazy val smclGeneralSettings = Seq(
  organization := projectOrganizationId,
  organizationName := projectOrganizationName,
  organizationHomepage := Some(url(projectOrganizationUrl)),

  startYear := Some(projectStartYear),
  homepage := Some(url(smclHomepageUrl)),
  developers ++= projectDevelopers,

  logLevel := Level.Info,

  scalaVersion in ThisBuild := ApplicationDependencies.ScalaVersion,

  javacOptions ++= Seq(
    "-source", projectJavaVersionSource,
    "-target", projectJavaVersionTarget
  ),

  parallelExecution := true,

  scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),

  scalacOptions in (Compile, doc) := Seq(
    "-implicits",
    "-doc-root-content", baseDirectory.value + "/root-doc.txt"
  ),

  libraryDependencies ++= Seq(
    "org.scalatest" %%% "scalatest" % "3.0.1" % "test,integration,gui,smoke,learning" withSources() withJavadoc(),
    "org.scalacheck" %%% "scalacheck" % "1.13.4" % "test,integration,gui,smoke,learning" withSources() withJavadoc()
  ),

  initialCommands in console :=
    """import aalto.smcl._
      |import aalto.smcl.infrastructure._
      |import aalto.smcl.geometry._
      |import aalto.smcl.fonts._
      |import aalto.smcl.colors._
      |import aalto.smcl.bitmaps._
      |import aalto.smcl.viewers._
      |
      |aalto.smcl.infrastructure.jvmawt.Initializer()
      |aalto.smcl.viewers.bitmaps.jvmawt.Initializer()
      |""".stripMargin
)

lazy val smclGeneralJsSettings = Seq(
  libraryDependencies ++= Seq(
    "org.scala-js" %%% "scalajs-dom" % "0.9.1" withSources() withJavadoc()
  ),

  jsDependencies += RuntimeDOM,

  testOptions in Test := Seq(Tests.Filter(unitTestFilterForJS)),
  testOptions in ItgTest := Seq(Tests.Filter(integrationTestFilterForJS)),
  testOptions in GUITest := Seq(Tests.Filter(guiTestFilterForJS)),
  testOptions in SmokeTest := Seq(Tests.Filter(smokeTestFilterForJS)),
  testOptions in LearningTest := Seq(Tests.Filter(learningTestFilterForJS))
  // testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "???")
)

lazy val smclGeneralJvmSettings = Seq(
  libraryDependencies ++= Seq(
    ApplicationDependencies.ScalaJsStubs % "provided"
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
    version := prjSmclBitmapViewerVersion,
    isSnapshot := isSnapshotVersion(prjSmclBitmapViewerVersion),
    description := prjSmclBitmapViewerDescription,
    smclGeneralSettings,
    scalacOptions in (Compile, doc) := Seq("-doc-title", prjSmclBitmapViewerName),
    inConfig(ItgTest)(Defaults.testTasks),
    inConfig(GUITest)(Defaults.testTasks),
    inConfig(SmokeTest)(Defaults.testTasks),
    inConfig(LearningTest)(Defaults.testTasks)
  )
  .jvmSettings(
    smclGeneralJvmSettings,
    onLoadMessage := prjSmclBitmapViewerName + " JVM Project Loaded",
    libraryDependencies ++= Seq(
      ApplicationDependencies.RxScala,
      ApplicationDependencies.ScalaSwing
    )
  )
  .jsSettings(
    smclGeneralJsSettings,
    onLoadMessage := prjSmclBitmapViewerName + " JS Project Loaded",
    inConfig(ItgTest)(ScalaJSPluginInternal.scalaJSTestSettings),
    inConfig(GUITest)(ScalaJSPluginInternal.scalaJSTestSettings),
    inConfig(SmokeTest)(ScalaJSPluginInternal.scalaJSTestSettings),
    inConfig(LearningTest)(ScalaJSPluginInternal.scalaJSTestSettings)
  )
  .dependsOn(smclCore)

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
    version := prjSmclCoreVersion,
    isSnapshot := isSnapshotVersion(prjSmclCoreVersion),
    description := prjSmclCoreDescription,
    smclGeneralSettings,
    scalacOptions in (Compile, doc) := Seq("-doc-title", prjSmclCoreName),
    inConfig(ItgTest)(Defaults.testTasks),
    inConfig(GUITest)(Defaults.testTasks),
    inConfig(SmokeTest)(Defaults.testTasks),
    inConfig(LearningTest)(Defaults.testTasks)
  )
  .jvmSettings(
    smclGeneralJvmSettings,
    onLoadMessage := prjSmclCoreName + " JVM Project Loaded"
  )
  .jsSettings(
    smclGeneralJsSettings,
    onLoadMessage := prjSmclCoreName + " JS Project Loaded",
    inConfig(ItgTest)(ScalaJSPluginInternal.scalaJSTestSettings),
    inConfig(GUITest)(ScalaJSPluginInternal.scalaJSTestSettings),
    inConfig(SmokeTest)(ScalaJSPluginInternal.scalaJSTestSettings),
    inConfig(LearningTest)(ScalaJSPluginInternal.scalaJSTestSettings)
  )

lazy val smclCoreJVM = smclCore.jvm
lazy val smclCoreJS = smclCore.js




//-------------------------------------------------------------------------------------------------
//
// PROJECT: SMCL ROOT AGGREGATE
//
//-------------------------------------------------------------------------------------------------

lazy val smcl = project.in(file("."))
  .configs(ItgTest, GUITest, SmokeTest, LearningTest)
  .settings(
    smclGeneralSettings,
    onLoadMessage := smclName + " Root Project Loaded"
  )
  .aggregate(
    smclBitmapViewerJVM, smclBitmapViewerJS,
    smclCoreJVM, smclCoreJS)
  .dependsOn(
    smclBitmapViewerJVM, smclBitmapViewerJS,
    smclCoreJS, smclCoreJVM)




//-------------------------------------------------------------------------------------------------
//
// COMMAND ALIASES
//
//-------------------------------------------------------------------------------------------------

addCommandAlias("cp", "; clean ; package")

addCommandAlias("rcp", "; reload ; clean ; package")

addCommandAlias("cpt", "; clean ; package ; test")

addCommandAlias("rcpt", "; reload ; clean ; package ; test")




//-------------------------------------------------------------------------------------------------
//
// MISCELLANEOUS TASK DEFINITIONS
//
//-------------------------------------------------------------------------------------------------

