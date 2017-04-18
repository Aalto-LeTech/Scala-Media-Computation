package aalto.smcl.infrastructure.tests


import org.scalatest._

import aalto.smcl.infrastructure.SMCLInitializer




/**
 *
 *
 * @param smclInitializers  initialization classes that have to be called before
 *                          running SMCL on JavaScript platform
 * @param suitesToRun       the shared test suites to run
 * @param argsUpdater       an ArgsUpdater instance
 */
abstract class AbstractSharedTestSuiteRunner(
    smclInitializers: Seq[SMCLInitializer],
    suitesToRun: Seq[Suite],
    argsUpdater: ArgsUpdater) extends Suites(suitesToRun: _*) {

  /**
   *
   *
   * @param args
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
