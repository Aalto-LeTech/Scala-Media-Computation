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

package smcl.pictures.operations


import scala.ref.WeakReference

import smcl.infrastructure.BitmapBufferAdapter




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
trait Buffered {
  this: AbstractOperation =>

  /** Rendering buffer for this operation. */
  private[this] var _staticBuffer: WeakReference[BitmapBufferAdapter] =
    WeakReference[BitmapBufferAdapter](null)

  /**
   *
   *
   * @return
   */
  protected final def staticBufferOption: Option[BitmapBufferAdapter] =
    _staticBuffer.get

  /**
   *
   *
   * @param sources
   *
   * @return
   */
  protected final def getOrCreateStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter = {
    if (_staticBuffer.get.isEmpty) {
      _staticBuffer = WeakReference(createStaticBuffer(sources: _*))
    }

    _staticBuffer.get.get
  }

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[Buffered]].
   *
   * @param sources possible [[BitmapBufferAdapter]] instances used as sources
   *
   * @return
   */
  protected def createStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter

}
