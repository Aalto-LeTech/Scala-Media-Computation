package aalto.smcl.bitmaps.fullfeatured


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait HasPosition[PointType <: PointBase] {

  /** Position of the object. */
  def position: PointType

}
