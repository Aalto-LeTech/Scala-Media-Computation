package aalto.smcl.images

import java.awt.{ Graphics2D => JGraphics2D }
import aalto.smcl.images.operations.BitmapOperation

/**
 * Ensures that the Java's `Graphics2D` API is available for use.
 *
 * @author Aleksi Lukkarinen
 */
private[images] trait OperableBitmap {

  /**
   * Applies a bitmap operation to a bitmap.
   */
  def applyOperation(operation: BitmapOperation): Unit

}
