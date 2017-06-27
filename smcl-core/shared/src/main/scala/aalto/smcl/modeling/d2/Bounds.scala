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

package aalto.smcl.modeling.d2


import aalto.smcl.modeling._




/**
 * Companion object for the [[Bounds]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Bounds {

  /** */
  val NumberOfCorners: Int = 4


  /**
   * Creates a new [[Bounds]] instance.
   *
   * @param upperLeftXInPixels
   * @param upperLeftYInPixels
   * @param lowerRightXInPixels
   * @param lowerRightYInPixels
   *
   * @return
   */
  def apply(
      upperLeftXInPixels: Double,
      upperLeftYInPixels: Double,
      lowerRightXInPixels: Double,
      lowerRightYInPixels: Double): Bounds = {

    val x0 :: x1 :: _ = Seq(upperLeftXInPixels, lowerRightXInPixels).sorted
    val y0 :: y1 :: _ = Seq(upperLeftYInPixels, lowerRightYInPixels).sorted

    val widthInPixels: Double = x1 - x0 + 1
    val heightInPixels: Double = y1 - y0 + 1

    val lengthInPixels: Double =
      2 * widthInPixels +
          2 * heightInPixels -
          NumberOfCorners

    new Bounds(
      Pos(x0, y0),
      Pos(x1, y1),
      widthInPixels,
      heightInPixels,
      Len(lengthInPixels),
      Area(widthInPixels * heightInPixels))
  }

  /**
   * Creates a new [[Bounds]] instance.
   *
   * @param upperLeft
   * @param lowerRight
   *
   * @return
   */
  def apply(
      upperLeft: Pos,
      lowerRight: Pos): Bounds = {

    apply(
      upperLeft.xInPixels, upperLeft.yInPixels,
      lowerRight.xInPixels, lowerRight.yInPixels)
  }

}




/**
 * Rectangular boundary in two-dimensional Cartesian coordinate system.
 *
 * @param upperLeft
 * @param lowerRight
 * @param widthInPixels
 * @param heightInPixels
 * @param length
 * @param area
 *
 * @author Aleksi Lukkarinen
 */
case class Bounds private(
    upperLeft: Pos,
    lowerRight: Pos,
    widthInPixels: Double,
    heightInPixels: Double,
    length: Len,
    area: Area)
    extends AbstractBoundary[Pos](Seq(upperLeft, lowerRight))
            with HasArea {

  val position: Pos = upperLeft

}
