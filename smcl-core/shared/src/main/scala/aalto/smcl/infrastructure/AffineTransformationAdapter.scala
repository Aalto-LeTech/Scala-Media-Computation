package aalto.smcl.infrastructure




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl]
trait AffineTransformationAdapter {

  /** */
  val OneQuadrantClockwise: Int = 1

  /** */
  val TwoQuadrants: Int = 2

  /** */
  val OneQuadrantCounterClockwise: Int = 3


  /**
   *
   *
   * @param factorX
   * @param factorY
   * @return
   */
  def scale(factorX: Double, factorY: Double): AffineTransformationAdapter

  /**
   *
   *
   * @param amountX
   * @param amountY
   * @return
   */
  def translate(amountX: Double, amountY: Double): AffineTransformationAdapter

  /**
   *
   *
   * @param angleInDegrees
   * @return
   */
  def rotateDegs(angleInDegrees: Double): AffineTransformationAdapter

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
      anchorY: Double): AffineTransformationAdapter

  /**
   *
   *
   * @return
   */
  def rotate90DegsCw(): AffineTransformationAdapter

  /**
   *
   *
   * @param anchorX
   * @param anchorY
   * @return
   */
  def rotate90DegsCwAround(
      anchorX: Double,
      anchorY: Double): AffineTransformationAdapter

  /**
   *
   *
   * @return
   */
  def rotate90DegsCcw(): AffineTransformationAdapter

  /**
   *
   *
   * @param anchorX
   * @param anchorY
   * @return
   */
  def rotate90DegsCcwAround(
      anchorX: Double,
      anchorY: Double): AffineTransformationAdapter

  /**
   *
   *
   * @return
   */
  def rotate180Degs(): AffineTransformationAdapter

  /**
   *
   *
   * @param anchorX
   * @param anchorY
   * @return
   */
  def rotate180DegsAround(
      anchorX: Double,
      anchorY: Double): AffineTransformationAdapter

  /**
   *
   *
   * @param amountX
   * @param amountY
   * @return
   */
  def shear(amountX: Double, amountY: Double): AffineTransformationAdapter

  /**
   *
   *
   * @return
   */
  def copy: AffineTransformationAdapter

}
