package aalto.smcl.bitmaps.operations


import scala.collection.mutable.ArrayBuffer

import aalto.smcl.GS
import aalto.smcl.bitmaps._
import aalto.smcl.colors.{ColorValidator, RGBAColor, _}
import aalto.smcl.infrastructure.{HorizontalAlignment, MetaInformationMap, VerticalAlignment}
import aalto.smcl.platform.PlatformBitmapBuffer




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
private[bitmaps] case class OverlayPerAlignments(
    bitmapsToOverlayFromBottomToTop: Seq[Bitmap])(
    horizontalAlignment: HorizontalAlignment.Value = GS.optionFor(DefaultHorizontalAlignment),
    verticalAlignment: VerticalAlignment.Value = GS.optionFor(DefaultVerticalAlignment),
    opacityForAllBitmaps: Int = ColorValidator.MaximumRgbaOpacity,
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground))
    extends AbstractOperation with BufferProvider with Immutable {

  require(bitmapsToOverlayFromBottomToTop.nonEmpty,
    "Overlay operation must be given a non-empty Sequence of Bitmap instances to overlay.")

  require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

  ColorValidator.validateRgbaOpacityComponent(opacityForAllBitmaps)

  /** The [[BitmapOperationList]] instances resulting the bitmaps to be combined. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(bitmapsToOverlayFromBottomToTop.map(_.operations))

  /** Information about this [[BufferProvider]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "horizontalAlignment" -> Option(horizontalAlignment.toString),
    "verticalAlignment" -> Option(verticalAlignment.toString),
    "opacityForAllBitmaps" -> Option(opacityForAllBitmaps.toString),
    "backgroundColor" -> Option(s"0x${backgroundColor.toArgbInt.toArgbHexColorString}")))

  /** Width of the provided buffer in pixels. */
  val widthInPixels: Int = childOperationListsOption.get.maxBy({_.widthInPixels}).widthInPixels

  /** Height of the provided buffer in pixels. */
  val heightInPixels: Int = childOperationListsOption.get.maxBy({_.heightInPixels}).heightInPixels

  /** Future horizontal offsets of the bitmaps to be overlaid. */
  val horizontalOffsets: Seq[Int] = horizontalAlignment match {
    case HorizontalAlignment.Left =>
      ArrayBuffer.fill[Int](bitmapsToOverlayFromBottomToTop.length)(0)

    case HorizontalAlignment.Right =>
      bitmapsToOverlayFromBottomToTop.map({widthInPixels - _.widthInPixels})

    case HorizontalAlignment.Center =>
      val canvasWidth = widthInPixels.toDouble / 2

      bitmapsToOverlayFromBottomToTop.map({bmp =>
        (canvasWidth - bmp.widthInPixels.toDouble / 2).floor.toInt
      })
  }

  /** Future vertical offsets of the bitmaps to be overlaid. */
  val verticalOffsets: Seq[Int] = verticalAlignment match {
    case VerticalAlignment.Top =>
      ArrayBuffer.fill[Int](bitmapsToOverlayFromBottomToTop.length)(0).toSeq

    case VerticalAlignment.Bottom =>
      bitmapsToOverlayFromBottomToTop.map({heightInPixels - _.heightInPixels})

    case VerticalAlignment.Middle =>
      val canvasMiddle = heightInPixels.toDouble / 2

      bitmapsToOverlayFromBottomToTop.map({bmp =>
        (canvasMiddle - bmp.heightInPixels.toDouble / 2).floor.toInt
      })
  }

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[BufferProvider]].
   *
   * @return
   */
  override def createStaticBuffer(sources: PlatformBitmapBuffer*): PlatformBitmapBuffer = {
    val newBuffer = PlatformBitmapBuffer(widthInPixels, heightInPixels)
    val drawingSurface = newBuffer.drawingSurface()

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
   * a [[PlatformBitmapBuffer]] instance always after instantiation of
   * the class claiming to provide the buffer.
   *
   * @return    bitmap buffer to be made copies of for providees
   */
  override protected def provideNewBufferToBeCopiedForProvidees(): PlatformBitmapBuffer =
    getOrCreateStaticBuffer()

}
