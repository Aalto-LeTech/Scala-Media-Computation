package aalto.smcl.bitmaps.fullfeatured


import aalto.smcl.infrastructure.Identity




/**
 *
 * @param identity
 * @param points
 * @param position
 * @tparam PointType
 *
 * @author Aleksi Lukkarinen
 */
abstract class Polygon[PointType <: PointBase](
    identity: Identity,
    points: Seq[PointType],
    position: PointType)
    extends VectorGraphic(identity)
            with HasPosition[PointType] {

}
