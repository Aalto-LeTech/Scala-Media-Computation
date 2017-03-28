package aalto.smcl.infrastructure




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
trait JvmUniqueIdProvider {

  /**
   *
   *
   * @return
   */
  def newId: String

}
