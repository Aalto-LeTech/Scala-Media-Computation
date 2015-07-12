package aalto.smcl.images

import java.awt.{
  Color => JColor,
  Graphics2D => JGraphics2D
}
import java.awt.image.{ BufferedImage => JBufferedImage }
import scala.collection.immutable

/**
 *
 *
 * @param controllerImage           [[BitmapImage]] instance, whose pixel data this model represents
 * @param pixelBuffer               the underlying Java's `BufferedImage` instance acting as a container for pixel data
 * @param initialBackgroundColor    background color at the time of creation as well as for clearing without a given color
 *
 * @author Aleksi Lukkarinen
 */
private[images] trait Bitmap { this: PixelRectangle with ColorableBackground =>

  /** Storage buffer for the actual bitmap. */
  private[images] val pixelBuffer = new JBufferedImage(widthInPixels, heightInPixels, JBufferedImage.TYPE_INT_ARGB)

  /** Java's `Graphics2D` interface to support more advanced graphic capabilities. */
  private[images] val graphics2D: JGraphics2D = pixelBuffer.createGraphics()

  clear()

  /**
   *
   */
  def clear(colorOption: Option[Int] = None): Unit = {
    val g = graphics2D
    val color = colorOption getOrElse initialBackgroundColor
    val awtc = new JColor(color, true)

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

}
