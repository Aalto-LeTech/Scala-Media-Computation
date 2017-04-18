package aalto.smcl


import aalto.smcl.infrastructure.tests.{AbstractSharedTestSuiteRunner, DefaultArgsUpdater}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class SharedItgTestsuiteForJVM extends AbstractSharedTestSuiteRunner(
  smclInitializers = Seq(
    aalto.smcl.infrastructure.jvmawt.Initializer
  ),
  suitesToRun = Seq(new SharedIntegrationTestsuite()),
  argsUpdater = new DefaultArgsUpdater()) {

}
