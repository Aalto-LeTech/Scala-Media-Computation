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


import aalto.smcl.infrastructure.Describable
import aalto.smcl.infrastructure.exceptions.SettingValidationError




/**
 * A base class for all settings.
 *
 * @param key
 * @param initialValue
 * @param validator
 * @param companion
 * @tparam SettingType
 *
 * @author Aleksi Lukkarinen
 */
abstract class Setting[SettingType] private[settings](
    val key: String,
    val initialValue: SettingType,
    val validator: SettingType => Option[Throwable],
    private val companion: SettingCompanionConstants) extends Describable {

  if (validator != null)
    validator(initialValue) foreach {reason => throw SettingValidationError(key, reason)}

  /** Name of this class. */
  val fullTypeName: String = companion.FullTypeName

  /** Singular form of the "layman's name" of the setting's data type in lower case. */
  val typeNameSingular: String = companion.TypeNameSingular

  /** Plural form of the "layman's name" of the setting's data type in lower case. */
  val typeNamePlural: String = companion.TypeNamePlural

  /** Holds the current value of this [[Setting]]. */
  private var _currentValue: SettingType = initialValue

  /** First text paragraph of the description of this [[Setting]]. */
  override val descriptionTitle: String = fullTypeName

  /** Information about this [[Setting]] instance */
  override val describedProperties = Map()

  /**
   * Returns the current value of this [[Setting]].
   */
  def value: SettingType = _currentValue

  /**
   * Sets the value of this [[Setting]].
   *
   * @param value The new value to be set.
   *
   * @throws SettingValidationError Thrown if the validator defined for the setting does not accept the proposed new value.
   */
  def value_=(value: SettingType): Unit = {
    if (validator != null) {
      validator(value) foreach {
        reason => throw SettingValidationError(key, reason)
      }
    }

    _currentValue = value
  }

  /**
   * Resets this [[Setting]] to its initial value.
   */
  def reset(): Unit = {
    value = initialValue
  }

}
