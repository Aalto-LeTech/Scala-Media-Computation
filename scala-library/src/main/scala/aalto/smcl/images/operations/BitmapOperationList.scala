package aalto.smcl.images.operations

import java.awt.image.{ BufferedImage => JBufferedImage }
import scala.annotation.tailrec

/**
 * A companion object for [[BitmapOperationList]].
 *
 * @author Aleksi Lukkarinen
 */
private[images] object BitmapOperationList {

  /**
   * Returns an empty [[BitmapOperationList]].
   */
  private[images] def apply(bufferProvider: AbstractOperation with BufferProvider): BitmapOperationList =
    new BitmapOperationList(bufferProvider, List[AbstractOperation with SingleSource]())

}

/**
 * A list containing bitmap operations.
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class BitmapOperationList private (
    private val bufferProvider: AbstractOperation with BufferProvider,
    private val operations: List[AbstractOperation with SingleSource]) {

  /** Width of the bitmap produced by the content of this [[BitmapOperationList]]. */
  val widthInPixels: Int = bufferProvider.widthInPixels

  /** Height of the bitmap produced by the content of this [[BitmapOperationList]]. */
  val heightInPixels: Int = bufferProvider.heightInPixels

  /**
   * Adds a new [[BitmapOperation]] to the beginning of this [[BitmapOperationList]].
   */
  private[images] def add(newOperation: AbstractOperation with SingleSource) =
    BitmapOperationList(bufferProvider, newOperation +: operations)

  /**
   * Adds a new [[BitmapOperation]] to the beginning of this [[BitmapOperationList]].
   */
  private[images] def +:(newOperation: AbstractOperation with SingleSource) = this.add(newOperation)

  /**
   *
   */
  def initialBackgroundColor(): Int =
    operations.last match {
      case Clear(color) => color getOrElse 0xFFFFFFFF
      case _            => 0xFFFFFFFF
    }

  /**
   *
   */
  private[images] def render(): JBufferedImage = renderOperations(operations, bufferProvider.buffer)

  /**
   *
   */
  @tailrec
  private def renderOperations(
    list: List[AbstractOperation with SingleSource],
    buffer: JBufferedImage): JBufferedImage = {

    list match {
      case Nil => buffer
      case theRest :+ theLast => {
        theLast render buffer
        renderOperations(theRest, buffer)
      }
    }
  }

}
