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

package smcl.modeling


import smcl.infrastructure.FlatMap
import smcl.modeling.misc.Magnitude




/**
 * Companion object for the [[Area]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Area {

  /** */
  lazy val Zero: Area = Area(0.0)

  /** */
  lazy val One: Area = Area(1.0)

  /** */
  lazy val PositiveInfinity: Area = Area(Double.PositiveInfinity)

  /** */
  lazy val NegativeInfinity: Area = Area(Double.NegativeInfinity)

  /** */
  lazy val NaN: Area = Area(Double.NaN)

  /** A constant for calculating areas of pentagons. */
  private
  lazy val PentagonAreaConstant: Double =
    1.25 * math.sqrt(2.5 + (math.sqrt(5.0) / 2.0))

  /** A constant for calculating areas of hexagons. */
  private
  lazy val HexagonAreaConstant: Double =
    1.5 * math.sqrt(3.0)

  /**
   * Creates a new [[Area]] instance on the basis of given area value.
   *
   * @param valueInPixels
   */
  @inline
  def apply(valueInPixels: Double): Area = {
    require(
      valueInPixels >= 0,
      s"Area cannot be negative (was $valueInPixels)")

    new Area(valueInPixels)
  }

  /**
   * Creates a new [[Area]] instance on the basis of triangle's width and height.
   *
   * @param baseLengthInPixels
   * @param heightInPixels
   *
   * @return
   */
  @inline
  def forTriangle(baseLengthInPixels: Double, heightInPixels: Double): Area = {
    require(
      baseLengthInPixels >= 0,
      s"Triangle's base length cannot be negative (was $baseLengthInPixels)")

    require(
      heightInPixels >= 0,
      s"Triangle's height cannot be negative (was $heightInPixels)")

    apply(baseLengthInPixels * heightInPixels / 2.0)
  }

  /**
   * Creates a new [[Area]] instance on the basis of triangle's width and height.
   *
   * @param baseLength
   * @param height
   *
   * @return
   */
  @inline
  def forTriangle(baseLength: Len, height: Len): Area = {
    forTriangle(baseLength.inPixels, height.inPixels)
  }

  /**
   * Creates a new [[Area]] instance on the basis of circle's radius.
   *
   * @param radiusInPixels
   */
  @inline
  def forCircle(radiusInPixels: Double): Area = {
    require(
      radiusInPixels >= 0,
      s"Circle's radius cannot be negative (was $radiusInPixels)")

    apply(math.Pi * math.pow(radiusInPixels, 2))
  }

  /**
   * Creates a new [[Area]] instance on the basis of circle's radius.
   *
   * @param radius
   */
  @inline
  def forCircle(radius: Len): Area = {
    forCircle(radius.inPixels)
  }

  /**
   * Creates a new [[Area]] instance on the basis of circle's diameter.
   *
   * @param diameterInPixels
   */
  @inline
  def forCircleByDiam(diameterInPixels: Double): Area = {
    require(
      diameterInPixels >= 0,
      s"Circle's diameter cannot be negative (was $diameterInPixels)")

    apply(math.Pi * math.pow(diameterInPixels, 2) / 4.0)
  }

  /**
   * Creates a new [[Area]] instance on the basis of circle's diameter.
   *
   * @param diameter
   */
  @inline
  def forCircleByDiam(diameter: Len): Area = {
    forCircleByDiam(diameter.inPixels)
  }

  /**
   * Creates a new [[Area]] instance on the basis of pentagon's circumradius.
   *
   * @param circumRadiusInPixels
   */
  @inline
  def forPentagon(circumRadiusInPixels: Double): Area = {
    require(
      circumRadiusInPixels >= 0,
      s"Pentagon's circumradius cannot be negative (was $circumRadiusInPixels)")

    val areaValue =
      PentagonAreaConstant *
          circumRadiusInPixels *
          circumRadiusInPixels

    apply(areaValue)
  }

  /**
   * Creates a new [[Area]] instance on the basis of pentagon's circumradius.
   *
   * @param circumRadius
   */
  @inline
  def forPentagon(circumRadius: Len): Area = {
    forPentagon(circumRadius.inPixels)
  }

  /**
   * Creates a new [[Area]] instance on the basis of hexagon's circumradius.
   *
   * @param circumRadiusInPixels
   */
  @inline
  def forHexagon(circumRadiusInPixels: Double): Area = {
    require(
      circumRadiusInPixels >= 0,
      s"Hexagon's circumradius cannot be negative (was $circumRadiusInPixels)")

    val areaValue =
      HexagonAreaConstant *
          circumRadiusInPixels *
          circumRadiusInPixels

    apply(areaValue)
  }

  /**
   * Creates a new [[Area]] instance on the basis of hexagon's circumradius.
   *
   * @param circumRadius
   */
  @inline
  def forHexagon(circumRadius: Len): Area = {
    forHexagon(circumRadius.inPixels)
  }

  /**
   * Creates a new [[Area]] instance on the basis of ellipse's radius.
   *
   * @param semiMajorAxisInPixels
   * @param semiMinorAxisInPixels
   */
  @inline
  def forEllipse(
      semiMajorAxisInPixels: Double,
      semiMinorAxisInPixels: Double): Area = {

    require(
      semiMajorAxisInPixels >= 0,
      s"Ellipse's semi-major axis cannot be negative (was $semiMajorAxisInPixels)")

    require(
      semiMinorAxisInPixels >= 0,
      s"Ellipse's semi-minor axis cannot be negative (was $semiMinorAxisInPixels)")

    apply(math.Pi * semiMajorAxisInPixels * semiMinorAxisInPixels)
  }

  /**
   * Creates a new [[Area]] instance on the basis of ellipse's radius.
   *
   * @param semiMajorAxis
   * @param semiMinorAxis
   */
  @inline
  def forEllipse(
      semiMajorAxis: Len,
      semiMinorAxis: Len): Area = {

    forEllipse(semiMajorAxis.inPixels, semiMinorAxis.inPixels)
  }

  /**
   * Creates a new [[Area]] instance on the basis of square's side length.
   *
   * @param sideLengthInPixels
   */
  @inline
  def forSquare(sideLengthInPixels: Double): Area = {
    require(
      sideLengthInPixels >= 0,
      s"Square's side length cannot be negative (was $sideLengthInPixels)")

    apply(sideLengthInPixels * sideLengthInPixels)
  }

  /**
   * Creates a new [[Area]] instance on the basis of square's side length.
   *
   * @param sideLength
   */
  @inline
  def forSquare(sideLength: Len): Area = {
    forSquare(sideLength.inPixels)
  }

  /**
   * Creates a new [[Area]] instance on the basis of rectangle's width and height.
   *
   * @param widthInPixels
   * @param heightInPixels
   */
  @inline
  def forRectangle(widthInPixels: Double, heightInPixels: Double): Area = {
    require(
      widthInPixels >= 0,
      s"Rectangle's width cannot be negative (was $widthInPixels)")

    require(
      heightInPixels >= 0,
      s"Rectangle's height cannot be negative (was $heightInPixels)")

    apply(widthInPixels * heightInPixels)
  }

  /**
   * Creates a new [[Area]] instance on the basis of rectangle's width and height.
   *
   * @param width
   * @param height
   */
  @inline
  def forRectangle(width: Len, height: Len): Area = {
    forRectangle(width.inPixels, height.inPixels)
  }

}




/**
 * Area of an object.
 *
 * @param inPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Area private(
    inPixels: Double)
    extends Magnitude[Area]
        with FlatMap[Area, Double]
        with Ordered[Area] {

  /**
   *
   *
   * @return
   */
  @inline
  protected
  def value: Double = inPixels

  /**
   *
   *
   * @param f
   *
   * @return
   */
  @inline
  override
  def map(f: (Double) => Double): Area = {
    Area(f(inPixels))
  }

  /**
   *
   *
   * @param that
   *
   * @return
   */
  @inline
  override
  def compare(that: Area): Int = {
    inPixels.compare(that.inPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def + (offset: Area): Area = {
    Area(inPixels + offset.inPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def - (offset: Area): Area = {
    Area(inPixels - offset.inPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def + (offset: Double): Area = {
    Area(inPixels + offset)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def - (offset: Double): Area = {
    Area(inPixels - offset)
  }

  /**
   *
   *
   * @param factor
   *
   * @return
   */
  @inline
  def * (factor: Len): Vol = {
    Vol(inPixels * factor.inPixels)
  }

  /**
   *
   *
   * @param divider
   *
   * @return
   */
  @inline
  def / (divider: Area): Double = {
    inPixels / divider.inPixels
  }

  /**
   *
   *
   * @param divider
   *
   * @return
   */
  @inline
  def / (divider: Len): Len = {
    Len(inPixels / divider.inPixels)
  }

  /**
   *
   *
   * @param factor
   *
   * @return
   */
  @inline
  def * (factor: Double): Area = {
    Area(inPixels * factor)
  }

  /**
   *
   *
   * @param divider
   *
   * @return
   */
  @inline
  def / (divider: Double): Area = {
    Area(inPixels / divider)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def squareRoot: Len = Len(math.sqrt(this.inPixels))

  /**
   * Returns the minimum of the given objects.
   *
   * @return
   */
  @inline
  override
  def min(others: Area*): Area = (this +: others).min

  /**
   * Returns the maximum of the given objects.
   *
   * @return
   */
  @inline
  override
  def max(others: Area*): Area = (this +: others).max

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toString: String = {
    s"Area($inPixels px2)"
  }

}
