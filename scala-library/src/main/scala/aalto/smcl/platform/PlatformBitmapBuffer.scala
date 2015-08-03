package aalto.smcl.platform


import java.awt.image.BufferedImage




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object PlatformBitmapBuffer {

  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @return
   */
  def apply(widthInPixels: Int, heightInPixels: Int): PlatformBitmapBuffer = {
    new PlatformBitmapBuffer(widthInPixels, heightInPixels)
  }

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] class PlatformBitmapBuffer private(val widthInPixels: Int, val heightInPixels: Int) {

  /** */
  val awtBufferedImage =
    new BufferedImage(widthInPixels, heightInPixels, BufferedImage.TYPE_INT_ARGB)

  /** */
  def drawingSurface(): PlatformDrawingSurface = PlatformDrawingSurface(this)

}
