package aalto.smcl.infrastructure

import scala.reflect.runtime.universe.{Constant, Literal}




/**
 * Miscellaneous string utility operations.
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class StringUtils private[infrastructure]() {

  /**
   * Returns Scala's standard escaped representation of a given string.
   *
   * @param s   the string to be escaped
   */
  def escapeString(s: String): String = Literal(Constant(s)).toString()

}
