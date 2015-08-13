package aalto.smcl.bitmaps.operations


import scala.ref.WeakReference

import aalto.smcl.common._
import aalto.smcl.platform.{PlatformAffineTransform, PlatformBitmapBuffer}




/**
 * Operation to flip a bitmap diagonally.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class FlipDiagonally()
    extends AbstractOperation with RenderableOperation with Immutable {

  /** Information about this [[RenderableOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map())

  /** Rendering buffer for this operation. */
  private[this] var _renderingBuffer: WeakReference[PlatformBitmapBuffer] =
    WeakReference[PlatformBitmapBuffer](null)


  /**
   * Flips the given bitmap diagonally.
   *
   * @param destination
   */
  override def render(destination: PlatformBitmapBuffer): Unit = {
    val bufferedFlip: PlatformBitmapBuffer = _renderingBuffer.get getOrElse {
      val transformation =
        PlatformAffineTransform.forDiagonalFlipOf(
          destination.widthInPixels,
          destination.heightInPixels)

      val newBuffer = destination.createTransfomedVersionWith(transformation)
      _renderingBuffer = WeakReference(newBuffer)

      newBuffer
    }

    destination.drawingSurface().drawBitmap(bufferedFlip)
  }

}
