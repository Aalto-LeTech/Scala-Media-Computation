package aalto.smcl.images.operations

import java.awt.image.{ BufferedImage => JBufferedImage }

/**
 * Ensures that the [[AbstractOperation]] classes providing a buffer for rendering images,
 * have a function for returning that buffer.
 *
 * @author Aleksi Lukkarinen
 */
private[images] abstract class AbstractBufferProviderOperation
    extends AbstractOperation with Immutable {

  /** Width of the provided buffer in pixels. */
  def widthInPixels: Int

  /** Height of the provided buffer in pixels. */
  def heightInPixels: Int

  /** A buffer for applying bitmap operations. */
  def buffer: JBufferedImage

}
