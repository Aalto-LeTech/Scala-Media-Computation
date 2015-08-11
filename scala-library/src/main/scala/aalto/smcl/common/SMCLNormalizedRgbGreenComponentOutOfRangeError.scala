package aalto.smcl.common


/**
 *
 *
 * @param invalidValue
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLNormalizedRgbGreenComponentOutOfRangeError private[smcl](invalidValue: Double)
    extends RuntimeException(
      s"The normalized RGB green component of given color was out of its Double range ${ColorValidator.MinimumNormalizedRgbGreen} - " +
          s"${ColorValidator.MaximumNormalizedRgbGreen} (was $invalidValue)") {

}
