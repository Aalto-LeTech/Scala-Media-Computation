package aalto.smcl.images


import java.awt.image.{BufferedImage => JBufferedImage}
import java.awt.{Graphics2D => JGraphics2D}




/**
 * Ensures that a bitmap can be rendered onto drawing surfaces.
 *
 * @author Aleksi Lukkarinen
 */
trait RenderableBitmap {

  /**
   * Renders this [[RenderableBitmap]] onto a drawing surface.
   */
  def renderOnto(drawingSurface: JGraphics2D, x: Int, y: Int): Unit

  /**
   * Returns an instance of Java's `BufferedImage` representing this [[RenderableBitmap]].
   */
  def toRenderedRepresentation: JBufferedImage

}
