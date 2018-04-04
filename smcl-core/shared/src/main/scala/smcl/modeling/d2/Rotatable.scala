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

package smcl.modeling.d2


import smcl.modeling.Angle




/**
 *
 *
 * @tparam ReturnType
 *
 * @author Aleksi Lukkarinen
 */
trait Rotatable[ReturnType] {

  // 90 Degs Clockwise
  // -------------------------------------------------------------------------------------------- \\

  /**
   * Rotates this object around its center by 90 degrees clockwise.
   *
   * @return
   */
  def rotateBy90DegsCW: ReturnType

  /**
   * Rotates this object around origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  def rotateBy90DegsCWAroundOrigo: ReturnType

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  def rotateBy90DegsCW(centerOfRotation: Pos): ReturnType


  // 90 Degs Counterclockwise
  // -------------------------------------------------------------------------------------------- \\

  /**
   * Rotates this object around its center by 90 degrees counterclockwise.
   *
   * @return
   */
  def rotateBy90DegsCCW: ReturnType

  /**
   * Rotates this object around origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  def rotateBy90DegsCCWAroundOrigo: ReturnType

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  def rotateBy90DegsCCW(centerOfRotation: Pos): ReturnType


  // 180 Degs
  // -------------------------------------------------------------------------------------------- \\

  /**
   * Rotates this object around its center by 180 degrees.
   *
   * @return
   */
  def rotateBy180Degs: ReturnType

  /**
   * Rotates this object around origo (0,0) by 180 degrees.
   *
   * @return
   */
  def rotateBy180DegsAroundOrigo: ReturnType

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  def rotateBy180Degs(centerOfRotation: Pos): ReturnType


  // Free Angle
  // -------------------------------------------------------------------------------------------- \\

  /**
   * Rotates this object around its center by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  def rotateBy(angle: Angle): ReturnType = rotateBy(angle.inDegrees)

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  def rotateBy(angleInDegrees: Double): ReturnType

  /**
   * Rotates this object around its center by the specified angle.
   *
   * @param angle
   *
   * @return
   */
  def rotateByAroundOrigo(angle: Angle): ReturnType = rotateByAroundOrigo(angle.inDegrees)

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  def rotateByAroundOrigo(angleInDegrees: Double): ReturnType

  /**
   * Rotates this object around a given point by the specified angle.
   *
   * @param angle
   * @param centerOfRotation
   *
   * @return
   */
  def rotateBy(
      angle: Angle,
      centerOfRotation: Pos): ReturnType = {

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
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): ReturnType

}
