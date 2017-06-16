package aalto.smcl.bitmaps.fullfeatured


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait HasChildren[ChildrenType <: ImageElement] {

  /** Child elements of the [[ImageElement]]. */
  def children: Seq[ChildrenType] = Seq()

}
