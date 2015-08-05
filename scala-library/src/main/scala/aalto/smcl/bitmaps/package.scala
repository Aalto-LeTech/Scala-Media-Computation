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


  /**
   * A string interpolator for creating [[Bitmap]] instances.
   *
   * @param sc
   */
  implicit class BitmapCreationStringInterpolator(val sc: StringContext) extends AnyVal {

    /**
     *
     *
     * @param args
     * @return
     */
    def bmp(args: Any*): Bitmap = {
      val s = sc.standardInterpolator(StringContext.processEscapes, args)

      // TODO: Replace with real functionality when it is available
      Bitmap()
    }
  }


}
