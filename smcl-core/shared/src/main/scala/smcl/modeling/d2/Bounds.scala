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
import smcl.settings._




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
   * @param widthInPixels
   * @param heightInPixels
   *
   * @return
   */
  @inline
  def apply(
      center: Pos,
      widthInPixels: Double,
      heightInPixels: Double): Bounds = {

    val halfWidth = widthInPixels / 2.0
    val halfHeight = heightInPixels / 2.0
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
   * @param upperLeftCorner
   * @param lowerRightCorner
   *
   * @return
   */
  @inline
  def apply(
      upperLeftCorner: Pos,
      lowerRightCorner: Pos): Bounds = {

    apply(
      upperLeftCorner.xInPixels, upperLeftCorner.yInPixels,
      lowerRightCorner.xInPixels, lowerRightCorner.yInPixels)
  }

  /**
   * Creates a new [[Bounds]] instance.
   *
   * @param corners
   *
   * @return
   */
  @inline
  def apply(corners: Pos*): Bounds = {
    require(
      corners.length == 2,
      s"Exactly two corner positions must be given (currently: ${corners.length})")

    apply(corners: _*)
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
   * @param upperLeftCorner
   * @param lowerRightCorner
   * @param isDefined
   *
   * @return
   */
  @inline
  private
  def createInstance(
      upperLeftCorner: Pos,
      lowerRightCorner: Pos,
      isDefined: Boolean): Bounds = {

    new Bounds(upperLeftCorner, lowerRightCorner, isDefined)
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
 * @param upperLeftCorner
 * @param lowerRightCorner
 * @param isDefined
 *
 * @author Aleksi Lukkarinen
 */
case class Bounds private(
    upperLeftCorner: Pos,
    lowerRightCorner: Pos,
    isDefined: Boolean)
    extends CoordSysIndepBoundary[Pos, Dims]
        with HasArea
        with Movable[Bounds] {

  /** */
  lazy val corners: Seq[Pos] =
    Seq(upperLeftCorner, lowerRightCorner)

  /** Position of this boundary. */
  @inline
  final
  def position: Pos = upperLeftCorner

  /** */
  lazy val center: Pos = upperLeftCorner.centerBetween(lowerRightCorner)

  /** */
  def upperRightCorner: Pos = Pos(lowerRightCorner.xInPixels, upperLeftCorner.yInPixels)

  /** */
  def lowerLeftCorner: Pos = Pos(upperLeftCorner.xInPixels, lowerRightCorner.yInPixels)

  /** */
  lazy val width: Len =
    Len(lowerRightCorner.xInPixels - upperLeftCorner.xInPixels + 1)

  /** */
  lazy val height: Len =
    Len(lowerRightCorner.yInPixels - upperLeftCorner.yInPixels + 1)

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

  /** */
  lazy val isUndefined: Boolean = !isDefined

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  override final
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
  final
  def horizontalPositionFor(
      alignment: HorizontalAlignment,
      boundaryToBeAligned: Bounds): Double = {

    val offset =
      alignment match {
        case HALeft   => Len.Zero
        case HACenter => width.half - boundaryToBeAligned.width.half
        case HARight  => width - boundaryToBeAligned.width
      }

    upperLeftCorner.xInPixels + offset.inPixels
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
  final
  def verticalPositionFor(
      alignment: VerticalAlignment,
      boundaryToBeAligned: Bounds): Double = {

    val offset =
      alignment match {
        case VATop    => Len.Zero
        case VAMiddle => height.half - boundaryToBeAligned.height.half
        case VABottom => height - boundaryToBeAligned.height
      }

    upperLeftCorner.yInPixels + offset.inPixels
  }

  /**
   *
   *
   * @return
   */
  @inline
  final
  def randomPosInside: Pos = {
    if (isUndefined)
      return Pos.Origo

    val offsetX = Random.nextDouble() * width.inPixels
    val offsetY = Random.nextDouble() * height.inPixels

    upperLeftCorner + (offsetX, offsetY)
  }

  /**
   * If this boundary is defined, returns a new [[Bounds]] instance
   * with the given corners. Otherwise, returns this instance, as
   * an undefined boundary cannot be used.
   *
   * @param newUpperLeftCorner
   * @param newLowerRightCorner
   *
   * @return
   */
  @inline
  final
  def copy(
      newUpperLeftCorner: Pos = upperLeftCorner,
      newLowerRightCorner: Pos = lowerRightCorner): Bounds = {

    if (isUndefined)
      return this

    Bounds(newUpperLeftCorner, newLowerRightCorner)
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  final
  def grow(offset: Double): Bounds =
    copy(
      newUpperLeftCorner = upperLeftCorner.subtract(offset),
      newLowerRightCorner = lowerRightCorner.add(offset))

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  final
  def grow(offset: Len): Bounds =
    copy(
      newUpperLeftCorner = upperLeftCorner.subtract(offset),
      newLowerRightCorner = lowerRightCorner.add(offset))

  /**
   *
   *
   * @param horizontalOffset
   * @param verticalOffset
   *
   * @return
   */
  @inline
  final
  def grow(
      horizontalOffset: Double,
      verticalOffset: Double): Bounds = {

    copy(
      newUpperLeftCorner = upperLeftCorner.subtract(horizontalOffset, verticalOffset),
      newLowerRightCorner = lowerRightCorner.add(horizontalOffset, verticalOffset))
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
  final
  def grow(
      horizontalOffset: Len,
      verticalOffset: Len): Bounds = {

    copy(
      newUpperLeftCorner = upperLeftCorner.subtract(horizontalOffset, verticalOffset),
      newLowerRightCorner = lowerRightCorner.add(horizontalOffset, verticalOffset))
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
  final
  def grow(
      topOffset: Double,
      leftOffset: Double,
      bottomOffset: Double,
      rightOffset: Double): Bounds = {

    copy(
      newUpperLeftCorner = upperLeftCorner.subtract(leftOffset, topOffset),
      newLowerRightCorner = lowerRightCorner.add(rightOffset, bottomOffset))
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
  final
  def grow(
      topOffset: Len,
      leftOffset: Len,
      bottomOffset: Len,
      rightOffset: Len): Bounds = {

    copy(
      newUpperLeftCorner = upperLeftCorner.subtract(leftOffset, topOffset),
      newLowerRightCorner = lowerRightCorner.add(rightOffset, bottomOffset))
  }

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  final
  def shrink(offset: Double): Bounds =
    copy(
      newUpperLeftCorner = upperLeftCorner.add(offset),
      newLowerRightCorner = lowerRightCorner.subtract(offset))

  /**
   *
   *
   * @param offset
   *
   * @return
   */
  @inline
  final
  def shrink(offset: Len): Bounds =
    copy(
      newUpperLeftCorner = upperLeftCorner.add(offset),
      newLowerRightCorner = lowerRightCorner.subtract(offset))

  /**
   *
   *
   * @param horizontalOffset
   * @param verticalOffset
   *
   * @return
   */
  @inline
  final
  def shrink(
      horizontalOffset: Double,
      verticalOffset: Double): Bounds = {

    copy(
      newUpperLeftCorner = upperLeftCorner.add(horizontalOffset, verticalOffset),
      newLowerRightCorner = lowerRightCorner.subtract(horizontalOffset, verticalOffset))
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
  final
  def shrink(
      horizontalOffset: Len,
      verticalOffset: Len): Bounds = {

    copy(
      newUpperLeftCorner = upperLeftCorner.add(horizontalOffset, verticalOffset),
      newLowerRightCorner = lowerRightCorner.subtract(horizontalOffset, verticalOffset))
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
  final
  def shrink(
      topOffset: Double,
      leftOffset: Double,
      bottomOffset: Double,
      rightOffset: Double): Bounds = {

    copy(
      newUpperLeftCorner = upperLeftCorner.add(leftOffset, topOffset),
      newLowerRightCorner = lowerRightCorner.subtract(rightOffset, bottomOffset))
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
  final
  def shrink(
      topOffset: Len,
      leftOffset: Len,
      bottomOffset: Len,
      rightOffset: Len): Bounds = {

    copy(
      newUpperLeftCorner = upperLeftCorner.add(leftOffset, topOffset),
      newLowerRightCorner = lowerRightCorner.subtract(rightOffset, bottomOffset))
  }

  /**
   *
   *
   * @param offsetsInPixels
   *
   * @return
   */
  @inline
  override final
  def moveBy(offsetsInPixels: Seq[Double]): Bounds = {
    copy(
      newUpperLeftCorner = upperLeftCorner.moveBy(offsetsInPixels),
      newLowerRightCorner = lowerRightCorner.moveBy(offsetsInPixels))
  }

  /**
   *
   *
   * @param xOffsetInPixels
   * @param yOffsetInPixels
   *
   * @return
   */
  @inline
  override final
  def moveBy(
      xOffsetInPixels: Double,
      yOffsetInPixels: Double): Bounds = {

    copy(
      newUpperLeftCorner = upperLeftCorner.moveBy(xOffsetInPixels, yOffsetInPixels),
      newLowerRightCorner = lowerRightCorner.moveBy(xOffsetInPixels, yOffsetInPixels))
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  @inline
  override final
  def moveUpperLeftCornerTo(coordinatesInPixels: Seq[Double]): Bounds = {
    if (isUndefined)
      return this

    require(
      coordinatesInPixels.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions coordinates must be given (found: ${coordinatesInPixels.length})")

    moveBy(
      coordinatesInPixels.head - upperLeftCorner.xInPixels,
      coordinatesInPixels.tail.head - upperLeftCorner.yInPixels)
  }

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   *
   * @return
   */
  @inline
  override final
  def moveUpperLeftCornerTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): Bounds = {

    if (isUndefined)
      return this

    moveBy(
      xCoordinateInPixels - upperLeftCorner.xInPixels,
      yCoordinateInPixels - upperLeftCorner.yInPixels)
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  @inline
  override final
  def moveCenterTo(coordinatesInPixels: Seq[Double]): Bounds = {
    if (isUndefined)
      return this

    require(
      coordinatesInPixels.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions coordinates must be given (found: ${coordinatesInPixels.length})")

    moveBy(
      coordinatesInPixels.head - center.xInPixels,
      coordinatesInPixels.tail.head - center.yInPixels)
  }

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   *
   * @return
   */
  @inline
  override final
  def moveCenterTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): Bounds = {

    if (isUndefined)
      return this

    moveBy(
      xCoordinateInPixels - center.xInPixels,
      yCoordinateInPixels - center.yInPixels)
  }

}
