package aalto.smcl.infrastructure




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class SettingValidatorFactory() {

  /** */
  val EmptyValidator = null

  /**
   *
   *
   * @param testFailingIfTrue
   * @param errorMessage
   * @tparam SettingType
   * @return
   */
  def conditionFalseValidator[SettingType](
    testFailingIfTrue: SettingType => Boolean,
    errorMessage: String): SettingType => Option[Throwable] = {
    {
      value =>
        if (testFailingIfTrue(value)) Option(new IllegalArgumentException(errorMessage))
        else None
    }
  }

  /**
   *
   *
   * @param errorMessage
   * @tparam SettingType
   * @return
   */
  def IsNullValidator[SettingType](errorMessage: String): SettingType => Option[Throwable] =
    conditionFalseValidator({
      _ == null
    }, errorMessage)

}
