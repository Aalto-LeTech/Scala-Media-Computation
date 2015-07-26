package aalto.smcl.images.viewer


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
    val initialFrameSize = initialFrameSizeFor(bitmap)
    val frame = new ViewerMainFrame(initialFrameSize)

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
    private val _initialPreferredSize: Dimension) extends Frame {

  /** */
  private val _actionMap = new ActionMap(this)

  /** */
  val imagePanel = new ImageDisplayPanel

  /** */
  val layoutBase = new LayoutBase(imagePanel)

  /** */
  private var _forcefulClosing: Boolean = false

  title = "SMCL Image Viewer"
  minimumSize = ViewerMainFrame.MIN_FRAME_SIZE
  preferredSize = _initialPreferredSize
  resizable = true
  contents = layoutBase

  menuBar = MenuBuilder.newMenuBarUsing(_actionMap)
      .menu("Image", Option(Key.I))
      .item('copyToClipboard).separator()
      .item('saveToFile).separator()
      .item('exitViewer)
      .defined()
      .menu("View", Option(Key.V))
      .item('ZoomIn)
      .item('ZoomOut).separator()
      .item('Zoom60Percent)
      .item('Zoom70Percent)
      .item('Zoom80Percent)
      .item('Zoom90Percent)
      .item('Zoom100Percent)
      .item('Zoom150Percent)
      .item('Zoom200Percent)
      .item('Zoom300Percent)
      .item('Zoom400Percent)
      .item('Zoom500Percent)
      .defined()
      .menu("Help", Option(Key.H))
      .item('About)
      .defined()
      .get()

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
    imagePanel.updateImageBuffer(bitmap)

    showWithoutFocusTransfer()
  }

}
