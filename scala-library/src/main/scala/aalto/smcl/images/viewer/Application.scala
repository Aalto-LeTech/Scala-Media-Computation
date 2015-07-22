package aalto.smcl.images.viewer


import java.awt.image.{BufferedImage => JBufferedImage}
import java.util.UUID

import rx.lang.scala.{Observable, Observer}

import aalto.smcl.images.viewer.events.{DisplayBitmapEvent, ViewerEvent}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[images] class Application(val incomingEventStream: Observable[ViewerEvent])
    extends Observer[ViewerEvent] {

  private[this] var _viewers = Map[UUID, ViewerMainFrame]()

  /**
   *
   *
   * @param event
   */
  private[this] def dispatchEvent(event: ViewerEvent): Unit = event match {
    case DisplayBitmapEvent(id, buffer) => createOrUpdateViewer(id, buffer)

    case _ =>
      throw new IllegalArgumentException(
        "Unexpected error occurred: An unknown viewer event type encountered:\n$event")
  }

  /**
   *
   *
   * @param id
   * @param newContent
   */
  private[this] def createOrUpdateViewer(id: UUID, newContent: JBufferedImage): Unit = {
    val viewer = _viewers.getOrElse(id, {
      val newViewer = new ViewerMainFrame()
      _viewers = _viewers + (id -> newViewer)
      newViewer
    })

    viewer.updateBitmapBuffer(newContent)
    viewer.visible = true
  }

  /**
   *
   */
  private[this] def closeAllViewers: Unit = {
    _viewers.foreach[Unit] {case (id, view) =>
      view.close()
      _viewers = _viewers - id
    }
  }


  // Set up an observer for the incoming event stream
  incomingEventStream.subscribe(new Observer[ViewerEvent] {

    /**
     *
     *
     * @param event
     */
    override def onNext(event: ViewerEvent): Unit = {
      dispatchEvent(event)
    }

    /**
     *
     *
     * @param error
     */
    override def onError(error: Throwable): Unit = {
      println("An unexpected error occurred with viewer's incoming event stream:\n" + error.toString)
    }

    /**
     *
     */
    override def onCompleted(): Unit = {
      closeAllViewers
    }

  })

}
