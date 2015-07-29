package aalto.smcl.common.settings


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class UninitializedSettingError(setting: SettingKeys.Value, cause: Throwable)
    extends RuntimeException(
      s"""No setting with name "${setting.toString}" is initialized.""",
      cause) {

  /**
   *
   *
   * @param setting
   * @return
   */
  def this(setting: SettingKeys.Value) = this(setting, null)

}
