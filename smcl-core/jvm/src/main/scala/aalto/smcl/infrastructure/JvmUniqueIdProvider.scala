package aalto.smcl.infrastructure




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class JvmUniqueIdProvider() {

  /** */
  def newId(): String = java.util.UUID.randomUUID().toString

}
