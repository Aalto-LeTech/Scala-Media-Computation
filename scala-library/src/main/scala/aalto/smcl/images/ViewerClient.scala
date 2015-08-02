package aalto.smcl.images


import java.awt.image.{BufferedImage => JBufferedImage}

import aalto.smcl.images.immutable.primitives.Bitmap

import scala.swing.Dialog

import rx.lang.scala.{JavaConversions, Subject}

import aalto.smcl.common.SwingUtils
import aalto.smcl.images.viewer.Application
import aalto.smcl.images.viewer.events.external.{DisplayBitmapEvent, ExternalViewerEvent, ForceAllViewersToClose}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[images] class ViewerClient {

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
    SwingUtils.yesNoDialogResultAsBoolean(result)

  /**
   *
   *
   * @return
   */
  def closingAllViewersMessageBoxResult(): Dialog.Result.Value = {
    SwingUtils.showParentlessYesNoQuestionDialog(
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
