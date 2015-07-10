package aalto.smcl

import org.scalatest.{ FreeSpec, Matchers }
import org.scalatest.prop.PropertyChecks

/**
 * Base class for unit tests.
 *
 * @author Aleksi Lukkarinen
 */
abstract class UnitBaseSpec extends FreeSpec with Matchers with PropertyChecks {

}
