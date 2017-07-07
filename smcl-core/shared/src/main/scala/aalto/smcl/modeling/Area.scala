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


import aalto.smcl.infrastructure.FlatMap
import aalto.smcl.modeling.misc.AbstractMagnitude




/**
 * Companion object for the [[Area]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Area {

  /** */
  lazy val Zero = Area(0.0)

  /** */
  lazy val One = Area(1.0)

  /** */
  lazy val PositiveInfinity = Area(Double.PositiveInfinity)

  /** */
  lazy val NegativeInfinity = Area(Double.NegativeInfinity)

  /** */
  lazy val NaN = Area(Double.NaN)


  /**
   * Creates a new [[Area]] instance on the basis of given area value.
   *
   * @param valueInPixels
   */
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
  def forTriangle(baseLength: Len, height: Len): Area = {
    baseLength * height / 2.0
  }

  /**
   * Creates a new [[Area]] instance on the basis of circle's radius.
   *
   * @param radiusInPixels
   */
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
  def forCircle(radius: Len): Area = {
    apply(math.Pi * radius.double.inPixels)
  }

  /**
   * Creates a new [[Area]] instance on the basis of circle's diameter.
   *
   * @param diameterInPixels
   */
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
  def forCircleByDiam(diameter: Len): Area = {
    apply(math.Pi * diameter.double.inPixels / 4.0)
  }

  /**
   * Creates a new [[Area]] instance on the basis of square's side length.
   *
   * @param sideLengthInPixels
   */
  def forSquare(sideLengthInPixels: Double): Area = {
    require(
      sideLengthInPixels >= 0,
      s"Square's side length cannot be negative (was $sideLengthInPixels)")

    apply(math.pow(sideLengthInPixels, 2))
  }

  /**
   * Creates a new [[Area]] instance on the basis of square's side length.
   *
   * @param sideLength
   */
  def forSquare(sideLength: Len): Area = sideLength * sideLength

  /**
   * Creates a new [[Area]] instance on the basis of rectangle's width and height.
   *
   * @param widthInPixels
   * @param heightInPixels
   */
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
  def forRectangle(width: Len, height: Len): Area = width * height

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
    extends AbstractMagnitude[Area](inPixels)
            with FlatMap[Area, Double]
            with Ordered[Area] {

  /**
   *
   *
   * @param f
   *
   * @return
   */
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
  override def compare(that: Area): Int = {
    inPixels.compare(that.inPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
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
  def / (divider: Double): Area = {
    Area(inPixels / divider)
  }

  /**
   *
   *
   * @return
   */
  def squareRoot: Len = Len(math.sqrt(this.inPixels))

  /**
   * Returns the minimum of the given objects.
   *
   * @return
   */
  override
  def min(others: Area*): Area = (this +: others).min

  /**
   * Returns the maximum of the given objects.
   *
   * @return
   */
  override
  def max(others: Area*): Area = (this +: others).max

}
