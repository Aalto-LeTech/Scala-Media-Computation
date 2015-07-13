package aalto.smcl.images

import java.awt.{
  Color => JColor,
  Graphics2D => JGraphics2D
}
import java.awt.image.{ BufferedImage => JBufferedImage }
import aalto.smcl.images.operations._

/**
 * Provides a way to add bitmap operations into [[OperableBitmap]] instances.
 *
 * @author Aleksi Lukkarinen
 */
object BitmapOps {

  /**
   * Adds a [[Clear]] operation to a given [[OperableBitmap]].
   */
  def clear(bmp: OperableBitmap, colorOption: Option[Int] = None): Unit = {
    bmp.apply(Clear(colorOption))
  }

}
