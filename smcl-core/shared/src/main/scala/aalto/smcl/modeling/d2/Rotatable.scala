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

package aalto.smcl.modeling.d2


/**
 *
 *
 * @tparam ReturnType
 *
 * @author Aleksi Lukkarinen
 */
trait Rotatable[ReturnType] {

  /**
   * Rotates this object around the origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  def rotateBy90DegsCW: ReturnType

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  def rotateBy90DegsCW(centerOfRotation: Pos): ReturnType

  /**
   * Rotates this object around the origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  def rotateBy90DegsCCW: ReturnType

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  def rotateBy90DegsCCW(centerOfRotation: Pos): ReturnType

  /**
   * Rotates this object around the origo (0,0) by 180 degrees.
   *
   * @return
   */
  @inline
  def rotateBy180Degs: ReturnType

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  def rotateBy180Degs(centerOfRotation: Pos): ReturnType

  /**
   * Rotates this object around the origo (0,0) by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  def rotateBy(angleInDegrees: Double): ReturnType

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
