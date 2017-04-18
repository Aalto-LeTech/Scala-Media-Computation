package aalto.smcl


import aalto.smcl.infrastructure.tests.{AbstractSharedTestSuiteRunner, DefaultArgsUpdater}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class SharedUnitTestsuiteForJS extends AbstractSharedTestSuiteRunner(
  smclInitializers = Seq(
    aalto.smcl.infrastructure.js.Initializer
  ),
  suitesToRun = Seq(new SharedUnitTestsuite()),
  argsUpdater = new DefaultArgsUpdater()) {

}
