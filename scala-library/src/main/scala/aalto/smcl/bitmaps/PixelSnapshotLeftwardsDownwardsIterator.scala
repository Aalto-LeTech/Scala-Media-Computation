package aalto.smcl.bitmaps


import scala.collection.AbstractIterator

import aalto.smcl.infrastructure.SMCLInitializationInvoker




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class PixelSnapshotLeftwardsDownwardsIterator(
  val relatedPixelSnapshot: PixelSnapshot)
  extends AbstractIterator[Pixel]
  with SMCLInitializationInvoker {

  /** A dummy variable needed to enforce the library initialization. */
  private val __smcl_initialization_ensuring_dummy_variable__ = null


  /** */
  lazy val widthInPixels: Int = relatedPixelSnapshot.widthInPixels

  /** */
  lazy val heightInPixels: Int = relatedPixelSnapshot.heightInPixels

  /** */
  val MinXInPixels: Int = 0

  /** */
  val MinYInPixels: Int = 0

  /** */
  lazy val MaxXInPixels: Int = widthInPixels - 1

  /** */
  lazy val MaxYInPixels: Int = heightInPixels - 1

  /** */
  private var _currentXInPixels: Int = MaxXInPixels

  /** */
  private var _currentYInPixels: Int = MinYInPixels

  /** */
  private var _rowHasChanged: Boolean = false

  /**
   *
   *
   * @return
   */
  private def currentXInPixels: Int = _currentXInPixels

  /**
   *
   *
   * @return
   */
  private def currentYInPixels: Int = _currentYInPixels

  /**
   *
   *
   * @return
   */
  def rowHasChanged: Boolean = hasNext && _rowHasChanged


  /**
   *
   */
  private def advance(): Unit = {
    if (_currentXInPixels > MinXInPixels) {
      _currentXInPixels -= 1
      _rowHasChanged = false
    }
    else {
      _currentXInPixels = MaxXInPixels
      _currentYInPixels += 1
      _rowHasChanged = true
    }
  }

  /**
   *
   *
   * @return
   */
  def hasNext: Boolean =
    _currentYInPixels <= MaxYInPixels &&
      _currentXInPixels >= MinXInPixels

  /**
   *
   *
   * @return
   */
  def next(): Pixel = {
    if (!hasNext)
      return Iterator.empty.next()

    val nextResult: Pixel = new Pixel(
      relatedPixelSnapshot,
      MinXInPixels, MaxXInPixels,
      MinYInPixels, MaxYInPixels,
      _currentXInPixels, _currentYInPixels)

    advance()

    nextResult
  }

}
