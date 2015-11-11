package aalto.smcl.infrastructure


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
final class SMCLUnexpectedInternalError private[smcl](private val detailMessage: String, cause: Throwable)
  extends {

    val message: String = {
      val sb = new StringBuilder(200)

      sb ++= "Unfortunately an unexpected internal error occurred"

      if (detailMessage.nonEmpty)
        sb ++= s": $detailMessage"

      sb ++= "."

      sb.toString()
    }

  }
  with RuntimeException(message, cause) {

  /**
   *
   *
   * @param detailMessage
   * @return
   */
  def this(detailMessage: String) = this(detailMessage, null)

}
