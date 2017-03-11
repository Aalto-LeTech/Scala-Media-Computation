package aalto.smcl.colors


import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLHsiIntensityComponentOutOfRangeError private[smcl](invalidValue: Double)
  extends RuntimeException(
    s"The HSI intensity component of given color was out of its Double range ${ColorValidator.MinimumHsiIntensity} - " +
      s"${ColorValidator.MaximumHsiIntensity} (was $invalidValue)")
  with SMCLInitializationInvoker {

}
