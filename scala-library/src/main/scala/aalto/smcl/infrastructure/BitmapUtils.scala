package aalto.smcl.infrastructure


import java.awt.image.BufferedImage




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[infrastructure] object BitmapUtils {

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
