package aalto.smcl


import aalto.smcl.init.InitializableModule




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object common extends InitializableModule {

  /** */
  lazy val Fonts: Fonts = new Fonts()

}
