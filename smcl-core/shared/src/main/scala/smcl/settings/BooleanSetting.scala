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
 * An companion object for [[BooleanSetting]] class.
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object BooleanSetting extends SettingCompanionMethods[Boolean, BooleanSetting] {

  /** Name of the related [[Setting]] subclass. */
  val FullTypeName = "BooleanSetting"

  /** Singular form of the "layman's name" of the setting's data type in lower case. */
  val TypeNameSingular = "boolean"

  /** Plural form of the "layman's name" of the setting's data type in lower case. */
  val TypeNamePlural = "booleans"

  /**
   * A factory method for creating a new setting instance.
   *
   * @param key          identification string for the setting
   * @param initialValue initial value of the setting
   * @param validator    validator function for the setting
   *
   * @return a new setting instance
   */
  override protected
  def newSettingInstance(
      key: String,
      initialValue: Boolean,
      validator: SettingValidator[Boolean]): BooleanSetting = {

    new BooleanSetting(key, initialValue, validator)
  }

}




/**
 * Setting of type `Boolean`.
 *
 * @author Aleksi Lukkarinen
 */
case class BooleanSetting private(
    override val key: String,
    override val initialValue: Boolean,
    override val validator: SettingValidator[Boolean])
    extends Setting[Boolean](
      key, initialValue, validator, BooleanSetting)
