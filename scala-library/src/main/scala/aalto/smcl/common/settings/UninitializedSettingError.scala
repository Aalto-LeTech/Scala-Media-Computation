package aalto.smcl.common.settings


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class UninitializedSettingError(setting: SmclSettingKey, cause: Throwable)
    extends RuntimeException(
      s"No setting with name \"${setting.toString}\" is initialized.",
      cause) {

  /**
   *
   *
   * @param setting
   * @return
   */
  def this(setting: SmclSettingKey) = this(setting, null)

}
