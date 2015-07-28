package aalto.smcl.common.settings


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Setting {

  /** */
  val EmptyValidator = null

}


/**
 *
 *
 * @param name
 * @param initialValue
 * @param validator
 * @tparam A
 * @author Aleksi Lukkarinen
 */
abstract class Setting[A] protected (
    val name: SettingKey,
    val initialValue: A,
    val validator: A => Option[Throwable]) extends Mutable {

  if (validator != null)
    validator(initialValue).foreach {reason => throw new SettingValidationError(name, reason)}

  /** */
  private var _currentValue: A = initialValue


  /**
   *
   *
   * @return
   */
  def value: A = _currentValue

  /**
   *
   *
   * @param value
   */
  def value_=(value: A): Unit = {
    validator(initialValue).foreach {
      reason => throw new SettingValidationError(name, reason)
    }

    _currentValue = value
  }

  /**
   *
   *
   * @return
   */
  def reset(): Unit = _currentValue = initialValue

}
