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
import aalto.smcl.infrastructure._
import aalto.smcl.modeling.AffineTransformation
import aalto.smcl.settings.{CanvasesAreResizedBasedOnTransformations, DefaultBackgroundColor}




/**
 * Operation to rotate a bitmap around its center.
 *
 * @param sourceBitmap
 * @param angleInDegrees
 * @param resizeCanvasBasedOnTransformation
 * @param backgroundColor
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class Rotate(
    sourceBitmap: AbstractBitmap,
    angleInDegrees: Double,
    resizeCanvasBasedOnTransformation: Boolean = CanvasesAreResizedBasedOnTransformations,
    backgroundColor: Color = DefaultBackgroundColor)
    extends AbstractOperation
        with BufferProvider
        with Immutable {

  require(sourceBitmap != null, s"Rotation requires exactly one source image (was null).")
  require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

  /** First text paragraph of the description of this class. */
  val descriptionTitle: String = "Rotate"

  /** Information about this [[Renderable]] instance */
  lazy val describedProperties = Map(
    "angle" -> Option(s"$angleInDegrees deg"),
    "resizeCanvasBasedOnTransformation" -> Option(resizeCanvasBasedOnTransformation.toString),
    "backgroundColor" -> Option(s"0x${backgroundColor.toARGBInt.toARGBHexColorString}")
  )

  /** The [[BitmapOperationList]] instance resulting the bitmap to be rotated. */
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
  override
  protected
  def createStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter = {
    sources(0).createTransformedVersionWith(
      AffineTransformation.forPointCentredRotation(
        angleInDegrees,
        0.5 * sources(0).widthInPixels,
        0.5 * sources(0).heightInPixels),
      resizeCanvasBasedOnTransformation,
      backgroundColor)._1
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
  override
  protected
  def provideNewBufferToBeCopiedForProvidees(): BitmapBufferAdapter = {
    getOrCreateStaticBuffer(sourceBitmap.toRenderedRepresentation)
  }

}
