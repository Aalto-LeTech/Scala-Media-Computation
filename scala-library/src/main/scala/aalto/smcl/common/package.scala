package aalto.smcl


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object common {

  /** An empty string. */
  protected[smcl] val STR_EMPTY = ""

  /** A string containing a single space. */
  protected[smcl] val STR_SPACE = " "

  /** A string containing a single colon. */
  protected[smcl] val STR_COLON = ":"

  /** A string containing a single colon. */
  protected[smcl] val STR_COLON_AS_UNICODE = "\u003A"

  /** A string containing a single semicolon. */
  protected[smcl] val STR_SEMICOLON = ";"

  /** A string containing a single semicolon. */
  protected[smcl] val STR_SEMICOLON_AS_UNICODE = "\u003B"

  /** A string containing a single left angle bracket. */
  protected[smcl] val STR_LEFT_ANGLE_BRACKET = "["

  /** A string containing a single right angle bracket. */
  protected[smcl] val STR_RIGHT_ANGLE_BRACKET = "]"

  /** A string containing a single zero. */
  protected[smcl] val STR_ZERO = "0"

  /** Global settings for the SMCL. */
  val GlobalSettings: SmclSettings = new SmclSettings()

}
