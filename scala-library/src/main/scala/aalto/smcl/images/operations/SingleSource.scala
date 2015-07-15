package aalto.smcl.images.operations

import java.awt.image.{ BufferedImage => JBufferedImage }

/**
 * Ensures that a [[BitmapOperation]] can be rendered to image buffers.
 *
 * @author Aleksi Lukkarinen
 */
trait SingleSource { this: AbstractOperation =>

  /** A buffer for applying bitmap operations. */
  def render(destination: JBufferedImage): Unit

}
