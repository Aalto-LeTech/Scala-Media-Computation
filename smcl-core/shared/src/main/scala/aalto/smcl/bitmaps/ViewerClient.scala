package aalto.smcl.bitmaps


//import aalto.smcl.infrastructure.awt.SwingUtils


//import scala.swing.Dialog

//import rx.lang.scala.{JavaConversions, Subject}

//import aalto.smcl.bitmaps.viewer.Application
//import aalto.smcl.bitmaps.viewer.events.external.{DisplayBitmapEvent, ExternalViewerEvent, ForceAllViewersToClose}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
class ViewerClient {

  /** */
  //private val _outgoingEventSubject = Subject[ExternalViewerEvent]()

  /** */
  //private val _observableOutgoingEventSubject =
  //  JavaConversions.toScalaObservable(_outgoingEventSubject.asJavaObservable)

  /** */
  //private val _viewerApplication = new Application(_observableOutgoingEventSubject)


  /**
   *
   *
   * @param sourceBitmap
   */
  def display(sourceBitmap: Bitmap): Unit = {
    require(sourceBitmap != null, "Source bitmap must not be null.")

    //dispatchEvent(DisplayBitmapEvent(sourceBitmap))
  }

  /**
   *
   */
  def closeAllViewersWithTheForce(): Unit = {
    //if (ifUserWantsToForceAllViewersToBeClosed())
      // dispatchEvent(ForceAllViewersToClose())
  }

  /**
   *
   *
   * @return
   */
  /*
  def ifUserWantsToForceAllViewersToBeClosed(): Boolean = {
  new SwingUtils().showParentlessYesNoQuestionDialog(
    "Do you really want to close all bitmap viewers without saving?\nALL unsaved bitmaps will be LOST.",
    "SMCL")
  }
  */

  /**
   *
   *
   */
  /*
  def dispatchEvent(event: ExternalViewerEvent): Unit = {
    require(event != null, "Event to be dispatched must not be null.")

    //_outgoingEventSubject.onNext(event)
  }
  */
}
