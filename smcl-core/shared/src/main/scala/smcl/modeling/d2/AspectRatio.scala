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

package smcl.modeling.d2


import smcl.modeling.Len




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object AspectRatio {

  /** */
  val ZeroFactor: Double = 0.0

  /** */
  val BaseLineFactor: Double = 1.0

  /** */
  val NotDefinedFactor: Double = Double.NaN

  /** */
  val NotDefinedDimensionValues: Seq[Double] =
    Seq(ZeroFactor, Double.PositiveInfinity, Double.NaN)

  /**
   *
   *
   * @param dimensions
   *
   * @return
   */
  def forDimensions(dimensions: Dims): AspectRatio =
    forDimensions(
      dimensions.width.inPixels,
      dimensions.height.inPixels)

  /**
   *
   *
   * @param width
   * @param height
   *
   * @return
   */
  def forDimensions(
      width: Len,
      height: Len): AspectRatio = {

    forDimensions(
      width.inPixels,
      height.inPixels)
  }

  /**
   *
   *
   * @param horizontalInPixels
   * @param verticalInPixels
   *
   * @return
   */
  def forDimensions(
      horizontalInPixels: Double,
      verticalInPixels: Double): AspectRatio = {

    require(horizontalInPixels >= ZeroFactor, "Horizontal dimension cannot be negative")
    require(verticalInPixels >= ZeroFactor, "Vertical dimension cannot be negative")

    if (NotDefinedDimensionValues.contains(horizontalInPixels)
        || NotDefinedDimensionValues.contains(verticalInPixels))
      return notDefined

    if (horizontalInPixels == verticalInPixels)
      return square

    if (horizontalInPixels > verticalInPixels)
      return landscape(verticalInPixels.toDouble / horizontalInPixels)

    portrait(horizontalInPixels.toDouble / verticalInPixels)
  }

  /**
   *
   *
   * @param horizontalFactor
   *
   * @return
   */
  def landscape(horizontalFactor: Double): AspectRatio = {
    require(horizontalFactor > BaseLineFactor,
      s"""For a landscape-type aspect ratio, the horizontal
         |factor must be larger than $BaseLineFactor"""
          .stripMargin.replace("\n", " "))

    new AspectRatio(
      isDefined = true,
      horizontal = horizontalFactor,
      vertical = BaseLineFactor)
  }

  /**
   *
   *
   * @param verticalFactor
   *
   * @return
   */
  def portrait(verticalFactor: Double): AspectRatio = {
    require(verticalFactor > BaseLineFactor,
      s"""For a portrait-type aspect ratio, the vertical
         |factor must be larger than $BaseLineFactor"""
          .stripMargin.replace("\n", " "))

    new AspectRatio(
      isDefined = true,
      horizontal = BaseLineFactor,
      vertical = verticalFactor)
  }

  /**
   *
   *
   * @return
   */
  def square: AspectRatio =
    new AspectRatio(
      isDefined = true,
      horizontal = BaseLineFactor,
      vertical = BaseLineFactor)

  /**
   *
   *
   * @return
   */
  def notDefined: AspectRatio =
    new AspectRatio(
      isDefined = false,
      horizontal = NotDefinedFactor,
      vertical = NotDefinedFactor)

}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class AspectRatio private(
    isDefined: Boolean,
    horizontal: Double,
    vertical: Double)