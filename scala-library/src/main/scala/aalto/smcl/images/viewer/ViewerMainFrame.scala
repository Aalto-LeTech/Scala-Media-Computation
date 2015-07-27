package aalto.smcl.images.viewer


import javax.swing.WindowConstants

import scala.swing.Dialog.{Message, Options}
import scala.swing._
import scala.swing.event.Key.Modifier.{Control, Shift}
import scala.swing.event._

import aalto.smcl.common.{Screen, SwingUtils}
import aalto.smcl.images.immutable.Bitmap




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[images] object ViewerMainFrame {

  // @formatter:off
  import aalto.smcl.Library._

  /** */
  val MSG_ABOUT: String =
    s"""Image viewing application of the $FULL_NAME.
      |
      |Version $VERSION_STRING.
      |
      |$ABBREVIATED_NAME was originally created by $ORIGINAL_AUTHOR_NAME in $INCEPTION_YEAR as
      |a part of his Master's Thesis for Aalto University.""".stripMargin
  // @formatter:on

  /** */
  val MIN_FRAME_SIZE: Dimension = new Dimension(300, 200)

  /**
   *
   *
   * @param bitmap
   */
  def apply(bitmap: Bitmap): ViewerMainFrame = {
    val initialViewAreaSize = initialViewAreaSizeFor(bitmap)
    val frame = new ViewerMainFrame(initialViewAreaSize)

    frame.updateBitmapBuffer(bitmap)
    frame
  }

  /**
   *
   *
   * @param bitmap
   * @return
   */
  def initialViewAreaSizeFor(bitmap: Bitmap): Dimension = {
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
    private val _initialPreferredViewAreaSize: Dimension) extends Frame {

  /** */
  private val NO_MODIFIERS: Key.Modifiers = 0

  /** */
  private val _actionMap = new ActionMap(this)

  /** */
  private var _forcefulClosing: Boolean = false

  /** */
  val imagePanel = new ImageDisplayPanel

  /** */
  val scrollerViewportBackground = new GridBagPanel {

    layout(imagePanel) = new Constraints
    background = new Color(40, 40, 40)
    revalidate()

  }

  /** */
  val scroller = new ScrollPane {

    contents = scrollerViewportBackground
    peer.getViewport.setPreferredSize(_initialPreferredViewAreaSize)
    horizontalScrollBarPolicy = ScrollPane.BarPolicy.Always
    verticalScrollBarPolicy = ScrollPane.BarPolicy.Always
    peer.setWheelScrollingEnabled(false)
    focusable = true
    revalidate()

  }

  /** */
  val verticalSwingScrollbar = scroller.verticalScrollBar.peer

  /** */
  val horizontalSwingScrollbar = scroller.horizontalScrollBar.peer

  /** */
  val layoutBase = new BorderPanel {

    layout(scroller) = BorderPanel.Position.Center

    focusable = true

  }

  title = Application.FULL_NAME
  minimumSize = ViewerMainFrame.MIN_FRAME_SIZE
  resizable = true
  contents = layoutBase

  peer.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE)

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

  val zoomInAction  = _actionMap.get('ZoomIn).get
  val zoomOutAction = _actionMap.get('ZoomOut).get

  reactions += {
    case KeyPressed(source: Component, key: Key.Value, modifiers: Key.Modifiers, location: Key.Location.Value) =>
      // NOTE (AL 26.7.2015):
      // For non-slugish performance, the keyboard processing is done via
      // event listener instead of relying on the Action-class-based accelerators.
      key match {
        case Key.Plus  => zoomInAction.apply()
        case Key.Minus => zoomOutAction.apply()
        case _         => ()
      }

    case MouseWheelMoved(source: Component, point: Point, modifiers: Key.Modifiers, rotation: Int)
      if rotation != 0 =>

      val upOrLeftDirection = rotation < 1

      modifiers match {
        case NO_MODIFIERS | Shift =>
          val targetScrollBar = modifiers match {
            case NO_MODIFIERS => verticalSwingScrollbar
            case _            => horizontalSwingScrollbar
          }

          val op = upOrLeftDirection match {
            case true => (_: Int) - (_: Int)
            case _    => (_: Int) + (_: Int)
          }

          val newPositionCandidate =
            op(targetScrollBar.getValue,
              targetScrollBar.getBlockIncrement * Math.abs(rotation))

          val newPosition = upOrLeftDirection match {
            case true => newPositionCandidate.max(targetScrollBar.getMinimum)
            case _    => newPositionCandidate.min(targetScrollBar.getMaximum)
          }

          targetScrollBar.setValue(newPosition)

        case Control =>
          cursor = Application.WAIT_CURSOR
          if (upOrLeftDirection) zoomOutAction.apply() else zoomInAction.apply()
          cursor = Application.DEFAULT_CURSOR

        case _ => ()
      }

    case WindowClosing(_) =>
      if (notToBeClosedForcefully) {
        if (shouldCloseBasedOn(closingMessageBoxResult()))
          hideAndDispose()
      }
      else {
        hideAndDispose()
      }
  }


  pack()

  listenTo(this,
    layoutBase.keys,
    scroller.mouse.clicks,
    scroller.mouse.moves,
    scroller.mouse.wheel)

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

  /**
   *
   */
  def showAboutBox(): Unit = {
    cursor = Application.WAIT_CURSOR
    Dialog.showMessage(parent = this, ViewerMainFrame.MSG_ABOUT, this.title)
    cursor = Application.DEFAULT_CURSOR
  }

}
