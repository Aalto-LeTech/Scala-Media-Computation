package aalto.smcl.images

import java.awt.image._

/**
 *
 *
 * @author Aleksi Lukkarinen
 */
case class Pixels(val image: BitmapImage) {

  /**  */
  val MAX_X = image.widthInPixels - 1

  /**  */
  val MAX_Y = image.heightInPixels - 1

  /**  */
  val count = image.widthInPixels * image.heightInPixels

  /** The underlying BufferedImage instance acting as a container for pixel data. */
  private val _image: BufferedImage = new BufferedImage(
    image.widthInPixels, image.heightInPixels, BufferedImage.TYPE_INT_ARGB)

  /**
   *
   */
  def pixelIntAt(x: Int, y: Int): Int = {
    require(image.widthRange.contains(x),
      s"The x coordinate must be >= zero and less than the width of the image (was $x)")

    require(image.heightRange.contains(y),
      s"The y coordinate must be >= zero and less than the height of the image (was $y)")

    _image.getRGB(x, y)
  }

  /**
   *
   */
  def colorComponentsAt(x: Int, y: Int): Tuple4[Int, Int, Int, Int] = {
    colorComponentsFrom(pixelIntAt(x, y))
  }

  /**
   *
   */
  def setPixelIntAt(x: Int, y: Int, pixelInt: Int) = {
    require(image.widthRange.contains(x),
      s"The x coordinate must be >= zero and less than the width of the image (was $x)")

    require(image.heightRange.contains(y),
      s"The y coordinate must be >= zero and less than the height of the image (was $y)")

    _image.setRGB(x, y, pixelInt)
  }

  /**
   *
   */
  def setColorComponentsAt(x: Int, y: Int, red: Int, green: Int, blue: Int, transparency: Int) = {
    require(image.widthRange.contains(x),
      s"The x coordinate must be >= zero and less than the width of the image (was $x)")

    require(image.heightRange.contains(y),
      s"The y coordinate must be >= zero and less than the height of the image (was $y)")

    _image.setRGB(x, y, pixelIntFrom(red, green, blue, transparency))
  }

  /**
   *
   */
  def redComponentAt(x: Int, y: Int): Int = redComponentFrom(pixelIntAt(x, y))

  /**
   *
   */
  def setRedComponentAt(x: Int, y: Int, red: Int) =
    setPixelIntAt(x, y, withNewRedComponent(pixelIntAt(x, y), red))

  /**
   *
   */
  def greenComponentAt(x: Int, y: Int): Int = greenComponentFrom(pixelIntAt(x, y))

  /**
   *
   */
  def setGreenComponentAt(x: Int, y: Int, green: Int) =
    setPixelIntAt(x, y, withNewGreenComponent(pixelIntAt(x, y), green))

  /**
   *
   */
  def blueComponentAt(x: Int, y: Int): Int = blueComponentFrom(pixelIntAt(x, y))

  /**
   *
   */
  def setBlueComponentAt(x: Int, y: Int, blue: Int) =
    setPixelIntAt(x, y, withNewBlueComponent(pixelIntAt(x, y), blue))

  /**
   *
   */
  def transparencyComponentAt(x: Int, y: Int): Int = transparencyComponentFrom(pixelIntAt(x, y))

  /**
   *
   */
  def setTransparencyComponentAt(x: Int, y: Int, transparency: Int) =
    setPixelIntAt(x, y, withNewTransparencyComponent(pixelIntAt(x, y), transparency))

}
