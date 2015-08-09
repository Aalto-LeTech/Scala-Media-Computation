package aalto.smcl


import aalto.smcl.bitmaps.immutable.primitives.Bitmap


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object bitmaps {

  SMCL.performInitialization()


  /** */
  private[this] val _viewerClient = new ViewerClient()

  /**
   *
   *
   * @param sourceBitmap
   */
  def display(sourceBitmap: Bitmap): Unit = _viewerClient.display(sourceBitmap)


  /**
   *
   */
  def closeBitmapViewersWithoutSaving(): Unit = _viewerClient.closeAllViewersWithTheForce()


  /** */
  private[bitmaps]
  implicit def BitmapCreationStringContextWrapper(sc: StringContext): BitmapCreationStringInterpolator =
    new BitmapCreationStringInterpolator(sc)

}
