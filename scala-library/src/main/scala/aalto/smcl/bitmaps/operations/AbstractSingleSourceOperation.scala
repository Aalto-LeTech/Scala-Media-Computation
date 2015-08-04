package aalto.smcl.bitmaps.operations


import aalto.smcl.platform.PlatformBitmapBuffer




/**
 * Ensures that a [[AbstractOperation]] can be rendered to image buffers.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] abstract class AbstractSingleSourceOperation
    extends AbstractOperation with Immutable {

  /** A buffer for applying bitmap operations. */
  def render(destination: PlatformBitmapBuffer): Unit

}
