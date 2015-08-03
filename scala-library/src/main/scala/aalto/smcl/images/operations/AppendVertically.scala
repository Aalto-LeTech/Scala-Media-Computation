package aalto.smcl.images.operations

import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.common.ColorOps._
import aalto.smcl.common._
import aalto.smcl.images.SettingKeys._
import aalto.smcl.images.immutable.primitives.Bitmap

import scala.collection.mutable.ArrayBuffer


/**
 * Operation to append bitmaps vertically next to each other.
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class AppendVertically(
    bitmapsToCombine: Seq[Bitmap])(
    horizontalAlignment: HorizontalAlignment.Value = HorizontalAlignment.Left,
    paddingInPixels: Int = 0,
    backgroundColor: Color = GS.colorFor(DefaultBackground))
    extends AbstractBufferProviderOperation with Immutable {

  require(bitmapsToCombine.nonEmpty,
    "Append operation must be given a non-empty array of Bitmap instances to combine.")

  require(paddingInPixels >= 0, s"The padding argument cannot be negative (was $paddingInPixels).")
  require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

  /** The [[BitmapOperationList]] instances resulting the bitmaps to be combined. */
  val childOperationListsOption: Option[Array[BitmapOperationList]] =
    Option(bitmapsToCombine.map(_.operations).toArray)

  /** Information about this [[AbstractSingleSourceOperation]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "padding" -> Option(s"$paddingInPixels px"),
    "horizontalAlignment" -> Option(horizontalAlignment.toString),
    "backgroundColor" -> Option(s"0x${backgroundColor.asPixelInt.toArgbHexColorString}")))

  /** Width of the provided buffer in pixels. */
  val widthInPixels: Int =
    childOperationListsOption.get.maxBy({ _.widthInPixels }).widthInPixels

  /** Height of the provided buffer in pixels. */
  val heightInPixels: Int =
    childOperationListsOption.get.foldLeft[Int](0)({ _ + _.heightInPixels }) +
      (childOperationListsOption.get.length - 1) * paddingInPixels

  /** Future vertical offsets of the bitmaps to be combined. */
  val horizontalOffsets: Array[Int] = horizontalAlignment match {
    case HorizontalAlignment.Left =>
      ArrayBuffer.fill[Int](bitmapsToCombine.length)(0).toArray

    case HorizontalAlignment.Right =>
      bitmapsToCombine.map({widthInPixels - _.widthInPixels}).toArray

    case HorizontalAlignment.Center =>
      bitmapsToCombine.map({bmp =>
        (widthInPixels.toDouble / 2 - bmp.widthInPixels.toDouble / 2).floor.toInt
      }).toArray
  }

  /** A buffer for applying bitmap operations. */
  val buffer: JBufferedImage = {
    val newBuffer = new JBufferedImage(
      widthInPixels, heightInPixels, JBufferedImage.TYPE_INT_ARGB)
    val drawingSurface = newBuffer.createGraphics()

    val oldColor = drawingSurface.getColor
    drawingSurface.fillRect(0, 0, widthInPixels, heightInPixels)
    drawingSurface.setColor(oldColor)

    var yPosition = 0
    var itemNumber = 0
    childOperationListsOption.get.foreach { opList =>
      val sourceBuffer = opList.render()

      drawingSurface.drawImage(sourceBuffer, horizontalOffsets(itemNumber), yPosition, null)

      yPosition += sourceBuffer.getHeight + paddingInPixels
      itemNumber += 1
    }

    newBuffer
  }

}
