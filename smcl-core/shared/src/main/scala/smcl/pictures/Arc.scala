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
import smcl.infrastructure.{DrawingSurfaceAdapter, Identity}
import smcl.modeling.d2.{Bounds, Dims, Pos}
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
   * @param startAngle
   * @param arcAngle
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
      startAngle: Double = Angle.Zero.inDegrees,
      arcAngle: Double = Angle.FullAngleInDegrees,
      hasBorder: Boolean = ShapesHaveBordersByDefault,
      hasFilling: Boolean = ShapesHaveFillingsByDefault,
      color: rgb.Color = DefaultPrimaryColor,
      fillColor: rgb.Color = DefaultSecondaryColor): VectorGraphic = {

    val identity = Identity()

    new Arc(
      identity,
      upperLeftCorner, lowerRightCorner,
      startAngle, arcAngle,
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
 * @param startAngle
 * @param arcAngle
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
    val startAngle: Double,
    val arcAngle: Double,
    val hasBorder: Boolean = ShapesHaveBordersByDefault,
    val hasFilling: Boolean = ShapesHaveFillingsByDefault,
    val color: rgb.Color = DefaultPrimaryColor,
    val fillColor: rgb.Color = DefaultSecondaryColor)
    extends VectorGraphic {

  /** Dimensions of this [[Arc]]. */
  override
  val dimensions: Dims = ???

  /** */
  override
  val boundary: Bounds = ???

  /** Position of this [[Arc]]. */
  override
  val position: Pos = Pos(
    internalCenter.width.inPixels,
    internalCenter.height.inPixels)

  /** Tells if this [[Arc]] can be rendered on a bitmap. */
  override
  val isRenderable: Boolean = true

  /**
   * Renders this [[Arc]] on a drawing surface.
   *
   * @param drawingSurface
   * @param offsetsToOrigo
   */
  override
  def renderOn(
      drawingSurface: DrawingSurfaceAdapter,
      offsetsToOrigo: Dims): Unit = ???

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  @inline
  def moveBy(offsets: Double*): Arc = {
    copy(

    )
  }

  /**
   *
   *
   * @param newUpperLeftCorner
   * @param newLowerRightCorner
   * @param newStartAngle
   * @param newArcAngle
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
      newStartAngle: Double = startAngle,
      newArcAngle: Double = arcAngle,
      newHasBorder: Boolean = hasBorder,
      newHasFilling: Boolean = hasFilling,
      newColor: rgb.Color = color,
      newFillColor: rgb.Color = fillColor): Arc = {

    new Arc(
      identity,
      newUpperLeftCorner, newLowerRightCorner,
      newStartAngle, newArcAngle,
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
  override
  def scaleBy(widthFactor: Double, heightFactor: Double): Arc = {
    this  // if (width != height) ==> produces an ellipse
  }

  /**
   * Rotates this object around the origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCW: ImageElement = ???

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy90DegsCW(centerOfRotation: Pos): ImageElement = ???

  /**
   * Rotates this object around the origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCCW: ImageElement = ???

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy90DegsCCW(centerOfRotation: Pos): ImageElement = ???

  /**
   * Rotates this object around the origo (0,0) by 180 degrees.
   *
   * @return
   */
  override
  def rotateBy180Degs: ImageElement = ???

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy180Degs(centerOfRotation: Pos): ImageElement = ???

  /**
   * Rotates this object around the origo (0,0) by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  override
  def rotateBy(angleInDegrees: Double): ImageElement = ???

  /**
   * Rotates this object around a given point by the specified number of degrees.
   *
   * @param angleInDegrees
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy(angleInDegrees: Double, centerOfRotation: Pos): ImageElement = ???

  /**
   * Transforms this object using the specified affine transformation.
   *
   * @param t
   *
   * @return
   */
  override
  def transformBy(t: AffineTransformation): ImageElement = ???

}
