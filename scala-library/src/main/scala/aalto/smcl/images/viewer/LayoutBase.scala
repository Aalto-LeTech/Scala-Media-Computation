package aalto.smcl.images.viewer


import java.awt.event.KeyEvent
import java.util.Calendar

import scala.swing._
import scala.swing.event.{Key, KeyPressed, MouseWheelMoved}




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[viewer] class LayoutBase(
    private val _imagePanel: ImageDisplayPanel) extends BorderPanel {

  /** */
  private val ENLARGEMENT_ZOOMFACTOR: Double = 1.1

  /** */
  private val REDUCTION_ZOOMFACTOR: Double = 0.9

  /** */
  private val _calendar = Calendar.getInstance()

  /** */
  private val _zoomKeyBindings = Map[Key.Value, ZoomFactor](
    Key.Key0 -> ZoomFactor.IDENTITY,
    Key.Key1 -> ZoomFactor(150),
    Key.Key2 -> ZoomFactor(200),
    Key.Key3 -> ZoomFactor(300),
    Key.Key4 -> ZoomFactor(400),
    Key.Key5 -> ZoomFactor(500),
    Key.Key6 -> ZoomFactor(60),
    Key.Key7 -> ZoomFactor(75),
    Key.Key8 -> ZoomFactor(80),
    Key.Key9 -> ZoomFactor(90)
  )

  /** */
  private val _scrollerViewportBackground = new GridBagPanel {

    layout(_imagePanel) = new Constraints
    background = new Color(40, 40, 40)

  }

  /** */
  private val _scroller = new ScrollPane {

    contents = _scrollerViewportBackground

    horizontalScrollBarPolicy = ScrollPane.BarPolicy.Always
    verticalScrollBarPolicy = ScrollPane.BarPolicy.Always

    focusable = true

  }

  /** */
  private val _verticalSwingScrollbar = _scroller.verticalScrollBar.peer

  /** */
  private val _horizontalSwingScrollbar = _scroller.horizontalScrollBar.peer

  layout(_scroller) = BorderPanel.Position.Center

  focusable = true

  reactions += {
    case KeyPressed(source: Component, key: Key.Value, modifiers: Key.Modifiers, location: Key.Location.Value) =>
      key match {
        case Key.Plus | Key.Minus =>
          val adjustmentFactor = enlargingIfTrueElseReducing(key == Key.Plus)
          _imagePanel.adjustZoomWith(_.adjustByIfPossible(adjustmentFactor))

        case _ =>
          _zoomKeyBindings.get(key).map(factor => _imagePanel.adjustZoomWith(_ => factor))
      }

    case MouseWheelMoved(source: Component, point: Point, modifiers: Key.Modifiers, rotation: Int) =>
      modifiers match {
        case Key.Modifier.Control =>
          val adjustmentFactor = enlargingIfTrueElseReducing(rotation > 0)
          _imagePanel.adjustZoomWith(_.adjustByIfPossible(Math.abs(rotation) * adjustmentFactor))

        case Key.Modifier.Shift => scrollToRightIfTrueElseToLeft(rotation > 0, source)
        case 0                  => scrollDownwardsIfTrueElseUpwards(rotation > 0, source)
        case _                  => ()
      }

    case _ => ()
  }

  listenTo(
    this, this.mouse.clicks, this.mouse.moves, this.mouse.wheel, this.keys,
    _scrollerViewportBackground.mouse.clicks, _scrollerViewportBackground.mouse.moves,
    _scrollerViewportBackground.mouse.wheel, _scrollerViewportBackground.keys,
    _imagePanel.mouse.clicks, _imagePanel.mouse.moves, _imagePanel.mouse.wheel, _imagePanel.keys)


  /**
   *
   *
   * @param downwardsIfTrue
   * @param source
   */
  def scrollDownwardsIfTrueElseUpwards(downwardsIfTrue: Boolean, source: Component) = {
    val keyCode = if (downwardsIfTrue) KeyEvent.VK_PAGE_DOWN else KeyEvent.VK_PAGE_UP

    _verticalSwingScrollbar.dispatchEvent(awtKeyEventFor(source, KeyEvent.KEY_PRESSED, keyCode))
    _verticalSwingScrollbar.dispatchEvent(awtKeyEventFor(source, KeyEvent.KEY_RELEASED, keyCode))
  }

  /**
   *
   *
   * @param toRightIfTrue
   * @param source
   */
  def scrollToRightIfTrueElseToLeft(toRightIfTrue: Boolean, source: Component) = {
    val keyCode = if (toRightIfTrue) KeyEvent.VK_PAGE_DOWN else KeyEvent.VK_PAGE_UP

    _horizontalSwingScrollbar.dispatchEvent(awtKeyEventFor(source, KeyEvent.KEY_PRESSED, keyCode))
    _horizontalSwingScrollbar.dispatchEvent(awtKeyEventFor(source, KeyEvent.KEY_RELEASED, keyCode))
  }

  /**
   *
   *
   * @param keyEventId
   * @param virtualKeyCode
   * @return
   */
  def awtKeyEventFor(source: Component, keyEventId: Int, virtualKeyCode: Int) =
    new KeyEvent(source.peer, keyEventId, _calendar.getTimeInMillis, 0,
      virtualKeyCode, KeyEvent.CHAR_UNDEFINED, KeyEvent.KEY_LOCATION_UNKNOWN)

  /**
   *
   *
   * @param enlargeIfTrue
   * @return
   */
  def enlargingIfTrueElseReducing(enlargeIfTrue: Boolean) =
    if (enlargeIfTrue) ENLARGEMENT_ZOOMFACTOR else REDUCTION_ZOOMFACTOR

}
