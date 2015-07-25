package aalto.smcl.images.viewer


import java.awt.geom.AffineTransform
import java.awt.image.{BufferedImage => JBufferedImage}
import java.awt.{Color => JColor, Graphics2D => JGraphics2D, RenderingHints}

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
  private var _zoomFactor: ZoomFactor = ZoomFactor.IDENTITY

  /** */
  private var _affineTransformation: AffineTransform = new AffineTransform()

  /**
   *
   *
   * @param g
   */
  override def paintComponent(g: JGraphics2D) = {
    super.paintComponent(g)

    g.setRenderingHint(
      RenderingHints.KEY_INTERPOLATION,
      RenderingHints.VALUE_INTERPOLATION_BICUBIC)

    _bitmapOption.get.renderOnto(g, _affineTransformation)
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
    require(value != null, "Internal error: Zoom factor cannot be null.")

    _zoomFactor = value

    updateView()
  }

  /**
   *
   *
   * @param adjuster
   */
  def adjustZoomWith(adjuster: ZoomFactor => ZoomFactor): ZoomFactor = {
    require(adjuster != null, "Internal error: Function cannot be null.")

    val newZoomFactor = adjuster(_zoomFactor)
    require(newZoomFactor != null, "Internal error: Zoom factor returned by adjuster function cannot be null.")

    _zoomFactor = newZoomFactor
    updateView()

    newZoomFactor
  }

  /**
   *
   *
   * @param bitmap
   */
  def updateImageBuffer(bitmap: Bitmap) = {
    require(bitmap != null, "Internal error: Bitmap to be used for update cannot be null.")

    _bitmapOption = Option(bitmap)

    updateView()
  }

  /**
   *
   */
  def updateView(): Unit = {
    if (_bitmapOption.isEmpty)
      throw new IllegalStateException(
        "Internal error: Bitmap to be displayed has to be set before calling this method.")

    val newSize = _zoomFactor.scaleToDimension(_bitmapOption.get.sizeInPixels)

    if (minimumSize != newSize) {
      minimumSize = newSize
      preferredSize = newSize
      maximumSize = newSize
    }

    _affineTransformation = _zoomFactor.asAffineTransformation

    revalidate()
    repaint()
  }

}
