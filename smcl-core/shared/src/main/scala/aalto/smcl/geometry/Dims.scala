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
 * Companion object for the [[Dims]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Dims {

  /**
   * Creates a new [[Dims]] instance.
   *
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  def apply(
      widthInPixels: Int,
      heightInPixels: Int): Dims[Int] = {

    validateDimensions(widthInPixels, heightInPixels)

    new Dims(widthInPixels, heightInPixels)
  }

  /**
   * Creates a new [[Dims]] instance.
   *
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  def apply(
      widthInPixels: Double,
      heightInPixels: Double): Dims[Double] = {

    validateDimensions(widthInPixels, heightInPixels)

    new Dims(widthInPixels, heightInPixels)
  }

  private
  def validateDimensions(
      widthInPixels: Double,
      heightInPixels: Double): Unit = {

    require(
      widthInPixels >= 0,
      s"Width cannot be negative (was $widthInPixels)")

    require(
      heightInPixels >= 0,
      s"Height cannot be negative (was $heightInPixels)")
  }

}




/**
 * Dimensions in two-dimensional Cartesian coordinate system.
 *
 * @param widthInPixels
 * @param heightInPixels
 * @tparam ValueType
 *
 * @author Aleksi Lukkarinen
 */
case class Dims[ValueType] private(
    widthInPixels: ValueType,
    heightInPixels: ValueType)
    extends CartesianDimensions(
      Seq(widthInPixels, heightInPixels)) {

}
