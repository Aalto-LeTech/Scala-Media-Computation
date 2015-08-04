package aalto.smcl.platform


import java.awt.image.{BufferedImage => JBufferedImage, ColorModel => JColorModel, WritableRaster => JWritableRaster}




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
  def deepCopy(source: JBufferedImage): JBufferedImage =
    new JBufferedImage(
      source.getColorModel,
      source.copyData(null),
      source.isAlphaPremultiplied,
      null)

}
