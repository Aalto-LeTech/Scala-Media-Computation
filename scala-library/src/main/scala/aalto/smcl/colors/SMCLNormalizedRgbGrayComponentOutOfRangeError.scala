package aalto.smcl.colors


import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
 *
 *
 * @param invalidValue
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLNormalizedRgbGrayComponentOutOfRangeError private[smcl](invalidValue: Double)
  extends RuntimeException(
    s"The normalized RGB gray component of given color was out of its Double range ${ColorValidator.MinimumNormalizedRgbGray} - " +
      s"${ColorValidator.MaximumNormalizedRgbGray} (was $invalidValue)")
  with SMCLInitializationInvoker {

}
