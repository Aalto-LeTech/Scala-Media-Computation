package aalto.smcl.bitmaps.operations


import aalto.smcl.bitmaps._
import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.common._
import aalto.smcl.platform.PlatformBitmapBuffer




/**
 * Operation to overlay one bitmap over another bitmap.
 *
 * @param bottomBitmap
 * @param topBitmap
 * @param topBitmapUpperLeftX
 * @param topBitmapUpperLeftY
 * @param backgroundColor
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class OverlayFreely(
    bottomBitmap: Bitmap,
    topBitmap: Bitmap,
    topBitmapUpperLeftX: Int,
    topBitmapUpperLeftY: Int,
    topBitmapOpacity: Int = ColorValidator.MaximumRgbaOpacity,
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground))
    extends AbstractOperation with BufferProvider with Immutable {

  require(bottomBitmap != null, "The lower bitmap argument has to be a Bitmap instance (was null).")
  require(topBitmap != null, "The upper bitmap argument has to be a Bitmap instance (was null).")
  require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

  ColorValidator.validateRgbaOpacityComponent(topBitmapOpacity)

  /** The [[BitmapOperationList]] instances resulting the bitmaps to be combined. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(Seq(bottomBitmap.operations, topBitmap.operations))

  /** Information about this [[BufferProvider]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "topBitmapUpperLeftX" -> Option(s"$topBitmapUpperLeftX px"),
    "topBitmapUpperLeftY" -> Option(s"$topBitmapUpperLeftY px"),
    "topBitmapOpacity" -> Option(topBitmapOpacity.toString),
    "backgroundColor" -> Option(s"0x${backgroundColor.toArgbInt.toArgbHexColorString}")))

  /** Width of the provided buffer in pixels. */
  val widthInPixels: Int =
    if (topBitmapUpperLeftX < 0)
      (bottomBitmap.widthInPixels - topBitmapUpperLeftX).max(topBitmap.widthInPixels)
    else
      bottomBitmap.widthInPixels.max(topBitmapUpperLeftX + topBitmap.widthInPixels)

  /** Height of the provided buffer in pixels. */
  val heightInPixels: Int =
    if (topBitmapUpperLeftY < 0)
      (bottomBitmap.heightInPixels - topBitmapUpperLeftY).max(topBitmap.heightInPixels)
    else
      bottomBitmap.heightInPixels.max(topBitmapUpperLeftY + topBitmap.heightInPixels)

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[BufferProvider]].
   *
   * @return
   */
  override def createStaticBuffer(sources: PlatformBitmapBuffer*): PlatformBitmapBuffer = {
    var bottomX, bottomY, topX, topY: Int = 0

    if (topBitmapUpperLeftX < 0) {
      bottomX = -topBitmapUpperLeftX
      topX = 0
    }
    else {
      bottomX = 0
      topX = topBitmapUpperLeftX
    }

    if (topBitmapUpperLeftY < 0) {
      bottomY = -topBitmapUpperLeftY
      topY = 0
    }
    else {
      bottomY = 0
      topY = topBitmapUpperLeftY
    }

    val newBuffer = PlatformBitmapBuffer(widthInPixels, heightInPixels)
    val drawingSurface = newBuffer.drawingSurface()

    drawingSurface.clearUsing(backgroundColor)

    val bottomBuffer = bottomBitmap.toRenderedRepresentation
    val topBuffer = topBitmap.toRenderedRepresentation

    drawingSurface.drawBitmap(bottomBuffer, bottomX, bottomY)
    drawingSurface.drawBitmap(topBuffer, topX, topY, topBitmapOpacity)

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
    getOrCreateStaticBuffer()

}
