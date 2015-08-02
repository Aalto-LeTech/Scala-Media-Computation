package aalto.smcl.images


import aalto.smcl.images.immutable.primitives.Bitmap
import Bitmap.ViewerUpdateStyle
import Bitmap.ViewerUpdateStyle.UpdateViewerPerDefaults
import aalto.smcl.images.immutable._
import aalto.smcl.images.operations._




/**
 * Ensures that a bitmap can be operated with using [[AbstractOperation]] with [[AbstractOperation]] instances.
 *
 * @author Aleksi Lukkarinen
 */
private[images] trait OperableBitmap {

  /**
   * Returns a new instance of this [[Bitmap]] with the [[AbstractOperation]] applied to it.
   *
   * @param operation
   * @param viewerHandling
   * @return
   */
  private[images] def apply(
      operation: AbstractSingleSourceOperation,
      viewerHandling: ViewerUpdateStyle.Value = UpdateViewerPerDefaults): Bitmap

}
