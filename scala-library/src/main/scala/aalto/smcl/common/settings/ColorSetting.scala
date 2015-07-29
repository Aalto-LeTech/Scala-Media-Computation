package aalto.smcl.common.settings


import aalto.smcl.common.Color
import aalto.smcl.common.settings.SettingKeys.ColorSettingKey




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
  def apply(name: ColorSettingKey, initialValue: Color, validator: Color => Option[Throwable]): ColorSetting =
    new ColorSetting(name, initialValue, validator)

}


/**
 *
 *
 * @param key
 * @param initialValue
 * @param validator
 * @author Aleksi Lukkarinen
 */
class ColorSetting private (
    override val key: ColorSettingKey,
    override val initialValue: Color,
    override val validator: Color => Option[Throwable])
    extends Setting[Color](key, initialValue, validator) with Mutable {

}
