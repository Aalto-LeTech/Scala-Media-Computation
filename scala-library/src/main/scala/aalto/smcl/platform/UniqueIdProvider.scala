package aalto.smcl.platform


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object UniqueIdProvider {

  /** */
  def newId(): String = java.util.UUID.randomUUID().toString

}
