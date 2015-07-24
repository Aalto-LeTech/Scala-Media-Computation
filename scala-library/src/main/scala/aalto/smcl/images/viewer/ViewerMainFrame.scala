package aalto.smcl.images.viewer


import java.awt.image.{BufferedImage => JBufferedImage}
import javax.swing.WindowConstants

import scala.swing.Dialog.{Message, Options}
import scala.swing._
import scala.swing.event._

import aalto.smcl.common.{Screen, SwingUtils}
import aalto.smcl.images.immutable.Bitmap




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[images] object ViewerMainFrame {

  /** */
  val MIN_FRAME_SIZE: Dimension = new Dimension(300, 200)


  /**
   *
   *
   * @param bitmap
   */
  def apply(bitmap: Bitmap): ViewerMainFrame = {
    val frameSize = initialFrameSizeFor(bitmap)
    val frame = new ViewerMainFrame(MIN_FRAME_SIZE, frameSize)

    frame.updateBitmapBuffer(bitmap)
    frame
  }

  /**
   *
   *
   * @param bitmap
   * @return
   */
  def initialFrameSizeFor(bitmap: Bitmap): Dimension = {
    val width = (Screen.width * 0.8).toInt
        .min((bitmap.widthInPixels * 1.1).toInt)
        .max(MIN_FRAME_SIZE.width)

    val height = (Screen.height * 0.8).toInt
        .min((bitmap.heightInPixels * 1.1).toInt)
        .max(MIN_FRAME_SIZE.height)

    new Dimension(width, height)
  }

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[images] class ViewerMainFrame private(
    private val _minimumSize: Dimension,
    private val _preferredSize: Dimension) extends Frame {

  /** */
  private var _forcefulClosing: Boolean = false

  /** */
  val contentPanel = new ImageDisplayPanel()

  /** */
  val scroller = new ScrollPane() {

    horizontalScrollBarPolicy = ScrollPane.BarPolicy.Always
    verticalScrollBarPolicy = ScrollPane.BarPolicy.Always

    contents = contentPanel
  }

  title = "SMCL Image Viewer"
  minimumSize = _minimumSize
  preferredSize = _preferredSize
  resizable = true
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

  pack()
  listenTo(this)

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
   * @param bitmap
   */
  def updateBitmapBuffer(bitmap: Bitmap): Unit = {
    contentPanel.updateImageBuffer(bitmap)

    showWithoutFocusTransfer()
  }

}
