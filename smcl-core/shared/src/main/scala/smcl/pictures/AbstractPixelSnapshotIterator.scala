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




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
abstract class AbstractPixelSnapshotIterator
    extends AbstractIterator[Pixel] {

  /** */
  def relatedPixelSnapshot: PixelSnapshot

  /**
   *
   *
   * @return
   */
  @inline
  def widthInPixels: Int = relatedPixelSnapshot.widthInPixels

  /**
   *
   *
   * @return
   */
  @inline
  def heightInPixels: Int = relatedPixelSnapshot.heightInPixels

  /**
   *
   *
   * @return
   */
  @inline
  def minXInPixels: Int = relatedPixelSnapshot.minXInPixels

  /**
   *
   *
   * @return
   */
  @inline
  def maxXInPixels: Int = relatedPixelSnapshot.maxXInPixels

  /**
   *
   *
   * @return
   */
  @inline
  def minYInPixels: Int = relatedPixelSnapshot.minYInPixels

  /**
   *
   *
   * @return
   */
  @inline
  def maxYInPixels: Int = relatedPixelSnapshot.maxYInPixels

  /**
   *
   *
   * @return
   */
  @inline
  def currentXInPixels: Int

  /**
   *
   *
   * @return
   */
  @inline
  def currentYInPixels: Int

  /**
   *
   *
   * @return
   */
  @inline
  def rowHasChanged: Boolean

  /**
   *
   *
   * @return
   */
  @inline
  def columnHasChanged: Boolean


  /**
   *
   *
   * @return
   */
  @inline
  override
  def hasNext: Boolean

  /**
   *
   *
   * @return
   */
  @inline
  override
  def next(): Pixel

}
