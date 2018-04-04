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
   * Creates a new [[Viewport]] instance.
   *
   * @param boundary
   *
   * @return
   */
  def apply(boundary: Bounds): Viewport =
    apply(boundary, None)

  /**
   * Creates a new [[Viewport]] instance.
   *
   * @param boundary
   * @param name
   *
   * @return
   */
  def apply(
      boundary: Bounds,
      name: Option[String]): Viewport = {

    // TODO: Check boundary for validness

    new Viewport(boundary, name.map(_.trim))
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
class Viewport private(
    val boundary: Bounds,
    val name: Option[String]) {

  /**
   *
   *
   * @return
   */
  @inline
  final
  def dimensions: Dims = boundary.dimensions

  /**
   *
   *
   * @return
   */
  @inline
  final
  def width: Len = boundary.width

  /**
   *
   *
   * @return
   */
  @inline
  final
  def height: Len = boundary.height

  /**
   *
   *
   * @return
   */
  @inline
  final
  def left: Double = upperLeftCorner.xInPixels

  /**
   *
   *
   * @return
   */
  @inline
  final
  def top: Double = upperLeftCorner.yInPixels

  /**
   *
   *
   * @return
   */
  @inline
  final
  def right: Double = lowerRightCorner.xInPixels

  /**
   *
   *
   * @return
   */
  @inline
  final
  def bottom: Double = lowerRightCorner.yInPixels

  /**
   *
   *
   * @return
   */
  @inline
  final
  def upperLeftCorner: Pos = boundary.upperLeftCorner

  /**
   *
   *
   * @return
   */
  @inline
  final
  def lowerRightCorner: Pos = boundary.lowerRightCorner

  /**
   *
   *
   * @param newBounds
   * @param newName
   *
   * @return
   */
  def copy(
      newBounds: Bounds = boundary,
      newName: Option[String] = name): Viewport = {

    Viewport.apply(newBounds, newName)
  }

}
