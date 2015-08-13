package aalto.smcl.platform


import java.awt.geom.{AffineTransform => AwtAffineTransformation}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object PlatformAffineTransformation {

  /**
   *
   *
   * @return
   */
  def apply(): PlatformAffineTransformation =
    new PlatformAffineTransformation(new AwtAffineTransformation())

  /**
   *
   *
   * @return
   */
  private[platform] def apply(awtAffineTransform: AwtAffineTransformation): PlatformAffineTransformation =
    new PlatformAffineTransformation(awtAffineTransform)

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] class PlatformAffineTransformation private(
  val awtAffineTransformation: AwtAffineTransformation) {

  /**
   *
   *
   * @param factorX
   * @param factorY
   */
  def scale(factorX: Double, factorY: Double): PlatformAffineTransformation = {
    awtAffineTransformation.scale(factorX, factorY)
    this
  }


  /**
   *
   *
   * @param amountX
   * @param amountY
   */
  def translate(amountX: Double, amountY: Double): PlatformAffineTransformation = {
    awtAffineTransformation.translate(amountX, amountY)
    this
  }


  /**
   *
   *
   * @param angleInDegrees
   */
  def rotate(angleInDegrees: Double): PlatformAffineTransformation = {
    awtAffineTransformation.rotate(angleInDegrees)
    this
  }

  /**
   *
   *
   * @param amountX
   * @param amountY
   */
  def shear(amountX: Double, amountY: Double): PlatformAffineTransformation = {
    awtAffineTransformation.shear(amountX, amountY)
    this
  }

  /**
   *
   *
   * @return
   */
  def copy(): PlatformAffineTransformation =
    PlatformAffineTransformation(new AwtAffineTransformation(awtAffineTransformation))

}
