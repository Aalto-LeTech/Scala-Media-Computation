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
 * Companion object for the [[Vol]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Vol {

  /** */
  lazy val Zero = Vol(0.0)

  /** */
  lazy val One = Vol(1.0)

  /** */
  lazy val PositiveInfinity = Vol(Double.PositiveInfinity)

  /** */
  lazy val NegativeInfinity = Vol(Double.NegativeInfinity)

  /** */
  lazy val NaN = Vol(Double.NaN)

  /**
   * Creates a new [[Vol]] instance on the basis of given area value.
   *
   * @param valueInPixels
   */
  @inline
  def apply(valueInPixels: Double): Vol = {
    require(
      valueInPixels >= 0,
      s"Volume cannot be negative (was $valueInPixels)")

    new Vol(valueInPixels)
  }

  /**
   * Creates a new [[Vol]] instance on the basis of cube's side length.
   *
   * @param sideLengthInPixels
   *
   * @return
   */
  @inline
  def forCube(sideLengthInPixels: Double): Vol = {
    require(
      sideLengthInPixels >= 0,
      s"Cube's side length cannot be negative (was $sideLengthInPixels)")

    apply(sideLengthInPixels * sideLengthInPixels * sideLengthInPixels)
  }

  /**
   * Creates a new [[Vol]] instance on the basis of cube's side length.
   *
   * @param sideLength
   *
   * @return
   */
  @inline
  def forCube(sideLength: Len): Vol = {
    forCube(sideLength.inPixels)
  }

  /**
   * Creates a new [[Vol]] instance on the basis of
   * cuboid's length, width, and height.
   *
   * @param lengthInPixels
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  @inline
  def forCuboid(
      lengthInPixels: Double,
      widthInPixels: Double,
      heightInPixels: Double): Vol = {

    require(
      lengthInPixels >= 0,
      s"Cuboid's length cannot be negative (was $lengthInPixels)")

    require(
      widthInPixels >= 0,
      s"Cuboid's width cannot be negative (was $widthInPixels)")

    require(
      heightInPixels >= 0,
      s"Cuboid's height cannot be negative (was $heightInPixels)")

    apply(lengthInPixels * widthInPixels * heightInPixels)
  }

  /**
   * Creates a new [[Vol]] instance on the basis of
   * cuboid's length, width, and height.
   *
   * @param length
   * @param width
   * @param height
   *
   * @return
   */
  @inline
  def forCuboid(
      length: Len,
      width: Len,
      height: Len): Vol = {

    forCuboid(
      length.inPixels,
      width.inPixels,
      height.inPixels)
  }

  /**
   * Creates a new [[Vol]] instance on the basis of
   * cuboid's base area and height.
   *
   * @param baseArea
   * @param heightInPixels
   *
   * @return
   */
  @inline
  def forCuboid(
      baseArea: Area,
      heightInPixels: Double): Vol = {

    require(
      baseArea.inPixels >= 0,
      s"Cuboid's base area cannot be negative (was ${baseArea.inPixels})")

    require(
      heightInPixels >= 0,
      s"Cuboid's height cannot be negative (was $heightInPixels)")

    apply(baseArea.inPixels * heightInPixels)
  }

  /**
   * Creates a new [[Vol]] instance on the basis of
   * cuboid's base area and height.
   *
   * @param baseArea
   * @param height
   *
   * @return
   */
  @inline
  def forCuboid(
      baseArea: Area,
      height: Len): Vol = {

    forCuboid(baseArea, height.inPixels)
  }

  /**
   * Creates a new [[Vol]] instance on the basis
   * of cylinder's radius and height.
   *
   * @param radiusInPixels
   * @param heightInPixels
   *
   * @return
   */
  @inline
  def forCylinder(radiusInPixels: Double, heightInPixels: Double): Vol = {
    require(
      radiusInPixels >= 0,
      s"Cylinder's radius cannot be negative (was $radiusInPixels)")

    require(
      heightInPixels >= 0,
      s"Cylinder's height cannot be negative (was $heightInPixels)")

    apply(math.Pi * radiusInPixels * radiusInPixels * heightInPixels)
  }

  /**
   * Creates a new [[Vol]] instance on the basis
   * of cylinder's radius and height.
   *
   * @param radius
   * @param height
   *
   * @return
   */
  @inline
  def forCylinder(radius: Len, height: Len): Vol = {
    forCylinder(radius.inPixels, height.inPixels)
  }

  /**
   * Creates a new [[Vol]] instance on the basis
   * of cone's base radius and height.
   *
   * @param radiusInPixels
   * @param heightInPixels
   *
   * @return
   */
  @inline
  def forCone(radiusInPixels: Double, heightInPixels: Double): Vol = {
    require(
      radiusInPixels >= 0,
      s"Cone's radius cannot be negative (was $radiusInPixels)")

    require(
      heightInPixels >= 0,
      s"Cone's height cannot be negative (was $heightInPixels)")

    apply(math.Pi * radiusInPixels * radiusInPixels * heightInPixels / 3.0)
  }

  /**
   * Creates a new [[Vol]] instance on the basis
   * of cone's base radius and height.
   *
   * @param radius
   * @param height
   *
   * @return
   */
  @inline
  def forCone(radius: Len, height: Len): Vol = {
    forCone(radius.inPixels, height.inPixels)
  }

  /**
   * Creates a new [[Vol]] instance on the sphere's radius.
   *
   * @param radiusInPixels
   *
   * @return
   */
  @inline
  def forSphere(radiusInPixels: Double): Vol = {
    require(
      radiusInPixels >= 0,
      s"Sphere's radius cannot be negative (was $radiusInPixels)")

    apply((4.0 * math.Pi * radiusInPixels * radiusInPixels * radiusInPixels) / 3.0)
  }

  /**
   * Creates a new [[Vol]] instance on the sphere's radius.
   *
   * @param radius
   *
   * @return
   */
  @inline
  def forSphere(radius: Len): Vol = {
    forSphere(radius.inPixels)
  }

  /**
   * Creates a new [[Vol]] instance on the basis
   * of cylinder's radius and height.
   *
   * @param baseArea
   * @param heightInPixels
   *
   * @return
   */
  @inline
  def forCylinder(
      baseArea: Area,
      heightInPixels: Double): Vol = {

    require(
      heightInPixels >= 0,
      s"Cylinder's height cannot be negative (was $heightInPixels)")

    apply(baseArea.inPixels * heightInPixels)
  }

  /**
   * Creates a new [[Vol]] instance on the basis
   * of cylinder's radius and height.
   *
   * @param baseArea
   * @param height
   *
   * @return
   */
  @inline
  def forCylinder(baseArea: Area, height: Len): Vol = {
    forCylinder(baseArea, height.inPixels)
  }

  /**
   * Creates a new [[Vol]] instance on the basis
   * of pyramid's base area and height.
   *
   * @param baseArea
   * @param heightInPixels
   *
   * @return
   */
  @inline
  def forPyramid(baseArea: Area, heightInPixels: Double): Vol = {
    require(
      heightInPixels >= 0,
      s"Pyramid's height cannot be negative (was $heightInPixels)")

    apply(baseArea.inPixels * heightInPixels / 3.0)
  }

  /**
   * Creates a new [[Vol]] instance on the basis
   * of pyramid's base area and height.
   *
   * @param baseArea
   * @param height
   *
   * @return
   */
  @inline
  def forPyramid(baseArea: Area, height: Len): Vol = {
    forPyramid(baseArea, height.inPixels)
  }

  /**
   * Creates a new [[Vol]] instance on the basis
   * of prism's base area and height.
   *
   * @param baseArea
   * @param heightInPixels
   *
   * @return
   */
  @inline
  def forPrism(baseArea: Area, heightInPixels: Double): Vol = {
    require(
      heightInPixels >= 0,
      s"Prism's height cannot be negative (was $heightInPixels)")

    apply(baseArea.inPixels * heightInPixels)
  }

  /**
   * Creates a new [[Vol]] instance on the basis
   * of prism's base area and height.
   *
   * @param baseArea
   * @param height
   *
   * @return
   */
  @inline
  def forPrism(baseArea: Area, height: Len): Vol = {
    forPrism(baseArea, height.inPixels)
  }

}




/**
 * Volume of an object.
 *
 * @param inPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Vol private(
    inPixels: Double)
    extends Magnitude[Vol]
        with FlatMap[Vol, Double]
        with Ordered[Vol] {

  /**
   *
   *
   * @return
   */
  @inline
  protected final
  def value: Double = inPixels

  /**
   *
   * @param f
   *
   * @return
   */
  override final
  def map(f: (Double) => Double): Vol = {
    Vol(f(inPixels))
  }

  /**
   *
   *
   * @param that
   *
   * @return
   */
  @inline
  override final
  def compare(that: Vol): Int = {
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
  final
  def + (offset: Vol): Vol = {
    Vol(inPixels + offset.inPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  final
  def - (offset: Vol): Vol = {
    Vol(inPixels - offset.inPixels)
  }

  /**
   *
   *
   * @param divider
   *
   * @return
   */
  @inline
  final
  def / (divider: Vol): Double = {
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
  final
  def / (divider: Area): Len = {
    Len(inPixels / divider.inPixels)
  }

  /**
   *
   *
   * @param divider
   *
   * @return
   */
  @inline
  final
  def / (divider: Len): Area = {
    Area(inPixels / divider.inPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  final
  def + (offset: Double): Vol = {
    Vol(inPixels + offset)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  final
  def - (offset: Double): Vol = {
    Vol(inPixels - offset)
  }

  /**
   *
   *
   * @param factor
   *
   * @return
   */
  @inline
  final
  def * (factor: Double): Vol = {
    Vol(inPixels * factor)
  }

  /**
   *
   *
   * @param divider
   *
   * @return
   */
  @inline
  final
  def / (divider: Double): Vol = {
    Vol(inPixels / divider)
  }

  /**
   * Returns the minimum of the given objects.
   *
   * @return
   */
  @inline
  override final
  def min(others: Vol*): Vol = (this +: others).min

  /**
   * Returns the maximum of the given objects.
   *
   * @return
   */
  @inline
  override final
  def max(others: Vol*): Vol = (this +: others).max

  /**
   *
   *
   * @return
   */
  @inline
  override final
  def toString: String = {
    s"Vol($inPixels px3)"
  }

}
