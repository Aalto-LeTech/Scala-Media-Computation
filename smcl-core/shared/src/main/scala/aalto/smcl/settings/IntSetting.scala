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
 * An companion object for [[IntSetting]] class.
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object IntSetting extends SettingCompanionMethods[Int, IntSetting] {

  /** Name of the related [[Setting]] subclass. */
  val FullTypeName = "IntSetting"

  /** Singular form of the "layman's name" of the setting's data type in lower case. */
  val TypeNameSingular = "integer"

  /** Plural form of the "layman's name" of the setting's data type in lower case. */
  val TypeNamePlural = "integers"

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
      initialValue: Int,
      validator: SettingValidator[Int]): IntSetting = {

    new IntSetting(key, initialValue, validator)
  }

}




/**
 * Setting of type `Int`.
 *
 * @author Aleksi Lukkarinen
 */
case class IntSetting private(
    override val key: String,
    override val initialValue: Int,
    override val validator: SettingValidator[Int])
    extends Setting[Int](
      key, initialValue, validator, IntSetting)
