package aalto.smcl.bitmaps.metadata


import aalto.smcl.SMCL
import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.interfaces.MetadataInterfaceSourceProvider




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class BitmapMetadataInterfaceSourceProvider extends MetadataInterfaceSourceProvider {

  SMCL.performInitialization()


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
