package aalto.smcl.bitmaps.operations


import scala.ref.WeakReference

import aalto.smcl.platform.PlatformBitmapBuffer




/**
 * Ensures that the [[AbstractOperation]] classes providing a buffer for rendering images
 * have necessary facilities for creating and returning that buffer.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] trait BufferProviderOperation
  extends AbstractOperation with Immutable {

  /** Width of the provided buffer in pixels. */
  def widthInPixels: Int

  /** Height of the provided buffer in pixels. */
  def heightInPixels: Int

  /** Rendering buffer for this operation. */
  private[this] var _staticBuffer: WeakReference[PlatformBitmapBuffer] =
    WeakReference[PlatformBitmapBuffer](null)

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[BufferProviderOperation]].
   *
   * @return
   */
  def createStaticBuffer(): PlatformBitmapBuffer

  /**
   * Creates a new buffer for applying bitmap operations.
   *
   * @return
   */
  final def createNewBuffer(): PlatformBitmapBuffer = {
    if (_staticBuffer.get.isEmpty) {
      _staticBuffer = WeakReference(createStaticBuffer())
    }

    _staticBuffer.get.get.copy()
  }

}
