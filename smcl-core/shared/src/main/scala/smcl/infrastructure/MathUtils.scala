/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package smcl.infrastructure


import scala.annotation.tailrec

import smcl.modeling.Angle




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object MathUtils {

  /**
   * Divides a length into a number of positions.
   *
   * @param lengthToDivide
   * @param numberOfDivisions
   *
   * @return
   */
  def divideLength(
      lengthToDivide: Double,
      numberOfDivisions: Int): Seq[Double] = {

    @tailrec
    def divideLengthRec(
        currentDivision: Int,
        numberOfDivisions: Int,
        lengthToDivide: Double,
        resultSeq: Seq[Double]): Seq[Double] = {

      if (currentDivision >= numberOfDivisions)
        return resultSeq

      divideLengthRec(
        currentDivision + 1,
        numberOfDivisions,
        lengthToDivide,
        resultSeq :+ ((currentDivision * lengthToDivide) / numberOfDivisions)
      )
    }

    divideLengthRec(0, numberOfDivisions, lengthToDivide, Seq[Double]())
  }

  /**
   *
   *
   * @param valueInDegrees
   *
   * @return
   */
  @inline
  def normalizeDegs(valueInDegrees: Double): Double = {
    valueInDegrees % Angle.FullAngleInDegrees
  }

  /**
   *
   *
   * @param valueInDegrees
   *
   * @return
   */
  @inline
  def normalizeToPosDegs(valueInDegrees: Double): Double = {
    val tmp = normalizeDegs(valueInDegrees)

    if (tmp < 0)
      Angle.FullAngleInDegrees - tmp
    else
      tmp
  }

  /**
   *
   *
   * @param valueInDegrees
   *
   * @return
   */
  @inline
  def normalizeToNegDegs(valueInDegrees: Double): Double = {
    val tmp = normalizeDegs(valueInDegrees)

    if (tmp > 0)
      -Angle.FullAngleInDegrees + tmp
    else
      tmp
  }

  /**
   *
   *
   * @param valueInRadians
   *
   * @return
   */
  @inline
  def normalizeRads(valueInRadians: Double): Double = {
    valueInRadians % Angle.FullAngleInRadians
  }

  /**
   *
   *
   * @param valueInRadians
   *
   * @return
   */
  @inline
  def normalizeToPosRads(valueInRadians: Double): Double = {
    val tmp = normalizeRads(valueInRadians)

    if (tmp < 0)
      tmp + Angle.FullAngleInRadians
    else
      tmp
  }

  /**
   *
   *
   * @param valueInRadians
   *
   * @return
   */
  @inline
  def normalizeToNegRads(valueInRadians: Double): Double = {
    val tmp = normalizeRads(valueInRadians)

    if (tmp > 0)
      tmp - Angle.FullAngleInRadians
    else
      tmp
  }

  /**
   *
   *
   * @param valueInGradians
   *
   * @return
   */
  @inline
  def normalizeGrads(valueInGradians: Double): Double = {
    valueInGradians % Angle.FullAngleInGradians
  }

  /**
   *
   *
   * @param valueInGradians
   *
   * @return
   */
  @inline
  def normalizeToPosGrads(valueInGradians: Double): Double = {
    val tmp = normalizeGrads(valueInGradians)

    if (tmp < 0)
      Angle.FullAngleInGradians - tmp
    else
      tmp
  }

  /**
   *
   *
   * @param valueInGradians
   *
   * @return
   */
  @inline
  def normalizeToNegGrads(valueInGradians: Double): Double = {
    val tmp = normalizeGrads(valueInGradians)

    if (tmp > 0)
      -Angle.FullAngleInGradians + tmp
    else
      tmp
  }

  /**
   *
   *
   * @param a
   *
   * @return
   */
  @inline
  def sinCos(a: Angle): (Double, Double) = sinCosRads(a.inRadians)

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def sinCos(angleInDegrees: Double): (Double, Double) = {
    sinCosRads(math.toRadians(angleInDegrees))
  }

  /**
   *
   *
   * @param angleInRadians
   *
   * @return
   */
  @inline
  def sinCosRads(angleInRadians: Double): (Double, Double) = {
    val sin = sinRads(angleInRadians)
    val cos = cosRads(angleInRadians)

    (sin, cos)
  }

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def sin(angleInDegrees: Double): Double = {
    sinRads(math.toRadians(angleInDegrees))
  }

  /**
   *
   *
   * @param a
   *
   * @return
   */
  @inline
  def sin(a: Angle): Double = sinRads(a.inRadians)

  /**
   *
   *
   * @param angleInRadians
   *
   * @return
   */
  @inline
  def sinRads(angleInRadians: Double): Double = {
    math.sin(angleInRadians)
  }

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def sinh(angleInDegrees: Double): Double = {
    sinhRads(math.toRadians(angleInDegrees))
  }

  /**
   *
   *
   * @param a
   *
   * @return
   */
  @inline
  def sinh(a: Angle): Double = sinhRads(a.inRadians)

  /**
   *
   *
   * @param angleInRadians
   *
   * @return
   */
  @inline
  def sinhRads(angleInRadians: Double): Double = {
    math.sinh(angleInRadians)
  }

  /**
   *
   *
   * @param sin
   *
   * @return
   */
  @inline
  def asin(sin: Double): Double = {
    math.toDegrees(asinRads(sin))
  }

  /**
   *
   *
   * @param sin
   *
   * @return
   */
  @inline
  def asinRads(sin: Double): Double = {
    math.asin(sin)
  }

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def cos(angleInDegrees: Double): Double = {
    cosRads(math.toRadians(angleInDegrees))
  }

  /**
   *
   *
   * @param a
   *
   * @return
   */
  @inline
  def cos(a: Angle): Double = cosRads(a.inRadians)

  /**
   *
   *
   * @param angleInRadians
   *
   * @return
   */
  @inline
  def cosRads(angleInRadians: Double): Double = {
    math.cos(angleInRadians)
  }

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def cosh(angleInDegrees: Double): Double = {
    coshRads(math.toRadians(angleInDegrees))
  }

  /**
   *
   *
   * @param a
   *
   * @return
   */
  @inline
  def cosh(a: Angle): Double = coshRads(a.inRadians)

  /**
   *
   *
   * @param angleInRadians
   *
   * @return
   */
  @inline
  def coshRads(angleInRadians: Double): Double = {
    math.cosh(angleInRadians)
  }

  /**
   *
   *
   * @param cos
   *
   * @return
   */
  @inline
  def acos(cos: Double): Double = {
    math.toDegrees(acosRads(cos))
  }

  /**
   *
   *
   * @param cos
   *
   * @return
   */
  @inline
  def acosRads(cos: Double): Double = {
    math.acos(cos)
  }

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def tan(angleInDegrees: Double): Double = {
    tanRads(math.toRadians(angleInDegrees))
  }

  /**
   *
   *
   * @param a
   *
   * @return
   */
  @inline
  def tan(a: Angle): Double = tanRads(a.inRadians)

  /**
   *
   *
   * @param angleInRadians
   *
   * @return
   */
  @inline
  def tanRads(angleInRadians: Double): Double = {
    math.tan(angleInRadians)
  }

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def tanh(angleInDegrees: Double): Double = {
    tanhRads(math.toRadians(angleInDegrees))
  }

  /**
   *
   *
   * @param a
   *
   * @return
   */
  @inline
  def tanh(a: Angle): Double = tanhRads(a.inRadians)

  /**
   *
   *
   * @param angleInRadians
   *
   * @return
   */
  @inline
  def tanhRads(angleInRadians: Double): Double = {
    math.tanh(angleInRadians)
  }

  /**
   *
   *
   * @param tan
   *
   * @return
   */
  @inline
  def atan(tan: Double): Double = {
    math.toDegrees(atanRads(tan))
  }

  /**
   *
   *
   * @param radianBasedTan
   *
   * @return
   */
  @inline
  def atanRads(radianBasedTan: Double): Double = {
    math.atan(radianBasedTan)
  }

  /**
   *
   *
   * @param a
   * @param b
   *
   * @return
   */
  @inline
  def sort(a: Double, b: Double): (Double, Double) = {
    if (a < b)
      (a, b)
    else
      (b, a)
  }

  /**
   *
   *
   * @param a
   * @param b
   *
   * @return
   */
  @inline
  def sort(a: Double, b: Double, c: Double): (Double, Double, Double) = {
    if (a <= b) {
      if (c <= a && c <= b) {
        (c, a, b)
      }
      else if (c > a && c <= b) {
        (a, c, b)
      }
      else {
        (a, b, c)
      }
    }
    else {
      if (c <= b && c <= a) {
        (c, b, a)
      }
      else if (c > a && c <= b) {
        (a, c, b)
      }
      else {
        (a, b, c)
      }
    }
  }

}
