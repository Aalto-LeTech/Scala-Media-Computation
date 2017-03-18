package aalto.smcl.colors.exceptions


import aalto.smcl.colors.ColorValidator




/**
 *
 *
 * @param invalidValue
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLNormalizedRgbBlueComponentOutOfRangeError private[smcl](invalidValue: Double)
  extends RuntimeException(
    s"The normalized RGB blue component of given color was out of its Double range ${ColorValidator.MinimumNormalizedRgbBlue} - " +
      s"${ColorValidator.MaximumNormalizedRgbBlue} (was $invalidValue)") {

}
