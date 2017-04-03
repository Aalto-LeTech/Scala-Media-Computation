/**
 * SBT build script for Scala Media Computation Library (SMCL).
 */

import org.scalajs.sbtplugin.ScalaJSPluginInternal
import org.scalajs.sbtplugin.cross.CrossProject
import sbt.Keys._



lazy val projectIdJvmPostfix = "-jvm"
lazy val projectIdJsPostfix = "-js"
lazy val projectIdTestPostfix = "-tests"

lazy val smclName = "Scala Media Computation Library"

lazy val projectOrganizationId = "aalto.cs"
lazy val projectOrganizationName = "Aalto University, Department of Computer Science"
lazy val projectOrganizationUrl = "http://cs.aalto.fi/"
lazy val projectStartYear = 2015

lazy val projectJavaVersionSource = "1.8"
lazy val projectJavaVersionTarget = "1.8"

lazy val prjSmclBitmapViewerId = "smcl-bitmap-viewer"
lazy val prjSmclBitmapViewerJvmId = prjSmclBitmapViewerId + projectIdJvmPostfix
lazy val prjSmclBitmapViewerJsId = prjSmclBitmapViewerId + projectIdJsPostfix
lazy val prjSmclBitmapViewerName = smclName
lazy val prjSmclBitmapViewerVersion = "1.0.0-SNAPSHOT"
lazy val prjSmclBitmapViewerDescription = "Bitmap viewers for " + smclName + "."

lazy val prjSmclCoreId = "smcl-core"
lazy val prjSmclCoreJvmId = prjSmclCoreId + projectIdJvmPostfix
lazy val prjSmclCoreJsId = prjSmclCoreId + projectIdJsPostfix
lazy val prjSmclCoreName = smclName
lazy val prjSmclCoreVersion = "1.0.0-SNAPSHOT"
lazy val prjSmclCoreDescription = "A class library for bitmap processing using Scala."

lazy val prjSmclPiId = "smcl-public-interfaces"
lazy val prjSmclPiJvmId = prjSmclPiId + projectIdJvmPostfix
lazy val prjSmclPiJsId = prjSmclPiId + projectIdJsPostfix
lazy val prjSmclPiName = smclName + ", Public Interfaces"
lazy val prjSmclPiVersion = "1.0.0-SNAPSHOT"
lazy val prjSmclPiDescription = "Public interfaces for communicating with " + smclName + "."


lazy val smclGeneralSettings = Seq(
  organization := projectOrganizationId,
  organizationName := projectOrganizationName,
  organizationHomepage := Some(url(projectOrganizationUrl)),

  startYear := Some(projectStartYear),

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
      |import aalto.smcl.common._
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


lazy val smclBitmapViewer =
  CrossProject(prjSmclBitmapViewerJsId, prjSmclBitmapViewerJvmId, file(prjSmclBitmapViewerId), CrossType.Full)
  .configs(ItgTest)
  .settings(
    name := prjSmclBitmapViewerId,
    version := prjSmclBitmapViewerVersion,
    description := prjSmclBitmapViewerDescription,
    smclGeneralSettings,
    inConfig(ItgTest)(Defaults.testTasks),
    testOptions in Test := Seq(Tests.Filter(unitTestFilter)),
    testOptions in ItgTest := Seq(Tests.Filter(integrationTestFilter))
  )
  .jvmSettings(
    smclGeneralJvmSettings,
    libraryDependencies ++= Seq(
      ApplicationDependencies.RxScala,
      ApplicationDependencies.ScalaSwing
    )
  )
  .jsSettings(
    smclGeneralJsSettings,
    inConfig(ItgTest)(ScalaJSPluginInternal.scalaJSTestSettings)
  )
  .dependsOn(smclCore, smclPublicInterfaces)

lazy val smclBitmapViewerJVM = smclBitmapViewer.jvm
lazy val smclBitmapViewerJS = smclBitmapViewer.js


lazy val smclCore =
  CrossProject(prjSmclCoreJsId, prjSmclCoreJvmId, file(prjSmclCoreId), CrossType.Full)
  .configs(ItgTest)
  .settings(
    name := prjSmclCoreId,
    version := prjSmclCoreVersion,
    description := prjSmclCoreDescription,
    smclGeneralSettings,
    inConfig(ItgTest)(Defaults.testTasks),
    testOptions in Test := Seq(Tests.Filter(unitTestFilter)),
    testOptions in ItgTest := Seq(Tests.Filter(integrationTestFilter))
  )
  .jvmSettings(
    smclGeneralJvmSettings
  )
  .jsSettings(
    smclGeneralJsSettings,
    inConfig(ItgTest)(ScalaJSPluginInternal.scalaJSTestSettings)
  )
  .dependsOn(smclPublicInterfaces)

lazy val smclCoreJVM = smclCore.jvm
lazy val smclCoreJS = smclCore.js


lazy val smclPublicInterfaces =
  CrossProject(prjSmclPiJsId, prjSmclPiJvmId, file(prjSmclPiId), CrossType.Full)
  .configs(ItgTest)
  .settings(
    name := prjSmclPiId,
    version := prjSmclPiVersion,
    description := prjSmclPiDescription,
    smclGeneralSettings,
    inConfig(ItgTest)(Defaults.testTasks),
    testOptions in Test := Seq(Tests.Filter(unitTestFilter)),
    testOptions in ItgTest := Seq(Tests.Filter(integrationTestFilter))
  )
  .jvmSettings(
    smclGeneralJvmSettings
  )
  .jsSettings(
    smclGeneralJsSettings,
    inConfig(ItgTest)(ScalaJSPluginInternal.scalaJSTestSettings)
  )

lazy val smclPublicInterfacesJVM = smclPublicInterfaces.jvm
lazy val smclPublicInterfacesJS = smclPublicInterfaces.js


lazy val smcl = project.in(file("."))
  .settings(smclGeneralSettings)
  .aggregate(
    smclBitmapViewerJVM, smclBitmapViewerJS,
    smclCoreJVM, smclCoreJS,
    smclPublicInterfacesJVM, smclPublicInterfacesJS)
  .dependsOn(
    smclBitmapViewerJVM, smclBitmapViewerJS,
    smclCoreJS, smclCoreJVM,
    smclPublicInterfacesJS, smclPublicInterfacesJVM)


addCommandAlias("cp", "; clean ; package")

addCommandAlias("rcp", "; reload ; clean ; package")

addCommandAlias("cpt", "; clean ; package ; test")

addCommandAlias("rcpt", "; reload ; clean ; package ; test")

