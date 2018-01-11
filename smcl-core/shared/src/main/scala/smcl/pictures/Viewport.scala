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


import smcl.modeling.Len
import smcl.modeling.d2.{Bounds, Dims, Pos}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object Viewport {

  /**
   *
   *
   * @param boundary
   *
   * @return
   */
  def apply(boundary: Bounds): Viewport = {
    apply(boundary, null)
  }

  /**
   * Creates a new [[Viewport]] instance.
   *
   * @param boundary
   * @param name
   *
   * @return
   */
  private[pictures]
  def apply(
      boundary: Bounds,
      name: String): Viewport = {

    new Viewport(boundary, name)
  }

}




/**
 *
 *
 * @param boundary
 * @param name
 *
 * @author Aleksi Lukkarinen
 */
class Viewport(
    val boundary: Bounds,
    val name: String) {

  /**
   *
   *
   * @return
   */
  lazy val toSimplifiedViewport: simplified.Viewport =
    new simplified.Viewport(boundary.toSimplifiedBounds)

  /**
   *
   *
   * @return
   */
  @inline
  def dimensions: Dims = boundary.dimensions

  /**
   *
   *
   * @return
   */
  @inline
  def width: Len = boundary.width

  /**
   *
   *
   * @return
   */
  @inline
  def height: Len = boundary.height

  /**
   *
   *
   * @return
   */
  @inline
  def left: Double =
    upperLeftCorner.xInPixels

  /**
   *
   *
   * @return
   */
  @inline
  def top: Double =
    upperLeftCorner.yInPixels

  /**
   *
   *
   * @return
   */
  @inline
  def right: Double =
    lowerRightCorner.xInPixels

  /**
   *
   *
   * @return
   */
  @inline
  def bottom: Double =
    lowerRightCorner.yInPixels

  /**
   *
   *
   * @return
   */
  @inline
  def upperLeftCorner: Pos =
    boundary.upperLeftMarker

  /**
   *
   *
   * @return
   */
  @inline
  def lowerRightCorner: Pos =
    boundary.lowerRightMarker

}
