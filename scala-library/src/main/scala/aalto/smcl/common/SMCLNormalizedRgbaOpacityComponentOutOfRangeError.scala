package aalto.smcl.common


/**
 *
 *
 * @param invalidValue
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLNormalizedRgbaOpacityComponentOutOfRangeError private[smcl](invalidValue: Double)
    extends RuntimeException(
      s"The normalized RGBA opacity component of given color was out of its Double range ${ColorValidator.MinimumNormalizedRgbaOpacity} - " +
          s"${ColorValidator.MaximumNormalizedRgbaOpacity} (was $invalidValue)") {

}
