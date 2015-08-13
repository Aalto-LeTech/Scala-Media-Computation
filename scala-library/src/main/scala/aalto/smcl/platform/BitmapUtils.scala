package aalto.smcl.platform


import java.awt.image.BufferedImage




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[platform] object BitmapUtils {

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
