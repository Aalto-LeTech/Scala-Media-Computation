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


import scala.collection.AbstractIterator

import smcl.pictures.fullfeatured.AbstractBitmap




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class PixelSnapshotRightwardsUpwardsIterator[BitmapType <: AbstractBitmap](
    relatedPixelSnapshot: PixelSnapshot[BitmapType])
    extends AbstractIterator[Pixel[BitmapType]] {

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
  private var _currentYInPixels: Int = MaxYInPixels

  /** */
  private var _rowHasChanged: Boolean = false

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
  def rowHasChanged: Boolean = hasNext && _rowHasChanged


  /**
   *
   */
  private def advance(): Unit = {
    if (_currentXInPixels < MaxXInPixels) {
      _currentXInPixels += 1
      _rowHasChanged = false
    }
    else {
      _currentXInPixels = MinXInPixels
      _currentYInPixels -= 1
      _rowHasChanged = true
    }
  }

  /**
   *
   *
   * @return
   */
  def hasNext: Boolean =
    _currentYInPixels >= MinYInPixels &&
        _currentXInPixels <= MaxXInPixels

  /**
   *
   *
   * @return
   */
  def next(): Pixel[BitmapType] = {
    if (!hasNext)
      return Iterator.empty.next()

    val nextResult = Pixel[BitmapType](
      relatedPixelSnapshot,
      MinXInPixels, MaxXInPixels,
      MinYInPixels, MaxYInPixels,
      _currentXInPixels, _currentYInPixels)

    advance()

    nextResult
  }

}
