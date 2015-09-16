package aalto.smcl.bitmaps.metadata


import scala.collection.GenTraversableLike

import aalto.smcl.interfaces.MetadataInterfaceSourceProvider




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[metadata]
class GenTraversableLikeMetadataInterfaceSourceProvider() extends MetadataInterfaceSourceProvider {

  /** */
  private[this] val _genTaversableLikeClass = classOf[GenTraversableLike[_, _]]


  /**
   *
   *
   * @param interestingObject
   * @return
   */
  override def querySourceFor(interestingObject: Any): Option[Any] = {
    val c = interestingObject.getClass

    if (_genTaversableLikeClass.isAssignableFrom(c)) {
      return Some(GenTraversableLikeMetadataSource(interestingObject.asInstanceOf[GenTraversableLike[_, _]]))
    }

    None
  }

}
