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


  /** */
  private[this] lazy val _viewerClient = new ViewerClient()

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
   *
   *
   * @param sc
   * @return
   */
  implicit def BitmapCreationStringContextWrapper(sc: StringContext): BitmapCreationStringInterpolator =
    new BitmapCreationStringInterpolator(sc)

}
