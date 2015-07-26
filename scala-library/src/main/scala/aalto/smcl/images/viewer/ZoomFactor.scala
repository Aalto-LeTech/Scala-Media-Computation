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

  /** */
  val STANDARD_ENLARGEMENT_FACTOR: Double = 1.1

  /** */
  val STANDARD_REDUCTION_FACTOR: Double = 0.9

  /**  */
  val IDENTITY = ZoomFactor(100)

  /**  */
  val ZF60 = ZoomFactor(60)

  /**  */
  val ZF70 = ZoomFactor(70)

  /**  */
  val ZF80 = ZoomFactor(80)

  /**  */
  val ZF90 = ZoomFactor(90)

  /**  */
  val ZF150 = ZoomFactor(150)

  /**  */
  val ZF200 = ZoomFactor(200)

  /**  */
  val ZF300 = ZoomFactor(300)

  /**  */
  val ZF400 = ZoomFactor(400)

  /**  */
  val ZF500 = ZoomFactor(500)

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

  private val HUNDRED_AS_DOUBLE: Double = 100.toDouble

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
  def enlargeByStandardAmountIfPossible() =
    this.adjustByIfPossible(ZoomFactor.STANDARD_ENLARGEMENT_FACTOR)

  /**
   *
   *
   * @return
   */
  def reduceByStandardAmountIfPossible() =
    this.adjustByIfPossible(ZoomFactor.STANDARD_REDUCTION_FACTOR)

  /**
   *
   *
   * @return
   */
  val asFactor: Double = valueInPercents / HUNDRED_AS_DOUBLE

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
  def scaleToDouble(measure: Double): Double = asFactor * measure

  /**
   *
   *
   * @param measure
   * @return
   */
  def scaleToDouble(measure: Int): Double = asFactor * measure

  /**
   *
   *
   * @param measure
   * @return
   */
  def scaleToInt(measure: Int): Int = (asFactor * measure).toInt

  /**
   *
   *
   * @param dimensions
   * @return
   */
  def scaleToDimension(dimensions: Dimension): Dimension =
    new Dimension(
      scaleToInt(dimensions.width),
      scaleToInt(dimensions.height))

  /**
   *
   *
   * @return
   */
  override def toString(): String = s"[ZoomFactor, $valueInPercents %]"

}
