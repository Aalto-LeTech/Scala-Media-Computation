package aalto.smcl.platform


import java.awt.font.TextLayout

import scala.swing.Font

import aalto.smcl.bitmaps.BitmapValidator




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object FontProvider {

  /**
   *
   *
   * @return
   */
  def availableFonts(): Seq[Font] =
    UIProvider.awtGraphEnv.getAllFonts.toSeq

  /**
   *
   *
   * @param textToRender
   * @param font
   * @return
   */
  def renderText(
    textToRender: String,
    font: Font): PlatformBitmapBuffer = {

    // This has to be refactored if e.g. anti-aliasing can change e.g. via a global setting,
    // so that the FontRenderContext reflects the current settings. For now, it is based on
    // a constant bitmap.
    val FontRenderingContext =
      PlatformBitmapBuffer.OneByOneSpecimen.drawingSurface().use {ds =>
        ds.getFontRenderContext
      }

    val testfont = new Font("Arial", 0, 20)

    val layout = new TextLayout(textToRender, testfont, FontRenderingContext)
    val bounds = layout.getBounds

    val width = Math.ceil(bounds.getWidth).toInt
    val height = Math.ceil(bounds.getHeight).toInt

    BitmapValidator.validateBitmapSize(width, height)

    if (BitmapValidator.warningSizeLimitsAreExceeded(width, height)) {
      println("\n\nWarning: The resulting image is larger than the recommended maximum size")
    }

    val buffer = PlatformBitmapBuffer(width, height)
    buffer.drawingSurface().use {ds =>
      layout.draw(ds, 0, layout.getAscent)
    }

    buffer
  }

}
