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
   * Creates a new [[aalto.smcl.modeling.Vol]] instance on the basis of given area value.
   *
   * @param valueInPixels
   */
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
  def forCube(sideLengthInPixels: Double): Vol = {
    require(
      sideLengthInPixels >= 0,
      s"Cube's side length cannot be negative (was $sideLengthInPixels)")

    apply(math.pow(sideLengthInPixels, 3))
  }

  /**
   * Creates a new [[Vol]] instance on the basis of cube's side length.
   *
   * @param sideLength
   *
   * @return
   */
  def forCube(sideLength: Len): Vol = sideLength.cube

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
   * @param lengthInPixels
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  def forCuboid(
      lengthInPixels: Len,
      widthInPixels: Len,
      heightInPixels: Len): Vol = {

    lengthInPixels * widthInPixels * heightInPixels
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
   * @param heightInPixels
   *
   * @return
   */
  def forCuboid(
      baseArea: Area,
      heightInPixels: Len): Vol = {

    baseArea * heightInPixels
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
  def forCylinder(radiusInPixels: Double, heightInPixels: Double): Vol = {
    require(
      radiusInPixels >= 0,
      s"Cylinder's radius cannot be negative (was $radiusInPixels)")

    require(
      heightInPixels >= 0,
      s"Cylinder's height cannot be negative (was $heightInPixels)")

    apply(math.Pi * math.pow(radiusInPixels, 2) * heightInPixels)
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
  def forCylinder(radius: Len, height: Len): Vol = {
    math.Pi * radius.square * height
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
  def forCone(radiusInPixels: Double, heightInPixels: Double): Vol = {
    require(
      radiusInPixels >= 0,
      s"Cone's radius cannot be negative (was $radiusInPixels)")

    require(
      heightInPixels >= 0,
      s"Cone's height cannot be negative (was $heightInPixels)")

    apply(math.Pi * math.pow(radiusInPixels, 2) * heightInPixels / 3.0)
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
  def forCone(radius: Len, height: Len): Vol = {
    math.Pi * radius.square * height / 3.0
  }

  /**
   * Creates a new [[Vol]] instance on the sphere's radius.
   *
   * @param radiusInPixels
   *
   * @return
   */
  def forSphere(radiusInPixels: Double): Vol = {
    require(
      radiusInPixels >= 0,
      s"Sphere's radius cannot be negative (was $radiusInPixels)")

    apply((4.0 * math.Pi * math.pow(radiusInPixels, 3)) / 3.0)
  }

  /**
   * Creates a new [[Vol]] instance on the sphere's radius.
   *
   * @param radius
   *
   * @return
   */
  def forSphere(radius: Len): Vol = {
    4.0 * math.Pi * radius.cube / 3.0
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
  def forCylinder(baseArea: Area, height: Len): Vol = {
    baseArea * height
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
  def forPyramid(baseArea: Area, height: Len): Vol = {
    baseArea * height / 3.0
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
  def forPrism(baseArea: Area, height: Len): Vol = {
    baseArea * height
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
    extends AbstractMagnitude[Vol](inPixels)
            with FlatMap[Vol, Double]
            with Ordered[Vol] {

  /**
   *
   * @param f
   *
   * @return
   */
  @inline
  override
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
  override def compare(that: Vol): Int = {
    inPixels.compare(that.inPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
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
  def / (divider: Double): Vol = {
    Vol(inPixels / divider)
  }

  /**
   * Returns the minimum of the given objects.
   *
   * @return
   */
  override
  def min(others: Vol*): Vol = (this +: others).min

  /**
   * Returns the maximum of the given objects.
   *
   * @return
   */
  override
  def max(others: Vol*): Vol = (this +: others).max

}
