package aalto.smcl.colors


/**
 *
 *
 * @param invalidValue
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLNormalizedRgbRedComponentOutOfRangeError private[smcl](invalidValue: Double)
    extends RuntimeException(
      s"The normalized RGB red component of given color was out of its Double range ${ColorValidator.MinimumNormalizedRgbRed} - " +
          s"${ColorValidator.MaximumNormalizedRgbRed} (was $invalidValue)") {

}
