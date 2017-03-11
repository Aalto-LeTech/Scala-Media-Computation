package aalto


import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
 * Main package of the Scala Media Computation Library. Subpackages contain
 * functionality related to several areas of multimedia computation.
 *
 * @author Aleksi Lukkarinen
 */
package object smcl extends SMCLInitializationInvoker {

  /** A dummy variable needed to enforce the library initialization. */
  private val __smcl_initialization_ensuring_dummy_variable__ = null


  /** */
  lazy val SMCL: SMCLLibrary = new SMCLLibrary()

}
