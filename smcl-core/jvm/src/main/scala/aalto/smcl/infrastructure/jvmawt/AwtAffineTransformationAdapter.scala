package aalto.smcl.infrastructure.jvmawt


import java.awt.geom.{AffineTransform => LowLevelAwtAffineTransform}

import aalto.smcl.infrastructure.AffineTransformationAdapter




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
object AwtAffineTransformationAdapter {

  /**
   *
   *
   * @return
   */
  def apply(): AwtAffineTransformationAdapter =
    new AwtAffineTransformationAdapter(new LowLevelAwtAffineTransform())

  /**
   *
   *
   * @return
   */
  private[infrastructure]
  def apply(awtAffineTransform: LowLevelAwtAffineTransform): AwtAffineTransformationAdapter =
    new AwtAffineTransformationAdapter(awtAffineTransform)

}


/**
  *
  *
  * @author Aleksi Lukkarinen
  */
private[smcl]
class AwtAffineTransformationAdapter private(
    val awtAffineTransformation: LowLevelAwtAffineTransform) extends AffineTransformationAdapter {

  /**
    *
    *
    * @param factorX
    * @param factorY
    * @return
    */
  override def scale(factorX: Double, factorY: Double): AwtAffineTransformationAdapter = {
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
  override def translate(amountX: Double, amountY: Double): AwtAffineTransformationAdapter = {
    awtAffineTransformation.translate(amountX, amountY)
    this
  }

  /**
    *
    *
    * @param angleInDegrees
    * @return
    */
  override def rotateDegs(angleInDegrees: Double): AwtAffineTransformationAdapter = {
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
  override def rotateDegsAround(
      angleInDegrees: Double,
      anchorX: Double,
      anchorY: Double): AwtAffineTransformationAdapter = {

    awtAffineTransformation.rotate(Math.toRadians(-angleInDegrees), anchorX, anchorY)
    this
  }

  /**
    *
    *
    * @return
    */
  override def rotate90DegsCw(): AwtAffineTransformationAdapter = {
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
  override def rotate90DegsCwAround(
      anchorX: Double,
      anchorY: Double): AwtAffineTransformationAdapter = {

    awtAffineTransformation.quadrantRotate(OneQuadrantClockwise, anchorX, anchorY)
    this
  }

  /**
    *
    *
    * @return
    */
  override def rotate90DegsCcw(): AwtAffineTransformationAdapter = {
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
  override def rotate90DegsCcwAround(
      anchorX: Double,
      anchorY: Double): AwtAffineTransformationAdapter = {

    awtAffineTransformation.quadrantRotate(OneQuadrantCounterClockwise, anchorX, anchorY)
    this
  }

  /**
    *
    *
    * @return
    */
  override def rotate180Degs(): AwtAffineTransformationAdapter = {
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
  override def rotate180DegsAround(
      anchorX: Double,
      anchorY: Double): AwtAffineTransformationAdapter = {

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
  override def shear(amountX: Double, amountY: Double): AwtAffineTransformationAdapter = {
    awtAffineTransformation.shear(amountX, amountY)
    this
  }

  /**
    *
    *
    * @return
    */
  override def copy(): AwtAffineTransformationAdapter =
    AwtAffineTransformationAdapter(new LowLevelAwtAffineTransform(awtAffineTransformation))

}
