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
 *
 *
 * @param bitmap
 * @param resourcePathOption
 * @param bitmapIndexInResourceOption
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class LoadedBitmap(
    bitmap: BitmapBufferAdapter,
    resourcePathOption: Option[String],
    bitmapIndexInResourceOption: Option[Int])
    extends AbstractOperation
        with BufferProvider
        with Immutable {

  require(resourcePathOption != null, "The resource path argument has to be a String or None (was null).")
  require(bitmapIndexInResourceOption != null, "The bitmap index argument has to be an Int or None (was null).")

  /** Width of the provided buffer in pixels. */
  override def widthInPixels: Int = bitmap.widthInPixels

  /** Height of the provided buffer in pixels. */
  override def heightInPixels: Int = bitmap.heightInPixels

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "LoadedBitmap"

  /** Information about this [[Renderable]] instance */
  lazy override val describedProperties = Map(
    "resourcePath" -> Option(resourcePathOption.getOrElse("<unknown>")),
    "imageIndexInFile" -> Option(bitmapIndexInResourceOption.fold("<undefined>"){_.toString})
  )

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[BufferProvider]].
   *
   * @return
   */
  override def createStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter = bitmap.copy

  /**
   * Returns the buffer from which the provided buffer copies are made.
   * Users of this trait must provide an implementation, which returns
   * a [[BitmapBufferAdapter]] instance always after instantiation of
   * the class claiming to provide the buffer.
   *
   * @return bitmap buffer to be made copies of for providees
   */
  override protected def provideNewBufferToBeCopiedForProvidees(): BitmapBufferAdapter = {
    getOrCreateStaticBuffer()
  }

}
