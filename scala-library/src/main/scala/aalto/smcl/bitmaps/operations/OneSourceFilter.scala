package aalto.smcl.bitmaps.operations


import aalto.smcl.platform.PlatformBitmapBuffer




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait OneSourceFilter extends Renderable with Buffered {
  this: AbstractOperation =>

  /**
   * Applies this convolution filter operation to the given bitmap.
   *
   * @param destination
   */
  def render(destination: PlatformBitmapBuffer): Unit =
    destination.drawingSurface().drawBitmap(getOrCreateStaticBuffer(destination))
}
