package aalto.smcl.images


import java.awt.image.{BufferedImage => JBufferedImage, ColorModel => JColorModel, WritableRaster => JWritableRaster}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[images] object ImageUtils {

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
