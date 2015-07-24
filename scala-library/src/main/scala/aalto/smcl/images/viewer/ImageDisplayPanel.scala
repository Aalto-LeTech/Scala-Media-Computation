package aalto.smcl.images.viewer


import java.awt.geom.AffineTransform
import java.awt.image.{BufferedImage => JBufferedImage}
import java.awt.{Color => JColor, Graphics2D => JGraphics2D}

import scala.swing._

import aalto.smcl.images.immutable.Bitmap




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[images] class ImageDisplayPanel extends Panel {

  /** The bitmap to be displayed. */
  private var _bitmapOption: Option[Bitmap] = None

  /** */
  private var _zoomFactor: ZoomFactor = ZoomFactor()

  /** */
  private var _affineTransformation: AffineTransform = new AffineTransform()

  /**
   *
   */
  override def paintComponent(g: JGraphics2D) = {
    super.paintComponent(g)

    _bitmapOption.get.renderOnto(g, _affineTransformation)
  }

  /**
   *
   */
  def updateImageBuffer(bitmap: Bitmap) = {
    require(bitmap != null, "Internal error: Bitmap to be used for update cannot be null.")

    _bitmapOption = Option(bitmap)

    updateView()
  }

  /**
   *
   */
  private def updateView(): Unit = {
    if (_bitmapOption.isEmpty)
      throw new IllegalStateException(
        "Internal error: Bitmap to be displayed has to be set before calling this method.")

    val newSize = _zoomFactor.scale(_bitmapOption.get.sizeInPixels)
    if (minimumSize != newSize) {
      minimumSize = newSize
      preferredSize = newSize
      maximumSize = newSize
    }

    _affineTransformation = _zoomFactor.asAffineTransformation

    revalidate()
    repaint()
  }

  /**
   *
   *
   * @return
   */
  def zoomFactor: ZoomFactor = _zoomFactor

  /**
   *
   *
   * @param value
   */
  def zoomFactor_=(value: ZoomFactor): Unit = {
    _zoomFactor = value

    updateView()
  }

}
