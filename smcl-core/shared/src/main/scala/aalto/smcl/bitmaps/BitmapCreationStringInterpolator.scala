package aalto.smcl.bitmaps




/**
 * A string interpolator for creating [[aalto.smcl.bitmaps.Bitmap]] instances.
 *
 * @param sc
 *
 * @author Aleksi Lukkarinen
 */
class BitmapCreationStringInterpolator(val sc: StringContext)
  extends AnyVal {


  /**
   * Loads bitmaps from a file.
   *
   * @param args path to the image file to be loaded
   * @return
   *
   * @author Aleksi Lukkarinen
   */
  def bmpf(args: Any*): BitmapLoadingResult = {
    val pathStringCandidate = sc.standardInterpolator(StringContext.processEscapes, args)

    Bitmap(pathStringCandidate)
  }

}
