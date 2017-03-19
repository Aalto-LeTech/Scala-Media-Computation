package aalto.smcl.infrastructure


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class DefaultJvmUniqueIdProvider() extends JvmUniqueIdProvider {

  /**
   *
   *
   * @return
   */
  def newId: String = java.util.UUID.randomUUID().toString

}
