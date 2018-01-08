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

package smcl.infrastructure.tests


import org.scalatest._
import smcl.infrastructure.SMCLInitializer




/**
 *
 *
 * @param smclInitializers initialization classes that have to be called before
 *                         running SMCL on JavaScript platform
 * @param suitesToRun      the shared test suites to run
 * @param argsUpdater      an ArgsUpdater instance
 */
abstract class AbstractSharedTestSuiteRunner(
    smclInitializers: Seq[SMCLInitializer],
    suitesToRun: Seq[Suite],
    argsUpdater: ArgsUpdater) extends Suites(suitesToRun: _*) {

  /**
   *
   *
   * @param args
   *
   * @return
   */
  override def runNestedSuites(args: Args): Status = {
    if (args == null)
      fail("Tried to run nested test suite without an Args() instance")

    val newConfigData = Map("smcl-initializers" -> smclInitializers)
    val updatedArgs = argsUpdater.appendToConfigMap(args, newConfigData)

    super.runNestedSuites(updatedArgs)
  }

}
