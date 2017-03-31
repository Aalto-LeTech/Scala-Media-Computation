package aalto.smcl.viewers


import aalto.smcl.infrastructure.Displayable




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
trait ViewerApplicationClient {

  /**
   *
   *
   * @param resource
   */
  def display(resource: Displayable): Unit

  /**
   *
   *
   */
  def closeAllViewersWithoutSaving(): Unit

}
