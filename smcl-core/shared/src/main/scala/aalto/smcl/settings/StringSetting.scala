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
 * An companion object for [[StringSetting]] class.
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object StringSetting extends SettingCompanionMethods[String, StringSetting] {

  /** Name of the related [[Setting]] subclass. */
  val FullTypeName = "StringSetting"

  /** Singular form of the "layman's name" of the setting's data type in lower case. */
  val TypeNameSingular = "string"

  /** Plural form of the "layman's name" of the setting's data type in lower case. */
  val TypeNamePlural = "strings"

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
      initialValue: String,
      validator: SettingValidator[String]): StringSetting = {

    new StringSetting(key, initialValue, validator)
  }

}




/**
 * Setting of type `String`.
 *
 * @author Aleksi Lukkarinen
 */
case class StringSetting private(
    override val key: String,
    override val initialValue: String,
    override val validator: SettingValidator[String])
    extends Setting[String](
      key, initialValue, validator, StringSetting)
