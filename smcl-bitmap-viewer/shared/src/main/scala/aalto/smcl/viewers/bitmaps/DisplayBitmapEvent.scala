package aalto.smcl.viewers.bitmaps

import aalto.smcl.bitmaps.Bitmap
import aalto.smcl.viewers.ExternalViewerEvent




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class DisplayBitmapEvent(bitmap: Bitmap)
  extends ExternalViewerEvent
  with Immutable {

}
