package aalto.smcl.bitmaps.operations


import scala.collection.mutable.ArrayBuffer

import aalto.smcl.common.ColorOps._
import aalto.smcl.common._
import aalto.smcl.bitmaps.SettingKeys._
import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.platform.PlatformBitmapBuffer




/**
 * Operation to append bitmaps horizontally next to each other.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class AppendHorizontally(
    bitmapsToCombine: Seq[Bitmap])(
    verticalAlignment: VerticalAlignment.Value = VerticalAlignment.Middle,
    paddingInPixels: Int = 0,
    backgroundColor: Color = GS.colorFor(DefaultBackground))
    extends AbstractBufferProviderOperation with Immutable {

  require(bitmapsToCombine.nonEmpty,
    "Append operation must be given a non-empty Sequence of Bitmap instances to combine.")

  require(paddingInPixels >= 0, s"The padding argument cannot be negative (was $paddingInPixels).")
  require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

  /** The [[BitmapOperationList]] instances resulting the bitmaps to be combined. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(bitmapsToCombine.map(_.operations).toSeq)

  /** Information about this [[AbstractSingleSourceOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "padding" -> Option(s"$paddingInPixels px"),
    "verticalAlignment" -> Option(verticalAlignment.toString),
    "backgroundColor" -> Option(s"0x${backgroundColor.asPixelInt.toArgbHexColorString}")))

  /** Height of the provided buffer in pixels. */
  val heightInPixels: Int =
    childOperationListsOption.get.maxBy({_.heightInPixels}).heightInPixels

  /** Width of the provided buffer in pixels. */
  val widthInPixels: Int =
    childOperationListsOption.get.foldLeft[Int](0)({_ + _.widthInPixels}) +
        (childOperationListsOption.get.length - 1) * paddingInPixels

  /** Vertical offsets of the bitmaps to be combined. */
  val verticalOffsets: Seq[Int] = verticalAlignment match {
    case VerticalAlignment.Top =>
      ArrayBuffer.fill[Int](bitmapsToCombine.length)(0).toSeq

    case VerticalAlignment.Bottom =>
      bitmapsToCombine.map({heightInPixels - _.heightInPixels}).toSeq

    case VerticalAlignment.Middle =>
      bitmapsToCombine.map({bmp =>
        (heightInPixels.toDouble / 2 - bmp.heightInPixels.toDouble / 2).floor.toInt
      }).toSeq
  }

  /** A buffer for applying bitmap operations. */
  val buffer: PlatformBitmapBuffer = {
    val newBuffer = PlatformBitmapBuffer(widthInPixels, heightInPixels)
    val drawingSurface = newBuffer.drawingSurface()

    drawingSurface.clearUsing(backgroundColor)

    var xPosition = 0
    var itemNumber = 0
    childOperationListsOption.get.foreach {opList =>
      val sourceBuffer = opList.render()

      drawingSurface.drawBitmap(sourceBuffer, xPosition, verticalOffsets(itemNumber))

      xPosition += sourceBuffer.widthInPixels + paddingInPixels
      itemNumber += 1
    }

    newBuffer
  }

}
