package aalto.smcl.bitmaps.operations


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLBufferNotCreatedYetError private[smcl](private val detailMessage: String, cause: Throwable)
    extends RuntimeException(detailMessage, cause) {

  /**
   *
   *
   * @param detailMessage
   * @return
   */
  def this(detailMessage: String) = this(detailMessage, null)

}
