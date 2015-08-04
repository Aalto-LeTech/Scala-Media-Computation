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
  def availableFonts(): Seq[Font] =
    UIProvider.awtGraphEnv.getAllFonts.toSeq

}
