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
trait ImageElement
    extends HasPos
        with HasBounds
        with HasDims
        with TypeQueryable
        with Movable[ImageElement]
        with Rotatable[ImageElement]
        with Scalable[ImageElement]
        with Cropable[Bmp] {

  /** */
  type SimpleTransformer = ImageElement => ImageElement

  /** */
  val IdentitySimpleTransformer: SimpleTransformer = (i: ImageElement) => i


  /**
   * Position of this [[ImageElement]].
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
   * Tells if this [[ImageElement]] can be rendered on a bitmap.
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
  def toImage: Image = Image(this)

  /**
   *
   */
  @inline
  def display(): ImageElement = {
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
  def addToBack(content: ImageElement): ImageElement = addToBack(Seq(content))

  /**
   *
   *
   * @param content
   *
   * @return
   */
  @inline
  def addToBack(content: Seq[ImageElement]): ImageElement =
    Image(appendTo(content, Seq(this)))

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
      contentToAppend: Seq[ImageElement],
      existingContent: Seq[ImageElement]): Seq[ImageElement] = {

    contentToAppend.foldLeft(existingContent){(allElements, currentElement) =>
      if (currentElement.isImage)
        allElements ++ currentElement.toImage.elements
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
  def addToFront(content: ImageElement): ImageElement = content.addToBack(this)

  /**
   *
   *
   * @param content
   *
   * @return
   */
  @inline
  def addToFront(content: Seq[ImageElement]): ImageElement =
    Image(prependTo(content, Seq(this)))

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
      contentToPrepend: Seq[ImageElement],
      existingContent: Seq[ImageElement]): Seq[ImageElement] = {

    contentToPrepend.foldRight(existingContent){(currentElement, allElements) =>
      if (currentElement.isImage)
        currentElement.toImage.elements ++ allElements
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
  def +: (content: ImageElement): ImageElement = addToFront(content)

  /**
   *
   *
   * @param content
   *
   * @return
   */
  @inline
  def :+ (content: ImageElement): ImageElement = addToBack(content)

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
      content: ImageElement,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: HorizontalAlignment = DefaultHorizontalAlignment): ImageElement = {

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
      content: ImageElement,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: VerticalAlignment = DefaultVerticalAlignment): ImageElement = {

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
      content: ImageElement,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: HorizontalAlignment = DefaultHorizontalAlignment): ImageElement = {

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
      content: ImageElement,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: VerticalAlignment = DefaultVerticalAlignment): ImageElement = {

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
  def addCopiesAtPos(content: ImageElement, positions: Seq[Pos]): ImageElement =
    positions.foldLeft(this)(_.addAtPos(content, _))

  /**
   *
   *
   * @param contentsAndUpperLeftCornerPoss
   *
   * @return
   */
  @inline
  def addAtPos(contentsAndUpperLeftCornerPoss: Seq[(ImageElement, Pos)]): ImageElement =
    contentsAndUpperLeftCornerPoss.foldLeft(this)(_.addAtPos(_))

  /**
   *
   *
   * @param contentAndUpperLeftCornerPos
   *
   * @return
   */
  @inline
  def addAtPos(contentAndUpperLeftCornerPos: (ImageElement, Pos)): ImageElement =
    (addAtPos(_: ImageElement, _: Pos)).tupled.apply(contentAndUpperLeftCornerPos)

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
      content: ImageElement,
      upperLeftCorner: Pos): ImageElement = {

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
  def addCopiesAt(content: ImageElement, positions: Seq[(Double, Double)]): ImageElement =
    positions.foldLeft(this)((picture, coords) => picture.addAt(content, coords._1, coords._2))

  /**
   *
   *
   * @param contentsAndUpperLeftCornerCoordinatesInPixels
   *
   * @return
   */
  @inline
  def addAt(contentsAndUpperLeftCornerCoordinatesInPixels: Seq[(ImageElement, Double, Double)]): ImageElement = {
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
  def addAt(contentAndUpperLeftCornerCoordinatesInPixels: (ImageElement, Double, Double)): ImageElement =
    (addAt(_: ImageElement, _: Double, _: Double)).tupled.apply(contentAndUpperLeftCornerCoordinatesInPixels)

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
      content: ImageElement,
      upperLeftCornerX: Double,
      upperLeftCornerY: Double): ImageElement = {

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
      transformer: SimpleTransformer = IdentitySimpleTransformer): ImageElement = {

    if (numberOfReplicas < 0) {
      throw new IllegalArgumentException(
        s"Number of replicas cannot be negative (was $numberOfReplicas)")
    }

    @tailrec
    def replicate(
        replicasLeft: Int,
        previousTransformedImage: ImageElement,
        resultImage: ImageElement): ImageElement = {

      if (replicasLeft == 0)
        return resultImage

      val transformed = transformer(previousTransformedImage)

      replicate(
        replicasLeft - 1,
        transformed,
        resultImage.addToRight(transformed, paddingInPixels, alignment))
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
      transformer: SimpleTransformer = IdentitySimpleTransformer): ImageElement = {

    if (numberOfReplicas < 0) {
      throw new IllegalArgumentException(
        s"Number of replicas cannot be negative (was $numberOfReplicas)")
    }

    @tailrec
    def replicate(
        replicasLeft: Int,
        previousTransformedImage: ImageElement,
        resultImage: ImageElement): ImageElement = {

      if (replicasLeft == 0)
        return resultImage

      val transformed = transformer(previousTransformedImage)

      replicate(
        replicasLeft - 1,
        transformed,
        resultImage.addToBottom(transformed, paddingInPixels, alignment))
    }

    replicate(numberOfReplicas, this, this)
  }

}
