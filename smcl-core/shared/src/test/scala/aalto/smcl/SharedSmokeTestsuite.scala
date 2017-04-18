package aalto.smcl

import org.scalatest.{DoNotDiscover, Suites}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
@DoNotDiscover
class SharedSmokeTestsuite extends Suites(
  new SMCLCoreSmokeTests()
) {

}
