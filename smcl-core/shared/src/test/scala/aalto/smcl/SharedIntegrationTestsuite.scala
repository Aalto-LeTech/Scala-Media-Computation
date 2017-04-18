package aalto.smcl

import org.scalatest.{DoNotDiscover, Suites}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
@DoNotDiscover
class SharedIntegrationTestsuite extends Suites(
  new DummySMCLItgTests()
) {

}
