package aalto.smcl

import scala.language.implicitConversions




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
package object bitmaps
  extends BitmapSettingKeys
  with BitmapOperationAPI
  with ShapeCreationAPI {

  /** */
  type Bitmap = ImmutableBitmap

  /** */
  val Bitmap = ImmutableBitmap


  /** */
  lazy val BitmapValidatorFunctionFactory: BitmapValidatorFunctionFactory =
    new BitmapValidatorFunctionFactory()

  /** */
  lazy val BitmapValidator: BitmapValidator = new BitmapValidator()


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
