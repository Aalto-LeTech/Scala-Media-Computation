package aalto.smcl.bitmaps.operations


import aalto.smcl.GS
import aalto.smcl.bitmaps._
import aalto.smcl.colors.RGBAColor
import aalto.smcl.infrastructure.MetaInformationMap
import aalto.smcl.platform.PlatformBitmapBuffer




/**
 * Operation to augment the "canvas" of a bitmap, i.e. to add "empty" space to its edges.
 *
 * @param sourceBitmap
 * @param extraPixelsOntoLeftEdge
 * @param extraPixelsOntoTopEdge
 * @param extraPixelsOntoRightEdge
 * @param extraPixelsOntoBottomEdge
 * @param color
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class AugmentCanvas(
    sourceBitmap: Bitmap,
    extraPixelsOntoLeftEdge: Int = 0,
    extraPixelsOntoTopEdge: Int = 0,
    extraPixelsOntoRightEdge: Int = 0,
    extraPixelsOntoBottomEdge: Int = 0,
    color: RGBAColor = GS.colorFor(DefaultBackground))
    extends AbstractOperation with BufferProvider with Immutable {

  require(sourceBitmap != null, s"Cropping requires exactly one source image (was null).")

  require(extraPixelsOntoLeftEdge >= 0,
    s"Number of extra pixels to be added onto the left edge must be >= 0 (was $extraPixelsOntoLeftEdge).")

  require(extraPixelsOntoTopEdge >= 0,
    s"Number of extra pixels to be added onto the top edge must be >= 0 (was $extraPixelsOntoTopEdge).")

  require(extraPixelsOntoRightEdge >= 0,
    s"Number of extra pixels to be added onto the right edge must be >= 0 (was $extraPixelsOntoRightEdge).")

  require(extraPixelsOntoBottomEdge >= 0,
    s"Number of extra pixels to be added onto the bottom edge must be >= 0 (was $extraPixelsOntoBottomEdge).")

  /** Information about this [[Renderable]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "extraPixelsOntoLeftEdge" -> Option(s"$extraPixelsOntoLeftEdge px"),
    "extraPixelsOntoTopEdge" -> Option(s"$extraPixelsOntoTopEdge px"),
    "extraPixelsOntoRightEdge" -> Option(s"$extraPixelsOntoRightEdge px"),
    "extraPixelsOntoBottomEdge" -> Option(s"$extraPixelsOntoBottomEdge px")
  ))

  /** The [[BitmapOperationList]] instance resulting the bitmap to be cropped. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(Seq(sourceBitmap.operations))

  /** Width of the provided buffer in pixels. */
  val widthInPixels: Int = extraPixelsOntoLeftEdge + sourceBitmap.widthInPixels + extraPixelsOntoRightEdge

  /** Height of the provided buffer in pixels. */
  val heightInPixels: Int = extraPixelsOntoTopEdge + sourceBitmap.heightInPixels + extraPixelsOntoBottomEdge

  BitmapValidator.validateBitmapSize(widthInPixels, heightInPixels)

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[Buffered]].
   *
   * @param sources     possible [[PlatformBitmapBuffer]] instances used as sources
   * @return
   */
  override protected def createStaticBuffer(sources: PlatformBitmapBuffer*): PlatformBitmapBuffer = {
    val newBuffer = PlatformBitmapBuffer(widthInPixels, heightInPixels)

    newBuffer.drawingSurface().clearUsing(color, useSourceColorLiterally = true)
    newBuffer.drawingSurface().drawBitmap(
      sources(0),
      extraPixelsOntoLeftEdge,
      extraPixelsOntoTopEdge)

    newBuffer
  }

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
