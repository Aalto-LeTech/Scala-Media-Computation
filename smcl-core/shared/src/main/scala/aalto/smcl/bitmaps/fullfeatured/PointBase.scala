package aalto.smcl.bitmaps.fullfeatured


import aalto.smcl.infrastructure.Identity




/**
 *
 *
 * @param coordinates
 *
 * @author Aleksi Lukkarinen
 */
abstract class PointBase(
    override val identity: Identity,
    val coordinates: Seq[Int])
    extends VectorGraphic(identity) {

  /** Tells if this point can be rendered onto a bitmap. All points can be. */
  override def isRenderable: Boolean = true

}
