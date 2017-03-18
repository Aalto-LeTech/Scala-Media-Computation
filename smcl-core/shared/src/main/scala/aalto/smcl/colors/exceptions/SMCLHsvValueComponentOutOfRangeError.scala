package aalto.smcl.colors.exceptions


import aalto.smcl.colors.ColorValidator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLHsvValueComponentOutOfRangeError private[smcl](invalidValue: Double)
  extends RuntimeException(
    s"The HSV value component of given color was out of its Double range ${ColorValidator.MinimumHsvValue} - " +
      s"${ColorValidator.MaximumHsvValue} (was $invalidValue)") {

}
