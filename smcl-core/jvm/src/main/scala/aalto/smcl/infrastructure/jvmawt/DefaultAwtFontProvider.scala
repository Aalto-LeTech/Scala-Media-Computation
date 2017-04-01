package aalto.smcl.infrastructure.jvmawt


import java.awt.{Font => LowLevelFont}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[infrastructure]
class DefaultAwtFontProvider extends AwtFontProvider {

  /**
   *
   *
   * @return
   */
  override def availableFonts: Seq[LowLevelFont] = UIProvider.awtGraphEnv.getAllFonts.toSeq

}
