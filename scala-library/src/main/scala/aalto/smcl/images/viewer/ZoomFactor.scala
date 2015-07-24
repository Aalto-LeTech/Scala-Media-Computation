package aalto.smcl.images.viewer


import java.awt.geom.AffineTransform

import scala.swing.Dimension




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[viewer] object ZoomFactor {

  /** */
  val validRangeInPercents: Range.Inclusive = 10 to 500

  /**
   *
   *
   * @param valueInPercents
   * @return
   */
  def apply(valueInPercents: Int = 100): ZoomFactor = {
    require(validRangeInPercents.contains(valueInPercents),
      s"Zoom factor must be on the valid range from " +
          "${validRangeInPercents.start} to ${validRangeInPercents.end} (was $valueAsPercents)")

    new ZoomFactor(valueInPercents)
  }

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[viewer] class ZoomFactor private(val valueInPercents: Int)
    extends Ordered[ZoomFactor] with Immutable {

  /**
   *
   *
   * @param factor
   * @return
   */
  def adjustByIfPossible(factor: Double): ZoomFactor = {
    val newValue = (factor * valueInPercents).toInt
        .max(ZoomFactor.validRangeInPercents.start)
        .min(ZoomFactor.validRangeInPercents.end)

    new ZoomFactor(newValue)
  }

  /**
   *
   *
   * @return
   */
  val asFactor: Double = valueInPercents / 100

  /**
   *
   */
  val asAffineTransformation: AffineTransform =
    AffineTransform.getScaleInstance(asFactor, asFactor)

  /**
   *
   *
   * @param that
   * @return
   */
  override def compare(that: ZoomFactor): Int = this.valueInPercents - that.valueInPercents

  /**
   *
   *
   * @param measure
   * @return
   */
  def scale(measure: Double): Double = asFactor * measure

  /**
   *
   *
   * @param measure
   * @return
   */
  def scale(measure: Int): Double = asFactor * measure

  /**
   *
   *
   * @param dimensions
   * @return
   */
  def scale(dimensions: Dimension): Dimension =
    new Dimension(
      (asFactor * dimensions.getWidth).toInt,
      (asFactor * dimensions.getHeight).toInt)

  /**
   *
   *
   * @return
   */
  override def toString(): String = s"[ZoomFactor, $valueInPercents %]"

}
