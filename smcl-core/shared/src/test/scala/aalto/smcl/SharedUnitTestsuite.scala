package aalto.smcl


import org.scalatest.{DoNotDiscover, Suites}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
@DoNotDiscover
class SharedUnitTestsuite extends Suites(
  new DummySMCLUnitTests()
) {

}
