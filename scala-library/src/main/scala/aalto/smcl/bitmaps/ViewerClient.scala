package aalto.smcl.bitmaps


import scala.swing.Dialog

import rx.lang.scala.{JavaConversions, Subject}

import aalto.smcl.bitmaps.viewer.Application
import aalto.smcl.bitmaps.viewer.events.external.{DisplayBitmapEvent, ExternalViewerEvent, ForceAllViewersToClose}
import aalto.smcl.infrastructure.{SMCLInitializationInvoker, SwingUtils}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
class ViewerClient extends SMCLInitializationInvoker {

  /** A dummy variable needed to enforce the library initialization. */
  private val __smcl_initialization_ensuring_dummy_variable__ = null


  /** */
  private val _outgoingEventSubject = Subject[ExternalViewerEvent]()

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

    dispatchEvent(DisplayBitmapEvent(sourceBitmap))
  }

  /**
   *
   */
  def closeAllViewersWithTheForce(): Unit = {
    if (shouldCloseBasedOn(closingAllViewersMessageBoxResult()))
      dispatchEvent(ForceAllViewersToClose())
  }

  /**
   *
   *
   * @return
   */
  def shouldCloseBasedOn(result: Dialog.Result.Value): Boolean =
    new SwingUtils().yesNoDialogResultAsBoolean(result)

  /**
   *
   *
   * @return
   */
  def closingAllViewersMessageBoxResult(): Dialog.Result.Value = {
    new SwingUtils().showParentlessYesNoQuestionDialog(
      "Do you really want to close all bitmap viewers without saving?\nALL unsaved bitmaps will be LOST.",
      "SMCL")
  }

  /**
   *
   *
   * @param event
   */
  def dispatchEvent(event: ExternalViewerEvent): Unit = {
    require(event != null, "Event to be dispatched must not be null.")

    _outgoingEventSubject.onNext(event)
  }

}
