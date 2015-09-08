package aalto.smcl.init


import aalto.smcl.SMCL




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait InitializableModule {

  /**
   *
   *
   * @param body
   */
  def delayedInit(body: => Unit): Unit = {

    import aalto.smcl.init.ModuleInitializationPhase._

    SMCL.performInitialization(Early)
    SMCL.performInitialization(Late)

    body
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


