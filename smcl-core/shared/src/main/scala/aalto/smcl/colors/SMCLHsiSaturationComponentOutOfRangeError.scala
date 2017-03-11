package aalto.smcl.colors




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLHsiSaturationComponentOutOfRangeError private[smcl](invalidValue: Double)
  extends RuntimeException(
    s"The HSI saturation component of given color was out of its Double range ${ColorValidator.MinimumHsiSaturation} - " +
      s"${ColorValidator.MaximumHsiSaturation} (was $invalidValue)") {

}
