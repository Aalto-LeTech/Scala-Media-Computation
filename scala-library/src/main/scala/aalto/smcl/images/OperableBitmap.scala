package aalto.smcl.images

import aalto.smcl.images.immutable.Bitmap
import aalto.smcl.images.operations.BitmapOperation

/**
 * Ensures that a bitmap can be operated with using [[BitmapOperation]] instances.
 *
 * @author Aleksi Lukkarinen
 */
private[images] trait OperableBitmap {

  /**
   * Returns a new instance of this [[Bitmap]] with the [[BitmapOperation]] applied to it.
   */
  def apply(operation: BitmapOperation): Bitmap

}
