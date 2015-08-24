package aalto.smcl.bitmaps.operations


import aalto.smcl.bitmaps.CanvasesAreResizedBasedOnTransformations
import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.common._
import aalto.smcl.platform.PlatformBitmapBuffer




/**
 * Operation to scale a bitmap.
 *
 * @param sourceBitmap
 * @param scalingFactorVertical
 * @param scalingFactorHorizontal
 * @param resizeCanvasBasedOnTransformation
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class Scale(
    sourceBitmap: Bitmap,
    scalingFactorVertical: Double = 1.0,
    scalingFactorHorizontal: Double = 1.0,
    resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations))
    extends AbstractOperation with BufferProvider with Immutable {

  require(sourceBitmap != null, s"Scaling requires exactly one source image (was null).")

  /** Information about this [[Renderable]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "scalingFactorX" -> Option(scalingFactorVertical.toString),
    "scalingFactorY" -> Option(scalingFactorHorizontal.toString),
    "resizeCanvasBasedOnTransformation" -> Option(resizeCanvasBasedOnTransformation.toString)
  ))

  /** The [[BitmapOperationList]] instance resulting the bitmap to be scaled. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(Seq(sourceBitmap.operations))

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for new buffers provided by this [[Buffered]].
   *
   * @param sources     possible [[PlatformBitmapBuffer]] instances used as sources
   * @return
   */
  override protected def createStaticBuffer(sources: PlatformBitmapBuffer*): PlatformBitmapBuffer = {
    sources(0).createTransfomedVersionWith(
      AffineTransformation.forFreeScalingOf(scalingFactorVertical, scalingFactorHorizontal),
      resizeCanvasBasedOnTransformation)
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
   * a [[PlatformBitmapBuffer]] instance always after instantiation of
   * the class claiming to provide the buffer.
   *
   * @return    bitmap buffer to be made copies of for providees
   */
  override protected def provideNewBufferToBeCopiedForProvidees(): PlatformBitmapBuffer =
    getOrCreateStaticBuffer(sourceBitmap.toRenderedRepresentation)

}
