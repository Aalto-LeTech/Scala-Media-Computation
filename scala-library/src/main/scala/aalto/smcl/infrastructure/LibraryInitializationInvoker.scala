package aalto.smcl.infrastructure


import aalto.smcl.infrastructure.PackageInitializationPhase._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
//noinspection ScalaDeprecation
@SuppressWarnings(Array("deprecation"))
trait LibraryInitializationInvoker extends DelayedInit {

  /**
   *
   *
   * @param body
   */
  def delayedInit(body: => Unit): Unit = {
    LibraryInitializer.performInitialization(this, Early)

    body

    LibraryInitializer.performInitialization(this, Late)
  }

}
