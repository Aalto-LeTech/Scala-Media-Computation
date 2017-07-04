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

package aalto.smcl.bitmaps.operations


import scala.collection.mutable.ArrayBuffer

import aalto.smcl.bitmaps.fullfeatured.AbstractBitmap
import aalto.smcl.colors.ColorValidator
import aalto.smcl.colors.rgb._
import aalto.smcl.infrastructure.{BitmapBufferAdapter, PRF}
import aalto.smcl.modeling.Len
import aalto.smcl.settings._




/**
 * Operation to overlay one bitmap over another bitmap per given horizontal and vertical alignments.
 *
 * @param bitmapsToOverlayFromBottomToTop
 * @param horizontalAlignment
 * @param verticalAlignment
 * @param backgroundColor
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class OverlayPerAlignments(
    bitmapsToOverlayFromBottomToTop: Seq[AbstractBitmap])(
    horizontalAlignment: HorizontalAlignment = DefaultHorizontalAlignment,
    verticalAlignment: VerticalAlignment = DefaultVerticalAlignment,
    opacityForAllBitmaps: Int = ColorValidator.MaximumOpacity,
    backgroundColor: Color = DefaultBackgroundColor,
    private val colorValidator: ColorValidator)
    extends AbstractOperation
            with BufferProvider
            with Immutable {

  require(bitmapsToOverlayFromBottomToTop.nonEmpty,
    "Overlay operation must be given a non-empty Sequence of Bitmap instances to overlay.")

  require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

  require(colorValidator != null, "The color validator argument has to be a ColorValidator instance (was null).")

  colorValidator.validateOpacityComponent(opacityForAllBitmaps)

  /** The [[BitmapOperationList]] instances resulting the bitmaps to be combined. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(bitmapsToOverlayFromBottomToTop.map(_.operations))

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "OverlayPerAlignments"

  /** Information about this [[BufferProvider]] instance */
  lazy val describedProperties = Map(
    "horizontalAlignment" -> Option(horizontalAlignment.toString),
    "verticalAlignment" -> Option(verticalAlignment.toString),
    "opacityForAllBitmaps" -> Option(opacityForAllBitmaps.toString),
    "backgroundColor" -> Option(s"0x${backgroundColor.toARGBInt.toARGBHexColorString}")
  )

  /** Width of the provided buffer in pixels. */
  val widthInPixels: Int = childOperationListsOption.get.maxBy({_.widthInPixels}).widthInPixels

  /** Height of the provided buffer in pixels. */
  val heightInPixels: Int = childOperationListsOption.get.maxBy({_.heightInPixels}).heightInPixels

  /** Future horizontal offsets of the bitmaps to be overlaid. */
  val horizontalOffsets: Seq[Int] = horizontalAlignment match {
    case HALeft =>
      ArrayBuffer.fill[Int](bitmapsToOverlayFromBottomToTop.length)(0)

    case HARight =>
      bitmapsToOverlayFromBottomToTop map {widthInPixels - _.widthInPixels}

    case HACenter =>
      val canvasWidth = widthInPixels.toDouble / 2

      bitmapsToOverlayFromBottomToTop map {bmp =>
        (canvasWidth - bmp.widthInPixels.toDouble / 2).floor.toInt
      }
  }

  /** Future vertical offsets of the bitmaps to be overlaid. */
  val verticalOffsets: Seq[Int] = verticalAlignment match {
    case VATop =>
      ArrayBuffer.fill[Int](bitmapsToOverlayFromBottomToTop.length)(0).toSeq

    case VABottom =>
      bitmapsToOverlayFromBottomToTop map {heightInPixels - _.heightInPixels}

    case VAMiddle =>
      val canvasMiddle = heightInPixels.toDouble / 2

      bitmapsToOverlayFromBottomToTop map {bmp =>
        (canvasMiddle - bmp.heightInPixels.toDouble / 2).floor.toInt
      }
  }

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[BufferProvider]].
   *
   * @return
   */
  override def createStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter = {
    val newBuffer = PRF.createPlatformBitmapBuffer(Len(widthInPixels), Len(heightInPixels))
    val drawingSurface = newBuffer.drawingSurface

    drawingSurface.clearUsing(backgroundColor)

    for (itemNumber <- childOperationListsOption.get.indices) {
      val sourceBuffer = childOperationListsOption.get(itemNumber).render()

      drawingSurface.drawBitmap(
        sourceBuffer,
        horizontalOffsets(itemNumber),
        verticalOffsets(itemNumber),
        opacityForAllBitmaps)
    }

    newBuffer
  }

  /**
   * Returns the buffer from which the provided buffer copies are made.
   * Users of this trait must provide an implementation, which returns
   * a [[aalto.smcl.infrastructure.BitmapBufferAdapter]] instance always after instantiation of
   * the class claiming to provide the buffer.
   *
   * @return bitmap buffer to be made copies of for providees
   */
  override protected def provideNewBufferToBeCopiedForProvidees(): BitmapBufferAdapter = {
    getOrCreateStaticBuffer()
  }

}
