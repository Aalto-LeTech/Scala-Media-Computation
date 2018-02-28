/* .            .           .                   .                 +             .          +      */
/*         +-----------+  +---+    +  +---+  +-----------+  +---+    Media Programming in Scala   */
/*   *     |           |  |    \     /    |  |           | +|   |            Since 2015           */
/*         |   +-------+  |     \   /     |  |   +-------+  |   |   .                        .    */
/*         |   |          |      \ /      |  |   |          |   |         Aalto University        */
/*       . |   +-------+  |   .   V   .   |  |   |   .      |   |      .   Espoo, Finland       . */
/*  +      |           |  |   |\     /|   |  |   |          |   |                  .    +         */
/*         +------+    |  |   | \   / |   |  |   |          |   |    +        *                   */
/*    *           |    |  |   |  \ /  |   |  |   |      *   |   |                     .      +    */
/*      -- +------+    |  |   |   V  *|   |  |   +-------+  |   +-------+ --    .                 */
/*    ---  |           |  |   | .     |   |  |           |  |           |  ---      +      *      */
/*  ------ +-----------+  +---+       +---+  +-----------+  +-----------+ ------               .  */
/*                                                                                     .          */
/*     T H E   S C A L A   M E D I A   C O M P U T A T I O N   L I B R A R Y      .         +     */
/*                                                                                    *           */

package smcl.pictures


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class PixelSnapshotDownwardsLeftwardsIterator(
    relatedPixelSnapshot: PixelSnapshot)
    extends AbstractPixelSnapshotIterator {

  /** */
  private
  var _currentX: Int = maxXInPixels

  /** */
  private
  var _currentY: Int = minYInPixels

  /** */
  private
  var _columnHasChanged: Boolean = false

  /**
   *
   *
   * @return
   */
  def currentXInPixels: Int = _currentX

  /**
   *
   *
   * @return
   */
  def currentYInPixels: Int = _currentY

  /**
   *
   *
   * @return
   */
  def rowHasChanged: Boolean = true

  /**
   *
   *
   * @return
   */
  def columnHasChanged: Boolean = hasNext && _columnHasChanged

  /**
   *
   */
  private
  def advance(): Unit = {
    if (_currentY < maxXInPixels) {
      _currentY += 1
      _columnHasChanged = false
    }
    else {
      _currentY = minYInPixels
      _currentX -= 1
      _columnHasChanged = true
    }
  }

  /**
   *
   *
   * @return
   */
  def hasNext: Boolean =
    _currentY <= maxYInPixels &&
        _currentX >= minXInPixels

  /**
   *
   *
   * @return
   */
  def next(): Pixel = {
    if (!hasNext)
      return Iterator.empty.next()

    val nextResult = Pixel(
      relatedPixelSnapshot,
      minXInPixels, maxXInPixels,
      minYInPixels, maxYInPixels,
      _currentX, _currentY)

    advance()

    nextResult
  }

}
