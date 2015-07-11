package aalto.smcl.images

import java.awt.image.{BufferedImage => JBufferedImage}
import java.awt.{Graphics2D => JGraphics2D}
import scala.collection.immutable

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
object BitmapImageModel {

  /**
   *
   */
  private[images] def apply(
    controllerImage: BitmapImage,
    widthInPixels: Int,
    heightInPixels: Int,
    initialBackgroundColorOption: Option[Int] = None): BitmapImageModel = {

    require(controllerImage != null, "The controllerImage parameter cannot be null")
    require(widthInPixels > 0, s"Width of the image must be greater than zero (was $widthInPixels)")
    require(heightInPixels > 0, s"Height of the image must be greater than zero (was $heightInPixels)")

    val bgColor = initialBackgroundColorOption getOrElse 0x00000000

    val pixelBuffer = new JBufferedImage(widthInPixels, heightInPixels, JBufferedImage.TYPE_INT_ARGB)
    val m = new BitmapImageModel(controllerImage, pixelBuffer, bgColor)

    m.clear()

    return m
  }
}

/**
 *
 * @param controllerImage           [[BitmapImage]] instance, whose pixel data this model represents
 * @param pixelBuffer               the underlying Java's `BufferedImage` instance acting as a container for pixel data
 * @param initialBackgroundColor    background color at the time of creation as well as for clearing without a given color
 *
 * @author Aleksi Lukkarinen
 */
class BitmapImageModel private (
    private[this] val controllerImage: BitmapImage,
    val pixelBuffer: JBufferedImage,
    val initialBackgroundColor: Int) {

  /**
   * Returns a `Range` representing the range of numbers from
   * zero to the width of this [[BitmapImageModel]]'s pixel buffer.
   */
  def widthRange: Range.Inclusive = 0 to (pixelBuffer.getWidth - 1)

  /**
   * Returns a `Range` representing the range of numbers from
   * zero to the height of this [[BitmapImageModel]]'s pixel buffer.
   */
  def heightRange: Range.Inclusive = 0 to (pixelBuffer.getHeight - 1)

  /**
   *
   */
  def numberOfPixels: Int = pixelBuffer.getWidth * pixelBuffer.getHeight

  /**
   *  Returns Java's `Graphics2D` interface to support more advanced graphic capabilities.
   */
  def graphics2D: JGraphics2D = pixelBuffer.createGraphics()

  /**
   *
   */
  def clear(colorOption: Option[Int] = None): Unit = {
    val g = graphics2D
    val c = colorOption getOrElse initialBackgroundColor

    val awtc = new java.awt.Color(c, true)

    g.setPaint(awtc)
    g.fillRect(0, 0, pixelBuffer.getWidth, pixelBuffer.getHeight)
  }

  /**
   *
   */
  def pixelIntAt(x: Int, y: Int): Int = {
    require(widthRange.contains(x),
      s"The x coordinate must be >= zero and less than the width of the image (was $x)")

    require(heightRange.contains(y),
      s"The y coordinate must be >= zero and less than the height of the image (was $y)")

    pixelBuffer.getRGB(x, y)
  }

  /**
   *
   */
  def colorComponentsAt(x: Int, y: Int): immutable.Map[Symbol, Int] = {
    colorComponentsFrom(pixelIntAt(x, y))
  }

  /**
   *
   */
  def setPixelIntAt(x: Int, y: Int, pixelInt: Int): Unit = {
    require(widthRange.contains(x),
      s"The x coordinate must be >= zero and less than the width of the image (was $x)")

    require(heightRange.contains(y),
      s"The y coordinate must be >= zero and less than the height of the image (was $y)")

    pixelBuffer.setRGB(x, y, pixelInt)
  }

  /**
   *
   */
  def setColorComponentsAt(x: Int, y: Int, red: Int, green: Int, blue: Int, transparency: Int): Unit = {
    require(widthRange.contains(x),
      s"The x coordinate must be >= zero and less than the width of the image (was $x)")

    require(heightRange.contains(y),
      s"The y coordinate must be >= zero and less than the height of the image (was $y)")

    pixelBuffer.setRGB(x, y, pixelIntFrom(red, green, blue, transparency))
  }

  /**
   *
   */
  def redComponentAt(x: Int, y: Int): Int = redComponentFrom(pixelIntAt(x, y))

  /**
   *
   */
  def setRedComponentAt(x: Int, y: Int, red: Int): Unit =
    setPixelIntAt(x, y, withNewRedComponent(pixelIntAt(x, y), red))

  /**
   *
   */
  def greenComponentAt(x: Int, y: Int): Int = greenComponentFrom(pixelIntAt(x, y))

  /**
   *
   */
  def setGreenComponentAt(x: Int, y: Int, green: Int): Unit =
    setPixelIntAt(x, y, withNewGreenComponent(pixelIntAt(x, y), green))

  /**
   *
   */
  def blueComponentAt(x: Int, y: Int): Int = blueComponentFrom(pixelIntAt(x, y))

  /**
   *
   */
  def setBlueComponentAt(x: Int, y: Int, blue: Int): Unit =
    setPixelIntAt(x, y, withNewBlueComponent(pixelIntAt(x, y), blue))

  /**
   *
   */
  def transparencyComponentAt(x: Int, y: Int): Int = transparencyComponentFrom(pixelIntAt(x, y))

  /**
   *
   */
  def transparencyComponentAt_=(x: Int, y: Int, transparency: Int): Unit =
    setPixelIntAt(x, y, withNewTransparencyComponent(pixelIntAt(x, y), transparency))

  /**
   *
   */
  override def toString() = {
    s"[BitmapImageModel ${pixelBuffer.getWidth}x${pixelBuffer.getWidth} px]"
  }
  
}
