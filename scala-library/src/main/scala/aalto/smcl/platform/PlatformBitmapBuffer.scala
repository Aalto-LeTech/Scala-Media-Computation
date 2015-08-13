package aalto.smcl.platform


import java.awt.Graphics2D
import java.awt.image.BufferedImage

import aalto.smcl.bitmaps.BitmapValidator
import aalto.smcl.common.AffineTransformation




/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] object PlatformBitmapBuffer {

  /** */
  val NormalizedBufferType = BufferedImage.TYPE_INT_ARGB


  /**
   *
   *
   * @param widthInPixels
   * @param heightInPixels
   * @return
   */
  def apply(widthInPixels: Int, heightInPixels: Int): PlatformBitmapBuffer = {
    BitmapValidator.validateBitmapSize(widthInPixels, heightInPixels)

    val newBuffer = createNormalizedLowLevelBitmapBufferOf(widthInPixels, heightInPixels)

    new PlatformBitmapBuffer(newBuffer)
  }

  /**
   *
   *
   * @param awtBufferedImage
   * @return
   */
  def apply(awtBufferedImage: BufferedImage): PlatformBitmapBuffer = {
    require(awtBufferedImage != null, "Provided image buffer cannot be null.")

    BitmapValidator.validateBitmapSize(awtBufferedImage.getWidth, awtBufferedImage.getHeight)

    val normalizedAwtBuffer = convertToNormalizedLowLevelBitmapBufferIfNecessary(awtBufferedImage)

    new PlatformBitmapBuffer(normalizedAwtBuffer)
  }

  /**
   *
   *
   * @param width
   * @param height
   * @return
   */
  private[platform] def createNormalizedLowLevelBitmapBufferOf(width: Int, height: Int) =
    new BufferedImage(width, height, NormalizedBufferType)


  /**
   *
   *
   * @param buffer
   * @return
   */
  private[platform] def convertToNormalizedLowLevelBitmapBufferIfNecessary(
      buffer: BufferedImage): BufferedImage = {

    var bufferCandidate = buffer

    if (bufferCandidate.getType != NormalizedBufferType) {
      val newBuffer = createNormalizedLowLevelBitmapBufferOf(
        bufferCandidate.getWidth, bufferCandidate.getHeight)

      var drawingSurface: Graphics2D = null
      try {
        drawingSurface = newBuffer.createGraphics()
        drawingSurface.drawImage(bufferCandidate, null, 0, 0)
      }
      finally {
        drawingSurface.dispose()
      }

      bufferCandidate = newBuffer
    }

    bufferCandidate
  }

}


/**
 *
 *
 * @author Aleksi Lukkarinen
 */
private[smcl] class PlatformBitmapBuffer private(val awtBufferedImage: BufferedImage) {

  /** */
  lazy val widthInPixels: Int = awtBufferedImage.getWidth

  /** */
  lazy val heightInPixels: Int = awtBufferedImage.getHeight

  /** */
  def drawingSurface(): PlatformDrawingSurface = PlatformDrawingSurface(this)

  /**
   *
   *
   * @param transformation
   * @return
   */
  def createTransfomedVersionWith(transformation: AffineTransformation): PlatformBitmapBuffer = {
    val newBuffer = emptyAlike()

    newBuffer.drawingSurface().use[Unit] {ds =>
      ds.transform(transformation.platformAffineTransform.awtAffineTransformation)
      ds.drawImage(awtBufferedImage, 0, 0, null)
    }

    newBuffer
  }

  /**
   *
   *
   * @return
   */
  def copy(): PlatformBitmapBuffer =
    PlatformBitmapBuffer(BitmapUtils.deepCopy(awtBufferedImage))

  /**
   *
   *
   * @return
   */
  def emptyAlike(): PlatformBitmapBuffer =
    PlatformBitmapBuffer(widthInPixels, heightInPixels)

}
