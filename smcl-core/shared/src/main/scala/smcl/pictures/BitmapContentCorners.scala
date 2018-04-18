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


import smcl.infrastructure.MathUtils
import smcl.modeling.d2.{Bounds, Pos}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object BitmapContentCorners {

  /** */
  val NotDefined: BitmapContentCorners =
    createInstance(Pos.Origo, Pos.Origo, Pos.Origo, Pos.Origo, isDefined = false)

  /**
   *
   *
   * @param bounds
   */
  def apply(bounds: Bounds): BitmapContentCorners = {
    val ulCorner = bounds.upperLeftCorner
    val urCorner = Pos(bounds.lowerRightCorner.xInPixels, bounds.upperLeftCorner.yInPixels)
    val lrCorner = bounds.lowerRightCorner
    val llCorner = Pos(bounds.upperLeftCorner.xInPixels, bounds.lowerRightCorner.yInPixels)

    apply(ulCorner, urCorner, lrCorner, llCorner)
  }

  /**
   *
   *
   * @param upperLeftCorner
   * @param lowerRightCorner
   */
  def apply(
      upperLeftCorner: Pos,
      lowerRightCorner: Pos): BitmapContentCorners = {

    apply(
      upperLeftCorner,
      Pos(lowerRightCorner.xInPixels, upperLeftCorner.yInPixels),
      lowerRightCorner,
      Pos(upperLeftCorner.xInPixels, lowerRightCorner.yInPixels))
  }

  /**
   *
   *
   * @param upperLeftCorner
   * @param upperRightCorner
   * @param lowerRightCorner
   * @param lowerLeftCorner
   */
  def apply(
      upperLeftCorner: Pos,
      upperRightCorner: Pos,
      lowerRightCorner: Pos,
      lowerLeftCorner: Pos): BitmapContentCorners = {

    createInstance(
      upperLeftCorner,
      upperRightCorner,
      lowerRightCorner,
      lowerLeftCorner,
      isDefined = true)
  }

  /**
   *
   *
   * @param upperLeftCorner
   * @param upperRightCorner
   * @param lowerRightCorner
   * @param lowerLeftCorner
   */
  private
  def createInstance(
      upperLeftCorner: Pos,
      upperRightCorner: Pos,
      lowerRightCorner: Pos,
      lowerLeftCorner: Pos,
      isDefined: Boolean): BitmapContentCorners = {

    new BitmapContentCorners(
      upperLeftCorner,
      upperRightCorner,
      lowerRightCorner,
      lowerLeftCorner,
      isDefined)
  }

}




/**
 *
 *
 * @param upperLeftCorner
 * @param upperRightCorner
 * @param lowerRightCorner
 * @param lowerLeftCorner
 * @param isDefined
 *
 * @author Aleksi Lukkarinen
 */
class BitmapContentCorners private(
    val upperLeftCorner: Pos,
    val upperRightCorner: Pos,
    val lowerRightCorner: Pos,
    val lowerLeftCorner: Pos,
    val isDefined: Boolean) {

  /** */
  lazy val corners: Seq[Pos] =
    Seq(upperLeftCorner, upperRightCorner, lowerRightCorner, lowerLeftCorner)

  /** */
  val isUndefined: Boolean = !isDefined

  /**
   *
   *
   * @param offsetsInPixels
   *
   * @return
   */
  @inline
  final
  def moveBy(offsetsInPixels: Seq[Double]): BitmapContentCorners = {
    if (isUndefined)
      return this

    internalCopy(
      newUpperLeftCorner = upperLeftCorner.moveBy(offsetsInPixels),
      newUpperRightCorner = upperRightCorner.moveBy(offsetsInPixels),
      newLowerRightCorner = lowerRightCorner.moveBy(offsetsInPixels),
      newLowerLeftCorner = lowerLeftCorner.moveBy(offsetsInPixels))
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
  final
  def moveBy(
      xOffsetInPixels: Double,
      yOffsetInPixels: Double): BitmapContentCorners = {

    if (isUndefined)
      return this

    internalCopy(
      newUpperLeftCorner = upperLeftCorner.moveBy(xOffsetInPixels, yOffsetInPixels),
      newUpperRightCorner = upperRightCorner.moveBy(xOffsetInPixels, yOffsetInPixels),
      newLowerRightCorner = lowerRightCorner.moveBy(xOffsetInPixels, yOffsetInPixels),
      newLowerLeftCorner = lowerLeftCorner.moveBy(xOffsetInPixels, yOffsetInPixels))
  }

  /**
   * Creates a copy of these bitmap content corners with given arguments.
   *
   * This is an unsafe method, as it can be used to create [[BitmapContentCorners]]
   * instances, whose internal state is incoherent. As such, it is not for public use.
   *
   * @param newUpperLeftCorner
   * @param newUpperRightCorner
   * @param newLowerRightCorner
   * @param newLowerLeftCorner
   *
   * @return
   */
  @inline
  private final
  def internalCopy(
      newUpperLeftCorner: Pos = upperLeftCorner,
      newUpperRightCorner: Pos = upperRightCorner,
      newLowerRightCorner: Pos = lowerRightCorner,
      newLowerLeftCorner: Pos = lowerLeftCorner): BitmapContentCorners = {

    BitmapContentCorners(
      newUpperLeftCorner,
      newUpperRightCorner,
      newLowerRightCorner,
      newLowerLeftCorner)
  }

  /**
   *
   *
   * @return
   */
  def rotateBy90DegsCWAroundOrigo: BitmapContentCorners = {
    if (isUndefined)
      return this

    val newUR = upperLeftCorner.rotateBy90DegsCWAroundOrigo
    val newLR = upperRightCorner.rotateBy90DegsCWAroundOrigo
    val newLL = lowerRightCorner.rotateBy90DegsCWAroundOrigo
    val newUL = lowerLeftCorner.rotateBy90DegsCWAroundOrigo

    internalCopy(newUL, newUR, newLR, newLL)
  }

  /**
   *
   *
   * @param centerOfRotation
   *
   * @return
   */
  def rotateBy90DegsCW(centerOfRotation: Pos): BitmapContentCorners = {
    if (isUndefined)
      return this

    val newUR = upperLeftCorner.rotateBy90DegsCW(centerOfRotation)
    val newLR = upperRightCorner.rotateBy90DegsCW(centerOfRotation)
    val newLL = lowerRightCorner.rotateBy90DegsCW(centerOfRotation)
    val newUL = lowerLeftCorner.rotateBy90DegsCW(centerOfRotation)

    internalCopy(newUL, newUR, newLR, newLL)
  }

  /**
   *
   *
   * @return
   */
  def rotateBy90DegsCCWAroundOrigo: BitmapContentCorners = {
    if (isUndefined)
      return this

    val newLL = upperLeftCorner.rotateBy90DegsCCWAroundOrigo
    val newUL = upperRightCorner.rotateBy90DegsCCWAroundOrigo
    val newUR = lowerRightCorner.rotateBy90DegsCCWAroundOrigo
    val newLR = lowerLeftCorner.rotateBy90DegsCCWAroundOrigo

    internalCopy(newUL, newUR, newLR, newLL)
  }

  /**
   *
   *
   * @param centerOfRotation
   *
   * @return
   */
  def rotateBy90DegsCCW(centerOfRotation: Pos): BitmapContentCorners = {
    if (isUndefined)
      return this

    val newLL = upperLeftCorner.rotateBy90DegsCCW(centerOfRotation)
    val newUL = upperRightCorner.rotateBy90DegsCCW(centerOfRotation)
    val newUR = lowerRightCorner.rotateBy90DegsCCW(centerOfRotation)
    val newLR = lowerLeftCorner.rotateBy90DegsCCW(centerOfRotation)

    internalCopy(newUL, newUR, newLR, newLL)
  }

  /**
   *
   *
   * @return
   */
  def rotateBy180DegsAroundOrigo: BitmapContentCorners = {
    if (isUndefined)
      return this

    val newLR = upperLeftCorner.rotateBy180DegsAroundOrigo
    val newLL = upperRightCorner.rotateBy180DegsAroundOrigo
    val newUL = lowerRightCorner.rotateBy180DegsAroundOrigo
    val newUR = lowerLeftCorner.rotateBy180DegsAroundOrigo

    internalCopy(newUL, newUR, newLR, newLL)
  }

  /**
   *
   *
   * @param centerOfRotation
   *
   * @return
   */
  def rotateBy180Degs(centerOfRotation: Pos): BitmapContentCorners = {
    if (isUndefined)
      return this

    val newLR = upperLeftCorner.rotateBy180Degs(centerOfRotation)
    val newLL = upperRightCorner.rotateBy180Degs(centerOfRotation)
    val newUL = lowerRightCorner.rotateBy180Degs(centerOfRotation)
    val newUR = lowerLeftCorner.rotateBy180Degs(centerOfRotation)

    internalCopy(newUL, newUR, newLR, newLL)
  }

  /**
   *
   *
   * @param angleInDegrees
   *
   * @return
   */
  def rotateByAroundOrigo(angleInDegrees: Double): BitmapContentCorners = {
    if (isUndefined)
      return this

    normalizeCornersForFreeRotation(
      rotationAngleInDegrees = angleInDegrees,
      rotatedUpperLeftCorner = upperLeftCorner.rotateByAroundOrigo(angleInDegrees),
      rotatedUpperRightCorner = upperRightCorner.rotateByAroundOrigo(angleInDegrees),
      rotatedLowerRightCorner = lowerRightCorner.rotateByAroundOrigo(angleInDegrees),
      rotatedLowerLeftCorner = lowerLeftCorner.rotateByAroundOrigo(angleInDegrees))
  }

  /**
   *
   *
   * @param angleInDegrees
   * @param centerOfRotation
   *
   * @return
   */
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): BitmapContentCorners = {

    if (isUndefined)
      return this

    normalizeCornersForFreeRotation(
      rotationAngleInDegrees = angleInDegrees,
      rotatedUpperLeftCorner = upperLeftCorner.rotateBy(angleInDegrees, centerOfRotation),
      rotatedUpperRightCorner = upperRightCorner.rotateBy(angleInDegrees, centerOfRotation),
      rotatedLowerRightCorner = lowerRightCorner.rotateBy(angleInDegrees, centerOfRotation),
      rotatedLowerLeftCorner = lowerLeftCorner.rotateBy(angleInDegrees, centerOfRotation))
  }

  /**
   * Scales this object in relation to its center.
   *
   * @param widthFactor
   * @param heightFactor
   *
   * @return
   */
  def scaleBy(
      widthFactor: Double,
      heightFactor: Double): BitmapContentCorners = {

    /*
    if (isUndefined)
      return this

    val newLR = upperLeftCorner.rotateBy180Degs(centerOfRotation)
    val newLL = upperRightCorner.rotateBy180Degs(centerOfRotation)
    val newUL = lowerRightCorner.rotateBy180Degs(centerOfRotation)
    val newUR = lowerLeftCorner.rotateBy180Degs(centerOfRotation)

    internalCopy(newUL, newUR, newLR, newLL)
    */
    this
  }

  /**
   *
   *
   * @return
   */
  @inline
  private final
  def normalizeCornersForFreeRotation(
      rotationAngleInDegrees: Double,
      rotatedUpperLeftCorner: Pos,
      rotatedUpperRightCorner: Pos,
      rotatedLowerRightCorner: Pos,
      rotatedLowerLeftCorner: Pos): BitmapContentCorners = {

    val oldUL = rotatedUpperLeftCorner
    val oldUR = rotatedUpperRightCorner
    val oldLR = rotatedLowerRightCorner
    val oldLL = rotatedLowerLeftCorner

    val normalizedAngle =
      MathUtils.normalizeToPosDegs(rotationAngleInDegrees)

    val (newUL, newUR, newLR, newLL) = {
      if (normalizedAngle >= 45 && normalizedAngle < 135) {
        (oldLL, oldUL, oldUR, oldLR)
      }
      else if (normalizedAngle < 225) {
        (oldLR, oldLL, oldUL, oldUR)
      }
      else if (normalizedAngle < 315) {
        (oldUR, oldLR, oldLL, oldUL)
      }
      else { // >= 315 || < 45
        (oldUL, oldUR, oldLR, oldLL)
      }
    }

    internalCopy(newUL, newUR, newLR, newLL)
  }

}
