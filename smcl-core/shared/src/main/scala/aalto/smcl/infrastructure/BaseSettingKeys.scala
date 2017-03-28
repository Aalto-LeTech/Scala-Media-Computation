package aalto.smcl.infrastructure


import aalto.smcl.colors.RGBAColor




/**
 * Base classes for setting keys of all possible setting data types.
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object BaseSettingKeys {

  /**
   * The base class for setting keys of all possible setting data types.
   *
   * @param typeNameSingular
   * @param typeNamePlural
   * @tparam SettingType
   */
  abstract sealed class Value[SettingType] (
    fullTypeName: String,
    val typeNameSingular: String,
    val typeNamePlural: String) extends Tokenizable {

    /** Information about this [[Settings]] instance */
    lazy val metaInformation = MetaInformationMap(fullTypeName, Map())

    /**
     * Returns full name of this setting key, which equals the full path of this class.
     */
    lazy val fullName: String = fullTypeName

    /**
     * Returns short name of this setting key, which equals the name of this class without any enclosing elements.
     */
    lazy val simpleName: String = fullTypeName    // TODO: Get the last part of the name

    /**
     * Returns a string representation of this setting key.
     */
    override def toToken: String = s"[$simpleName]"

    /**
     * Returns a string representation of this setting key.
     */
    override def toString: String = simpleName

  }


  /**
   * Base class for setting keys of type `Boolean`.
   */
  abstract class BooleanSettingKey()
    extends Value[Boolean]("BooleanSettingKey", "boolean", "booleans")


  /**
   * Base class for setting keys of type `Enumeration`.
   */
  abstract class EnumSettingKey[SettingType]()
    extends Value[SettingType]("EnumSettingKey", "enumeration", "enumerations")


  /**
   * Base class for setting keys of type `Int`.
   */
  abstract class IntSettingKey()
    extends Value[Int]("IntSettingKey", "integer", "integers")


  /**
   * Base class for setting keys of type `String`.
   */
  abstract class StringSettingKey()
    extends Value[String]("StringSettingKey", "string", "strings")


  /**
   * Base class for setting keys of type [[aalto.smcl.colors.RGBAColor]].
   */
  abstract class ColorSettingKey()
    extends Value[RGBAColor]("ColorSettingKey", "color", "colors")

}
