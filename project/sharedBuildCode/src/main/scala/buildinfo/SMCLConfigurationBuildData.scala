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

package buildinfo


/**
 *
 *
 * @param majorVersion
 * @param minorVersion
 * @param microVersion
 * @param isRelease
 * @param projectVersionString
 * @param moduleVersionString
 * @param scalaVersion
 * @param sbtVersion
 * @param sourceJavaVersion
 * @param targetJavaVersion
 * @param targetPlatform
 * @param isJVMAWTPlatform
 * @param isJSHTML5Platform
 * @param buildTime
 *
 * @author Aleksi Lukkarinen
 */
case class SMCLConfigurationBuildData(
    majorVersion: Int,
    minorVersion: Int,
    microVersion: Int,
    isRelease: Boolean,
    projectVersionString: String,
    moduleVersionString: String,
    scalaVersion: String,
    sbtVersion: String,
    sourceJavaVersion: String,
    targetJavaVersion: String,
    targetPlatform: String,
    isJVMAWTPlatform: Boolean,
    isJSHTML5Platform: Boolean,
    buildTime: Long)
