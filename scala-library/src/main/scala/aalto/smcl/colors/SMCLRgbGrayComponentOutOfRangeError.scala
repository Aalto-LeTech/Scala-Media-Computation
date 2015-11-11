package aalto.smcl.colors


import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLRgbGrayComponentOutOfRangeError private[smcl](invalidValue: Int)
  extends RuntimeException(
    s"The RGB gray component of given color was out of its Int range ${ColorValidator.MinimumRgbGray} - " +
      s"${ColorValidator.MaximumRgbGray} (was $invalidValue)")
  with SMCLInitializationInvoker {

}
