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
import smcl.modeling.d2.{BoundaryCalculator, Bounds, Dims, NumberOfDimensions, Pos}
import smcl.modeling.{Angle, Transformer}
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
   * @param position
   * @param pointsRelativeToCenterAtOrigo
   * @param referencePointRelativeToCenterAtOrigo
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def apply(
      position: Pos = DefaultPosition,
      pointsRelativeToCenterAtOrigo: Seq[Pos],
      referencePointRelativeToCenterAtOrigo: Pos,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: rgb.Color = DefaultPrimaryColor,
      fillColor: rgb.Color = DefaultSecondaryColor): Polygon = {

    val identity = Identity()

    new Polygon(
      identity,
      position,
      pointsRelativeToCenterAtOrigo,
      referencePointRelativeToCenterAtOrigo,
      hasBorder, hasFilling,
      color, fillColor)
  }

}




/**
 *
 *
 * @param identity
 * @param position
 * @param pointsRelativeToCenterAtOrigo
 * @param referencePointRelativeToCenterAtOrigo
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
class Polygon private(
    val identity: Identity,
    override val position: Pos,
    override val pointsRelativeToCenterAtOrigo: Seq[Pos],
    override val referencePointRelativeToCenterAtOrigo: Pos,
    val hasBorder: Boolean,
    val hasFilling: Boolean,
    val color: rgb.Color,
    val fillColor: rgb.Color)
    extends VectorGraphic {

  //private[smcl]
  val contentBoundary: Bounds =
    BoundaryCalculator.fromPositions(pointsRelativeToCenterAtOrigo)

  /** Tells if this [[Polygon]] can be rendered on a bitmap. */
  override
  lazy val isRenderable: Boolean =
    (contentBoundary.isDefined
        && contentBoundary.width.inPixels >= 0.5
        && contentBoundary.height.inPixels >= 0.5
        && pointsRelativeToCenterAtOrigo.nonEmpty
        && (hasBorder || hasFilling))

  private[this]
  lazy val corners: Seq[Pos] = {
    if (isNotRenderable) {
      Seq.fill(4)(Pos.NotDefined)
    }
    else {
      val ulX = contentBoundary.upperLeftCorner.xInPixels
      val ulY = contentBoundary.upperLeftCorner.yInPixels
      val lrX = contentBoundary.lowerRightCorner.xInPixels
      val lrY = contentBoundary.lowerRightCorner.yInPixels

      val refX = referencePointRelativeToCenterAtOrigo.xInPixels
      val refY = referencePointRelativeToCenterAtOrigo.yInPixels

      Seq(
        position + contentBoundary.upperLeftCorner,
        position + Pos(lrX, ulY),
        position + contentBoundary.lowerRightCorner,
        position + Pos(ulX, lrY)
      )
    }
  }

  /** Upper left corner of this [[Polygon]]. */
  lazy val upperLeftCorner: Pos = corners.head

  /** Upper right corner of this [[Polygon]]. */
  lazy val upperRightCorner: Pos = corners.tail.head

  /** Lower right corner of this [[Polygon]]. */
  lazy val lowerRightCorner: Pos = corners.tail.tail.head

  /** Lower left corner of this [[Polygon]]. */
  lazy val lowerLeftCorner: Pos = corners.tail.tail.tail.head

  /** Boundary of this [[Polygon]]. */
  override
  lazy val boundary: Bounds =
    if (isNotRenderable)
      Bounds.NotDefined
    else
      Bounds(upperLeftCorner, lowerRightCorner)

  /** Dimensions of this [[Polygon]]. */
  override
  val dimensions: Dims =
    Dims(boundary.width, boundary.height)

  /**
   *
   *
   * @return
   */
  @inline
  override final
  def isPolygon: Boolean = true

  /**
   *
   *
   * @return
   */
  override
  lazy val hashCode: Int = {
    val prime = 31
    var sum = 0

    sum = prime * sum + position.##

    pointsRelativeToCenterAtOrigo.foreach{p =>
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
  override
  def equals(other: Any): Boolean = {
    other match {
      case that: Polygon =>
        that.canEqual(this) &&
            that.position == this.position &&
            that.pointsRelativeToCenterAtOrigo == this.pointsRelativeToCenterAtOrigo &&
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
  def copy(
      newPosition: Pos = position,
      newPointsRelativeToCenterAtOrigo: Seq[Pos] = pointsRelativeToCenterAtOrigo,
      newReferencePointRelativeToCenterAtOrigo: Pos = referencePointRelativeToCenterAtOrigo,
      newHasBorder: Boolean = hasBorder,
      newHasFilling: Boolean = hasFilling,
      newColor: rgb.Color = color,
      newFillColor: rgb.Color = fillColor): Polygon = {

    new Polygon(
      identity,
      newPosition,
      newPointsRelativeToCenterAtOrigo,
      newReferencePointRelativeToCenterAtOrigo,
      newHasBorder, newHasFilling,
      newColor, newFillColor)
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
  override
  def moveCenterTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): PictureElement = {

    moveBy(
      xCoordinateInPixels - boundary.center.xInPixels,
      yCoordinateInPixels - boundary.center.yInPixels)
  }

  /**
   *
   *
   * @param offsetsInPixels
   *
   * @return
   */
  override
  def moveBy(offsetsInPixels: Seq[Double]): PictureElement = {
    val newPos = position + (offsetsInPixels.head, offsetsInPixels.tail.head)
    copy(newPosition = newPos)
  }

  /**
   *
   *
   * @param xOffsetInPixels
   * @param yOffsetInPixels
   *
   * @return
   */
  override
  def moveBy(
      xOffsetInPixels: Double,
      yOffsetInPixels: Double): PictureElement = {

    val newPos = position + (xOffsetInPixels, yOffsetInPixels)
    copy(newPosition = newPos)
  }

  /**
   * Rotates this object around origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCWAroundOrigo: Polygon = {
    copy(
      newPosition = Transformer.rotateBy90DegsCW(position),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.rotateBy90DegsCWAroundOrigo),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.rotateBy90DegsCWAroundOrigo)
  }

  /**
   * Rotates this object around its position by 90 degrees clockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCW: Polygon = {
    copy(
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.rotateBy90DegsCWAroundOrigo),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.rotateBy90DegsCWAroundOrigo)
  }

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy90DegsCW(centerOfRotation: Pos): Polygon = {
    copy(
      newPosition = Transformer.rotateBy90DegsCW(position, centerOfRotation),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.rotateBy90DegsCWAroundOrigo),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.rotateBy90DegsCWAroundOrigo)
  }

  /**
   * Rotates this object around origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCCWAroundOrigo: Polygon = {
    copy(
      newPosition = Transformer.rotateBy90DegsCCW(position),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.rotateBy90DegsCCWAroundOrigo),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.rotateBy90DegsCCWAroundOrigo)
  }

  /**
   * Rotates this object around the its position by 90 degrees counterclockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCCW: Polygon = {
    copy(
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.rotateBy90DegsCCWAroundOrigo),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.rotateBy90DegsCCWAroundOrigo)
  }

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy90DegsCCW(centerOfRotation: Pos): Polygon = {
    copy(
      newPosition = Transformer.rotateBy90DegsCCW(position, centerOfRotation),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.rotateBy90DegsCCWAroundOrigo),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.rotateBy90DegsCCWAroundOrigo)
  }

  /**
   * Rotates this object around origo (0,0) by 180 degrees.
   *
   * @return
   */
  override
  def rotateBy180DegsAroundOrigo: Polygon = {
    copy(
      newPosition = Transformer.rotateBy180Degs(position),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.rotateBy180DegsAroundOrigo),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.rotateBy180DegsAroundOrigo)
  }

  /**
   * Rotates this object around its position by 180 degrees.
   *
   * @return
   */
  override
  def rotateBy180Degs: Polygon = {
    copy(
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.rotateBy180DegsAroundOrigo),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.rotateBy180DegsAroundOrigo)
  }

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy180Degs(centerOfRotation: Pos): Polygon = {
    copy(
      newPosition = Transformer.rotateBy180Degs(position, centerOfRotation),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.rotateBy180DegsAroundOrigo),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.rotateBy180DegsAroundOrigo)
  }

  /**
   * Rotates this object around origo (0,0) by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  override
  def rotateByAroundOrigo(angle: Angle): Polygon = {
    rotateByAroundOrigo(angle.inDegrees)
  }

  /**
   * Rotates this object around origo (0,0) by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  override
  def rotateByAroundOrigo(angleInDegrees: Double): Polygon = {
    copy(
      newPosition = Transformer.rotate(position, angleInDegrees),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.rotateByAroundOrigo(angleInDegrees)),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.rotateByAroundOrigo(angleInDegrees))
  }

  /**
   * Rotates this object around its position by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  override
  def rotateBy(angle: Angle): Polygon = {
    rotateBy(angle.inDegrees)
  }

  /**
   * Rotates this object around its position by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  override
  def rotateBy(angleInDegrees: Double): Polygon = {
    copy(
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.rotateByAroundOrigo(angleInDegrees)),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.rotateByAroundOrigo(angleInDegrees))
  }

  /**
   * Rotates this object around a given point by the specified angle.
   *
   * @param angle
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy(
      angle: Angle,
      centerOfRotation: Pos): Polygon = {

    rotateBy(angle.inDegrees, centerOfRotation)
  }

  /**
   * Rotates this object around a given point by the specified number of degrees.
   *
   * @param angleInDegrees
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): Polygon = {

    // println(s"rotateBy($angleInDegrees, $centerOfRotation): Polygon(${pointsRelativeToCenterAtOrigo.length})")

    copy(
      newPosition = Transformer.rotate(position, angleInDegrees, centerOfRotation),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.rotateByAroundOrigo(angleInDegrees)),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.rotateByAroundOrigo(angleInDegrees))
  }

  /**
   * Scales this object to a given width in relation to its position.
   *
   * @param targetWidth
   *
   * @return
   */
  override
  def scaleHorizontallyTo(targetWidth: Double): Polygon =
    scaleHorizontallyTo(targetWidth, position)

  /**
   * Scales this object to a given width in relation to a given point.
   *
   * @param targetWidth
   * @param relativityPoint
   *
   * @return
   */
  override
  def scaleHorizontallyTo(
      targetWidth: Double,
      relativityPoint: Pos): Polygon = {

    scaleTo(
      targetWidth,
      targetHeight = height.inPixels,
      relativityPoint = relativityPoint)
  }

  /**
   * Scales this object to a given width in relation to the origo.
   *
   * @param targetWidth
   *
   * @return
   */
  override
  def scaleHorizontallyToRelativeToOrigo(targetWidth: Double): Polygon =
    scaleToRelativeToOrigo(
      targetWidth,
      targetHeight = height.inPixels)

  /**
   * Scales this object to a given height in relation to its position.
   *
   * @param targetHeight
   *
   * @return
   */
  override
  def scaleVerticallyTo(targetHeight: Double): Polygon =
    scaleVerticallyTo(targetHeight, position)

  /**
   * Scales this object to a given height in relation to a given point.
   *
   * @param targetHeight
   * @param relativityPoint
   *
   * @return
   */
  override
  def scaleVerticallyTo(
      targetHeight: Double,
      relativityPoint: Pos): Polygon = {

    scaleTo(
      targetWidth = width.inPixels,
      targetHeight = targetHeight,
      relativityPoint = relativityPoint)
  }

  /**
   * Scales this object to a given height in relation to the origo.
   *
   * @param targetHeight
   *
   * @return
   */
  override
  def scaleVerticallyToRelativeToOrigo(targetHeight: Double): Polygon =
    scaleToRelativeToOrigo(
      targetWidth = width.inPixels,
      targetHeight = targetHeight)

  /**
   * Scales this object in relation to its position by
   * using a single length for both width and height.
   *
   * @param targetSideLength
   *
   * @return
   */
  override
  def scaleTo(targetSideLength: Double): Polygon =
    scaleTo(targetSideLength, position)

  /**
   * Scales this object in relation to a given point by
   * using a single length for both width and height.
   *
   * @param targetSideLength
   * @param relativityPoint
   *
   * @return
   */
  override
  def scaleTo(
      targetSideLength: Double,
      relativityPoint: Pos): Polygon = {

    scaleTo(
      targetWidth = targetSideLength,
      targetHeight = targetSideLength,
      relativityPoint = relativityPoint)
  }

  /**
   * Scales this object in relation to the origo by
   * using a single length for both width and height.
   *
   * @param targetSideLength
   *
   * @return
   */
  override
  def scaleToRelativeToOrigo(targetSideLength: Double): Polygon =
    scaleToRelativeToOrigo(
      targetWidth = targetSideLength,
      targetHeight = targetSideLength)

  /**
   * Scales this object to given width and height in relation to its position.
   *
   * @param targetWidth
   * @param targetHeight
   *
   * @return
   */
  override
  def scaleTo(
      targetWidth: Double,
      targetHeight: Double): Polygon = {

    scaleTo(targetWidth, targetHeight, position)
  }

  /**
   * Scales this object to given width and height in relation to a given point.
   *
   * @param targetWidth
   * @param targetHeight
   * @param relativityPoint
   *
   * @return
   */
  override
  def scaleTo(
      targetWidth: Double,
      targetHeight: Double,
      relativityPoint: Pos): Polygon = {

    val (horizontalFactor, verticalFactor) =
      scalingFactorsFor(targetWidth, targetHeight)

    scaleBy(
      horizontalFactor = horizontalFactor,
      verticalFactor = verticalFactor,
      relativityPoint = relativityPoint)
  }

  /**
   * Scales this object to given width and height in relation to the origo.
   *
   * @param targetWidth
   * @param targetHeight
   *
   * @return
   */
  override
  def scaleToRelativeToOrigo(
      targetWidth: Double,
      targetHeight: Double): Polygon = {

    val (horizontalFactor, verticalFactor) =
      scalingFactorsFor(targetWidth, targetHeight)

    scaleByRelativeToOrigo(
      horizontalFactor = horizontalFactor,
      verticalFactor = verticalFactor)
  }

  /**
   *
   *
   * @param targetWidth
   * @param targetHeight
   *
   * @return
   */
  def scalingFactorsFor(
      targetWidth: Double,
      targetHeight: Double): (Double, Double) = {

    val horizontalFactor = targetWidth / width.inPixels
    val verticalFactor = targetHeight / height.inPixels

    (horizontalFactor, verticalFactor)
  }

  /**
   * Scales this object horizontally in relation to its position.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleHorizontallyBy(factor: Double): Polygon =
    copy(
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.scaleHorizontallyByRelativeToOrigo(factor)),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.scaleHorizontallyByRelativeToOrigo(factor))

  /**
   * Scales this object horizontally in relation to a given point.
   *
   * @param factor
   * @param relativityPoint
   *
   * @return
   */
  override
  def scaleHorizontallyBy(
      factor: Double,
      relativityPoint: Pos): Polygon = {

    copy(
      newPosition = Transformer.scaleHorizontally(position, factor, relativityPoint),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.scaleHorizontallyByRelativeToOrigo(factor)),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.scaleHorizontallyByRelativeToOrigo(factor))
  }

  /**
   * Scales this object horizontally in relation to the origo.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleHorizontallyByRelativeToOrigo(factor: Double): Polygon =
    copy(
      newPosition = Transformer.scaleHorizontally(position, factor),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.scaleHorizontallyByRelativeToOrigo(factor)),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.scaleHorizontallyByRelativeToOrigo(factor))

  /**
   * Scales this object vertically in relation to its position.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleVerticallyBy(factor: Double): Polygon =
    copy(
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.scaleVerticallyByRelativeToOrigo(factor)),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.scaleVerticallyByRelativeToOrigo(factor))

  /**
   * Scales this object vertically in relation to a given point.
   *
   * @param factor
   * @param relativityPoint
   *
   * @return
   */
  override
  def scaleVerticallyBy(
      factor: Double,
      relativityPoint: Pos): Polygon = {

    copy(
      newPosition = Transformer.scaleVertically(position, factor, relativityPoint),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.scaleVerticallyByRelativeToOrigo(factor)),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.scaleVerticallyByRelativeToOrigo(factor))
  }

  /**
   * Scales this object vertically in relation to the origo.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleVerticallyByRelativeToOrigo(factor: Double): Polygon =
    copy(
      newPosition = Transformer.scaleVertically(position, factor),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.scaleVerticallyByRelativeToOrigo(factor)),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.scaleVerticallyByRelativeToOrigo(factor))

  /**
   * Scales this object in relation to its position by using a given factor
   * for both horizontal and vertical directions.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleBy(factor: Double): Polygon = {
    copy(
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.scaleByRelativeToOrigo(factor)),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.scaleByRelativeToOrigo(factor))
  }

  /**
   * Scales this object in relation to a given point by using a given factor
   * for both horizontal and vertical directions.
   *
   * @param factor
   * @param relativityPoint
   *
   * @return
   */
  override
  def scaleBy(
      factor: Double,
      relativityPoint: Pos): Polygon = {

    copy(
      newPosition = Transformer.scale(position, factor, relativityPoint),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.scaleByRelativeToOrigo(factor)),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.scaleByRelativeToOrigo(factor))
  }

  /**
   * Scales this object in relation to the origo by using a given factor for
   * both horizontal and vertical directions.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleByRelativeToOrigo(factor: Double): Polygon =
    copy(
      newPosition = Transformer.scale(position, factor),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.scaleByRelativeToOrigo(factor)),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.scaleByRelativeToOrigo(factor))

  /**
   * Scales this object by given horizontal and vertical factors in relation to its position.
   *
   * @param horizontalFactor
   * @param verticalFactor
   *
   * @return
   */
  override
  def scaleBy(
      horizontalFactor: Double,
      verticalFactor: Double): Polygon = {

    copy(
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.scaleByRelativeToOrigo(horizontalFactor, verticalFactor)),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.scaleByRelativeToOrigo(horizontalFactor, verticalFactor))
  }

  /**
   * Scales this object by given horizontal and vertical factors in relation to a given point.
   *
   * @param horizontalFactor
   * @param verticalFactor
   * @param relativityPoint
   *
   * @return
   */
  override
  def scaleBy(
      horizontalFactor: Double,
      verticalFactor: Double,
      relativityPoint: Pos): Polygon = {

    copy(
      newPosition = Transformer.scale(position, horizontalFactor, verticalFactor, relativityPoint),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.scaleByRelativeToOrigo(horizontalFactor, verticalFactor)),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.scaleByRelativeToOrigo(horizontalFactor, verticalFactor))
  }

  /**
   * Scales this object by given horizontal and vertical factors in relation to the origo.
   *
   * @param horizontalFactor
   * @param verticalFactor
   *
   * @return
   */
  override
  def scaleByRelativeToOrigo(
      horizontalFactor: Double,
      verticalFactor: Double): Polygon = {

    copy(
      newPosition = Transformer.scale(position, horizontalFactor, verticalFactor),
      newPointsRelativeToCenterAtOrigo = pointsRelativeToCenterAtOrigo.map(_.scaleByRelativeToOrigo(horizontalFactor, verticalFactor)),
      newReferencePointRelativeToCenterAtOrigo = referencePointRelativeToCenterAtOrigo.scaleByRelativeToOrigo(horizontalFactor, verticalFactor))
  }

}
