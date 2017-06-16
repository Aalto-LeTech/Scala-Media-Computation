package aalto.smcl.bitmaps.fullfeatured


import aalto.smcl.infrastructure.Identity




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class Line private(
    override val identity: Identity,
    val position: Point,
    val start: Point,
    val end: Point)
    extends Polygon[Point](
      identity,
      Seq(start, end),
      position) {

  /** Tells if this [[Line]] can be rendered on a bitmap. */
  val isRenderable: Boolean = true


  /**
   * Renders this [[Line]] on a drawing surface.
   *
   * @param drawingSurface
   */
  override def renderOn(drawingSurface: DrawingSurface): Unit = {

  }

  /**
   * Rotates this [[Line]].
   *
   * @param angleInDegrees
   *
   * @return
   */
  override def rotateDegs(angleInDegrees: Double): Line = {
    this
  }

}
