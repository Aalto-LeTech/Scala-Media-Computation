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


import aalto.smcl.infrastructure.exceptions.SettingValidationError




/**
 * Represents a single setting of a specific type.
 *
 * @param key          Identifier of this [[Setting]].
 * @param initialValue Initial value of this [[Setting]].
 * @param validator    A function literal of type `A => Option[Throwable]` used to validate this [[Setting]].
 * @tparam SettingType Type of the value contained by this [[Setting]].
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
final class Setting[SettingType](
    val key: BaseSettingKeys.Value[SettingType],
    val initialValue: SettingType,
    val validator: SettingType => Option[Throwable])
    extends Mutable with Describable {

  if (validator != null)
    validator(initialValue) foreach {reason => throw SettingValidationError(key, reason)}

  /** Holds the current value of this [[Setting]]. */
  private var _currentValue: SettingType = initialValue

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "Setting"

  /** Information about this [[Setting]] instance */
  lazy val describedProperties = Map()

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
  def reset(): Unit = value = initialValue

}
