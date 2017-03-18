package aalto.smcl.colors.exceptions


import aalto.smcl.colors.ColorValidator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLRgbBlueComponentOutOfRangeError private[smcl](invalidValue: Int)
  extends RuntimeException(
    s"The RGB blue component of given color was out of its Int range ${ColorValidator.MinimumRgbBlue} - " +
      s"${ColorValidator.MaximumRgbBlue} (was $invalidValue)") {

}
