package aalto.smcl.images.viewer


import java.awt.image.{BufferedImage => JBufferedImage}
import java.awt.{Color => JColor, Graphics2D => JGraphics2D}

import scala.swing._

import aalto.smcl.images.ImageUtils




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[images] class ImageDisplayPanel extends Panel {

  /** Buffer for the image to be displayed. */
  private var _buffer: JBufferedImage = new JBufferedImage(1, 1, JBufferedImage.TYPE_INT_ARGB)

  /**
   *
   */
  override def paintComponent(g: JGraphics2D) = {
    super.paintComponent(g)

    g.drawImage(_buffer, null, 0, 0)
  }

  /**
   *
   */
  def updateImageBuffer(newBuffer: JBufferedImage) = {
    _buffer = ImageUtils.deepCopy(newBuffer)

    repaint()
  }

}
