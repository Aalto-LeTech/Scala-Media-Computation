package aalto.smcl.bitmaps.fullfeatured


import aalto.smcl.colors.rgb
import aalto.smcl.infrastructure.{DrawingSurfaceAdapter, Identity}
import aalto.smcl.modeling.d2.Pos




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
    override val xInPixels: Double,
    override val yInPixels: Double,
    val color: rgb.Color)
    extends Pos(xInPixels, yInPixels)
            with VectorGraphic {

  /** */
  def position: Pos = this

  /** Tells if this [[Point]] can be rendered on a bitmap. */
  override
  def isRenderable: Boolean = true

  /**
   *
   *
   * @param drawingSurface
   */
  override def renderOn(
      drawingSurface: DrawingSurfaceAdapter,
      position: Pos): Unit = {

    drawingSurface.drawPoint(
      position.xInPixels, position.yInPixels, color)
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
