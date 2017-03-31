package aalto.smcl.viewers


import aalto.smcl.bitmaps.Bitmap




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
trait BitmapViewerApplication {

  /**
   *
   *
   * @param sourceBitmap
   */
  def display(sourceBitmap: Bitmap): Unit

  /**
   *
   */
  def closeAllViewersWithoutSaving(): Unit

}
