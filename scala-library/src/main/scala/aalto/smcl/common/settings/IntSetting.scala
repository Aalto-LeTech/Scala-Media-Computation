package aalto.smcl.common.settings


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
  def apply(name: SettingKey, initialValue: Int, validator: Int => Option[Throwable]): IntSetting =
    new IntSetting(name, initialValue, validator)

}


/**
 *
 *
 * @param name
 * @param initialValue
 * @param validator
 * @author Aleksi Lukkarinen
 */
class IntSetting(
    name: SettingKey,
    initialValue: Int,
    validator: Int => Option[Throwable])
    extends Setting[Int](name, initialValue, validator) with Mutable {

}
