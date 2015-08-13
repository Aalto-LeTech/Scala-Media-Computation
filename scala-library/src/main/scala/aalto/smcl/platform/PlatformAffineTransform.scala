package aalto.smcl.platform


import java.awt.geom.AffineTransform




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object PlatformAffineTransform {

  /**
   *
   *
   * @return
   */
  def apply(): PlatformAffineTransform =
    new PlatformAffineTransform(new AffineTransform())

  /**
   *
   *
   * @return
   */
  private[platform] def apply(awtAffineTransform: AffineTransform): PlatformAffineTransform =
    new PlatformAffineTransform(new AffineTransform())

  /**
   *
   *
   * @param bitmapWidth
   * @return
   */
  def forHorizontalFlipOf(bitmapWidth: Int): PlatformAffineTransform =
    PlatformAffineTransform().scale(-1, 1).translate(-bitmapWidth, 0)

  /**
   *
   *
   * @param bitmapHeight
   * @return
   */
  def forVerticalFlipOf(bitmapHeight: Int): PlatformAffineTransform =
    PlatformAffineTransform().scale(1, -1).translate(0, -bitmapHeight)

  /**
   *
   *
   * @param bitmapHeight
   * @return
   */
  def forDiagonalFlipOf(bitmapWidth: Int, bitmapHeight: Int): PlatformAffineTransform =
    PlatformAffineTransform().scale(-1, -1).translate(-bitmapWidth, -bitmapHeight)

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] class PlatformAffineTransform private(val awtAffineTransform: AffineTransform) {

  /**
   *
   *
   * @param factorX
   * @param factorY
   */
  def scale(factorX: Double, factorY: Double): PlatformAffineTransform = {
    awtAffineTransform.scale(factorX, factorY)
    this
  }


  /**
   *
   *
   * @param amountX
   * @param amountY
   */
  def translate(amountX: Double, amountY: Double): PlatformAffineTransform = {
    awtAffineTransform.translate(amountX, amountY)
    this
  }


  /**
   *
   *
   * @param angleInDegrees
   */
  def translate(angleInDegrees: Double): PlatformAffineTransform = {
    awtAffineTransform.rotate(angleInDegrees)
    this
  }

  /**
   *
   *
   * @param amountX
   * @param amountY
   */
  def shear(amountX: Double, amountY: Double): PlatformAffineTransform = {
    awtAffineTransform.shear(amountX, amountY)
    this
  }

}
