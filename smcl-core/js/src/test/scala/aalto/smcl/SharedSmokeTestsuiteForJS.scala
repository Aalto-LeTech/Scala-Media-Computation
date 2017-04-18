package aalto.smcl


import aalto.smcl.infrastructure.tests.{AbstractSharedTestSuiteRunner, DefaultArgsUpdater}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class SharedSmokeTestsuiteForJS extends AbstractSharedTestSuiteRunner(
  smclInitializers = Seq(
    aalto.smcl.infrastructure.js.Initializer
  ),
  suitesToRun = Seq(new SharedSmokeTestsuite()),
  argsUpdater = new DefaultArgsUpdater()) {

}
