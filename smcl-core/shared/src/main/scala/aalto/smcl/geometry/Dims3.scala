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
 * Companion object for the [[Dims3]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Dims3 {

  /**
   * Creates a new [[Dims3]] instance.
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param depthInPixels
   *
   * @return
   */
  def apply(
      widthInPixels: Int,
      heightInPixels: Int,
      depthInPixels: Int): Dims3[Int] = {

    validateDimensions(widthInPixels, heightInPixels, depthInPixels)

    new Dims3(widthInPixels, heightInPixels, depthInPixels)
  }

  /**
   * Creates a new [[Dims3]] instance.
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param depthInPixels
   *
   * @return
   */
  def apply(
      widthInPixels: Double,
      heightInPixels: Double,
      depthInPixels: Double): Dims3[Double] = {

    validateDimensions(widthInPixels, heightInPixels, depthInPixels)

    new Dims3(widthInPixels, heightInPixels, depthInPixels)
  }

  private
  def validateDimensions(
      widthInPixels: Double,
      heightInPixels: Double,
      depthInPixels: Double): Unit = {

    require(
      widthInPixels >= 0,
      s"Width cannot be negative (was $widthInPixels)")

    require(
      heightInPixels >= 0,
      s"Height cannot be negative (was $heightInPixels)")

    require(
      depthInPixels >= 0,
      s"Depth cannot be negative (was $depthInPixels)")
  }

}




/**
 * Dimensions in three-dimensional Cartesian coordinate system.
 *
 * @param widthInPixels
 * @param heightInPixels
 * @param depthInPixels
 * @tparam ValueType
 *
 * @author Aleksi Lukkarinen
 */
case class Dims3[ValueType] private(
    widthInPixels: ValueType,
    heightInPixels: ValueType,
    depthInPixels: ValueType)
    extends CartesianDimensions(
      Seq(widthInPixels, heightInPixels, depthInPixels)) {

}
