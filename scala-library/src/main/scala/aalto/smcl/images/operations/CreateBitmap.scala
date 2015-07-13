package aalto.smcl.images.operations

import java.awt.{
  Color => JColor,
  Graphics2D => JGraphics2D
}
import java.awt.image.{ BufferedImage => JBufferedImage }
import aalto.smcl.images.OperableBitmap

/**
 * An operation to create a bitmap of a given size.
 *
 * @author Aleksi Lukkarinen
 */
case class CreateBitmap(widthInPixels: Int, heightInPixels: Int) extends BitmapOperation {

  /**
   * Does nothing.
   */
  override def apply(
    bmp: OperableBitmap,
    drawingSurface: JGraphics2D,
    widthInPixels: Int,
    heightInPixels: Int) = {

    // Does nothing.
  }

  /**
   * Returns a string token corresponding this bitmap operation.
   */
  override def toToken(): String = "[CreateBitmap; width: ${widthInPixels}px; height: ${heightInPixels}px]"

}
