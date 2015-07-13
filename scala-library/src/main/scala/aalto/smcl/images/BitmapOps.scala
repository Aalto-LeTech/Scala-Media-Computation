package aalto.smcl.images

import java.awt.{
  Color => JColor,
  Graphics2D => JGraphics2D
}
import java.awt.image.{ BufferedImage => JBufferedImage }

/**
 * Implements a number of operations performable to any [[OperableBitmap]].
 * 
 * @author Aleksi Lukkarinen
 */
object BitmapOps {
  
  /**
   *
   */
  def clear(bmp: OperableBitmap, colorOption: Option[Int] = None): Unit = {
    val g = bmp.graphics2D
    val oldColor = g.getColor
    
    g.setPaint(new JColor(colorOption getOrElse 0xFFFFFFFF, true))
    g.fillRect(0, 0, g.getClipBounds.width, g.getClipBounds.height)
    g.setColor(oldColor)
  }

}