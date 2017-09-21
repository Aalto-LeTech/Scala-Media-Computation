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
import aalto.smcl.infrastructure.{DrawingSurfaceAdapter, Identity}
import aalto.smcl.modeling.d2.{Dims, Pos}
import aalto.smcl.modeling.{AffineTransformation, Bounds}




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
 * @author Aleksi Lukkarinen
 */
class Line private(
    override val identity: Identity,
    val start: Pos,
    val end: Pos,
    val color: rgb.Color)
    extends {

      /** */
      val boundary: Option[Bounds] = Some(Bounds(start, end))

    } with Polygon(
      identity,
      Seq(start, end),
      boundary.get.upperLeftMarker) {

  /** The upper-left corner of the bounding box of this line. */
  val position: Pos = boundary.get.upperLeftMarker

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

    println("Line:")
    println("offsets: " + offsetsToOrigo)
    println("x0, y0: " + (offsetsToOrigo.width.inPixels + start.xInPixels).toInt + " ," + (offsetsToOrigo.height.inPixels + start.yInPixels).toInt)
    println("x1, y1: " + (offsetsToOrigo.width.inPixels + end.xInPixels).toInt + " ," + (offsetsToOrigo.height.inPixels + end.yInPixels).toInt)

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

    copy(
      newStart = start.rotateBy(angleInDegrees, centerOfRotation),
      newEnd = end.rotateBy(angleInDegrees, centerOfRotation))
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

}
