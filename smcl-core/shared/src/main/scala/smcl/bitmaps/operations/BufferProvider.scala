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

package smcl.bitmaps.operations


import smcl.infrastructure.BitmapBufferAdapter




/**
 * Ensures that the [[AbstractOperation]] classes providing a buffer for rendering images
 * have necessary facilities for creating and returning that buffer.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
trait BufferProvider extends Buffered {
  this: AbstractOperation =>


  /** Width of the provided buffer in pixels. */
  def widthInPixels: Int

  /** Height of the provided buffer in pixels. */
  def heightInPixels: Int


  /**
   * Returns the buffer from which the provided buffer copies are made.
   * Users of this trait must provide an implementation, which returns
   * a [[smcl.infrastructure.BitmapBufferAdapter]] instance always after instantiation of
   * the class claiming to provide the buffer.
   *
   * @return bitmap buffer to be made copies of for providees
   */
  protected def provideNewBufferToBeCopiedForProvidees(): BitmapBufferAdapter


  /**
   * Returns a new buffer for applying bitmap operations.
   *
   * @return
   */
  final def createNewBuffer(): BitmapBufferAdapter = {
    val templateBuffer = provideNewBufferToBeCopiedForProvidees()

    templateBuffer.copy
  }

}
