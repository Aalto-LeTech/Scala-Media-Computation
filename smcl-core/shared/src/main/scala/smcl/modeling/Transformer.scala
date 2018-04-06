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

package smcl.modeling


import smcl.infrastructure.MathUtils




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Transformer {

  /**
   * Rotates a [[Pos]] instance around the origo (0,0) by 90 degrees clockwise.
   *
   * @param position position to be rotated
   *
   * @return a rotated [[Pos]] instance
   */
  @inline
  final
  def rotateBy90DegsCW(position: Pos): Pos =
    Pos(-position.yInPixels, position.xInPixels)

  /**
   * Rotates a sequence of [[Pos]] instances around the origo (0,0) by 90 degrees clockwise.
   *
   * @param positions positions to be rotated
   *
   * @return a sequence of rotated [[Pos]] instances
   */
  @inline
  final
  def rotateBy90DegsCW(positions: Seq[Pos]): Seq[Pos] = {
    positions map rotateBy90DegsCW
  }

  /**
   * Rotates a [[Pos]] instance around a given point by 90 degrees clockwise.
   *
   * @param position         position to be rotated
   * @param centerOfRotation center point of the rotation
   *
   * @return a rotated [[Pos]] instance
   */
  @inline
  final
  def rotateBy90DegsCW(
      position: Pos,
      centerOfRotation: Pos): Pos = {

    val newX = -position.yInPixels + centerOfRotation.xInPixels + centerOfRotation.yInPixels
    val newY = position.xInPixels - centerOfRotation.xInPixels + centerOfRotation.yInPixels

    Pos(newX, newY)
  }

  /**
   * Rotates a sequence of [[Pos]] instances around a given point by 90 degrees clockwise.
   *
   * @param positions        positions to be rotated
   * @param centerOfRotation center point of the rotation
   *
   * @return a sequence of rotated [[Pos]] instances
   */
  @inline
  final
  def rotateBy90DegsCW(
      positions: Seq[Pos],
      centerOfRotation: Pos): Seq[Pos] = {

    positions map {rotateBy90DegsCW(_, centerOfRotation)}
  }

  /**
   * Rotates a [[Pos]] instance around the origo (0,0) by 90 degrees counterclockwise.
   *
   * @param position position to be rotated
   *
   * @return a rotated [[Pos]] instance
   */
  @inline
  final
  def rotateBy90DegsCCW(position: Pos): Pos = {
    Pos(position.yInPixels, -position.xInPixels)
  }

  /**
   * Rotates a sequence of [[Pos]] instances around the origo (0,0) by 90 degrees counterclockwise.
   *
   * @param positions positions to be rotated
   *
   * @return a sequence of rotated [[Pos]] instances
   */
  @inline
  final
  def rotateBy90DegsCCW(positions: Seq[Pos]): Seq[Pos] = {
    positions map rotateBy90DegsCCW
  }

  /**
   * Rotates a [[Pos]] instance around a given point by 90 degrees counterclockwise.
   *
   * @param position         position to be rotated
   * @param centerOfRotation center point of the rotation
   *
   * @return a rotated [[Pos]] instance
   */
  @inline
  final
  def rotateBy90DegsCCW(
      position: Pos,
      centerOfRotation: Pos): Pos = {

    val newX = position.yInPixels - centerOfRotation.yInPixels + centerOfRotation.xInPixels
    val newY = -position.xInPixels + centerOfRotation.xInPixels + centerOfRotation.yInPixels

    Pos(newX, newY)
  }

  /**
   * Rotates a sequence of [[Pos]] instances around a given point by 90 degrees counterclockwise.
   *
   * @param positions        positions to be rotated
   * @param centerOfRotation center point of the rotation
   *
   * @return a sequence of rotated [[Pos]] instances
   */
  @inline
  final
  def rotateBy90DegsCCW(
      positions: Seq[Pos],
      centerOfRotation: Pos): Seq[Pos] = {

    positions map {rotateBy90DegsCCW(_, centerOfRotation)}
  }

  /**
   * Rotates a [[Pos]] instance around the origo (0,0) by 180 degrees.
   *
   * @param position position to be rotated
   *
   * @return a rotated [[Pos]] instance
   */
  @inline
  final
  def rotateBy180Degs(position: Pos): Pos = {
    Pos(-position.xInPixels, -position.yInPixels)
  }

  /**
   * Rotates a sequence of [[Pos]] instances around the origo (0,0) by 180 degrees.
   *
   * @param positions positions to be rotated
   *
   * @return a sequence of rotated [[Pos]] instances
   */
  @inline
  final
  def rotateBy180Degs(positions: Seq[Pos]): Seq[Pos] = {
    positions map rotateBy180Degs
  }

  /**
   * Rotates a [[Pos]] instance around a given point by 180 degrees.
   *
   * @param position         position to be rotated
   * @param centerOfRotation center point of the rotation
   *
   * @return a rotated [[Pos]] instance
   */
  @inline
  final
  def rotateBy180Degs(
      position: Pos,
      centerOfRotation: Pos): Pos = {

    val newX = 2 * centerOfRotation.xInPixels - position.xInPixels
    val newY = 2 * centerOfRotation.yInPixels - position.yInPixels

    Pos(newX, newY)
  }

  /**
   * Rotates a sequence of [[Pos]] instances around a given point by 180 degrees.
   *
   * @param positions        positions to be rotated
   * @param centerOfRotation center point of the rotation
   *
   * @return a sequence of rotated [[Pos]] instances
   */
  @inline
  final
  def rotateBy180Degs(
      positions: Seq[Pos],
      centerOfRotation: Pos): Seq[Pos] = {

    positions map {rotateBy180Degs(_, centerOfRotation)}
  }

  /**
   * Rotates a [[Pos]] instance around the origo by a given angle.
   *
   * @param position       position to be rotated
   * @param angleInDegrees the angle (in degrees), the amount of which to rotate
   *
   * @return a rotated [[Pos]] instance
   */
  @inline
  final
  def rotate(
      position: Pos,
      angleInDegrees: Double): Pos = {

    val (sin, cos) =
      MathUtils.sinCosRads(math.toRadians(angleInDegrees))

    rotate(position, sin, cos)
  }

  /**
   * Rotates a sequence of [[Pos]] instances around the origo by a given angle.
   *
   * @param positions      positions to be rotated
   * @param angleInDegrees the angle (in degrees), the amount of which to rotate
   *
   * @return a sequence of rotated [[Pos]] instances
   */
  @inline
  final
  def rotate(
      positions: Seq[Pos],
      angleInDegrees: Double): Seq[Pos] = {

    val (sin, cos) =
      MathUtils.sinCosRads(math.toRadians(angleInDegrees))

    positions map {rotate(_, sin, cos)}
  }

  /**
   * Rotates a [[Pos]] instance around the origo by an angle, whose sin and cos are given.
   *
   * @param position position to be rotated
   * @param sin      sin of the angle, the amount of which to rotate
   * @param cos      cos of the angle, the amount of which to rotate
   *
   * @return a rotated [[Pos]] instance
   */
  @inline
  private final
  def rotate(
      position: Pos,
      sin: Double,
      cos: Double): Pos = {

    val xNew = cos * position.xInPixels + sin * position.yInPixels
    val yNew = cos * position.yInPixels - sin * position.xInPixels

    Pos(xNew, yNew)
  }

  /**
   * Rotates a [[Pos]] instance around a given point by a given angle.
   *
   * @param position         position to be rotated
   * @param angleInDegrees   the angle (in degrees), the amount of which to rotate
   * @param centerOfRotation center point of the rotation
   *
   * @return a rotated [[Pos]] instance
   */
  @inline
  final
  def rotate(
      position: Pos,
      angleInDegrees: Double,
      centerOfRotation: Pos): Pos = {

    val (sin, cos) =
      MathUtils.sinCosRads(math.toRadians(angleInDegrees))

    rotate(position, centerOfRotation, sin, cos)
  }

  /**
   * Rotates a sequence of [[Pos]] instances around a given point by a given angle.
   *
   * @param positions        positions to be rotated
   * @param angleInDegrees   the angle (in degrees), the amount of which to rotate
   * @param centerOfRotation center point of the rotation
   *
   * @return a sequence of rotated [[Pos]] instances
   */
  @inline
  final
  def rotate(
      positions: Seq[Pos],
      angleInDegrees: Double,
      centerOfRotation: Pos): Seq[Pos] = {

    val (sin, cos) =
      MathUtils.sinCosRads(math.toRadians(angleInDegrees))

    positions map {rotate(_, centerOfRotation, sin, cos)}
  }

  /**
   * Rotates a [[Pos]] instance around a given point by an angle, whose sin and cos are given.
   *
   * @param position         position to be rotated
   * @param centerOfRotation center point of the rotation
   * @param sin              sin of the angle, the amount of which to rotate
   * @param cos              cos of the angle, the amount of which to rotate
   *
   * @return a rotated [[Pos]] instance
   */
  @inline
  private final
  def rotate(
      position: Pos,
      centerOfRotation: Pos,
      sin: Double,
      cos: Double): Pos = {

    val xNorm = position.xInPixels - centerOfRotation.xInPixels
    val yNorm = position.yInPixels - centerOfRotation.yInPixels

    val xNew = cos * xNorm - sin * yNorm + centerOfRotation.xInPixels
    val yNew = cos * yNorm + sin * xNorm + centerOfRotation.yInPixels

    Pos(xNew, yNew)
  }

}
