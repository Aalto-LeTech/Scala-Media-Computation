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
import smcl.modeling.d2.{Bounds, Dims, NumberOfDimensions, Pos}
import smcl.modeling.{AffineTransformation, Angle}
import smcl.settings._




/**
 * An object-based API for creating arcs.
 *
 * @author Aleksi Lukkarinen
 */
object Arc {

  /** */
  private
  val InitialScalingFactor = 1.0

  /** */
  private
  val InitialShearingFactor = 0.0

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
  @inline
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
    val currentRotationAngleInDegrees = Angle.Zero.inDegrees
    val currentHorizontalScalingFactor = InitialScalingFactor
    val currentVerticalScalingFactor = InitialScalingFactor
    val currentHorizontalShearingFactor = InitialShearingFactor
    val currentVerticalShearingFactor = InitialShearingFactor

    val correctionOffsets = (0.5, 0.5)
    val correctedUpperLeftCorner = upperLeftCorner + correctionOffsets
    val correctedLowerRightCorner = lowerRightCorner - correctionOffsets

    val x = correctedUpperLeftCorner.centerBetween(correctedLowerRightCorner)
    val currentTransformation =
      AffineTransformation.forTranslationOf(x.xInPixels, x.yInPixels)

    new Arc(
      identity,
      correctedUpperLeftCorner, correctedLowerRightCorner,
      startAngleInDegrees, arcAngleInDegrees,
      currentRotationAngleInDegrees,
      currentHorizontalScalingFactor,
      currentVerticalScalingFactor,
      currentHorizontalShearingFactor,
      currentVerticalShearingFactor,
      currentTransformation,
      hasBorder, hasFilling,
      color, fillColor)
  }

}




/**
 *
 *
 * @param identity
 * @param upperLeftCorner
 * @param lowerRightCorner
 * @param startAngleInDegrees
 * @param arcAngleInDegrees
 * @param currentRotationAngleInDegrees
 * @param currentHorizontalScalingFactor
 * @param currentVerticalScalingFactor
 * @param currentHorizontalShearingFactor
 * @param currentVerticalShearingFactor
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
    val upperLeftCorner: Pos,
    val lowerRightCorner: Pos,
    val startAngleInDegrees: Double,
    val arcAngleInDegrees: Double,
    val currentRotationAngleInDegrees: Double,
    val currentHorizontalScalingFactor: Double,
    val currentVerticalScalingFactor: Double,
    val currentHorizontalShearingFactor: Double,
    val currentVerticalShearingFactor: Double,
    val currentTransformation: AffineTransformation,
    val hasBorder: Boolean = ShapesHaveBordersByDefault,
    val hasFilling: Boolean = ShapesHaveFillingsByDefault,
    val color: rgb.Color = DefaultPrimaryColor,
    val fillColor: rgb.Color = DefaultSecondaryColor)
    extends VectorGraphic {

  /** Boundary of this [[Arc]]. */
  // TODO: Calculate boundary so that it reflects the current transformation!!!!
  override
  val boundary: Bounds = Bounds(upperLeftCorner, lowerRightCorner)

  /** Dimensions of this [[Arc]]. */
  override
  val dimensions: Dims = boundary.dimensions

  /** Position of this [[Arc]]. */
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
  @inline
  override
  def isArc: Boolean = true

  /**
   *
   *
   * @param newUpperLeftCorner
   * @param newLowerRightCorner
   * @param newStartAngleInDegrees
   * @param newArcAngleInDegrees
   * @param newHasBorder
   * @param newHasFilling
   * @param newColor
   * @param newFillColor
   *
   * @return
   */
  @inline
  def copy(
      newUpperLeftCorner: Pos = upperLeftCorner,
      newLowerRightCorner: Pos = lowerRightCorner,
      newStartAngleInDegrees: Double = startAngleInDegrees,
      newArcAngleInDegrees: Double = arcAngleInDegrees,
      newHasBorder: Boolean = hasBorder,
      newHasFilling: Boolean = hasFilling,
      newColor: rgb.Color = color,
      newFillColor: rgb.Color = fillColor): Arc = {

    internalCopy(
      newUpperLeftCorner,
      newLowerRightCorner,
      newStartAngleInDegrees,
      newArcAngleInDegrees,
      newHasBorder = newHasBorder,
      newHasFilling = newHasFilling,
      newColor = newColor,
      newFillColor = newFillColor)
  }

  /**
   *
   *
   * @param newUpperLeftCorner
   * @param newLowerRightCorner
   * @param newStartAngleInDegrees
   * @param newArcAngleInDegrees
   * @param newRotationAngleInDegrees
   * @param newHorizontalScalingFactor
   * @param newVerticalScalingFactor
   * @param newHorizontalShearingFactor
   * @param newVerticalShearingFactor
   * @param newTransformation
   * @param newHasBorder
   * @param newHasFilling
   * @param newColor
   * @param newFillColor
   *
   * @return
   */
  @inline
  private
  def internalCopy(
      newUpperLeftCorner: Pos = upperLeftCorner,
      newLowerRightCorner: Pos = lowerRightCorner,
      newStartAngleInDegrees: Double = startAngleInDegrees,
      newArcAngleInDegrees: Double = arcAngleInDegrees,
      newRotationAngleInDegrees: Double = currentRotationAngleInDegrees,
      newHorizontalScalingFactor: Double = currentHorizontalScalingFactor,
      newVerticalScalingFactor: Double = currentVerticalScalingFactor,
      newHorizontalShearingFactor: Double = currentHorizontalShearingFactor,
      newVerticalShearingFactor: Double = currentVerticalShearingFactor,
      newTransformation: AffineTransformation = currentTransformation,
      newHasBorder: Boolean = hasBorder,
      newHasFilling: Boolean = hasFilling,
      newColor: rgb.Color = color,
      newFillColor: rgb.Color = fillColor): Arc = {

    new Arc(
      identity,
      newUpperLeftCorner, newLowerRightCorner,
      newStartAngleInDegrees, newArcAngleInDegrees,
      newRotationAngleInDegrees,
      newHorizontalScalingFactor, newVerticalScalingFactor,
      newHorizontalShearingFactor, newVerticalShearingFactor,
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
  @inline
  override
  def moveUpperLeftCornerTo(coordinatesInPixels: Seq[Double]): PictureElement = {
    require(
      coordinatesInPixels.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions coordinates must be given (found: ${coordinatesInPixels.length})")

    moveBy(
      coordinatesInPixels.head - boundary.upperLeftMarker.xInPixels,
      coordinatesInPixels.tail.head - boundary.upperLeftMarker.yInPixels)
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
  def moveUpperLeftCornerTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): PictureElement = {

    moveBy(
      xCoordinateInPixels - boundary.upperLeftMarker.xInPixels,
      yCoordinateInPixels - boundary.upperLeftMarker.yInPixels)
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
   *
   *
   * @param offsetsInPixels
   *
   * @return
   */
  @inline
  def moveBy(offsetsInPixels: Seq[Double]): Arc = {
    val newUL = upperLeftCorner.moveBy(offsetsInPixels)
    val newLR = lowerRightCorner.moveBy(offsetsInPixels)
    val newTx = currentTransformation.translate(
      offsetsInPixels.head,
      offsetsInPixels.tail.head)

    internalCopy(
      newUpperLeftCorner = newUL,
      newLowerRightCorner = newLR,
      newTransformation = newTx)
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
  override
  def moveBy(
      xOffsetInPixels: Double,
      yOffsetInPixels: Double): PictureElement = {

    val newUL = upperLeftCorner.moveBy(xOffsetInPixels, yOffsetInPixels)
    val newLR = lowerRightCorner.moveBy(xOffsetInPixels, yOffsetInPixels)
    val newTx = currentTransformation.translate(xOffsetInPixels, yOffsetInPixels)

    internalCopy(
      newUpperLeftCorner = newUL,
      newLowerRightCorner = newLR,
      newTransformation = newTx)
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
      heightFactor: Double): Arc = {

    val newWidthFactor = widthFactor * currentHorizontalScalingFactor
    val newHeightFactor = heightFactor * currentVerticalScalingFactor
    val newTransformation =
      currentTransformation.scaleRelativeToPoint(
        newWidthFactor, newHeightFactor, position)

    internalCopy(
      newHorizontalScalingFactor = newWidthFactor,
      newVerticalScalingFactor = newHeightFactor,
      newTransformation = newTransformation)
  }

  /**
   * Rotates this object around origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCWAroundOrigo: Arc = {
    val newRotationAngle = currentRotationAngleInDegrees + Angle.RightAngleInDegrees
    val newTransformation = currentTransformation.rotate90DegsCWAroundOrigo

    internalCopy(
      newRotationAngleInDegrees = newRotationAngle,
      newTransformation = newTransformation)
  }

  /**
   * Rotates this object around its center by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW: Arc = rotateBy90DegsCW(position)

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW(centerOfRotation: Pos): Arc = {
    val newRotationAngle = currentRotationAngleInDegrees + Angle.RightAngleInDegrees
    val newTransformation = currentTransformation.rotate90DegsCWAroundPoint(centerOfRotation)

    internalCopy(
      newRotationAngleInDegrees = newRotationAngle,
      newTransformation = newTransformation)
  }

  /**
   * Rotates this object around origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCWAroundOrigo: Arc = {
    val newRotationAngle = currentRotationAngleInDegrees - Angle.RightAngleInDegrees
    val newTransformation = currentTransformation.rotate90DegsCCWAroundOrigo

    internalCopy(
      newRotationAngleInDegrees = newRotationAngle,
      newTransformation = newTransformation)
  }

  /**
   * Rotates this object around the its center by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW: Arc = rotateBy90DegsCCW(position)

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW(centerOfRotation: Pos): Arc = {
    val newRotationAngle = currentRotationAngleInDegrees - Angle.RightAngleInDegrees
    val newTransformation = currentTransformation.rotate90DegsCCWAroundPoint(centerOfRotation)

    internalCopy(
      newRotationAngleInDegrees = newRotationAngle,
      newTransformation = newTransformation)
  }

  /**
   * Rotates this object around origo (0,0) by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180DegsAroundOrigo: Arc = {
    val newRotationAngle = currentRotationAngleInDegrees + Angle.StraightAngleInDegrees
    val newTransformation = currentTransformation.rotate180DegsAroundOrigo

    internalCopy(
      newRotationAngleInDegrees = newRotationAngle,
      newTransformation = newTransformation)
  }

  /**
   * Rotates this object around its center by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs: Arc = rotateBy180Degs(position)

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs(centerOfRotation: Pos): Arc = {
    val newRotationAngle = currentRotationAngleInDegrees + Angle.StraightAngleInDegrees
    val newTransformation = currentTransformation.rotate180DegsAroundPoint(centerOfRotation)

    internalCopy(
      newRotationAngleInDegrees = newRotationAngle,
      newTransformation = newTransformation)
  }

  /**
   * Rotates this object around its center by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  @inline
  override
  def rotateByAroundOrigo(angle: Angle): Arc = rotateByAroundOrigo(angle)

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  override
  def rotateByAroundOrigo(angleInDegrees: Double): Arc = {
    val newRotationAngle = currentRotationAngleInDegrees - angleInDegrees
    val newTransformation =
      currentTransformation.rotateAroundOrigo(Angle(-angleInDegrees))

    internalCopy(
      newRotationAngleInDegrees = newRotationAngle,
      newTransformation = newTransformation)
  }

  /**
   * Rotates this object around its center by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  @inline
  override
  def rotateBy(angle: Angle): Arc = rotateBy(angle)

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
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
  @inline
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
  @inline
  override
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): Arc = {

    val newRotationAngle = currentRotationAngleInDegrees + angleInDegrees
    val newTransformation =
      currentTransformation.rotateAroundPoint(Angle(angleInDegrees), centerOfRotation)

    internalCopy(
      newRotationAngleInDegrees = newRotationAngle,
      newTransformation = newTransformation)
  }

}
