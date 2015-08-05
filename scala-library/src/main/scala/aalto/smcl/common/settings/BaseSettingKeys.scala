package aalto.smcl.common.settings


import aalto.smcl.common.{RGBAColor, ReflectionUtils}




/**
 * Base classes for setting keys of all possible setting data types.
 *
 * @author Aleksi Lukkarinen
 */
object BaseSettingKeys {


  /**
   * The base class for setting keys of all possible setting data types.
   *
   * @param typeNameSingular
   * @param typeNamePlural
   * @tparam SettingType
   */
  abstract sealed class Value[SettingType](
      val typeNameSingular: String,
      val typeNamePlural: String) {

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
  abstract class BooleanSettingKey() extends Value[Boolean]("boolean", "booleans")


  /**
   * Base class for setting keys of type `Enumeration`.
   */
  abstract class EnumSettingKey[SettingType]() extends Value[SettingType]("enumeration", "enumerations")


  /**
   * Base class for setting keys of type `Int`.
   */
  abstract class IntSettingKey() extends Value[Int]("integer", "integers")


  /**
   * Base class for setting keys of type `String`.
   */
  abstract class StringSettingKey() extends Value[String]("string", "strings")


  /**
   * Base class for setting keys of type [[RGBAColor]].
   */
  abstract class ColorSettingKey() extends Value[RGBAColor]("color", "colors")


}
