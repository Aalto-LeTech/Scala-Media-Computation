package aalto.smcl.images.viewer


import java.awt.image.{BufferedImage => JBufferedImage}
import java.util.UUID

import scala.swing.Swing

import rx.lang.scala.{Observable, Observer}

import aalto.smcl.images.viewer.events.{ForceAllViewersToClose, DisplayBitmapEvent, ViewerEvent}




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
    case DisplayBitmapEvent(id, buffer) => createOrUpdateViewerFor(id, buffer)

    case ForceAllViewersToClose() => closeAllViewersWithTheForce()

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
  private[this] def createOrUpdateViewerFor(id: UUID, newContent: JBufferedImage): Unit = {
    val viewer = _viewers.getOrElse(id, {
      val newViewer = new ViewerMainFrame()

      _viewers = _viewers + (id -> newViewer)

      newViewer.centerOnScreen()
      newViewer
    })

    Swing.onEDT { viewer.updateBitmapBuffer(newContent) }
  }


  /**
   *
   */
  private[this] def closeAllViewersWithTheForce(): Unit = {
    _viewers.values.foreach { viewer =>
      Swing.onEDT { viewer.forceToClose() }
    }

    _viewers = _viewers.empty
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
      closeAllViewersWithTheForce
    }

  })

}
