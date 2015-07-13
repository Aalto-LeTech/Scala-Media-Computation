package aalto.smcl.images

import java.awt.{ Graphics2D => JGraphics2D }
import aalto.smcl.images.operations.BitmapOperation

/**
 * Ensures that a bitmap can be operated with using [[BitmapOperation]] instances.
 *
 * @author Aleksi Lukkarinen
 */
private[images] trait OperableBitmap {

  /**
   * Applies a bitmap operation to a bitmap.
   */
  def apply(operation: BitmapOperation): Unit

}
