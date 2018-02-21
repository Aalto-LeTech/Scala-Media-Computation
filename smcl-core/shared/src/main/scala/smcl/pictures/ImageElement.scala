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


import smcl.infrastructure.Identity
import smcl.modeling.d2._
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
  def addToBack(content: ImageElement): ImageElement = {
    if (isImage) {
      val thisImage = toImage
      val wholeContent =
        if (content.isImage)
          thisImage.elements ++ content.toImage.elements
        else
          thisImage.elements :+ content

      thisImage.copy(newElements = wholeContent)
    }
    else {
      val wholeContent =
        if (content.isImage)
          this +: content.toImage.elements
        else
          Seq(this, content)

      Image(wholeContent: _*)
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
  def addToFront(content: ImageElement): ImageElement = {
    if (isImage) {
      val thisImage = toImage
      val wholeContent =
        if (content.isImage)
          content.toImage.elements ++ thisImage.elements
        else
          content +: thisImage.elements

      thisImage.copy(newElements = wholeContent)
    }
    else {
      val wholeContent =
        if (content.isImage)
          content.toImage.elements :+ this
        else
          Seq(content, this)

      Image(wholeContent: _*)
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

}
