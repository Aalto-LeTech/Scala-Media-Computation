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

package smcl.bitmaps.fullfeatured


import smcl.colors.rgb
import smcl.infrastructure.{DrawingSurfaceAdapter, Identity}
import smcl.modeling.d2.{Dims, Pos}
import smcl.modeling.{AffineTransformation, Bounds, Transformer}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Line {

  /**
   *
   *
   * @param start
   * @param end
   * @param color
   *
   * @return
   */
  def apply(
      start: Pos,
      end: Pos,
      color: rgb.Color): Line = {

    new Line(Identity(), start, end, color)
  }

}



/**
 *
 *
 * {{{
 * def moire(w: Double) = {
 *   def lines(x: Double, c: Double) = Seq(
 *           Line(Pos.Origo, Pos(x, -c), Red),
 *           Line(Pos.Origo, Pos(x, c), Blue),
 *           Line(Pos.Origo, Pos(-c, x), Green),
 *           Line(Pos.Origo, Pos(c, x), Brown))
 *
 *   val wPerTwo = (w/2).toInt
 *   val r = Range.inclusive(-wPerTwo, wPerTwo, 2)
 *
 *   Image((for{x <- r} yield lines(x, wPerTwo)).flatten: _*)
 * }
 * }}}
 *
 * @param identity
 * @param start
 * @param end
 * @param color
 *
 * @author Aleksi Lukkarinen
 */
class Line private(
    override val identity: Identity,
    val start: Pos,
    val end: Pos,
    val color: rgb.Color)
    extends {

      /** */
      val boundary: Bounds = Bounds(start, end)

    } with Polygon(
      identity,
      Seq(start, end),
      boundary.upperLeftMarker) {

  /** Tells if this [[Line]] can be rendered on a bitmap. */
  val isRenderable: Boolean = true


  /**
   * Renders this [[Line]] on a drawing surface.
   *
   * @param drawingSurface
   * @param offsetsToOrigo
   */
  override
  def renderOn(
      drawingSurface: DrawingSurfaceAdapter,
      offsetsToOrigo: Dims): Unit = {

    drawingSurface.drawLine(
      fromXInPixels = (offsetsToOrigo.width.inPixels + start.xInPixels).toInt,
      fromYInPixels = (offsetsToOrigo.height.inPixels + start.yInPixels).toInt,
      toXInPixels = (offsetsToOrigo.width.inPixels + end.xInPixels).toInt,
      toYInPixels = (offsetsToOrigo.height.inPixels + end.yInPixels).toInt,
      color = color)
  }

  /**
   *
   *
   * @return
   */
  @inline
  def copy(
      newStart: Pos = start,
      newEnd: Pos = end,
      newColor: rgb.Color = color): Line = {

    new Line(identity, newStart, newEnd, newColor)
  }

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  @inline
  def moveBy(offsets: Double*): Line = {
    copy(
      newStart = start.moveBy(offsets: _*),
      newEnd = end.moveBy(offsets: _*))
  }

  /**
   * Transforms this object using the specified affine transformation.
   *
   * @param t
   *
   * @return
   */
  @inline
  def transformBy(t: AffineTransformation): Line = {
    copy(
      newStart = t.process(start),
      newEnd = t.process(end))
  }

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

    sum = prime * sum + start.xInPixels.##
    sum = prime * sum + start.yInPixels.##
    sum = prime * sum + end.xInPixels.##
    sum = prime * sum + end.yInPixels.##
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
    other.isInstanceOf[Line]
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
      case that: Line =>
        that.canEqual(this) &&
            that.start == this.start &&
            that.end == this.end &&
            that.color == this.color

      case _ => false
    }
  }

  /**
   *
   */
  @inline
  override
  def display(): Line = {
    super.display()

    this
  }

  /**
   * Rotates this object around the origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW: Line = {
    copy(
      newStart = start.rotateBy90DegsCW,
      newEnd = end.rotateBy90DegsCW)
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
  def rotateBy90DegsCW(centerOfRotation: Pos): Line = {
    copy(
      newStart = start.rotateBy90DegsCW(centerOfRotation),
      newEnd = end.rotateBy90DegsCW(centerOfRotation))
  }

  /**
   * Rotates this object around the origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW: Line = {
    copy(
      newStart = start.rotateBy90DegsCCW,
      newEnd = end.rotateBy90DegsCCW)
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
  def rotateBy90DegsCCW(centerOfRotation: Pos): Line = {
    copy(
      newStart = start.rotateBy90DegsCCW(centerOfRotation),
      newEnd = end.rotateBy90DegsCCW(centerOfRotation))
  }

  /**
   * Rotates this object around the origo (0,0) by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs: Line = {
    copy(
      newStart = start.rotateBy180Degs,
      newEnd = end.rotateBy180Degs)
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
  def rotateBy180Degs(centerOfRotation: Pos): Line = {
    copy(
      newStart = start.rotateBy180Degs(centerOfRotation),
      newEnd = end.rotateBy180Degs(centerOfRotation))
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
  def rotateBy(angleInDegrees: Double): Line = {
    val newPoints = Transformer.rotate(points, angleInDegrees)

    copy(
      newStart = newPoints.head,
      newEnd = newPoints(1))
  }

  /**
   * Rotates this object around a given point of the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): Line = {

    val newPoints = Transformer.rotate(points, angleInDegrees, centerOfRotation)

    copy(
      newStart = newPoints.head,
      newEnd = newPoints(1))
  }

}
