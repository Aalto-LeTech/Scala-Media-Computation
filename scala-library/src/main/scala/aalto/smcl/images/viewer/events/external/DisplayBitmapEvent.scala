package aalto.smcl.images.viewer.events.external

import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.images.immutable.Bitmap




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class DisplayBitmapEvent(bitmap: Bitmap)
    extends ExternalViewerEvent with Immutable {

}
