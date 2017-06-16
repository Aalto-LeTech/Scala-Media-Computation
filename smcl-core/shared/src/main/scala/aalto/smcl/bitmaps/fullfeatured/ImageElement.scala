package aalto.smcl.bitmaps.fullfeatured


import aalto.smcl.infrastructure.Identity




/**
 *
 *
 * @param identity
 *
 * @author Aleksi Lukkarinen
 */
abstract class ImageElement(
    val identity: Identity) {

  /** Tells if this [[ImageElement]] can be rendered on a bitmap. */
  def isRenderable: Boolean

  /**
   * Renders this [[ImageElement]] on a drawing surface.
   *
   * @param drawingSurface
   */
  def renderOn(drawingSurface: DrawingSurface): Unit

  /**
   * Rotates this [[ImageElement]].
   *
   * @param angleInDegrees
   *
   * @return
   */
  def rotateDegs(angleInDegrees: Double): ImageElement

}
