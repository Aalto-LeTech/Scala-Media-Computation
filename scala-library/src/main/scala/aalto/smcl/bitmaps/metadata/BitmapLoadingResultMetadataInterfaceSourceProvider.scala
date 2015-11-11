package aalto.smcl.bitmaps.metadata


import aalto.smcl.bitmaps.BitmapLoadingResult
import aalto.smcl.interfaces.MetadataInterfaceSourceProvider




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[metadata]
class BitmapLoadingResultMetadataInterfaceSourceProvider()
  extends MetadataInterfaceSourceProvider {


  /** */
  private[this] val _bitmapLoadingResultClass = new BitmapLoadingResult(Seq(Right((0, null)))).getClass


  /**
   *
   *
   * @param interestingObject
   * @return
   */
  override def querySourceFor(interestingObject: Any): Option[Any] = {
    val c = interestingObject.getClass

    if (_bitmapLoadingResultClass.isAssignableFrom(c)) {
      return Some(BitmapLoadingResultMetadataSource(interestingObject.asInstanceOf[BitmapLoadingResult]))
    }

    None
  }

}
