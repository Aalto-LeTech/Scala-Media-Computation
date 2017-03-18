package aalto.smcl.colors.exceptions


import aalto.smcl.colors.ColorValidator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLHsiIntensityComponentOutOfRangeError private[smcl](invalidValue: Double)
  extends RuntimeException(
    s"The HSI intensity component of given color was out of its Double range ${ColorValidator.MinimumHsiIntensity} - " +
      s"${ColorValidator.MaximumHsiIntensity} (was $invalidValue)") {

}
