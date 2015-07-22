package aalto.smcl.images.viewer.events


import java.awt.image.{BufferedImage => JBufferedImage}
import java.util.UUID




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class DisplayBitmapEvent(bitmapId: UUID, bitmapBuffer: JBufferedImage)
    extends ViewerEvent with Immutable {

}
