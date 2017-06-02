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

package aalto.smcl.bitmaps.operations


import aalto.smcl.bitmaps.fullfeatured.AbstractBitmap
import aalto.smcl.infrastructure.BitmapBufferAdapter




/**
 * Operation to crop a bitmap, i.e. to keep only a specified rectangular window of the bitmap.
 *
 * @param sourceBitmap
 * @param windowTopLeftX
 * @param windowTopLeftY
 * @param windowBottomRightX
 * @param windowBottomRightY
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class Crop(
    sourceBitmap: AbstractBitmap,
    windowTopLeftX: Int,
    windowTopLeftY: Int,
    windowBottomRightX: Int,
    windowBottomRightY: Int)
    extends AbstractOperation
            with BufferProvider
            with Immutable {

  require(sourceBitmap != null, s"Cropping requires exactly one source image (was null).")

  require(windowTopLeftX >= 0 && windowTopLeftX < sourceBitmap.widthInPixels,
    "X coordinate of cropping window's top left corner was outside of the bitmap to be cropped.")

  require(windowTopLeftY >= 0 && windowTopLeftY < sourceBitmap.heightInPixels,
    "Y coordinate of cropping window's top left corner was outside of the bitmap to be cropped.")

  require(windowBottomRightX >= 0 && windowBottomRightX < sourceBitmap.widthInPixels,
    "X coordinate of cropping window's bottom right corner was outside of the bitmap to be cropped.")

  require(windowBottomRightY >= 0 && windowBottomRightY < sourceBitmap.heightInPixels,
    "Y coordinate of cropping window's bottom right corner was outside of the bitmap to be cropped.")

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "Crop"

  /** Information about this [[Renderable]] instance */
  lazy val describedProperties = Map(
    "windowTopLeftX" -> Option(s"$windowTopLeftX px"),
    "windowTopLeftY" -> Option(s"$windowTopLeftY px"),
    "windowBottomRightX" -> Option(s"$windowBottomRightX px"),
    "windowBottomRightY" -> Option(s"$windowBottomRightY px")
  )

  /** The [[BitmapOperationList]] instance resulting the bitmap to be cropped. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(Seq(sourceBitmap.operations))

  /** Width of the provided buffer in pixels. */
  val widthInPixels: Int = windowBottomRightX - windowTopLeftX + 1

  /** Height of the provided buffer in pixels. */
  val heightInPixels: Int = windowBottomRightY - windowTopLeftY + 1

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[Buffered]].
   *
   * @param sources possible [[BitmapBufferAdapter]] instances used as sources
   *
   * @return
   */
  override protected def createStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter = {
    sources(0).copyPortionXYWH(
      windowTopLeftX,
      windowTopLeftY,
      widthInPixels,
      heightInPixels)
  }

  /**
   * Returns the buffer from which the provided buffer copies are made.
   * Users of this trait must provide an implementation, which returns
   * a [[BitmapBufferAdapter]] instance always after instantiation of
   * the class claiming to provide the buffer.
   *
   * @return bitmap buffer to be made copies of for providees
   */
  override protected def provideNewBufferToBeCopiedForProvidees(): BitmapBufferAdapter = {
    getOrCreateStaticBuffer(sourceBitmap.toRenderedRepresentation)
  }

}
