package aalto.smcl.bitmaps


import scala.collection.AbstractIterator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
class PixelSnapshotDownwardsRightwardsIterator(
  val relatedPixelSnapshot: PixelSnapshot)
  extends AbstractIterator[Pixel] {

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
  private var _currentXInPixels: Int = MinXInPixels

  /** */
  private var _currentYInPixels: Int = MinYInPixels

  /** */
  private var _columnHasChanged: Boolean = false

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
  def columnHasChanged: Boolean = hasNext && _columnHasChanged

  /**
   *
   */
  private def advance(): Unit = {
    if (_currentYInPixels < MaxYInPixels) {
      _currentYInPixels += 1
      _columnHasChanged = false
    }
    else {
      _currentYInPixels = MinYInPixels
      _currentXInPixels += 1
      _columnHasChanged = true
    }
  }

  /**
   *
   *
   * @return
   */
  def hasNext: Boolean =
    _currentYInPixels <= MaxYInPixels && _currentXInPixels <= MaxXInPixels

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
