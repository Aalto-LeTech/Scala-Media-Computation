package aalto.smcl.colors


import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLRgbRedComponentOutOfRangeError private[smcl](invalidValue: Int)
  extends RuntimeException(
    s"The RGB red component of given color was out of its Int range ${ColorValidator.MinimumRgbRed} - " +
      s"${ColorValidator.MaximumRgbRed} (was $invalidValue)")
  with SMCLInitializationInvoker {

}
