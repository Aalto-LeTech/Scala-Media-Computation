package aalto.smcl


import scala.language.implicitConversions




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object bitmaps
  extends BitmapOperationAPI
  with ShapeCreationAPI {

  /** */
  type Bitmap = ImmutableBitmap

  /** */
  val Bitmap = ImmutableBitmap


  /**
   *
   *
   * @param sc
   * @return
   */
  implicit def BitmapCreationStringContextWrapper(sc: StringContext): BitmapCreationStringInterpolator =
    new BitmapCreationStringInterpolator(sc)

}
