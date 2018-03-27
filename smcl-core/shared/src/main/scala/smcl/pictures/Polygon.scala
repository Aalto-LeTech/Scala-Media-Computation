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

package smcl.pictures


import smcl.colors.rgb
import smcl.infrastructure.Identity
import smcl.modeling.Angle
import smcl.modeling.d2.{BoundaryCalculator, Bounds, Dims, NumberOfDimensions, Pos}
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

  /** Tells if this [[Polygon]] can be rendered on a bitmap. */
  override
  val isRenderable: Boolean = true

  /**
   *
   *
   * @return
   */
  @inline
  override
  def isPolygon: Boolean = true

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
      heightFactor: Double): PictureElement = {

    copy(newPoints = points.map(_.scaleBy(widthFactor, heightFactor)))
  }

  /**
   *
   *
   * @param offsetsInPixels
   *
   * @return
   */
  @inline
  override
  def moveBy(offsetsInPixels: Seq[Double]): PictureElement =
    copy(newPoints = points.map(_.moveBy(offsetsInPixels)))

  /**
   *
   *
   * @param xOffsetInPixels
   * @param yOffsetInPixels
   *
   * @return
   */
  @inline
  override
  def moveBy(
      xOffsetInPixels: Double,
      yOffsetInPixels: Double): PictureElement = {

    copy(newPoints = points.map(_.moveBy(xOffsetInPixels, yOffsetInPixels)))
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  override
  def moveUpperLeftCornerTo(coordinatesInPixels: Seq[Double]): PictureElement = {
    require(
      coordinatesInPixels.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions coordinates must be given (found: ${coordinatesInPixels.length})")

    moveBy(
      coordinatesInPixels.head - boundary.upperLeftCorner.xInPixels,
      coordinatesInPixels.tail.head - boundary.upperLeftCorner.yInPixels)
  }

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   *
   * @return
   */
  override
  def moveUpperLeftCornerTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): PictureElement = {

    moveBy(
      xCoordinateInPixels - boundary.upperLeftCorner.xInPixels,
      yCoordinateInPixels - boundary.upperLeftCorner.yInPixels)
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  @inline
  override
  def moveCenterTo(coordinatesInPixels: Seq[Double]): PictureElement = {
    require(
      coordinatesInPixels.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions coordinates must be given (found: ${coordinatesInPixels.length})")

    moveBy(
      coordinatesInPixels.head - boundary.center.xInPixels,
      coordinatesInPixels.tail.head - boundary.center.yInPixels)
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
  override
  def moveCenterTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): PictureElement = {

    moveBy(
      xCoordinateInPixels - boundary.center.xInPixels,
      yCoordinateInPixels - boundary.center.yInPixels)
  }

  /**
   * Rotates this object around origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCWAroundOrigo: Polygon =
    copy(newPoints = points.map(_.rotateBy90DegsCWAroundOrigo))

  /**
   * Rotates this object around its center by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW: Polygon = rotateBy90DegsCW(position)

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW(centerOfRotation: Pos): Polygon =
    copy(newPoints = points.map(_.rotateBy90DegsCW(centerOfRotation)))

  /**
   * Rotates this object around origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCWAroundOrigo: Polygon =
    copy(newPoints = points.map(_.rotateBy90DegsCCWAroundOrigo))

  /**
   * Rotates this object around the its center by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW: Polygon = rotateBy90DegsCCW(position)

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW(centerOfRotation: Pos): Polygon =
    copy(newPoints = points.map(_.rotateBy90DegsCCW(centerOfRotation)))

  /**
   * Rotates this object around origo (0,0) by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180DegsAroundOrigo: Polygon =
    copy(newPoints = points.map(_.rotateBy180DegsAroundOrigo))

  /**
   * Rotates this object around its center by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs: Polygon = rotateBy180Degs(position)

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs(centerOfRotation: Pos): Polygon =
    copy(newPoints = points.map(_.rotateBy180Degs(centerOfRotation)))

  /**
   * Rotates this object around its center by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  @inline
  override
  def rotateByAroundOrigo(angle: Angle): Polygon = rotateByAroundOrigo(angle)

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  override
  def rotateByAroundOrigo(angleInDegrees: Double): Polygon =
    copy(newPoints = points.map(_.rotateByAroundOrigo(angleInDegrees)))

  /**
   * Rotates this object around its center by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  @inline
  override
  def rotateBy(angle: Angle): Polygon = rotateBy(angle)

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  override
  def rotateBy(angleInDegrees: Double): Polygon =
    rotateBy(angleInDegrees, position)

  /**
   * Rotates this object around a given point by the specified angle.
   *
   * @param angle
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy(
      angle: Angle,
      centerOfRotation: Pos): Polygon = {

    rotateBy(angle, centerOfRotation)
  }

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
      centerOfRotation: Pos): Polygon = {

    copy(newPoints = points.map(_.rotateBy(angleInDegrees, centerOfRotation)))
  }

}
