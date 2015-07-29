package aalto.smcl.common.settings


import aalto.smcl.common.settings.SettingKeys.StringSettingKey




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object StringSetting {

  /**
   *
   *
   * @param name
   * @param initialValue
   * @param validator
   * @return
   */
  def apply(name: StringSettingKey, initialValue: String, validator: String => Option[Throwable]): StringSetting =
    new StringSetting(name, initialValue, validator)

}


/**
 *
 *
 * @param name
 * @param initialValue
 * @param validator
 * @author Aleksi Lukkarinen
 */
class StringSetting(
    override val name: StringSettingKey,
    override val initialValue: String,
    override val validator: String => Option[Throwable])
    extends Setting[String](name, initialValue, validator) with Mutable {

}
