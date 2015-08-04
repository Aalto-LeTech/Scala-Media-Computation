package aalto.smcl.common.settings


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object SettingValidatorFactory {

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
  def IsNullValidator[SettingType](
      errorMessage: String): SettingType => Option[Throwable] = {

    {value =>
      if (value == null) Option(new IllegalArgumentException(errorMessage))
      else None
    }
  }

}
