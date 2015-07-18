package aalto.smcl.common


import scala.reflect.runtime.universe.{Constant, Literal}




/**
 * Miscellaneous string utility operations.
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object StringUtils {

  /**
   * Returns Scala's standard escaped representation of a given string.
   *
   * @param s   the string to be escaped
   */
  def escapeString(s: String): String = Literal(Constant(s)).toString()

}
