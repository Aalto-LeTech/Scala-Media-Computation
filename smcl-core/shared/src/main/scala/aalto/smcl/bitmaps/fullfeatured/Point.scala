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
import aalto.smcl.modeling.Len
import aalto.smcl.modeling.d2.{Bounds, CoordinateTuple, Movable, Pos}




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
 * @author Aleksi Lukkarinen
 */
case class Point private(
    override val identity: Identity,
    position: Pos,
    color: rgb.Color)
    extends VectorGraphic
            with FlatMap[Point, (Pos, rgb.Color)]
            with Movable[Point] {

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
   */
  def renderOn(
      drawingSurface: DrawingSurfaceAdapter,
      position: Pos): Unit = {

    drawingSurface.drawPoint(
      position.xInPixels, position.yInPixels, color)
  }

  /**
   *
   *
   * @param newPosition
   *
   * @return
   */
  def copy(
      newPosition: Pos = position,
      newColor: rgb.Color = color): Point = {

    Point(newPosition, newColor)
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
  override
  def moveBy(offsets: Double*): Point = {
    copy(newPosition = position.moveBy(offsets: _*))
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
  override
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
   * Provides an iterator for the coordinate values.
   *
   * @return
   */
  @inline
  def coordinateIterator: Iterator[Double] = {
    position.iterator
  }

  /**
   *
   */
  override
  def display(): Point = {
    super.display()

    this
  }

}
