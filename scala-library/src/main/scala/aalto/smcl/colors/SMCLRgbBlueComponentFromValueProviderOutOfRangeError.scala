package aalto.smcl.colors


import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLRgbBlueComponentFromValueProviderOutOfRangeError private[smcl](invalidValue: Int)
  extends RuntimeException(
    s"An RGB blue component value returned by a value provider function was out of its range " +
      s"${ColorValidator.MinimumRgbBlue} - ${ColorValidator.MaximumRgbBlue} (was $invalidValue)")
  with SMCLInitializationInvoker {

}
