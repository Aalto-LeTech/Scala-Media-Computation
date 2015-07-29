package aalto.smcl.common.settings


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class WrongSettingTypeError(setting: SettingKeys.Value, settingValue: Any, cause: Throwable)
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
  def this(setting: SettingKeys.Value, settingValue: Any) = this(setting, settingValue, null)

}
