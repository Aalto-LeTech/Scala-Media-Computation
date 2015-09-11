package aalto.smcl.bitmaps.metadata


import aalto.smcl.bitmaps.{Bitmap, ImmutableBitmap}
import aalto.smcl.interfaces.MetadataInterfaceSourceProvider




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[metadata]
class ImmutableBitmapMetadataInterfaceSourceProvider() extends MetadataInterfaceSourceProvider {

  /** */
  private[this] lazy val _bitmapClass = Bitmap().getClass

  /** */
  private[this] lazy val _immutableBitmapClass = ImmutableBitmap().getClass


  /**
   *
   *
   * @param interestingObject
   * @return
   */
  override def querySourceFor(interestingObject: Any): Option[Any] = {
    val c = interestingObject.getClass

    if (c.isAssignableFrom(_bitmapClass)
        || c.isAssignableFrom(_immutableBitmapClass)) {

      return Some(ImmutableBitmapMetadataSource(interestingObject.asInstanceOf[ImmutableBitmap]))
    }

    None
  }

}
