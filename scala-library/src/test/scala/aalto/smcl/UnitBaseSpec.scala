package aalto.smcl

import org.scalatest._

/**
 * Base class for unit tests.
 *
 * @author Aleksi Lukkarinen
 */
abstract class UnitBaseSpec extends WordSpec with Matchers {
  val haveADefault = afterWord("have a default")
  val haveItsDefault = afterWord("have its default")
  val triedToInstantiateWith = afterWord("tried to instantiate with")
}
