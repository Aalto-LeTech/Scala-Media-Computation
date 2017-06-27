package aalto.smcl.bitmaps.fullfeatured


import aalto.smcl.colors.rgb
import aalto.smcl.geometry.Transformer
import aalto.smcl.geometry.d2.Pos
import aalto.smcl.infrastructure.{DrawingSurfaceAdapter, Identity}




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
      xInPixels: Double,
      yInPixels: Double,
      color: rgb.Color): Point = {

    new Point(
      Identity(),
      xInPixels,
      yInPixels,
      color)
  }

  /**
   *
   *
   * @param position
   * @param color
   */
  def apply(
      position: Pos,
      color: rgb.Color): Point = {

    Point(
      position.xInPixels,
      position.yInPixels,
      color)
  }

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class Point private(
    override val identity: Identity,
    val xInPixels: Double,
    val yInPixels: Double,
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
  override def renderOn(drawingSurface: DrawingSurfaceAdapter): Unit = {
    drawingSurface.drawPoint(xInPixels, yInPixels, color)
  }

  /**
   *
   *
   * @return
   */
  override def toBitmap: Bmp = {
    Bmp(1, 1)
  }

  /**
   * Rotates this object around a given point of the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  override
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): Point = {

    val pos = Transformer.rotate(position, angleInDegrees, centerOfRotation)

    Point(
      pos.xInPixels,
      pos.yInPixels,
      color)
  }

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  override
  def moveBy(offsets: Double*): Point = {
    require(
      offsets.length == 2,
      s"Exactly two offsets has to be given (given: ${offsets.length})")

    Point(
      xInPixels + offsets(0),
      yInPixels + offsets(1),
      color)
  }

}
