package aalto.smcl.infrastructure.exceptions

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLInvalidPercentageError private[smcl](private val detailMessage: String, cause: Throwable)
  extends RuntimeException(detailMessage, cause) {

  /**
   *
   *
   * @param detailMessage
   * @return
   */
  def this(detailMessage: String) = this(detailMessage, null)

}
