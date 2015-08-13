package aalto.smcl.bitmaps.operations


import scala.annotation.tailrec

import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.common.{PresetColors, RGBAColor}
import aalto.smcl.platform.PlatformBitmapBuffer




/**
 * A companion object for [[BitmapOperationList]].
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] object BitmapOperationList {

  /**
   * Returns an empty [[BitmapOperationList]].
   */
  private[bitmaps] def apply(bufferProvider: BufferProviderOperation): BitmapOperationList =
    new BitmapOperationList(bufferProvider, List[AbstractOperation with RenderableOperation]())

}


/**
 * A list containing bitmap operations.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class BitmapOperationList private(
  private val bufferProvider: BufferProviderOperation,
  private val operations: List[RenderableOperation]) extends Immutable {

  /** Width of the bitmap produced by the content of this [[BitmapOperationList]]. */
  val widthInPixels: Int = bufferProvider.widthInPixels

  /** Height of the bitmap produced by the content of this [[BitmapOperationList]]. */
  val heightInPixels: Int = bufferProvider.heightInPixels

  /**
   * Adds a new [[Bitmap]] to the beginning of this [[BitmapOperationList]].
   */
  private[bitmaps] def add(newOperation: RenderableOperation) =
    BitmapOperationList(bufferProvider, newOperation +: operations)

  /**
   * Adds a new [[Bitmap]] to the beginning of this [[BitmapOperationList]].
   */
  private[bitmaps] def +:(newOperation: RenderableOperation) = this.add(newOperation)

  /**
   *
   */
  def initialBackgroundColor(): RGBAColor =
    operations.lastOption.getOrElse(None) match {
      case Clear(color) => color
      case _ => PresetColors('white)
    }

  /**
   *
   */
  private[bitmaps] def render(): PlatformBitmapBuffer =
    renderOperations(operations, bufferProvider.createNewBuffer)

  /**
   *
   */
  @tailrec
  private def renderOperations(
    list: List[RenderableOperation],
    buffer: PlatformBitmapBuffer): PlatformBitmapBuffer = {

    list match {
      case Nil => buffer
      case theRest :+ theLast =>
        theLast.render(buffer)
        renderOperations(theRest, buffer)
    }
  }

}
