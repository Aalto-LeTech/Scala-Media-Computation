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
 * Plugin definitions for the Scala Media Computation Library.
 */


//-------------------------------------------------------------------------------------------------
//
// PLUGINS
//
//-------------------------------------------------------------------------------------------------

// https://dl.bintray.com/sbt/sbt-plugin-releases/org.scala-js/sbt-scalajs/
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.22")

// https://dl.bintray.com/sbt/sbt-plugin-releases/com.typesafe.sbt/sbt-native-packager/
//addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.2")

// https://dl.bintray.com/sbt/sbt-plugin-releases/com.typesafe.sbt/sbt-git/
//addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.9.3")

// https://dl.bintray.com/sbt/sbt-plugin-releases/com.github.gseitz/sbt-release/
//addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.7")

// https://github.com/jamiely/sbt-slack-notify
// NOTE: Binaries are not in a plublic repository yet --> compile yourself.
// addSbtPlugin("ly.jamie" % "sbt-slack-notify" % "0.3.1")

// Our own sbt-libraryinfo
addSbtPlugin("fi.aalto.cs" % "sbtlibraryinfo" % "0.1.0")
