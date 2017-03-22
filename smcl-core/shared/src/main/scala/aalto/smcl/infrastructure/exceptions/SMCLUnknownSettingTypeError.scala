package aalto.smcl.infrastructure.exceptions




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLUnknownSettingTypeError private[smcl](settingCandidateName: String, cause: Throwable)
  extends RuntimeException(
    s"""The given object of type $settingCandidateName does not represent a.valid setting""",
    cause) {

  /**
   *
   *
   * @param settingCandidate
   * @return
   */
  def this(settingCandidate: String) = this(settingCandidate, null)

}
