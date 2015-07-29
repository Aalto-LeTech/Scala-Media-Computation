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
 * @param name
 * @param initialValue
 * @param validator
 * @author Aleksi Lukkarinen
 */
class ColorSetting(
    override val name: ColorSettingKey,
    override val initialValue: Color,
    override val validator: Color => Option[Throwable])
    extends Setting[Color](name, initialValue, validator) with Mutable {

}
