package aalto.smcl.bitmaps.fullfeatured


import aalto.smcl.colors.rgb
import aalto.smcl.infrastructure.{DrawingSurfaceAdapter, Identity}
import aalto.smcl.modeling.d2.{Bounds, Pos}




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
case class Point private(
    override val identity: Identity,
    xInPixels: Double,
    yInPixels: Double,
    color: rgb.Color)
    extends VectorGraphic {

  /** Position of this object. */
  lazy val position: Pos = Pos(xInPixels, yInPixels)

  /** */
  lazy val boundary: Option[Bounds] =
    Some(Bounds(position, position))

  /** Tells if this [[Point]] can be rendered on a bitmap. */
  def isRenderable: Boolean = true

  /**
   *
   *
   * @param drawingSurface
   */
  def renderOn(
      drawingSurface: DrawingSurfaceAdapter,
      position: Pos): Unit = {

    drawingSurface.drawPoint(
      position.xInPixels, position.yInPixels, color)
  }

  /**
   *
   *
   * @param newXInPixels
   * @param newYInPixels
   *
   * @return
   */
  def copy(
      newXInPixels: Double = xInPixels,
      newYInPixels: Double = yInPixels,
      newColor: rgb.Color = color): Point = {

    Point(newXInPixels, newYInPixels, newColor)
  }

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toString: String = {
    s"Point(x: $xInPixels px, y: $yInPixels px)"
  }

}
