package aalto.smcl.common.settings


import aalto.smcl.common.ReflectionUtils




/**
 * Constants related to the [[Settings]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Setting {

  /** */
  val EmptyValidator = null

}


/**
 * Represents a single setting of a specific type. This class must be inherited to
 * define the actual class to be used (see for instance [[BooleanSetting]] and [[ColorSetting]]).
 *
 * @param key             Identifier of this [[Setting]].
 * @param initialValue    Initial value of this [[Setting]].
 * @param validator       A function literal of type `A => Option[Throwable]` used to validate this [[Setting]].
 * @tparam A              Type of the value contained by this [[Setting]].
 *
 * @author Aleksi Lukkarinen
 */
abstract class Setting[A] protected(
    val key: SettingKeys.Value,
    val initialValue: A,
    val validator: A => Option[Throwable]) extends Mutable {

  if (validator != null)
    validator(initialValue).foreach {reason => throw new SettingValidationError(key, reason)}

  /** Holds the current value of this [[Setting]]. */
  private var _currentValue: A = initialValue


  /**
   * Returns the current value of this [[Setting]].
   */
  def value: A = _currentValue

  /**
   * Sets the value of this [[Setting]].
   *
   * @param value                     The new value to be set.
   * @throws SettingValidationError   Thrown if the validator defined for the setting does not accept the proposed new value.
   */
  def value_=(value: A): Unit = {
    validator(initialValue).foreach {
      reason => throw new SettingValidationError(key, reason)
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
