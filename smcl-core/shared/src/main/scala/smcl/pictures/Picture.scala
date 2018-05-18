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
  override final
  def dimensions: Dims = Dims(boundary.width, boundary.height)

  /** Position of this object. */
  @inline
  override final
  def position: Pos = boundary.center

  /** */
  val isRenderable: Boolean = width > 0 && height > 0

  /**
   *
   *
   * @return
   */
  @inline
  override final
  def isPicture: Boolean = true

  /**
   *
   *
   * @return
   */
  @inline
  override final
  def toPicture: Picture = this

  /**
   *
   */
  @inline
  override final
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
  final
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
  def map(f: (PictureElement) => PictureElement): Picture =
    copy(newElements = elements.map(f))

  /**
   *
   * @param f
   *
   * @return
   */
  override
  def flatMap(f: (Seq[PictureElement]) => Picture): Picture = f(elements)

  /**
   *
   *
   * @param viewport
   *
   * @return
   */
  override
  def setViewport(viewport: Viewport): Picture =
    copy(newViewport = Option(viewport))

  /**
   *
   *
   * @return
   */
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
  override final
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
  override final
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
  override final
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
  override final
  def moveUpperLeftCornerTo(
      xCoordinateInPixels: Double,
      yCoordinateInPixels: Double): Picture = {

    moveBy(
      xCoordinateInPixels - boundary.upperLeftCorner.xInPixels,
      yCoordinateInPixels - boundary.upperLeftCorner.yInPixels)
  }

  /**
   *
   *
   * @param coordinatesInPixels
   *
   * @return
   */
  @inline
  override final
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
  override final
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
  override final
  def rotateBy90DegsCWAroundOrigo: Picture = map{_.rotateBy90DegsCWAroundOrigo}

  /**
   * Rotates this object around its center by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override final
  def rotateBy90DegsCW: Picture = rotateBy90DegsCW(position)

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override final
  def rotateBy90DegsCW(centerOfRotation: Pos): Picture =
    map{_.rotateBy90DegsCW(centerOfRotation)}

  /**
   * Rotates this object around origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override final
  def rotateBy90DegsCCWAroundOrigo: Picture = map{_.rotateBy90DegsCCWAroundOrigo}

  /**
   * Rotates this object around the its center by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override final
  def rotateBy90DegsCCW: Picture = rotateBy90DegsCCW(position)

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override final
  def rotateBy90DegsCCW(centerOfRotation: Pos): Picture =
    map{_.rotateBy90DegsCCW(centerOfRotation)}

  /**
   * Rotates this object around origo (0,0) by 180 degrees.
   *
   * @return
   */
  @inline
  override final
  def rotateBy180DegsAroundOrigo: Picture = map{_.rotateBy180DegsAroundOrigo}

  /**
   * Rotates this object around its center by 180 degrees.
   *
   * @return
   */
  @inline
  override final
  def rotateBy180Degs: Picture = rotateBy180Degs(position)

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override final
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
  override final
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
  override final
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
  override final
  def rotateBy(
      angleInDegrees: Double,
      centerOfRotation: Pos): Picture = {

    map{_.rotateBy(angleInDegrees, centerOfRotation)}
  }

  /**
   * Scales this object to a given width in relation to its center.
   *
   * @param targetWidth
   *
   * @return
   */
  override
  def scaleHorizontallyTo(targetWidth: Double): Picture =
    scaleHorizontallyTo(targetWidth, position)

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
      relativityPoint: Pos): Picture = {

    scaleTo(
      targetWidth,
      targetHeight = height.inPixels,
      relativityPoint = relativityPoint)
  }

  /**
   * Scales this object to a given width in relation to the origo.
   *
   * @param targetWidth
   *
   * @return
   */
  override
  def scaleHorizontallyToRelativeToOrigo(targetWidth: Double): Picture =
    scaleToRelativeToOrigo(
      targetWidth,
      targetHeight = height.inPixels)

  /**
   * Scales this object to a given height in relation to its center.
   *
   * @param targetHeight
   *
   * @return
   */
  override
  def scaleVerticallyTo(targetHeight: Double): Picture =
    scaleVerticallyTo(targetHeight, position)

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
      relativityPoint: Pos): Picture = {

    scaleTo(
      targetWidth = width.inPixels,
      targetHeight = targetHeight,
      relativityPoint = relativityPoint)
  }

  /**
   * Scales this object to a given height in relation to the origo.
   *
   * @param targetHeight
   *
   * @return
   */
  override
  def scaleVerticallyToRelativeToOrigo(targetHeight: Double): Picture =
    scaleToRelativeToOrigo(
      targetWidth = width.inPixels,
      targetHeight = targetHeight)

  /**
   * Scales this object in relation to its center by
   * using a single length for both width and height.
   *
   * @param targetSideLength
   *
   * @return
   */
  override
  def scaleTo(targetSideLength: Double): Picture =
    scaleTo(targetSideLength, position)

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
      relativityPoint: Pos): Picture = {

    scaleTo(
      targetWidth = targetSideLength,
      targetHeight = targetSideLength,
      relativityPoint = relativityPoint)
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
  def scaleToRelativeToOrigo(targetSideLength: Double): Picture =
    scaleToRelativeToOrigo(
      targetWidth = targetSideLength,
      targetHeight = targetSideLength)

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
      targetHeight: Double): Picture = {

    scaleTo(targetWidth, targetHeight, position)
  }

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
      relativityPoint: Pos): Picture = {

    val (horizontalFactor, verticalFactor) =
      scalingFactorsFor(targetWidth, targetHeight)

    map(_.scaleBy(horizontalFactor, verticalFactor, relativityPoint))
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
      targetHeight: Double): Picture = {

    val (horizontalFactor, verticalFactor) =
      scalingFactorsFor(targetWidth, targetHeight)

    map(_.scaleByRelativeToOrigo(horizontalFactor, verticalFactor))
  }

  /**
   *
   *
   * @param targetWidth
   * @param targetHeight
   *
   * @return
   */
  def scalingFactorsFor(
      targetWidth: Double,
      targetHeight: Double): (Double, Double) = {

    val horizontalFactor = targetWidth / width.inPixels
    val verticalFactor = targetHeight / height.inPixels

    (horizontalFactor, verticalFactor)
  }

  /**
   * Scales this object horizontally in relation to its center.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleHorizontallyBy(factor: Double): Picture =
    map(_.scaleHorizontallyBy(factor, position))

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
      relativityPoint: Pos): Picture = {

    map(_.scaleHorizontallyBy(factor, relativityPoint))
  }

  /**
   * Scales this object horizontally in relation to the origo.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleHorizontallyByRelativeToOrigo(factor: Double): Picture =
    map(_.scaleHorizontallyByRelativeToOrigo(factor))

  /**
   * Scales this object vertically in relation to its center.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleVerticallyBy(factor: Double): Picture =
    map(_.scaleVerticallyBy(factor, position))

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
      relativityPoint: Pos): Picture = {

    map(_.scaleBy(factor, relativityPoint))
  }

  /**
   * Scales this object vertically in relation to the origo.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleVerticallyByRelativeToOrigo(factor: Double): Picture =
    map(_.scaleVerticallyByRelativeToOrigo(factor))

  /**
   * Scales this object in relation to its center by using a given factor
   * for both horizontal and vertical directions.
   *
   * @param factor
   *
   * @return
   */
  override
  def scaleBy(factor: Double): Picture =
    map(_.scaleBy(factor, position))

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
      relativityPoint: Pos): Picture = {

    map(_.scaleBy(factor, relativityPoint))
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
  def scaleByRelativeToOrigo(factor: Double): Picture =
    map(_.scaleByRelativeToOrigo(factor))

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
      verticalFactor: Double): Picture = {

    map(_.scaleBy(horizontalFactor, verticalFactor, position))
  }

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
      relativityPoint: Pos): Picture = {

    map(_.scaleBy(horizontalFactor, verticalFactor, relativityPoint))
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
      verticalFactor: Double): Picture = {

    map(_.scaleByRelativeToOrigo(horizontalFactor, verticalFactor))
  }

}
