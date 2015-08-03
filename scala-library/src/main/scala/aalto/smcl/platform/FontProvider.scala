package aalto.smcl.platform


import scala.swing.Font




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object FontProvider {

  /**
   *
   *
   * @return
   */
  def availableFonts(): Array[Font] =
    UIProvider.awtGraphEnv.getAllFonts

}
