package aalto.smcl.bitmaps


import aalto.smcl.bitmaps.immutable.primitives.Bitmap




/**
 * A string interpolator for creating [[Bitmap]] instances.
 *
 * @param sc
 */
class BitmapCreationStringInterpolator(val sc: StringContext) extends AnyVal {

  /**
   * Loads bitmaps from a file.
   *
   * @param args path to the image file to be loaded
   * @return
   *
   * @author Aleksi Lukkarinen
   */
  def bmpf(args: Any*): Seq[Either[Throwable, Bitmap]] = {
    val s = sc.standardInterpolator(StringContext.processEscapes, args)

    Bitmap(s)
  }

}
