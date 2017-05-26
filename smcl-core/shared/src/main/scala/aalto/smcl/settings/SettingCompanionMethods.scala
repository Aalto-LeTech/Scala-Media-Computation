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

package aalto.smcl.settings


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait SettingCompanionMethods[SettingDataType, +SettingType <: Setting[SettingDataType]]
    extends SettingCompanionConstants {

  /**
   * A factory method for creating a new setting instance.
   *
   * @param key          identification string for the setting
   * @param initialValue initial value of the setting
   * @param validator    validator function for the setting
   *
   * @return a new setting instance
   */
  protected
  def newSettingInstance(
      key: String,
      initialValue: SettingDataType,
      validator: SettingValidator[SettingDataType]): SettingType

  /**
   * Creates a new setting instance and registers it to the global [[Settings]] map.
   *
   * @param key          identification string for the setting
   * @param initialValue initial value of the setting
   * @param validator    validator function for the setting
   *
   * @return a new setting instance
   */
  def apply(
      key: String,
      initialValue: SettingDataType,
      validator: SettingValidator[SettingDataType]): SettingType = {

    val newSetting = newSettingInstance(key, initialValue, validator)

    settingRegisterer.register(newSetting)

    newSetting
  }

}
