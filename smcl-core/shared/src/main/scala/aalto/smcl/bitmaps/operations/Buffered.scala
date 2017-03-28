package aalto.smcl.bitmaps.operations


import scala.ref.WeakReference

import aalto.smcl.infrastructure.BitmapBufferAdapter




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait Buffered {
  this: AbstractOperation =>

  /** Rendering buffer for this operation. */
  private[this] var _staticBuffer: WeakReference[BitmapBufferAdapter] =
    WeakReference[BitmapBufferAdapter](null)

  /**
   *
   *
   * @return
   */
  protected final def staticBufferOption: Option[BitmapBufferAdapter] =
    _staticBuffer.get

  /**
   *
   *
   * @param sources
   * @return
   */
  protected final def getOrCreateStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter = {
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
   * @param sources possible [[BitmapBufferAdapter]] instances used as sources
   * @return
   */
  protected def createStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter

}
