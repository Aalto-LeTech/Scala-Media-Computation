package aalto.smcl.bitmaps.operations


import aalto.smcl.fonts._
import aalto.smcl.geometry.AffineTransformation
import aalto.smcl.infrastructure.{BitmapBufferAdapter, MetaInformationMap}




/**
 * Operation to flip a bitmap vertically.
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps]
case class FlipVertically()
  extends AbstractOperation
  with OneSourceFilter
  with Immutable {

  /** Information about this [[Renderable]] instance */
  lazy val metaInformation = MetaInformationMap("FlipVertically", Map())

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[Buffered]].
   *
   * @param sources possible [[BitmapBufferAdapter]] instances used as sources
   * @return
   */
  override protected def createStaticBuffer(sources: BitmapBufferAdapter*): BitmapBufferAdapter = {
    require(sources.length == 1, s"Flip requires exactly one source image (provided: ${sources.length}).")

    sources(0).createTransformedVersionWith(
      AffineTransformation.forVerticalFlipOf(sources(0).heightInPixels))
  }

}
