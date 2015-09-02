package aalto.smcl.platform


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] class UniqueIdProvider private[platform]() {

  /** */
  def newId(): String = java.util.UUID.randomUUID().toString

}
