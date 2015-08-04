package aalto.smcl.bitmaps


import java.awt.image.{BufferedImage => JBufferedImage, ColorModel => JColorModel, WritableRaster => JWritableRaster}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] object ImageUtils {

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
