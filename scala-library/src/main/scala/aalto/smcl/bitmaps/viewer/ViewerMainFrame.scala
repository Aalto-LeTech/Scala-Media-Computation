package aalto.smcl.bitmaps.viewer


import javax.swing.WindowConstants

import aalto.smcl.bitmaps.immutable.primitives.Bitmap

import scala.swing.Dialog.{Message, Options}
import scala.swing._
import scala.swing.event.Key.Modifier.{Control, Shift}
import scala.swing.event._

import aalto.smcl.common.SwingUtils
import aalto.smcl.platform.Screen




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] object ViewerMainFrame {

  // @formatter:off
  import aalto.smcl.SMCL._

  /** */
  val MsgAbout: String =
    s"""Image viewing application of the $FullName.
      |
      |Version $VersionString.
      |
      |$AbbreviatedName was originally created by $OriginalAuthorName in $InceptionYear as
      |a part of his Master's Thesis for Aalto University.""".stripMargin
  // @formatter:on

  /** */
  val MinimumFrameSize: Dimension = new Dimension(300, 200)

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
        .max(MinimumFrameSize.width)

    val height = (Screen.height * 0.8).toInt
        .min((bitmap.heightInPixels * 1.1).toInt)
        .max(MinimumFrameSize.height)

    new Dimension(width, height)
  }

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] class ViewerMainFrame private(
    private val _initialPreferredViewAreaSize: Dimension) extends Frame {

  /** */
  private val NoModifiers: Key.Modifiers = 0

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
    //horizontalScrollBarPolicy = ScrollPane.BarPolicy.Always
    //verticalScrollBarPolicy = ScrollPane.BarPolicy.Always
    peer.setWheelScrollingEnabled(false)
    focusable = true
    revalidate()

  }

  /** */
  val verticalSwingScrollbar = scroller.verticalScrollBar.peer

  /** */
  val horizontalSwingScrollbar = scroller.horizontalScrollBar.peer

  /** */
  val toolbar = new ToolBar() {

    name = "Main"

    contents += new Button(_actionMap.get('copyToClipboard).get) {

    }

    contents += new Button(_actionMap.get('saveToFile).get) {

    }

    contents += new Separator()

    contents += new Button(_actionMap.get('ZoomIn).get) {

    }

    contents += new Button(_actionMap.get('ZoomOut).get) {

    }

    contents += new Button(_actionMap.get('Zoom100Percent).get) {

    }

  }

  /** */
  val layoutBase = new BorderPanel {

    layout(toolbar) = BorderPanel.Position.North
    layout(scroller) = BorderPanel.Position.Center

    focusable = true

  }

  title = Application.FullName
  minimumSize = ViewerMainFrame.MinimumFrameSize
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
      // For non-sluggish performance, the keyboard processing is done via
      // event listener instead of relying on the Action-class-based accelerators.
      key match {
        case Key.Plus  => zoomInAction.apply()
        case Key.Minus => zoomOutAction.apply()

        case Key.Up | Key.KpUp | Key.Down | Key.KpDown |
             Key.Left | Key.KpLeft | Key.Right | Key.KpRight |
             Key.PageUp | Key.PageDown | Key.Home | Key.End =>

          val directions = representedDirections(key)

          val magnitude = modifiers match {
            case Shift => ScrollingMagnitude.Unit
            case _     => ScrollingMagnitude.Block
          }

          adjustScrollBars(directions, magnitude)

        case _ => ()
      }

    case MouseWheelMoved(source: Component, point: Point, modifiers: Key.Modifiers, rotation: Int)
      if rotation != 0 =>

      val upOrLeftDirection = rotation < 1

      modifiers match {
        case NoModifiers | Shift =>
          val direction = (modifiers, upOrLeftDirection) match {
            case (NoModifiers, true)  => ScrollingDirection.Upwards
            case (NoModifiers, false) => ScrollingDirection.Downwards
            case (Shift, true)         => ScrollingDirection.Leftwards
            case (Shift, false)        => ScrollingDirection.Rightwards

            case _ =>
              throw new RuntimeException(
                "Internal error: Invalid value match when determining scrolling direction.")
          }

          adjustScrollBars(Seq(direction), ScrollingMagnitude.Block, Math.abs(rotation))

        case Control =>
          cursor = Application.WaitCursor
          if (upOrLeftDirection) zoomOutAction.apply() else zoomInAction.apply()
          cursor = Application.DefaultCursor

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
   * @param magnitude
   * @param directions
   */
  def adjustScrollBars(
      directions: Seq[ScrollingDirection.Value],
      magnitude: ScrollingMagnitude.Value,
      steps: Int = 1) = {

    import aalto.smcl.bitmaps.viewer.ScrollingDirection._

    directions.foreach {direction =>
      val upOrLeftDirection = direction match {
        case Upwards | Leftwards => true
        case _                   => false
      }

      val targetScrollBar = direction match {
        case Upwards | Downwards => verticalSwingScrollbar
        case _                   => horizontalSwingScrollbar
      }

      val op = upOrLeftDirection match {
        case true => (_: Int) - (_: Int)
        case _    => (_: Int) + (_: Int)
      }

      val adjustment = magnitude match {
        case ScrollingMagnitude.Unit => targetScrollBar.getUnitIncrement
        case _                       => targetScrollBar.getBlockIncrement
      }

      val newPositionCandidate = op(targetScrollBar.getValue, adjustment)

      val newPosition = upOrLeftDirection match {
        case true => newPositionCandidate.max(targetScrollBar.getMinimum)
        case _    => newPositionCandidate.min(targetScrollBar.getMaximum)
      }

      targetScrollBar.setValue(newPosition)
    }
  }


  /**
   *
   *
   * @param key
   * @return
   */
  def representedDirections(key: Key.Value): Seq[ScrollingDirection.Value] = {
    var result = Seq[ScrollingDirection.Value]()

    if (representsMovingUpwards(key))
      result = result :+ ScrollingDirection.Upwards

    if (representsMovingDownwards(key))
      result = result :+ ScrollingDirection.Downwards

    if (representsMovingLeftwards(key))
      result = result :+ ScrollingDirection.Leftwards

    if (representsMovingRightwards(key))
      result = result :+ ScrollingDirection.Rightwards

    result
  }

  /**
   *
   *
   * @param key
   * @return
   */
  def representsMovingUpwards(key: Key.Value): Boolean =
    (key == Key.Up) || (key == Key.KpUp) || (key == Key.Home) || (key == Key.PageUp)

  /**
   *
   *
   * @param key
   * @return
   */
  def representsMovingDownwards(key: Key.Value): Boolean =
    (key == Key.Down) || (key == Key.KpDown) || (key == Key.End) || (key == Key.PageDown)

  /**
   *
   *
   * @param key
   * @return
   */
  def representsMovingLeftwards(key: Key.Value): Boolean =
    (key == Key.Left) || (key == Key.KpLeft) || (key == Key.Home) || (key == Key.End)

  /**
   *
   *
   * @param key
   * @return
   */
  def representsMovingRightwards(key: Key.Value): Boolean =
    (key == Key.Right) || (key == Key.KpRight) || (key == Key.PageUp) || (key == Key.PageDown)

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
      "Are you sure you want to exit without saving?\n\nUnsaved content will be lost.",
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
    cursor = Application.WaitCursor
    Dialog.showMessage(parent = this, ViewerMainFrame.MsgAbout, this.title)
    cursor = Application.DefaultCursor
  }

}
