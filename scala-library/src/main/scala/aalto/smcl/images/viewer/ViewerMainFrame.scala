package aalto.smcl.images.viewer


import java.awt.image.{BufferedImage => JBufferedImage}
import javax.swing.WindowConstants

import scala.swing.Dialog.{Message, Options}
import scala.swing._
import scala.swing.event._

import aalto.smcl.common.SwingUtils




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class ViewerMainFrame extends Frame {

  /** */
  private var _forcefulClosing: Boolean = false

  /** */
  val contentPanel = new ImageDisplayPanel()

  /** */
  val scroller = new ScrollPane() {contents = contentPanel}

  title = "SMCL Image Viewer"
  resizable = true
  preferredSize = new Dimension(600, 400)
  minimumSize = new Dimension(100, 100)
  contents = scroller

  reactions += {
    case WindowClosing(_) =>
      if (notToBeClosedForcefully) {
        if (shouldCloseBasedOn(closingMessageBoxResult()))
          hideAndDispose()
      }
      else {
        hideAndDispose()
      }

    case _ => ()
  }

  peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)

  listenTo(this)
  pack()

  /**
   *
   *
   * @return
   */
  def notToBeClosedForcefully: Boolean = !_forcefulClosing

  /**
   *
   *
   * @return
   */
  def forceToClose(): Unit = {
    _forcefulClosing = true
    publish(WindowClosing(this))
  }

  /**
   *
   */
  def showWithoutFocusTransfer(): Unit = {
    if (!visible)
      visible = true
  }

  /**
   *
   */
  def hideAndDispose(): Unit = {
    visible = false
    dispose()
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
  def closingMessageBoxResult(): Dialog.Result.Value = {
    Dialog.showConfirmation(
      this,
      "Do you really want to close this window without saving?",
      this.title,
      Options.YesNo,
      Message.Question
    )
  }

  /**
   *
   *
   * @param newContent
   */
  def updateBitmapBuffer(newContent: JBufferedImage): Unit = {
    contentPanel.updateImageBuffer(newContent)

    showWithoutFocusTransfer()
  }

}
