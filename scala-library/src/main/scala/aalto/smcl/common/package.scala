package aalto.smcl


import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
  *
  *
  * @author Aleksi Lukkarinen
  */
package object common extends SMCLInitializationInvoker {

  /** A dummy variable needed to enforce the library initialization. */
  private val __smcl_initialization_ensuring_dummy_variable__ = null


  /** */
  lazy val Fonts: Fonts = new Fonts()

}
