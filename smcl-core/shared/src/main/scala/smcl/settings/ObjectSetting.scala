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

package smcl.settings


/**
 * An companion object for [[ObjectSetting]] class.
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object ObjectSetting extends SettingCompanionConstants {

  /** Name of the related [[Setting]] subclass. */
  val FullTypeName = "ObjectSetting"

  /** Singular form of the "layman's name" of the setting's data type in lower case. */
  val TypeNameSingular = "object"

  /** Plural form of the "layman's name" of the setting's data type in lower case. */
  val TypeNamePlural = "objects"

  /**
   * Creates a new setting instance and registers it to the global [[com.sun.scenario.Settings]] map.
   *
   * @param key          identification string for the setting
   * @param initialValue initial value of the setting
   * @param validator    validator function for the setting
   *
   * @return a new setting instance
   */
  def apply[SettingDataType](
      key: String,
      initialValue: SettingDataType,
      validator: SettingValidator[SettingDataType]): ObjectSetting[SettingDataType] = {

    val newSetting = new ObjectSetting[SettingDataType](key, initialValue, validator)

    settingRegisterer.register(newSetting)

    newSetting
  }

}




/**
 * Setting of type `Object`.
 *
 * @author Aleksi Lukkarinen
 */
case class ObjectSetting[SettingDataType] private(
    override val key: String,
    override val initialValue: SettingDataType,
    override val validator: SettingValidator[SettingDataType])
    extends Setting[SettingDataType](
      key, initialValue, validator, ObjectSetting)
