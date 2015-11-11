package aalto.smcl.colors


import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLRgbaOpacityComponentOutOfRangeError private[smcl](invalidValue: Int)
  extends RuntimeException(
    s"The RGBA opacity component of given color was out of its Int range ${ColorValidator.MinimumRgbaOpacity} - " +
      s"${ColorValidator.MaximumRgbaOpacity} (was $invalidValue)")
  with SMCLInitializationInvoker {

}
