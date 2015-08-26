package aalto.smcl.bitmaps.operations


import aalto.smcl.bitmaps.DefaultBackground
import aalto.smcl.common.GS
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
  def render(destination: PlatformBitmapBuffer): Unit = {
    val filteredBitmap = getOrCreateStaticBuffer(destination)

    val ds = destination.drawingSurface()
    ds.clearUsing(GS.colorFor(DefaultBackground), useSourceColorLiterally = true)
    ds.drawBitmap(filteredBitmap)
  }

}
