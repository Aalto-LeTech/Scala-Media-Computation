package aalto.smcl.bitmaps

import aalto.smcl.bitmaps.immutable.primitives.Bitmap


/**
 * A string interpolator for creating [[Bitmap]] instances.
 *
 * @param sc
 */
class BitmapCreationStringInterpolator(val sc: StringContext) extends AnyVal {

  /**
   *
   *
   * @param args
   * @return
   *
   * @author Aleksi Lukkarinen
   */
  def bmp(args: Any*): Bitmap = {
    val s = sc.standardInterpolator(StringContext.processEscapes, args)

    // TODO: Replace with real functionality when it is available
    Bitmap()
  }
}
