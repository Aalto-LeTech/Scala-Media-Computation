package aalto.smcl.infrastructure


import aalto.smcl.infrastructure.PackageInitializationPhase._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
//noinspection ScalaDeprecation
@SuppressWarnings(Array("deprecation"))
trait SMCLInitializationInvoker
  extends DelayedInit {


  /**
   *
   *
   * @param body
   */
  override def delayedInit(body: => Unit): Unit = {
    LibraryInitializer.performInitialization(this, Early)

    body

    LibraryInitializer.performInitialization(this, Late)
  }

}
