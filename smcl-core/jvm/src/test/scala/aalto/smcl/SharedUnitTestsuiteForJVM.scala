package aalto.smcl


import aalto.smcl.infrastructure.tests.{AbstractSharedTestSuiteRunner, DefaultArgsUpdater}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class SharedUnitTestsuiteForJVM extends AbstractSharedTestSuiteRunner(
  smclInitializers = Seq(
    aalto.smcl.infrastructure.jvmawt.Initializer
  ),
  suitesToRun = Seq(new SharedUnitTestsuite()),
  argsUpdater = new DefaultArgsUpdater()) {

}
