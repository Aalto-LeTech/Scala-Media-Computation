package aalto.smcl.bitmaps.fullfeatured


import aalto.smcl.colors.rgb
import aalto.smcl.geometry.Pos
import aalto.smcl.infrastructure.Identity




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Point {


  /**
   *
   *
   * @param xInPixels
   * @param yInPixels
   */
  def apply(
      xInPixels: Int,
      yInPixels: Int,
      color: rgb.Color): Point = {

    new Point(Identity(), xInPixels, yInPixels, color)
  }

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class Point private(
    override val identity: Identity,
    val xInPixels: Int,
    val yInPixels: Int,
    override val color: rgb.Color)
    extends AbstractPoint(
      identity,
      Pos(xInPixels, yInPixels),
      color) {

  /**
   *
   *
   * @param drawingSurface
   */
  override def renderOn(drawingSurface: DrawingSurface): Unit = {
    drawingSurface.drawPoint(xInPixels, yInPixels)
  }

  /**
   * Rotates this [[Point]] around its center, i.e., just returns the object being rotated.
   *
   * @param angleInDegrees
   *
   * @return
   */
  override def rotateDegs(angleInDegrees: Double): Point = {
    this
  }

  /**
   *
   *
   * @return
   */
  override def toBitmap: Bmp = {
    Bmp(1, 1)
  }

}
