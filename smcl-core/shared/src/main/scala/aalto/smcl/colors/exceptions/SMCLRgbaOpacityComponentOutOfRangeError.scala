package aalto.smcl.colors.exceptions


import aalto.smcl.colors.ColorValidator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLRgbaOpacityComponentOutOfRangeError private[smcl](invalidValue: Int)
  extends RuntimeException(
    s"The RGBA opacity component of given color was out of its Int range ${ColorValidator.MinimumRgbaOpacity} - " +
      s"${ColorValidator.MaximumRgbaOpacity} (was $invalidValue)") {

}