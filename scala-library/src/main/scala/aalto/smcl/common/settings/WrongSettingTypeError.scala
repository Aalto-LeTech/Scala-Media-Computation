package aalto.smcl.common.settings


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class WrongSettingTypeError(setting: SettingKey, settingValue: Any, cause: Throwable)
    extends RuntimeException(
      s"""Setting with "${setting.toString}" is of type ${settingValue.getClass.getName}.""",
      cause) {

  /**
   *
   *
   * @param setting
   * @param settingValue
   * @return
   */
  def this(setting: SettingKey, settingValue: Any) = this(setting, settingValue, null)

}
