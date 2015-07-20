package aalto.smcl.images


import java.awt.image.{BufferedImage => JBufferedImage}
import java.awt.{Color => JColor, Graphics2D => JGraphics2D}

import aalto.smcl.images.immutable.Color
import aalto.smcl.images.operations._




/**
 * Provides a way to add bitmap operations into [[OperableBitmap]] instances.
 *
 * @author Aleksi Lukkarinen
 */
object BitmapOps {

  /**
   * Adds a [[Clear]] operation to a given [[OperableBitmap]].
   */
  def clear(bmp: OperableBitmap, colorOption: Option[Color] = None): Unit = {
    bmp.apply(Clear(colorOption))
  }

}


//  /**
//   *
//   */
//  def pixelIntAt(x: Int, y: Int): Int = {
//    require(widthRange.contains(x),
//      s"The x coordinate must be >= zero and less than the width of the image (was $x)")
//
//    require(heightRange.contains(y),
//      s"The y coordinate must be >= zero and less than the height of the image (was $y)")
//
//    buffer.getRGB(x, y)
//  }
//
//  /**
//   *
//   */
//  def colorComponentsAt(x: Int, y: Int): collection.immutable.Map[Symbol, Int] = {
//    colorComponentsFrom(pixelIntAt(x, y))
//  }
//
//  /**
//   *
//   */
//  def setPixelIntAt(x: Int, y: Int, pixelInt: Int): Unit = {
//    require(widthRange.contains(x),
//      s"The x coordinate must be >= zero and less than the width of the image (was $x)")
//
//    require(heightRange.contains(y),
//      s"The y coordinate must be >= zero and less than the height of the image (was $y)")
//
//    buffer.setRGB(x, y, pixelInt)
//  }
//
//  /**
//   *
//   */
//  def setColorComponentsAt(x: Int, y: Int, red: Int, green: Int, blue: Int, transparency: Int): Unit = {
//    require(widthRange.contains(x),
//      s"The x coordinate must be >= zero and less than the width of the image (was $x)")
//
//    require(heightRange.contains(y),
//      s"The y coordinate must be >= zero and less than the height of the image (was $y)")
//
//    buffer.setRGB(x, y, pixelIntFrom(red, green, blue, transparency))
//  }
//
//  /**
//   *
//   */
//  def redComponentAt(x: Int, y: Int): Int = redComponentFrom(pixelIntAt(x, y))
//
//  /**
//   *
//   */
//  def setRedComponentAt(x: Int, y: Int, red: Int): Unit =
//    setPixelIntAt(x, y, withNewRedComponent(pixelIntAt(x, y), red))
//
//  /**
//   *
//   */
//  def greenComponentAt(x: Int, y: Int): Int = greenComponentFrom(pixelIntAt(x, y))
//
//  /**
//   *
//   */
//  def setGreenComponentAt(x: Int, y: Int, green: Int): Unit =
//    setPixelIntAt(x, y, withNewGreenComponent(pixelIntAt(x, y), green))
//
//  /**
//   *
//   */
//  def blueComponentAt(x: Int, y: Int): Int = blueComponentFrom(pixelIntAt(x, y))
//
//  /**
//   *
//   */
//  def setBlueComponentAt(x: Int, y: Int, blue: Int): Unit =
//    setPixelIntAt(x, y, withNewBlueComponent(pixelIntAt(x, y), blue))
//
//  /**
//   *
//   */
//  def transparencyComponentAt(x: Int, y: Int): Int = transparencyComponentFrom(pixelIntAt(x, y))
//
//  /**
//   *
//   */
//  def transparencyComponentAt_=(x: Int, y: Int, transparency: Int): Unit =
//    setPixelIntAt(x, y, withNewTransparencyComponent(pixelIntAt(x, y), transparency))
