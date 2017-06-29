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

package aalto.smcl.infrastructure


import aalto.smcl.modeling.Angle




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object MathUtils {

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
      Angle.FullAngleInRadians - tmp
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
      -Angle.FullAngleInRadians + tmp
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
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def asin(angleInDegrees: Double): Double = {
    asinRads(math.toRadians(angleInDegrees))
  }

  /**
   *
   *
   * @param angleInRadians
   *
   * @return
   */
  @inline
  def asinRads(angleInRadians: Double): Double = {
    math.asin(angleInRadians)
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
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def acos(angleInDegrees: Double): Double = {
    acosRads(math.toRadians(angleInDegrees))
  }

  /**
   *
   *
   * @param angleInRadians
   *
   * @return
   */
  @inline
  def acosRads(angleInRadians: Double): Double = {
    math.acos(angleInRadians)
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
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def atan(angleInDegrees: Double): Double = {
    atanRads(math.toRadians(angleInDegrees))
  }

  /**
   *
   *
   * @param angleInRadians
   *
   * @return
   */
  @inline
  def atanRads(angleInRadians: Double): Double = {
    math.atan(angleInRadians)
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
