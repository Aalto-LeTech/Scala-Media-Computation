/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package aalto.smcl.viewers.bitmaps.jvmawt


import java.awt.Cursor

import scala.swing.Swing

import rx.lang.scala.{Observable, Observer}

import aalto.smcl.SMCLLibrary
import aalto.smcl.bitmaps.{Bitmap, BitmapIdentity}
import aalto.smcl.viewers.bitmaps.DisplayBitmapEvent
import aalto.smcl.viewers.{ExternalViewerEvent, ForceAllViewersToCloseEvent}




/**
 * Information and functionality related to this application.
 */
private[jvmawt]
object ViewerManager {

  // @formatter:off

  /** Full name of this application. */
  val FullName = s"${SMCLLibrary.AbbreviatedName} Bitmap Viewer"

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
private[jvmawt]
class ViewerManager(val incomingEventStream: Observable[ExternalViewerEvent])
    extends Observer[ExternalViewerEvent] {

  private[this] var _viewers = Map[BitmapIdentity, ViewerMainFrame]()

  /**
   *
   *
   * @param event
   */
  private[this] def processEvent(event: ExternalViewerEvent): Unit = event match {
    case DisplayBitmapEvent(bitmap) => createOrUpdateViewerFor(bitmap)

    case ForceAllViewersToCloseEvent() => closeAllViewersWithTheForce()

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

    Swing.onEDT{
      viewer.updateBitmapBuffer(bitmap)
    }
  }


  /**
   *
   */
  private[this] def closeAllViewersWithTheForce(): Unit = {
    _viewers.values foreach {viewer =>
      Swing.onEDT{
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
