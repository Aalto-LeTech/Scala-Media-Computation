package aalto.smcl.bitmaps.operations


import scala.ref.WeakReference

import aalto.smcl.infrastructure.PlatformBitmapBuffer




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait Buffered {
  this: AbstractOperation =>

  /** Rendering buffer for this operation. */
  private[this] var _staticBuffer: WeakReference[PlatformBitmapBuffer] =
    WeakReference[PlatformBitmapBuffer](null)

  /**
   *
   *
   * @return
   */
  protected final def staticBufferOption: Option[PlatformBitmapBuffer] =
    _staticBuffer.get

  /**
   *
   *
   * @param sources
   * @return
   */
  protected final def getOrCreateStaticBuffer(sources: PlatformBitmapBuffer*): PlatformBitmapBuffer = {
    if (_staticBuffer.get.isEmpty) {
      _staticBuffer = WeakReference(createStaticBuffer(sources: _*))
    }

    _staticBuffer.get.get
  }

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[Buffered]].
   *
   * @param sources     possible [[PlatformBitmapBuffer]] instances used as sources
   * @return
   */
  protected def createStaticBuffer(sources: PlatformBitmapBuffer*): PlatformBitmapBuffer

}
