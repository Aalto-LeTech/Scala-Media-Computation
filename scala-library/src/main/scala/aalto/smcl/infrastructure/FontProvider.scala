package aalto.smcl.infrastructure


import scala.swing.Font




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] class FontProvider private[infrastructure]() {

  /**
   *
   *
   * @return
   */
  def availableFonts(): Seq[Font] =
    UIProvider.awtGraphEnv.getAllFonts.toSeq

}
