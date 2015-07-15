package aalto.smcl.images.operations

import java.awt.image.{ BufferedImage => JBufferedImage }

/**
 * Ensures that the [[BitmapOperation]] classes providing a buffer for rendering images,
 * have a function for returning that buffer.
 *
 * @author Aleksi Lukkarinen
 */
trait BufferProvider { this: AbstractOperation =>

  /** Width of the provided buffer in pixels. */
  def widthInPixels: Int

  /** Height of the provided buffer in pixels. */
  def heightInPixels: Int

  /** A buffer for applying bitmap operations. */
  def buffer: JBufferedImage

}
