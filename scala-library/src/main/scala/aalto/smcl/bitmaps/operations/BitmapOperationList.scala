package aalto.smcl.bitmaps.operations


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
  private[bitmaps] def apply(bufferProvider: BufferProvider): BitmapOperationList =
    new BitmapOperationList(bufferProvider, List[AbstractOperation with Renderable]())

}


/**
 * A list containing bitmap operations.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class BitmapOperationList private(
  private val bufferProvider: BufferProvider,
  private val operations: List[Renderable]) extends Immutable {

  /** Width of the bitmap produced by the content of this [[BitmapOperationList]]. */
  val widthInPixels: Int = bufferProvider.widthInPixels

  /** Height of the bitmap produced by the content of this [[BitmapOperationList]]. */
  val heightInPixels: Int = bufferProvider.heightInPixels

  /**
   * Adds a new [[Bitmap]] to the beginning of this [[BitmapOperationList]].
   */
  private[bitmaps] def add(newOperation: Renderable) =
    BitmapOperationList(bufferProvider, newOperation +: operations)

  /**
   * Adds a new [[Bitmap]] to the beginning of this [[BitmapOperationList]].
   */
  private[bitmaps] def +:(newOperation: Renderable) = this.add(newOperation)

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
  private[bitmaps] def render(): PlatformBitmapBuffer = {
    val newBuffer = bufferProvider.createNewBuffer()
    val renderedBuffer = renderOperations(operations, newBuffer)

    renderedBuffer
  }


  /**
   *
   */
  //@tailrec
  private def renderOperations(
    list: List[Renderable],
    buffer: PlatformBitmapBuffer): PlatformBitmapBuffer = {

    list match {
      case Nil => buffer
      case theRest :+ theLast =>
        theLast.render(buffer)
        renderOperations(theRest, buffer)
    }
  }

}
