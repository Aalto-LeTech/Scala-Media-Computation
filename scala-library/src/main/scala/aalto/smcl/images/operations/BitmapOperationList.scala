package aalto.smcl.images.operations


import java.awt.image.{BufferedImage => JBufferedImage}

import scala.annotation.tailrec

import aalto.smcl.images.immutable._




/**
 * A companion object for [[BitmapOperationList]].
 *
 * @author Aleksi Lukkarinen
 */
private[images] object BitmapOperationList {

  /**
   * Returns an empty [[BitmapOperationList]].
   */
  private[images] def apply(bufferProvider: AbstractBufferProviderOperation): BitmapOperationList =
    new BitmapOperationList(bufferProvider, List[AbstractOperation with AbstractSingleSourceOperation]())

}




/**
 * A list containing bitmap operations.
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class BitmapOperationList private(
    private val bufferProvider: AbstractBufferProviderOperation,
    private val operations: List[AbstractSingleSourceOperation]) extends Immutable {

  /** Width of the bitmap produced by the content of this [[BitmapOperationList]]. */
  val widthInPixels: Int = bufferProvider.widthInPixels

  /** Height of the bitmap produced by the content of this [[BitmapOperationList]]. */
  val heightInPixels: Int = bufferProvider.heightInPixels

  /**
   * Adds a new [[Bitmap]] to the beginning of this [[BitmapOperationList]].
   */
  private[images] def add(newOperation: AbstractSingleSourceOperation) =
    BitmapOperationList(bufferProvider, newOperation +: operations)

  /**
   * Adds a new [[Bitmap]] to the beginning of this [[BitmapOperationList]].
   */
  private[images] def +: (newOperation: AbstractSingleSourceOperation) = this.add(newOperation)

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
      list: List[AbstractSingleSourceOperation],
      buffer: JBufferedImage): JBufferedImage = {

    list match {
      case Nil                => buffer
      case theRest :+ theLast =>
        theLast.render(buffer)
        renderOperations(theRest, buffer)
    }
  }

}
