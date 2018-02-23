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
object Image {

  /**
   *
   *
   * @param elements
   *
   * @return
   */
  @inline
  def apply(elements: ImageElement*): Image =
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
      elements: Seq[ImageElement],
      anchor: Anchor[HasAnchor]): Image = {

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
      elements: Seq[ImageElement] = Seq(),
      viewport: Option[Viewport] = None,
      anchor: Anchor[HasAnchor] = Anchor.Center): Image = {

    val identity: Identity = Identity()

    new Image(identity, elements, viewport, anchor)
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
class Image private(
    override val identity: Identity,
    val elements: Seq[ImageElement],
    val viewport: Option[Viewport],
    val anchor: Anchor[HasAnchor])
    extends ImageElement
        with HasAnchor
        with HasViewport[Image]
        with FlatMap[Image, Seq[ImageElement]] {

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
  def isImage: Boolean = true

  /**
   *
   *
   * @return
   */
  @inline
  override
  def toImage: Image = this

  /**
   *
   *
   * @param viewport
   *
   * @return
   */
  @inline
  override
  def setViewport(viewport: Viewport): Image =
    copy(newViewport = Option(viewport))

  /**
   *
   *
   * @return
   */
  @inline
  override
  def removeViewport: Image = copy(newViewport = None)

  /**
   *
   *
   * @param f
   *
   * @return
   */
  @inline
  def map(f: (ImageElement) => ImageElement): Image =
    copy(newElements = elements.map(f))

  /**
   *
   * @param f
   *
   * @return
   */
  @inline
  override
  def flatMap(f: (Seq[ImageElement]) => Image): Image = f(elements)

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
      newElements: Seq[ImageElement] = elements,
      newViewport: Option[Viewport] = viewport,
      newAnchor: Anchor[HasAnchor] = anchor): Image = {

    new Image(identity, newElements, newViewport, newAnchor)
  }

  /**
   *
   *
   * @param newContent
   *
   * @return
   */
  @inline
  override
  def addToBack(newContent: ImageElement): ImageElement = {
    val wholeContent =
      if (newContent.isImage)
        elements ++ newContent.toImage.elements
      else
        elements :+ newContent

    // Has to be copy of *this* Image because of viewports/anchors etc.
    copy(newElements = wholeContent)
  }

  /**
   *
   *
   * @param newContent
   *
   * @return
   */
  @inline
  override
  def addToFront(newContent: ImageElement): ImageElement = {
    val wholeContent =
      if (newContent.isImage)
        newContent.toImage.elements ++ elements
      else
        newContent +: elements

    // Has to be copy of *this* Image because of viewports/anchors etc.
    copy(newElements = wholeContent)
  }

  /**
   *
   *
   * @param offsets
   *
   * @return
   */
  @inline
  override
  def moveBy(offsets: Double*): Image = {
    map{_.moveBy(offsets: _*)}
  }

  /**
   *
   */
  @inline
  override
  def display(): Image = {
    super.display()

    this
  }


  /**
   * Rotates this object around origo (0,0) by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCWAroundOrigo: ImageElement = map{_.rotateBy90DegsCWAroundOrigo}

  /**
   * Rotates this object around its center by 90 degrees clockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW: ImageElement = rotateBy90DegsCW(position)

  /**
   * Rotates this object around a given point by 90 degrees clockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCW(centerOfRotation: Pos): ImageElement =
    map{_.rotateBy90DegsCW(centerOfRotation)}

  /**
   * Rotates this object around origo (0,0) by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCWAroundOrigo: ImageElement = map{_.rotateBy90DegsCCWAroundOrigo}

  /**
   * Rotates this object around the its center by 90 degrees counterclockwise.
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW: ImageElement = rotateBy90DegsCCW(position)

  /**
   * Rotates this object around a given point by 90 degrees counterclockwise.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy90DegsCCW(centerOfRotation: Pos): ImageElement =
    map{_.rotateBy90DegsCCW(centerOfRotation)}

  /**
   * Rotates this object around origo (0,0) by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180DegsAroundOrigo: ImageElement = map{_.rotateBy180DegsAroundOrigo}

  /**
   * Rotates this object around its center by 180 degrees.
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs: ImageElement = rotateBy180Degs(position)

  /**
   * Rotates this object around a given point by 180 degrees.
   *
   * @param centerOfRotation
   *
   * @return
   */
  @inline
  override
  def rotateBy180Degs(centerOfRotation: Pos): ImageElement =
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
  def rotateByAroundOrigo(angleInDegrees: Double): ImageElement =
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
  def rotateBy(angleInDegrees: Double): ImageElement = rotateBy(angleInDegrees, position)

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
      centerOfRotation: Pos): ImageElement = {

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
      heightFactor: Double): Image = {

    this
  }

}
