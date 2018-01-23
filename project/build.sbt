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
 * Build definition for the SMCL's build definition.
 */

val sharedBuildCode = project
val buildRoot = (project in file("."))
    .dependsOn(sharedBuildCode)



libraryDependencies ++= Seq(
  /**
   * circe
   *
   * @see https://github.com/circe/circe
   * @see http://search.maven.org/#search%7Cga%7C1%7Ccirce
   */
  "io.circe" %% "circe-core" % "0.9.1" withSources () withJavadoc (),
  "io.circe" %% "circe-generic" % "0.9.1" withSources () withJavadoc (),
  "io.circe" %% "circe-parser" % "0.9.1" withSources () withJavadoc (),
)
