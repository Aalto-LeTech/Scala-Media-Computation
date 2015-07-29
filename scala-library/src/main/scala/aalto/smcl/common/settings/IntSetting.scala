package aalto.smcl.common.settings


import aalto.smcl.common.settings.SettingKeys.IntSettingKey




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object IntSetting {

  /**
   *
   *
   * @param name
   * @param initialValue
   * @param validator
   * @return
   */
  def apply(name: IntSettingKey, initialValue: Int, validator: Int => Option[Throwable]): IntSetting =
    new IntSetting(name, initialValue, validator)

}


/**
 *
 *
 * @param key
 * @param initialValue
 * @param validator
 * @author Aleksi Lukkarinen
 */
class IntSetting private (
    override val key: IntSettingKey,
    override val initialValue: Int,
    override val validator: Int => Option[Throwable])
    extends Setting[Int](key, initialValue, validator) with Mutable {

}
