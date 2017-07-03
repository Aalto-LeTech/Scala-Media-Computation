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



  /** An angle that represents positive infinity. */
  lazy val PositiveInfinity = Angle(Double.PositiveInfinity)

  /** An angle that represents negative infinity. */
  lazy val NegativeInfinity = Angle(Double.NegativeInfinity)

  /** An angle that represents a not-a-number. */
  lazy val NaN = Angle(Double.NaN)

  /** A zero angle. */
  lazy val Zero = Angle(0.0)

  /** An angle of one degree. */
  lazy val Deg1 = Angle(1.0)

  /** An angle of 10 degrees. */
  lazy val Deg10 = Angle(10.0)

  /** An angle of 20 degrees. */
  lazy val Deg20 = Angle(20.0)

  /** An angle of 30 degrees. */
  lazy val Deg30 = Angle(30.0)

  /** An angle of 1/6 * Pi radians, i.e., 30 degrees. */
  lazy val RadOneSixthPi = Deg30

  /** An angle of 40 degrees. */
  lazy val Deg40 = Angle(40.0)

  /** An angle of 45 degrees. */
  lazy val Deg45 = Angle(45.0)

  /** An angle of 1/4 * Pi radians, i.e., 45 degrees. */
  lazy val RadOneFourthPi = Deg45

  /** An angle of 50 degrees. */
  lazy val Deg50 = Angle(50.0)

  /** An angle of 60 degrees. */
  lazy val Deg60 = Angle(60.0)

  /** An angle of 1/3 * Pi radians, i.e., 60 degrees. */
  lazy val RadOneThirdPi = Deg60

  /** An angle of 70 degrees. */
  lazy val Deg70 = Angle(70.0)

  /** An angle of 80 degrees. */
  lazy val Deg80 = Angle(80.0)

  /** An angle of 90 degrees, i.e., a right angle. */
  lazy val Deg90 = RightAngle

  /** An angle of 1/2 * Pi radians, i.e., a right angle (90 degrees). */
  lazy val RadHalfPi = RightAngle

  /** An angle of 100 degrees. */
  lazy val Deg100 = Angle(100.0)

  /** An angle of 110 degrees. */
  lazy val Deg110 = Angle(110.0)

  /** An angle of 120 degrees. */
  lazy val Deg120 = Angle(120.0)

  /** An angle of 2/3 * Pi radians, i.e., 120 degrees. */
  lazy val RadTwoThirdsPi = Deg120

  /** An angle of 130 degrees. */
  lazy val Deg130 = Angle(130.0)

  /** An angle of 45 degrees. */
  lazy val Deg135 = Angle(135.0)

  /** An angle of 3/4 * Pi radians, i.e., 135 degrees. */
  lazy val RadThreeFourthsPi = Deg135

  /** An angle of 140 degrees. */
  lazy val Deg140 = Angle(140.0)

  /** An angle of 150 degrees. */
  lazy val Deg150 = Angle(150.0)

  /** An angle of 5/6 * Pi radians, i.e., 150 degrees. */
  lazy val RadFiveSixthsPi = Deg150

  /** An angle of 160 degrees. */
  lazy val Deg160 = Angle(160.0)

  /** An angle of 170 degrees. */
  lazy val Deg170 = Angle(170.0)

  /** An angle of 180 degrees, i.e., a straight angle. */
  lazy val Deg180 = StraightAngle

  /** An angle of Pi radians, i.e., a straight angle (180 degrees). */
  lazy val RadPi = StraightAngle

  /** An angle of 190 degrees. */
  lazy val Deg190 = Angle(190.0)

  /** An angle of 200 degrees. */
  lazy val Deg200 = Angle(200.0)

  /** An angle of 210 degrees. */
  lazy val Deg210 = Angle(210.0)

  /** An angle of 7/6 * Pi radians, i.e., 210 degrees. */
  lazy val RadSevenSixthsPi = Deg210

  /** An angle of 220 degrees. */
  lazy val Deg220 = Angle(220.0)

  /** An angle of 255 degrees. */
  lazy val Deg225 = Angle(225.0)

  /** An angle of 5/4 * Pi radians, i.e., 225 degrees. */
  lazy val RadFiveFourthsPi = Deg225

  /** An angle of 230 degrees. */
  lazy val Deg230 = Angle(230.0)

  /** An angle of 240 degrees. */
  lazy val Deg240 = Angle(240.0)

  /** An angle of 4/3 * Pi radians, i.e., 240 degrees. */
  lazy val RadFourThirdsPi = Deg240

  /** An angle of 250 degrees. */
  lazy val Deg250 = Angle(250.0)

  /** An angle of 260 degrees. */
  lazy val Deg260 = Angle(260.0)

  /** An angle of 270 degrees. */
  lazy val Deg270 = Angle(270.0)

  /** An angle of 3/2 * Pi radians. */
  lazy val RadThreeHalfsPi = Deg270

  /** An angle of 280 degrees. */
  lazy val Deg280 = Angle(280.0)

  /** An angle of 290 degrees. */
  lazy val Deg290 = Angle(290.0)

  /** An angle of 300 degrees. */
  lazy val Deg300 = Angle(300.0)

  /** An angle of 5/3 * Pi radians, i.e., 300 degrees. */
  lazy val RadFiveThirdsPi = Deg300

  /** An angle of 310 degrees. */
  lazy val Deg310 = Angle(310.0)

  /** An angle of 315 degrees. */
  lazy val Deg315 = Angle(315.0)

  /** An angle of 7/4 * Pi radians, i.e., 315 degrees. */
  lazy val RadSevenFourthsPi = Deg315

  /** An angle of 320 xdegrees. */
  lazy val Deg320 = Angle(320.0)

  /** An angle of 330 degrees. */
  lazy val Deg330 = Angle(330.0)

  /** An angle of 11/6 * Pi radians, i.e., 330 degrees. */
  lazy val RadElevenSixthsPi = Deg330

  /** An angle of 340 degrees. */
  lazy val Deg340 = Angle(340.0)

  /** An angle of 350 degrees. */
  lazy val Deg350 = Angle(350.0)

  /** An angle of 360 degrees, i.e., a full angle. */
  lazy val Deg360 = FullAngle

  /** An angle of 2 * Pi radians, i.e., a full angle (360 degrees). */
  lazy val RadTwoPi = FullAngle


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
   * @param valueInRadians
   *
   * @return
   */
  def fromRads(valueInRadians: Double): Angle = {
    val angleValue = math.toDegrees(valueInRadians)

    apply(angleValue)
  }

  /**
   *
   *
   * @param valueInGradians
   *
   * @return
   */
  def fromGrads(valueInGradians: Double): Angle = {
    val angleValue = valueInGradians * GradianInDegrees

    apply(angleValue)
  }

  /**
   *
   *
   * @param sin
   *
   * @return
   */
  def fromSin(sin: Double): Angle = {
    apply(MathUtils.asin(sin))
  }

  /**
   *
   *
   * @param cos
   *
   * @return
   */
  def fromCos(cos: Double): Angle = {
    apply(MathUtils.acos(cos))
  }

  /**
   *
   *
   * @param tan
   *
   * @return
   */
  def fromTan(tan: Double): Angle = {
    apply(MathUtils.atan(tan))
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
            with FlatMap[Angle, Double]
            with Ordered[Angle] {

  /** */
  lazy val isCounterClockwise: Boolean =
    isPositive

  /** */
  lazy val isClockwise: Boolean = isNegative

  /** */
  lazy val isCoterminalToZero: Boolean =
    normalizedAbs == Angle.Zero

  /** */
  lazy val isAcute: Boolean =
    isAbsBetweenExclExcl(0, Angle.RightAngleInDegrees)

  /** */
  lazy val isCoterminalToAcute: Boolean =
    isCoterminallyBetweenExclExcl(
      Angle.Zero, Angle.RightAngle)

  /** */
  lazy val isRight: Boolean =
    math.abs(inDegrees) == Angle.RightAngleInDegrees

  /** */
  lazy val isCoterminalToRight: Boolean =
    normalizedAbs == Angle.RightAngle

  /** */
  lazy val isObtuse: Boolean =
    isAbsBetweenExclExcl(
      Angle.RightAngleInDegrees,
      Angle.StraightAngleInDegrees)

  /** */
  lazy val isCoterminalToObtuse: Boolean =
    isCoterminallyBetweenExclExcl(
      Angle.RightAngle, Angle.StraightAngle)

  /** */
  lazy val isStraight: Boolean =
    math.abs(inDegrees) == Angle.StraightAngleInDegrees

  /** */
  lazy val isCoterminalToStraight: Boolean =
    normalizedAbs == Angle.StraightAngle

  /** */
  lazy val isReflex: Boolean =
    isAbsBetweenExclExcl(
      Angle.StraightAngleInDegrees,
      Angle.FullAngleInDegrees)

  /** */
  lazy val isCoterminalToReflex: Boolean =
    isCoterminallyBetweenExclExcl(
      Angle.StraightAngle, Angle.FullAngle)

  /** */
  lazy val isFull: Boolean =
    math.abs(inDegrees) == Angle.FullAngleInDegrees

  /** */
  lazy val isOblique: Boolean =
    inDegrees % Angle.RightAngleInDegrees == 0

  /** The value of this angle in radians. */
  lazy val inRadians: Double =
    inDegrees * Angle.DegreeInRadians

  /** The value of this angle in gradians. */
  lazy val inGradians: Double =
    inDegrees * Angle.DegreeInGradians

  /** */
  lazy val normalized: Angle =
    Angle(MathUtils.normalizeDegs(inDegrees))

  /** */
  lazy val normalizedAbs: Angle =
    Angle(math.abs(MathUtils.normalizeDegs(inDegrees)))

  /** */
  lazy val normalizedPos: Angle =
    Angle(MathUtils.normalizeToPosDegs(inDegrees))

  /** */
  lazy val normalizedNeg: Angle =
    Angle(MathUtils.normalizeToNegDegs(inDegrees))

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

  /**
   *
   *
   * @return
   */
  override def inverse: Angle = Angle(-inDegrees)

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isCoterminallyBetweenExclExcl(
      rangeStart: Angle,
      rangeEnd: Angle): Boolean = {

    rangeStart.normalizedAbs < this.normalizedAbs &&
        this.normalizedAbs < rangeEnd.normalizedAbs
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isCoterminallyBetweenExclIncl(
      rangeStart: Angle,
      rangeEnd: Angle): Boolean = {

    rangeStart.normalizedAbs < this.normalizedAbs &&
        this.normalizedAbs <= rangeEnd.normalizedAbs
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isCoterminallyBetweenInclExcl(
      rangeStart: Angle,
      rangeEnd: Angle): Boolean = {

    rangeStart.normalizedAbs <= this.normalizedAbs &&
        this.normalizedAbs < rangeEnd.normalizedAbs
  }

  /**
   *
   *
   * @param rangeStart
   * @param rangeEnd
   *
   * @return
   */
  def isCoterminallyBetweenInclIncl(
      rangeStart: Angle,
      rangeEnd: Angle): Boolean = {

    rangeStart.normalizedAbs <= this.normalizedAbs &&
        this.normalizedAbs <= rangeEnd.normalizedAbs
  }

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
   * @param that
   *
   * @return
   */
  override def compare(that: Angle): Int = {
    inDegrees.compare(that.inDegrees)
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

  /**
   *
   *
   * @return
   */
  override def unary_+(): Angle = this

}
