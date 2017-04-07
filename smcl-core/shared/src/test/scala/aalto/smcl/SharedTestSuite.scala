package aalto.smcl

import org.scalatest.{DoNotDiscover, Suites}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
@DoNotDiscover
class SharedTestSuite extends Suites(
  new SmclCoreSmokeTests()
) {

  println("SharedTestSuite initializing...")

}
