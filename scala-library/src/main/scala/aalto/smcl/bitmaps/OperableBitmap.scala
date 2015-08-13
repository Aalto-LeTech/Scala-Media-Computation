package aalto.smcl.bitmaps


import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.bitmaps.immutable.primitives.Bitmap.ViewerUpdateStyle
import aalto.smcl.bitmaps.immutable.primitives.Bitmap.ViewerUpdateStyle.UpdateViewerPerDefaults
import aalto.smcl.bitmaps.operations._




/**
 * Ensures that a bitmap can be operated with using [[AbstractOperation]] with [[AbstractOperation]] instances.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] trait OperableBitmap {

  /**
   * Returns a new instance of this [[Bitmap]] with the [[AbstractOperation]] applied to it.
   *
   * @param operation
   * @param viewerHandling
   * @return
   */
  private[bitmaps] def apply(
    operation: Renderable,
    viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap

}
