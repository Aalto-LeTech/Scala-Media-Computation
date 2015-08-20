package aalto.smcl.bitmaps.operations


import scala.swing._

import aalto.smcl.common.MetaInformationMap
import aalto.smcl.platform.{FontProvider, PlatformBitmapBuffer}




/**
 *
 *
 * @param textToRender
 * @param font
 *
 * @author Aleksi Lukkarinen
 */
private[bitmaps] case class CreateText(textToRender: String, font: Font)
  extends AbstractOperation with BufferProvider with Immutable {

  require(textToRender != null, "The text argument cannot be null.")
  require(font != null, "The font argument cannot be null.")

  /**
   * Creates the buffer which contains the results of applying this operation
   * and which is used as a background for a new buffers provided by this
   * [[BufferProvider]].
   *
   * @return
   */
  override def createStaticBuffer(sources: PlatformBitmapBuffer*): PlatformBitmapBuffer =
    FontProvider.renderText(textToRender, font)

  /** Width of the provided buffer in pixels. */
  override val widthInPixels: Int = getOrCreateStaticBuffer().widthInPixels

  /** Height of the provided buffer in pixels. */
  override val heightInPixels: Int = getOrCreateStaticBuffer().heightInPixels

  /** Information about this [[Renderable]] instance */
  lazy override val metaInformation = MetaInformationMap(Map(
    "textToRender" -> Option(textToRender),
    "font" -> Option(font.getFontName),
    "width" -> Option(s"$widthInPixels px"),
    "height" -> Option(s"$heightInPixels px")
  ))

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
