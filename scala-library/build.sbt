name := "Scala Media Computation Library"

version := "0.1"

scalaVersion := "2.11.7"

crossScalaVersions := Seq("2.10.5")

libraryDependencies += (scalaBinaryVersion.value match {
    case "2.10" => "org.scalatest" % "scalatest_2.10" % "2.2.4" % "test" 
    case "2.11" => "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test" withSources() withJavadoc()
})

scalacOptions in (Compile, doc) ++=
    Seq("-implicits",
        "-doc-title", "Scala Media Computation Library")
