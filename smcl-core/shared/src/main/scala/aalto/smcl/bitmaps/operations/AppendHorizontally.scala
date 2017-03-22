package aalto.smcl.bitmaps.operations


import scala.collection.mutable.ArrayBuffer

import aalto.smcl.bitmaps._
import aalto.smcl.colors.{RGBAColor, _}
import aalto.smcl.infrastructure._




/**
 * Operation to append bitmaps horizontally next to each other.
 *
 * @param bitmapsToCombine
 * @param verticalAlignment
 * @param paddingInPixels
 * @param backgroundColor
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class AppendHorizontally(
  bitmapsToCombine: Seq[Bitmap])(
  verticalAlignment: VerticalAlignment.Value = GS.optionFor(DefaultVerticalAlignment),
  paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
  backgroundColor: RGBAColor = GS.colorFor(DefaultBackground))
  extends AbstractOperation
  with BufferProvider
  with Immutable {

  require(bitmapsToCombine.nonEmpty,
    "Append operation must be given a non-empty Sequence of Bitmap instances to combine.")

  require(paddingInPixels >= 0, s"The padding argument cannot be negative (was $paddingInPixels).")
  require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

  /** The [[BitmapOperationList]] instances resulting the bitmaps to be combined. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(bitmapsToCombine.map(_.operations))

  /** Information about this [[BufferProvider]] instance */
  lazy val metaInformation = MetaInformationMap("AppendHorizontally", Map(
    "padding" -> Option(s"$paddingInPixels px"),
    "verticalAlignment" -> Option(verticalAlignment.toString),
    "backgroundColor" -> Option(s"0x${backgroundColor.toArgbInt.toArgbHexColorString}")))

  /** Height of the provided buffer in pixels. */
  val heightInPixels: Int = childOperationListsOption.get.maxBy({_.heightInPixels}).heightInPixels

  /** Width of the provided buffer in pixels. */
  val widthInPixels: Int =
    childOperationListsOption.get.foldLeft[Int](0)({_ + _.widthInPixels}) +
      (childOperationListsOption.get.length - 1) * paddingInPixels

  BitmapValidator.validateBitmapSize(heightInPixels, widthInPixels)

  /** Vertical offsets of the bitmaps to be combined. */
  val verticalOffsets: Seq[Int] = verticalAlignment match {
    case VerticalAlignment.Top =>
      ArrayBuffer.fill[Int](bitmapsToCombine.length)(0).toSeq

    case VerticalAlignment.Bottom =>
      bitmapsToCombine map {heightInPixels - _.heightInPixels}

    case VerticalAlignment.Middle =>
      bitmapsToCombine map {bmp =>
        (heightInPixels.toDouble / 2 - bmp.heightInPixels.toDouble / 2).floor.toInt
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
    val newBuffer = PRF.createPlatformBitmapBuffer(widthInPixels, heightInPixels)
    val drawingSurface = newBuffer.drawingSurface

    drawingSurface clearUsing backgroundColor

    var xPosition = 0
    var itemNumber = 0
    childOperationListsOption.get foreach {opList =>
      val sourceBuffer = opList.render()

      drawingSurface.drawBitmap(sourceBuffer, xPosition, verticalOffsets(itemNumber))

      xPosition += sourceBuffer.widthInPixels + paddingInPixels
      itemNumber += 1
    }

    newBuffer
  }

  /**
   * Returns the buffer from which the provided buffer copies are made.
   * Users of this trait must provide an implementation, which returns
   * a [[BitmapBufferAdapter]] instance always after instantiation of
   * the class claiming to provide the buffer.
   *
   * @return    bitmap buffer to be made copies of for providees
   */
  override protected def provideNewBufferToBeCopiedForProvidees(): BitmapBufferAdapter =
    getOrCreateStaticBuffer()

}
