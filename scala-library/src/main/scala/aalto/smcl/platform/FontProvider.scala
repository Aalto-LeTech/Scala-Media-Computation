package aalto.smcl.platform


import scala.swing.Font




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] class FontProvider private[platform]() {

  /**
   *
   *
   * @return
   */
  def availableFonts(): Seq[Font] =
    UIProvider.awtGraphEnv.getAllFonts.toSeq

}
