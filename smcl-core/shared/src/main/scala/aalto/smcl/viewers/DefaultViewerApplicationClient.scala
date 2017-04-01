package aalto.smcl.viewers


import aalto.smcl.bitmaps.Bitmap
import aalto.smcl.infrastructure.Displayable
import aalto.smcl.infrastructure.exceptions.{SMCLImplementationNotSetError, SMCLUnknownMediaTypeError}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object DefaultViewerApplicationClient extends ViewerApplicationClient {

  /** Bitmap viewer to be used. */
  private var _bitmapViewerApplication: Option[BitmapViewerApplication] = None

  /**
   *
   *
   * @param resource
   */
  override def display(resource: Displayable): Unit = {
    resource match {
      case bmp: Bitmap => bitmapViewerApplication.display(bmp)
      case _           => throw new SMCLUnknownMediaTypeError()
    }
  }

  /**
   *
   */
  def closeAllViewersWithoutSaving(): Unit = {
    bitmapViewerApplication.closeAllViewersWithoutSaving()
  }

  /**
   *
   *
   * @param viewer
   */
  def setBitmapViewer(viewer: BitmapViewerApplication): Unit =
    _bitmapViewerApplication = Some(viewer)

  /**
   *
   *
   * @return
   */
  private def bitmapViewerApplication: BitmapViewerApplication =
    _bitmapViewerApplication.getOrElse(throw new SMCLImplementationNotSetError("DefaultViewerClient"))

}
