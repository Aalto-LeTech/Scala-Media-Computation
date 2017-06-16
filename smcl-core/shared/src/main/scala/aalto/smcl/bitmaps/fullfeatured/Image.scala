package aalto.smcl.bitmaps.fullfeatured


import aalto.smcl.infrastructure.Identity




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class Image(
    override val identity: Identity,
    val widthInPixels: Int,
    val heightInPixels: Int,
    val position: Point)
    extends ImageElement(identity)
            with HasPosition[Point]
            with Has2DBoundary {

  // TODO: Tarkistukset

  /** */
  val boundary: Option[Boundary] =
    Boundary(Point.Origo, Point(widthInPixels, heightInPixels))

  /** */
  val isRenderable: Boolean =
    widthInPixels > 0 && heightInPixels > 0

  /**
   *
   *
   * @param drawingSurface
   */
  override def renderOn(drawingSurface: DrawingSurface): Unit = {

  }

  /**
   * Rotates this [[Image]].
   *
   * @param angleInDegrees
   *
   * @return
   */
  override def rotateDegs(angleInDegrees: Double): Image = {
    this
  }

}
