package aalto.smcl.common


import aalto.smcl.platform.PlatformAffineTransformation




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object AffineTransformation {

  /**
   *
   *
   * @return
   */
  def apply(): AffineTransformation =
    new AffineTransformation(PlatformAffineTransformation())


  /**
   *
   *
   * @param bitmapWidth
   * @return
   */
  def forHorizontalFlipOf(bitmapWidth: Int): AffineTransformation =
    AffineTransformation().scale(-1, 1).translate(-bitmapWidth, 0)

  /**
   *
   *
   * @param bitmapHeight
   * @return
   */
  def forVerticalFlipOf(bitmapHeight: Int): AffineTransformation =
    AffineTransformation().scale(1, -1).translate(0, -bitmapHeight)

  /**
   *
   *
   * @param bitmapHeight
   * @return
   */
  def forDiagonalFlipOf(bitmapWidth: Int, bitmapHeight: Int): AffineTransformation =
    AffineTransformation().scale(-1, -1).translate(-bitmapWidth, -bitmapHeight)

  /**
   *
   *
   * @param factorX
   * @param factorY
   * @return
   */
  def forFreeScalingOf(factorX: Double, factorY: Double): AffineTransformation =
    AffineTransformation().scale(factorX, factorY)

  /**
   *
   *
   * @param angleInDegrees
   * @return
   */
  def forFreeRotationOf(angleInDegrees: Double): AffineTransformation =
    AffineTransformation().rotate(angleInDegrees)

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class AffineTransformation private(
  private[smcl] val platformAffineTransform: PlatformAffineTransformation) {

  require(platformAffineTransform != null, "Platform transformation argument cannot be null.")


  /**
   *
   *
   * @param factorX
   * @param factorY
   */
  def scale(factorX: Double, factorY: Double): AffineTransformation =
    AffineTransformation(platformAffineTransform.copy().scale(factorX, factorY))

  /**
   *
   *
   * @param amountX
   * @param amountY
   */
  def translate(amountX: Double, amountY: Double): AffineTransformation =
    AffineTransformation(platformAffineTransform.copy().translate(amountX, amountY))

  /**
   *
   *
   * @param angleInDegrees
   */
  def rotate(angleInDegrees: Double): AffineTransformation =
    AffineTransformation(platformAffineTransform.copy().rotate(angleInDegrees))

  /**
   *
   *
   * @param amountX
   * @param amountY
   */
  def shear(amountX: Double, amountY: Double): AffineTransformation =
    AffineTransformation(platformAffineTransform.copy().shear(amountX, amountY))

  /**
   *
   *
   * @return
   */
  def copy(): AffineTransformation =
    AffineTransformation(platformAffineTransform.copy())

}
