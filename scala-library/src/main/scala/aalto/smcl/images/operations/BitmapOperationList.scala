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
  private[images] def apply(bufferProvider: BitmapOperation with BufferProvider): BitmapOperationList =
    new BitmapOperationList(bufferProvider, List[BitmapOperation with SingleSource]())

}

/**
 * A list containing bitmap operations.
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class BitmapOperationList private (
    private val bufferProvider: BitmapOperation with BufferProvider,
    private val operations: List[BitmapOperation with SingleSource]) {

  /**
   * Adds a new [[BitmapOperation]] to the beginning of this [[BitmapOperationList]].
   */
  private[images] def add(newOperation: BitmapOperation with SingleSource) =
    BitmapOperationList(bufferProvider, newOperation +: operations)

  /**
   * Adds a new [[BitmapOperation]] to the beginning of this [[BitmapOperationList]].
   */
  private[images] def +:(newOperation: BitmapOperation with SingleSource) = this.add(newOperation)

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
    list: List[BitmapOperation with SingleSource],
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
