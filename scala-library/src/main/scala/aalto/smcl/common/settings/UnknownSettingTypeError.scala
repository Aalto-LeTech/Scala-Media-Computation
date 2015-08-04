package aalto.smcl.common.settings


import aalto.smcl.common.ReflectionUtils




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class UnknownSettingTypeError(settingCandidate: AnyRef, cause: Throwable)
    extends RuntimeException(
      s"""The given object of type ${ReflectionUtils.shortTypeNameOf(settingCandidate)} does not represent a.valid setting""",
      cause) {

  /**
   *
   *
   * @param settingCandidate
   * @return
   */
  def this(settingCandidate: AnyRef) = this(settingCandidate, null)

}
