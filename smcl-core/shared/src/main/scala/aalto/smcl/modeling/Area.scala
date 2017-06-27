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

}




/**
 * Area of an object.
 *
 * @param inPixels
 *
 * @author Aleksi Lukkarinen
 */
case class Area private(
    override val inPixels: Double)
    extends Magnitude[Area](inPixels)
            with FlatMap[Area] {

  /**
   *
   * @param f
   *
   * @return
   */
  override def map(f: (Double) => Double): Area = {
    Area(f(inPixels))
  }

}
