name := "Scala Media Computation Library"

version := "0.1"

scalaVersion := "2.11.7"

crossScalaVersions := Seq("2.10.5")


//
// D E P E N D E N C I ES
//
// RxScala                      http://search.maven.org/#search|ga|1|rxscala
// ScalaCheck                   http://search.maven.org/#search|ga|1|scalacheck
// scala-reflect                http://search.maven.org/#search|ga|1|scala-reflect
// scala-swing                  http://search.maven.org/#search|ga|1|scala-swing
// ScalaTest                    http://search.maven.org/#search|ga|1|scalatest
// scala-xml                    http://search.maven.org/#search|ga|1|scala-xml
//

// @formatter:off
libraryDependencies ++= (scalaBinaryVersion.value match {
  case "2.10" => Seq(
    "io.reactivex"              % "rxscala_2.10"      % "0.25.0"                  withSources() withJavadoc(),
    "org.scala-lang.modules"    % "scala-swing_2.10"  % "2.0.0-M2"                withSources() withJavadoc(),
    // No separate scala-reflect
    "org.scalatest"             % "scalatest_2.10"    % "3.0.0-M7"  % "test"      withSources() withJavadoc(),
    "org.scalacheck"            % "scalacheck_2.10"   % "1.12.4"    % "test"      withSources() withJavadoc()
    // No separate scala-xml
  )
  case "2.11" => Seq(
    "io.reactivex"              % "rxscala_2.11"      % "0.25.0"                  withSources() withJavadoc(),
    "org.scala-lang.modules"    % "scala-swing_2.11"  % "2.0.0-M2"                withSources() withJavadoc(),
    "org.scala-lang"            % "scala-reflect"     % "2.11.7"                  withSources() withJavadoc(),
    "org.scalatest"             % "scalatest_2.11"    % "3.0.0-M7"  % "test"      withSources() withJavadoc(),
    "org.scalacheck"            % "scalacheck_2.11"   % "1.12.4"    % "test"      withSources() withJavadoc(),
    "org.scala-lang.modules"    % "scala-xml_2.11"    % "1.0.4"                   withSources() withJavadoc()
  )
})
// @formatter:on


initialCommands in console :=
    """import aalto.smcl._
      |import aalto.smcl.common._
      |import aalto.smcl.images._
      |import aalto.smcl.images.BitmapOps._
      |import aalto.smcl.images.immutable._
      |SMCL.welcomingMessage""".stripMargin


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
