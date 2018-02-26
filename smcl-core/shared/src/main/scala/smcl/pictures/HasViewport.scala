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


import smcl.modeling.d2.{Bounds, HasBounds, Pos}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait HasViewport[ReturnType <: PictureElement] {

  self: HasBounds =>

  /**
   *
   *
   * @return
   */
  def viewport: Option[Viewport]

  /**
   *
   *
   * @param viewportHolder
   * @param newName
   *
   * @return
   */
  @inline
  def setViewportFrom(
      viewportHolder: HasViewport[ReturnType],
      newName: Option[String] = None): ReturnType = {

    val possibleViewport = viewportHolder.viewport

    if (possibleViewport.isDefined)
      setViewport(possibleViewport.get.copy(newName = newName))
    else
      removeViewport
  }

  /**
   *
   *
   * @param name
   *
   * @return
   */
  @inline
  //noinspection MutatorLikeMethodIsParameterless
  def setViewportToContentBoundary(name: Option[String] = None): ReturnType =
    setViewportToContentBoundaryOf(this, name)

  /**
   *
   *
   * @param boundaryHolder
   * @param name
   *
   * @return
   */
  @inline
  def setViewportToContentBoundaryOf(
      boundaryHolder: HasBounds,
      name: Option[String]): ReturnType = {

    setViewport(boundaryHolder.boundary, name)
  }

  /**
   *
   *
   * @param upperLeftCorner
   * @param lowerRightCorner
   *
   * @return
   */
  @inline
  def setViewport(
      upperLeftCorner: Pos,
      lowerRightCorner: Pos): ReturnType = {

    setViewport(upperLeftCorner, lowerRightCorner, name = None)
  }

  /**
   *
   *
   * @param upperLeftCorner
   * @param lowerRightCorner
   * @param name
   *
   * @return
   */
  @inline
  def setViewport(
      upperLeftCorner: Pos,
      lowerRightCorner: Pos,
      name: Option[String]): ReturnType = {

    val boundary = Bounds(upperLeftCorner, lowerRightCorner)

    setViewport(boundary, name)
  }

  /**
   *
   *
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param lowerRightCornerXInPixels
   * @param lowerRightCornerYInPixels
   *
   * @return
   */
  @inline
  def setViewport(
      upperLeftCornerXInPixels: Double,
      upperLeftCornerYInPixels: Double,
      lowerRightCornerXInPixels: Double,
      lowerRightCornerYInPixels: Double): ReturnType = {

    setViewport(
      upperLeftCornerXInPixels,
      upperLeftCornerYInPixels,
      lowerRightCornerXInPixels,
      lowerRightCornerYInPixels,
      name = None)
  }

  /**
   *
   *
   * @param upperLeftCornerXInPixels
   * @param upperLeftCornerYInPixels
   * @param lowerRightCornerXInPixels
   * @param lowerRightCornerYInPixels
   * @param name
   *
   * @return
   */
  @inline
  def setViewport(
      upperLeftCornerXInPixels: Double,
      upperLeftCornerYInPixels: Double,
      lowerRightCornerXInPixels: Double,
      lowerRightCornerYInPixels: Double,
      name: Option[String]): ReturnType = {

    val boundary = Bounds(
      upperLeftCornerXInPixels,
      upperLeftCornerYInPixels,
      lowerRightCornerXInPixels,
      lowerRightCornerYInPixels)

    setViewport(boundary, name)
  }

  /**
   *
   *
   * @param boundary
   *
   * @return
   */
  @inline
  def setViewport(boundary: Bounds): ReturnType =
    setViewport(boundary, name = None)

  /**
   *
   *
   * @param boundary
   * @param name
   *
   * @return
   */
  @inline
  def setViewport(
      boundary: Bounds,
      name: Option[String]): ReturnType = {

    setViewport(Viewport(boundary, name))
  }

  /**
   *
   *
   * @param viewport
   *
   * @return
   */
  @inline
  def setViewport(viewport: Viewport): ReturnType

  /**
   *
   *
   * @return
   */
  @inline
  def hasViewport: Boolean = viewport != null

  /**
   *
   *
   * @return
   */
  //noinspection MutatorLikeMethodIsParameterless
  @inline
  def removeViewport: ReturnType

}
