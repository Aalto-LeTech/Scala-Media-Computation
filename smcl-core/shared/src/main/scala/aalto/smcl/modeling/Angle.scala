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

package aalto.smcl.modeling


import aalto.smcl.infrastructure.{FlatMap, MathUtils}




/**
 * Companion object for the [[Angle]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Angle {

  /** A full circle in degrees. */
  val FullCircleInDegrees: Int = 360

  /** A half circle in degrees. */
  val HalfCircleInDegrees: Int = FullCircleInDegrees / 2

  /** A quarter circle in degrees. */
  val QuarterCircleInDegrees: Int = FullCircleInDegrees / 4

  /** A full circle in radians. */
  val FullCircleInRadians: Double = 2.0 * math.Pi

  /** A half circle in radians. */
  val HalfCircleInRadians: Double = math.Pi

  /** A quarter circle in radians. */
  val QuarterCircleInRadians: Double = math.Pi / 2.0

  /** Number of degrees in a radian. */
  val DegreesPerRadian: Double = FullCircleInDegrees / FullCircleInRadians

  /** Number of radians in a degree. */
  val RadiansPerDegree: Double = FullCircleInRadians / FullCircleInDegrees

  /**
   * Creates a new [[Angle]] instance on the basis of given angle value.
   *
   * @param valueInDegrees
   */
  def apply(valueInDegrees: Double): Angle = {
    new Angle(valueInDegrees)
  }

  /**
   * Creates a new [[Angle]] instance on the basis of given angle value.
   *
   * @param valueInDegrees
   */
  def normalized(valueInDegrees: Double): Angle = {
    apply(valueInDegrees)
  }

}




/**
 * Angle.
 *
 * @param inDegrees
 *
 * @author Aleksi Lukkarinen
 */
case class Angle private(
    inDegrees: Double)
    extends Magnitude[Angle](inDegrees)
            with FlatMap[Angle, Double] {

  /** The value of this angle in radians. */
  lazy val inRadians: Double = {
    inDegrees * Angle.RadiansPerDegree
  }

  /**
   *
   * @param f
   *
   * @return
   */
  @inline
  override
  def map(f: (Double) => Double): Angle = {
    Angle(f(inDegrees))
  }

  /**
   *
   *
   * @return
   */
  @inline
  def normalize: Angle = {
    Angle(MathUtils.normalizeDegs(inDegrees))
  }

  /**
   *
   *
   * @return
   */
  @inline
  def sin: Double = {
    MathUtils.sinFor(inDegrees)
  }

}
