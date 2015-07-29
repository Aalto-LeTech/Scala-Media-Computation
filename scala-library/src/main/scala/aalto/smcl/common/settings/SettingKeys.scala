package aalto.smcl.common.settings


import aalto.smcl.common.{ReflectionUtils, Color}




/**
 * Base classes for setting keys of all possible setting data types.
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object SettingKeys {


  /**
   * The base class for setting keys of all possible setting data types.
   */
  abstract sealed class Value {

    /**
     * Returns full name of this setting key, which equals the full path of this class.
     */
    lazy val fullName: String = ReflectionUtils.fullTypeNameOf(this)

    /**
     * Returns short name of this setting key, which equals the name of this class without any enclosing elements.
     */
    lazy val simpleName: String = ReflectionUtils.shortTypeNameOf(this)

    /**
     * Returns a string representation of this setting key.
     */
    def toToken: String = s"[$simpleName]"

    /**
     * Returns a string representation of this setting key.
     */
    override def toString: String = simpleName

  }


  /**
   * Base class for setting keys of type `Boolean`.
   */
  abstract class BooleanSettingKey() extends Value


  /**
   * Base class for setting keys of type `Int`.
   */
  abstract class IntSettingKey() extends Value


  /**
   * Base class for setting keys of type `String`.
   */
  abstract class StringSettingKey() extends Value


  /**
   * Base class for setting keys of type [[Color]].
   */
  abstract class ColorSettingKey() extends Value

}
