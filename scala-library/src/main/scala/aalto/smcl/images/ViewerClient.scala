package aalto.smcl.images


import java.awt.image.{BufferedImage => JBufferedImage}

import rx.lang.scala.{JavaConversions, Subject}

import aalto.smcl.images.immutable.Bitmap
import aalto.smcl.images.viewer.Application
import aalto.smcl.images.viewer.events.{DisplayBitmapEvent, ViewerEvent}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[images] class ViewerClient {

  /** */
  private val _outgoingEventSubject = Subject[ViewerEvent]()

  /** */
  private val _observableOutgoingEventSubject =
    JavaConversions.toScalaObservable(_outgoingEventSubject.asJavaObservable)

  /** */
  private val _viewerApplication = new Application(_observableOutgoingEventSubject)

  /**
   *
   *
   * @param sourceBitmap
   */
  def display(sourceBitmap: Bitmap): Unit = {
    require(sourceBitmap != null, "Source bitmap must not be null.")

    dispatchEvent(
      DisplayBitmapEvent(
        sourceBitmap.id,
        ImageUtils.deepCopy(sourceBitmap.toRenderedRepresentation)))
  }

  /**
   *
   *
   * @param event
   */
  private def dispatchEvent(event: ViewerEvent): Unit = {
    require(event != null, "Event to be dispatched must not be null.")

    _outgoingEventSubject.onNext(event)
  }

}
