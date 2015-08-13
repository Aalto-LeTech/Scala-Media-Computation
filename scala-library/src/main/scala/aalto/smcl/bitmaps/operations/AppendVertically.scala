package aalto.smcl.bitmaps.operations


import scala.collection.mutable.ArrayBuffer

import aalto.smcl.bitmaps.BitmapSettingKeys.{DefaultBackground, DefaultHorizontalAlignment, DefaultPaddingInPixels}
import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.common._
import aalto.smcl.platform.PlatformBitmapBuffer




/**
 * Operation to append bitmaps vertically next to each other.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class AppendVertically(
    bitmapsToCombine: Seq[Bitmap])(
    horizontalAlignment: HorizontalAlignment.Value = GS.optionFor(DefaultHorizontalAlignment),
    paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground))
    extends AbstractOperation with BufferProvider with Immutable {

  require(bitmapsToCombine.nonEmpty,
    "Append operation must be given a non-empty Sequence of Bitmap instances to combine.")

  require(paddingInPixels >= 0, s"The padding argument cannot be negative (was $paddingInPixels).")
  require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

  /** The [[BitmapOperationList]] instances resulting the bitmaps to be combined. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(bitmapsToCombine.map(_.operations).toSeq)

  /** Information about this [[BufferProvider]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "padding" -> Option(s"$paddingInPixels px"),
    "horizontalAlignment" -> Option(horizontalAlignment.toString),
    "backgroundColor" -> Option(s"0x${backgroundColor.toPixelInt.toArgbHexColorString}")))

  /** Width of the provided buffer in pixels. */
  val widthInPixels: Int = childOperationListsOption.get.maxBy({_.widthInPixels}).widthInPixels

  /** Height of the provided buffer in pixels. */
  val heightInPixels: Int =
    childOperationListsOption.get.foldLeft[Int](0)({_ + _.heightInPixels}) +
        (childOperationListsOption.get.length - 1) * paddingInPixels

  /** Future vertical offsets of the bitmaps to be combined. */
  val horizontalOffsets: Seq[Int] = horizontalAlignment match {
    case HorizontalAlignment.Left =>
      ArrayBuffer.fill[Int](bitmapsToCombine.length)(0).toSeq

    case HorizontalAlignment.Right =>
      bitmapsToCombine.map({widthInPixels - _.widthInPixels}).toSeq

    case HorizontalAlignment.Center =>
      bitmapsToCombine.map({bmp =>
        (widthInPixels.toDouble / 2 - bmp.widthInPixels.toDouble / 2).floor.toInt
      }).toSeq
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

    var yPosition = 0
    var itemNumber = 0
    childOperationListsOption.get.foreach {opList =>
      val sourceBuffer = opList.render()

      drawingSurface.drawBitmap(sourceBuffer, horizontalOffsets(itemNumber), yPosition)

      yPosition += sourceBuffer.heightInPixels + paddingInPixels
      itemNumber += 1
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
