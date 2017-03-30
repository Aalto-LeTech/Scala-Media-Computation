/**
 * Sbt build script for Scala Media Computation Library (SMCL).
 */

import sbt.Keys._



lazy val projectIdTestPostfix = "-tests"

lazy val smclName = "Scala Media Computation Library"

lazy val projectOrganizationId = "aalto.cs"
lazy val projectOrganizationName = "Aalto University, Department of Computer Science"
lazy val projectOrganizationUrl = "http://cs.aalto.fi/"
lazy val projectStartYear = 2015

lazy val projectJavaVersionSource = "1.8"
lazy val projectJavaVersionTarget = "1.8"

lazy val prjSmclCoreId = "smcl-core"
lazy val prjSmclCoreName = smclName
lazy val prjSmclCoreVersion = "1.0.0-SNAPSHOT"
lazy val prjSmclCoreDescription = "A class library for bitmap processing using Scala."

lazy val prjSmclCoreTestsId = prjSmclCoreId + projectIdTestPostfix

lazy val prjSmclPiId = "smcl-public-interfaces"
lazy val prjSmclPiName = smclName + ", Public Interfaces"
lazy val prjSmclPiVersion = "1.0.0-SNAPSHOT"
lazy val prjSmclPiDescription = "Public interfaces for communicating with " + smclName + "."

lazy val prjSmclPiTestsId = prjSmclPiId + projectIdTestPostfix


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
    //ApplicationDependencies.ScalaReflection
    //ApplicationDependencies.GraphForScalaCore,
    //ApplicationDependencies.GraphForScalaDot,
    //ApplicationDependencies.RxScala,
    //ApplicationDependencies.ScalaCompiler,
    //ApplicationDependencies.ScalaXml
  ),

  initialCommands in console :=
      """import aalto.smcl._
        |import aalto.smcl.infrastructure._
        |import aalto.smcl.common._
        |import aalto.smcl.colors._
        |import aalto.smcl.bitmaps._
        |aalto.smcl.infrastructure.awt.Initializer()""".stripMargin
)

lazy val smclGeneralJsDependencySettings = Seq(
  libraryDependencies ++= Seq(

  )
)

lazy val smclGeneralJvmDependencySettings = Seq(
  libraryDependencies ++= Seq(
    ApplicationDependencies.ScalaJsStubs,
    ApplicationDependencies.ScalaSwing
  )
)

lazy val smclTestingJvmDependencySettings = Seq(
  libraryDependencies ++= Seq(
    ApplicationDependencies.ScalaTest
  )
)

lazy val smclCore = crossProject
  .crossType(CrossType.Full)
  .in(file(prjSmclCoreId))
  .settings(smclGeneralSettings: _*)
  .settings(
    name := prjSmclCoreId,
    version := prjSmclCoreVersion,
    description := prjSmclCoreDescription
  )
  .jvmSettings(smclGeneralJvmDependencySettings: _*)
  .jsSettings(smclGeneralJsDependencySettings: _*)
  .dependsOn(smclPublicInterfaces)

lazy val smclCoreJVM = smclCore.jvm
lazy val smclCoreJS = smclCore.js


lazy val smclCoreTests = crossProject
  .crossType(CrossType.Full)
  .in(file(prjSmclCoreTestsId))
  .settings(smclGeneralSettings: _*)
  .settings(
    name := prjSmclCoreTestsId,
    version := prjSmclCoreVersion,
    description := prjSmclCoreDescription
  )
  .jvmSettings(smclGeneralJvmDependencySettings ++ smclTestingJvmDependencySettings: _*)
  .jsSettings(smclGeneralJsDependencySettings: _*)
  .dependsOn(smclCore)

lazy val smclCoreTestsJVM = smclCoreTests.jvm
lazy val smclCoreTestsJS = smclCoreTests.js


lazy val smclPublicInterfaces = crossProject
  .crossType(CrossType.Full)
  .in(file(prjSmclPiId))
  .settings(smclGeneralSettings: _*)
  .settings(
    name := prjSmclPiId,
    version := prjSmclPiVersion,
    description := prjSmclPiDescription
  )
  .jvmSettings(smclGeneralJvmDependencySettings: _*)
  .jsSettings(smclGeneralJsDependencySettings: _*)

lazy val smclPublicInterfacesJVM = smclPublicInterfaces.jvm
lazy val smclPublicInterfacesJS = smclPublicInterfaces.js


lazy val smclPublicInterfacesTests = crossProject
  .crossType(CrossType.Full)
  .in(file(prjSmclPiTestsId))
  .settings(smclGeneralSettings: _*)
  .settings(
    name := prjSmclPiTestsId,
    version := prjSmclPiVersion,
    description := prjSmclPiDescription
  )
  .jvmSettings(smclGeneralJvmDependencySettings ++ smclTestingJvmDependencySettings: _*)
  .jsSettings(smclGeneralJsDependencySettings: _*)
  .dependsOn(smclPublicInterfaces)

lazy val smclPublicInterfacesTestsJVM = smclPublicInterfacesTests.jvm
lazy val smclPublicInterfacesTestsJS = smclPublicInterfacesTests.js


lazy val smcl = project.in(file("."))
    .settings(smclGeneralSettings: _*)
    .aggregate(
      smclCoreJS, smclCoreJVM,
      smclCoreTestsJS, smclCoreTestsJS,
      smclPublicInterfacesJS, smclPublicInterfacesJVM,
      smclPublicInterfacesTestsJVM, smclPublicInterfacesTestsJS)
    .dependsOn(
      smclCoreJS, smclCoreJVM,
      smclCoreTestsJS, smclCoreTestsJS,
      smclPublicInterfacesJS, smclPublicInterfacesJVM,
      smclPublicInterfacesTestsJVM, smclPublicInterfacesTestsJS)


addCommandAlias("cp", "; clean ; package")

addCommandAlias("rcp", "; reload ; clean ; package")

addCommandAlias("cpt", "; clean ; package ; test")

addCommandAlias("rcpt", "; reload ; clean ; package ; test")
