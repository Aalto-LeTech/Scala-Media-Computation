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


import smcl.infrastructure.{FlatMap, Identity}
import smcl.modeling.d2._




/**
 *
 */
object Picture {

  /**
   *
   *
   * @param elements
   *
   * @return
   */
  @inline
  def apply(elements: PictureElement*): Picture =
    apply(elements, Anchor.Center)

  /**
   *
   *
   * @param elements
   * @param anchor
   *
   * @return
   */
  @inline
  def apply(
      elements: Seq[PictureElement],
      anchor: Anchor[HasAnchor]): Picture = {

    apply(elements, viewport = None, anchor)
  }

  /**
   *
   *
   * @param elements
   * @param viewport
   * @param anchor
   *
   * @return
   */
  @inline
  def apply(
      elements: Seq[PictureElement] = Seq(),
      viewport: Option[Viewport] = None,
      anchor: Anchor[HasAnchor] = Anchor.Center): Picture = {

    val identity: Identity = Identity()

    new Picture(identity, elements, viewport, anchor)
  }

}




/**
 *
 *
 * @param identity
 * @param elements
 * @param viewport
 * @param anchor
 *
 * @author Aleksi Lukkarinen
 */
class Picture private(
    override val identity: Identity,
    val elements: Seq[PictureElement],
    val viewport: Option[Viewport],
    val anchor: Anchor[HasAnchor])
    extends PictureElement
        with HasAnchor
        with HasViewport[Picture]
        with FlatMap[Picture, Seq[PictureElement]] {

  // TODO: Tarkistukset

  /** */
  val boundary: Bounds =
    BoundaryCalculator.fromBoundaries(elements)

  /** Dimensions of this object. */
  @inline
  override
  def dimensions: Dims = Dims(boundary.width, boundary.height)

  /** Position of this object. */
  @inline
  override
  def position: Pos = boundary.upperLeftMarker

  /** */
  val isRenderable: Boolean = width > 0 && height > 0

  /**
   *
   *
   * @return
   */
  @inline
  override
  def isPicture: Boolean = true

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toPicture: Picture = this

  /**
   *
   */
  @inline
  override
  def display(): Picture = {
    super.display()

    this
  }

  /**
   *
   *
   * @param newElements
   * @param newViewport
   * @param newAnchor
   *
   * @return
   */
  @inline
  def copy(
      newElements: Seq[PictureElement] = elements,
      newViewport: Option[Viewport] = viewport,
      newAnchor: Anchor[HasAnchor] = anchor): Picture = {

    new Picture(identity, newElements, newViewport, newAnchor)
  }

  /**
   *
   *
   * @param f
   *
   * @return
   */
  @inline
  def map(f: (PictureElement) => PictureElement): Picture =
    copy(newElements = elements.map(f))

  /**
   *
   * @param f
   *
   * @return
   */
  @inline
  override
  def flatMap(f: (Seq[PictureElement]) => Picture): Picture = f(elements)

  /**
   *
   *
   * @param viewport
   *
   * @return
   */
  @inline
  override
  def setViewport(viewport: Viewport): Picture =
    copy(newViewport = Option(viewport))

  /**
   *
   *
   * @return
   */
  @inline
  override
  def removeViewport: Picture = copy(newViewport = None)

  /**
   *
   *
   * @param content
   *
   * @return
   */
  // Has to return a copy of *this* Picture because of viewports/anchors etc.
  @inline
  override
  def addToBack(content: Seq[PictureElement]): Picture =
    copy(newElements = appendTo(content, this.elements))

  /**
   *
   *
   * @param content
   *
   * @return
   */
  // Has to return a copy of *this* Picture because of viewports/anchors etc.
  @inline
  override
  def addToFront(content: Seq[PictureElement]): Picture =
    copy(newElements = prependTo(content, this.elements))

  /**
   *
   *
   * @param offsetsInPixels
   *
   * @return
   */
  @inline
  override
  def moveBy(offsetsInPixels: Seq[Double]): Picture = {
    require(
      offsetsInPixels.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions offsets must be given (found: ${offsetsInPixels.length})")

    moveBy(
      offsetsInPixels.head,
      offsetsInPixels.tail.head)
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
  override
  def moveBy(
      xOffsetInPixels: Double,
      yOffsetInPixels: Double): Picture = {

    map{_.moveBy(xOffsetInPixels, yOffsetInPixels)}
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  @inline
  override
  def moveUpperLeftCornerTo(coordinatesInPixels: Seq[Double]): Picture = {
    require(
      coordinatesInPixels.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions coordinates must be given (found: ${coordinatesInPixels.length})")

    moveUpperLeftCornerTo(
      coordinatesInPixels.head,
      coordinatesInPixels.tail.head)
  }

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   *
   * @return
   */
  @inline
  override
  def moveUpperLeftCornerTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): Picture = {

    moveBy(
      xCoordinateInPixels - boundary.upperLeftMarker.xInPixels,
      yCoordinateInPixels - boundary.upperLeftMarker.yInPixels)
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  @inline
  override
  def moveCenterTo(coordinatesInPixels: Seq[Double]): Picture = {
    require(
      coordinatesInPixels.length == NumberOfDimensions,
      s"Exactly $NumberOfDimensions coordinates must be given (found: ${coordinatesInPixels.length})")

    moveCenterTo(
      coordinatesInPixels.head,
      coordinatesInPixels.tail.head)
  }

  /**
   *
   *
   * @param xCoordinateInPixels
   * @param yCoordinateInPixels
   *
   * @return
   */
  @inline
  override
  def moveCenterTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): Picture = {

    moveBy(
      xCoordinateInPixels - boundary.center.xInPixels,
      yCoordinateInPixels - boundary.center.yInPixels)
  }

  /**
   * Rotates this object around origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCWAroundOrigo: Picture = map{_.rotateBy90DegsCWAroundOrigo}

  /**
   * Rotates this object around its center by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW: Picture = rotateBy90DegsCW(position)

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW(centerOfRotation: Pos): Picture =
    map{_.rotateBy90DegsCW(centerOfRotation)}

  /**
   * Rotates this object around origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCWAroundOrigo: Picture = map{_.rotateBy90DegsCCWAroundOrigo}

  /**
   * Rotates this object around the its center by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW: Picture = rotateBy90DegsCCW(position)

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW(centerOfRotation: Pos): Picture =
    map{_.rotateBy90DegsCCW(centerOfRotation)}

  /**
   * Rotates this object around origo (0,0) by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180DegsAroundOrigo: Picture = map{_.rotateBy180DegsAroundOrigo}

  /**
   * Rotates this object around its center by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs: Picture = rotateBy180Degs(position)

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs(centerOfRotation: Pos): Picture =
    map{_.rotateBy180Degs(centerOfRotation)}

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  override
  def rotateByAroundOrigo(angleInDegrees: Double): Picture =
    map{_.rotateByAroundOrigo(angleInDegrees)}

  /**
   * Rotates this object around its center by the specified number of degrees.
   *
   * @param angleInDegrees
   *
   * @return
   */
  @inline
  override
  def rotateBy(angleInDegrees: Double): Picture = rotateBy(angleInDegrees, position)

  /**
   * Rotates this object around a given point by the specified number of degrees.
   *
   * @param angleInDegrees
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): Picture = {

    map{_.rotateBy(angleInDegrees, centerOfRotation)}
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
  def scaleBy(
      widthFactor: Double,
      heightFactor: Double): Picture = {

    this
  }

}
