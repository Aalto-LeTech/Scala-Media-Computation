package aalto.smcl.bitmaps.fullfeatured


import aalto.smcl.geometry.{AbstractPosition, HasPos}
import aalto.smcl.infrastructure.Identity




/**
 *
 * @param identity
 * @param points
 * @param position
 * @tparam PositionType
 *
 * @author Aleksi Lukkarinen
 */
abstract class Polygon[PositionType <: AbstractPosition[ValueType], ValueType](
    identity: Identity,
    points: Seq[PositionType],
    position: PositionType)
    extends VectorGraphic(identity)
            with HasPos[ValueType] {

}
