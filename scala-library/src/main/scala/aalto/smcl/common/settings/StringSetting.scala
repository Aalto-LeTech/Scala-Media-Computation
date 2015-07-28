package aalto.smcl.common.settings


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
  def apply(name: SettingKey, initialValue: String, validator: String => Option[Throwable]): StringSetting =
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
    name: SettingKey,
    initialValue: String,
    validator: String => Option[Throwable])
    extends Setting[String](name, initialValue, validator) with Mutable {

}
