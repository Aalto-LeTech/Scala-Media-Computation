package aalto.smcl.platform


import java.awt.image.BufferedImage

import aalto.smcl.SMCL




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[platform] object BitmapUtils {

  SMCL.performInitialization()


  /**
   *
   *
   * @param source
   * @return
   */
  def deepCopy(source: BufferedImage): BufferedImage =
    new BufferedImage(
      source.getColorModel,
      source.copyData(null),
      source.isAlphaPremultiplied,
      null)

}
