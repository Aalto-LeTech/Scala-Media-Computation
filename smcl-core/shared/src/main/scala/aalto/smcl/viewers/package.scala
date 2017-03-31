package aalto.smcl


import aalto.smcl.infrastructure.Displayable




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object viewers {

  /**
   *
   *
   * @param resource
   */
  def display(resource: Displayable): Unit =
    DefaultViewerApplicationClient.display(resource)

  /**
   *
   */
  def closeAllViewersWithoutSaving(): Unit =
    DefaultViewerApplicationClient.closeAllViewersWithoutSaving()

}
