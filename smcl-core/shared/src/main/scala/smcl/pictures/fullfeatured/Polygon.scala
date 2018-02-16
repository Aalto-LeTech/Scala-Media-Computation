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

package smcl.pictures.fullfeatured


import smcl.colors.rgb
import smcl.infrastructure.{DrawingSurfaceAdapter, Identity}
import smcl.modeling.AffineTransformation
import smcl.modeling.d2.{BoundaryCalculator, Bounds, Dims, Pos}
import smcl.settings._




/**
 * An object-based API for creating general polygons.
 *
 * @author Aleksi Lukkarinen
 */
object Polygon {

  /**
   *
   *
   * @param points
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def apply(
      points: Seq[Pos],
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: rgb.Color = DefaultPrimaryColor,
      fillColor: rgb.Color = DefaultSecondaryColor): Polygon = {

    val identity = Identity()
    new Polygon(identity, points, hasBorder, hasFilling, color, fillColor)
  }

}




/**
 *
 *
 * @param identity
 * @param points
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
class Polygon private(
    val identity: Identity,
    override val points: Seq[Pos],
    val hasBorder: Boolean,
    val hasFilling: Boolean,
    val color: rgb.Color,
    val fillColor: rgb.Color)
    extends VectorGraphic {

  /** Bounding box of this [[Polygon]]. */
  override
  val boundary: Bounds =
    BoundaryCalculator.fromPositions(points)

  /** Dimensions of this [[Polygon]]. */
  override
  val dimensions: Dims =
    Dims(boundary.width, boundary.height)

  /** Position of this [[Polygon]]. */
  override
  val position: Pos = Pos(
    internalCenter.width.inPixels,
    internalCenter.height.inPixels)

  /** Tells if this [[Polygon]] can be rendered on a bitmap. */
  override
  val isRenderable: Boolean = true

  /**
   * Renders this [[Polygon]] on a drawing surface.
   *
   * @param drawingSurface
   * @param offsetsToOrigo
   */
  override
  def renderOn(
      drawingSurface: DrawingSurfaceAdapter,
      offsetsToOrigo: Dims): Unit = {

    if (points.isEmpty)
      return

    val xOffset = offsetsToOrigo.width.inPixels
    val yOffset = offsetsToOrigo.height.inPixels

    if (points.lengthCompare(1) == 0) {
      val p = points.head

      drawingSurface.drawPoint(
        xOffset + p.xInPixels,
        yOffset + p.yInPixels,
        color)
    }
    else if (points.lengthCompare(2) == 0) {
      val start = points.head
      val end = points.tail.head

      drawingSurface.drawLine(
        fromXInPixels = xOffset + start.xInPixels,
        fromYInPixels = yOffset + start.yInPixels,
        toXInPixels = xOffset + end.xInPixels,
        toYInPixels = yOffset + end.yInPixels,
        color = color)
    }
    else {
      val (rawXs, rawYs) = points.unzip[Double, Double]

      drawingSurface.drawPolygon(
        rawXs.map(xOffset + _),
        rawYs.map(yOffset + _),
        points.length,
        hasBorder,
        hasFilling,
        color,
        fillColor)
    }
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

    points.foreach{p =>
      sum = prime * sum + p.xInPixels.##
      sum = prime * sum + p.yInPixels.##
    }

    sum = prime * sum + hasBorder.##
    sum = prime * sum + hasFilling.##
    sum = prime * sum + color.##
    sum = prime * sum + fillColor.##

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
    other.isInstanceOf[Polygon]
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
      case that: Polygon =>
        that.canEqual(this) &&
            that.points == this.points &&
            that.hasBorder == this.hasBorder &&
            that.hasFilling == this.hasFilling &&
            that.color == this.color &&
            that.fillColor == this.fillColor

      case _ => false
    }
  }

  /**
   *
   */
  @inline
  override
  def display(): Polygon = {
    super.display()

    this
  }

  /**
   *
   *
   * @return
   */
  @inline
  def copy(
      newPoints: Seq[Pos] = points,
      newHasBorder: Boolean = hasBorder,
      newHasFilling: Boolean = hasFilling,
      newColor: rgb.Color = color,
      newFillColor: rgb.Color = fillColor): Polygon = {

    new Polygon(
      identity,
      newPoints,
      newHasBorder, newHasFilling,
      newColor, newFillColor)
  }

  /**
   *
   *
   * @param widthFactor
   * @param heightFactor
   *
   * @return
   */
  @inline
  override
  def scaleBy(
      widthFactor: Double,
      heightFactor: Double): ImageElement = {

    copy(newPoints = points.map(_.scaleBy(widthFactor, heightFactor)))
  }

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  @inline
  override
  def moveBy(offsets: Double*): ImageElement =
    copy(newPoints = points.map(_.moveBy(offsets: _*)))

  /**
   * Rotates this object around the origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW: ImageElement =
    copy(newPoints = points.map(_.rotateBy90DegsCW))

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW(centerOfRotation: Pos): ImageElement =
    copy(newPoints = points.map(_.rotateBy90DegsCW(centerOfRotation)))

  /**
   * Rotates this object around the origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW: ImageElement =
    copy(newPoints = points.map(_.rotateBy90DegsCCW))

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW(centerOfRotation: Pos): ImageElement =
    copy(newPoints = points.map(_.rotateBy90DegsCCW(centerOfRotation)))

  /**
   * Rotates this object around the origo (0,0) by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs: ImageElement =
    copy(newPoints = points.map(_.rotateBy180Degs))

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs(centerOfRotation: Pos): ImageElement =
    copy(newPoints = points.map(_.rotateBy180Degs(centerOfRotation)))

  /**
   * Rotates this object around the origo (0,0) by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  override
  def rotateBy(angleInDegrees: Double): ImageElement =
    copy(newPoints = points.map(_.rotateBy(angleInDegrees)))

  /**
   * Rotates this object around a given point by the specified number of degrees.
   *
   * @param angleInDegrees
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): ImageElement = {

    copy(newPoints = points.map(_.rotateBy(angleInDegrees, centerOfRotation)))
  }

  /**
   * Transforms this object using the specified affine transformation.
   *
   * @param t
   *
   * @return
   */
  @inline
  override
  def transformBy(t: AffineTransformation): ImageElement =
    copy(newPoints = points.map(t.process))

}
