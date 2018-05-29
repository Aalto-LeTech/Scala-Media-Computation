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
import smcl.infrastructure.{Identity, MathUtils}
import smcl.modeling.d2._
import smcl.modeling.{Angle, Transformer}
import smcl.settings._




/**
 * An object-based API for creating arcs.
 *
 * @author Aleksi Lukkarinen
 */
object Arc {

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @param startAngleInDegrees
   * @param arcAngleInDegrees
   * @param hasBorder
   * @param hasFilling
   * @param color
   * @param fillColor
   *
   * @return
   */
  def apply(
      position: Pos = DefaultPosition,
      widthInPixels: Double,
      heightInPixels: Double,
      startAngleInDegrees: Double = Angle.Zero.inDegrees,
      arcAngleInDegrees: Double = Angle.FullAngleInDegrees,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: rgb.Color = DefaultPrimaryColor,
      fillColor: rgb.Color = DefaultSecondaryColor): VectorGraphic = {

    require(widthInPixels >= 0, "Width of an arc cannot be negative")
    require(heightInPixels >= 0, "Height of an arc cannot be negative")

    val identity = Identity()

    new Arc(
      identity,
      position,
      widthInPixels, heightInPixels,
      startAngleInDegrees, arcAngleInDegrees,
      horizontalScalingFactor = IdentityScalingFactor,
      verticalScalingFactor = IdentityScalingFactor,
      rotationAngleInDegrees = DefaultRotationAngleInDegrees,
      hasBorder, hasFilling,
      color, fillColor)
  }

}




/**
 *
 *
 * @param identity
 * @param position
 * @param untransformedWidthInPixels
 * @param untransformedHeightInPixels
 * @param startAngleInDegrees
 * @param arcAngleInDegrees
 * @param horizontalScalingFactor
 * @param verticalScalingFactor
 * @param rotationAngleInDegrees
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
class Arc private(
    val identity: Identity,
    override val position: Pos,
    val untransformedWidthInPixels: Double,
    val untransformedHeightInPixels: Double,
    val startAngleInDegrees: Double,
    val arcAngleInDegrees: Double,
    val horizontalScalingFactor: Double,
    val verticalScalingFactor: Double,
    val rotationAngleInDegrees: Double,
    val hasBorder: Boolean = ShapesHaveBordersByDefault,
    val hasFilling: Boolean = ShapesHaveFillingsByDefault,
    val color: rgb.Color = DefaultPrimaryColor,
    val fillColor: rgb.Color = DefaultSecondaryColor)
    extends VectorGraphic {

  /** Tells if this [[Arc]] can be rendered on a bitmap. */
  override
  lazy val isRenderable: Boolean =
    (untransformedWidthInPixels >= 0.5
        && untransformedHeightInPixels >= 0.5
        && arcAngleInDegrees != 0
        && horizontalScalingFactor * untransformedWidthInPixels >= 0.5
        && verticalScalingFactor * untransformedHeightInPixels >= 0.5
        && (hasBorder || hasFilling))

  private[this]
  lazy val corners: Seq[Pos] = {
    if (isNotRenderable) {
      Seq.fill(4)(Pos.NotDefined)
    }
    else {
      val halfWidth = horizontalScalingFactor * untransformedWidthInPixels / 2.0
      val halfHeight = verticalScalingFactor * untransformedHeightInPixels / 2.0

      Seq(
        Transformer.rotate(position + (-halfWidth, -halfHeight), rotationAngleInDegrees),
        Transformer.rotate(position + (halfWidth, -halfHeight), rotationAngleInDegrees),
        Transformer.rotate(position + (halfWidth, halfHeight), rotationAngleInDegrees),
        Transformer.rotate(position + (-halfWidth, halfHeight), rotationAngleInDegrees)
      )
    }
  }

  /** Transformed upper left corner of this [[Arc]]. */
  lazy val upperLeftCorner: Pos = corners.head

  /** Transformed upper right corner of this [[Arc]]. */
  lazy val upperRightCorner: Pos = corners.tail.head

  /** Transformed lower right corner of this [[Arc]]. */
  lazy val lowerRightCorner: Pos = corners.tail.tail.head

  /** Transformed lower left corner of this [[Arc]]. */
  lazy val lowerLeftCorner: Pos = corners.tail.tail.tail.head

  /** Transformed boundary of this [[Arc]]. */
  override
  lazy val boundary: Bounds =
    if (isNotRenderable)
      Bounds.NotDefined
    else
      Bounds(upperLeftCorner, lowerRightCorner)

  /** Transformed width of this [[Arc]]. */
  lazy val widthInPixels: Double = dimensions.width.inPixels

  /** Transformed height of this [[Arc]]. */
  lazy val heightInPixels: Double = dimensions.height.inPixels

  /** Tells if this [[Arc]] represents a circle or an ellipse. */
  override
  lazy val isFullCycle: Boolean = arcAngleInDegrees.abs >= Angle.FullAngleInDegrees

  /** Tells if this [[Arc]] represents a circle. */
  override
  lazy val isCircle: Boolean = isFullCycle && (widthInPixels == heightInPixels)

  /** Tells if this [[Arc]] represents an ellipse. */
  override
  lazy val isEllipse: Boolean = isFullCycle && (widthInPixels != heightInPixels)

  /** Tell if this [[PictureElement]] instance is an [[Arc]]. */
  override
  val isArc: Boolean = true

  private
  def decideNewRotationAngleFor(newRotationAngleInDegrees: Double): Double = {
    // If this arc represents a circle, rotating it should not have any effect on
    // its appearance, and thus the rotation angle can (and must) be zero all the
    // time. (The position can, of course, change if the rotation is not performed
    // around the arc's center point, but that is irrelevant here.)
    if (isCircle)
      Angle.Zero.inDegrees
    else
      rotationAngleInDegrees + newRotationAngleInDegrees
  }

  /**
   *
   *
   * @param newStartAngleInDegrees
   * @param newArcAngleInDegrees
   * @param newHasBorder
   * @param newHasFilling
   * @param newColor
   * @param newFillColor
   *
   * @return
   */
  def copy(
      newStartAngleInDegrees: Double = startAngleInDegrees,
      newArcAngleInDegrees: Double = arcAngleInDegrees,
      newHasBorder: Boolean = hasBorder,
      newHasFilling: Boolean = hasFilling,
      newColor: rgb.Color = color,
      newFillColor: rgb.Color = fillColor): Arc = {

    internalCopy(
      newStartAngleInDegrees = newStartAngleInDegrees,
      newArcAngleInDegrees = newArcAngleInDegrees,
      newHasBorder = newHasBorder,
      newHasFilling = newHasFilling,
      newColor = newColor,
      newFillColor = newFillColor)
  }

  /**
   *
   *
   * @param newPosition
   * @param newUntransformedWidthInPixels
   * @param newUntransformedHeightInPixels
   * @param newStartAngleInDegrees
   * @param newArcAngleInDegrees
   * @param newHorizontalScalingFactor
   * @param newVerticalScalingFactor
   * @param newRotationAngleInDegrees
   * @param newHasBorder
   * @param newHasFilling
   * @param newColor
   * @param newFillColor
   *
   * @return
   */
  private
  def internalCopy(
      newPosition: Pos = position,
      newUntransformedWidthInPixels: Double = untransformedWidthInPixels,
      newUntransformedHeightInPixels: Double = untransformedHeightInPixels,
      newStartAngleInDegrees: Double = startAngleInDegrees,
      newArcAngleInDegrees: Double = arcAngleInDegrees,
      newHorizontalScalingFactor: Double = horizontalScalingFactor,
      newVerticalScalingFactor: Double = verticalScalingFactor,
      newRotationAngleInDegrees: Double = rotationAngleInDegrees,
      newHasBorder: Boolean = hasBorder,
      newHasFilling: Boolean = hasFilling,
      newColor: rgb.Color = color,
      newFillColor: rgb.Color = fillColor): Arc = {

    val limitedArcAngle =
      newArcAngleInDegrees
          .min(Angle.FullAngleInDegrees)
          .max(-Angle.FullAngleInDegrees)

    new Arc(
      identity,
      newPosition,
      newUntransformedWidthInPixels,
      newUntransformedHeightInPixels,
      newStartAngleInDegrees,
      limitedArcAngle,
      newHorizontalScalingFactor,
      newVerticalScalingFactor,
      newRotationAngleInDegrees,
      newHasBorder, newHasFilling,
      newColor, newFillColor)
  }

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
    sum = prime * sum + untransformedWidthInPixels.##
    sum = prime * sum + untransformedHeightInPixels.##
    sum = prime * sum + startAngleInDegrees.##
    sum = prime * sum + arcAngleInDegrees.##
    sum = prime * sum + horizontalScalingFactor.##
    sum = prime * sum + verticalScalingFactor.##
    sum = prime * sum + rotationAngleInDegrees.##
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
    other.isInstanceOf[Arc]
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
      case that: Arc =>
        that.canEqual(this) &&
            that.position == this.position &&
            that.untransformedWidthInPixels == this.untransformedWidthInPixels &&
            that.untransformedHeightInPixels == this.untransformedHeightInPixels &&
            that.startAngleInDegrees == this.startAngleInDegrees &&
            that.arcAngleInDegrees == this.arcAngleInDegrees &&
            that.horizontalScalingFactor == this.horizontalScalingFactor &&
            that.verticalScalingFactor == this.verticalScalingFactor &&
            that.rotationAngleInDegrees == this.rotationAngleInDegrees &&
            that.hasBorder == this.hasBorder &&
            that.hasFilling == this.hasFilling &&
            that.color == this.color &&
            that.fillColor == this.fillColor

      case _ => false
    }
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  override
  def moveUpperLeftCornerTo(coordinatesInPixels: Seq[Double]): Arc = {
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
      yCoordinateInPixels: Double): Arc = {

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
  def moveCenterTo(coordinatesInPixels: Seq[Double]): Arc = {
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
      yCoordinateInPixels: Double): Arc = {

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
  def moveBy(offsetsInPixels: Seq[Double]): Arc = {
    val newPos = position + (offsetsInPixels.head, offsetsInPixels.tail.head)
    internalCopy(newPosition = newPos)
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
      yOffsetInPixels: Double): Arc = {

    val newPos = position + (xOffsetInPixels, yOffsetInPixels)
    internalCopy(newPosition = newPos)
  }

  /**
   * Rotates this object around origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCWAroundOrigo: Arc = {
    internalCopy(
      newPosition = Transformer.rotateBy90DegsCW(position),
      newRotationAngleInDegrees = decideNewRotationAngleFor(-Angle.RightAngleInDegrees))
  }

  /**
   * Rotates this object around its center by 90 degrees clockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCW: Arc = {
    internalCopy(
      newRotationAngleInDegrees = decideNewRotationAngleFor(-Angle.RightAngleInDegrees))
  }

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy90DegsCW(centerOfRotation: Pos): Arc = {
    internalCopy(
      newPosition = Transformer.rotateBy90DegsCW(position, centerOfRotation),
      newRotationAngleInDegrees = decideNewRotationAngleFor(-Angle.RightAngleInDegrees))
  }

  /**
   * Rotates this object around origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCCWAroundOrigo: Arc = {
    internalCopy(
      newPosition = Transformer.rotateBy90DegsCCW(position),
      newRotationAngleInDegrees = decideNewRotationAngleFor(Angle.RightAngleInDegrees))
  }

  /**
   * Rotates this object around the its center by 90 degrees counterclockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCCW: Arc = {
    internalCopy(
      newRotationAngleInDegrees = decideNewRotationAngleFor(Angle.RightAngleInDegrees))
  }

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy90DegsCCW(centerOfRotation: Pos): Arc = {
    internalCopy(
      newPosition = Transformer.rotateBy90DegsCCW(position, centerOfRotation),
      newRotationAngleInDegrees = decideNewRotationAngleFor(Angle.RightAngleInDegrees))
  }

  /**
   * Rotates this object around origo (0,0) by 180 degrees.
   *
   * @return
   */
  override
  def rotateBy180DegsAroundOrigo: Arc = {
    internalCopy(
      newPosition = Transformer.rotateBy180Degs(position),
      newRotationAngleInDegrees = decideNewRotationAngleFor(
        MathUtils.rotateHalfTurnTowardsZeroAngle(rotationAngleInDegrees)))
  }

  /**
   * Rotates this object around its center by 180 degrees.
   *
   * @return
   */
  override
  def rotateBy180Degs: Arc = {
    internalCopy(
      newRotationAngleInDegrees = decideNewRotationAngleFor(
        MathUtils.rotateHalfTurnTowardsZeroAngle(rotationAngleInDegrees)))
  }

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy180Degs(centerOfRotation: Pos): Arc = {
    internalCopy(
      newPosition = Transformer.rotateBy180Degs(position, centerOfRotation),
      newRotationAngleInDegrees = decideNewRotationAngleFor(
        MathUtils.rotateHalfTurnTowardsZeroAngle(rotationAngleInDegrees)))
  }

  /**
   * Rotates this object around its center by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  override
  def rotateByAroundOrigo(angle: Angle): Arc =
    rotateByAroundOrigo(angle.inDegrees)

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  override
  def rotateByAroundOrigo(angleInDegrees: Double): Arc = {
    internalCopy(
      newPosition = Transformer.rotate(position, angleInDegrees),
      newRotationAngleInDegrees = decideNewRotationAngleFor(angleInDegrees))
  }

  /**
   * Rotates this object around its center by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  override
  def rotateBy(angle: Angle): Arc = rotateBy(angle.inDegrees)

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  override
  def rotateBy(angleInDegrees: Double): Arc = {
    internalCopy(
      newRotationAngleInDegrees = decideNewRotationAngleFor(angleInDegrees))
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
      centerOfRotation: Pos): Arc = {

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
      centerOfRotation: Pos): Arc = {

    // println(s"rotateBy($angleInDegrees, $centerOfRotation): Circle($widthInPixels x $heightInPixels)")

    internalCopy(
      newPosition = Transformer.rotate(position, angleInDegrees, centerOfRotation),
      newRotationAngleInDegrees = decideNewRotationAngleFor(angleInDegrees))
  }

  /**
   * Scales this object to a given width in relation to its center.
   *
   * @param targetWidth
   *
   * @return
   */
  override
  def scaleHorizontallyTo(targetWidth: Double): Arc =
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
      relativityPoint: Pos): Arc = {

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
  def scaleHorizontallyToRelativeToOrigo(targetWidth: Double): Arc =
    scaleToRelativeToOrigo(
      targetWidth,
      targetHeight = height.inPixels)

  /**
   * Scales this object to a given height in relation to its center.
   *
   * @param targetHeight
   *
   * @return
   */
  override
  def scaleVerticallyTo(targetHeight: Double): Arc =
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
      relativityPoint: Pos): Arc = {

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
  def scaleVerticallyToRelativeToOrigo(targetHeight: Double): Arc =
    scaleToRelativeToOrigo(
      targetWidth = width.inPixels,
      targetHeight = targetHeight)

  /**
   * Scales this object in relation to its center by
   * using a single length for both width and height.
   *
   * @param targetSideLength
   *
   * @return
   */
  override
  def scaleTo(targetSideLength: Double): Arc =
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
      relativityPoint: Pos): Arc = {

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
  def scaleToRelativeToOrigo(targetSideLength: Double): Arc =
    scaleToRelativeToOrigo(
      targetWidth = targetSideLength,
      targetHeight = targetSideLength)

  /**
   * Scales this object to given width and height in relation to its center.
   *
   * @param targetWidth
   * @param targetHeight
   *
   * @return
   */
  override
  def scaleTo(
      targetWidth: Double,
      targetHeight: Double): Arc = {

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
      relativityPoint: Pos): Arc = {

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
      targetHeight: Double): Arc = {

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
   * Scales this object horizontally in relation to its center.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleHorizontallyBy(factor: Double): Arc =
    scaleHorizontallyBy(factor, position)

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
      relativityPoint: Pos): Arc = {

    scaleBy(
      horizontalFactor = factor,
      verticalFactor = Scalable.IdentityScalingFactor,
      relativityPoint = relativityPoint)
  }

  /**
   * Scales this object horizontally in relation to the origo.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleHorizontallyByRelativeToOrigo(factor: Double): Arc =
    scaleByRelativeToOrigo(
      horizontalFactor = factor,
      verticalFactor = Scalable.IdentityScalingFactor)

  /**
   * Scales this object vertically in relation to its center.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleVerticallyBy(factor: Double): Arc =
    scaleVerticallyBy(factor, position)

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
      relativityPoint: Pos): Arc = {

    scaleBy(
      horizontalFactor = Scalable.IdentityScalingFactor,
      verticalFactor = factor,
      relativityPoint = relativityPoint)
  }

  /**
   * Scales this object vertically in relation to the origo.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleVerticallyByRelativeToOrigo(factor: Double): Arc =
    scaleByRelativeToOrigo(
      horizontalFactor = Scalable.IdentityScalingFactor,
      verticalFactor = factor)

  /**
   * Scales this object in relation to its center by using a given factor
   * for both horizontal and vertical directions.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleBy(factor: Double): Arc =
    scaleBy(factor, position)

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
      relativityPoint: Pos): Arc = {

    scaleBy(
      horizontalFactor = factor,
      verticalFactor = factor,
      relativityPoint = relativityPoint)
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
  def scaleByRelativeToOrigo(factor: Double): Arc =
    scaleByRelativeToOrigo(
      horizontalFactor = factor,
      verticalFactor = factor)

  /**
   * Scales this object by given horizontal and vertical factors in relation to its center.
   *
   * @param horizontalFactor
   * @param verticalFactor
   *
   * @return
   */
  override
  def scaleBy(
      horizontalFactor: Double,
      verticalFactor: Double): Arc = {

    internalCopy(
      newHorizontalScalingFactor = horizontalFactor * horizontalScalingFactor,
      newVerticalScalingFactor = verticalFactor * verticalScalingFactor)
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
      relativityPoint: Pos): Arc = {

    internalCopy(
      newPosition = Transformer.scale(position, horizontalFactor, verticalFactor, relativityPoint),
      newHorizontalScalingFactor = horizontalFactor * horizontalScalingFactor,
      newVerticalScalingFactor = verticalFactor * verticalScalingFactor)
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
      verticalFactor: Double): Arc = {

    internalCopy(
      newPosition = Transformer.scale(position, horizontalFactor, verticalFactor),
      newHorizontalScalingFactor = horizontalFactor * horizontalScalingFactor,
      newVerticalScalingFactor = verticalFactor * verticalScalingFactor)
  }

}
