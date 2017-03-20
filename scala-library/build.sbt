name := "smcl-scala-library"

version := "0.11-SNAPSHOT"
isSnapshot := true

organization := "aalto.smcl"

organizationName := "Aalto University"

organizationHomepage := Some(url("http://www.aalto.fi/"))

startYear := Some(2015)

description :=
    """Scala Media Computation Library is a media computation
      |library for Scala-based introductory programming teaching.""".stripMargin



scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.12.1")

retrieveManaged := true


//
// D E P E N D E N C I ES
//
// Graph for Scala                  http://search.maven.org/#search|ga|1|graph-core
// Graph for Scala Dot Module       http://search.maven.org/#search|ga|1|graph-dot
// RxScala                          http://search.maven.org/#search|ga|1|rxscala
// scala-compiler                   http://search.maven.org/#search|ga|1|scala-compiler
// scala-reflect                    http://search.maven.org/#search|ga|1|scala-reflect
// scala-swing                      http://search.maven.org/#search|ga|1|scala-swing
// scala-xml                        http://search.maven.org/#search|ga|1|scala-xml
// ScalaCheck                       http://search.maven.org/#search|ga|1|scalacheck
// ScalaTest                        http://search.maven.org/#search|ga|1|scalatest
// SMCL Public Interfaces


// @formatter:off
libraryDependencies ++= (scalaBinaryVersion.value match {
  case "2.11" => Seq(
//    "com.assembla.scala-incubator"  % "graph-core_2.11"             % "1.9.4"                   withSources() withJavadoc(),
//    "com.assembla.scala-incubator"  % "graph-dot_2.11"              % "1.10.0"                  withSources() withJavadoc(),
    "io.reactivex"                  % "rxscala_2.11"                % "0.25.0"                  withSources() withJavadoc(),
    "org.scala-lang"                % "scala-compiler"              % "2.11.8"                  withSources() withJavadoc(),
    "org.scala-lang"                % "scala-reflect"               % "2.11.8"                  withSources() withJavadoc(),
    "org.scala-lang.modules"        % "scala-swing_2.11"            % "2.0.0-M2"                withSources() withJavadoc(),
    "org.scala-lang.modules"        % "scala-xml_2.11"              % "1.0.4"                   withSources() withJavadoc(),
    "org.scalatest"                 % "scalatest_2.11"              % "3.0.0-M7"  % "test"      withSources() withJavadoc(),
    "org.scalacheck"                % "scalacheck_2.11"             % "1.12.4"    % "test"      withSources() withJavadoc(),
    "aalto.smcl"                    % "smcl-public-interfaces_2.11" % "1.0-SNAPSHOT"            withSources() withJavadoc()
  )
  case "2.12" => Seq(
//    "org.scala-graph"               % "graph-core_2.12"             % "1.11.4"                  withSources() withJavadoc(),
//    "org.scala-graph"               % "graph-dot_2.12"              % "1.11.0"                  withSources() withJavadoc(),
    "io.reactivex"                  % "rxscala_2.12"                % "0.26.5"                  withSources() withJavadoc(),
    "org.scala-lang"                % "scala-compiler"              % "2.12.1"                  withSources() withJavadoc(),
    "org.scala-lang"                % "scala-reflect"               % "2.12.1"                  withSources() withJavadoc(),
    "org.scala-lang.modules"        % "scala-swing_2.12"            % "2.0.0"                   withSources() withJavadoc(),
    "org.scala-lang.modules"        % "scala-xml_2.12"              % "1.0.6"                   withSources() withJavadoc(),
    "org.scalatest"                 % "scalatest_2.12"              % "3.0.1"  % "test"         withSources() withJavadoc(),
    "org.scalacheck"                % "scalacheck_2.12"             % "1.13.5" % "test"         withSources() withJavadoc(),
    "aalto.smcl"                    % "smcl-public-interfaces_2.12" % "1.0-SNAPSHOT"            withSources() withJavadoc()
  )
})
// @formatter:on

initialCommands in console :=
    """import aalto.smcl._
      |import aalto.smcl.infrastructure._
      |import aalto.smcl.common._
      |import aalto.smcl.colors._
      |import aalto.smcl.bitmaps._""".stripMargin


scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked"
)

scalacOptions in (Compile, doc) ++= Seq(
  "-implicits",
  "-doc-root-content", baseDirectory.value + "/root-doc.txt",
  "-doc-title", "Scala Media Computation Library"
)
