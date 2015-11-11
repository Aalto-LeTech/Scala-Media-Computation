package aalto.smcl.infrastructure


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLUninitializedSettingError private[smcl](settingKey: BaseSettingKeys.Value[_], cause: Throwable)
  extends RuntimeException(
    s"""No setting with name "${settingKey.toString}" is initialized.""",
    cause) {

  /**
   *
   *
   * @param settingKey
   * @return
   */
  def this(settingKey: BaseSettingKeys.Value[_]) = this(settingKey, null)

}
