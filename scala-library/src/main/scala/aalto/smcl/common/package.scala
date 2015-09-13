package aalto.smcl


import aalto.smcl.infrastructure.LibraryInitializationInvoker




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object common extends LibraryInitializationInvoker {

  /** */
  lazy val Fonts: Fonts = new Fonts()

}
