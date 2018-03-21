/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

/**
 * Plugin dependencies for the Scala Media Computation Library.
 */



// For Scala.js 1.0 in the future:
// libraryDependencies += "org.scala-js" %% "scalajs-env-nodejs" % "1.0.0-M3"




//--------------------------------------------------------------------------------------------------
//
// PLUGINS
//
//--------------------------------------------------------------------------------------------------

//
// sbt-scalajs
//
// https://dl.bintray.com/sbt/sbt-plugin-releases/org.scala-js/sbt-scalajs/
//
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.21")

//
// sbt-crossproject
//
//
//
addSbtPlugin("org.portable-scala" % "sbt-crossproject" % "0.3.1")

//
// sbt-scalajs-crossproject
//
//
//
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "0.3.1")

// For Scala.js 1.0 in the future:
//
// sbt-jsdependencies
//
//
//
// addSbtPlugin("org.scala-js" % "sbt-jsdependencies" % "1.0.0-M3")

// For Scala.js 1.0 in the future:
//
// For PhantomJS Testing:
//
//
//
// addSbtPlugin("org.scala-js" % "sbt-scalajs-env-phantomjs" % "1.0.0-M3")

//
// sbt-native-packager
//
// https://dl.bintray.com/sbt/sbt-plugin-releases/com.typesafe.sbt/sbt-native-packager/
//
//addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.2")

//
// sbt-git
//
// https://dl.bintray.com/sbt/sbt-plugin-releases/com.typesafe.sbt/sbt-git/
//
//addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.9.3")

//
// sbt-release
//
// https://dl.bintray.com/sbt/sbt-plugin-releases/com.github.gseitz/sbt-release/
//
//addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.7")

//
// sbt-github-release
//
// https://github.com/ohnosequences/sbt-github-release
// https://search.maven.org/#search%7Cgav%7C1%7Cg%3A%22javax.activation%22%20AND%20a%3A%22activation%22
//
addSbtPlugin("ohnosequences" % "sbt-github-release" % "0.7.0")

// Needed for JDK9, as the module is not included in classpath by default
libraryDependencies += "javax.activation" % "activation" % "1.1.1"

//
// sbt-dependency-graph
//
// https://github.com/jrudolph/sbt-dependency-graph
//
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.0")

//
// sbt-slack-notify
//
// https://github.com/jamiely/sbt-slack-notify
//
// NOTE: Binaries are not in a plublic repository yet --> compile yourself.
//
addSbtPlugin("ly.jamie" % "sbt-slack-notify" % "0.3.1")

//
// sbt-env-vars
//
// https://version.aalto.fi/gitlab/LeTech/sbt/sbt-env-vars
//
addSbtPlugin("fi.aalto.cs" % "sbt-env-vars" % "0.1.2")

//
// sbt-libraryinfo
//
// https://version.aalto.fi/gitlab/LeTech/sbt/sbt-library-info
//
addSbtPlugin("fi.aalto.cs" % "sbt-library-info" % "0.2.2")
