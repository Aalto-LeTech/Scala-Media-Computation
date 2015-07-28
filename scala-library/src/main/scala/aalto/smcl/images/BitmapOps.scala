package aalto.smcl.images


import aalto.smcl.common.{Color, GlobalSettings}
import aalto.smcl.images.immutable.Bitmap
import aalto.smcl.images.operations._




/**
 * Provides a way to add bitmap operations into [[OperableBitmap]] instances.
 *
 * @author Aleksi Lukkarinen
 */
object BitmapOps {

  /**
   * Adds a [[Clear]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param colorOption
   * @return
   */
  def clear(
      bmp: OperableBitmap,
      colorOption: Option[Color] = Option(GlobalSettings.defaultBackgroundColor)): Bitmap = {

    require(bmp != null, "The bitmap to be cleared cannot be null.")
    require(colorOption != null, "The color argument has to be None or a Color instance (was null).")

    bmp.apply(Clear(colorOption))
  }

  /**
   * Adds a [[DrawCircle]] operation to a given [[OperableBitmap]].
   *
   * @param bmp
   * @param centerXInPixels
   * @param centerYInPixels
   * @param radiusInPixels
   * @param isFilled
   * @param lineColor
   * @param fillColor
   * @return
   */
  def drawCircle(
      bmp: OperableBitmap,
      centerXInPixels: Int,
      centerYInPixels: Int,
      radiusInPixels: Int,
      isFilled: Boolean,
      lineColor: Color = GlobalSettings.defaultPrimaryColor,
      fillColor: Color = GlobalSettings.defaultSecondaryColor): Bitmap = {

    require(bmp != null, "The bitmap argument has to be a Bitmap instance (was null).")
    require(lineColor != null, "The line color argument has to be a Color instance (was null).")
    require(fillColor != null, "The fill color argument has to be a Color instance (was null).")

    bmp.apply(DrawCircle(centerXInPixels, centerYInPixels, radiusInPixels, isFilled, lineColor, fillColor))
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
