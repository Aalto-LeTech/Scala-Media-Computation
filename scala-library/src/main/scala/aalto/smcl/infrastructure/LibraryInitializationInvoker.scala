package aalto.smcl.infrastructure


import aalto.smcl.infrastructure.PackageInitializationPhase._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait LibraryInitializationInvoker extends DelayedInit {

  /**
   *
   *
   * @param body
   */
  def delayedInit(body: => Unit): Unit = {
    LibraryInitializer.performInitialization(Early)

    body

    LibraryInitializer.performInitialization(Late)
  }

}
