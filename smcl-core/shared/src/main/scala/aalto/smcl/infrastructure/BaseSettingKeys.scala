/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

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
  abstract sealed class Value[SettingType](
      val fullTypeName: String,
      val typeNameSingular: String,
      val typeNamePlural: String) extends Describable {

    /** First text paragraph of the description of this class. */
    override def descriptionTitle: String = fullTypeName

    /** Information about this [[Settings]] instance */
    override val describedProperties = Map()
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
