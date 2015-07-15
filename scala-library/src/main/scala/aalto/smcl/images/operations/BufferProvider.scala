package aalto.smcl.images.operations

import java.awt.image.{ BufferedImage => JBufferedImage }

/**
 * Ensures that the [[BitmapOperation]] classes providing a buffer for rendering images,
 * have a function for returning that buffer.
 *
 * @author Aleksi Lukkarinen
 */
trait BufferProvider { this: BitmapOperation =>

  /** A buffer for applying bitmap operations. */
  def buffer: JBufferedImage

}
