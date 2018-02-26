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


import scala.annotation.tailrec

import smcl.infrastructure.Identity
import smcl.modeling.d2._
import smcl.settings._
import smcl.viewers.{display => displayInViewer}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait PictureElement
    extends HasPos
        with HasBounds
        with HasDims
        with TypeQueryable
        with Movable[PictureElement]
        with Rotatable[PictureElement]
        with Scalable[PictureElement]
        with Cropable[Bmp] {

  /** */
  type SimpleTransformer = PictureElement => PictureElement

  /** */
  val IdentitySimpleTransformer: SimpleTransformer = (i: PictureElement) => i


  /**
   * Position of this [[PictureElement]].
   *
   * @return
   */
  @inline
  def position: Pos = boundary.center

  /**
   *
   *
   * @return
   */
  def points: Seq[Pos] = Seq()

  /**
   *
   *
   * @return
   */
  def identity: Identity

  /**
   * Tells if this [[PictureElement]] can be rendered on a bitmap.
   *
   * @return
   */
  def isRenderable: Boolean

  /**
   *
   *
   * @return
   */
  @inline
  def toBitmap: Bmp = Bmp(this)

  /**
   *
   *
   * @return
   */
  @inline
  def toPicture: Picture = Picture(this)

  /**
   *
   */
  @inline
  def display(): PictureElement = {
    displayInViewer(toBitmap)

    this
  }

  /**
   *
   *
   * @param upperLeftCornerX
   * @param upperLeftCornerY
   * @param lowerRightCornerX
   * @param lowerRightCornerY
   *
   * @return
   */
  @inline
  override
  def crop(
      upperLeftCornerX: Double,
      upperLeftCornerY: Double,
      lowerRightCornerX: Double,
      lowerRightCornerY: Double): Bmp = {

    toBitmap.crop(
      upperLeftCornerX,
      upperLeftCornerY,
      lowerRightCornerX,
      lowerRightCornerY)
  }

  /**
   *
   *
   * @param content
   *
   * @return
   */
  @inline
  def addToBack(content: PictureElement): PictureElement = addToBack(Seq(content))

  /**
   *
   *
   * @param content
   *
   * @return
   */
  @inline
  def addToBack(content: Seq[PictureElement]): PictureElement =
    Picture(appendTo(content, Seq(this)))

  /**
   *
   *
   * @param contentToAppend
   * @param existingContent
   *
   * @return
   */
  @inline
  protected final
  def appendTo(
      contentToAppend: Seq[PictureElement],
      existingContent: Seq[PictureElement]): Seq[PictureElement] = {

    contentToAppend.foldLeft(existingContent){(allElements, currentElement) =>
      if (currentElement.isPicture)
        allElements ++ currentElement.toPicture.elements
      else
        allElements :+ currentElement
    }
  }

  /**
   *
   *
   * @param content
   *
   * @return
   */
  @inline
  def addToFront(content: PictureElement): PictureElement = content.addToBack(this)

  /**
   *
   *
   * @param content
   *
   * @return
   */
  @inline
  def addToFront(content: Seq[PictureElement]): PictureElement =
    Picture(prependTo(content, Seq(this)))

  /**
   *
   *
   * @param contentToPrepend
   * @param existingContent
   *
   * @return
   */
  @inline
  protected final
  def prependTo(
      contentToPrepend: Seq[PictureElement],
      existingContent: Seq[PictureElement]): Seq[PictureElement] = {

    contentToPrepend.foldRight(existingContent){(currentElement, allElements) =>
      if (currentElement.isPicture)
        currentElement.toPicture.elements ++ allElements
      else
        currentElement +: allElements
    }
  }

  /**
   *
   *
   * @param content
   *
   * @return
   */
  @inline
  def +: (content: PictureElement): PictureElement = addToFront(content)

  /**
   *
   *
   * @param content
   *
   * @return
   */
  @inline
  def :+ (content: PictureElement): PictureElement = addToBack(content)

  /**
   *
   *
   * @param content
   * @param paddingInPixels
   * @param alignment
   *
   * @return
   */
  @inline
  def addToTop(
      content: PictureElement,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: HorizontalAlignment = DefaultHorizontalAlignment): PictureElement = {

    val newUpperLeftCornerX = boundary.horizontalPositionFor(alignment, content.boundary)

    val newUpperLeftCornerY =
      boundary.upperLeftMarker.yInPixels - paddingInPixels - content.boundary.height.inPixels

    addAt(content, newUpperLeftCornerX, newUpperLeftCornerY)
  }

  /**
   *
   *
   * @param content
   * @param paddingInPixels
   * @param alignment
   *
   * @return
   */
  @inline
  def addToRight(
      content: PictureElement,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: VerticalAlignment = DefaultVerticalAlignment): PictureElement = {

    val newUpperLeftCornerX = boundary.lowerRightMarker.xInPixels + paddingInPixels
    val newUpperLeftCornerY = boundary.verticalPositionFor(alignment, content.boundary)

    addAt(content, newUpperLeftCornerX, newUpperLeftCornerY)
  }

  /**
   *
   *
   * @param content
   * @param paddingInPixels
   * @param alignment
   *
   * @return
   */
  @inline
  def addToBottom(
      content: PictureElement,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: HorizontalAlignment = DefaultHorizontalAlignment): PictureElement = {

    val newUpperLeftCornerX = boundary.horizontalPositionFor(alignment, content.boundary)
    val newUpperLeftCornerY = boundary.lowerRightMarker.yInPixels + paddingInPixels

    addAt(content, newUpperLeftCornerX, newUpperLeftCornerY)
  }

  /**
   *
   *
   * @param content
   * @param paddingInPixels
   * @param alignment
   *
   * @return
   */
  @inline
  def addToLeft(
      content: PictureElement,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: VerticalAlignment = DefaultVerticalAlignment): PictureElement = {

    val newUpperLeftCornerX =
      boundary.upperLeftMarker.xInPixels - paddingInPixels - content.boundary.width.inPixels

    val newUpperLeftCornerY = boundary.verticalPositionFor(alignment, content.boundary)

    addAt(content, newUpperLeftCornerX, newUpperLeftCornerY)
  }

  /**
   *
   *
   * @param content
   * @param positions
   *
   * @return
   */
  @inline
  def addCopiesAtPos(content: PictureElement, positions: Seq[Pos]): PictureElement =
    positions.foldLeft(this)(_.addAtPos(content, _))

  /**
   *
   *
   * @param contentsAndUpperLeftCornerPoss
   *
   * @return
   */
  @inline
  def addAtPos(contentsAndUpperLeftCornerPoss: Seq[(PictureElement, Pos)]): PictureElement =
    contentsAndUpperLeftCornerPoss.foldLeft(this)(_.addAtPos(_))

  /**
   *
   *
   * @param contentAndUpperLeftCornerPos
   *
   * @return
   */
  @inline
  def addAtPos(contentAndUpperLeftCornerPos: (PictureElement, Pos)): PictureElement =
    (addAtPos(_: PictureElement, _: Pos)).tupled.apply(contentAndUpperLeftCornerPos)

  /**
   *
   *
   * @param content
   * @param upperLeftCorner
   *
   * @return
   */
  @inline
  def addAtPos(
      content: PictureElement,
      upperLeftCorner: Pos): PictureElement = {

    addAt(
      content,
      upperLeftCorner.xInPixels,
      upperLeftCorner.yInPixels)
  }

  /**
   *
   *
   * @param content
   * @param positions
   *
   * @return
   */
  @inline
  def addCopiesAt(content: PictureElement, positions: Seq[(Double, Double)]): PictureElement =
    positions.foldLeft(this)((picture, coords) => picture.addAt(content, coords._1, coords._2))

  /**
   *
   *
   * @param contentsAndUpperLeftCornerCoordinatesInPixels
   *
   * @return
   */
  @inline
  def addAt(contentsAndUpperLeftCornerCoordinatesInPixels: Seq[(PictureElement, Double, Double)]): PictureElement = {
    val movedContent = contentsAndUpperLeftCornerCoordinatesInPixels.map{params =>
      params._1.moveTo(params._2, params._3)
    }

    addToFront(movedContent)
  }

  /**
   *
   *
   * @param contentAndUpperLeftCornerCoordinatesInPixels
   *
   * @return
   */
  @inline
  def addAt(contentAndUpperLeftCornerCoordinatesInPixels: (PictureElement, Double, Double)): PictureElement =
    (addAt(_: PictureElement, _: Double, _: Double)).tupled.apply(contentAndUpperLeftCornerCoordinatesInPixels)

  /**
   *
   *
   * @param content
   * @param upperLeftCornerX
   * @param upperLeftCornerY
   *
   * @return
   */
  @inline
  def addAt(
      content: PictureElement,
      upperLeftCornerX: Double,
      upperLeftCornerY: Double): PictureElement = {

    addToFront(content.moveTo(upperLeftCornerX, upperLeftCornerY))
  }

  /**
   *
   *
   * @param numberOfReplicas
   * @param paddingInPixels
   * @param alignment
   * @param transformer
   *
   * @return
   */
  def replicateHorizontally(
      numberOfReplicas: Int,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: VerticalAlignment = DefaultVerticalAlignment,
      transformer: SimpleTransformer = IdentitySimpleTransformer): PictureElement = {

    if (numberOfReplicas < 0) {
      throw new IllegalArgumentException(
        s"Number of replicas cannot be negative (was $numberOfReplicas)")
    }

    @tailrec
    def replicate(
        replicasLeft: Int,
        previousTransformedPicture: PictureElement,
        resultPicture: PictureElement): PictureElement = {

      if (replicasLeft == 0)
        return resultPicture

      val transformed = transformer(previousTransformedPicture)

      replicate(
        replicasLeft - 1,
        transformed,
        resultPicture.addToRight(transformed, paddingInPixels, alignment))
    }

    replicate(numberOfReplicas, this, this)
  }

  /**
   *
   *
   * @param numberOfReplicas
   * @param paddingInPixels
   * @param alignment
   * @param transformer
   *
   * @return
   */
  def replicateVertically(
      numberOfReplicas: Int,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: HorizontalAlignment = DefaultHorizontalAlignment,
      transformer: SimpleTransformer = IdentitySimpleTransformer): PictureElement = {

    if (numberOfReplicas < 0) {
      throw new IllegalArgumentException(
        s"Number of replicas cannot be negative (was $numberOfReplicas)")
    }

    @tailrec
    def replicate(
        replicasLeft: Int,
        previousTransformedPicture: PictureElement,
        resultPicture: PictureElement): PictureElement = {

      if (replicasLeft == 0)
        return resultPicture

      val transformed = transformer(previousTransformedPicture)

      replicate(
        replicasLeft - 1,
        transformed,
        resultPicture.addToBottom(transformed, paddingInPixels, alignment))
    }

    replicate(numberOfReplicas, this, this)
  }

}
