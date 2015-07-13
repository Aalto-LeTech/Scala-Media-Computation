package aalto.smcl.images.operations

import java.awt.{
  Color => JColor,
  Graphics2D => JGraphics2D
}
import java.awt.image.{ BufferedImage => JBufferedImage }
import aalto.smcl.images.OperableBitmap

/**
 * Operation to clear a bitmap with a given color. If a color is not given, a pure white will be used.
 *
 * @author Aleksi Lukkarinen
 */
case class Clear(private val colorOption: Option[Int] = None) extends BitmapOperation {

  /** The color with which to clear bitmaps. */
  private[this] val color = new JColor(colorOption getOrElse 0xFFFFFFFF, true)

  /**
   * Clears the given bitmap with the given color.
   *
   * @param bmp           the bitmap to be operated with
   * @param colorOption   the color with which the bitmap is to be cleared
   */
  override def apply(bmp: OperableBitmap) = {
    val g = bmp.graphics2D
    val oldColor = g.getColor

    g.setColor(color)
    g.fillRect(0, 0, g.getClipBounds.width, g.getClipBounds.height)
    g.setColor(oldColor)
  }

  /**
   * Returns a string token corresponding this bitmap operation.
   */
  override def toToken(): String = "[Clear; bgColor: ${_color}]"

}
