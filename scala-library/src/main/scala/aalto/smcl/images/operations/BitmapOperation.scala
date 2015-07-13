package aalto.smcl.images.operations

import java.awt.{ Graphics2D => JGraphics2D }
import aalto.smcl.images._

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait BitmapOperation {

  /**
   * Applies this operation to a bitmap.
   */
  def apply(bmp: OperableBitmap, drawingSurface: JGraphics2D, widthInPixels: Int, heightInPixels: Int): Unit

  /**
   * Returns this operation as a formal string, which could be used to assess exercises.
   */
  def toToken(): String

}
