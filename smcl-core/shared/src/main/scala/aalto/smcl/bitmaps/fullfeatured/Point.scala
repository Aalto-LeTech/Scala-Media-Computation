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

package aalto.smcl.bitmaps.fullfeatured


import aalto.smcl.colors.rgb
import aalto.smcl.infrastructure.{DrawingSurfaceAdapter, FlatMap, Identity}
import aalto.smcl.modeling.d2.{Bounds, CoordinateTuple, Dims, Pos}
import aalto.smcl.modeling.{AffineTransformation, Len}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Point {

  /**
   *
   *
   * @param position
   * @param color
   */
  def apply(
      position: Pos,
      color: rgb.Color): Point = {

    apply(position, color)
  }

  /**
   *
   *
   * @param xInPixels
   * @param yInPixels
   */
  def apply(
      xInPixels: Double,
      yInPixels: Double,
      color: rgb.Color): Point = {

    new Point(
      Identity(),
      Pos(xInPixels, yInPixels),
      color)
  }

}




/**
 *
 *
 * @param identity
 * @param position
 * @param color
 *
 * @author Aleksi Lukkarinen
 */
class Point private(
    override val identity: Identity,
    val position: Pos,
    val color: rgb.Color)
    extends VectorGraphic
        with FlatMap[Point, (Pos, rgb.Color)] {

  /**
   *
   *
   * @return
   */
  def boundary: Option[Bounds] = position.boundary

  /** Tells if this [[Point]] can be rendered on a bitmap. */
  def isRenderable: Boolean = true

  /**
   *
   *
   * @param drawingSurface
   * @param offsetsToOrigo
   */
  @inline
  def renderOn(
      drawingSurface: DrawingSurfaceAdapter,
      offsetsToOrigo: Dims): Unit = {

    drawingSurface.drawPoint(
      offsetsToOrigo.width.inPixels + position.xInPixels,
      offsetsToOrigo.height.inPixels + position.yInPixels,
      color)
  }

  /**
   *
   *
   * @param newPosition
   *
   * @return
   */
  @inline
  def copy(
      newPosition: Pos = position,
      newColor: rgb.Color = color): Point = {

    new Point(identity, newPosition, newColor)
  }

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toString: String = {
    s"Point(x: ${position.xInPixels} px, y: ${position.yInPixels} px)"
  }

  /**
   * Returns the coordinates of this point as a tuple.
   *
   * @return
   */
  @inline
  def toCoordinateTuple: CoordinateTuple = {
    (position.xInPixels, position.yInPixels)
  }

  /**
   *
   *
   * @param f
   *
   * @return
   */
  @inline
  def flatMap(f: ((Pos, rgb.Color)) => Point): Point = {
    f(position, color)
  }

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  @inline
  def moveBy(offsets: Double*): Point = {
    copy(newPosition =
        position.moveBy(offsets: _*))
  }

  /**
   *
   *
   * @return
   */
  @inline
  def isOnOrigo: Boolean = position.isOrigo

  /**
   *
   *
   * @return
   */
  @inline
  override
  lazy val hashCode: Int = {
    val prime = 31
    var sum = 0

    sum = prime * sum + position.xInPixels.##
    sum = prime * sum + position.yInPixels.##
    sum = prime * sum + color.##

    sum
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  def canEqual(other: Any): Boolean = {
    other.isInstanceOf[Point]
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  override
  def equals(other: Any): Boolean = {
    other match {
      case that: Point =>
        that.canEqual(this) &&
            that.position == this.position &&
            that.color == this.color

      case _ => false
    }
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  def distanceFrom(other: Pos): Len = {
    position.distanceFrom(other)
  }

  /**
   *
   *
   * @param other
   *
   * @return
   */
  @inline
  def distanceFrom(other: Point): Len = {
    position.distanceFrom(other.position)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def isOnHorizontalAxis: Boolean = {
    position.isOnHorizontalAxis
  }

  /**
   *
   *
   * @return
   */
  @inline
  def isOnVerticalAxis: Boolean = {
    position.isOnVerticalAxis
  }

  /**
   *
   *
   * @return
   */
  @inline
  def isOnFirstQuadrant: Boolean = {
    position.isOnFirstQuadrant
  }

  /**
   *
   *
   * @return
   */
  @inline
  def isOnSecondQuadrant: Boolean = {
    position.isOnSecondQuadrant
  }

  /**
   *
   *
   * @return
   */
  @inline
  def isOnThirdQuadrant: Boolean = {
    position.isOnThirdQuadrant
  }

  /**
   *
   *
   * @return
   */
  @inline
  def isOnFourthQuadrant: Boolean = {
    position.isOnFourthQuadrant
  }

  /**
   *
   */
  @inline
  override
  def display(): Point = {
    super.display()

    this
  }

  /**
   * Transforms this [[Point]] using the specified affine transformation.
   *
   * @param t
   *
   * @return
   */
  @inline
  override
  def transformBy(t: AffineTransformation): Point = {
    copy(newPosition = t.process(position))
  }

  /**
   * Rotates this object around the origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW: Point = {
    copy(newPosition = position.rotateBy90DegsCW)
  }

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW(centerOfRotation: Pos): Point = {
    copy(newPosition = position.rotateBy90DegsCW(centerOfRotation))
  }

  /**
   * Rotates this object around the origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW: Point = {
    copy(newPosition = position.rotateBy90DegsCCW)
  }

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW(centerOfRotation: Pos): Point = {
    copy(newPosition = position.rotateBy90DegsCCW(centerOfRotation))
  }

  /**
   * Rotates this object around the origo (0,0) by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs: Point = {
    copy(newPosition = position.rotateBy180Degs)
  }

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs(centerOfRotation: Pos): Point = {
    copy(newPosition = position.rotateBy180Degs(centerOfRotation))
  }

  /**
   * Rotates this object around the origo (0,0) by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  override
  def rotateBy(angleInDegrees: Double): Point = {
    copy(newPosition = position.rotateBy(angleInDegrees))
  }

  /**
   * Rotates this object around a given point of the specified number of degrees.
   *
   * @param angleInDegrees
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): Point = {

    copy(newPosition = position.rotateBy(angleInDegrees, centerOfRotation))
  }

}
