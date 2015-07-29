package aalto.smcl.common.settings


import aalto.smcl.common.settings.SettingKeys.BooleanSettingKey




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object BooleanSetting {

  /**
   *
   *
   * @param name
   * @param initialValue
   * @param validator
   * @return
   */
  def apply(name: BooleanSettingKey, initialValue: Boolean, validator: Boolean => Option[Throwable]): BooleanSetting =
    new BooleanSetting(name, initialValue, validator)

  /**
   *
   *
   * @param name
   * @param initialValue
   * @return
   */
  def apply(name: BooleanSettingKey, initialValue: Boolean): BooleanSetting =
    new BooleanSetting(name, initialValue, Setting.EmptyValidator)

}


/**
 *
 *
 * @param name
 * @param initialValue
 * @param validator
 * @author Aleksi Lukkarinen
 */
class BooleanSetting(
    override val name: BooleanSettingKey,
    override val initialValue: Boolean,
    override val validator: Boolean => Option[Throwable])
    extends Setting[Boolean](name, initialValue, validator) with Mutable {

}
