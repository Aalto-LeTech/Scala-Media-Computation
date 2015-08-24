package aalto.smcl.bitmaps.metadata


import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.interfaces.MetadataInterfaceSourceProvider




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class BitmapMetadataInterfaceSourceProvider extends MetadataInterfaceSourceProvider {

  /**
   *
   *
   * @param interestingObject
   * @return
   */
  override def querySourceFor(interestingObject: Any): Option[Any] =
    interestingObject match {
      case b: Bitmap => Some(BitmapMetadataSource(b))
      case _         => None
    }

}
