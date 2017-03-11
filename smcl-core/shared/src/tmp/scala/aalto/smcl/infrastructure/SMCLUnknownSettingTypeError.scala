package aalto.smcl.infrastructure


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLUnknownSettingTypeError private[smcl](settingCandidate: AnyRef, cause: Throwable)
  extends RuntimeException(
    s"""The given object of type ${new ReflectionUtils().shortTypeNameOf(settingCandidate)} does not represent a.valid setting""",
    cause) {

  /**
   *
   *
   * @param settingCandidate
   * @return
   */
  def this(settingCandidate: AnyRef) = this(settingCandidate, null)

}
