package aalto.smcl.colors


import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLRgbaOpacityComponentFromValueProviderOutOfRangeError private[smcl](invalidValue: Int)
  extends RuntimeException(
    s"An RGBA opacity component value returned by a value provider function was out of its range " +
      s"${ColorValidator.MinimumRgbaOpacity} - ${ColorValidator.MaximumRgbaOpacity} (was $invalidValue)")
  with SMCLInitializationInvoker {

}
