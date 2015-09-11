package aalto.smcl.infrastructure


import aalto.smcl.SMCL
import aalto.smcl.infrastructure.ModuleInitializationPhase._




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] trait InitializableModule {

  /**
   *
   *
   * @param body
   */
  def delayedInit(body: => Unit): Unit = {
    body

    SMCL.performInitialization(Early)
    SMCL.performInitialization(Late)
  }

}


//trait Initializable[T] extends T {
//  def early() : T
//
//  early()
//
//}
//
//class A extends Initializable[A] {
//
//
//  def early(): Unit = {
//
//  }
//
//}


