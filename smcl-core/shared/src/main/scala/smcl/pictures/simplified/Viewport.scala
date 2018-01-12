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

package smcl.pictures.simplified

import smcl.modeling.d2.simplified.{Bounds, Pos}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Viewport {

  /**
   * Creates a new [[Viewport]] instance.
   *
   * @param boundary
   *
   * @return
   */
  def apply(boundary: Bounds): Viewport = {
    new Viewport(boundary)
  }

}




/**
 *
 *
 * @param boundary
 *
 * @author Aleksi Lukkarinen
 */
class Viewport(val boundary: Bounds) {

  /**
   *
   *
   * @return
   */
  @inline
  def width: Double = boundary.width

  /**
   *
   *
   * @return
   */
  @inline
  def height: Double = boundary.height

  /**
   *
   *
   * @return
   */
  @inline
  def left: Double = boundary.height

  /**
   *
   *
   * @return
   */
  @inline
  def top: Double = boundary.top

  /**
   *
   *
   * @return
   */
  @inline
  def right: Double = boundary.right

  /**
   *
   *
   * @return
   */
  @inline
  def bottom: Double = boundary.bottom

  /**
   *
   *
   * @return
   */
  @inline
  def upperLeftCorner: Pos = boundary.pos

  /**
   *
   *
   * @return
   */
  @inline
  lazy val lowerRightCorner: Pos =
    new Pos(right, bottom)

}
