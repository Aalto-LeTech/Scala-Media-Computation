package aalto.smcl


import aalto.smcl.infrastructure.LibraryInitializer




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object common {

  LibraryInitializer.performInitialization()


  /** */
  lazy val Fonts: Fonts = new Fonts()

}
