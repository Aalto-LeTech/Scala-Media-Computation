package aalto.smcl.colors.exceptions


import aalto.smcl.colors.ColorValidator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLRgbRedComponentOutOfRangeError private[smcl](invalidValue: Int)
  extends RuntimeException(
    s"The RGB red component of given color was out of its Int range ${ColorValidator.MinimumRgbRed} - " +
      s"${ColorValidator.MaximumRgbRed} (was $invalidValue)") {

}
