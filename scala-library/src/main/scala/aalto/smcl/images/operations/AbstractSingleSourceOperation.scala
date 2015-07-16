package aalto.smcl.images.operations

import java.awt.image.{ BufferedImage => JBufferedImage }

/**
 * Ensures that a [[AbstractOperation]] can be rendered to image buffers.
 *
 * @author Aleksi Lukkarinen
 */
private[images] abstract class AbstractSingleSourceOperation
    extends AbstractOperation with Immutable {

  /** A buffer for applying bitmap operations. */
  def render(destination: JBufferedImage): Unit

}
