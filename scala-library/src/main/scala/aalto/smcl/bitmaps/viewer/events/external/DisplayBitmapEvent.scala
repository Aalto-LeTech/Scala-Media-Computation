package aalto.smcl.bitmaps.viewer.events.external


import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.bitmaps.immutable.primitives.Bitmap




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class DisplayBitmapEvent(bitmap: Bitmap)
    extends ExternalViewerEvent with Immutable {

}
