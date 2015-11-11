package aalto.smcl.colors


import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLInvalidColorWeightCombinationError private[smcl](private val detailMessage: String, cause: Throwable)
  extends RuntimeException(detailMessage, cause)
  with SMCLInitializationInvoker {

  
  /**
   *
   *
   * @param detailMessage
   * @return
   */
  def this(detailMessage: String) = this(detailMessage, null)

}
