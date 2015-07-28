package aalto.smcl.common.settings


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class WrongSettingTypeError(setting: SmclSettingKey, settingValue: AnyRef, cause: Throwable)
    extends RuntimeException(
      s"Setting with \"${setting.toString}\" is of type ${settingValue.getClass.getName}.",
      cause) {

  /**
   *
   *
   * @param setting
   * @param settingValue
   * @return
   */
  def this(setting: SmclSettingKey, settingValue: AnyRef) = this(setting, settingValue, null)

}
