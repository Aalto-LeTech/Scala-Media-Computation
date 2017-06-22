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

package aalto.smcl.geometry


/**
 * Companion object for the [[Area]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Area {

  /**
   * Creates a new [[Area]] instance on the basis of given area value.
   *
   * @param valueInPixels
   */
  def apply(valueInPixels: Double): Area[Double] = {
    validateArea(valueInPixels)

    new Area(valueInPixels)
  }

  /**
   * Creates a new [[Area]] instance on the basis of given area value.
   *
   * @param valueInPixels
   */
  def apply(valueInPixels: Int): Area[Int] = {
    validateArea(valueInPixels)

    new Area(valueInPixels)
  }

  private
  def validateArea(valueInPixels: Double): Unit = {
    require(
      valueInPixels >= 0,
      s"Area cannot be negative (was $valueInPixels)")
  }

  /**
   * Creates a new [[Area]] instance on the basis of triangle's width and height.
   *
   * @param baseLengthInPixels
   * @param heightInPixels
   *
   * @return
   */
  def forTriangle(baseLengthInPixels: Double, heightInPixels: Double): Area[Double] = {
    validateTriangleDimensions(baseLengthInPixels, heightInPixels)

    apply(baseLengthInPixels * heightInPixels / 2.0)
  }

  /**
   * Creates a new [[Area]] instance on the basis of triangle's width and height.
   *
   * @param baseLengthInPixels
   * @param heightInPixels
   *
   * @return
   */
  def forTriangle(baseLengthInPixels: Int, heightInPixels: Int): Area[Int] = {
    validateTriangleDimensions(baseLengthInPixels, heightInPixels)

    apply((baseLengthInPixels * heightInPixels / 2.0).toInt)
  }

  private
  def validateTriangleDimensions(
      baseLengthInPixels: Double,
      heightInPixels: Double): Unit = {

    require(
      baseLengthInPixels >= 0,
      s"Triangle's base length cannot be negative (was $baseLengthInPixels)")

    require(
      heightInPixels >= 0,
      s"Triangle's height cannot be negative (was $heightInPixels)")
  }

  /**
   * Creates a new [[Area]] instance on the basis of circle's radius.
   *
   * @param radiusInPixels
   */
  def forCircle(radiusInPixels: Double): Area[Double] = {
    validateRadius(radiusInPixels)

    apply(math.Pi * math.pow(radiusInPixels, 2))
  }

  /**
   * Creates a new [[Area]] instance on the basis of circle's radius.
   *
   * @param radiusInPixels
   */
  def forCircle(radiusInPixels: Int): Area[Int] = {
    validateRadius(radiusInPixels)

    apply((math.Pi * math.pow(radiusInPixels, 2)).toInt)
  }

  private
  def validateRadius(radiusInPixels: Double): Unit = {
    require(
      radiusInPixels >= 0,
      s"Circle's radius cannot be negative (was $radiusInPixels)")
  }

  /**
   * Creates a new [[Area]] instance on the basis of circle's diameter.
   *
   * @param diameterInPixels
   */
  def forCircleByDiam(diameterInPixels: Double): Area[Double] = {
    validateDiameter(diameterInPixels)

    apply(math.Pi * math.pow(diameterInPixels, 2) / 4.0)
  }

  /**
   * Creates a new [[Area]] instance on the basis of circle's diameter.
   *
   * @param diameterInPixels
   */
  def forCircleByDiam(diameterInPixels: Int): Area[Int] = {
    validateDiameter(diameterInPixels)

    apply((math.Pi * math.pow(diameterInPixels, 2) / 4.0).toInt)
  }

  private
  def validateDiameter(diameterInPixels: Double): Unit = {
    require(
      diameterInPixels >= 0,
      s"Circle's diameter cannot be negative (was $diameterInPixels)")
  }

  /**
   * Creates a new [[Area]] instance on the basis of square's side length.
   *
   * @param sideLengthInPixels
   */
  def forSquare(sideLengthInPixels: Double): Area[Double] = {
    validateSquareSide(sideLengthInPixels)

    apply(math.pow(sideLengthInPixels, 2))
  }

  /**
   * Creates a new [[Area]] instance on the basis of square's side length.
   *
   * @param sideLengthInPixels
   */
  def forSquare(sideLengthInPixels: Int): Area[Int] = {
    validateSquareSide(sideLengthInPixels)

    apply(sideLengthInPixels * sideLengthInPixels)
  }

  private
  def validateSquareSide(sideLengthInPixels: Double): Unit = {
    require(
      sideLengthInPixels >= 0,
      s"Square's side length cannot be negative (was $sideLengthInPixels)")
  }

  /**
   * Creates a new [[Area]] instance on the basis of rectangle's width and height.
   *
   * @param widthInPixels
   * @param heightInPixels
   */
  def forRectangle(widthInPixels: Double, heightInPixels: Double): Area[Double] = {
    validateRectangleDimensions(widthInPixels, heightInPixels)

    apply(widthInPixels * heightInPixels)
  }

  /**
   * Creates a new [[Area]] instance on the basis of rectangle's width and height.
   *
   * @param widthInPixels
   * @param heightInPixels
   */
  def forRectangle(widthInPixels: Int, heightInPixels: Int): Area[Int] = {
    validateRectangleDimensions(widthInPixels, heightInPixels)

    apply(widthInPixels * heightInPixels)
  }

  private
  def validateRectangleDimensions(
      widthInPixels: Double,
      heightInPixels: Double): Unit = {

    require(
      widthInPixels >= 0,
      s"Rectangle's width cannot be negative (was $widthInPixels)")

    require(
      heightInPixels >= 0,
      s"Rectangle's height cannot be negative (was $heightInPixels)")
  }

}




/**
 * Area of an object.
 *
 * @param valueInPixels
 * @tparam ValueType
 *
 * @author Aleksi Lukkarinen
 */
case class Area[ValueType] private(
    override val valueInPixels: ValueType)
    extends Magnitude[ValueType](valueInPixels) {

}
