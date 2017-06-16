package aalto.smcl.bitmaps.fullfeatured


import aalto.smcl.infrastructure.Identity




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Point {

  /** The origo of a 2-dimensional coordinate system. */
  val Origo = new Point(Identity(), 0, 0)

  /**
   *
   *
   * @param xInPixels
   * @param yInPixels
   */
  def apply(
      xInPixels: Int,
      yInPixels: Int): Point = {

    new Point(Identity(), xInPixels, yInPixels)
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
    val yInPixels: Int)
    extends PointBase(identity, Seq(xInPixels, yInPixels)) {

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

}
