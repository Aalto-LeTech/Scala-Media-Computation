package aalto.smcl.images

import java.awt.{ Graphics2D => JGraphics2D }

/**
 * Ensures that a bitmap can be rendered onto drawing surfaces.
 *
 * @author Aleksi Lukkarinen
 */
trait RenderableBitmap {

  /**
   * Applies a bitmap operation to a bitmap.
   */
  def render(drawingSurface: JGraphics2D, x: Int, y: Int): Unit

}
