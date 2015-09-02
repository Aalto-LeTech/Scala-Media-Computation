package aalto.smcl.infrastructure.settings


import aalto.smcl.SMCL
import aalto.smcl.infrastructure.ReflectionUtils




/**
 * Represents a single setting of a specific type.
 *
 * @param key             Identifier of this [[Setting]].
 * @param initialValue    Initial value of this [[Setting]].
 * @param validator       A function literal of type `A => Option[Throwable]` used to validate this [[Setting]].
 * @tparam SettingType    Type of the value contained by this [[Setting]].
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] final class Setting[SettingType](
    val key: BaseSettingKeys.Value[SettingType],
    val initialValue: SettingType,
    val validator: SettingType => Option[Throwable]) extends Mutable {

  SMCL.performInitialization()


  if (validator != null)
    validator(initialValue).foreach {reason => throw new SMCLSettingValidationError(key, reason)}

  /** Holds the current value of this [[Setting]]. */
  private var _currentValue: SettingType = initialValue


  /**
   * Returns the current value of this [[Setting]].
   */
  def value: SettingType = _currentValue

  /**
   * Sets the value of this [[Setting]].
   *
   * @param value                     The new value to be set.
   * @throws SMCLSettingValidationError   Thrown if the validator defined for the setting does not accept the proposed new value.
   */
  def value_=(value: SettingType): Unit = {
    if (validator != null) {
      validator(initialValue).foreach {
        reason => throw new SMCLSettingValidationError(key, reason)
      }
    }

    _currentValue = value
  }

  /**
   * Resets this [[Setting]] to its initial value.
   */
  def reset(): Unit = value = initialValue

  /**
   * Returns a formalized token representation of this [[Setting]].
   */
  def toToken: String =
    s"[${ReflectionUtils.shortTypeNameOf(this)}; " +
        s"key: ${key.simpleName}; initial-value: $initialValue; current-value: $value]"

  /**
   * Returns a string representation of this [[Setting]].
   */
  override def toString: String = s"${key.simpleName} = $value  (initially $initialValue)"

}
