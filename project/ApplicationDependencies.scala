/**
 * Definitions of application enrencies for Scala Media Computation Library (SMCL).
 */


import sbt._



object ApplicationDependencies {

  /** Scala version. */
  lazy val ScalaVersion = "2.12.1"


  /**
   * Scala.js Stubs for Scala
   *
   * @see https://www.scala-js.org/
   * @see http://search.maven.org/#search|ga|1|scalajs-stubs
   */
  lazy val ScalaJsStubs: ModuleID = "org.scala-js" %% "scalajs-stubs" % "0.6.14" withSources() withJavadoc()


  /**
   * Scala.js DOM
   *
   * @see https://www.scala-js.org/
   * @see http://search.maven.org/#search|ga|1|scalajs-dom
   */
  //lazy val ScalaJsDOM: ModuleID = new CrossGroupArtifactID("org.scala-js", "scalajs-dom", ScalaJSCrossVersion.binary).%("0.9.1") withSources() withJavadoc()


  /**
   * scalatest
   *
   * @see http://www.scalatest.org
   * @see http://search.maven.org/#search|ga|1|scalatest
   */
  lazy val ScalaTest: ModuleID = "org.scalatest" %% "scalatest" % "3.0.1" withSources() withJavadoc()


  /**
   * Scalactic
   *
   * @see http://www.scalactic.org/
   * @see http://search.maven.org/#search|ga|1|scalactic
   */
  //lazy val Scalactic: ModuleID = "org.scalactic" %% "scalactic" % "3.0.1" withSources() withJavadoc()


  /**
   * ScalaCheck
   *
   * @see https://www.scalacheck.org/
   * @see http://search.maven.org/#search|ga|1|scalacheck
   */
  lazy val ScalaCheck: ModuleID = "org.scalacheck" %% "scalacheck" % "1.13.4" withSources() withJavadoc()


  /**
   * Graph for Scala: Core
   *
   * @see http://www.scala-graph.org/
   * @see http://search.maven.org/#search|ga|1|graph-core
   */
  lazy val GraphForScalaCore: ModuleID = "org.scala-graph"  %% "graph-core" % "1.11.4" withSources() withJavadoc()


  /**
   * Graph for Scala: Dot
   *
   * @see http://www.scala-graph.org/
   * @see http://search.maven.org/#search|ga|1|graph-dot
   */
  lazy val GraphForScalaDot: ModuleID = "org.scala-graph" %% "graph-dot" % "1.11.0" withSources() withJavadoc()


  /**
   * RxScala
   *
   * @see http://reactivex.io/rxscala/
   * @see http://search.maven.org/#search|ga|1|rxscala
   */
  lazy val RxScala: ModuleID = "io.reactivex" %% "rxscala" % "0.26.5" withSources() withJavadoc()


  /**
   * Scala Compiler
   *
   * @see http://www.scala-lang.org/
   * @see http://search.maven.org/#search|ga|1|scala-compiler
   */
  lazy val ScalaCompiler: ModuleID = "org.scala-lang" % "scala-compiler" % ScalaVersion withSources() withJavadoc()


  /**
   * Scala Reflection
   *
   * @see http://www.scala-lang.org/
   * @see http://search.maven.org/#search|ga|1|scala-reflect
   */
  lazy val ScalaReflection: ModuleID = "org.scala-lang" % "scala-reflect" % ScalaVersion withSources() withJavadoc()


  /**
   * Scala Swing
   *
   * @see http://www.scala-lang.org/
   * @see http://search.maven.org/#search|ga|1|scala-swing
   */
  lazy val ScalaSwing: ModuleID = "org.scala-lang.modules" %% "scala-swing" % "2.0.0" withSources() withJavadoc()


  /**
   * Scala XML
   *
   * @see http://www.scala-lang.org/
   * @see http://search.maven.org/#search|ga|1|scala-xml
   */
  lazy val ScalaXml: ModuleID = "org.scala-lang.modules" %% "scala-xml" % "1.0.4" withSources() withJavadoc()

}
