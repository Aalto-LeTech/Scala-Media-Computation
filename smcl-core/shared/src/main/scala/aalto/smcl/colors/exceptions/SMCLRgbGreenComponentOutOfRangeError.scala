package aalto.smcl.colors.exceptions


import aalto.smcl.colors.ColorValidator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLRgbGreenComponentOutOfRangeError private[smcl](invalidValue: Int)
  extends RuntimeException(
    s"The RGB green component of given color was out of Int its range ${ColorValidator.MinimumRgbGreen} - " +
      s"${ColorValidator.MaximumRgbGreen} (was $invalidValue)") {

}
