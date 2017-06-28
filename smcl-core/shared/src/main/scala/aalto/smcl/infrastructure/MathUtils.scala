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
    valueInDegrees % Angle.FullCircleInDegrees
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
      Angle.FullCircleInDegrees - tmp
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
      -Angle.FullCircleInDegrees + tmp
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
    valueInRadians % Angle.FullCircleInDegrees
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
    val tmp = normalizeDegs(valueInRadians)

    if (tmp < 0)
      Angle.FullCircleInRadians - tmp
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
    val tmp = normalizeDegs(valueInRadians)

    if (tmp > 0)
      -Angle.FullCircleInRadians + tmp
    else
      tmp
  }

  /**
   *
   *
   * @param angleInRadians
   *
   * @return
   */
  @inline
  def sinCosFor(angleInRadians: Double): (Double, Double) = {
    (math.sin(angleInRadians): @inline,
        math.cos(angleInRadians): @inline)
  }

  /**
   *
   *
   * @param angleInRadians
   *
   * @return
   */
  @inline
  def sinFor(angleInRadians: Double): Double = {
    math.sin(angleInRadians): @inline
  }

  /**
   *
   *
   * @param angleInRadians
   *
   * @return
   */
  @inline
  def cosFor(angleInRadians: Double): Double = {
    math.cos(angleInRadians): @inline
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
