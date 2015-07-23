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
  minimumSize = new Dimension(200, 100)
  preferredSize = calculateInitialFrameSize
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
   */
  def calculateInitialFrameSize(): Dimension = {
    val tk = java.awt.Toolkit.getDefaultToolkit
    val screenWidth = tk.getScreenSize.width
    val screenHeight = tk.getScreenSize.height
    val bufferWidth = contentPanel.bufferWidth
    val bufferHeight = contentPanel.bufferHeight



    new Dimension(400, 400)
  }

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
