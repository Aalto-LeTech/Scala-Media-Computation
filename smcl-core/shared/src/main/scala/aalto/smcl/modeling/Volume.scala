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
 * Companion object for the [[Volume]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Volume {

  /**
   * Creates a new [[Volume]] instance on the basis of given area value.
   *
   * @param valueInPixels
   */
  def apply(valueInPixels: Double): Volume = {
    require(
      valueInPixels >= 0,
      s"Volume cannot be negative (was $valueInPixels)")

    new Volume(valueInPixels)
  }

  /**
   * Creates a new [[Volume]] instance on the basis of cube's side length.
   *
   * @param sideLengthInPixels
   *
   * @return
   */
  def forCube(sideLengthInPixels: Double): Volume = {
    require(
      sideLengthInPixels >= 0,
      s"Cube's side length cannot be negative (was $sideLengthInPixels)")

    apply(math.pow(sideLengthInPixels, 3))
  }

  /**
   * Creates a new [[Volume]] instance on the basis of
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
      heightInPixels: Double): Volume = {

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
   * Creates a new [[Volume]] instance on the basis of
   * cuboid's base area and height.
   *
   * @param baseArea
   * @param heightInPixels
   *
   * @return
   */
  def forCuboid(
      baseArea: Area,
      heightInPixels: Double): Volume = {

    require(
      baseArea.inPixels >= 0,
      s"Cuboid's base area cannot be negative (was ${baseArea.inPixels})")

    require(
      heightInPixels >= 0,
      s"Cuboid's height cannot be negative (was $heightInPixels)")

    apply(baseArea.inPixels * heightInPixels)
  }

  /**
   * Creates a new [[Volume]] instance on the basis
   * of cylinder's radius and height.
   *
   * @param radiusInPixels
   * @param heightInPixels
   *
   * @return
   */
  def forCylinder(radiusInPixels: Double, heightInPixels: Double): Volume = {
    require(
      radiusInPixels >= 0,
      s"Cylinder's radius cannot be negative (was $radiusInPixels)")

    require(
      heightInPixels >= 0,
      s"Cylinder's height cannot be negative (was $heightInPixels)")

    apply(math.Pi * math.pow(radiusInPixels, 2) * heightInPixels)
  }

  /**
   * Creates a new [[Volume]] instance on the basis
   * of cone's base radius and height.
   *
   * @param radiusInPixels
   * @param heightInPixels
   *
   * @return
   */
  def forCone(radiusInPixels: Double, heightInPixels: Double): Volume = {
    require(
      radiusInPixels >= 0,
      s"Cone's radius cannot be negative (was $radiusInPixels)")

    require(
      heightInPixels >= 0,
      s"Cone's height cannot be negative (was $heightInPixels)")

    apply(math.Pi * math.pow(radiusInPixels, 2) * heightInPixels / 3.0)
  }

  /**
   * Creates a new [[Volume]] instance on the sphere's radius.
   *
   * @param radiusInPixels
   *
   * @return
   */
  def forSphere(radiusInPixels: Double): Volume = {
    require(
      radiusInPixels >= 0,
      s"Sphere's radius cannot be negative (was $radiusInPixels)")

    apply((4.0 * math.Pi * math.pow(radiusInPixels, 3)) / 3.0)
  }

  /**
   * Creates a new [[Volume]] instance on the basis
   * of cylinder's radius and height.
   *
   * @param baseArea
   * @param heightInPixels
   *
   * @return
   */
  def forCylinder(baseArea: Area, heightInPixels: Double): Volume = {
    require(
      baseArea.inPixels >= 0,
      s"Cylinder's base area cannot be negative (was ${baseArea.inPixels})")

    require(
      heightInPixels >= 0,
      s"Cylinder's height cannot be negative (was $heightInPixels)")

    apply(baseArea.inPixels * heightInPixels)
  }

  /**
   * Creates a new [[Volume]] instance on the basis
   * of pyramid's base area and height.
   *
   * @param baseArea
   * @param heightInPixels
   *
   * @return
   */
  def forPyramid(baseArea: Area, heightInPixels: Double): Volume = {
    require(
      baseArea.inPixels >= 0,
      s"Pyramid's base area cannot be negative (was ${baseArea.inPixels})")

    require(
      heightInPixels >= 0,
      s"Pyramid's height cannot be negative (was $heightInPixels)")

    apply(baseArea.inPixels * heightInPixels / 3.0)
  }

  /**
   * Creates a new [[Volume]] instance on the basis
   * of prism's base area and height.
   *
   * @param baseArea
   * @param heightInPixels
   *
   * @return
   */
  def forPrism(baseArea: Area, heightInPixels: Double): Volume = {
    require(
      baseArea.inPixels >= 0,
      s"Prism's base area cannot be negative (was ${baseArea.inPixels})")

    require(
      heightInPixels >= 0,
      s"Prism's height cannot be negative (was $heightInPixels)")

    apply(baseArea.inPixels * heightInPixels)
  }

}




/**
 * Volume of an object.
 *
 * @param inPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Volume private(
    inPixels: Double)
    extends Magnitude[Volume](inPixels)
            with FlatMap[Volume, Double] {

  /**
   *
   * @param f
   *
   * @return
   */
  @inline
  override
  def map(f: (Double) => Double): Volume = {
    Volume(f(inPixels))
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  def + (offset: Volume): Volume = {
    Volume(inPixels + offset.inPixels)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  def - (offset: Volume): Volume = {
    Volume(inPixels - offset.inPixels)
  }

  /**
   *
   *
   * @param divider
   *
   * @return
   */
  def / (divider: Volume): Double = {
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
  def + (offset: Double): Volume = {
    Volume(inPixels + offset)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  def - (offset: Double): Volume = {
    Volume(inPixels - offset)
  }

  /**
   *
   *
   * @param factor
   *
   * @return
   */
  def * (factor: Double): Volume = {
    Volume(inPixels * factor)
  }

  /**
   *
   *
   * @param divider
   *
   * @return
   */
  def / (divider: Double): Volume = {
    Volume(inPixels / divider)
  }

}
