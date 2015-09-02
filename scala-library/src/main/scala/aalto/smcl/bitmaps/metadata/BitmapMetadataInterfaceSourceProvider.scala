package aalto.smcl.bitmaps.metadata


import aalto.smcl.bitmaps.Bitmap
import aalto.smcl.interfaces.MetadataInterfaceSourceProvider




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[metadata]
class BitmapMetadataInterfaceSourceProvider() extends MetadataInterfaceSourceProvider {

  /**
   *
   *
   * @param interestingObject
   * @return
   */
  override def querySourceFor(interestingObject: Any): Option[Any] = {
    interestingObject.getClass match {
      case b if b.isAssignableFrom(Bitmap().getClass) =>
        Some(BitmapMetadataSource(interestingObject.asInstanceOf[Bitmap]))

      case _ => None
    }

  }

}
