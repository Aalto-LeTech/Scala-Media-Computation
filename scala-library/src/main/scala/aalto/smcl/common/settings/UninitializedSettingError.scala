package aalto.smcl.common.settings


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class UninitializedSettingError(setting: SettingKey, cause: Throwable)
    extends RuntimeException(
      s"""No setting with name "${setting.toString}" is initialized.""",
      cause) {

  /**
   *
   *
   * @param setting
   * @return
   */
  def this(setting: SettingKey) = this(setting, null)

}
