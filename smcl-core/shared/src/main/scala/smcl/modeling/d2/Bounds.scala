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


import scala.util.Random

import smcl.infrastructure.MathUtils
import smcl.modeling._
import smcl.modeling.misc.CoordSysIndepBoundary
import smcl.settings.{HACenter, HALeft, HARight, HorizontalAlignment, VABottom, VAMiddle, VATop, VerticalAlignment}




/**
 * Companion object for the [[Bounds]] class.
 *
 * @author Aleksi Lukkarinen
 */
object Bounds {

  /** */
  val NumberOfCorners: Int = 4

  /** */
  val ZeroAreaAtOrigo: Bounds =
    createInstance(Pos.Origo, Pos.Origo, isDefined = false)

  /** */
  val NotDefined: Bounds =
    createInstance(Pos.Origo, Pos.Origo, isDefined = false)

  /**
   * Creates a new [[Bounds]] instance based on center point, width, and height.
   *
   * @param center
   * @param width
   * @param height
   *
   * @return
   */
  @inline
  def apply(
      center: Pos,
      width: Len,
      height: Len): Bounds = {

    apply(center, width.inPixels, height.inPixels)
  }

  /**
   * Creates a new [[Bounds]] instance based on center point, width, and height.
   *
   * @param center
   * @param width
   * @param height
   *
   * @return
   */
  @inline
  def apply(
      center: Pos,
      width: Double,
      height: Double): Bounds = {

    val halfWidth = width / 2.0
    val halfHeight = height / 2.0
    val upperLeftCorner = center - (halfWidth, halfHeight)
    val lowerRightCorner = center + (halfWidth, halfHeight)

    createInstance(upperLeftCorner, lowerRightCorner, isDefined = true)
  }

  /**
   * Creates a new [[Bounds]] instance.
   *
   * @param position
   *
   * @return
   */
  @inline
  def apply(position: Pos): Bounds =
    createInstance(position, position, isDefined = true)

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
  def apply(markers: Pos*): Bounds = {
    require(
      markers.length == 2,
      s"Exactly two marker positions must be given (currently: ${markers.length})")

    apply(markers: _*)
  }

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
  @inline
  def apply(
      upperLeftXInPixels: Double,
      upperLeftYInPixels: Double,
      lowerRightXInPixels: Double,
      lowerRightYInPixels: Double): Bounds = {

    val (x0, x1) = MathUtils.sort(upperLeftXInPixels, lowerRightXInPixels)
    val (y0, y1) = MathUtils.sort(upperLeftYInPixels, lowerRightYInPixels)

    createInstance(Pos(x0, y0), Pos(x1, y1), isDefined = true)
  }

  /**
   * Creates a new [[Bounds]] instance.
   *
   * @param upperLeftMarker
   * @param lowerRightMarker
   * @param isDefined
   *
   * @return
   */
  @inline
  private
  def createInstance(
      upperLeftMarker: Pos,
      lowerRightMarker: Pos,
      isDefined: Boolean): Bounds = {

    new Bounds(upperLeftMarker, lowerRightMarker, isDefined)
  }




  /**
   *
   */
  object getWidth {

    /**
     *
     *
     * @param bounds
     *
     * @return
     */
    @inline
    def unapply(bounds: Bounds): Option[Len] = {
      Some(bounds.width)
    }

  }




  /**
   *
   */
  object getHeight {

    /**
     *
     *
     * @param bounds
     *
     * @return
     */
    @inline
    def unapply(bounds: Bounds): Option[Len] = {
      Some(bounds.height)
    }

  }




  /**
   *
   */
  object getLength {

    /**
     *
     *
     * @param bounds
     *
     * @return
     */
    @inline
    def unapply(bounds: Bounds): Option[Len] = {
      Some(bounds.length)
    }

  }




  /**
   *
   */
  object extractArea {

    /**
     *
     *
     * @param bounds
     *
     * @return
     */
    @inline
    def unapply(bounds: Bounds): Option[Area] = {
      Some(bounds.area)
    }

  }




}




/**
 * Rectangular boundary in two-dimensional Cartesian coordinate system.
 *
 * @param upperLeftMarker
 * @param lowerRightMarker
 * @param isDefined
 *
 * @author Aleksi Lukkarinen
 */
case class Bounds private(
    upperLeftMarker: Pos,
    lowerRightMarker: Pos,
    isDefined: Boolean)
    extends CoordSysIndepBoundary[Pos, Dims]
        with HasArea {

  /** */
  lazy val markers: Seq[Pos] =
    Seq(upperLeftMarker, lowerRightMarker)

  /** Position of this boundary. */
  @inline
  def position: Pos = upperLeftMarker

  /** */
  lazy val center: Pos = upperLeftMarker.centerBetween(lowerRightMarker)

  /** */
  lazy val width: Len =
    Len(lowerRightMarker.xInPixels - upperLeftMarker.xInPixels + 1)

  /** */
  lazy val height: Len =
    Len(lowerRightMarker.yInPixels - upperLeftMarker.yInPixels + 1)

  /** */
  lazy val dimensions: Dims =
    Dims(width, height)

  /** */
  lazy val length: Len =
    Len(2 * width.inPixels +
        2 * height.inPixels -
        Bounds.NumberOfCorners)

  /** */
  lazy val area: Area =
    Area.forRectangle(width.inPixels, height.inPixels)

  /** */
  lazy val aspectRatio: AspectRatio =
    AspectRatio.forDimensions(width, height)

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

  /**
   *
   *
   * @param alignment
   * @param boundaryToBeAligned
   *
   * @return
   */
  @inline
  def horizontalPositionFor(
      alignment: HorizontalAlignment,
      boundaryToBeAligned: Bounds): Double = {

    val offset =
      alignment match {
        case HALeft   => Len.Zero
        case HACenter => width.half - boundaryToBeAligned.width.half
        case HARight  => width - boundaryToBeAligned.width
      }

    upperLeftMarker.xInPixels + offset.inPixels
  }

  /**
   *
   *
   * @param alignment
   * @param boundaryToBeAligned
   *
   * @return
   */
  @inline
  def verticalPositionFor(
      alignment: VerticalAlignment,
      boundaryToBeAligned: Bounds): Double = {

    val offset =
      alignment match {
        case VATop    => Len.Zero
        case VAMiddle => height.half - boundaryToBeAligned.height.half
        case VABottom => height - boundaryToBeAligned.height
      }

    upperLeftMarker.yInPixels + offset.inPixels
  }

  /**
   *
   *
   * @return
   */
  @inline
  def randomPosInside: Pos = {
    val offsetX = Random.nextDouble() * width.inPixels
    val offsetY = Random.nextDouble() * height.inPixels

    upperLeftMarker + (offsetX, offsetY)
  }

  /**
   *
   *
   * @param newUpperLeftMarker
   * @param newLowerRightMarker
   *
   * @return
   */
  @inline
  def copy(
      newUpperLeftMarker: Pos = upperLeftMarker,
      newLowerRightMarker: Pos = lowerRightMarker): Bounds = {

    Bounds(newUpperLeftMarker, newLowerRightMarker)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def grow(offset: Double): Bounds =
    copy(
      newUpperLeftMarker = upperLeftMarker.subtract(offset),
      newLowerRightMarker = lowerRightMarker.add(offset))

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def grow(offset: Len): Bounds =
    copy(
      newUpperLeftMarker = upperLeftMarker.subtract(offset),
      newLowerRightMarker = lowerRightMarker.add(offset))

  /**
   *
   *
   * @param horizontalOffset
   * @param verticalOffset
   *
   * @return
   */
  @inline
  def grow(
      horizontalOffset: Double,
      verticalOffset: Double): Bounds = {

    copy(
      newUpperLeftMarker = upperLeftMarker.subtract(horizontalOffset, verticalOffset),
      newLowerRightMarker = lowerRightMarker.add(horizontalOffset, verticalOffset))
  }

  /**
   *
   *
   * @param horizontalOffset
   * @param verticalOffset
   *
   * @return
   */
  @inline
  def grow(
      horizontalOffset: Len,
      verticalOffset: Len): Bounds = {

    copy(
      newUpperLeftMarker = upperLeftMarker.subtract(horizontalOffset, verticalOffset),
      newLowerRightMarker = lowerRightMarker.add(horizontalOffset, verticalOffset))
  }

  /**
   *
   *
   * @param topOffset
   * @param leftOffset
   * @param bottomOffset
   * @param rightOffset
   *
   * @return
   */
  @inline
  def grow(
      topOffset: Double,
      leftOffset: Double,
      bottomOffset: Double,
      rightOffset: Double): Bounds = {

    copy(
      newUpperLeftMarker = upperLeftMarker.subtract(leftOffset, topOffset),
      newLowerRightMarker = lowerRightMarker.add(rightOffset, bottomOffset))
  }

  /**
   *
   *
   * @param topOffset
   * @param leftOffset
   * @param bottomOffset
   * @param rightOffset
   *
   * @return
   */
  @inline
  def grow(
      topOffset: Len,
      leftOffset: Len,
      bottomOffset: Len,
      rightOffset: Len): Bounds = {

    copy(
      newUpperLeftMarker = upperLeftMarker.subtract(leftOffset, topOffset),
      newLowerRightMarker = lowerRightMarker.add(rightOffset, bottomOffset))
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def shrink(offset: Double): Bounds =
    copy(
      newUpperLeftMarker = upperLeftMarker.add(offset),
      newLowerRightMarker = lowerRightMarker.subtract(offset))

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  def shrink(offset: Len): Bounds =
    copy(
      newUpperLeftMarker = upperLeftMarker.add(offset),
      newLowerRightMarker = lowerRightMarker.subtract(offset))

  /**
   *
   *
   * @param horizontalOffset
   * @param verticalOffset
   *
   * @return
   */
  @inline
  def shrink(
      horizontalOffset: Double,
      verticalOffset: Double): Bounds = {

    copy(
      newUpperLeftMarker = upperLeftMarker.add(horizontalOffset, verticalOffset),
      newLowerRightMarker = lowerRightMarker.subtract(horizontalOffset, verticalOffset))
  }

  /**
   *
   *
   * @param horizontalOffset
   * @param verticalOffset
   *
   * @return
   */
  @inline
  def shrink(
      horizontalOffset: Len,
      verticalOffset: Len): Bounds = {

    copy(
      newUpperLeftMarker = upperLeftMarker.add(horizontalOffset, verticalOffset),
      newLowerRightMarker = lowerRightMarker.subtract(horizontalOffset, verticalOffset))
  }

  /**
   *
   *
   * @param topOffset
   * @param leftOffset
   * @param bottomOffset
   * @param rightOffset
   *
   * @return
   */
  @inline
  def shrink(
      topOffset: Double,
      leftOffset: Double,
      bottomOffset: Double,
      rightOffset: Double): Bounds = {

    copy(
      newUpperLeftMarker = upperLeftMarker.add(leftOffset, topOffset),
      newLowerRightMarker = lowerRightMarker.subtract(rightOffset, bottomOffset))
  }

  /**
   *
   *
   * @param topOffset
   * @param leftOffset
   * @param bottomOffset
   * @param rightOffset
   *
   * @return
   */
  @inline
  def shrink(
      topOffset: Len,
      leftOffset: Len,
      bottomOffset: Len,
      rightOffset: Len): Bounds = {

    copy(
      newUpperLeftMarker = upperLeftMarker.add(leftOffset, topOffset),
      newLowerRightMarker = lowerRightMarker.subtract(rightOffset, bottomOffset))
  }

}
