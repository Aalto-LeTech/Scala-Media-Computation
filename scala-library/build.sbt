name := "Scala Media Computation Library"

version := "0.1"

scalaVersion := "2.11.7"

crossScalaVersions := Seq("2.10.5")



// Please see: http://search.maven.org/#search|ga|1|scala-swing
libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.11" % "2.0.0-M2" withSources() withJavadoc()

// Please see: http://search.maven.org/#search|ga|1|scala-reflect
libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.7" withSources() withJavadoc()

// Please see: http://search.maven.org/#search|ga|1|rxscala
libraryDependencies += "io.reactivex" %% "rxscala" % "0.25.0" withSources() withJavadoc()

libraryDependencies += (scalaBinaryVersion.value match {
    case "2.10" => "org.scalatest" % "scalatest_2.10" % "2.2.4" % "test"
    case "2.11" => "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test" withSources() withJavadoc()
})

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.12.4" % "test"




scalacOptions in (Compile, doc) ++=
    Seq("-implicits",
      "-doc-root-content", baseDirectory.value + "/root-doc.txt",
        "-doc-title", "Scala Media Computation Library")
