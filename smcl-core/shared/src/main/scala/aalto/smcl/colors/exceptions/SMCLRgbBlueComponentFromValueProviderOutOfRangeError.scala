package aalto.smcl.colors.exceptions


import aalto.smcl.colors.ColorValidator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLRgbBlueComponentFromValueProviderOutOfRangeError private[smcl](invalidValue: Int)
  extends RuntimeException(
    s"An RGB blue component value returned by a value provider function was out of its range " +
      s"${ColorValidator.MinimumRgbBlue} - ${ColorValidator.MaximumRgbBlue} (was $invalidValue)") {

}
