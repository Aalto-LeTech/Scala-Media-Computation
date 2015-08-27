package aalto.smcl.bitmaps.operations


import aalto.smcl.bitmaps._
import aalto.smcl.bitmaps.immutable.primitives.Bitmap
import aalto.smcl.common._
import aalto.smcl.platform.PlatformBitmapBuffer




/**
 * Operation to replicate one bitmap vertically a specified number of times.
 *
 * @param bitmapToReplicate
 * @param numberOfReplicas
 * @param paddingInPixels
 * @param backgroundColor
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class ReplicateVertically(
    bitmapToReplicate: Bitmap,
    numberOfReplicas: Int,
    paddingInPixels: Int = GS.intFor(DefaultPaddingInPixels),
    backgroundColor: RGBAColor = GS.colorFor(DefaultBackground))
    extends AbstractOperation with BufferProvider with Immutable {

  require(bitmapToReplicate != null,
    "Replicate operation must be given a non-empty Bitmap instance to replicate.")

  require(numberOfReplicas > 0, s"Number of replicas must be 1 or larger (was $numberOfReplicas)")
  require(paddingInPixels >= 0, s"The padding argument cannot be negative (was $paddingInPixels).")
  require(backgroundColor != null, "The background color argument has to be a Color instance (was null).")

  /** The [[BitmapOperationList]] instance resulting the bitmap to be replicated. */
  val childOperationListsOption: Option[Seq[BitmapOperationList]] =
    Option(Seq(bitmapToReplicate.operations))

  /** Information about this [[BufferProvider]] instance */
  lazy val metaInformation = MetaInformationMap(Map(
    "numberOfReplicas" -> Option(numberOfReplicas.toString),
    "padding" -> Option(s"$paddingInPixels px"),
    "backgroundColor" -> Option(s"0x${backgroundColor.toArgbInt.toArgbHexColorString}")))

  /** Height of the provided buffer in pixels. */
  val heightInPixels: Int =
    (numberOfReplicas + 1) * bitmapToReplicate.heightInPixels +
        numberOfReplicas * paddingInPixels

  /** Width of the provided buffer in pixels. */
  val widthInPixels: Int = bitmapToReplicate.widthInPixels

  BitmapValidator.validateBitmapSize(heightInPixels, widthInPixels)

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[BufferProvider]].
   *
   * @return
   */
  override def createStaticBuffer(sources: PlatformBitmapBuffer*): PlatformBitmapBuffer = {
    val bufferToReplicate = childOperationListsOption.get.head.render()

    val newBuffer = PlatformBitmapBuffer(widthInPixels, heightInPixels)
    val drawingSurface = newBuffer.drawingSurface()

    drawingSurface.clearUsing(backgroundColor)

    var yPosition = 0
    for (itemNumber <- 0 to numberOfReplicas) {
      drawingSurface.drawBitmap(bufferToReplicate, 0, yPosition)

      yPosition += bufferToReplicate.heightInPixels + paddingInPixels
    }

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
