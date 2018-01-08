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


import smcl.colors.rgb.Color




/**
 * An companion object for [[ColorSetting]] class.
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object ColorSetting extends SettingCompanionMethods[Color, ColorSetting] {

  /** Name of the related [[Setting]] subclass. */
  val FullTypeName = "ColorSetting"

  /** Singular form of the "layman's name" of the setting's data type in lower case. */
  val TypeNameSingular = "color"

  /** Plural form of the "layman's name" of the setting's data type in lower case. */
  val TypeNamePlural = "colors"

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
      initialValue: Color,
      validator: SettingValidator[Color]): ColorSetting = {

    new ColorSetting(key, initialValue, validator)
  }

}




/**
 * Setting of type `RGBAColor`.
 *
 * @author Aleksi Lukkarinen
 */
case class ColorSetting private(
    override val key: String,
    override val initialValue: Color,
    override val validator: SettingValidator[Color])
    extends Setting[Color](
      key, initialValue, validator, ColorSetting)
