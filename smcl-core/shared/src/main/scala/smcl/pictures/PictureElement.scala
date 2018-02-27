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
import scala.collection.mutable.ListBuffer

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
        with Cropable[Bitmap] {

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
  def toBitmap: Bitmap = Bitmap(this)

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
      lowerRightCornerY: Double): Bitmap = {

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

    addAt(
      content,
      newUpperLeftCornerX, newUpperLeftCornerY,
      UpperLeftCornerPosition)
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

    addAt(
      content,
      newUpperLeftCornerX, newUpperLeftCornerY,
      UpperLeftCornerPosition)
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

    addAt(
      content,
      newUpperLeftCornerX, newUpperLeftCornerY,
      UpperLeftCornerPosition)
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

    addAt(
      content,
      newUpperLeftCornerX, newUpperLeftCornerY,
      UpperLeftCornerPosition)
  }

  /**
   *
   *
   * @param content
   * @param positions
   * @param positionType
   *
   * @return
   */
  @inline
  def addCopiesAtPos(
      content: PictureElement,
      positions: Seq[Pos],
      positionType: PositionType): PictureElement = {

    val wholeContent = ListBuffer[PictureElement]()
    val mover =
      if (content.isPicture)
        wholeContent ++= content.moveTo(_: Pos, positionType).toPicture.elements
      else
        wholeContent += content.moveTo(_: Pos, positionType)

    positions.foreach(mover)
    addToFront(wholeContent)
  }

  /**
   *
   *
   * @param content
   * @param positions
   * @param positionType
   *
   * @return
   */
  @inline
  def addCopiesAt(
      content: PictureElement,
      positions: Seq[(Double, Double)],
      positionType: PositionType): PictureElement = {

    val wholeContent = ListBuffer[PictureElement]()
    val mover =
      if (content.isPicture) {
        wholeContent ++= content.moveTo(_: Double, _: Double, positionType).toPicture.elements
      }
      else {
        wholeContent += content.moveTo(_: Double, _: Double, positionType)
      }

    positions.foreach(coords => mover(coords._1, coords._2))
    addToFront(wholeContent)
  }

  /**
   *
   *
   * @param contentsAndPositions
   * @param positionType
   *
   * @return
   */
  @inline
  def addAtPos(
      contentsAndPositions: Seq[(PictureElement, Pos)],
      positionType: PositionType): PictureElement = {

    val movedContent = contentsAndPositions.map{params =>
      params._1.moveTo(params._2.xInPixels, params._2.yInPixels, positionType)
    }

    addToFront(movedContent)
  }

  /**
   *
   *
   * @param contentAndPosition
   * @param positionType
   *
   * @return
   */
  @inline
  def addAtPos(
      contentAndPosition: (PictureElement, Pos),
      positionType: PositionType): PictureElement = {

    addAtPos(
      contentAndPosition._1,
      contentAndPosition._2,
      positionType)
  }

  /**
   *
   *
   * @param content
   * @param position
   * @param positionType
   *
   * @return
   */
  @inline
  def addAtPos(
      content: PictureElement,
      position: Pos,
      positionType: PositionType): PictureElement = {

    addAt(
      content,
      position.xInPixels,
      position.yInPixels)
  }

  /**
   *
   *
   * @param contentsAndCoordinatesInPixels
   * @param positionType
   *
   * @return
   */
  @inline
  def addAt(
      contentsAndCoordinatesInPixels: Seq[(PictureElement, Double, Double)],
      positionType: PositionType): PictureElement = {

    val movedContent = contentsAndCoordinatesInPixels.map{params =>
      params._1.moveTo(params._2, params._3, positionType)
    }

    addToFront(movedContent)
  }

  /**
   *
   *
   * @param contentAndCoordinatesInPixels
   * @param positionType
   *
   * @return
   */
  @inline
  def addAt(
      contentAndCoordinatesInPixels: (PictureElement, Double, Double),
      positionType: PositionType): PictureElement = {

    addAt(
      contentAndCoordinatesInPixels._1,
      contentAndCoordinatesInPixels._2,
      contentAndCoordinatesInPixels._3,
      positionType)
  }

  /**
   *
   *
   * @param content
   * @param xCoordinate
   * @param yCoordinate
   * @param positionType
   *
   * @return
   */
  @inline
  def addAt(
      content: PictureElement,
      xCoordinate: Double,
      yCoordinate: Double,
      positionType: PositionType = DefaultPositionType): PictureElement = {

    addToFront(content.moveTo(xCoordinate, yCoordinate, positionType))
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

  /**
   *
   *
   * @param alternatives
   * @param numberOfAlternations
   * @param paddingInPixels
   * @param alignment
   *
   * @return
   */
  def alternateHorizontallyWith(
      alternatives: Seq[PictureElement],
      numberOfAlternations: Int,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: VerticalAlignment = DefaultVerticalAlignment): PictureElement = {

    val NumberOfFirstAlternation = 1

    if (numberOfAlternations < 0) {
      throw new IllegalArgumentException(
        s"Number of alternations cannot be negative (was $numberOfAlternations)")
    }

    val numberOfSourceElements = alternatives.length + 1

    @tailrec
    def alternate(
        numberOfNextAlternation: Int,
        resultPicture: PictureElement): PictureElement = {

      if (numberOfNextAlternation > numberOfAlternations)
        return resultPicture

      val numberOfNextElement = numberOfNextAlternation % numberOfSourceElements
      val selectedElement =
        if (numberOfNextElement == 0)
          this
        else
          alternatives(numberOfNextElement - 1)

      alternate(
        numberOfNextAlternation + 1,
        resultPicture.addToRight(selectedElement, paddingInPixels, alignment))
    }

    alternate(NumberOfFirstAlternation, this)
  }

  /**
   *
   *
   * @param alternatives
   * @param numberOfAlternations
   * @param paddingInPixels
   * @param alignment
   *
   * @return
   */
  def alternateVerticallyWith(
      alternatives: Seq[PictureElement],
      numberOfAlternations: Int,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: HorizontalAlignment = DefaultHorizontalAlignment): PictureElement = {

    val NumberOfFirstAlternation = 1

    if (numberOfAlternations < 0) {
      throw new IllegalArgumentException(
        s"Number of alternations cannot be negative (was $numberOfAlternations)")
    }

    val numberOfSourceElements = alternatives.length + 1

    @tailrec
    def alternate(
        numberOfNextAlternation: Int,
        resultPicture: PictureElement): PictureElement = {

      if (numberOfNextAlternation > numberOfAlternations)
        return resultPicture

      val numberOfNextElement = numberOfNextAlternation % numberOfSourceElements
      val selectedElement =
        if (numberOfNextElement == 0)
          this
        else
          alternatives(numberOfNextElement - 1)

      alternate(
        numberOfNextAlternation + 1,
        resultPicture.addToBottom(selectedElement, paddingInPixels, alignment))
    }

    alternate(NumberOfFirstAlternation, this)
  }

}
