package aalto.smcl.colors


import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLHsvSaturationComponentOutOfRangeError private[smcl](invalidValue: Double)
  extends RuntimeException(
    s"The HSV saturation component of given color was out of its Double range ${ColorValidator.MinimumHsvSaturation} - " +
      s"${ColorValidator.MaximumHsvSaturation} (was $invalidValue)")
  with SMCLInitializationInvoker {

}
