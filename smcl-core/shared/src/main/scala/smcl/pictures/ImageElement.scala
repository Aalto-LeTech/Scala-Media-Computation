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
   * @param newContent
   *
   * @return
   */
  @inline
  def addToBack(newContent: ImageElement): ImageElement = {
    val wholeContent =
      if (newContent.isImage)
        this +: newContent.toImage.elements
      else
        Seq(this, newContent)

    Image(wholeContent: _*)
  }

  /**
   *
   *
   * @param newContent
   *
   * @return
   */
  @inline
  def addToFront(newContent: ImageElement): ImageElement =
    newContent.addToBack(this)

  /**
   *
   *
   * @param newContent
   *
   * @return
   */
  @inline
  def +: (newContent: ImageElement): ImageElement = addToFront(newContent)

  /**
   *
   *
   * @param newContent
   *
   * @return
   */
  @inline
  def :+ (newContent: ImageElement): ImageElement = addToBack(newContent)

  /**
   *
   *
   * @param newContent
   * @param paddingInPixels
   * @param alignment
   *
   * @return
   */
  @inline
  def addToTop(
      newContent: ImageElement,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: HorizontalAlignment = DefaultHorizontalAlignment): ImageElement = {

    val ncNewUpperLeftX = boundary.horizontalPositionFor(alignment, newContent.boundary)

    val ncNewUpperLeftY =
      boundary.upperLeftMarker.yInPixels - paddingInPixels - newContent.boundary.height.inPixels

    moveAndAddToSide(newContent, ncNewUpperLeftX, ncNewUpperLeftY)
  }

  /**
   *
   *
   * @param newContent
   * @param paddingInPixels
   * @param alignment
   *
   * @return
   */
  @inline
  def addToRight(
      newContent: ImageElement,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: VerticalAlignment = DefaultVerticalAlignment): ImageElement = {

    val ncNewUpperLeftX = boundary.lowerRightMarker.xInPixels + paddingInPixels
    val ncNewUpperLeftY = boundary.verticalPositionFor(alignment, newContent.boundary)

    moveAndAddToSide(newContent, ncNewUpperLeftX, ncNewUpperLeftY)
  }

  /**
   *
   *
   * @param newContent
   * @param paddingInPixels
   * @param alignment
   *
   * @return
   */
  @inline
  def addToBottom(
      newContent: ImageElement,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: HorizontalAlignment = DefaultHorizontalAlignment): ImageElement = {

    val ncNewUpperLeftX = boundary.horizontalPositionFor(alignment, newContent.boundary)
    val ncNewUpperLeftY = boundary.lowerRightMarker.yInPixels + paddingInPixels

    moveAndAddToSide(newContent, ncNewUpperLeftX, ncNewUpperLeftY)
  }

  /**
   *
   *
   * @param newContent
   * @param paddingInPixels
   * @param alignment
   *
   * @return
   */
  @inline
  def addToLeft(
      newContent: ImageElement,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: VerticalAlignment = DefaultVerticalAlignment): ImageElement = {

    val ncNewUpperLeftX =
      boundary.upperLeftMarker.xInPixels - paddingInPixels - newContent.boundary.width.inPixels

    val ncNewUpperLeftY = boundary.verticalPositionFor(alignment, newContent.boundary)

    moveAndAddToSide(newContent, ncNewUpperLeftX, ncNewUpperLeftY)
  }

  /**
   *
   *
   * @param newContent
   * @param ncNewUpperLeftCornerX
   * @param ncNewUpperLeftCornerY
   *
   * @return
   */
  @inline
  private
  def moveAndAddToSide(
      newContent: ImageElement,
      ncNewUpperLeftCornerX: Double,
      ncNewUpperLeftCornerY: Double): ImageElement = {

    val ncCurrentUpperLeftCorner = newContent.boundary.upperLeftMarker
    val totalOffsetX = ncNewUpperLeftCornerX - ncCurrentUpperLeftCorner.xInPixels
    val totalOffsetY = ncNewUpperLeftCornerY - ncCurrentUpperLeftCorner.yInPixels

    val movedContent = newContent.moveBy(totalOffsetX, totalOffsetY)

    addToFront(movedContent)
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
