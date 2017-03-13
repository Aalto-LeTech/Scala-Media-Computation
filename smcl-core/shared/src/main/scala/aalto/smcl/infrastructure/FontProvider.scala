package aalto.smcl.infrastructure


import java.awt.Font




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
