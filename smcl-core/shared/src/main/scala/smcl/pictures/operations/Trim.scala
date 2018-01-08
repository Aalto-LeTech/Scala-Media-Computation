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


import smcl.colors.rgb._
import smcl.infrastructure._
import smcl.pictures.fullfeatured.AbstractBitmap
import smcl.settings.DefaultBackgroundColor




/**
 * Operation to trim a bitmap, i.e. to remove "empty" horizontal
 * and vertical slices of pixels from its edges.
 *
 * @param sourceBitmap
 * @param colorToTrim
 *
 * @author Aleksi Lukkarinen
 */
private[pictures]
case class Trim(
    sourceBitmap: AbstractBitmap,
    colorToTrim: Color = DefaultBackgroundColor)
    extends AbstractOperation
        with BufferProvider
        with Immutable {

  require(sourceBitmap != null, s"Trimming requires exactly one source image (was null).")
  require(colorToTrim != null, "The background color argument has to be a Color instance (was null).")

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "Trim"

  /** Information about this [[Renderable]] instance */
  lazy val describedProperties = Map(
    "colorToTrim" -> Option(s"0x${colorToTrim.toARGBInt.toARGBHexColorString}")
  )

  /** The [[BitmapOperationList]] instance resulting the bitmap to be trimmed. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(Seq(sourceBitmap.operations))

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
    sources(0).trim(colorToTrim)
  }

  /** Width of the provided buffer in pixels. */
  lazy val widthInPixels: Int =
    getOrCreateStaticBuffer(sourceBitmap.toRenderedRepresentation).widthInPixels

  /** Height of the provided buffer in pixels. */
  lazy val heightInPixels: Int =
    getOrCreateStaticBuffer(sourceBitmap.toRenderedRepresentation).heightInPixels

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
