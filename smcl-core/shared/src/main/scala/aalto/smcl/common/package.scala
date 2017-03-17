package aalto.smcl


import aalto.smcl.infrastructure.PRF




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object common {

  /**  */
  lazy val Screen: DefaultScreen = new DefaultScreen(PRF.screenInformationProvider)

  /**  */
  lazy val Fonts: Fonts = new Fonts()

}
