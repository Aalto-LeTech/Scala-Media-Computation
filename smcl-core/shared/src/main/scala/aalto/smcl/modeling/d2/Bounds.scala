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


import aalto.smcl.infrastructure.MathUtils
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

    val (x0, x1) = MathUtils.sort(upperLeftXInPixels, lowerRightXInPixels)
    val (y0, y1) = MathUtils.sort(upperLeftYInPixels, lowerRightYInPixels)

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
  @inline
  def apply(
      upperLeft: Pos,
      lowerRight: Pos): Bounds = {

    apply(
      upperLeft.xInPixels, upperLeft.yInPixels,
      lowerRight.xInPixels, lowerRight.yInPixels)
  }

  /**
   * Creates a new [[Bounds]] instance.
   *
   * @param markers
   *
   * @return
   */
  @inline
  def apply(markers: Pos*): Dims = {
    require(
      markers.length == 2,
      s"Exactly two marker positions must be given (currently: ${markers.length})")

    apply(markers: _*)
  }

}




/**
 * Rectangular boundary in two-dimensional Cartesian coordinate system.
 *
 * @param upperLeftMarker
 * @param lowerRightMarker
 * @param widthInPixels
 * @param heightInPixels
 * @param length
 * @param area
 *
 * @author Aleksi Lukkarinen
 */
case class Bounds private(
    upperLeftMarker: Pos,
    lowerRightMarker: Pos,
    widthInPixels: Double,
    heightInPixels: Double,
    length: Len,
    area: Area)
    extends AbstractBoundary[Pos](Seq(upperLeftMarker, lowerRightMarker))
            with HasArea {

  /** Position of this boundary. */
  val position: Pos = upperLeftMarker

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  override
  def canEqual(other: Any): Boolean = {
    other.isInstanceOf[Bounds]
  }

}
