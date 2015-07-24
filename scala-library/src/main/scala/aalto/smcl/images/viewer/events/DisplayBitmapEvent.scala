package aalto.smcl.images.viewer.events


import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.images.immutable.Bitmap




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[images] case class DisplayBitmapEvent(bitmap: Bitmap)
    extends ViewerEvent with Immutable {

}
