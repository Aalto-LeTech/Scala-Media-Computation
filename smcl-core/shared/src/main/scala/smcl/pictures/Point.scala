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


import scala.language.implicitConversions

import smcl.colors.rgb
import smcl.infrastructure.{FlatMap, Identity}
import smcl.modeling.d2.{Bounds, CoordinateTuple, Pos}
import smcl.modeling.{Angle, Len}




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

  /**
   *
   *
   * @param p
   *
   * @return
   */
  implicit def asPair(p: Point): (Double, Double) = {
    val pos = p.position

    (pos.xInPixels, pos.yInPixels)
  }

  /**
   *
   *
   * @param p
   *
   * @return
   */
  implicit def asFlooredIntPair(p: Point): (Int, Int) = {
    val pos = p.position

    (pos.xInPixels.floor.toInt, pos.yInPixels.floor.toInt)
  }

}




/**
 *
 *
 * @param identity
 * @param position
 * @param color
 *
 * @author Aleksi Lukkarinen
 */
class Point private(
    override val identity: Identity,
    override val position: Pos,
    val color: rgb.Color)
    extends VectorGraphic
        with FlatMap[Point, (Pos, rgb.Color)] {

  /**
   *
   *
   * @return
   */
  def boundary: Bounds = position.boundary

  /** Tells if this [[Point]] can be rendered on a bitmap. */
  def isRenderable: Boolean = true

  /**
   *
   *
   * @return
   */
  override
  def isPoint: Boolean = true

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

    new Point(identity, newPosition, newColor)
  }

  /**
   *
   *
   * @return
   */
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
   * @param offsetsInPixels
   *
   * @return
   */
  override
  def moveBy(offsetsInPixels: Seq[Double]): Point =
    copy(newPosition = position.moveBy(offsetsInPixels))

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

    copy(newPosition = position.moveBy(xOffsetInPixels, yOffsetInPixels))
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  override
  def moveUpperLeftCornerTo(coordinatesInPixels: Seq[Double]): PictureElement =
    copy(newPosition = position.moveUpperLeftCornerTo(coordinatesInPixels))

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

    copy(newPosition = position.moveUpperLeftCornerTo(xCoordinateInPixels, yCoordinateInPixels))
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  override
  def moveCenterTo(coordinatesInPixels: Seq[Double]): PictureElement =
    copy(newPosition = position.moveCenterTo(coordinatesInPixels))

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

    copy(newPosition = position.moveCenterTo(xCoordinateInPixels, yCoordinateInPixels))
  }

  /**
   *
   *
   * @return
   */
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
  def distanceFrom(other: Point): Len = {
    position.distanceFrom(other.position)
  }

  /**
   *
   *
   * @return
   */
  def isOnHorizontalAxis: Boolean = {
    position.isOnHorizontalAxis
  }

  /**
   *
   *
   * @return
   */
  def isOnVerticalAxis: Boolean = {
    position.isOnVerticalAxis
  }

  /**
   *
   *
   * @return
   */
  def isOnFirstQuadrant: Boolean = {
    position.isOnFirstQuadrant
  }

  /**
   *
   *
   * @return
   */
  def isOnSecondQuadrant: Boolean = {
    position.isOnSecondQuadrant
  }

  /**
   *
   *
   * @return
   */
  def isOnThirdQuadrant: Boolean = {
    position.isOnThirdQuadrant
  }

  /**
   *
   *
   * @return
   */
  def isOnFourthQuadrant: Boolean = {
    position.isOnFourthQuadrant
  }

  /**
   *
   */
  override
  def display(): Point = {
    super.display()

    this
  }

  /**
   * Rotates this object around origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCWAroundOrigo: Point =
    copy(newPosition = position.rotateBy90DegsCWAroundOrigo)

  /**
   * Rotates this object around its center by 90 degrees clockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCW: Point = this

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy90DegsCW(centerOfRotation: Pos): Point =
    copy(newPosition = position.rotateBy90DegsCW(centerOfRotation))

  /**
   * Rotates this object around origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCCWAroundOrigo: Point =
    copy(newPosition = position.rotateBy90DegsCCWAroundOrigo)

  /**
   * Rotates this object around the its center by 90 degrees counterclockwise.
   *
   * @return
   */
  override
  def rotateBy90DegsCCW: Point = this

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy90DegsCCW(centerOfRotation: Pos): Point =
    copy(newPosition = position.rotateBy90DegsCCW(centerOfRotation))

  /**
   * Rotates this object around origo (0,0) by 180 degrees.
   *
   * @return
   */
  override
  def rotateBy180DegsAroundOrigo: Point =
    copy(newPosition = position.rotateBy180DegsAroundOrigo)

  /**
   * Rotates this object around its center by 180 degrees.
   *
   * @return
   */
  override
  def rotateBy180Degs: Point = this

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  override
  def rotateBy180Degs(centerOfRotation: Pos): Point =
    copy(newPosition = position.rotateBy180Degs(centerOfRotation))

  /**
   * Rotates this object around its center by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  override
  def rotateByAroundOrigo(angle: Angle): Point = rotateByAroundOrigo(angle)

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  override
  def rotateByAroundOrigo(angleInDegrees: Double): Point =
    copy(newPosition = position.rotateByAroundOrigo(angleInDegrees))

  /**
   * Rotates this object around its center by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  override
  def rotateBy(angle: Angle): Point = rotateBy(angle)

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  override
  def rotateBy(angleInDegrees: Double): Point = this

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
      centerOfRotation: Pos): Point = {

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
      centerOfRotation: Pos): Point = {

    copy(newPosition = position.rotateBy(angleInDegrees, centerOfRotation))
  }

  /**
   * Scales this object to a given width in relation to its center.
   *
   * @param targetWidth
   *
   * @return
   */
  override
  def scaleHorizontallyTo(targetWidth: Double): PictureElement = this

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
      relativityPoint: Pos): PictureElement = {

    this
  }

  /**
   * Scales this object to a given width in relation to the origo.
   *
   * @param targetWidth
   *
   * @return
   */
  override
  def scaleHorizontallyToRelativeToOrigo(targetWidth: Double): PictureElement = this

  /**
   * Scales this object to a given height in relation to its center.
   *
   * @param targetHeight
   *
   * @return
   */
  override
  def scaleVerticallyTo(targetHeight: Double): PictureElement = this

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
      relativityPoint: Pos): PictureElement = {

    this
  }

  /**
   * Scales this object to a given height in relation to the origo.
   *
   * @param targetHeight
   *
   * @return
   */
  override
  def scaleVerticallyToRelativeToOrigo(targetHeight: Double): PictureElement = this

  /**
   * Scales this object in relation to its center by
   * using a single length for both width and height.
   *
   * @param targetSideLength
   *
   * @return
   */
  override
  def scaleTo(targetSideLength: Double): PictureElement = this

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
      relativityPoint: Pos): PictureElement = {

    this
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
  def scaleToRelativeToOrigo(targetSideLength: Double): PictureElement = this

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
      targetHeight: Double): PictureElement = this

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
      relativityPoint: Pos): PictureElement = {

    this
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
      targetHeight: Double): PictureElement = {

    this
  }

  /**
   * Scales this object horizontally in relation to its center.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleHorizontallyBy(factor: Double): PictureElement = this

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
      relativityPoint: Pos): PictureElement = {

    copy(newPosition = position.scaleHorizontallyBy(factor, relativityPoint))
  }

  /**
   * Scales this object horizontally in relation to the origo.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleHorizontallyByRelativeToOrigo(factor: Double): PictureElement =
    copy(newPosition = position.scaleHorizontallyByRelativeToOrigo(factor))

  /**
   * Scales this object vertically in relation to its center.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleVerticallyBy(factor: Double): PictureElement = this

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
      relativityPoint: Pos): PictureElement = {

    copy(newPosition = position.scaleVerticallyBy(factor, relativityPoint))
  }

  /**
   * Scales this object vertically in relation to the origo.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleVerticallyByRelativeToOrigo(factor: Double): PictureElement =
    copy(newPosition = position.scaleVerticallyByRelativeToOrigo(factor))

  /**
   * Scales this object in relation to its center by using a given factor
   * for both horizontal and vertical directions.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleBy(factor: Double): PictureElement = this

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
      relativityPoint: Pos): PictureElement = {

    copy(newPosition = position.scaleBy(factor, relativityPoint))
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
  def scaleByRelativeToOrigo(factor: Double): PictureElement =
    copy(newPosition = position.scaleByRelativeToOrigo(factor))

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
      verticalFactor: Double): PictureElement = this

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
      relativityPoint: Pos): PictureElement = {

    copy(newPosition = position.scaleBy(horizontalFactor, verticalFactor, relativityPoint))
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
      verticalFactor: Double): PictureElement = {

    copy(newPosition = position.scaleByRelativeToOrigo(horizontalFactor, verticalFactor))
  }

}
