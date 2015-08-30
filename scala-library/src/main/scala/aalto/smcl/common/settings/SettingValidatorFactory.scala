package aalto.smcl.common.settings


import aalto.smcl.SMCL




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object SettingValidatorFactory {

  SMCL.performInitialization()


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
  def ConditionFalseValidator[SettingType](
    testFailingIfTrue: SettingType => Boolean,
    errorMessage: String): SettingType => Option[Throwable] = {

    {value =>
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
    ConditionFalseValidator({
      _ == null
    }, errorMessage)

}
