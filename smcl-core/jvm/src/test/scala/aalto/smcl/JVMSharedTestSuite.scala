package aalto.smcl


import aalto.smcl.infrastructure.tests.{AbstractSharedTestSuiteRunner, DefaultArgsUpdater}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class JVMSharedTestSuite extends AbstractSharedTestSuiteRunner(
  smclInitializers = Seq(
    aalto.smcl.infrastructure.jvmawt.Initializer
  ),
  suitesToRun = Seq(new SharedTestSuite()),
  argsUpdater = new DefaultArgsUpdater()) {

}
