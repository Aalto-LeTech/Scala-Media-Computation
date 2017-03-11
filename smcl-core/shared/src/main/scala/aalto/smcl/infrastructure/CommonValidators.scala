package aalto.smcl.infrastructure

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class CommonValidators() {

  /** */
  val MinimumPercentage: Double = 0.0

  /** */
  val MaximumPercentage: Double = 100.0

  /** */
  val MinimumZeroToOneFactor: Double = 0.0

  /** */
  val MaximumZeroToOneFactor: Double = 1.0


  /**
   *
   *
   * @param valueCandidate
   * @return
   */
  @inline
  def percentIsInRange(valueCandidate: Double): Boolean =
    valueCandidate >= MinimumPercentage && valueCandidate <= MaximumPercentage

  /**
   *
   *
   * @param valueCandidate
   * @return
   */
  @inline
  def zeroToOneFactorIsInRange(valueCandidate: Double): Boolean =
    valueCandidate >= MinimumZeroToOneFactor && valueCandidate <= MaximumZeroToOneFactor

  /**
   *
   *
   * @param valueCandidate
   * @param percentageNameOption
   *
   * @throws SMCLInvalidPercentageError
   */
  @inline
  def validatePercentage(valueCandidate: Double, percentageNameOption: Option[String]): Unit =
    if (!percentIsInRange(valueCandidate)) {
      val strError = percentageNameOption.fold("Given")({_.capitalize}) +
        s" percentage must be between $MinimumPercentage and $MaximumPercentage (was $valueCandidate)"

      throw new SMCLInvalidPercentageError(strError)
    }

  /**
   *
   *
   * @param valueCandidate
   * @param factorNameOption
   *
   * @throws SMCLInvalidZeroToOneFactorError
   */
  @inline
  def validateZeroToOneFactor(valueCandidate: Double, factorNameOption: Option[String]): Unit =
    if (!percentIsInRange(valueCandidate)) {
      val strError = factorNameOption.fold("Given")({_.capitalize}) +
        s" factor must be between $MinimumZeroToOneFactor and $MaximumZeroToOneFactor (was $valueCandidate)"

      throw new SMCLInvalidZeroToOneFactorError(strError)
    }

}
