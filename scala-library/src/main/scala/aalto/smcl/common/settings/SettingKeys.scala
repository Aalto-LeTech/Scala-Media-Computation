package aalto.smcl.common.settings


/**
 * A marker trait for all key objects of the Settigns class.
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object SettingKeys {


  /** */
  abstract sealed class Value


  /** */
  class BooleanSettingKey() extends Value


  /** */
  class IntSettingKey() extends Value


  /** */
  class StringSettingKey() extends Value


  /** */
  class ColorSettingKey() extends Value


}
