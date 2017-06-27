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
import aalto.smcl.colors.rgb._
import aalto.smcl.modeling.AffineTransformation
import aalto.smcl.infrastructure._
import aalto.smcl.settings.{CanvasesAreResizedBasedOnTransformations, DefaultBackgroundColor}




/**
 * Operation to shear a bitmap.
 *
 * @param sourceBitmap
 * @param shearingFactorHorizontal
 * @param shearingFactorVertical
 * @param resizeCanvasBasedOnTransformation
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class Shear(
    sourceBitmap: AbstractBitmap,
    shearingFactorHorizontal: Double = 0.0,
    shearingFactorVertical: Double = 0.0,
    resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
    backgroundColor: Color = DefaultBackgroundColor)
    extends AbstractOperation
            with BufferProvider
            with Immutable {

  require(sourceBitmap != null, s"Shearing requires exactly one source image (was null).")
  require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "Shear"

  /** Information about this [[Renderable]] instance */
  lazy val describedProperties = Map(
    "shearingFactorHorizontal" -> Option(shearingFactorHorizontal.toString),
    "shearingFactorVertical" -> Option(shearingFactorVertical.toString),
    "resizeCanvasBasedOnTransformation" -> Option(resizeCanvasBasedOnTransformation.toString),
    "backgroundColor" -> Option(s"0x${backgroundColor.toARGBInt.toARGBHexColorString}")
  )

  /** The [[BitmapOperationList]] instance resulting the bitmap to be sheared. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(Seq(sourceBitmap.operations))

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for new buffers provided by this [[Buffered]].
   *
   * @param sources possible [[BitmapBufferAdapter]] instances used as sources
   *
   * @return
   */
  override protected def createStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter = {
    sources(0).createTransformedVersionWith(
      AffineTransformation.forFreeShearingOf(shearingFactorHorizontal, shearingFactorVertical),
      resizeCanvasBasedOnTransformation,
      backgroundColor)
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
