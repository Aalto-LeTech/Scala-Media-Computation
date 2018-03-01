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
case class NullPixelSnapshotIterator(
    relatedPixelSnapshot: NullPixelSnapshot)
    extends AbstractPixelSnapshotIterator {

  /**
   *
   *
   * @return
   */
  @inline
  def currentXInPixels: Int = minXInPixels

  /**
   *
   *
   * @return
   */
  @inline
  def currentYInPixels: Int = minYInPixels

  /**
   *
   *
   * @return
   */
  @inline
  def columnHasChanged: Boolean = false

  /**
   *
   *
   * @return
   */
  @inline
  def rowHasChanged: Boolean = false

  /**
   *
   *
   * @return
   */
  @inline
  def hasNext: Boolean = false

  /**
   *
   *
   * @return
   */
  @inline
  def next(): Pixel = Iterator.empty.next()

}
