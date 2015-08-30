package aalto.smcl.platform


import scala.swing.Font

import aalto.smcl.SMCL




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object FontProvider {

  SMCL.performInitialization()


  /**
   *
   *
   * @return
   */
  def availableFonts(): Seq[Font] =
    UIProvider.awtGraphEnv.getAllFonts.toSeq

}
