package aalto.smcl.common


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
