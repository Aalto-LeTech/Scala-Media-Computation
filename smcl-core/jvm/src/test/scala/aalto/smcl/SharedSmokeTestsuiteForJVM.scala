package aalto.smcl


import aalto.smcl.infrastructure.tests.{AbstractSharedTestSuiteRunner, DefaultArgsUpdater}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class SharedSmokeTestsuiteForJVM extends AbstractSharedTestSuiteRunner(
  smclInitializers = Seq(
    aalto.smcl.infrastructure.jvmawt.Initializer
  ),
  suitesToRun = Seq(new SharedSmokeTestsuite()),
  argsUpdater = new DefaultArgsUpdater()) {

}
