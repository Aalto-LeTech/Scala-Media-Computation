package aalto

import org.scalatest.{ FreeSpec, Matchers }
import org.scalatest.prop.PropertyChecks

/**
 * Base class for unit tests.
 *
 * @author Aleksi Lukkarinen
 */
abstract class UnitSpecBase extends FreeSpec with Matchers with PropertyChecks {

}
