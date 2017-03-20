name := "smcl-public-interfaces"

version := "1.0-SNAPSHOT"

organization := "aalto.smcl"

organizationName := "Aalto University"

organizationHomepage := Some(url("http://www.aalto.fi/"))

startYear := Some(2015)

description := "Public interfaces for querying data from objects produced by the Scala Media Computation Library."



scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.12.1")

retrieveManaged := true


scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked"
)

scalacOptions in (Compile, doc) ++= Seq(
  "-implicits",
  "-doc-root-content", baseDirectory.value + "/root-doc.txt",
  "-doc-title", "Scala Media Computation Library, Public Interfaces"
)
