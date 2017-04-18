package aalto.smcl


import aalto.smcl.infrastructure.tests.{AbstractSharedTestSuiteRunner, DefaultArgsUpdater}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class SharedItgTestsuiteForJS extends AbstractSharedTestSuiteRunner(
  smclInitializers = Seq(
    aalto.smcl.infrastructure.js.Initializer
  ),
  suitesToRun = Seq(new SharedIntegrationTestsuite()),
  argsUpdater = new DefaultArgsUpdater()) {

}
