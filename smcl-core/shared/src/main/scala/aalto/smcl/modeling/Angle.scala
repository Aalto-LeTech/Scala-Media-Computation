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

  /** A zero angle. */
  val Zero = Angle(0)

  /** An angle of 10 degrees. */
  lazy val Deg10 = Angle(10)

  /** An angle of 20 degrees. */
  lazy val Deg20 = Angle(20)

  /** An angle of 30 degrees. */
  lazy val Deg30 = Angle(30)

  /** An angle of 40 degrees. */
  lazy val Deg40 = Angle(40)

  /** An angle of 45 degrees. */
  lazy val Deg45 = Angle(45)

  /** An angle of 50 degrees. */
  lazy val Deg50 = Angle(50)

  /** An angle of 60 degrees. */
  lazy val Deg60 = Angle(60)

  /** An angle of 70 degrees. */
  lazy val Deg70 = Angle(70)

  /** An angle of 80 degrees. */
  lazy val Deg80 = Angle(80)

  /** An angle of 90 degrees. */
  lazy val Deg90 = Angle(90)

  /** An angle of 100 degrees. */
  lazy val Deg100 = Angle(100)

  /** An angle of 110 degrees. */
  lazy val Deg110 = Angle(110)

  /** An angle of 120 degrees. */
  lazy val Deg120 = Angle(120)

  /** An angle of 130 degrees. */
  lazy val Deg130 = Angle(130)

  /** An angle of 45 degrees. */
  lazy val Deg135 = Angle(135)

  /** An angle of 140 degrees. */
  lazy val Deg140 = Angle(140)

  /** An angle of 150 degrees. */
  lazy val Deg150 = Angle(150)

  /** An angle of 160 degrees. */
  lazy val Deg160 = Angle(160)

  /** An angle of 170 degrees. */
  lazy val Deg170 = Angle(170)

  /** An angle of 180 degrees. */
  lazy val Deg180 = Angle(180)

  /** An angle of 190 degrees. */
  lazy val Deg190 = Angle(190)

  /** An angle of 200 degrees. */
  lazy val Deg200 = Angle(200)

  /** An angle of 210 degrees. */
  lazy val Deg210 = Angle(210)

  /** An angle of 220 degrees. */
  lazy val Deg220 = Angle(220)

  /** An angle of 255 degrees. */
  lazy val Deg225 = Angle(225)

  /** An angle of 230 degrees. */
  lazy val Deg230 = Angle(230)

  /** An angle of 240 degrees. */
  lazy val Deg240 = Angle(240)

  /** An angle of 250 degrees. */
  lazy val Deg250 = Angle(250)

  /** An angle of 260 degrees. */
  lazy val Deg260 = Angle(260)

  /** An angle of 270 degrees. */
  lazy val Deg270 = Angle(270)

  /** An angle of 280 degrees. */
  lazy val Deg280 = Angle(280)

  /** An angle of 290 degrees. */
  lazy val Deg290 = Angle(290)

  /** An angle of 300 degrees. */
  lazy val Deg300 = Angle(300)

  /** An angle of 300 degrees. */
  lazy val Deg310 = Angle(310)

  /** An angle of 320 xdegrees. */
  lazy val Deg320 = Angle(320)

  /** An angle of 330 degrees. */
  lazy val Deg330 = Angle(330)

  /** An angle of 340 degrees. */
  lazy val Deg340 = Angle(340)

  /** An angle of 350 degrees. */
  lazy val Deg350 = Angle(350)

  /** Number of divisions to a full angle to get a radian. */
  val RadianDivisions: Double = 2.0 * math.Pi

  /** Number of divisions to a full angle to get a degree. */
  val DegreeDivisions: Int = 360

  /** Number of divisions to a full angle to get a gradian. */
  val GradianDivisions: Int = 400


  /** Number of degrees in a radian. */
  val RadianInDegrees: Double =
    DegreeDivisions / RadianDivisions

  /** Number of radians in a degree. */
  val DegreeInRadians: Double =
    RadianDivisions / DegreeDivisions

  /** Number of degrees in a gradian. */
  val GradianInDegrees: Double =
    DegreeDivisions / GradianDivisions

  /** Number of gradians in a degree. */
  val DegreeInGradians: Double =
    GradianDivisions / DegreeDivisions

  /** Number of gradians in a radian. */
  val RadianInGradians: Double =
    GradianDivisions / RadianDivisions

  /** Number of radians in a gradian. */
  val GradianInRadians: Double =
    RadianDivisions / GradianDivisions


  /** A full angle in degrees. */
  val FullAngleInDegrees: Int = DegreeDivisions

  /** A full angle in radians. */
  val FullAngleInRadians: Double = RadianDivisions

  /** A full angle in gradians. */
  val FullAngleInGradians: Double = GradianDivisions

  /** A full angle. */
  val FullAngle = Angle(FullAngleInDegrees)

  /** A full turn (i.e., a full angle). */
  val FullTurn = FullAngle


  /** Number of divisions to a full angle to get a straight angle. */
  val StraightAngleDivisions: Int = 2

  /** A straight angle in degrees. */
  val StraightAngleInDegrees: Int =
    FullAngleInDegrees / StraightAngleDivisions

  /** A straight angle in radians. */
  val StraightAngleInRadians: Double = math.Pi

  /** A straight angle in gradians. */
  val StraightAngleInGradians: Double =
    FullAngleInGradians / StraightAngleDivisions

  /** A straight angle. */
  val StraightAngle = Angle(StraightAngleInDegrees)

  /** A half turn (i.e., a straight angle). */
  val HalfTurn = StraightAngle


  /** Number of divisions to a full angle to get a right angle. */
  val RightAngleDivisions: Int = 4

  /** A right angle in degrees. */
  val RightAngleInDegrees: Int =
    FullAngleInDegrees / RightAngleDivisions

  /** A right angle in radians. */
  val RightAngleInRadians: Double =
    FullAngleInRadians / RightAngleDivisions.toDouble

  /** A right angle in gradians. */
  val RightAngleInGradians: Double =
    FullAngleInGradians / RightAngleDivisions

  /** A right angle. */
  val RightAngle = Angle(RightAngleInDegrees)

  /** A quadrant (i.e., a right angle). */
  val Quadrant = RightAngle

  /** A quarter turn (i.e., a right angle). */
  val QuarterTurn = RightAngle


  /** Number of divisions to a full angle to get a sextant. */
  val SextantDivisions: Int = 6

  /** A sextant in degrees. */
  val SextantInDegrees: Int =
    FullAngleInDegrees / SextantDivisions

  /** A sextant in radians. */
  val SextantInRadians: Double =
    StraightAngleInRadians / SextantDivisions.toDouble

  /** A sextant in gradians. */
  val SextantAngleInGradians: Double =
    FullAngleInGradians / SextantDivisions

  /** A sextant. */
  val Sextant = Angle(SextantInDegrees)


  /** Number of divisions to a full angle to get a clock position angle. */
  val ClockPositionAngleDivisions: Int = 12

  /** A clock position angle in degrees. */
  val ClockPositionAngleInDegrees: Int =
    FullAngleInDegrees / ClockPositionAngleDivisions

  /** A clock position angle in radians. */
  val ClockPositionAngleInRadians: Double =
    FullAngleInRadians / ClockPositionAngleDivisions.toDouble

  /** A clock position angle in gradians. */
  val ClockPositionAngleInGradians: Double =
    FullAngleInGradians / ClockPositionAngleDivisions

  /** A clock position angle. */
  val ClockPositionAngle = Angle(ClockPositionAngleInDegrees)


  /** Number of divisions to a full angle to get an hour angle. */
  val HourAngleDivisions: Int = 24

  /** An hour angle in degrees. */
  val HourAngleInDegrees: Int =
    FullAngleInDegrees / HourAngleDivisions

  /** An hour angle in radians. */
  val HourAngleInRadians: Double =
    FullAngleInRadians / HourAngleDivisions.toDouble

  /** An hour angle in gradians. */
  val HourAngleInGradians: Double =
    FullAngleInGradians / HourAngleDivisions

  /** An hour angle. */
  val HourAngle = Angle(HourAngleInDegrees)


  /** Number of divisions to a full angle to get a compass point. */
  val CompassPointDivisions: Int = 32

  /** A compass point in degrees. */
  val CompassPointInDegrees: Int =
    FullAngleInDegrees / CompassPointDivisions

  /** A compass point in radians. */
  val CompassPointInRadians: Double =
    FullAngleInRadians / CompassPointDivisions.toDouble

  /** A compass point in gradians. */
  val CompassPointInGradians: Double =
    FullAngleInGradians / CompassPointDivisions

  /** A compass point. */
  val CompassPoint = Angle(CompassPointInDegrees)


  /** Number of divisions to a full angle to get a binary degree. */
  val BinaryDegreeDivisions: Int = 256

  /** A binary degree in degrees. */
  val BinaryDegreeInDegrees: Int =
    FullAngleInDegrees / BinaryDegreeDivisions

  /** A binary degree in radians. */
  val BinaryDegreeInRadians: Double =
    FullAngleInRadians / BinaryDegreeDivisions.toDouble

  /** A binary degree in gradians. */
  val BinaryDegreeInGradians: Double =
    FullAngleInGradians / BinaryDegreeDivisions

  /** A binary degree. */
  val BinaryDegree = Angle(BinaryDegreeInDegrees)


  /**
   * Creates a new [[Angle]] instance on the basis of given angle value.
   *
   * @param valueInDegrees
   */
  def apply(valueInDegrees: Double): Angle = {
    new Angle(valueInDegrees)
  }

  /**
   *
   *
   * @param valueInDegrees
   */
  def normalized(valueInDegrees: Double): Angle = {
    apply(MathUtils.normalizeDegs(valueInDegrees))
  }

  /**
   * Creates a new [[Angle]] instance on the basis of given angle value.
   *
   * @param valueInDegrees
   */
  def normalizedPos(valueInDegrees: Double): Angle = {
    apply(MathUtils.normalizeToPosDegs(valueInDegrees))
  }

  /**
   * Creates a new [[Angle]] instance on the basis of given angle value.
   *
   * @param valueInDegrees
   */
  def normalizedNeg(valueInDegrees: Double): Angle = {
    apply(MathUtils.normalizeToNegDegs(valueInDegrees))
  }

  /**
   *
   *
   * @param sin
   *
   * @return
   */
  def fromSin(sin: Double): Angle = {
    val angleValue =
      math.toDegrees(MathUtils.asin(sin))

    apply(angleValue)
  }

  /**
   *
   *
   * @param cos
   *
   * @return
   */
  def fromCos(cos: Double): Angle = {
    val angleValue =
      math.toDegrees(MathUtils.acos(cos))

    apply(angleValue)
  }

  /**
   *
   *
   * @param tan
   *
   * @return
   */
  def fromTan(tan: Double): Angle = {
    val angleValue =
      math.toDegrees(MathUtils.atan(tan))

    apply(angleValue)
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
    extends AbstractMagnitude[Angle](inDegrees)
            with FlatMap[Angle, Double] {

  /** The value of this angle in radians. */
  lazy val inRadians: Double = {
    inDegrees * Angle.DegreeInRadians
  }

  /** The value of this angle in gradians. */
  lazy val inGradians: Double = {
    inDegrees * Angle.DegreeInGradians
  }

  /** */
  lazy val normalized: Angle = {
    Angle(MathUtils.normalizeDegs(inDegrees))
  }

  /** */
  lazy val normalizedPos: Angle = {
    Angle(MathUtils.normalizeToPosDegs(inDegrees))
  }

  /** */
  lazy val normalizedNeg: Angle = {
    Angle(MathUtils.normalizeToNegDegs(inDegrees))
  }

  /** */
  lazy val sin: Double = MathUtils.sinRads(inRadians)

  /** */
  lazy val sinh: Double = MathUtils.sinhRads(inRadians)

  /** */
  lazy val cos: Double = MathUtils.cosRads(inRadians)

  /** */
  lazy val cosh: Double = MathUtils.coshRads(inRadians)

  /** */
  lazy val tan: Double = MathUtils.tanRads(inRadians)

  /** */
  lazy val tanh: Double = MathUtils.tanhRads(inRadians)

  /** */
  lazy val isZero: Boolean = inDegrees == 0

  /** */
  lazy val isRight: Boolean =
    inDegrees == Angle.RightAngleInDegrees

  /** */
  lazy val isStraight: Boolean =
    inDegrees == Angle.StraightAngleInDegrees

  /** */
  lazy val isFull: Boolean =
    inDegrees == Angle.FullAngleInDegrees

  /**
   *
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
   * @param other
   *
   * @return
   */
  def + (other: Angle): Angle = {
    Angle(inDegrees + other.inDegrees)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def - (other: Angle): Angle = {
    Angle(inDegrees - other.inDegrees)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  def / (other: Angle): Double = {
    inDegrees / other.inDegrees
  }

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  def + (angleInDegrees: Double): Angle = {
    Angle(inDegrees + angleInDegrees)
  }

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  def - (angleInDegrees: Double): Angle = {
    Angle(inDegrees - angleInDegrees)
  }

  /**
   *
   *
   * @param factor
   *
   * @return
   */
  def * (factor: Double): Angle = {
    Angle(inDegrees * factor)
  }

  /**
   *
   *
   * @param divider
   *
   * @return
   */
  def / (divider: Double): Angle = {
    Angle(inDegrees / divider)
  }

}
