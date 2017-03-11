package aalto.smcl.infrastructure.awt

import java.awt.{Font => LowLevelFont}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[infrastructure]
class FontProvider {

  /**
   *
   *
   * @return
   */
  def availableFonts(): Seq[LowLevelFont] = UIProvider.awtGraphEnv.getAllFonts.toSeq

}
