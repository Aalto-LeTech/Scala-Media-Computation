package aalto.smcl.infrastructure


import scala.swing.Font




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
class FontProvider() {

  /**
   *
   *
   * @return
   */
  def availableFonts(): Seq[Font] =
    new UIProvider().awtGraphEnv.getAllFonts.toSeq

}
