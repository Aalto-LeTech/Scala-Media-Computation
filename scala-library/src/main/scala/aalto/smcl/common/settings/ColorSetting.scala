package aalto.smcl.common.settings


import aalto.smcl.common.Color




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object ColorSetting {

  /**
   *
   *
   * @param name
   * @param initialValue
   * @param validator
   * @return
   */
  def apply(name: SettingKey, initialValue: Color, validator: Color => Option[Throwable]): ColorSetting =
    new ColorSetting(name, initialValue, validator)

}


/**
 *
 *
 * @param name
 * @param initialValue
 * @param validator
 * @author Aleksi Lukkarinen
 */
class ColorSetting(
    name: SettingKey,
    initialValue: Color,
    validator: Color => Option[Throwable])
    extends Setting[Color](name, initialValue, validator) with Mutable {

}
