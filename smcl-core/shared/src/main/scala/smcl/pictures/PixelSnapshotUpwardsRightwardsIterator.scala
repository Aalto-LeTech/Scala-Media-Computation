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
case class PixelSnapshotUpwardsRightwardsIterator(
    relatedPixelSnapshot: PixelSnapshot)
    extends AbstractPixelSnapshotIterator {

  /** */
  private
  var _currentXInPixels: Int = minXInPixels

  /** */
  private
  var _currentYInPixels: Int = maxYInPixels

  /** */
  private
  var _columnHasChanged: Boolean = false

  /**
   *
   *
   * @return
   */
  def currentXInPixels: Int = _currentXInPixels

  /**
   *
   *
   * @return
   */
  def currentYInPixels: Int = _currentYInPixels

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
    if (_currentYInPixels > minYInPixels) {
      _currentYInPixels -= 1
      _columnHasChanged = false
    }
    else {
      _currentYInPixels = maxYInPixels
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
    _currentYInPixels >= minYInPixels &&
        _currentXInPixels <= maxXInPixels

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
      _currentXInPixels, _currentYInPixels)

    advance()

    nextResult
  }

}
