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
import smcl.modeling.d2.{Bounds, Dims, NumberOfDimensions, Pos, Scalable}
import smcl.modeling.{AffineTransformation, Angle}
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
   * @param upperLeftCorner
   * @param lowerRightCorner
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
      upperLeftCorner: Pos,
      lowerRightCorner: Pos,
      startAngleInDegrees: Double = Angle.Zero.inDegrees,
      arcAngleInDegrees: Double = Angle.FullAngleInDegrees,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: rgb.Color = DefaultPrimaryColor,
      fillColor: rgb.Color = DefaultSecondaryColor): VectorGraphic = {

    val identity = Identity()

    val size = upperLeftCorner.dimsTo(lowerRightCorner)
    val width = size.width.inPixels
    val height = size.height.inPixels

    val x = upperLeftCorner.centerBetween(lowerRightCorner)
    val currentTransformation =
      AffineTransformation.forTranslationOf(x.xInPixels, x.yInPixels)

    new Arc(
      identity,
      width, height,
      startAngleInDegrees, arcAngleInDegrees,
      currentTransformation,
      hasBorder, hasFilling,
      color, fillColor)
  }

}




/**
 *
 *
 * @param identity
 * @param untransformedWidthInPixels
 * @param untransformedHeightInPixels
 * @param startAngleInDegrees
 * @param arcAngleInDegrees
 * @param currentTransformation
 * @param hasBorder
 * @param hasFilling
 * @param color
 * @param fillColor
 *
 * @author Aleksi Lukkarinen
 */
class Arc private(
    val identity: Identity,
    val untransformedWidthInPixels: Double,
    val untransformedHeightInPixels: Double,
    val startAngleInDegrees: Double,
    val arcAngleInDegrees: Double,
    val currentTransformation: AffineTransformation,
    val hasBorder: Boolean = ShapesHaveBordersByDefault,
    val hasFilling: Boolean = ShapesHaveFillingsByDefault,
    val color: rgb.Color = DefaultPrimaryColor,
    val fillColor: rgb.Color = DefaultSecondaryColor)
    extends VectorGraphic {

  val corners: Seq[Pos] = {
    val halfWidth = untransformedWidthInPixels / 2.0
    val halfHeight = untransformedHeightInPixels / 2.0

    val upperLeftCorner = currentTransformation.process(Pos(-halfWidth, -halfHeight))
    val lowerRightCorner = currentTransformation.process(Pos(halfWidth, halfHeight))

    Seq(upperLeftCorner, lowerRightCorner)
  }

  /** Transformed upper left corner of this [[Arc]]. */
  val upperLeftCorner: Pos = corners.head

  /** Transformed lower right corner of this [[Arc]]. */
  val lowerRightCorner: Pos = corners.tail.head

  /** Transformed boundary of this [[Arc]]. */
  // TODO: Calculate boundary so that it reflects the current transformation!!!!
  override
  val boundary: Bounds = Bounds(upperLeftCorner, lowerRightCorner)

  /** Transformed dimensions of this [[Arc]]. */
  override
  val dimensions: Dims = boundary.dimensions

  /** Transformed width of this [[Arc]]. */
  val widthInPixels: Double = dimensions.width.inPixels

  /** Transformed height of this [[Arc]]. */
  val heightInPixels: Double = dimensions.height.inPixels

  /** Transformed position of this [[Arc]]. */
  override
  val position: Pos = boundary.center

  /** Tells if this [[Arc]] can be rendered on a bitmap. */
  override
  val isRenderable: Boolean = true

  /**
   *
   *
   * @return
   */
  override
  def isArc: Boolean = true

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
   * @param newStartAngleInDegrees
   * @param newArcAngleInDegrees
   * @param newTransformation
   * @param newHasBorder
   * @param newHasFilling
   * @param newColor
   * @param newFillColor
   *
   * @return
   */
  private
  def internalCopy(
      newStartAngleInDegrees: Double = startAngleInDegrees,
      newArcAngleInDegrees: Double = arcAngleInDegrees,
      newTransformation: AffineTransformation = currentTransformation,
      newHasBorder: Boolean = hasBorder,
      newHasFilling: Boolean = hasFilling,
      newColor: rgb.Color = color,
      newFillColor: rgb.Color = fillColor): Arc = {

    new Arc(
      identity,
      untransformedWidthInPixels, untransformedHeightInPixels,
      newStartAngleInDegrees, newArcAngleInDegrees,
      newTransformation,
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
  def moveBy(offsetsInPixels: Seq[Double]): Arc = {
    val newTx = currentTransformation.translate(
      offsetsInPixels.head,
      offsetsInPixels.tail.head)

    internalCopy(newTransformation = newTx)
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

    val newTx = currentTransformation.translate(xOffsetInPixels, yOffsetInPixels)

    internalCopy(newTransformation = newTx)
  }

  /**
   * Rotates this object around origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCWAroundOrigo: Arc = {
    val newTransformation = currentTransformation.rotate90DegsCWAroundOrigo

    internalCopy(newTransformation = newTransformation)
  }

  /**
   * Rotates this object around its center by 90 degrees clockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCW: Arc = rotateBy90DegsCW(position)

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy90DegsCW(centerOfRotation: Pos): Arc = {
    val newTransformation = currentTransformation.rotate90DegsCWAroundPoint(centerOfRotation)

    internalCopy(newTransformation = newTransformation)
  }

  /**
   * Rotates this object around origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCCWAroundOrigo: Arc = {
    val newTransformation = currentTransformation.rotate90DegsCCWAroundOrigo

    internalCopy(newTransformation = newTransformation)
  }

  /**
   * Rotates this object around the its center by 90 degrees counterclockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCCW: Arc = rotateBy90DegsCCW(position)

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy90DegsCCW(centerOfRotation: Pos): Arc = {
    val newTransformation = currentTransformation.rotate90DegsCCWAroundPoint(centerOfRotation)

    internalCopy(newTransformation = newTransformation)
  }

  /**
   * Rotates this object around origo (0,0) by 180 degrees.
   *
   * @return
   */
  override
  def rotateBy180DegsAroundOrigo: Arc = {
    val newTransformation = currentTransformation.rotate180DegsAroundOrigo

    internalCopy(newTransformation = newTransformation)
  }

  /**
   * Rotates this object around its center by 180 degrees.
   *
   * @return
   */
  override
  def rotateBy180Degs: Arc = rotateBy180Degs(position)

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy180Degs(centerOfRotation: Pos): Arc = {
    val newTransformation = currentTransformation.rotate180DegsAroundPoint(centerOfRotation)

    internalCopy(newTransformation = newTransformation)
  }

  /**
   * Rotates this object around its center by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  override
  def rotateByAroundOrigo(angle: Angle): Arc = rotateByAroundOrigo(angle)

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  override
  def rotateByAroundOrigo(angleInDegrees: Double): Arc = {
    val newTransformation =
      currentTransformation.rotateAroundOrigo(Angle(-angleInDegrees))

    internalCopy(newTransformation = newTransformation)
  }

  /**
   * Rotates this object around its center by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  override
  def rotateBy(angle: Angle): Arc = rotateBy(angle)

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  override
  def rotateBy(angleInDegrees: Double): Arc = rotateBy(angleInDegrees, position)

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
  override
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): Arc = {

    val newTransformation =
      currentTransformation.rotateAroundPoint(Angle(angleInDegrees), centerOfRotation)

    internalCopy(newTransformation = newTransformation)
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

    scaleBy(horizontalFactor, verticalFactor, position)
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

    val newTx = currentTransformation.scaleRelativeToPoint(
      horizontalFactor, verticalFactor, relativityPoint)

    internalCopy(newTransformation = newTx)
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

    val newTx = currentTransformation.scaleRelativeToOrigo(
      horizontalFactor, verticalFactor)

    internalCopy(newTransformation = newTx)
  }

}
