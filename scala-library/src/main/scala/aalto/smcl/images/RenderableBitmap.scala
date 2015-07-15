package aalto.smcl.images

import java.awt.{ Graphics2D => JGraphics2D }

/**
 * Ensures that a bitmap can be rendered onto drawing surfaces.
 *
 * @author Aleksi Lukkarinen
 */
trait RenderableBitmap {

  /**
   * Renders this [[RenderableBitmap]] onto a drawing surface.
   */
  def render(drawingSurface: JGraphics2D, x: Int, y: Int): Unit

}
