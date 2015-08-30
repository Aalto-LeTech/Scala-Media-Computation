package aalto.smcl.platform


import java.awt.geom.{AffineTransform => AwtAffineTransformation}

import aalto.smcl.SMCL




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object PlatformAffineTransformation {

  SMCL.performInitialization()


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

  /** */
  val OneQuadrantClockwise: Int = 1

  /** */
  val OneQuadrantCounterClockwise: Int = 3

  /** */
  val TwoQuadrants: Int = 2

  /**
   *
   *
   * @param factorX
   * @param factorY
   * @return
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
   * @return
   */
  def translate(amountX: Double, amountY: Double): PlatformAffineTransformation = {
    awtAffineTransformation.translate(amountX, amountY)
    this
  }

  /**
   *
   *
   * @param angleInDegrees
   * @return
   */
  def rotateDegs(angleInDegrees: Double): PlatformAffineTransformation = {
    awtAffineTransformation.rotate(Math.toRadians(-angleInDegrees))
    this
  }

  /**
   *
   *
   * @param angleInDegrees
   * @param anchorX
   * @param anchorY
   * @return
   */
  def rotateDegsAround(
    angleInDegrees: Double,
    anchorX: Double,
    anchorY: Double): PlatformAffineTransformation = {

    awtAffineTransformation.rotate(Math.toRadians(-angleInDegrees), anchorX, anchorY)
    this
  }

  /**
   *
   *
   * @return
   */
  def rotate90DegsCw(): PlatformAffineTransformation = {
    awtAffineTransformation.quadrantRotate(OneQuadrantClockwise)
    this
  }

  /**
   *
   *
   * @param anchorX
   * @param anchorY
   * @return
   */
  def rotate90DegsCwAround(
    anchorX: Double,
    anchorY: Double): PlatformAffineTransformation = {

    awtAffineTransformation.quadrantRotate(OneQuadrantClockwise, anchorX, anchorY)
    this
  }

  /**
   *
   *
   * @return
   */
  def rotate90DegsCcw(): PlatformAffineTransformation = {
    awtAffineTransformation.quadrantRotate(OneQuadrantCounterClockwise)
    this
  }

  /**
   *
   *
   * @param anchorX
   * @param anchorY
   * @return
   */
  def rotate90DegsCcwAround(
    anchorX: Double,
    anchorY: Double): PlatformAffineTransformation = {

    awtAffineTransformation.quadrantRotate(OneQuadrantCounterClockwise, anchorX, anchorY)
    this
  }

  /**
   *
   *
   * @return
   */
  def rotate180Degs(): PlatformAffineTransformation = {
    awtAffineTransformation.quadrantRotate(TwoQuadrants)
    this
  }

  /**
   *
   *
   * @param anchorX
   * @param anchorY
   * @return
   */
  def rotate180DegsAround(
    anchorX: Double,
    anchorY: Double): PlatformAffineTransformation = {

    awtAffineTransformation.quadrantRotate(TwoQuadrants, anchorX, anchorY)
    this
  }

  /**
   *
   *
   * @param amountX
   * @param amountY
   * @return
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
