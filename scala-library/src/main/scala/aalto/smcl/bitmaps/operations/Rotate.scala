package aalto.smcl.bitmaps.operations


import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.bitmaps.{CanvasesAreResizedBasedOnTransformations, DefaultBackground}
import aalto.smcl.common._
import aalto.smcl.platform.PlatformBitmapBuffer




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
private[bitmaps] case class Rotate(
    sourceBitmap: Bitmap,
    angleInDegrees: Double,
    resizeCanvasBasedOnTransformation: Boolean = GS.isTrueThat(CanvasesAreResizedBasedOnTransformations),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground))
    extends AbstractOperation with BufferProvider with Immutable {

  require(sourceBitmap != null, s"Rotation requires exactly one source image (was null).")

  /** Information about this [[Renderable]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "angle" -> Option(s"$angleInDegrees deg"),
    "resizeCanvasBasedOnTransformation" -> Option(resizeCanvasBasedOnTransformation.toString),
    "backgroundColor" -> Option(s"0x${backgroundColor.toArgbInt.toArgbHexColorString}")
  ))

  /** The [[BitmapOperationList]] instance resulting the bitmap to be rotated. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(Seq(sourceBitmap.operations))

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[Buffered]].
   *
   * @param sources     possible [[PlatformBitmapBuffer]] instances used as sources
   * @return
   */
  override protected def createStaticBuffer(sources: PlatformBitmapBuffer*): PlatformBitmapBuffer = {
    sources(0).createTransfomedVersionWith(
      AffineTransformation.forFreeRotationOfAround(
        angleInDegrees,
        0.5 * sources(0).widthInPixels,
        0.5 * sources(0).heightInPixels),
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
   * a [[PlatformBitmapBuffer]] instance always after instantiation of
   * the class claiming to provide the buffer.
   *
   * @return    bitmap buffer to be made copies of for providees
   */
  override protected def provideNewBufferToBeCopiedForProvidees(): PlatformBitmapBuffer =
    getOrCreateStaticBuffer(sourceBitmap.toRenderedRepresentation)

}
