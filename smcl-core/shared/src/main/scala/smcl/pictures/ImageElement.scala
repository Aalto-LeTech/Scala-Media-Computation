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
  def addToRight(
      newContent: ImageElement,
      paddingInPixels: Double = DefaultPaddingInPixels,
      alignment: VerticalAlignment = DefaultVerticalAlignment): ImageElement = {

    val ncBounds = newContent.boundary

    val ncNewUpperLeftX = boundary.lowerRightMarker.xInPixels + paddingInPixels
    val alignmentOffsetY: Double =
      alignment match {
        case VATop    => 0.0
        case VAMiddle => height.half.inPixels - ncBounds.height.half.inPixels
        case VABottom => height.inPixels - ncBounds.height.inPixels
      }
    val ncNewUpperLeftY = boundary.upperLeftMarker.yInPixels + alignmentOffsetY

    val ncCurrenyUpperLeftCorner = ncBounds.upperLeftMarker
    val totalOffsetX = ncNewUpperLeftX - ncCurrenyUpperLeftCorner.xInPixels
    val totalOffsetY = ncNewUpperLeftY - ncCurrenyUpperLeftCorner.yInPixels

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
  @inline
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

}
