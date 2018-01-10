/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package smcl.viewers.bitmaps.jvmawt


import java.awt.{RenderingHints, Graphics2D => JGraphics2D}

import scala.swing._
import scala.util.Try

import smcl.pictures.fullfeatured.Bitmap
import smcl.infrastructure.jvmawt._
import smcl.modeling.AffineTransformation




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[jvmawt]
class ImageDisplayPanel
    extends Panel {

  /** The bitmap to be displayed. */
  private var _bitmapOption: Option[Bitmap] = None

  /** */
  private var _zoomFactor: ZoomFactor = ZoomFactor.Identity

  /** */
  private var _zoomingTransformation: AffineTransformation =
    AffineTransformation.Identity

  /**
   *
   *
   * @param lowLevelGraphics2D
   */
  override def paintComponent(lowLevelGraphics2D: JGraphics2D): Unit = {
    super.paintComponent(lowLevelGraphics2D)

    val bufferRetrievalTry =
      Try(_bitmapOption.get.toRenderedRepresentation.asInstanceOf[AWTBitmapBufferAdapter].awtBufferedImage)
    if (bufferRetrievalTry.isFailure)
      return

    val drawingSurface = lowLevelGraphics2D.create().asInstanceOf[Graphics2D]
    try {
      drawingSurface.setRenderingHint(
        RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BICUBIC)

      drawingSurface.drawImage(
        bufferRetrievalTry.get,
        _zoomingTransformation.toAWTAffineTransform,
        null)
    }
    finally {
      drawingSurface.dispose()
    }
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


    cursor = ViewerManager.WaitCursor
    _zoomFactor = value
    val result = Try(updateView()) // TODO: Error processing, here as well as elsewhere...
    if (result.isFailure)
      println(result)
    cursor = ViewerManager.DefaultCursor
  }

  /**
   *
   *
   * @param adjuster
   *
   * @return
   */
  def adjustZoomWith(adjuster: ZoomFactor => ZoomFactor): ZoomFactor = {
    require(adjuster != null, "Internal error: Function cannot be null.")

    val newZoomFactor = adjuster(_zoomFactor)
    require(newZoomFactor != null, "Internal error: Zoom factor returned by adjuster function cannot be null.")

    zoomFactor = newZoomFactor

    newZoomFactor
  }

  /**
   *
   *
   * @param bitmap
   */
  def updateImageBuffer(bitmap: Bitmap): Unit = {
    require(bitmap != null, "Internal error: Bitmap to be used for update cannot be null.")

    _bitmapOption = Option(bitmap)

    cursor = ViewerManager.WaitCursor
    val result = Try(updateView()) // TODO: Error processing, here as well as elsewhere...
    if (result.isFailure)
      println(result)
    cursor = ViewerManager.DefaultCursor
  }

  /**
   *
   */
  def updateView(): Unit = {
    if (_bitmapOption.isEmpty)
      throw new IllegalStateException(
        "Internal error: Bitmap to be displayed has to be set before calling this method.")

    val bitmapSize = _bitmapOption.get.sizeInPixels.toIntTuple
    val newSize = _zoomFactor.scaleToDimension(
      new java.awt.Dimension(bitmapSize._1, bitmapSize._2))

    if (minimumSize != newSize) {
      minimumSize = newSize
      preferredSize = newSize
      maximumSize = newSize
    }

    _zoomingTransformation = _zoomFactor.asAffineTransformation

    revalidate()
    repaint()
  }

}
