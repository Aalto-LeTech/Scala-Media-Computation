package aalto.smcl.infrastructure.tests


import org.scalatest.prop.PropertyChecks
import org.scalatest.{FreeSpec, Matchers}




/**
 * Base class for unit tests.
 *
 * @author Aleksi Lukkarinen
 */
abstract class UnitSpecBase extends FreeSpec with Matchers with PropertyChecks {

}
