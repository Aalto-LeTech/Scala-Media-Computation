package aalto.smcl.infrastructure.awt


import java.awt.{Font => LowLevelFont}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[infrastructure]
trait AwtFontProvider {

  /**
   *
   *
   * @return
   */
  def availableFonts: Seq[LowLevelFont]

}
