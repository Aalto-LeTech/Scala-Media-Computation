package aalto.smcl.infrastructure


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class UniqueIdProvider() {

  /** */
  def newId(): String = java.util.UUID.randomUUID().toString

}
