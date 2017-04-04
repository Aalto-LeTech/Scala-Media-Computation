/**
 *
 * SBT build script for Scala Media Computation Library (SMCL).
 *
 */

//
// NOTE AL 3.4.2017: For some strange reason, the onLoadMessage strings get switched between
// the cross-compiled JVM/JS project pairs; consequently, they are defined backwards.
//

import org.scalajs.sbtplugin.ScalaJSPluginInternal
import org.scalajs.sbtplugin.cross.CrossProject
import sbt.Keys._




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

lazy val prjSmclPiId = "smcl-public-interfaces"
lazy val prjSmclPiJvmId = prjSmclPiId + projectIdJvmPostfix
lazy val prjSmclPiJsId = prjSmclPiId + projectIdJsPostfix
lazy val prjSmclPiName = smclName + " Public Interfaces"
lazy val prjSmclPiVersion = "1.0.0-SNAPSHOT"
lazy val prjSmclPiDescription = "Public interfaces for communicating with " + smclName + "."




//-------------------------------------------------------------------------------------------------
//
// GENERAL SETTINGS
//
//-------------------------------------------------------------------------------------------------

lazy val smclGeneralSettings = Seq(
  organization := projectOrganizationId,
  organizationName := projectOrganizationName,
  organizationHomepage := Some(url(projectOrganizationUrl)),

  startYear := Some(projectStartYear),
  homepage := Some(url(smclHomepageUrl)),
  developers ++= projectDevelopers,

  scalaVersion in ThisBuild := ApplicationDependencies.ScalaVersion,

  javacOptions ++= Seq(
    "-source", projectJavaVersionSource,
    "-target", projectJavaVersionTarget
  ),

  scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),

  scalacOptions in (Compile, doc) := Seq(
    "-implicits",
    "-doc-root-content", baseDirectory.value + "/root-doc.txt",
    "-doc-title", prjSmclPiName
  ),

  libraryDependencies ++= Seq(
    ApplicationDependencies.ScalaCheck
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

  ),

  libraryDependencies in Test ++= Seq(
    ApplicationDependencies.ScalaTest,
    ApplicationDependencies.ScalaCheck
  )
)

lazy val smclGeneralJvmSettings = Seq(
  libraryDependencies ++= Seq(
    ApplicationDependencies.ScalaJsStubs
  ),

  libraryDependencies in Test ++= Seq(
    ApplicationDependencies.ScalaTest,
    ApplicationDependencies.ScalaCheck
  )
)

lazy val ItgTest = config("it") extend Test

def integrationTestFilter(name: String): Boolean = name endsWith "ItgTest"
def unitTestFilter(name: String): Boolean = (name endsWith "Test") && !integrationTestFilter(name)

def isSnapshotVersion(version: String): Boolean = version endsWith "-SNAPSHOT"




//-------------------------------------------------------------------------------------------------
//
// PROJECT: SMCL BITMAP VIEWER
//
//-------------------------------------------------------------------------------------------------

lazy val smclBitmapViewer =
  CrossProject(prjSmclBitmapViewerJsId, prjSmclBitmapViewerJvmId, file(prjSmclBitmapViewerId), CrossType.Full)
  .configs(ItgTest)
  .settings(
    name := prjSmclBitmapViewerId,
    version := prjSmclBitmapViewerVersion,
    isSnapshot := isSnapshotVersion(prjSmclBitmapViewerVersion),
    description := prjSmclBitmapViewerDescription,
    smclGeneralSettings,
    inConfig(ItgTest)(Defaults.testTasks),
    testOptions in Test := Seq(Tests.Filter(unitTestFilter)),
    testOptions in ItgTest := Seq(Tests.Filter(integrationTestFilter))
  )
  .jvmSettings(
    smclGeneralJvmSettings,
    onLoadMessage := prjSmclBitmapViewerName + " JS Project Loaded",    // see the note at the top
    libraryDependencies ++= Seq(
      ApplicationDependencies.RxScala,
      ApplicationDependencies.ScalaSwing
    )
  )
  .jsSettings(
    smclGeneralJsSettings,
    onLoadMessage := prjSmclBitmapViewerName + " JVM Project Loaded",   // see the note at the top
    inConfig(ItgTest)(ScalaJSPluginInternal.scalaJSTestSettings)
  )
  .dependsOn(smclCore, smclPublicInterfaces)

lazy val smclBitmapViewerJVM = smclBitmapViewer.jvm
lazy val smclBitmapViewerJS = smclBitmapViewer.js




//-------------------------------------------------------------------------------------------------
//
// PROJECT: SMCL CORE LIBRARY
//
//-------------------------------------------------------------------------------------------------

lazy val smclCore =
  CrossProject(prjSmclCoreJsId, prjSmclCoreJvmId, file(prjSmclCoreId), CrossType.Full)
  .configs(ItgTest)
  .settings(
    name := prjSmclCoreId,
    version := prjSmclCoreVersion,
    isSnapshot := isSnapshotVersion(prjSmclCoreVersion),
    description := prjSmclCoreDescription,
    smclGeneralSettings,
    inConfig(ItgTest)(Defaults.testTasks),
    testOptions in Test := Seq(Tests.Filter(unitTestFilter)),
    testOptions in ItgTest := Seq(Tests.Filter(integrationTestFilter))
  )
  .jvmSettings(
    smclGeneralJvmSettings,
    onLoadMessage := prjSmclCoreName + " JS Project Loaded"             // see the note at the top
  )
  .jsSettings(
    smclGeneralJsSettings,
    onLoadMessage := prjSmclCoreName + " JVM Project Loaded",           // see the note at the top
    inConfig(ItgTest)(ScalaJSPluginInternal.scalaJSTestSettings)
  )
  .dependsOn(smclPublicInterfaces)

lazy val smclCoreJVM = smclCore.jvm
lazy val smclCoreJS = smclCore.js




//-------------------------------------------------------------------------------------------------
//
// PROJECT: SMCL PUBLIC INTERFACES
//
//-------------------------------------------------------------------------------------------------

lazy val smclPublicInterfaces =
  CrossProject(prjSmclPiJsId, prjSmclPiJvmId, file(prjSmclPiId), CrossType.Full)
  .configs(ItgTest)
  .settings(
    name := prjSmclPiId,
    version := prjSmclPiVersion,
    isSnapshot := isSnapshotVersion(prjSmclPiVersion),
    description := prjSmclPiDescription,
    smclGeneralSettings,
    inConfig(ItgTest)(Defaults.testTasks),
    testOptions in Test := Seq(Tests.Filter(unitTestFilter)),
    testOptions in ItgTest := Seq(Tests.Filter(integrationTestFilter))
  )
  .jvmSettings(
    smclGeneralJvmSettings,
    onLoadMessage := prjSmclPiName + " JS Project Loaded"               // see the note at the top
  )
  .jsSettings(
    smclGeneralJsSettings,
    onLoadMessage := prjSmclPiName + " JVM Project Loaded",             // see the note at the top
    inConfig(ItgTest)(ScalaJSPluginInternal.scalaJSTestSettings)
  )

lazy val smclPublicInterfacesJVM = smclPublicInterfaces.jvm
lazy val smclPublicInterfacesJS = smclPublicInterfaces.js




//-------------------------------------------------------------------------------------------------
//
// PROJECT: SMCL ROOT AGGREGATE PROJECT
//
//-------------------------------------------------------------------------------------------------

lazy val smcl = project.in(file("."))
  .settings(
    smclGeneralSettings,
    onLoadMessage := smclName + " Root Project Loaded"
  )
  .aggregate(
    smclBitmapViewerJVM, smclBitmapViewerJS,
    smclCoreJVM, smclCoreJS,
    smclPublicInterfacesJVM, smclPublicInterfacesJS)
  .dependsOn(
    smclBitmapViewerJVM, smclBitmapViewerJS,
    smclCoreJS, smclCoreJVM,
    smclPublicInterfacesJS, smclPublicInterfacesJVM)
