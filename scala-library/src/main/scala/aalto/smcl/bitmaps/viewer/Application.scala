package aalto.smcl.bitmaps.viewer


import java.awt.Cursor

import scala.swing.Swing

import rx.lang.scala.{Observable, Observer}

import aalto.smcl.bitmaps.{BitmapIdentity, Bitmap}
import aalto.smcl.bitmaps.viewer.events.external.{DisplayBitmapEvent, ExternalViewerEvent, ForceAllViewersToClose}




/**
 * Information and functionality related to this application.
 */
private[bitmaps] object Application {

  // @formatter:off




  import aalto.smcl.SMCL




  /** Full name of this application. */
  val FullName = s"${SMCL.AbbreviatedName} Bitmap Viewer"

  /** Default mouse cursor used by this application. */
  val DefaultCursor: Cursor = Cursor.getDefaultCursor

  /** Waiting mouse cursor used by this application. */
  val WaitCursor: Cursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)

  /** Hand mouse cursor used by this application. */
  val HandCursor: Cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)

  // @formatter:on

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] class Application(val incomingEventStream: Observable[ExternalViewerEvent])
    extends Observer[ExternalViewerEvent] {

  private[this] var _viewers = Map[BitmapIdentity, ViewerMainFrame]()

  /**
   *
   *
   * @param event
   */
  private[this] def processEvent(event: ExternalViewerEvent): Unit = event match {
    case DisplayBitmapEvent(bitmap) => createOrUpdateViewerFor(bitmap)

    case ForceAllViewersToClose() => closeAllViewersWithTheForce()

    case _ =>
      throw new IllegalArgumentException(
        "Unexpected error occurred: An unknown viewer event type encountered:\n$event")
  }

  /**
   *
   *
   * @param bitmap
   */
  private[this] def createOrUpdateViewerFor(bitmap: Bitmap): Unit = {
    val viewer = _viewers.getOrElse(bitmap.uniqueIdentifier, {
      val newViewer = ViewerMainFrame(bitmap)

      _viewers = _viewers + (bitmap.uniqueIdentifier -> newViewer)

      newViewer.centerOnScreen()
      newViewer
    })

    Swing.onEDT {
      viewer.updateBitmapBuffer(bitmap)
    }
  }


  /**
   *
   */
  private[this] def closeAllViewersWithTheForce(): Unit = {
    _viewers.values foreach {viewer =>
      Swing.onEDT {
        viewer.forceToClose()
      }
    }

    _viewers = _viewers.empty
  }

  // Set up an observer for the incoming event stream
  incomingEventStream.subscribe(new Observer[ExternalViewerEvent] {

    /**
     *
     *
     * @param event
     */
    override def onNext(event: ExternalViewerEvent): Unit = {
      processEvent(event)
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

    }

  })

}
