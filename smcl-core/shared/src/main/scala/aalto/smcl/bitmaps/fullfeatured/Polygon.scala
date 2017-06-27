package aalto.smcl.bitmaps.fullfeatured


import aalto.smcl.modeling.d2.{HasPos, Pos}
import aalto.smcl.infrastructure.Identity




/**
 *
 * @param identity
 * @param points
 * @param position
 *
 * @author Aleksi Lukkarinen
 */
abstract class Polygon(
    identity: Identity,
    points: Seq[Pos],
    position: Pos)
    extends VectorGraphic(identity)
            with HasPos {

}
